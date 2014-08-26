package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.eclipse.ecf.core.identity.ID;

public class RelayClient extends Thread
{
	String host = "localhost";
	int port;
	private InetAddress address;
	private boolean connected;
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;
	private DistributedSimulationManager distMgm;
	private ID senderID;

	public RelayClient(int port, ID senderID, DistributedSimulationManager distributedSimulationManager) throws IOException
	{
		this.port = port;
		this.senderID = senderID;
		this.distMgm = distributedSimulationManager;
		address = InetAddress.getByName(host);
		setDaemon(true);

		connection = new Socket(address, port);
		out = new PrintWriter(connection.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	@Override
	public void run()
	{
		try
		{
			connected = true;

			while (connected)
			{
				receive();
				connected = false;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void receive() throws IOException
	{
		String userInput;

		while ((userInput = in.readLine()) != null)
		{
			//System.out.println("=== Coordinator ==========:  Response from coordinator server to ECF client: " + userInput);
			distMgm.relayMessageToClient(senderID, userInput);
		}
	}

	public void send(String inputData)
	{
		//System.out.println("=== Coordinator ==========:  Sent to coordinator server from ECF client:" + inputData);
		out.println(inputData);
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
