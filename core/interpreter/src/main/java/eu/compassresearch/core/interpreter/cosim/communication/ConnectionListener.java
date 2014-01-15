package eu.compassresearch.core.interpreter.cosim.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Vector;

import eu.compassresearch.core.interpreter.cosim.IProcessBehaviourDelegationManager;

/**
 * Connection listener used by a co-simulation coordinator to accept clients. For each connected client a new
 * {@link ConnectionThread} is created.
 * 
 * @author kel
 */
public class ConnectionListener extends Thread
{
	private ServerSocket socket;
	private ThreadGroup group;
	private boolean listening;
	private ConnectionThread principal = null;

	public final List<ConnectionThread> connections = new Vector<ConnectionThread>();
	IProcessBehaviourDelegationManager delegationManager;

	public ConnectionListener(int port, int timeout,
			IProcessBehaviourDelegationManager delegationManager)
			throws IOException
	{
		socket = new ServerSocket(port);
		socket.setSoTimeout(timeout);

		group = new ThreadGroup("Connections");
		this.delegationManager = delegationManager;
		setDaemon(true);
		setName("Connection Listener");
	}

	public int getPort()
	{
		return socket.getLocalPort();
	}

	public synchronized ConnectionThread getPrincipal()
	{
		return principal;
	}

	@Override
	public void run()
	{
		listening = true;

		try
		{
			while (listening)
			{
				try
				{
					Socket conn = socket.accept();

					if (group.activeCount() >= 1)
					{
						System.out.println("Too many DBGp connections");
						conn.close();
						continue;
					}

					ConnectionThread worker = new ConnectionThread(group, conn, principal == null, this.delegationManager);

					if (principal == null)
					{
						principal = worker; // The main connection
					}

					worker.start();
					connections.add(worker);
				} catch (SocketTimeoutException e)
				{
					// System.out.println("Listener timed out: " + e.getMessage());
				}
			}
		} catch (SocketException e)
		{
			// Killed by die() or VDMJ crash
		} catch (SocketTimeoutException e)
		{
			System.out.println("Listener timed out: " + e.getMessage());
		} catch (IOException e)
		{
			System.out.println("Listener exception: " + e.getMessage());
		}

		die();
	}

	public synchronized void die()
	{
		try
		{
			listening = false;
			socket.close();

			for (ConnectionThread ct : getConnections())
			{
				ct.die();
			}
		} catch (IOException e)
		{
			System.out.println("Cannot stop connection listener");
		}

	}

	public ConnectionThread findConnection(String id)
	{
		if (id == null)
		{
			return principal;
		}

		for (ConnectionThread ct : getConnections())
		{
			if (ct.getIdeId() != null && ct.getIdeId().equals(id))
			{
				return ct;
			}
		}

		return null;
	}

	public ConnectionThread[] getConnections()
	{
		ConnectionThread[] all = null;

		do
		{
			all = new ConnectionThread[group.activeCount()];
		} while (group.enumerate(all) != all.length);

		return all;
	}
}
