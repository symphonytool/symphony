package eu.compassresearch.core.interpreter.cosim.communication;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.cosim.IProcessBehaviourDelegationManager;
import eu.compassresearch.core.interpreter.cosim.MessageManager;
import eu.compassresearch.core.interpreter.cosim.ProcessDelegate;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * A connection thread used to handle co-simulation communication from the coordinator to a single client
 * 
 * @author kel
 */
public class ConnectionThread extends Thread
{
	private final boolean principal;
	private final Socket socket;

	private String id = "";
	// private long xid = 0;

	private boolean connected;
	private static boolean trace = false;
	private static boolean quiet = false;

	/**
	 * The delegation manager that hold a registry of avaliable delegates for processes
	 */
	IProcessBehaviourDelegationManager delegationManager;

	/**
	 * utility class responsible for sending and receiving messages
	 */
	MessageManager comm;

	/**
	 * synchronization for message parsing
	 */
	private Map<String, SynchronousQueue<CmlTransitionSet>> availableTransitionsMap = new HashMap<String, SynchronousQueue<CmlTransitionSet>>();
	/**
	 * synchronization for message parsing for is finished
	 */
	private Map<String, SynchronousQueue<Boolean>> isFinishedMap = new HashMap<String, SynchronousQueue<Boolean>>();

	Semaphore executingSem = new Semaphore(0);
	private String registeredProcessName;
	
	public ConnectionThread(ThreadGroup group, Socket conn, boolean principal,
			IProcessBehaviourDelegationManager delegationManager)
			throws IOException
	{
		super(group, null, "DBGp Connection");

		this.socket = conn;
		this.socket.setSoTimeout(0);
		this.principal = principal;
		this.comm = new MessageManager(conn);
		this.delegationManager = delegationManager;
		setDaemon(true);
	}

	public String getIdeId()
	{
		return id;
	}

	public static synchronized boolean setTrace()
	{
		trace = !trace;
		return trace;
	}

	public static synchronized boolean setQuiet()
	{
		quiet = !quiet;
		return quiet;
	}

	@Override
	public void run()
	{
		connected = true;
		try
		{
			if (!principal)
			{
				// runme(); // Send run command to start new thread
			}

			while (connected)
			{
				receive(); // Blocking
			}
		} catch (SocketException e)
		{
			// Caused by die(), and CDMJ death
			if (connected)
			{
				System.err.println("Error in connection thread for: "+registeredProcessName);
				e.printStackTrace();
			}
		} catch (IOException e)
		{
			System.out.println("Connection exception: " + e.getMessage());

		} finally
		{
			die();
		}

	}

	public synchronized void die()
	{
		try
		{
			connected = false;
			socket.close();
		} catch (IOException e)
		{
			// ?
		}
	}

	private void receive() throws IOException
	{
		JsonMessage message = comm.receive();

		if (message instanceof RegisterSubSystemMessage)
		{
			for (String processName : ((RegisterSubSystemMessage) message).getProcesses())
			{
				this.registeredProcessName = processName;
				this.delegationManager.addDelegate(new ProcessDelegate(registeredProcessName, this));
				availableTransitionsMap.put(processName, new SynchronousQueue<CmlTransitionSet>());
			}
		} else if (message instanceof InspectReplyMessage)
		{
			InspectReplyMessage replyMsg = (InspectReplyMessage) message;
			availableTransitionsMap.get(replyMsg.getProcess()).add(replyMsg.getTransitions());
		} else if (message instanceof FinishedReplyMessage)
		{
			FinishedReplyMessage replyMsg = (FinishedReplyMessage) message;
			isFinishedMap.get(replyMsg.getProcess()).offer(replyMsg.isFinished());
		}else if (message instanceof ExecuteCompletedMessage)
		{
			executingSem.release();
		}
	}

	public CmlTransitionSet inspect(String processName)
			throws JsonGenerationException, JsonMappingException, IOException,
			InterruptedException
	{
		comm.send(new InspectMessage(processName));
		return availableTransitionsMap.get(processName).take();
	}

	public void execute(CmlTransition transition)
			throws JsonGenerationException, JsonMappingException, IOException
	{
		comm.send(new ExecuteMessage(transition));
		try
		{
			executingSem.acquire();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFinished(String processName)
			throws JsonGenerationException, JsonMappingException, IOException,
			InterruptedException
	{
		if (!isFinishedMap.containsKey(processName))
		{
			isFinishedMap.put(processName, new SynchronousQueue<Boolean>());
		}
		comm.send(new FinishedRequestMessage(processName));
		return isFinishedMap.get(processName).take();
	}

	public void disconnect() throws JsonGenerationException,
			JsonMappingException, IOException
	{
		comm.send(new DisconnectMessage());
		connected = false;
	}

}
