package eu.compassresearch.core.interpreter.cosim;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import eu.compassresearch.core.interpreter.cosim.communication.Utils;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.ICoSimProtocol;
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
	private ICoSimProtocol protocol;
	
	public MessageManager(Socket socket,ICoSimProtocol protocol) throws IOException
	{
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new BufferedOutputStream(socket.getOutputStream());
		this.protocol = protocol;
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

	public void send(JsonMessage message) throws Exception
	{
		output.write(protocol.encode(message));
		output.write(System.lineSeparator().getBytes());
		output.flush();
	}

	public JsonMessage receive() throws IOException
	{
		JsonMessage msg = null;

		while (msg == null)
		{
			String strMessage = input.readLine();
			
//			logger.trace("Read RAW: " + strMessage);
			if (strMessage != null)
			{
				try
				{
					Object[] data =protocol.decode(strMessage.getBytes(), Object[].class);
					if (data != null && data.length > 0)
					{
						msg = (JsonMessage) data[0];
//						logger.debug("Read " + msg);
						msg.toString();
						break;
					}

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			
			
			Utils.milliPause(10);
		}
		return msg;
	}
}
