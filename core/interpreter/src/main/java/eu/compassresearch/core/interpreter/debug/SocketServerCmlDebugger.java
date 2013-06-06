package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

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
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.utility.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;

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
	
	public void simulate(CmlInterpreter cmlInterpreter) throws Exception
	{
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());

		cmlInterpreter.execute(sve);
	}
	
	public void animate(final CmlInterpreter cmlInterpreter) throws Exception
	{

		//Create the supervisor environment with the a selction strategy that has a connection to
		//the eclipse debugger
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new SelectionStrategy() {
					Scanner scanIn = new Scanner(System.in);
					@Override
					public CmlTransition select(CmlAlphabet availableChannelEvents) {

						sendStatusMessage(CmlDbgpStatus.CHOICE, cmlInterpreter.getStatus());

						//convert to list of strings for now
						List<String> events = new LinkedList<String>();
						for(CmlTransition transition : availableChannelEvents.getAllEvents())
						{
							events.add(transition.toString());
						}

						ResponseMessage response = sendRequestSynchronous(new RequestMessage(CmlRequest.CHOICE,events));

						if(response.isRequestInterrupted())
							throw new InterpreterRuntimeException("The simulation was interrupted");

						//TODO At the moment if there are two identical events from different processes on the same channel
						//	then the user cannot distuingiues between the two and for now it will only be the first event in the list
						String responseStr = response.getContent(String.class);
						//System.out.println("response: " + responseStr);

						CmlTransition selectedEvent = null;
						//For now we just search naively to find the event
						for(CmlTransition transition : availableChannelEvents.getAllEvents())
						{
							//System.out.println("found: " + comEvent.getChannel().getName());
							if(transition.toString().equals(responseStr))
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
				});

		cmlInterpreter.execute(sve);
	}
	
	/**
	 * State change methods
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
	 * Message communication methods
	 */
	
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
			return processStatusMessage(messageContainer.<CmlDbgStatusMessage>getMessage(CmlDbgStatusMessage.class));
		case COMMAND:
			return processCommand(messageContainer.<CmlDbgCommandMessage>getMessage(CmlDbgCommandMessage.class));
		case RESPONSE:
			return processResponse(messageContainer.<ResponseMessage>getMessage(ResponseMessage.class));
		default:
		}
		
		return false;
	}
	
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

//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		
//	}
}
