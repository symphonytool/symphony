package eu.compassresearch.rttMbtTmsClientApi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RttMbtHttpResponseHandler implements ResponseHandler<Object> {

	private CharBuffer closingBracketPattern;
	private CharBuffer jobAcknowledgePattern;
	private CharBuffer progressItemPattern;
	private CharBuffer consoleItemPattern;

	private RttMbtClient client;
	private Boolean hasJobId;
	private Boolean hasProgress;
	private Boolean hasConsole;

	public RttMbtHttpResponseHandler() {

		// initialize patterns
		String pattern = "}";
		closingBracketPattern = CharBuffer.allocate(pattern.length());
		closingBracketPattern.put(pattern.toCharArray());
		pattern = "\"job-acknowledge\"";
		jobAcknowledgePattern = CharBuffer.allocate(pattern.length());
		jobAcknowledgePattern.put(pattern.toCharArray());
		pattern = "\"progress-item\"";
		progressItemPattern = CharBuffer.allocate(pattern.length());
		progressItemPattern.put(pattern.toCharArray());
		pattern = "\"console-item\"";
		consoleItemPattern = CharBuffer.allocate(pattern.length());
		consoleItemPattern.put(pattern.toCharArray());

		// initialize attributes
		hasJobId = false;
		client = null;
	}

	public static String executeCommand(RttMbtClient client, String url,
			String username, String password,
			String command, Boolean consoleItems, Boolean progressItems) {
		String completeReply = null;
		try {
			// split URL in server and path
			int pos = url.indexOf("://");
			String path = "/";
			String server = url.substring(pos + 3);
			pos = server.indexOf("/");
			if (pos != -1) {
				path = server.substring(pos);
				server = server.substring(0, pos);
			}

			KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
			/*
            // TODO! handle keystore
            FileInputStream instream = new FileInputStream(new File("my.keystore"));
            try {
                trustStore.load(instream, "nopassword".toCharArray());
            } finally {
                instream.close();
            }
			*/
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			// create HttpClient and define POST request
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost post = new HttpPost(path);
			post.setEntity(new StringEntity(command));

			// authentication credentials
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			// Create AuthCache instance
			AuthCache authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local authentication cache
			HttpHost targetHost = new HttpHost(server, 443, "https");
			AuthScope scope = new AuthScope(targetHost.getHostName(), targetHost.getPort());
			credentialsProvider.setCredentials(scope, credentials);
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);
			// Add AuthCache to the execution context
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credentialsProvider);
			context.setAuthCache(authCache);

			// execute command and receive response
			RttMbtHttpResponseHandler handler = new RttMbtHttpResponseHandler();
			handler.setClient(client);
			handler.setHasConsole(consoleItems);
			handler.setHasProgress(progressItems);
			completeReply = (String) httpclient.execute(targetHost, post, handler, context);
			httpclient.close();
		}
		catch (Exception e) {
			client.addErrorMessage("unable to send command to RTT-MBT server!");
			if (client.getVerboseLogging()) {
				client.addLogMessage("Problem details: " + e.toString());
			}
			e.printStackTrace();
		}
		return completeReply;
	}

	@Override
	public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String responseString = "";
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(
					statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		if (entity == null) {
			throw new ClientProtocolException("Response contains no content");
		}

		// init data structures
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		Reader reader = new InputStreamReader(entity.getContent(), charset);
		CharBuffer partMessage = CharBuffer.allocate(4096);
		int progress = -1;

		// read chunk
		int charRead = reader.read(partMessage);
		while (charRead != -1) {

			// copy chunk to response string
			partMessage.position(0);
			partMessage.limit(charRead);
			responseString = responseString + partMessage.toString();

			// only scan for progress and console items
			// if they are expected for this command
			if (hasProgress || hasConsole || (!hasJobId)) {
				if (charRead > 0) {
					if (!hasJobId) {
						scanForJobAcknowledge(partMessage);
					}
					if ((hasProgress) && (progress < 100)) {
						progress = scanForProgressItem(partMessage);
					}
					if ((hasConsole) && (client.getVerboseLogging())) {
						scanForConsoleItem(partMessage);
					}
				}
			}

			// read next chunk
			partMessage.clear();
			charRead = reader.read(partMessage);
		}

		// return response string to HttpClient.execute call.
		return responseString;
	}

	// scan for job acknowledge on byte array
	private int indexOfPattern(CharBuffer message, CharBuffer pattern, int offset) {
		Boolean found = false;

		// if message is shorter that the pattern, 
		if ((offset > message.limit()) || (offset < 0)) {
			return -1;
		}

		// if message is shorter that the pattern, 
		if ((message.limit() - offset) < pattern.limit()) {
			return -1;
		}

		// search for pattern in byte array
		int midx;
		for (midx = offset; (midx < (message.limit() - pattern.limit()) && (!found)); midx++) {
			int pidx;
			for (pidx = 0; pidx < pattern.limit(); pidx++) {
				char m = message.get(midx + pidx);
				char p = pattern.get(pidx);
				if (m != p) {
					break;
				}
			}
			found = (pidx == pattern.limit());
		}

		if (found) {
			return midx - 1;
		} else {
			return -1;
		}
	}

	private void scanForJobAcknowledge(CharBuffer message) {
		int start, end, first, last;
		start = indexOfPattern(message, jobAcknowledgePattern, 0);
		end = indexOfPattern(message, closingBracketPattern, start);
		while ((start != -1) && (end != -1)) {
			if ((start == -1) || (end == -1) || (start > end)) {
				return;
			}

			// extract item
			CharBuffer itemArray = CharBuffer.allocate((end - start) + 1);
			int position = message.position();
			int limit = message.limit();
			message.position(start);
			message.limit(end);
			itemArray.put(message);
			itemArray.position(0);
			message.position(position);
			message.limit(limit);
			String item = itemArray.toString();

			// extract job identification from item
			first = item.lastIndexOf(':') + 2;
			last = item.length() - 2;
			if ((first < 0) || (last > item.length())) return;
			String jobIdString = item.substring(first, last);
			if (client.getCurrentJobId() != null) {
				hasJobId = true;
			}
			client.setCurrentJobId(jobIdString);
			System.out.println("current job id: " + client.getCurrentJobId());

			// scan again in the rest of the message
			start = indexOfPattern(message, jobAcknowledgePattern, start + jobAcknowledgePattern.limit());
			end = indexOfPattern(message, closingBracketPattern, start);
		}
	}

	private int scanForProgressItem(CharBuffer message) {
		int percent, start, end, first, last, firstDigit, lastDigit;

		// prepare first loop
		percent = -1;
		start = indexOfPattern(message, progressItemPattern, 0);
		end = indexOfPattern(message, closingBracketPattern, start);
		while ((start != -1) && (end != -1) && (percent < 100)) {

			if ((start == -1) || (end == -1) || (start > end)) {
				return percent;
			}

			// extract item
			CharBuffer itemArray = CharBuffer.allocate((end - start) + 1);
			int position = message.position();
			int limit = message.limit();
			message.position(start);
			message.limit(end);
			itemArray.put(message);
			itemArray.position(0);
			message.position(position);
			message.limit(limit);
			String item = itemArray.toString();

			// extract percent from item
			first = item.indexOf(':') + 3;
			last = item.lastIndexOf(':');
			firstDigit = item.lastIndexOf(':') + 2;
			lastDigit = item.lastIndexOf('\"') ;
			if ((firstDigit >= 3) && (lastDigit >= firstDigit) && (lastDigit <= firstDigit + 3)) {
				String taskName = item.substring(first, last);
				String number = item.substring(firstDigit, lastDigit);
				percent = Integer.parseInt(number);
				client.setProgress(jsonCommand.String2Task(taskName), percent);
			}

			// scan again in the rest of the message
			start = indexOfPattern(message, progressItemPattern, start + progressItemPattern.limit());
			end = indexOfPattern(message, closingBracketPattern, start);
		}
		return percent;
	}

	private void scanForConsoleItem(CharBuffer message) {
		int start, end, first, last;

		// prepare first loop
		start = indexOfPattern(message, consoleItemPattern, 0);
		end = indexOfPattern(message, closingBracketPattern, start);
		while ((start != -1) && (end != -1)) {

			if ((start == -1) || (end == -1) || (start > end)) {
				return;
			}

			// extract item
			CharBuffer itemArray = CharBuffer.allocate((end - start) + 1);
			int position = message.position();
			int limit = message.limit();
			message.position(start);
			message.limit(end);
			itemArray.put(message);
			itemArray.position(0);
			message.position(position);
			message.limit(limit);
			String item = itemArray.toString();

			// extract console message from item
			first = item.lastIndexOf(':') + 3;
			last = item.lastIndexOf('\"') ;
			// - extract base64 encoded string
			String base64content = item.substring(first, last);
			// base64 decode encoded into content
			String content = new String(Base64.decodeBase64(base64content));
			// remove trailing newlines
			while (content.endsWith("\n")) {
				content = content.substring(0, content.length() -1);
			}
			// remove leading newlines
			while (content.startsWith("\n")) {
				content = content.substring(1);
			}
			if (content.length() > 0) {
				client.addLogMessage(content);			
			}
			// scan again in the rest of the message
			start = indexOfPattern(message, consoleItemPattern, start + consoleItemPattern.limit());
			end = indexOfPattern(message, closingBracketPattern, start);
		}
	}

	public void setClient(RttMbtClient client) {
		this.client = client;
	}

	public void setHasProgress(Boolean hasProgress) {
		this.hasProgress = hasProgress;
	}

	public void setHasConsole(Boolean hasConsole) {
		this.hasConsole = hasConsole;
	}

}