package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RelayServerClientHandler extends Thread
{
	private Socket clientSocket;
	private DistributedSimulationManager distSimManager;

	public RelayServerClientHandler(DistributedSimulationManager distSimManager, Socket conn)
	{
		this.distSimManager = distSimManager;
		clientSocket = conn;
		
		setDaemon(true);
		setName("Relay Server : Client Handler " + conn.toString());
	}

	@Override
	public void run()
	{
		if (clientSocket.isConnected())
		{

			BufferedReader stdIn;
			try
			{
				stdIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				System.out.println("Response from client:");
				String inputData;

				while ((inputData = stdIn.readLine()) != null)
				{
					System.out.println(inputData); //TODO remove
					distSimManager.relayMessageToCoordinator(inputData);
				}
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
