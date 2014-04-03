package eu.compassresearch.core.interpreter.debug;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.CoSimClientInterpreterFactory;
import eu.compassresearch.core.interpreter.CoSimCoordinatorInterpreterFactory;
import eu.compassresearch.core.interpreter.CoSimulationMode;
import eu.compassresearch.core.interpreter.Config;
import eu.compassresearch.core.interpreter.Console;
import eu.compassresearch.core.interpreter.InterpreterFactory;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.DebugAnimationStrategy;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.cosim.CoSimulationClient;
import eu.compassresearch.core.interpreter.cosim.CoSimulationServer;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;
import eu.compassresearch.core.interpreter.remote.RemoteInterpreter;
import eu.compassresearch.core.interpreter.utility.TimedSpeckChecker;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class DebugMain
{

	static InterpreterFactory factory = new VanillaInterpreterFactory();
	static CoSimulationServer server = null;
	static CoSimulationClient client = null;

	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 * @throws CmlInterpreterException
	 * @throws AnalysisException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception
	{

		Console.enableDebug(false);
		Console.enableOut(true);
		CmlDebugger debugger = new SocketServerCmlDebugger();
		String remoteName = null;
		Class<IRemoteControl> remoteClass = null;
		boolean autoFilterTockEvents = false;
		int port = CmlDebugDefaultValues.PORT;
		String host = "localhost";

		try
		{
			// ----------- Config -------------------------------------

			// Index 0 of args is the JSON config
			JSONObject jargs = (JSONObject) JSONValue.parse(args[0]);
			// retrieve the paths for the cml sources of the project
			List<String> sourcesPaths = new LinkedList<String>();
			Object sourcesPathsObject = jargs.get(CmlInterpreterArguments.CML_SOURCES_PATH.key);
			// Since the used json encoding for some reason does not encode a list of size of 1 as a list but
			// as a single normal string, we need to check this.
			if (sourcesPathsObject instanceof List<?>)
			{
				sourcesPaths.addAll((List<String>) sourcesPathsObject);
			} else
			{
				sourcesPaths.add((String) sourcesPathsObject);
			}

			// retrieve the top process name
			String startProcessName = (String) jargs.get(CmlInterpreterArguments.PROCESS_NAME.toString());
			// retrieve the interpretation mode
			InterpreterExecutionMode interpreterExecutionMode = InterpreterExecutionMode.ANIMATE;
			boolean execMode = (boolean) jargs.get(CmlInterpreterArguments.CML_EXEC_MODE.key);
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

			if (jargs.containsKey(CmlInterpreterArguments.REMOTE_NAME.key))
			{
				remoteName = (String) jargs.get(CmlInterpreterArguments.REMOTE_NAME.key);
			}

			if (jargs.containsKey(CmlInterpreterArguments.PORT.key))
			{
				port = Integer.parseInt(""
						+ jargs.get(CmlInterpreterArguments.PORT.key));
			}

			if (jargs.containsKey(CmlInterpreterArguments.HOST.key))
			{
				host = (String) jargs.get(CmlInterpreterArguments.HOST.key);
			}

			if (jargs.containsKey(CmlInterpreterArguments.AUTO_FILTER_TOCK_EVENTS.key)
					&& Boolean.parseBoolean(jargs.get(CmlInterpreterArguments.AUTO_FILTER_TOCK_EVENTS.key)
							+ ""))
			{
				autoFilterTockEvents = true;
			}

			// -----------------------------------------------------------------------------------------------
			// start running
			// remoteName="eu.compassresearch.core.interpreter.remote.RemoteTester";
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

			IRemoteControl remote = remoteClass == null ? null
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
			try
			{
				debugger.connect(host, port);

				// create the typechecker and typecheck the source forest
				ITypeIssueHandler ih = VanillaFactory.newCollectingIssueHandle();
				ICmlTypeChecker tc = VanillaFactory.newTypeChecker(res.definitions, ih);
				Console.debug.println("Debug Thread: Typechecking...");
				if (tc.typeCheck())
				{
					Console.debug.println("Debug Thread: Typechecking: OK");

					configureCoSimulation(jargs);

					boolean filterTockEvents = autoFilterTockEvents
							&& !TimedSpeckChecker.containsTimeConstructs(res.definitions);
					Config config = factory.newDefaultConfig(filterTockEvents);
					CmlInterpreter cmlInterpreter = factory.newInterpreter(res.definitions, config);
					cmlInterpreter.setDefaultName(startProcessName);
					CmlRuntime.consoleMode = false;
					Console.debug.println("Debug Thread: Initializing the interpreter...");
					debugger.initialize(cmlInterpreter);

					if (remote == null)
					{
						Console.debug.println("Debug Thread: Starting the interpreter...");

						SelectionStrategy strategy = null;

						switch (interpreterExecutionMode)
						{
							case ANIMATE:
								strategy = new DebugAnimationStrategy();
								break;
							case SIMULATE:
							default:
								strategy = new RandomSelectionStrategy();
								break;

						}

						debugger.start(strategy);
					} else
					{
						IRemoteInterpreter interpreter = new RemoteInterpreter(cmlInterpreter, debugger);
						remote.run(interpreter);
					}
				} else
				{
					// TODO send this to Eclipse also
					Console.err.println("Typechecking: Error(s)");
					Console.err.println(ih.getTypeErrors());
				}
			} finally
			{
				debugger.dicsonnect();
			}

		} catch (IOException | AnalysisException e)
		{
			e.printStackTrace(Console.err);
		} finally
		{
			shutdownCoSimulation();
		}
		Console.out.println("Terminated");
	}

	private static void shutdownCoSimulation() throws InterruptedException
	{
		if (server != null)
		{
			server.close();
		}

		if (client != null)
		{
			Console.out.println("Waiting for client to recieve disconnect instructions...");
			try
			{
				client.join();
			} catch (InterruptedException e)
			{
				// ignore
			}
			try
			{
				Console.out.println("Client instructed to disconnect so disconnecting now");
				client.disconnect();
			} catch (Exception e)
			{
				// don't care
			}
		}

	}

	private static void configureCoSimulation(JSONObject jargs)
			throws IOException
	{
		CoSimulationMode mode = null;
		List<String> externalProcesses = new Vector<String>();
		String host = null;
		int port = 0;

		String processName = (String) jargs.get(CmlInterpreterArguments.PROCESS_NAME.toString());

		if (jargs.containsKey(CmlInterpreterArguments.COSIM_MODE.key))
		{
			mode = CoSimulationMode.fromString((String) jargs.get(CmlInterpreterArguments.COSIM_MODE.key));
		} else
		{
			return;
		}

		if (mode == CoSimulationMode.CoSimCoordinator)
		{
			if (jargs.containsKey(CmlInterpreterArguments.COSIM_EXTERNAL_PROCESSES.key))
			{
				String tmp = (String) jargs.get(CmlInterpreterArguments.COSIM_EXTERNAL_PROCESSES.key);
				externalProcesses.addAll(Arrays.asList(tmp.split(",")));
			} else
			{
				Console.err.println("Missing required argument for external processes");
			}

		}

		if (jargs.containsKey(CmlInterpreterArguments.COSIM_HOST.key))
		{
			String tmp = (String) jargs.get(CmlInterpreterArguments.COSIM_HOST.key);

			String[] decodedTmp = tmp.split("\\:");
			host = decodedTmp[0];
			port = Integer.valueOf(decodedTmp[1]);
		}

		// configure
		Console.out.println("Starting co-simulation with " + host + ":" + port);

		switch (mode)
		{
			case CoSimCoordinator:
			{

				server = new CoSimulationServer(externalProcesses, port);
				server.listen();
				server.waitForClients();
				factory = new CoSimCoordinatorInterpreterFactory(server);

				break;
			}
			case CoSimClient:
			{
				client = new CoSimulationClient(host, port);
				client.connect();
				client.start();

				client.registerImplementation(processName);
				factory = new CoSimClientInterpreterFactory(client);

				break;
			}
		}

	}

	private static void usage(String message)
	{
		Console.err.println(message);

	}
}
