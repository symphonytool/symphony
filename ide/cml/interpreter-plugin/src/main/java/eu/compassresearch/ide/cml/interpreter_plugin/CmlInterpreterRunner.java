package eu.compassresearch.ide.cml.interpreter_plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import com.google.gson.Gson;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
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
	
	public CmlInterpreterRunner(List<PSource> cmlSources) throws InterpreterException
	{
		cmlInterpreter = VanillaInterpreterFactory.newInterpreter(cmlSources);
	}
	
	private void connect() throws UnknownHostException, IOException
	{
		if(!isConnected())
		{
			requestSocket = new Socket("localhost",CmlDebugDefaultValues.REQUEST_PORT);
			requestOS = requestSocket.getOutputStream();
			requestIS = requestSocket.getInputStream();
			requestReader = new BufferedReader(new InputStreamReader(requestIS));
			connected = true;
		}
	}
	
	private void init()
	{
		CmlDbgStatusMessage dm = new CmlDbgStatusMessage(CmlDbgpStatus.STARTING);
		sendMessage(dm);
	}
	
	private void sendMessage(CmlDbgStatusMessage dm)
	{
		CmlMessageCommunicator.sendMessage(requestOS, dm);
	}

	private CmlMessageContainer recvMessage() throws IOException
	{
		return CmlMessageCommunicator.receiveMessage(requestReader);
	}
	
	private boolean isConnected()
	{
		return connected;
	}
	
	public void run() throws AnalysisException
	{
		cmlInterpreter.execute();
	}
	
	public void debug() throws AnalysisException
	{
		
		try {
			connect();
			init();
			debugLoop();
			
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
	 * This handles the communication with the eclipse debugger UI
	 * @throws IOException
	 */
	protected void debugLoop() throws IOException
	{
		CmlMessageContainer messageContainer = null;

		do
		{
			messageContainer = recvMessage();
			System.out.println(messageContainer);
		}
		while (processMessage(messageContainer));
		
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
	
	private boolean processMessage(CmlMessageContainer messageContainer)
	{
		switch(messageContainer.getType())
		{
		case STATUS:
			return processStatusMessage(messageContainer.<CmlDbgStatusMessage>getMessage(CmlDbgStatusMessage.class));
		default:
			break;
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
