package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RelayClient extends Thread
{
	String host = "localhost";
	int port = 8882;
	private InetAddress address;
	private boolean connected;
	private Socket connection;
	
	public RelayClient() throws UnknownHostException
	{	
		address = InetAddress.getByName(host);
	}
	
	@Override
	public void run()
	{
	    try
		{
			connection = new Socket(address, port);
			connected = true;
			
			while (connected)
			{
				receive(); 
			
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SocketClient initialized");
	}
	
	private void receive() throws IOException
	{
		  String userInput;
	        BufferedReader stdIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));

	        System.out.println("Relay client: Response from server:");
	        while ((userInput = stdIn.readLine()) != null) {
	            System.out.println(userInput);
	        }
	}

	public void send(){
		
		 try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			writer.write("bla");
			
			 writer.flush();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public synchronized void die()
	{
		try
		{
			connected = false;
			connection.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
