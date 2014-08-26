package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RelayServerClientHandler extends Thread implements RelayServerListener
{
	private Socket clientSocket;
	private DistributedSimulationManager distSimManager;
	private PrintWriter out;

	public RelayServerClientHandler(DistributedSimulationManager distSimManager, Socket conn)
	{
		this.distSimManager = distSimManager;
		clientSocket = conn;
		
		distSimManager.addRelayListener(this);
		
		setDaemon(true);
		setName("Relay Server : Client Handler " + conn.toString());
	}

	@Override
	public void run()
	{
		if (clientSocket.isConnected())
		{
			BufferedReader in;
			try
			{
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String inputData;

				while ((inputData = in.readLine()) != null)
				{
					System.out.println("=== Client ==========: Send from client to coordinator: " + inputData); //TODO remove
					distSimManager.relayMessageToCoordinator(inputData);
				}
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onReceivedData(String data)
	{
		System.out.println("=== Client ==========: Send from coordinator to client: " + data);
		out.println(data);
	}
}
