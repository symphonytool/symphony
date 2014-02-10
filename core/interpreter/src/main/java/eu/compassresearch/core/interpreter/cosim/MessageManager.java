package eu.compassresearch.core.interpreter.cosim;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import eu.compassresearch.core.interpreter.cosim.communication.Utils;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;

/**
 * Wrapper class for Json communication. It wraps a socket and provides a simple message interface for communication.
 * This class uses {@link MessageCommunicator} to send and receive messages
 * 
 * @author kel
 */
public class MessageManager
{
	private BufferedReader input;
	private BufferedOutputStream output;

	public MessageManager(Socket socket) throws IOException
	{
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new BufferedOutputStream(socket.getOutputStream());
	}

	public void close()
	{
		try
		{
			input.close();
		} catch (IOException e)
		{
		}
		try
		{
			output.close();
		} catch (IOException e)
		{
		}
	}

	public void send(JsonMessage message) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		MessageCommunicator.sendRawMessage(output, message);
	}

	public JsonMessage receive() throws IOException
	{
		JsonMessage msg = null;

		while (msg == null)
		{
			msg = MessageCommunicator.receiveRawMessage(input);
			Utils.milliPause(10);
		}
		return msg;
	}
}
