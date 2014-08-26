package eu.compassresearch.core.interpreter.cosim.communication;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.cosim.IProcessBehaviourDelegationManager;
import eu.compassresearch.core.interpreter.cosim.MessageManager;
import eu.compassresearch.core.interpreter.cosim.ProcessDelegate;
import eu.compassresearch.core.interpreter.cosim.communication.protocol.CoSimProtocolFactory;
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
	private AbortMessage abortedMsg;
	
	/**
	 * Protocol factory
	 */
	private CoSimProtocolFactory protocolFactory = new CoSimProtocolFactory();

	public ConnectionThread(ThreadGroup group, Socket conn, boolean principal,
			IProcessBehaviourDelegationManager delegationManager)
			throws IOException
	{
		super(group, null, "DBGp Connection");

		this.socket = conn;
		this.socket.setSoTimeout(0);
		this.principal = principal;
		this.comm = new MessageManager(conn,protocolFactory.getInstance(protocolFactory.DEFAULT_VERSION));
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
				System.err.println("Error in connection thread for: "
						+ registeredProcessName);
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
			final RegisterSubSystemMessage regMsg = (RegisterSubSystemMessage) message;
			
			this.comm.setProtocol(protocolFactory.getInstance(regMsg.version));
			
			for (String processName : regMsg.getProcesses())
			{
				this.registeredProcessName = processName;
				this.delegationManager.addDelegate(new ProcessDelegate(registeredProcessName, this));
				availableTransitionsMap.put(processName, new SynchronousQueue<CmlTransitionSet>());
			}
		} else if (message instanceof InspectReplyMessage)
		{
			InspectReplyMessage replyMsg = (InspectReplyMessage) message;
			final SynchronousQueue<CmlTransitionSet> synchronousQueue = availableTransitionsMap.get(replyMsg.getProcess());

			try
			{
				System.out.println("got transisions offering now ("+replyMsg.getProcess()+"): "+replyMsg.getTransitions());
				synchronousQueue.put(replyMsg.getTransitions());
			} catch (IllegalStateException e)
			{
				// this is queue full, it may happen due to phantom Tau's used to signal, try me again later, i'm
				// working on it and may be finished as next inspect
				System.err.println("Queue full: "+synchronousQueue);
				synchronousQueue.poll();
				System.err.println("Trying again: "+replyMsg.getTransitions());
				synchronousQueue.add(replyMsg.getTransitions());
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (message instanceof FinishedReplyMessage)
		{
			FinishedReplyMessage replyMsg = (FinishedReplyMessage) message;
			try
			{
				isFinishedMap.get(replyMsg.getProcess()).put(replyMsg.isFinished());
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (message instanceof ExecuteCompletedMessage)
		{
			executingSem.release();
		} else if (message instanceof AbortMessage)
		{
			this.delegationManager.abortedBy(this, registeredProcessName, ((AbortMessage) message).getErrorCode(), ((AbortMessage) message).getMessage());
			this.abortedMsg = (AbortMessage) message;
		}
	}

	public CmlTransitionSet inspect(String processName) throws Exception,
			InterruptedException
	{
		checkAbortState();
		comm.send(new InspectMessage(processName));
		System.out.println("Waiting for taking ("+processName+")");
		CmlTransitionSet tmp = availableTransitionsMap.get(processName).take();
		System.out.println("took: ("+processName+")"+tmp);
		return tmp;
	}

	public void execute(CmlTransition transition) throws Exception, IOException
	{
		checkAbortState();

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

	private void checkAbortState()
	{
		if (abortedMsg != null)
		{
			throw new InterpreterRuntimeException("The external co-simulation process "
					+ registeredProcessName
					+ " aborted with error: "
					+ abortedMsg.getErrorCode() + " " + abortedMsg.getMessage());
		}
	}

	public boolean isFinished(String processName) throws Exception,
			IOException, InterruptedException
	{
		checkAbortState();

		if (!isFinishedMap.containsKey(processName))
		{
			isFinishedMap.put(processName, new SynchronousQueue<Boolean>());
		}
		comm.send(new FinishedRequestMessage(processName));
		return isFinishedMap.get(processName).take();
	}

	public void disconnect() throws Exception, IOException
	{
		comm.send(new DisconnectMessage());
		connected = false;
	}

}
