package eu.compassresearch.core.interpreter.cosim;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import eu.compassresearch.core.interpreter.cosim.communication.ConnectionListener;
import eu.compassresearch.core.interpreter.cosim.communication.Utils;

/**
 * Co-simulation server which is the simulation coordinator. This must have the top system level process
 * 
 * @author kel
 */
public class CoSimulationServer implements IProcessBehaviourDelegationManager
{
	public final int port;
	private ConnectionListener listener;

	private final Map<String, IProcessDelegate> processDelegation = new HashMap<String, IProcessDelegate>();
	private final Map<String, IProcessDelegate> freeProcessDelegation = new HashMap<String, IProcessDelegate>();
	private final List<String> delegatedProcesses;

	public CoSimulationServer(List<String> delegatedProcesses, int port)
	{
		this.delegatedProcesses = delegatedProcesses;
		this.port = port;
	}

	public void listen() throws IOException
	{
		listener = new ConnectionListener(port, 30000, this);
		listener.start();
		System.out.println("Listening on port: " + port);
	}

	public void waitForClients()
	{
		System.out.println("Waiting for clients...");
		while (!processDelegation.keySet().containsAll(delegatedProcesses))
		{
			Utils.milliPause(1000);
		}
		System.out.println("All clients now connected for: "
				+ delegatedProcesses + ". Resuming simulation.");
	}

	public void close()
	{
		for (IProcessDelegate delegate : processDelegation.values())
		{
			try
			{
				delegate.disconnect();
			} catch (Exception e)
			{

			}
		}

		listener.die();
	}

	@Override
	public void addDelegate(IProcessDelegate delegate)
	{
		this.processDelegation.put(delegate.getProcessName(), delegate);
		this.freeProcessDelegation.put(delegate.getProcessName(), delegate);
	}

	@Override
	public boolean hasDelegate(String name)
	{
		return this.freeProcessDelegation.containsKey(name);
	}

	@Override
	public IProcessDelegate getDelegate(String name)
	{
		return this.freeProcessDelegation.get(name);
	}

	@Override
	public boolean registerUse(IProcessDelegate delegate)
	{
		if (freeProcessDelegation.values().contains(delegate))
		{
			List<String> keys = new Vector<String>();
			for (Entry<String, IProcessDelegate> entry : freeProcessDelegation.entrySet())
			{
				if (entry.getValue() == delegate)
				{
					keys.add(entry.getKey());
				}
			}

			for (String key : keys)
			{
				freeProcessDelegation.remove(key);
			}
			return true;
		}

		return false;
	}

}
