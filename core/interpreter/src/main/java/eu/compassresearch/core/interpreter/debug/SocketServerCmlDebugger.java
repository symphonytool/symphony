package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.runtime.ValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.DebugAnimationStrategy;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStateChangedEvent;
import eu.compassresearch.core.interpreter.debug.messaging.AbstractMessage;
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

	final static Logger logger = LoggerFactory.getLogger("cml-interpreter");

	/**
	 * The communication socket
	 */
	private Socket requestSocket;
	private OutputStream requestOS;
	private InputStream requestIS;
	private BufferedReader requestReader;
	private boolean connected = false;
	private CmlInterpreter runningInterpreter;

	Thread worker = null;
	public final List<TransitionDTO> waitingChoices = new LinkedList<TransitionDTO>();

	/**
	 * Response Queue
	 */
	private SynchronousQueue<ResponseMessage> responseQueue = new SynchronousQueue<ResponseMessage>();
	/**
	 * Transition Choice Queue
	 */
	public final SynchronousQueue<TransitionDTO> choiceQueue = new SynchronousQueue<TransitionDTO>();
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
				logger.warn("", e);
			}
		}

		@Override
		public void run()
		{
			MessageContainer messageContainer = null;
			final MessageContainer DEFAULT_MESSAGE = new MessageContainer(new CmlDbgStatusMessage(CmlInterpreterState.TERMINATED_BY_USER));
			try
			{

				do
				{
					// Receives a CML message. This is a blocking call
					messageContainer = MessageCommunicator.receiveMessage(requestReader, DEFAULT_MESSAGE);

					if (messageContainer == DEFAULT_MESSAGE)
					{
						logger.debug("IDE connection disconnected, reached EOF");
						break;
					}
					logger.trace("Debug event thread received a message: "
							+ messageContainer.toString());
				} while (!stopped && processMessage(messageContainer));

			} catch (IOException e)
			{
				// stopped();
				if (!stopped)
				{
					logger.warn("", e);
				}
			}
		}
	}

	/**
	 * Connects to the request tcp connection on "localhost" (for now) where the eclipse UI should listening.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect(String host, int port) throws UnknownHostException,
			IOException
	{
		if (!isConnected())
		{
			InetAddress server = InetAddress.getByName(host);
			requestSocket = new Socket(server, port);
			requestOS = requestSocket.getOutputStream();
			requestIS = requestSocket.getInputStream();
			requestReader = new BufferedReader(new InputStreamReader(requestIS));
			connected = true;
		}
	}

	public void dicsonnect()
	{
		commandDispatcher.stop();
		worker.interrupt();
		try
		{
			requestSocket.close();
		} catch (IOException e)
		{
			// ignore
		}
	}

	private boolean isConnected()
	{
		return connected;
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
	}

	private void sendStatusMessage(CmlInterpreterStateDTO interpreterStatus)
			throws IOException
	{
		CmlDbgStatusMessage dm = new CmlDbgStatusMessage(interpreterStatus);
		logger.trace("Sending status message : " + dm.toString());
		sendMessage(requestOS, dm);
	}

	static void sendMessage(OutputStream requestOS, AbstractMessage dm)
			throws IOException
	{
		MessageCommunicator.sendMessage(requestOS, dm);
	}

	private ResponseMessage sendRequestSynchronous(RequestMessage message)
			throws IOException
	{
		sendMessage(requestOS, message);
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
				DebugContext debugContext = this.runningInterpreter.getDebugContext(id);
				if (debugContext != null)
				{
					List<StackFrameDTO> stackframes = new LinkedList<StackFrameDTO>();

					List<Context> contextStack = new LinkedList<Context>();
					Context nextContext = debugContext.ctxt;
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
						{
							stackframes.add(new StackFrameDTO(debugContext.location.getStartLine(), c.location.getFile().toURI(), contextIndex--));
						} else
						{
							stackframes.add(new StackFrameDTO(c.location.getStartLine(), c.location.getFile().toURI(), contextIndex--));
						}
					}
					ResponseMessage responseMessage = new ResponseMessage(message.getRequestId(), CmlRequest.GET_STACK_FRAMES, stackframes);
					try
					{
						sendMessage(requestOS, responseMessage);
					} catch (IOException e)
					{
						throw new InterpreterRuntimeException("Unable to send message", e);
					}

					return true;
				}

				throw new InterpreterRuntimeException("Unable to get debug context");
			}

			case GET_CONTEXT_PROPERTIES:
			{
				int[] args = message.getContent();

				int threadId = args[0];
				int level = args[1];

				DebugContext debugContext = this.runningInterpreter.getDebugContext(threadId);
				if (debugContext != null)
				{
					Context context = debugContext.ctxt;

					List<Context> contexts = new LinkedList<Context>();
					while (context.outer != null)
					{
						contexts.add(0, context);
						context = context.outer;
					}

					ResponseMessage responseMessage = new ResponseMessage(message.getRequestId(), CmlRequest.GET_CONTEXT_PROPERTIES, VariableDTO.extractVariables(contexts.get(level - 1)));
					try
					{
						sendMessage(requestOS, responseMessage);
					} catch (IOException e)
					{
						throw new InterpreterRuntimeException("Unable to send message", e);
					}

					return true;
				}
				throw new InterpreterRuntimeException("Unable to get debug context");
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
		worker = new Thread(commandDispatcher, "CMLInterpreterRunner event dipsatcher");
		worker.setDaemon(true);
		worker.start();
		runningInterpreter.initialize();
	}

	private void requestSetup()
	{
		try
		{
			sendRequestSynchronous(new RequestMessage(CmlRequest.SETUP));
		} catch (IOException e)
		{
			throw new InterpreterRuntimeException("Unable to send request sync setup message", e);
		}
	}

	@Override
	public void start(SelectionStrategy strategy)
	{

		try
		{
			requestSetup();

			if (strategy instanceof DebugAnimationStrategy)
			{
				((DebugAnimationStrategy) strategy).initialize(runningInterpreter, this);
			}
			runningInterpreter.execute(strategy);

			stopped(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter));
		} catch (CmlInterpreterException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			if (e.hasErrorNode())
			{
				status.addError(new InterpreterErrorDTO(e.getMessage(), LocationExtractor.extractLocation(e.getErrorNode())));
			} else
			{
				status.addError(new InterpreterErrorDTO(e.getMessage()));
			}
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
		} catch (ContextException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage(), e.location));
			stopped(status);
		} finally
		{
			runningInterpreter.onStateChanged().unregisterObserver(this);
		}
	}

	@Override
	public void onStateChanged(Object source, InterpreterStateChangedEvent event)
	{
		try
		{
			// Only send this if status is not FAILED, this will be handled in the start method
			// which appends the correct errors to the status
			CmlInterpreterState status = event.getStatus();
			if (status == CmlInterpreterState.WAITING_FOR_ENVIRONMENT)
			{
				if (!waitingChoices.isEmpty())
				{
					Console.debug.println("Debug thread sending Status event to controller: "
							+ event);

					sendStatusMessage(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter, waitingChoices));

				}
			} else if (status != CmlInterpreterState.FAILED)
			{
				Console.debug.println("Debug thread sending Status event to controller: "
						+ event);
				sendStatusMessage(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter, waitingChoices));
			}
			logger.trace(status.toString());
		} catch (IOException e)
		{
			throw new InterpreterRuntimeException("Unable to send message", e);
		}
	}

}
