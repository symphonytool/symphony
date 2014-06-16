package eu.compassresearch.ide.collaboration.distributedsimulation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Vector;

public class RelayServer extends Thread
{
	public final int port;
	private ServerSocket socket;
	private boolean listening;
	public final List<RelayServerClientHandler> connections = new Vector<RelayServerClientHandler>();
	private DistributedSimulationManager distributedSimulationManager;

	public RelayServer(DistributedSimulationManager distributedSimulationManager, int port) throws IOException
	{
		this.distributedSimulationManager = distributedSimulationManager;
		
		//socket setup
		this.port = port;
		socket = new ServerSocket(this.port);
		
		//thread setup 
		setDaemon(true);
		setName("Relay Server");
	}
	
	@Override
	public void run()
	{
		listening = true;

		try
		{
			while (listening)
			{
				Socket conn = socket.accept();
				RelayServerClientHandler incoming = new RelayServerClientHandler(distributedSimulationManager, conn);
				
				incoming.start();
				connections.add(incoming);
				
                System.out.println("Relay Server: Incoming: " + incoming);  
			}
		} catch (SocketException e)
		{
			System.out.println("Relay Server: Socket Exception: " + e.getMessage());
		} catch (SocketTimeoutException e)
		{
			System.out.println("Relay Server: timed out: " + e.getMessage());
		} catch (IOException e)
		{
			System.out.println("Relay Server: IO exception: " + e.getMessage());
		}
		
		die();
	}
	
	public synchronized void die()
	{
		try
		{
			listening = false;
			socket.close();
//
//			for (ConnectionThread ct : getConnections())
//			{
//				ct.die();
//			}
		} catch (IOException e)
		{
			System.out.println("Relay Server: Cannot stop connection listener");
		}

	}
	
	
}
