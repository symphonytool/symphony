package eu.compassresearch.core.interpreter;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class CoSimulationIntegrationTest extends ExternalProcessTest
{
	public Process setUpCoordinator(String file, String mainProcess,
			String delegatedProcesses) throws Exception
	{
		Process process = startSecondJVM(CMLJ.class, new String[] { "-process",
				mainProcess, "-delegatedprocessed", delegatedProcesses,
				"-mode", "server", "-cosimport", port.toString(), "-simulate",
				file.replace('/', File.separatorChar) });
		ConsoleWatcher watch = new ConsoleWatcher("coordinator");
		this.watched.add(watch);
		startAutoRead(process, "server", watch, quit);
		return process;

	}

	public Process setUpClient(String file, String mainProcess)
			throws Exception
	{

		Process process = startSecondJVM(CMLJ.class, new String[] { "-process",
				mainProcess, "-mode", "client", "-cosimport", port.toString(),
				"-simulate", file.replace('/', File.separatorChar) });
		String name = "client:" + mainProcess;
		ConsoleWatcher watch = new ConsoleWatcher(name);
		this.watched.add(watch);
		startAutoRead(process, name, watch, quit);
		return process;
	}

	
	
	
	protected static class ConsoleWatcher implements IConsoleWatcher
	{
		public final String FINISHED_MATCH_TEXT = "Simulator status event : FINISHED";

		public boolean finished;

		private String name;

		public ConsoleWatcher(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return "Console watcher for: " + name;
		}
		
		/* (non-Javadoc)
		 * @see eu.compassresearch.core.interpreter.IConsoleWatcher#match(java.lang.String)
		 */
		@Override
		public void match(String line)
		{
			if (line.contains(FINISHED_MATCH_TEXT))
			{
				finished = true;
			}	
		}
		
		/* (non-Javadoc)
		 * @see eu.compassresearch.core.interpreter.IConsoleWatcher#matched()
		 */
		@Override
		public boolean matched()
		{
			return finished;
		}

	}

	@Test
	public void testMain() throws Exception
	{
		String source = "src/test/resources/cosim/main.cml";
		Process coordinator = setUpCoordinator(source, "P", "B");
		setUpClient(source, "B");

		waitForCompletion(coordinator, DEFAULT_TIMEOUT);

		Assert.assertTrue("Simulators did not finish successfully", isFinished());

	}

	@Test
	public void testMainTwoClients() throws Exception
	{
		String source = "src/test/resources/cosim/main.cml";
		Process coordinator = setUpCoordinator(source, "P", "B,A");
		setUpClient(source, "B");
		setUpClient(source, "A");

		waitForCompletion(coordinator, DEFAULT_TIMEOUT);

		Assert.assertTrue("Simulators did not finish successfully", isFinished());

	}

	@Test
	public void testMainDeadlocked() throws Exception
	{
		String source = "src/test/resources/cosim/main-deadlocked.cml";
		Process coordinator = setUpCoordinator(source, "P", "B");
		setUpClient(source, "B");

		waitForCompletion(coordinator, DEFAULT_TIMEOUT);

		Assert.assertFalse("Simulators did not finish successfully", isFinished());

	}

}
