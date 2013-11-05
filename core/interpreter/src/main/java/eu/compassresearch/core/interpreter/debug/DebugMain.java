package eu.compassresearch.core.interpreter.debug;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class DebugMain
{

	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 * @throws CmlInterpreterException
	 * @throws AnalysisException
	 */
	public static void main(String[] args) throws Exception
	{

		Console.enableDebug(false);
		Console.enableOut(true);
		CmlDebugger debugger = new SocketServerCmlDebugger();
		String remoteName = null;
		Class<IRemoteControl> remoteClass = null;
		int port = CmlDebugDefaultValues.PORT;
		String host = "localhost";
		try
		{
			// ----------- Config -------------------------------------

			// Index 0 of args is the JSON config
			JSONObject config = (JSONObject) JSONValue.parse(args[0]);
			// retrieve the paths for the cml sources of the project
			List<String> sourcesPaths = new LinkedList<String>();
			Object sourcesPathsObject = config.get(CmlInterpreterArguments.CML_SOURCES_PATH.key);
			// Since the used json encoding for some reason does not encode a list of size of 1 as a list but
			// as a single normal string, we need to check this.
			if (sourcesPathsObject instanceof List<?>)
				sourcesPaths.addAll((List<String>) sourcesPathsObject);
			else
				sourcesPaths.add((String) sourcesPathsObject);

			// retrieve the top process name
			String startProcessName = (String) config.get(CmlInterpreterArguments.PROCESS_NAME.toString());
			// retrieve the interpretation mode
			InterpreterExecutionMode interpreterExecutionMode = InterpreterExecutionMode.ANIMATE;
			boolean execMode = (boolean) config.get(CmlInterpreterArguments.CML_EXEC_MODE.key);
			if (!execMode)
			{
				interpreterExecutionMode = InterpreterExecutionMode.SIMULATE;
			}
			Console.debug.println("interpreterExecutionMode: "
					+ interpreterExecutionMode);

			if (sourcesPaths == null || sourcesPaths.size() == 0)
			{
				usage("The path to the cml sources are not defined");
				return;
			}

			if (config.containsKey(CmlInterpreterArguments.REMOTE_NAME.key))
			{
				remoteName = (String) config.get(CmlInterpreterArguments.REMOTE_NAME.key);
			}

			if (config.containsKey(CmlInterpreterArguments.PORT.key))
			{
				port = (int) config.get(CmlInterpreterArguments.PORT.key);
			}

			if (config.containsKey(CmlInterpreterArguments.HOST.key))
			{
				host = (String) config.get(CmlInterpreterArguments.HOST.key);
			}

			// -----------------------------------------------------------------------------------------------
			// start running

			if (remoteName != null)
			{
				try
				{
					Class<?> cls = ClassLoader.getSystemClassLoader().loadClass(remoteName);
					remoteClass = (Class<IRemoteControl>) cls;
				} catch (ClassNotFoundException e)
				{
					usage("Cannot locate " + remoteName + " on the CLASSPATH");
				}
			}

			IRemoteControl remote = (remoteClass == null) ? null
					: remoteClass.newInstance();

			// build the forest
			List<File> sources = new LinkedList<File>();
			for (String path : sourcesPaths)
			{
				sources.add(new File(path));
			}

			Console.debug.println("Parsing files: " + sourcesPaths);
			ParserResult res = ParserUtil.parse(sources);

			if (!res.errors.isEmpty())
			{
				Console.err.println("Model Contained Parser errors");
				res.printErrors(Console.err);
				return;
			}

			// Since the process that started expects the debugger to connect
			// we do this first so the connection doesn't time out
			debugger.connect(host, port);

			// create the typechecker and typecheck the source forest
			ITypeIssueHandler ih = VanillaFactory.newCollectingIssueHandle();
			ICmlTypeChecker tc = VanillaFactory.newTypeChecker(res.definitions, ih);
			Console.debug.println("Debug Thread: Typechecking...");
			if (tc.typeCheck())
			{
				Console.debug.println("Debug Thread: Typechecking: OK");

				CmlInterpreter cmlInterpreter = VanillaInterpreterFactory.newInterpreter(res.definitions);
				cmlInterpreter.setDefaultName(startProcessName);
				Console.debug.println("Debug Thread: Initializing the interpreter...");
				debugger.initialize(cmlInterpreter);

				if (remote == null)
				{
					Console.debug.println("Debug Thread: Starting the interpreter...");
					debugger.start(interpreterExecutionMode);
				} else
				{
					IRemoteInterpreter interpreter = null;
					remote.run(interpreter);
				}
			} else
			{
				// TODO send this to Eclipse also
				Console.err.println("Typechecking: Error(s)");
				Console.err.println(ih.getTypeErrors());
			}

		} catch (IOException | AnalysisException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace(Console.err);
		} finally
		{
			// debugger.close();
		}
	}

	private static void usage(String message)
	{
		Console.err.println(message);

	}
}
