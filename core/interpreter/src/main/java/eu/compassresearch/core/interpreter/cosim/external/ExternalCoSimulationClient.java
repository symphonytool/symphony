package eu.compassresearch.core.interpreter.cosim.external;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.CoSimCmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.cosim.DelegatedCmlBehaviour;
import eu.compassresearch.core.interpreter.cosim.IProcessDelegate;
import eu.compassresearch.core.interpreter.cosim.MessageManager;
import eu.compassresearch.core.interpreter.cosim.communication.DisconnectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ExecuteMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.FinishedRequestMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectMessage;
import eu.compassresearch.core.interpreter.cosim.communication.InspectionReplyMessage;
import eu.compassresearch.core.interpreter.cosim.communication.ProvidesImplementationMessage;
import eu.compassresearch.core.interpreter.cosim.communication.Utils;
import eu.compassresearch.core.interpreter.debug.messaging.Message;

/**
 * Co-simulation client controller
 * 
 * @author kel
 */
public class ExternalCoSimulationClient extends Thread
{
	private Socket socket;

	MessageManager comm;

	private int port;

	private final boolean principal;

	private boolean connected;

	/**
	 * synchronization for message parsing
	 */
	private SynchronousQueue<CmlTransition> availableTransitions = new SynchronousQueue<CmlTransition>();

	private Boolean executionPending = false;

	CmlTransitionSet transitions = null;

	private IProcessDelegate subsystem = null;

	private String host;

	public ExternalCoSimulationClient(String host, int port)
	{
		this.host = host;
		this.port = port;
		this.principal = false;
	}

	public void connect() throws IOException
	{
		InetAddress server = InetAddress.getByName(host);
		socket = new Socket(server, port);
		socket.setSoTimeout(0);
		comm = new MessageManager(socket);
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
			e.printStackTrace();
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
			comm.close();
			socket.close();
		} catch (IOException e)
		{
			// ?
		}
	}

	private void receive() throws SocketException, IOException
	{
		Message message = comm.receive();

		if (message instanceof InspectMessage)
		{
			InspectMessage inspectMessage = (InspectMessage) message;

			try
			{
				while (executionPending)
				{
					Utils.milliPause(10);
				}
				transitions = subsystem.inspect();
			} catch (Exception e)
			{
				throw new InterpreterRuntimeException("Interpreter inspection failed in co-simulation client", e);
			}

			for (ObservableTransition t : transitions.getObservableChannelEvents())
			{
				System.out.println("Offering event: " + t.getTransitionId());
			}
			comm.send(new InspectionReplyMessage(inspectMessage.getProcess(), transitions));
		} else if (message instanceof ExecuteMessage)
		{
			final ExecuteMessage executeMessage = (ExecuteMessage) message;
			synchronized (executionPending)
			{
				executionPending = true;
				try
				{
					availableTransitions.put(remapTransitionIds((ObservableTransition) executeMessage.getTransition()));
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else if (message instanceof FinishedRequestMessage)
		{
			FinishedRequestMessage finishedRequest = (FinishedRequestMessage) message;
			try
			{
				comm.send(new FinishedReplyMessage(finishedRequest.getProcess(), subsystem.isFinished()));
			} catch (Exception e)
			{
				// ignore
			}
		} else if (message instanceof DisconnectMessage)
		{
			this.connected = false;
			return;
		}

	}

	public void executionCompleted()
	{
		synchronized (executionPending)
		{
			executionPending = false;
		}
	}

	private static CmlTransition remapTransitionIds(CmlTransition transition)
	{
		try
		{
			DelegatedCmlBehaviour.setTransitionId(transition, transition.getRawTransitionId());
		} catch (IllegalAccessException e)
		{
			throw new InterpreterRuntimeException(e);
		}

		return transition;
	}

	public void registerImplementation(String... processes)
	{
		try
		{
			comm.send(new ProvidesImplementationMessage(Arrays.asList(processes)));
		} catch (IOException e)
		{
			throw new InterpreterRuntimeException("The co-simulation client failed to send the provides implementation message", e);
		}
	}

	public void disconnect()
	{
		die();
	}

	public CmlTransition getExecutableTransition() throws InterruptedException
	{
		CmlTransition tmp = this.availableTransitions.take();
		if (transitions != null)
		{

			List<CmlBehaviour> list = new Vector<CmlBehaviour>();
			for (CmlTransition t : transitions.getAllEvents())
			{
				list.addAll(t.getEventSources());
			}

			SortedSet<CmlBehaviour> eventSources = new TreeSet<CmlBehaviour>();

			for (CmlBehaviour source : list)
			{
				if (tmp.getHashedEventSources().contains(source.hashCode()))
				{
					eventSources.add(source);
				}
			}

			try
			{
				DelegatedCmlBehaviour.setEventSources(tmp, eventSources);
			} catch (IllegalAccessException e)
			{
				throw new InterpreterRuntimeException(e);
			}

			return tmp;
		}
		return null;
	}

	public void setSubSystem(IProcessDelegate subsystem)
	{
		this.subsystem = subsystem;
	}
}
