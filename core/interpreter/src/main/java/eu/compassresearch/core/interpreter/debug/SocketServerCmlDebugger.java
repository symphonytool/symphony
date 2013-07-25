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
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.types.ASourceType;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterError;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.Pair;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.utility.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;

/**
 * Implements a CmlDebugger that communicates through sockets
 * @author akm
 *
 */
public class SocketServerCmlDebugger implements CmlDebugger , CmlInterpreterStatusObserver {

	/**
	 * The communication socket 
	 */
	private Socket requestSocket;
	private OutputStream requestOS;
	private InputStream requestIS;
	private BufferedReader requestReader;
	private boolean connected = false;
	
	/**
	 * Response Queue
	 */
	private SynchronousQueue<ResponseMessage> responseQueue = new SynchronousQueue<ResponseMessage>();
	/**
	 * Dispatches incomming messages
	 */
	private CommandDispatcher commandDispatcher;
	
	/**
	 * Processes incomming messages
	 * @author akm
	 *
	 */
	class CommandDispatcher implements Runnable
	{
		private boolean stopped = false;

		public void stop()
		{
			stopped = true;
			try {
				requestSocket.shutdownInput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			MessageContainer messageContainer = null;
			try{

				do
				{
					messageContainer = recvMessage();
					CmlRuntime.logger().finest("Receiving message: " + messageContainer.toString());
				}
				while (!stopped && processMessage(messageContainer));
			
			}catch(IOException e)
			{
				stopped();
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Connects to the request tcp connection on "localhost" (for now) 
	 * where the eclipse UI should listening.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void connect() throws UnknownHostException, IOException
	{
		if(!isConnected())
		{
			requestSocket = new Socket("localhost",CmlDebugDefaultValues.PORT);
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
	
	private void simulate(CmlInterpreter cmlInterpreter) throws Exception
	{
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());

		cmlInterpreter.execute(sve);
	}
	
	private void animate(final CmlInterpreter cmlInterpreter) throws Exception
	{

		//Create the supervisor environment with the a selction strategy that has a connection to
		//the eclipse debugger
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new SelectionStrategy() {
					
					private Scanner scanIn = new Scanner(System.in);
					private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
					
					private boolean isSystemSelect(CmlAlphabet availableChannelEvents)
					{
						return availableChannelEvents.getSilentTransitions().size() > 0;
					}
					
					private CmlTransition systemSelect(CmlAlphabet availableChannelEvents)
					{
						return rndSelect.select(new CmlAlphabet((Set)availableChannelEvents.getSilentTransitions()));
					}
					
					private ILexLocation correctLexLocation(ILexLocation location,INode node)
					{
						if(location.getFile().getPath().isEmpty())
						{
							AFileSource sourceFile = node.getAncestor(AFileSource.class);
							if(sourceFile!=null)
							{
								return new LexLocation(sourceFile.getFile(), location.getModule(), location.getStartLine(), location.getStartPos(), location.getEndLine(), location.getEndPos(), location.getStartOffset(),location.getEndOffset());
							}
							
						}
						return location;
					}
					private CmlTransition userSelect(CmlAlphabet availableChannelEvents)
					{
						sendStatusMessage(CmlDbgpStatus.CHOICE, cmlInterpreter.getStatus());

						//convert to list of strings for now
						 List<Choice> transitions = new LinkedList<Choice>();
						for(CmlTransition transition : availableChannelEvents.getAllEvents())
						{
							//First find all the locations of the transition sources
							List<ILexLocation> locations = new LinkedList<ILexLocation>();
							for(CmlBehaviour source : transition.getEventSources())
							{
								ILexLocation loc = null;
								INode node = source.getNextState().first;
								if(node instanceof PAction)
								{
									loc = ((PAction)node).getLocation();
								}
								else if(node instanceof PProcess)
								{
									loc=((PProcess)source.getNextState().first).getLocation();
								}
								locations.add(correctLexLocation(loc, node));
							}
							 	
							transitions.add(new Choice(System.identityHashCode(transition),transition.toString(),locations));
						}

						ResponseMessage response = sendRequestSynchronous(new RequestMessage(CmlRequest.CHOICE,transitions));

						if(response.isRequestInterrupted())
							throw new InterpreterRuntimeException("The simulation was interrupted");

						//TODO At the moment if there are two identical events from different processes on the same channel
						//	then the user cannot distuingiues between the two and for now it will only be the first event in the list
						Choice choice = response.getContent();
						//System.out.println("response: " + responseStr);

						CmlTransition selectedEvent = null;
						//For now we just search naively to find the event
						for(CmlTransition transition : availableChannelEvents.getAllEvents())
						{
							//System.out.println("found: " + comEvent.getChannel().getName());
							if(System.identityHashCode(transition) == choice.getTransitionObjectId())
								selectedEvent = transition;
						}

						if(selectedEvent instanceof ChannelEvent && !((ChannelEvent)selectedEvent).isPrecise())
						{
							System.out.println("Enter value : "); 

							Value val = new IntegerValue(scanIn.nextInt());
							((ChannelEvent)selectedEvent).setValue(val);
						}

						return selectedEvent;
					}
					
					@Override
					public CmlTransition select(CmlAlphabet availableChannelEvents) {

						//At this point we don't want the internal transition to propagate 
						//to the user, so we randomly choose all the possible internal transitions
						//before we let anything through to the user
						if(isSystemSelect(availableChannelEvents))
							return systemSelect(availableChannelEvents);
						else
							return userSelect(availableChannelEvents);
					}
				});

		cmlInterpreter.execute(sve);
	}
	
	
	
	/**
	 * Message communication methods
	 */
	
	/**
	 * Sends the initialization message to the eclipse debug target
	 */
	private void init()
	{
		sendStatusMessage(CmlDbgpStatus.STARTING);
		commandDispatcher = new CommandDispatcher();
		new Thread(commandDispatcher, "CMLInterpreterRunner event dipsatcher").start();
	}
	
	private void stopped()
	{
		stopped(null);
	}
	
	private void stopped(InterpreterStatus status)
	{
		sendStatusMessage(CmlDbgpStatus.STOPPED,status);
		commandDispatcher.stop();
	}
	/**
	 * This message sends a status message to the eclipse debug target UI
	 * @param status The status to send
	 */
	private void sendStatusMessage(CmlDbgpStatus status)
	{
		sendStatusMessage(status, null);
	}
	
	private void sendStatusMessage(CmlDbgpStatus status, InterpreterStatus interpreterStatus)
	{
		CmlDbgStatusMessage dm = new CmlDbgStatusMessage(status,interpreterStatus);
		CmlRuntime.logger().finest("Sending status message : " + dm.toString());
		MessageCommunicator.sendMessage(requestOS, dm);
	}
	
	private ResponseMessage sendRequestSynchronous(RequestMessage message)
	{
		MessageCommunicator.sendMessage(requestOS, message);
		ResponseMessage responseMessage = null;
		try {
			responseMessage = responseQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return responseMessage;
	}

	/**
	 * Receives a CML message. This is a blocking call
	 * @return The received message
	 * @throws IOException
	 */
	private MessageContainer recvMessage() throws IOException
	{
		return MessageCommunicator.receiveMessage(requestReader,
				new MessageContainer(new CmlDbgStatusMessage(CmlDbgpStatus.CONNECTION_CLOSED))); 
	}
	
	
	private void stopping()
	{
		sendStatusMessage(CmlDbgpStatus.STOPPING);
		responseQueue.add(new ResponseMessage());
	}

	/*
	 * Message handlers
	 */
	
	/**
	 * Processes messages of type MessageType.STATUS, this is status messages from the other end.
	 * @param message The status message
	 * @return false if the connection has been closed by the other end else false
	 */
	private boolean processStatusMessage(CmlDbgStatusMessage message)
	{
		switch(message.getStatus())
		{
		case CONNECTION_CLOSED:
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * Processes messages of type MessageType.COMMAND, this is commands from the other end.
	 * @param message the message containing a command
	 * @return
	 */
	private boolean processCommand(CmlDbgCommandMessage message)
	{
		switch(message.getCommand())
		{
		case STOP:
			stopping();
			
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * Handles the response messages sent
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
		switch(messageContainer.getType())
		{
		case STATUS:
			return processStatusMessage((CmlDbgStatusMessage)messageContainer.getMessage());
		case COMMAND:
			return processCommand((CmlDbgCommandMessage)messageContainer.getMessage());
		case RESPONSE:
			return processResponse((ResponseMessage)messageContainer.getMessage());
		default:
		}
		
		return false;
	}
	
	
	/*
	 * CmlDebugger methods
	 */
	
	@Override
	public void initialize() throws Exception{
		connect();
		init();
	}

	@Override
	public void start(DebugMode mode, CmlInterpreter cmlInterpreter) {
		
		try{
			cmlInterpreter.onStatusChanged().registerObserver(this);
			if(mode == DebugMode.ANIMATE)
				animate(cmlInterpreter);
			else if (mode == DebugMode.SIMULATE)
				simulate(cmlInterpreter);
			
			stopped(cmlInterpreter.getStatus());
		}
		catch(Exception e)
		{
			InterpreterStatus status = cmlInterpreter.getStatus();
			status.AddError(new InterpreterError(e.getMessage()));
			stopped(status);
		}
		finally{
			cmlInterpreter.onStatusChanged().unregisterObserver(this);
		}
		
	}

	@Override
	public void onStatusChanged(Object source, InterpreterStatusEvent event) {
		// TODO Auto-generated method stub
		
	}

}
