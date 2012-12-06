package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlCommunicationSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.runtime.RandomSelectionStrategy;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;



public class CmlInterpreterRunner {

	private CmlInterpreter cmlInterpreter;
	private Socket requestSocket;
	private OutputStream requestOS;
	private InputStream requestIS;
	private BufferedReader requestReader;
	private boolean connected = false;
	
	//private BlockingQueue<CmlResponseMessage> responseQueue = new LinkedBlockingQueue<CmlResponseMessage>();
	private SynchronousQueue<CmlResponseMessage> responseSync = new SynchronousQueue<CmlResponseMessage>();
	//private CmlRequestMessage pendingRequest = null;
	
	private CommandDispatcher commandDispatcher;
	
	
	class CommandDispatcher implements Runnable
	{
		private boolean stopped = false;
		private Thread thread = null;

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
			thread = Thread.currentThread();
			//thread.setDaemon(true);
			CmlMessageContainer messageContainer = null;
			try{

				do
				{
					messageContainer = recvMessage();
					System.out.println(messageContainer);
				}
				while (!stopped && processMessage(messageContainer));
			
			}catch(IOException e)
			{
				stopped();
				e.printStackTrace();
			}
		}
		
	}
	
	public CmlInterpreterRunner(List<PSource> cmlSources) throws InterpreterException
	{
		cmlInterpreter = VanillaInterpreterFactory.newInterpreter(cmlSources);
	}
	
	private boolean isConnected()
	{
		return connected;
	}
	
	public void run() throws InterpreterException
	{
		cmlInterpreter.execute();
	}
	
	public void debug() throws InterpreterException
	{
		
		try {
			connect();
			init();
			debugLoop();
			stopped();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			
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

	/**
	 * Message communication methods
	 */
	
	/**
	 * This message sends a status message to the eclipse debug target UI
	 * @param status The status to send
	 */
	private void sendStatusMessage(CmlDbgpStatus status)
	{
		CmlDbgStatusMessage dm = new CmlDbgStatusMessage(status);
		System.out.println(dm);
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
	protected void debugLoop() throws IOException, InterpreterException
	{
//		CmlMessageContainer messageContainer = null;

//		do
//		{
			sendStatusMessage(CmlDbgpStatus.RUNNING);
			cmlInterpreter.execute(new CmlCommunicationSelectionStrategy() {
				
				@Override
				public CmlCommunicationEvent select(CmlAlphabet availableChannelEvents) {
					
					//convert to list of strings for now
					List<String> events = new LinkedList<String>();
					for(CmlCommunicationEvent comEvent : availableChannelEvents.getCommunicationEvents())
					{
						events.add(comEvent.getChannel().getName());
					}
					
					CmlDialogMessage response = sendRequestSynchronous(new CmlRequestMessage(CmlRequest.CHOICE,events));
					System.out.println(response);
					return new RandomSelectionStrategy().select(availableChannelEvents);
				}
			});
			
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
	
	private boolean processCommand(CmlDbgCommandMessage message)
	{
		switch(message.getCommand())
		{
		case STOP:
			sendStatusMessage(CmlDbgpStatus.STOPPING);
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
		System.out.println(args);
		//Index 0 of args is the JSON config
		Object obj=JSONValue.parse(args[0]);
		JSONObject config =(JSONObject)obj;
		//retrieve the paths for the cml sources of the project
		List<String> cmlfilePaths = getCmlfilePaths((String)config.get(CmlLaunchConfigurationConstants.ATTR_CML_SOURCES_PATH.toString()));
		System.out.println(cmlfilePaths);
	
		List<PSource> sourceForest = new LinkedList<PSource>();
		
		// build the forest
		for (String path : cmlfilePaths) {
			
			File source = new File(path);
			System.out.println("Parsing file: " + source);
			AFileSource currentTree = new AFileSource();
			currentTree.setName(source.getName());
			FileReader input = new FileReader(source);
			CmlLexer lexer = new CmlLexer(input);
			CmlParser parser = new CmlParser(lexer);
			parser.setDocument(currentTree);
			if (!parser.parse()) {
				//handleError(lexer, source);
				return;
			} else
				sourceForest.add(currentTree);
		}

		//create the typechecker and typecheck the source forest
		TypeIssueHandler ih = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker tc = VanillaFactory.newTypeChecker(sourceForest, ih);
		
		if(tc.typeCheck())
		{
			String mode = (String)config.get("mode");
			CmlInterpreterRunner runner = new CmlInterpreterRunner(sourceForest);
			if(mode.equals("run"))
				runner.run();
			else if(mode.equals("debug"))
				runner.debug();
			else if(mode.equals("animate"))
				runner.debug();
		}
		else
		{
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

}
