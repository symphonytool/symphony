package eu.compassresearch.core.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.overture.ast.lex.Dialect;
import org.overture.interpreter.runtime.ValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.ConsoleSelectionStrategy;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.SelectionStrategy;
import eu.compassresearch.core.interpreter.api.events.CmlInterpreterStateObserver;
import eu.compassresearch.core.interpreter.api.events.InterpreterStateChangedEvent;
import eu.compassresearch.core.interpreter.cosim.CoSimulationClient;
import eu.compassresearch.core.interpreter.cosim.CoSimulationServer;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;
import eu.compassresearch.core.interpreter.remote.RemoteInterpreter;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class CMLJ
{
	final static Logger logger = LoggerFactory.getLogger("cml-interpreter");

	static InterpreterFactory factory = new VanillaInterpreterFactory();

	/**
	 * Cosim server: -process P -delegatedprocessed A -mode server -cosimport 8088 -simulate
	 * C:\overture\runtime-compass.product\cosim-cml\main.cml
	 * <p/>
	 * Cosim client: -process A -mode client -cosimport 8088 -simulate
	 * C:\overture\runtime-compass.product\cosim-cml\main.cml
	 * 
	 * @param args
	 * @throws IOException
	 * @throws CmlInterpreterException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException,
			CmlInterpreterException, InstantiationException,
			IllegalAccessException
	{
		List<String> largs = Arrays.asList(args);
		List<File> filenames = new Vector<File>();
		String processName = null;
		String remoteName = null;
		List<String> delegatedProcesses = null;
		CoSimulationMode mode = CoSimulationMode.Standard;
		int coSimPort = -1;

		SelectionStrategy selectionStrategy = new ConsoleSelectionStrategy();

		for (Iterator<String> i = largs.iterator(); i.hasNext();)
		{
			String arg = i.next();
			if (arg.equals("-process"))
			{
				if (i.hasNext())
				{
					processName = i.next();
				} else
				{
					usage("-process option requires a process name");
				}
			} else if (arg.equals("-delegatedprocessed"))
			{
				if (i.hasNext())
				{
					delegatedProcesses = new ArrayList<String>();

					String processes = i.next();

					String tmp[] = processes.split(",");
					delegatedProcesses.addAll(Arrays.asList(tmp));

				} else
				{
					usage("-delegatedprocessed option requires a comma seperated list of delegated process names");
				}
			} else if (arg.equals("-mode"))
			{
				if (i.hasNext())
				{
					mode = CoSimulationMode.fromString(i.next());
				} else
				{
					usage("-mode option requires a mode");
				}
			} else if (arg.equals("-cosimport"))
			{
				if (i.hasNext())
				{
					coSimPort = Integer.valueOf(i.next());
				} else
				{
					usage("-cosimport option requires a number");
				}
			} else if (arg.equals("-animate"))
			{
				selectionStrategy = new ConsoleSelectionStrategy();
			} else if (arg.equals("-simulate"))
			{
				selectionStrategy = new RandomSelectionStrategy();
			} else if (arg.equals("-remote"))
			{
				if (i.hasNext())
				{
					remoteName = i.next();
				} else
				{
					usage("-remote option requires a fully qualified java class name");
				}
			} else if (arg.startsWith("-"))
			{
				usage("Unknown option " + arg);
			} else
			{
				// It's a file or a directory
				File file = new File(arg);

				if (file.isDirectory())
				{
					for (File subFile : file.listFiles(Dialect.CML.getFilter()))
					{
						if (subFile.isFile())
						{
							filenames.add(subFile);
						}
					}
				} else
				{
					if (file.exists())
					{
						filenames.add(file);
					} else
					{
						usage("Cannot find file " + file);

					}
				}
			}
		}

		Class<IRemoteControl> remoteClass = null;
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

		CoSimulationServer server = null;
		switch (mode)
		{
			case CoSimCoordinator:
			{

				server = new CoSimulationServer(delegatedProcesses, coSimPort);
				server.listen();
				server.waitForClients();
				factory = new CoSimCoordinatorInterpreterFactory(server);

				break;
			}
			case CoSimClient:
			{
				CoSimulationClient client = new CoSimulationClient("localhost", coSimPort);
				client.connect();
				client.start();
				client.registerImplementation(processName);
				factory = new CoSimClientInterpreterFactory(client);

				break;
			}
		}

		execute(remote, selectionStrategy, processName, filenames.toArray(new File[filenames.size()]));

		if (mode == CoSimulationMode.CoSimCoordinator)
		{
			server.close();
		}
	}

	private static void execute(IRemoteControl remote,
			SelectionStrategy selectionStrategy, String processName, File... f)
			throws IOException, CmlInterpreterException
	{
		ParserResult res = ParserUtil.parse(f);

		if (res.errors.size() > 0)
		{
			res.printErrors(System.err);
			return;
		}

		ITypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();

		// Type check
		ICmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(res.definitions, issueHandler);

		// Print result and report errors if any
		cmlTC.typeCheck();
		if (issueHandler.hasErrors())
		{
			System.out.println("Failed to type check: " + Arrays.toString(f));
			System.out.println(issueHandler.getTypeErrors());
			return;
		}

		// interpret
		CmlInterpreter interpreter = factory.newInterpreter(res.definitions);
		if (processName != null)
		{
			interpreter.setDefaultName(processName);
		}
		interpreter.onStateChanged().registerObserver(new CmlInterpreterStateObserver()
		{

			@Override
			public void onStateChanged(Object source,
					InterpreterStateChangedEvent event)
			{
				logger.debug("Simulator status event : " + event.getStatus());

			}
		});

		try
		{
			interpreter.initialize();
			if (remote == null)
			{
				interpreter.execute(selectionStrategy);
			} else
			{
				IRemoteInterpreter remoteInterpreter = new RemoteInterpreter(interpreter);
				remote.run(remoteInterpreter);
			}
		} catch (ValueException e)
		{
			System.out.println("With Error : " + e);
			System.out.println(e.ctxt.location);
			System.out.println("With stack trace : ");
			e.printStackTrace();
		} catch (Exception ex)
		{
			System.out.println("Failed to interpret: " + Arrays.toString(f));
			System.out.println("With Error : " + ex.getMessage());
			System.out.println("With stack trace : ");
			ex.printStackTrace();
			return;
		}

		// Report success
		System.out.println("The given CML Program is done simulating.");
	}

	private static void usage(String msg)
	{
		System.err.println("COMPASS: " + msg + "\n");
		System.err.println("Usage: COMPASS <-process> [<options>] [<files or dirs>]");
		System.err.println("-animate: use the animation strategy");
		System.err.println("-simulate: use the simulation strategy that autoselects events");
		System.err.println("-remote: use a remote interpreter as selection strategy");
		// System.err.println("-path: search path for files");
		// System.err.println("-r <release>: VDM language release");
		// System.err.println("-w: suppress warning messages");
		// System.err.println("-q: suppress information messages");
		// System.err.println("-i: run the interpreter if successfully type checked");
		// System.err.println("-p: generate proof obligations and stop");
		// System.err.println("-e <exp>: evaluate <exp> and stop");
		// System.err.println("-c <charset>: select a file charset");
		// System.err.println("-t <charset>: select a console charset");
		// System.err.println("-o <filename>: saved type checked specification");
		// System.err.println("-default <name>: set the default module/class");
		// System.err.println("-pre: disable precondition checks");
		// System.err.println("-post: disable postcondition checks");
		// System.err.println("-inv: disable type/state invariant checks");
		// System.err.println("-dtc: disable all dynamic type checking");
		// System.err.println("-measures: disable recursive measure checking");
		// System.err.println("-log: enable real-time event logging");
		// System.err.println("-remote <class>: enable remote control");

		System.exit(1);
	}
}
