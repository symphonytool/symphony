package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.ConsoleSelectionStrategy;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStateChangedEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.debug.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;

/**
 * Implements a CmlDebugger that communicates through sockets
 * 
 * @author akm
 */
public class SocketServerCmlDebugger implements CmlDebugger,
		CmlInterpreterStateObserver
{

	/**
	 * The communication socket
	 */
	private Socket requestSocket;
	private OutputStream requestOS;
	private InputStream requestIS;
	private BufferedReader requestReader;
	private boolean connected = false;
	private CmlInterpreter runningInterpreter;
	private List<TransitionDTO> waitingChoices = new LinkedList<TransitionDTO>();
	CmlTransitionSet availableChannelEvents;

	/**
	 * Response Queue
	 */
	private SynchronousQueue<ResponseMessage> responseQueue = new SynchronousQueue<ResponseMessage>();
	/**
	 * Transition Choice Queue
	 */
	private SynchronousQueue<TransitionDTO> choiceQueue = new SynchronousQueue<TransitionDTO>();
	/**
	 * Dispatches incomming messages
	 */
	private CommandDispatcher commandDispatcher;

	/**
	 * Processes incomming messages
	 * 
	 * @author akm
	 */
	class CommandDispatcher implements Runnable
	{
		private boolean stopped = false;

		public void stop()
		{
			stopped = true;
			try
			{
				requestSocket.shutdownInput();
			} catch (IOException e)
			{
				CmlRuntime.logger().log(Level.WARNING, "", e);
			}
		}

		@Override
		public void run()
		{
			MessageContainer messageContainer = null;
			try
			{

				do
				{
					messageContainer = recvMessage();
					CmlRuntime.logger().finest("Debug event thread received a message: "
							+ messageContainer.toString());
				} while (!stopped && processMessage(messageContainer));

			} catch (IOException e)
			{
				stopped();
				CmlRuntime.logger().log(Level.WARNING, "", e);
			}
		}
	}

	/**
	 * Connects to the request tcp connection on "localhost" (for now) where the eclipse UI should listening.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException
	{
		if (!isConnected())
		{
			requestSocket = new Socket("localhost", CmlDebugDefaultValues.PORT);
			requestOS = requestSocket.getOutputStream();
			requestIS = requestSocket.getInputStream();
			requestReader = new BufferedReader(new InputStreamReader(requestIS));
			connected = true;
		}
	}

	private boolean isConnected()
	{
		return connected;
	}

	private void simulate(CmlInterpreter cmlInterpreter)
			throws AnalysisException
	{
		cmlInterpreter.execute(new RandomSelectionStrategy());
	}

	private void animate(final CmlInterpreter cmlInterpreter)
			throws AnalysisException
	{
		cmlInterpreter.setSuspendBeforeTermination(true);
		cmlInterpreter.execute(new SelectionStrategy()
		{

			private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();

			private boolean isSystemSelect(
					CmlTransitionSet availableChannelEvents)
			{
				return availableChannelEvents.getSilentTransitionsAsSet().size() > 0;
			}

			private CmlTransition systemSelect()
			{
				rndSelect.choices(new CmlTransitionSet((Set) availableChannelEvents.getSilentTransitionsAsSet()));
				return rndSelect.resolveChoice();
			}

			private CmlTransition userSelect() throws InterruptedException
			{
				// //Wait for a transition choice taken by the user
				TransitionDTO choice = choiceQueue.take();

				CmlTransition selectedEvent = null;
				// For now we just search naively to find the event
				for (CmlTransition transition : availableChannelEvents.getAllEvents())
				{
					if (System.identityHashCode(transition) == choice.getTransitionObjectId())
						selectedEvent = transition;
				}

				if (selectedEvent instanceof LabelledTransition
						&& !((LabelledTransition) selectedEvent).getChannelName().isPrecise())
				{
					ConsoleSelectionStrategy.readChannelNameValues((LabelledTransition) selectedEvent);
				}

				return selectedEvent;
			}

			@Override
			public void choices(CmlTransitionSet availableTransitions)
			{
				availableChannelEvents = availableTransitions;

				// We only convert the CmlTransitions into DTO's if no silent transitions
				// can occur, since we only send the transitions to Eclipse if so.
				if (!isSystemSelect(availableChannelEvents))
				{
					waitingChoices = convertTransitionsToChoices(availableChannelEvents, waitingChoices);
				}
			}

			@Override
			public CmlTransition resolveChoice()
			{

				// At this point we don't want the internal transition to propagate
				// to the user, so we randomly choose all the possible internal transitions
				// before we let anything through to the user
				if (isSystemSelect(availableChannelEvents))
					return systemSelect();
				else
				{

					try
					{
						return userSelect();
					} catch (InterruptedException e)
					{
						e.printStackTrace();
						return null;
					} finally
					{
						waitingChoices.clear();
					}
				}

			}
		});
	}

	private List<TransitionDTO> convertTransitionsToChoices(
			CmlTransitionSet availableTransitions, List<TransitionDTO> choices)
	{
		List<TransitionDTO> convertedTransitionObjs = new LinkedList<TransitionDTO>();

		for (CmlTransition transition : availableChannelEvents.getAllEvents())
		{
			// First find all the locations of the transition sources
			List<ILexLocation> locations = new LinkedList<ILexLocation>();
			for (CmlBehaviour source : transition.getEventSources())
			{
				INode node = source.getNextState().first;
				locations.add(LocationExtractor.extractLocation(node));
			}

			convertedTransitionObjs.add(new TransitionDTO(System.identityHashCode(transition), transition.toString(), locations));
		}

		return convertedTransitionObjs;
	}

	/**
	 * Message communication methods
	 */

	private void stopped()
	{
		stopped(null);
	}

	private void stopped(CmlInterpreterStateDTO status)
	{
		if (status != null && status.hasErrors())
		{
			for (InterpreterErrorDTO error : status.getErrors())
			{
				Console.err.println(error);
			}
		}
		sendStatusMessage(status);
		commandDispatcher.stop();
	}

	private void sendStatusMessage(CmlInterpreterStateDTO interpreterStatus)
	{
		CmlDbgStatusMessage dm = new CmlDbgStatusMessage(interpreterStatus);
		CmlRuntime.logger().finest("Sending status message : " + dm.toString());
		MessageCommunicator.sendMessage(requestOS, dm);
	}

	private ResponseMessage sendRequestSynchronous(RequestMessage message)
	{
		MessageCommunicator.sendMessage(requestOS, message);
		ResponseMessage responseMessage = null;
		try
		{
			// TODO check message id
			responseMessage = responseQueue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return responseMessage;
	}

	private void sendResponse(ResponseMessage message)
	{
		MessageCommunicator.sendMessage(requestOS, message);
	}

	/**
	 * Receives a CML message. This is a blocking call
	 * 
	 * @return The received message
	 * @throws IOException
	 */
	private MessageContainer recvMessage() throws IOException
	{
		return MessageCommunicator.receiveMessage(requestReader, new MessageContainer(new CmlDbgStatusMessage(CmlInterpreterState.TERMINATED_BY_USER)));
	}

	private void stopping()
	{
		// sendStatusMessage(CmlDbgpStatus.STOPPING);

		try
		{
			// This is to release the interpreter thread if stuck here
			responseQueue.offer(new ResponseMessage(), 1, TimeUnit.MILLISECONDS);
			choiceQueue.offer(new TransitionDTO(Integer.MAX_VALUE, "", new LinkedList<ILexLocation>()), 1, TimeUnit.MILLISECONDS);
			runningInterpreter.resume();
		} catch (InterruptedException e)
		{
		}
	}

	/*
	 * Message handlers
	 */

	/**
	 * Processes messages of type MessageType.COMMAND, this is commands from the other end.
	 * 
	 * @param message
	 *            the message containing a command
	 * @return
	 */
	private boolean processCommand(CmlDbgCommandMessage message)
	{
		switch (message.getCommand())
		{
			case STOP:
				stopping();
				return false;
			case SET_BREAKPOINT:
			{
				Breakpoint bp = message.getContent();
				Console.debug.println("Break point added : " + bp);
				runningInterpreter.addBreakpoint(bp);
				return true;
			}
			case REMOVE_BREAKPOINT:
			{
				Breakpoint bp = message.getContent();
				Console.debug.println("Break point removed : " + bp);
				runningInterpreter.removeBreakpoint(bp);
				return true;
			}
			case RESUME:
				runningInterpreter.resume();
				return true;
			case STEP:
				runningInterpreter.step();
				return true;
			case SET_CHOICE:
				TransitionDTO c = message.getContent();
				// notify if a choice is selected
				choiceQueue.offer(c);
				return true;
			default:
				return true;
		}
	}

	private boolean processRequest(RequestMessage message)
	{
		switch (message.getRequest())
		{
			case GET_STACK_FRAMES:
			{
				int id = message.getContent();
				CmlBehaviour foundBehavior = this.runningInterpreter.findBehaviorById(id);
				Context context = foundBehavior.getNextState().second;
				List<StackFrameDTO> stackframes = new LinkedList<StackFrameDTO>();

				List<Context> contextStack = new LinkedList<Context>();
				Context nextContext = context;
				int contextCount = 0;
				// First count the contexts since the getDepth method does not do what you would expect
				// we remove the global context
				for (; nextContext.outer != null; contextCount++)
				{
					contextStack.add(nextContext);
					nextContext = nextContext.outer;
				}
				int contextIndex = contextCount;
				for (Context c : contextStack)
				{
					if (contextIndex == contextCount)
						stackframes.add(new StackFrameDTO(LocationExtractor.extractLocation(foundBehavior.getNextState().first).getStartLine(), c.location.getFile().toURI(), contextIndex--));
					else
						stackframes.add(new StackFrameDTO(c.location.getStartLine(), c.location.getFile().toURI(), contextIndex--));
				}
				ResponseMessage responseMessage = new ResponseMessage(message.getRequestId(), CmlRequest.GET_STACK_FRAMES, stackframes);
				sendResponse(responseMessage);
				return true;
			}

			case GET_CONTEXT_PROPERTIES:
			{
				int[] args = message.getContent();

				int threadId = args[0];
				int level = args[1];

				CmlBehaviour foundBehavior = this.runningInterpreter.findBehaviorById(threadId);
				Context context = foundBehavior.getNextState().second;

				List<Context> contexts = new LinkedList<Context>();
				while (context.outer != null)
				{
					contexts.add(0, context);
					context = context.outer;
				}

				ResponseMessage responseMessage = new ResponseMessage(message.getRequestId(), CmlRequest.GET_CONTEXT_PROPERTIES, VariableDTO.extractVariables(contexts.get(level - 1)));
				sendResponse(responseMessage);

				return true;
			}

			default:
				return true;
		}
	}

	/**
	 * Handles the response messages sent
	 * 
	 * @param message
	 * @return
	 */
	private boolean processResponse(ResponseMessage message)
	{
		responseQueue.offer(message);
		return true;
	}

	private boolean processMessage(MessageContainer messageContainer)
	{
		switch (messageContainer.getType())
		{
			case STATUS:
				// We ignore status messages since they should only be sent in the other direction
				return true;
			case COMMAND:
				return processCommand((CmlDbgCommandMessage) messageContainer.getMessage());
			case REQUEST:
				return processRequest((RequestMessage) messageContainer.getMessage());
			case RESPONSE:
				return processResponse((ResponseMessage) messageContainer.getMessage());
			default:
		}

		return false;
	}

	/*
	 * CmlDebugger methods
	 */

	@Override
	public void initialize(CmlInterpreter cmlInterpreter)
			throws AnalysisException
	{

		runningInterpreter = cmlInterpreter;
		runningInterpreter.onStateChanged().registerObserver(this);
		// sendStatusMessage(this.runningInterpreter.getStatus());
		commandDispatcher = new CommandDispatcher();
		new Thread(commandDispatcher, "CMLInterpreterRunner event dipsatcher").start();
		runningInterpreter.initialize();
	}

	private void requestSetup()
	{
		sendRequestSynchronous(new RequestMessage(CmlRequest.SETUP));
	}

	@Override
	public void start(InterpreterExecutionMode mode)
	{

		try
		{
			// currentMode = mode;
			if (mode == InterpreterExecutionMode.ANIMATE)
			{
				requestSetup();
				animate(runningInterpreter);
			} else if (mode == InterpreterExecutionMode.SIMULATE)
			{
				requestSetup();
				simulate(runningInterpreter);
			}

			stopped(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter));
		} catch (CmlInterpreterException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			if (e.hasErrorNode())
				status.addError(new InterpreterErrorDTO(e.getMessage(), LocationExtractor.extractLocation(e.getErrorNode())));
			else
				status.addError(new InterpreterErrorDTO(e.getMessage()));
			stopped(status);
		} catch (ValueException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage(), e.ctxt.location));
			stopped(status);
		} catch (AnalysisException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage()));
			stopped(status);
		} 
		catch (ContextException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage(), e.location));
			stopped(status);
		}
		finally
		{
			runningInterpreter.onStateChanged().unregisterObserver(this);
		}
	}

	@Override
	public void onStateChanged(Object source, InterpreterStateChangedEvent event)
	{
		// Only send this if status is not FAILED, this will be handled in the start method
		// which appends the correct errors to the status
		if (event.getStatus() == CmlInterpreterState.WAITING_FOR_ENVIRONMENT)
		{
			if (!waitingChoices.isEmpty())
			{
				Console.debug.println("Debug thread sending Status event to controller: "
						+ event);
				sendStatusMessage(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter, waitingChoices));
			}
		} else if (event.getStatus() != CmlInterpreterState.FAILED)
		{
			Console.debug.println("Debug thread sending Status event to controller: "
					+ event);
			sendStatusMessage(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter, waitingChoices));
		}
		CmlRuntime.logger().fine(event.getStatus().toString());

	}

}
