package eu.compassresearch.rttMbtTmsClientApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.Base64;

public class RttMbtSocketResponseHandler {

	// patterns in the right data type
	private static byte[] closingBracketPattern = {'}'};
	private static byte[] jobAcknowledgePattern={'\"','j','o','b','-','a','c','k','n','o','w','l','e','d','g','e','\"'};
	private static byte[] progressItemPattern={'\"','p','r','o','g','r','e','s','s','-','i','t','e','m','\"'};
	private static byte[] consoleItemPattern={'\"','c','o','n','s','o','l','e','-','i','t','e','m','\"'};

	// client and command information
	private RttMbtClient client;
	private Boolean hasJobId;
	private Boolean hasProgress;
	private Boolean hasConsole;
	private Boolean isConnected;

	// sockets and streams
	private Socket clientSocket;
	private OutputStream commandStream;
	private InputStream replyStream;

	public RttMbtSocketResponseHandler() {
		// initialize attributes
		isConnected = false;
		hasJobId = false;
		client = null;
	}

	public String executeCommand(RttMbtClient client, String server, int port,
								 String command, Boolean consoleItems, Boolean progressItems) {
		String completeReply = "";
		this.client = client;
		hasConsole = consoleItems;
		hasProgress = progressItems;

		// connect to server
		if (!connectToServer(server, port)) {
			client.addErrorMessage("unable to connect to RTT-MBT server '" + server + ":" + port + "'!");
			return completeReply;
		}

		// send command to server
		if (!sendCommand(command, server, port)) {
			client.addErrorMessage("unable to send command to RTT-MBT server '" + server + ":" + port + "'!");
			return completeReply;
		}

		// receive reply and scan for job-acknowledge, console-items, progress items
		completeReply = receiveReply();

		return completeReply;
	}

	private Boolean connectToServer(String server, int port) {
		if (isConnected) {
			closeConnection();
		}
		isConnected = false;
		try {
			//1. creating a socket to connect to the server
			SocketAddress sockaddr = new InetSocketAddress(server, port);
			clientSocket = new Socket();
			clientSocket.connect(sockaddr, 10000);
			isConnected = true;
		}
		catch(UnknownHostException unknownHost){
			System.err.println("*** error: unknown host " + server + "!");
			return false;
		}
		catch(IOException ioException){
			System.err.println("*** error: unable to connect to " + server + ", port " + port + "!");
			return false;
		}
		finally{
		}
		return isConnected;
	}

	private Boolean sendCommand(String command, String server, int port) {
		if (!isConnected) {
			if (!connectToServer(server, port)) {
				System.err.println("*** error: unable to connect to server " + server + "!");
				return false;
			}
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
			return false;
		}		
		finally{
		}
		return true;
	}

	private String receiveReply() {
		if (!isConnected) {
			return null;
		}
		String message = "";
		int progress = -1;
		try{

			// read file content into buffer
			replyStream = clientSocket.getInputStream();
			int max_chunk_size = 1024*1024;
			int buffer_size = max_chunk_size;
			byte buffer[] = new byte[buffer_size];
			int offset = 0;
			int bytes_read = 0;
			while ((offset < buffer_size) && (bytes_read >= 0)) {
				bytes_read = replyStream.read(buffer, offset, buffer_size - offset);
				// only scan for progress and console items
				// if they are expected for this command
				if (hasProgress || hasConsole || (!hasJobId)) {
					byte[] chunkBuffer = null;
					if (bytes_read > 0) {
						chunkBuffer = new byte[bytes_read];
						System.arraycopy(buffer, offset, chunkBuffer, 0, bytes_read);
						if (!hasJobId) {
							scanForJobAcknowledge(chunkBuffer);
						}
						if ((progress < 100) && (hasProgress)) {
							progress = scanForProgressItem(chunkBuffer);
						}
						if ((client.getVerboseLogging()) && (hasConsole)) {
							scanForConsoleItem(chunkBuffer);
						}
					}
				}
				offset += bytes_read;
				if (offset == buffer_size) {
					// use new chunk
					byte[] old = buffer;
					buffer_size += max_chunk_size;
					buffer = new byte[buffer_size];
					System.arraycopy(old, 0, buffer, 0, buffer_size - max_chunk_size);
				}
			}

			// restrict buffer length to the number of bytes actually read
			if (offset < buffer_size) {
				byte[] binary = new byte[offset + 1];
				System.arraycopy(buffer, 0, binary, 0, offset + 1);
				buffer = binary;
			}

			// copy buffer to string for return type
			message = new String(buffer);
		
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

	private void closeConnection() {
		if (!isConnected) {
			return;
		}
		//Closing connection
		try{
			if (commandStream != null) commandStream.close();
			if (replyStream != null) replyStream.close();
			if (clientSocket != null) clientSocket.close();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
		}
		return;
	}

	// scan for patterns in byte arrays
	private int indexOfPattern(byte[] message, byte[] pattern, int offset) {
		Boolean found = false;

		// if message is shorter that the pattern, 
		if ((offset > message.length) || (offset < 0)) {
			return -1;
		}

		// if message is shorter that the pattern, 
		if ((message.length - offset) < pattern.length) {
			return -1;
		}

		// search for pattern in byte array
		int midx;
		for (midx = offset;
			(midx < (message.length - pattern.length) && (!found));
			midx++) {
			int pidx;
			for (pidx = 0; pidx < pattern.length; pidx++) {
				if (message[midx + pidx] != pattern[pidx]) {
					break;
				}
			}
			found = (pidx == pattern.length);
		}

		if (found) {
			return midx - 1;
		} else {
			return -1;
		}
	}

	private void scanForJobAcknowledge(byte[] message) {
		int start, end, first, last;
		start = indexOfPattern(message, jobAcknowledgePattern, 0);
		end = indexOfPattern(message, closingBracketPattern, start);
		while ((start != -1) && (end != -1)) {
			if ((start == -1) || (end == -1) || (start > end)) {
				return;
			}

			// extract item
			byte[] itemArray = new byte[(end - start) + 1];
			System.arraycopy(message, start, itemArray, 0, itemArray.length);
			String item = new String(itemArray);

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
			start = indexOfPattern(message, jobAcknowledgePattern, start + jobAcknowledgePattern.length);
			end = indexOfPattern(message, closingBracketPattern, start);
		}
	}
	
	private int scanForProgressItem(byte[] message) {
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
			byte[] itemArray = new byte[(end - start) + 1];
			System.arraycopy(message, start, itemArray, 0, itemArray.length);
			String item = new String(itemArray);

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
			start = indexOfPattern(message, progressItemPattern, start + progressItemPattern.length);
			end = indexOfPattern(message, closingBracketPattern, start);
		}
		return percent;
	}

	private void scanForConsoleItem(byte[] message) {
		int start, end, first, last;

		// prepare first loop
		start = indexOfPattern(message, consoleItemPattern, 0);
		end = indexOfPattern(message, closingBracketPattern, start);
		while ((start != -1) && (end != -1)) {

			if ((start == -1) || (end == -1) || (start > end)) {
				return;
			}

			// extract item
			byte[] itemArray = new byte[(end - start) + 1];
			System.arraycopy(message, start, itemArray, 0, itemArray.length);
			String item = new String(itemArray);

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
			start = indexOfPattern(message, consoleItemPattern, start + consoleItemPattern.length);
			end = indexOfPattern(message, closingBracketPattern, start);
		}
	}
}
