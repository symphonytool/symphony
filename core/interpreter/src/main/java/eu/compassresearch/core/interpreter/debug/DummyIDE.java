package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.debug.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;

public class DummyIDE
{

	public static void main(String[] args) throws IOException
	{
		int port = Integer.parseInt(args[0]);

		ServerSocket socket = new ServerSocket(port);
		socket.setSoTimeout(30000);

		try
		{

			while (true)
			{
				try
				{
					final Socket conn = socket.accept();

					Thread dummy = new Thread(new Runnable()
					{

						@Override
						public void run()
						{
							// InputStream input;
							try
							{
								BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								OutputStream outStream = conn.getOutputStream();
								MessageContainer msg = null;
								do
								{
									msg = MessageCommunicator.receiveMessage(reader);
									System.out.println(msg);
									JsonMessage imsg = msg.getMessage();
									if (imsg instanceof RequestMessage)
									{
										RequestMessage rmsg = (RequestMessage) msg.getMessage();
										if (rmsg.getRequest() == CmlRequest.SETUP)
										{
											MessageCommunicator.sendMessage(outStream, new ResponseMessage(null, CmlRequest.SETUP, ""));
										}
									}
								} while (msg != null);
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

					dummy.setDaemon(true);
					dummy.start();
				} catch (SocketTimeoutException e)
				{
				}
			}
		} finally
		{
			socket.close();
		}
	}

}
