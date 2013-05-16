package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.CmlParserUtil;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterError;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.utility.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.utility.messaging.MessageCommunicator;
import eu.compassresearch.core.interpreter.utility.messaging.MessageContainer;
import eu.compassresearch.core.interpreter.utility.messaging.RequestMessage;
import eu.compassresearch.core.interpreter.utility.messaging.ResponseMessage;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class CmlInterpreterController implements CmlInterpreterStatusObserver {

	private CmlInterpreter cmlInterpreter;
	private Socket requestSocket;
	private OutputStream requestOS;
	private InputStream requestIS;
	private BufferedReader requestReader;
	private boolean connected = false;
	
	private SynchronousQueue<ResponseMessage> responseSync = new SynchronousQueue<ResponseMessage>();
	
	private CommandDispatcher commandDispatcher;
	
	
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
	
	public CmlInterpreterController(CmlInterpreter cmlInterpreter) throws InterpreterException
	{
		this.cmlInterpreter = cmlInterpreter;
	}
	
	private boolean isConnected()
	{
		return connected;
	}
	
	public void run() 
	{
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());
		
		try {
			connect();
			init();
			cmlInterpreter.execute(sve);
			stopped(cmlInterpreter.getStatus());
		} 
		catch (AnalysisException e) {

			InterpreterStatus status = cmlInterpreter.getStatus();
			status.AddError(new InterpreterError(e.getMessage()));
			stopped(cmlInterpreter.getStatus());
		}
		catch(InterpreterRuntimeException e)
		{
			InterpreterStatus status = this.cmlInterpreter.getStatus();
			status.AddError(new InterpreterError(e.getMessage()));
			stopped(status);
		}
		catch (ContextException e) {

			InterpreterStatus status = cmlInterpreter.getStatus();
			status.AddError(new InterpreterError(e.getMessage()));
			stopped(cmlInterpreter.getStatus());
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void debug() throws InterpreterException
	{
		cmlInterpreter.onStatusChanged().registerObserver(this);
		try {
			connect();
			init();
			debugLoop();
			//stopped();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			cmlInterpreter.onStatusChanged().unregisterObserver(this);
		}
		
		//cmlInterpreter.execute();
	}
	
	/**
	 * Private methods
	 */
	
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
		sendStatusMessage(CmlDbgpStatus.STOPPED);
		commandDispatcher.stop();
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
			responseMessage = responseSync.take();
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
	
	/**
	 * This handles the communication with the eclipse debugger UI
	 * @throws IOException
	 */
	protected void debugLoop() throws IOException
	{
//		CmlMessageContainer messageContainer = null;

//		do
//		{
			//sendStatusMessage(CmlDbgpStatus.RUNNING);
			
			try{
				CmlSupervisorEnvironment sve = 
						VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new SelectionStrategy() {
							Scanner scanIn = new Scanner(System.in);
							@Override
							public ObservableEvent select(CmlAlphabet availableChannelEvents) {

								sendStatusMessage(CmlDbgpStatus.CHOICE, CmlInterpreterController.this.cmlInterpreter.getStatus());
								
								//convert to list of strings for now
								List<String> events = new LinkedList<String>();
								for(ObservableEvent comEvent : availableChannelEvents.getObservableEvents())
								{
									events.add(comEvent.toString());
								}

								ResponseMessage response = sendRequestSynchronous(new RequestMessage(CmlRequest.CHOICE,events));

								if(response.isRequestInterrupted())
									throw new InterpreterRuntimeException("The simulation was interrupted");

								//TODO At the moment if there are two identical events from different processes on the same channel
								//	then the user cannot distuingiues between the two and for now it will only be the first event in the list
								String responseStr = response.getContent(String.class);
								//System.out.println("response: " + responseStr);
								
								ObservableEvent selectedEvent = null;
								//For now we just search naively to find the event
								for(ObservableEvent comEvent : availableChannelEvents.getObservableEvents())
								{
									//System.out.println("found: " + comEvent.getChannel().getName());
									if(comEvent.toString().equals(responseStr))
										selectedEvent = comEvent;
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
				stopped(this.cmlInterpreter.getStatus());
			}
			catch(ContextException e)
			{
				InterpreterStatus status = this.cmlInterpreter.getStatus();
				status.AddError(new InterpreterError(e.getMessage()));
				stopped(status);
			}
			catch(AnalysisException e)
			{
				InterpreterStatus status = this.cmlInterpreter.getStatus();
				status.AddError(new InterpreterError(e.getMessage()));
				stopped(status);
			}
			catch(InterpreterRuntimeException e)
			{
				InterpreterStatus status = this.cmlInterpreter.getStatus();
				status.AddError(new InterpreterError(e.getMessage()));
				stopped(status);
			}
//			messageContainer = recvMessage();
//			System.out.println(messageContainer);
//		}
//		while (processMessage(messageContainer));
		
		//if(message.getStatus() != CmlDbgpStatus.CONNECTION_CLOSED)
		//	sendMessage(new CmlDebugStatusMessage(CmlDbgpStatus.STOPPED));
		
	}
	
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
	
	private void stopping()
	{
		sendStatusMessage(CmlDbgpStatus.STOPPING);
		responseSync.add(new ResponseMessage());
	}
	
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
		responseSync.offer(message);
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
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterpreterException 
	 * @throws AnalysisException 
	 */
	public static void main(String[] args) throws IOException, InterpreterException, AnalysisException {

		//Index 0 of args is the JSON config
		JSONObject config =(JSONObject)JSONValue.parse(args[0]);
		//retrieve the paths for the cml sources of the project
		System.out.println(config.get(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString()));
		
		List<String> sourcesPaths = new LinkedList<String>();
		
		Object sourcesPathsObject = config.get(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString());
		//Since the used json encoding for some reason does not encode a list of size of 1 as a list but
		//as a single normal string, we need to check this.
		if(sourcesPathsObject instanceof List<?>)
			sourcesPaths.addAll((List<String>)sourcesPathsObject);
		else
			sourcesPaths.add((String)sourcesPathsObject);

		//retrieve the top process name
		String startProcessName = (String)config.get(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString());

		if(sourcesPaths == null || sourcesPaths.size() == 0)
		{
			System.out.println("The path to the cml sources are not defined");
			return;
		}
	
		List<PSource> sourceForest = new LinkedList<PSource>();
		
		// build the forest
		for (String path : sourcesPaths) {
			
			File source = new File(path);
			System.out.println("Parsing file: " + source);
			AFileSource currentFileSource = new AFileSource();
			currentFileSource.setName(source.getName());
			currentFileSource.setFile(source);
						
			if (!CmlParserUtil.parseSource(currentFileSource)) {
				//handleError(lexer, source);
				return;
			} else
				sourceForest.add(currentFileSource);
		}

		//create the typechecker and typecheck the source forest
		TypeIssueHandler ih = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker tc = VanillaFactory.newTypeChecker(sourceForest, ih);
		System.out.println("Typechecking...");
		if(tc.typeCheck())
		{
			System.out.println("Typechecking: OK");
			String mode = (String)config.get("mode");
			
			CmlInterpreter cmlInterpreter = VanillaInterpreterFactory.newInterpreter(sourceForest);
			cmlInterpreter.setDefaultName(startProcessName);
			
			CmlInterpreterController runner = new CmlInterpreterController(cmlInterpreter);
			System.out.println("Starting the interpreter...");
			if(mode.equals("run"))
				runner.run();
			else if(mode.equals("debug"))
				runner.debug();
//			else if(mode.equals("animate"))
//				runner.debug();
		}
		else
		{
			System.out.println("Typechecking: Error(s)");
			System.out.println(ih.getTypeErrors());
		}
	}
	
//	/**
//	 * Find all the CML files in the specified path
//	 * @return
//	 */
//	public static List<String> getCmlfilePaths(String path) {
//
//		File dir = new File(path);
//		List<String> paths = new LinkedList<String>();
//
//		FilenameFilter filter = new FilenameFilter() {
//			public boolean accept(File dir, String name) {
//				return name.toLowerCase().endsWith(".cml");
//			}
//		};
//
//		String[] children = dir.list(filter);
//		if (children == null) {
//			// Either dir does not exist or is not a directory
//		} else {
//			for (int i = 0; i < children.length; i++) {
//				// Get filename of file or directory
//				paths.add(dir.getPath() + "/" + children[i]);
//			}
//		}
//
//		return paths;
//	}

	@Override
	public void onStatusChanged(Object source, InterpreterStatusEvent event) {

		switch(event.getStatus())
		{
		case RUNNING:
			sendStatusMessage(CmlDbgpStatus.RUNNING, cmlInterpreter.getStatus());
			break;
		}
		
	}

}
