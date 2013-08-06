package eu.compassresearch.core.interpreter.debug;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.CmlParserUtil;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class DebugMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterpreterException 
	 * @throws AnalysisException 
	 */
	public static void main(String[] args) {

		CmlDebugger debugger = new SocketServerCmlDebugger();
		try{
			//Since the process that started expects the debugger to connect
			//we do this first so the connection doesn't time out
			debugger.initialize();

			//Index 0 of args is the JSON config
			JSONObject config =(JSONObject)JSONValue.parse(args[0]);
			//retrieve the paths for the cml sources of the project
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

			// build the forest
			List<PSource> sourceForest = new LinkedList<PSource>();
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

				System.out.println("Starting the interpreter...");
				if(mode.equals("run"))
					debugger.start(DebugMode.SIMULATE, cmlInterpreter);
				else if(mode.equals("debug"))
					debugger.start(DebugMode.ANIMATE, cmlInterpreter);
				//			else if(mode.equals("animate"))
				//				runner.debug();
			}
			else
			{
				//TODO send this to Eclipse also
				System.out.println("Typechecking: Error(s)");
				System.out.println(ih.getTypeErrors());
			}

		} catch (IOException | InterpreterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//debugger.close();
		}
	}

	//	@Override
	//	public void onStatusChanged(Object source, InterpreterStatusEvent event) {
	//
	//		switch(event.getStatus())
	//		{
	//		case RUNNING:
	//			sendStatusMessage(CmlDbgpStatus.RUNNING, cmlInterpreter.getStatus());
	//			break;
	//		}
	//		
	//	}

}
