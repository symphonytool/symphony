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
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.SeqValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.ValueParser;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.debug.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.debug.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.ResponseMessage;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.parser.CmlParser.instanceVariableDefinition_return;

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
	private CmlInterpreter runningInterpreter;
//	private InterpreterExecutionMode currentMode = null;

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
					CmlRuntime.logger().finest("Debug event thread received a message: " + messageContainer.toString());
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
	public void connect() throws UnknownHostException, IOException
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

	private void simulate(CmlInterpreter cmlInterpreter) throws AnalysisException
	{
//		CmlSupervisorEnvironment sve = 
//				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());

		cmlInterpreter.execute(new RandomSelectionStrategy());
	}

	private void animate(final CmlInterpreter cmlInterpreter) throws AnalysisException
	{
		//Create the supervisor environment with the a selection strategy that has a connection to
		//the eclipse debugger
//		CmlSupervisorEnvironment sve = 
//				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new SelectionStrategy() {
//
//					/*private Scanner scanIn = new Scanner(System.in);*/
//					private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();
//
//					private boolean isSystemSelect(CmlTransitionSet availableChannelEvents)
//					{
//						return availableChannelEvents.getSilentTransitions().size() > 0;
//					}
//
//					private CmlTransition systemSelect(CmlTransitionSet availableChannelEvents)
//					{
//						return rndSelect.select(new CmlTransitionSet((Set)availableChannelEvents.getSilentTransitions()));
//					}
//
//					private CmlTransition userSelect(CmlTransitionSet availableChannelEvents)
//					{
//						//sendStatusMessage(CmlDbgpStatus.CHOICE, cmlInterpreter.getStatus());
//
//						List<Choice> transitions = new LinkedList<Choice>();
//						for(CmlTransition transition : availableChannelEvents.getAllEvents())
//						{
//							//First find all the locations of the transition sources
//							List<ILexLocation> locations = new LinkedList<ILexLocation>();
//							for(CmlBehaviour source : transition.getEventSources())
//							{
//								INode node = source.getNextState().first;
//								locations.add(LocationExtractor.extractLocation(node));
//							}
//
//							transitions.add(new Choice(System.identityHashCode(transition),transition.toString(),locations));
//						}
//
//						ResponseMessage response = sendRequestSynchronous(new RequestMessage(CmlRequest.CHOICE,transitions));
//
//						if(response.isRequestInterrupted())
//							throw new InterpreterRuntimeException("The simulation was interrupted");
//
//						//Grab the response 
//						Choice choice = response.getContent();
//						//System.out.println("response: " + responseStr);
//
//						CmlTransition selectedEvent = null;
//						//For now we just search naively to find the event
//						for(CmlTransition transition : availableChannelEvents.getAllEvents())
//						{
//							//System.out.println("found: " + comEvent.getChannel().getName());
//							if(System.identityHashCode(transition) == choice.getTransitionObjectId())
//								selectedEvent = transition;
//						}
//
////						if(selectedEvent instanceof ChannelEvent && !((ChannelEvent)selectedEvent).isPrecise())
////						{
////							System.out.println("Enter value : "); 
////
////							Value val = new IntegerValue(scanIn.nextInt());
////							((ChannelEvent)selectedEvent).setValue(val);
////						}
//						
//						if(selectedEvent instanceof LabelledTransition && !((LabelledTransition)selectedEvent).getChannelName().isPrecise())
//						{
//							LabelledTransition chosenChannelEvent = (LabelledTransition)selectedEvent;
//							ChannelNameValue channnelName = chosenChannelEvent.getChannelName(); 
//							
//							for(int i = 0 ; i < channnelName.getValues().size() ; i++ )
//							{
//								Value currentValue = channnelName.getValues().get(i);
//								
//								if(!AbstractValueInterpreter.isValueMostPrecise(currentValue))
//								{
//									System.out.println("Enter value : "); 
//									Value val;
//									try {
//										val = channnelName.getChannel().getValueTypes().get(i).apply(new ValueParser());
//										channnelName.updateValue(i, val);
//									} catch (AnalysisException e) {
//										e.printStackTrace();
//										System.exit(-1);
//									}
//								}
//							}
//						}
//
//						return selectedEvent;
//					}
//
//					@Override
//					public CmlTransition select(CmlTransitionSet availableChannelEvents) {
//
//						//At this point we don't want the internal transition to propagate 
//						//to the user, so we randomly choose all the possible internal transitions
//						//before we let anything through to the user
//						if(isSystemSelect(availableChannelEvents))
//							return systemSelect(availableChannelEvents);
//						else
//							return userSelect(availableChannelEvents);
//					}
//				});

		cmlInterpreter.execute(new SelectionStrategy() {

			/*private Scanner scanIn = new Scanner(System.in);*/
			private RandomSelectionStrategy rndSelect = new RandomSelectionStrategy();

			private boolean isSystemSelect(CmlTransitionSet availableChannelEvents)
			{
				return availableChannelEvents.getSilentTransitions().size() > 0;
			}

			private CmlTransition systemSelect(CmlTransitionSet availableChannelEvents)
			{
				return rndSelect.select(new CmlTransitionSet((Set)availableChannelEvents.getSilentTransitions()));
			}

			private CmlTransition userSelect(CmlTransitionSet availableChannelEvents)
			{
				//sendStatusMessage(CmlDbgpStatus.CHOICE, cmlInterpreter.getStatus());

				List<Choice> transitions = new LinkedList<Choice>();
				for(CmlTransition transition : availableChannelEvents.getAllEvents())
				{
					//First find all the locations of the transition sources
					List<ILexLocation> locations = new LinkedList<ILexLocation>();
					for(CmlBehaviour source : transition.getEventSources())
					{
						INode node = source.getNextState().first;
						locations.add(LocationExtractor.extractLocation(node));
					}

					transitions.add(new Choice(System.identityHashCode(transition),transition.toString(),locations));
				}

				ResponseMessage response = sendRequestSynchronous(new RequestMessage(CmlRequest.CHOICE,transitions));

				if(response.isRequestInterrupted())
					throw new InterpreterRuntimeException("The simulation was interrupted");

				//Grab the response 
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

//				if(selectedEvent instanceof ChannelEvent && !((ChannelEvent)selectedEvent).isPrecise())
//				{
//					System.out.println("Enter value : "); 
//
//					Value val = new IntegerValue(scanIn.nextInt());
//					((ChannelEvent)selectedEvent).setValue(val);
//				}
				
				if(selectedEvent instanceof LabelledTransition && !((LabelledTransition)selectedEvent).getChannelName().isPrecise())
				{
					LabelledTransition chosenChannelEvent = (LabelledTransition)selectedEvent;
					ChannelNameValue channnelName = chosenChannelEvent.getChannelName(); 
					
					for(int i = 0 ; i < channnelName.getValues().size() ; i++ )
					{
						Value currentValue = channnelName.getValues().get(i);
						
						if(!AbstractValueInterpreter.isValueMostPrecise(currentValue))
						{
							System.out.println("Enter value : "); 
							Value val;
							try {
								val = channnelName.getChannel().getValueTypes().get(i).apply(new ValueParser());
								channnelName.updateValue(i, val);
							} catch (AnalysisException e) {
								e.printStackTrace();
								System.exit(-1);
							}
						}
					}
				}

				return selectedEvent;
			}

			@Override
			public CmlTransition select(CmlTransitionSet availableChannelEvents) {

				//At this point we don't want the internal transition to propagate 
				//to the user, so we randomly choose all the possible internal transitions
				//before we let anything through to the user
				if(isSystemSelect(availableChannelEvents))
					return systemSelect(availableChannelEvents);
				else
					return userSelect(availableChannelEvents);
			}
		});
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
		if(status!=null && status.hasErrors())
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
		try {
			//check message id
			responseMessage = responseQueue.take();
		} catch (InterruptedException e) {
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
	 * @return The received message
	 * @throws IOException
	 */
	private MessageContainer recvMessage() throws IOException
	{
		return MessageCommunicator.receiveMessage(requestReader,
				new MessageContainer(new CmlDbgStatusMessage(CmlInterpretationStatus.TERMINATED))); 
	}


	private void stopping()
	{
		//sendStatusMessage(CmlDbgpStatus.STOPPING);
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
		if(message.getStatus() != null)
		{
			switch(message.getStatus())
			{
			//		case null:
			//			return false;
			default:
				return false;
			}
		}
		else
			return false;
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
		case SET_BREAKPOINT:	
			Breakpoint bp = message.getContent();
			Console.debug.println("Break point added : " + bp);
			runningInterpreter.addBreakpoint(bp);
			return true;
		case RESUME:
			runningInterpreter.resume();
			return true;
		case STEP:
			runningInterpreter.step();
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

				Context nextContext = context;
				while (nextContext != null)
				{
					stackframes.add(new StackFrameDTO(nextContext.location.getStartLine(), nextContext.location.getFile().toURI(), nextContext.getDepth()));
					nextContext = nextContext.outer;
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
				
				for (int i = 0;context!=null &&  i < level-1; i++)
				{
					context = context.outer;
				}
				
				ResponseMessage responseMessage = new ResponseMessage(message.getRequestId(), CmlRequest.GET_CONTEXT_PROPERTIES, VariableDTO.extractVariables(context));
				sendResponse(responseMessage);
				
				return true;
			}

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
		case REQUEST:
			return processRequest((RequestMessage)messageContainer.getMessage());
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
	public void initialize(CmlInterpreter cmlInterpreter) throws AnalysisException {

		runningInterpreter = cmlInterpreter;
		runningInterpreter.onStatusChanged().registerObserver(this);
		//sendStatusMessage(this.runningInterpreter.getStatus());
		commandDispatcher = new CommandDispatcher();
		new Thread(commandDispatcher, "CMLInterpreterRunner event dipsatcher").start();
		runningInterpreter.initialize();
	}

	private void requestSetup()
	{
		sendRequestSynchronous(new RequestMessage(CmlRequest.SETUP));
	}

	@Override
	public void start(InterpreterExecutionMode mode) {

		try{
//			currentMode = mode;
			if(mode == InterpreterExecutionMode.ANIMATE)
			{
				requestSetup();
				animate(runningInterpreter);
			}
			else if (mode == InterpreterExecutionMode.SIMULATE)
			{
				requestSetup();
				simulate(runningInterpreter);
			}

			stopped(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter));
		}
		catch(CmlInterpreterException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			if(e.hasErrorNode())
				status.addError(new InterpreterErrorDTO(e.getMessage(),LocationExtractor.extractLocation(e.getErrorNode())));
			else
				status.addError(new InterpreterErrorDTO(e.getMessage()));
			stopped(status);
		}
		catch(ValueException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage(),e.ctxt.location));
			stopped(status);
		}
		catch(AnalysisException e)
		{
			CmlInterpreterStateDTO status = CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter);
			status.addError(new InterpreterErrorDTO(e.getMessage()));
			stopped(status);
		}
		finally{
			runningInterpreter.onStatusChanged().unregisterObserver(this);
		}
	}

	@Override
	public void onStatusChanged(Object source, InterpreterStatusEvent event) {
		//Only send this if status is not FAILED, this will be handled in the start method
		//which appends the correct errors to the status
		if(event.getStatus() != CmlInterpretationStatus.FAILED)
		{
			Console.debug.println("Debug thread sending Status event to controller: " + event);
			sendStatusMessage(CmlInterpreterStateDTO.createCmlInterpreterStateDTO(runningInterpreter));
		}
	}

}
