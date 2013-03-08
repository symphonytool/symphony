package eu.compassresearch.core.interpreter.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
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
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterError;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgCommandMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgStatusMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlDbgpStatus;
import eu.compassresearch.core.interpreter.debug.messaging.CmlMessageCommunicator;
import eu.compassresearch.core.interpreter.debug.messaging.CmlMessageContainer;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequest;
import eu.compassresearch.core.interpreter.debug.messaging.CmlRequestMessage;
import eu.compassresearch.core.interpreter.debug.messaging.CmlResponseMessage;
import eu.compassresearch.core.interpreter.events.CmlInterpreterStatusObserver;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.scheduler.FCFSPolicy;
import eu.compassresearch.core.interpreter.scheduler.CmlScheduler;
import eu.compassresearch.core.interpreter.util.CmlUtil;
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
	
	private SynchronousQueue<CmlResponseMessage> responseSync = new SynchronousQueue<CmlResponseMessage>();
	
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
			CmlMessageContainer messageContainer = null;
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
		CmlScheduler scheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newCmlSupervisorEnvironment(new RandomSelectionStrategy(), scheduler);
		
		try {
			connect();
			init();
			cmlInterpreter.execute(sve,scheduler);
			stopped(cmlInterpreter.getStatus());
		} catch (InterpreterException e) {

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
	 * Sends the initialisation message to the eclipse debug target
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
		CmlMessageCommunicator.sendMessage(requestOS, dm);
	}
	
	private CmlResponseMessage sendRequestSynchronous(CmlRequestMessage message)
	{
		CmlMessageCommunicator.sendMessage(requestOS, message);
		CmlResponseMessage responseMessage = null;
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
	private CmlMessageContainer recvMessage() throws IOException
	{
		return CmlMessageCommunicator.receiveMessage(requestReader);
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
				CmlScheduler scheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
				CmlSupervisorEnvironment sve = 
						VanillaInterpreterFactory.newCmlSupervisorEnvironment(new CmlCommunicationSelectionStrategy() {
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

								CmlResponseMessage response = sendRequestSynchronous(new CmlRequestMessage(CmlRequest.CHOICE,events));

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
								
								if(!selectedEvent.isValuePrecise())
								{
									System.out.println("Enter value : "); 
									
									Value val = new IntegerValue(scanIn.nextInt());
									selectedEvent.setValue(val);
								}

								return selectedEvent;
							}
						}, scheduler);
			
				cmlInterpreter.execute(sve,scheduler);
				stopped(this.cmlInterpreter.getStatus());
			}
			catch(ContextException e)
			{
				InterpreterStatus status = this.cmlInterpreter.getStatus();
				status.AddError(new InterpreterError(e.getMessage()));
				stopped(status);
			}
			catch(InterpreterException e)
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
		responseSync.add(new CmlResponseMessage());
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
	private boolean processResponse(CmlResponseMessage message)
	{
		responseSync.offer(message);
		return true;
	}
		
	
	private boolean processMessage(CmlMessageContainer messageContainer)
	{
		switch(messageContainer.getType())
		{
		case STATUS:
			return processStatusMessage(messageContainer.<CmlDbgStatusMessage>getMessage(CmlDbgStatusMessage.class));
		case COMMAND:
			return processCommand(messageContainer.<CmlDbgCommandMessage>getMessage(CmlDbgCommandMessage.class));
		case RESPONSE:
			return processResponse(messageContainer.<CmlResponseMessage>getMessage(CmlResponseMessage.class));
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
		String sourcesPath = (String)config.get(CmlInterpreterLaunchConfigurationConstants.CML_SOURCES_PATH.toString());
		//retrieve the top process name
		String startProcessName = (String)config.get(CmlInterpreterLaunchConfigurationConstants.PROCESS_NAME.toString());

		if(sourcesPath == null || sourcesPath.length() == 0)
		{
			System.out.println("The path to the cml sources are not defined");
			return;
		}
	
		List<PSource> sourceForest = new LinkedList<PSource>();
		
		// build the forest
		for (String path : getCmlfilePaths(sourcesPath)) {
			
			File source = new File(path);
			System.out.println("Parsing file: " + source);
			AFileSource currentFileSource = new AFileSource();
			currentFileSource.setName(source.getName());
			currentFileSource.setFile(source);
						
			if (!CmlUtil.parseSource(currentFileSource)) {
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
	
	/**
	 * Find all the CML files in the specified path
	 * @return
	 */
	public static List<String> getCmlfilePaths(String path) {

		File dir = new File(path);
		List<String> paths = new LinkedList<String>();

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cml");
			}
		};

		String[] children = dir.list(filter);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(dir.getPath() + "/" + children[i]);
			}
		}

		return paths;
	}

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
