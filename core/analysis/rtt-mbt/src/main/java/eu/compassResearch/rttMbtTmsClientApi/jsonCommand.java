/**
 * 
 */
package eu.compassResearch.rttMbtTmsClientApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author uwe
 *
 */
public class jsonCommand {
	
	private Socket clientSocket;
	private OutputStream commandStream;
	private InputStream replyStream;
	private String rttMbtServer;
	private Integer rttMbtServerPort;
	private Boolean isConnected;
	private Boolean success;
	private Boolean debugMode;
	private Boolean debugVerbose;
	private Boolean debugIMR;
	protected String userName;
	protected String userId;
	protected Boolean resultValue;
	protected RttMbtClient client;

	// constructor
	jsonCommand(RttMbtClient c) {
			client = c;
			rttMbtServer = client.getRttMbtServer();
			rttMbtServerPort = client.getRttMbtPort();
			userName = client.getUserName();
			userId = client.getUserId();
			isConnected = false;
			success = false;
			resultValue = false;
			debugMode = false;
			debugVerbose = false;
			debugIMR = false;
	}

	public void setDebugMode(Boolean value) { debugMode = value; }
	public void setDebugVerbose(Boolean value) { debugVerbose = value; }
	public void setDebugIMR(Boolean value) { debugIMR = value; }
	
	public String executeCommand() {
		String reply = "";
		
		// connect to server
		if (!connectToServer()) {
			System.err.println("*** error: unable to connect to server " + rttMbtServer + "!");
			return reply;
		}
		
		// send client information
		sendClientInformation();
		
		// send debug information request (if debugMode)
		sendDebugInformation();
		
		// send command
		sendCommand(getJsonCommandString());

		// receive reply
		reply = receiveReply();
		
		// handle reply
		processReply(reply);
		
		// close connection
		closeConnection();
		
		return reply;
	}
	
	public void sendClientInformation() {
		if (!isConnected) {
			if (!connectToServer()) {
				System.err.println("*** error: unable to connect to server " + rttMbtServer + "!");
				return;
			}
		}
		// send client information
		sendCommand("{\"client-information\":{\"user\":\"" + userName + "\",\"user-id\":\"" + userId + "\"}}");
	}

	public void sendDebugInformation() {
		if (!isConnected) {
			if (!connectToServer()) {
				System.err.println("*** error: unable to connect to server " + rttMbtServer + "!");
				return;
			}
		}
		if (!debugMode) {
			return;
		}
		// send debug information request
		String verbose;
		String imr;
		if (debugVerbose) { verbose = "true"; } else { verbose = "false"; }
		if (debugIMR) { imr = "true"; } else { imr = "false"; }
		sendCommand("{\"debug-information\":{\"verbose\":\"" + verbose + "\",\"imr-graph\":\"" + imr + "\"}}");
	}

	public Boolean connectToServer() {
		try{
			//1. creating a socket to connect to the server
			clientSocket = new Socket(rttMbtServer, rttMbtServerPort);
		}
		catch(UnknownHostException unknownHost){
			System.err.println("*** error: unknown host " + rttMbtServer + "!");
			return false;
		}
		catch(IOException ioException){
			return false;
		}
		finally{
		}
		isConnected = true;
		return true;
	}

	public void sendCommand(String command) {
		if (!isConnected) {
			return;
		}
		try{
			commandStream = clientSocket.getOutputStream();
			commandStream.flush();
			commandStream.write(command.getBytes());
			commandStream.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
			System.err.println("*** error: unable to send command!");
			return;
		}		
		finally{
		}
		return;
	}
	
	public String receiveReply() {
		String message = "";
		try{
			int character;
			replyStream = clientSocket.getInputStream();
			character = replyStream.read();
			while (character != -1) {
				message += (char)character;
				character = replyStream.read();
			}
		}
		catch(IOException ioException){
			System.err.println("*** error: unable to receive reply!");
			ioException.printStackTrace();
			return message;
		}		
		finally{
		}
		return message;
	}

	public void processReply(String replyString) {

		// generate list of JSON words:
		List<String> jsonWords = new ArrayList<String>();
		int pos = 0;
		String jsonWord = null;
		while (pos < replyString.length() && pos >= 0) {
			int next = replyString.indexOf("}{", pos);
			if (next == -1) {
				jsonWord = replyString.substring(pos);
			} else {
				jsonWord = replyString.substring(pos, next + 1);
			}
			jsonWords.add(jsonWord);
			if (next >=0) { pos = next + 1; } else { pos = -1; }
		}

		// parse JSON words
		JSONParser parser=new JSONParser();
		JSONObject reply = null;
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		int idx = 0;
		while (idx < jsonWords.size()) {
			try {
				Object obj = parser.parse(jsonWords.get(idx));
				reply = (JSONObject)obj;
				jsonObjects.add(reply);
			}
			catch (ParseException e) {
				System.err.println("*** error: unable to parse reply '" + replyString + "'!");
				e.printStackTrace();
			}
			idx++;
		}
		
		// handle replies
		success = true;
		idx = 0;
		while (idx < jsonObjects.size()) {
			reply = jsonObjects.get(idx);

			// if the JSON word is an exception with a problem list:
			// write list to stdout
			if (containsException(reply)) {
				success = false;
				String[] errorMsgs = getExceptions(reply);
				int erridx = 0;
				while (erridx < errorMsgs.length) {
					System.err.println("*** error: " + errorMsgs[erridx]);
					erridx++;
				}
				// if errors did occur, do NOT extract result files
				return;

			// If the JSON word is the expected parameter list;
			// process parameters
			} else if (containsDebugInfo(reply)) {
				// if reply contains debug information: dump zip file
				JSONObject parameters = (JSONObject)reply.get("debug-result");
				writeBase64StringFileContent("debug-information.zip",
						(String)parameters.get("debug-information.zip"), false);
			} else {
				// call handler for complete message
				handleCompletemessage(reply);
				// extract expected files from result string
				JSONObject parameters = getParameters(reply);
				if (parameters != null) {
					handleParameters(parameters);
				}
			}
			// next JSON object
			idx++;
		}		
	}

	public Boolean executedSuccessfully() {
		return success;
	}
	
	public Boolean getResult() {
		return resultValue;
	}
	
	public Boolean containsException(JSONObject reply) {
		if (reply == null) {
			return false;
		}
		return (reply.get("exception") != null );
	}

	public Boolean containsDebugInfo(JSONObject reply) {
		if (reply == null) {
			return false;
		}
		return (reply.get("debug-result") != null );
	}

	public String[] getExceptions(JSONObject reply) {
		if (reply == null) {
			return new String[0];
		}
		JSONObject exceptions = (JSONObject)reply.get("exception");
		if (exceptions == null) {
			return new String[0];
		}
		JSONArray problems = (JSONArray)exceptions.get("problems");
		if (problems == null) {
			return new String[0];
		}
		String[] errorMsgs = new String[problems.size()];
		int idx = 0;
		while (idx < problems.size()) {
			errorMsgs[idx] = (String)problems.get(idx);
			idx++;
		}
		return errorMsgs;
	}
	
	public void closeConnection() {
		if (!isConnected) {
			return;
		}
		//Closing connection
		try{
			if (commandStream != null) commandStream.close();
			if (replyStream != null) replyStream.close();
			clientSocket.close();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
		}
		return;
	}

    public byte[] gzipByteArray(byte[] content) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch(IOException e){
			System.err.println("*** error: gzip compression failed!");
			return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] gunzipByteArray(byte[] content){
		if (content.length == 0) {
			return new byte[0];
		}
		
    	int max_chunk_size = 1024*1024;
		int buffer_size = max_chunk_size;
		byte buffer[] = new byte[buffer_size];
        try {
        	// setup GZIP input stream from byte array
        	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
        	GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);

        	int offset = 0;
			int bytes_read = 0;
			while ((offset < buffer_size) && (bytes_read >= 0)) {
				bytes_read = gzipInputStream.read(buffer, offset, buffer_size - offset);
				offset += bytes_read;
				if (offset == buffer_size) {
					// use new chunk
					byte[] old = buffer;
					buffer_size += max_chunk_size;
					buffer = new byte[buffer_size];
					System.arraycopy(old, 0, buffer, 0, buffer_size - max_chunk_size);
				}
			}
			if (offset < buffer_size) {
				byte[] binary = new byte[offset + 1];
				System.arraycopy(buffer, 0, binary, 0, offset + 1);
				buffer = binary;
			}
        }
        catch(IOException e) {
			System.err.println("*** error: gzip decompression failed!");
			e.printStackTrace();
			return null;
        }
        return buffer;
    }

	public String getBase64StringFileContent(String filename, Boolean compressed) {
		String content = "";
		if (filename == null) return content;

		try{
			File file = new File(filename);
			if (!file.isFile()) {
				System.err.println("*** error: " + filename + " is not a regualr file!");
				return content;
			}
			if (!file.canRead()) {
				System.err.println("*** error: " + filename + " cannot be read!");
				return content;
			}

			// read file content into rawContent
			int max_chunk_size = 1024*1024;
			int buffer_size = max_chunk_size;
			InputStream fileStream = new FileInputStream(file);
			byte buffer[] = new byte[buffer_size];
			int offset = 0;
			int bytes_read = 0;
			while ((offset < buffer_size) && (bytes_read >= 0)) {
				bytes_read = fileStream.read(buffer, offset, buffer_size - offset);
				offset += bytes_read;
				if (offset == buffer_size) {
					// use new chunk
					byte[] old = buffer;
					buffer_size += max_chunk_size;
					buffer = new byte[buffer_size];
					System.arraycopy(old, 0, buffer, 0, buffer_size - max_chunk_size);
				}
			}
			fileStream.close();

			if (offset < buffer_size) {
				// restrict buffer length to the number of bytes actually read
				byte[] binary = new byte[offset + 1];
				System.arraycopy(buffer, 0, binary, 0, offset + 1);
				buffer = binary;
			}

			if (compressed) {
				// gzip compression of content
				buffer = gzipByteArray(buffer);
			}

			// base64 encode rawContent into content
			content = Base64.encodeBase64String(buffer);
		}

		catch(SecurityException e){
			System.err.println("*** error: not allowed to access " + filename + "!");
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			System.err.println("*** error: unable to find file " + filename + "!");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public long getFileSizeString(String filename) {
		long size = 0;
		try{
			File file = new File(filename);
			if (!file.isFile()) {
				System.err.println("*** error: " + filename + " is not a regualr file!");
				return size;
			}
			size = file.length();
		}		
		catch(SecurityException e){
			System.err.println("*** error: not allowed to access " + filename + "!");
			e.printStackTrace();
		}
		return size;
	}
	
	public String getSHA256Checksum(String filename) {
		String checksum = "";
		if (filename == null) return checksum;

		try{
			File file = new File(filename);
			if (!file.isFile()) {
				System.err.println("*** error: " + filename + " is not a regualr file!");
				return checksum;
			}
			if (!file.canRead()) {
				System.err.println("*** error: " + filename + " cannot be read!");
				return checksum;
			}

			// read file content into rawContent
			InputStream fileStream = new FileInputStream(file);
			MessageDigest hash = MessageDigest.getInstance("SHA-256");
			byte buffer[] = new byte[1024];
			int bytes_read = 0;
			while (bytes_read >= 0) {
				bytes_read = fileStream.read(buffer, 0, 1024);
				if (bytes_read >= 0) {
					// update SHA-256 checksum
					hash.update(buffer, 0, bytes_read);
				}
			}
			fileStream.close();
			
			// create hex string from hash value
			byte[] buff = hash.digest();
			for (int i=0; i < buff.length; i++) {
				checksum += Integer.toString( ( buff[i] & 0xff ) + 0x100, 16).substring( 1 );
			}
			//System.out.println("SHA-256 checksum of " + filename + ": " + checksum);
		}

		catch(SecurityException e){
			System.err.println("*** error: not allowed to access " + filename + "!");
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			System.err.println("*** error: unable to find file " + filename + "!");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e) {
			System.err.println("*** error: a problem occurred durgin the SHA-256 calculation for file " + filename + "!");
			e.printStackTrace();
		}

		return checksum;
	}

	public Boolean writeBase64StringFileContent(String filename, String encoded, Boolean compressed) {
		if (filename == null) return false;
		if (encoded == null) return false;

		try{
			File file = new File(filename);
			if (file.isFile()) {
				// System.err.println("*** warning: file " + filename + " already exists. The file will be replaced!");
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file.canWrite()) {
				System.err.println("*** error: " + filename + " is not writable!");
				return false;
			}

			// base64 decode encoded into content
			byte [] content = Base64.decodeBase64(encoded);
			
			if (compressed) {
				content = gunzipByteArray(content);
				if (content == null) {
					return false;
				}
			}

			// write file content
			OutputStream fileStream = new FileOutputStream(file);
			fileStream.write(content);
			fileStream.close();
		}

		catch(SecurityException e) {
			System.err.println("*** error: not allowed to access " + filename + "!");
			e.printStackTrace();
			return false;
		}
		catch (FileNotFoundException e) {
			System.err.println("*** error: unable to find file " + filename + "!");
			e.printStackTrace();
			return false;
		}
		catch (IOException e) {
			System.err.println("*** error: problem writing content to file " + filename + "!");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String getJsonCommandString() {
		return "{}";
	}

	public JSONObject getParameters(JSONObject reply) {
		return null;
	}

	public void handleParameters(JSONObject parameters) {
		return;
	}

	public void handleCompletemessage(JSONObject parameters) {
		return;
	}

	public String[] getExpectedFiles() {
		return new String[0];
	}
	
}
