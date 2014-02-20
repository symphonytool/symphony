package eu.compassresearch.core.interpreter;

import java.io.File;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

import eu.compassresearch.core.interpreter.cosim.communication.Utils;

public class CoSimulationIntegrationTest extends ExternalProcessTest
{
	public Process setUpCoordinator(String file, String mainProcess,
			String delegatedProcesses) throws Exception
	{
		Process process = startSecondJVM(CMLJ.class, new String[] { "-process",
				mainProcess, "-delegatedprocessed", delegatedProcesses,
				"-mode", "server", "-cosimport", port.toString(), "-simulate",
				file.replace('/', File.separatorChar) });
		ConsoleWatcher watch = new ConsoleWatcher("coordinator",ConsoleWatcher.FINISHED_MATCH_TEXT);
		ConsoleWatcher listningWatch = new ConsoleWatcher("coordinator","Waiting for clients...");
		this.watched.add(watch);
		startAutoRead(process, "server", quiet,watch,listningWatch );
		
		int time = 0;
		final int RETRY_WAIT = 200;
		while(!listningWatch.isMatched())
		{
			if(time*1000 == DEFAULT_TIMEOUT)
			{
				throw new TimeoutException("Server never started to listen for clients");
			}
			Utils.milliPause(RETRY_WAIT);
			time +=RETRY_WAIT ;
		}
		return process;

	}

	public Process setUpClient(String file, String mainProcess)
			throws Exception
	{

		Process process = startSecondJVM(CMLJ.class, new String[] { "-process",
				mainProcess, "-mode", "client", "-cosimport", port.toString(),
				"-simulate", file.replace('/', File.separatorChar) });
		String name = "client:" + mainProcess;
		ConsoleWatcher watch = new ConsoleWatcher(name,ConsoleWatcher.FINISHED_MATCH_TEXT);
		this.watched.add(watch);
		startAutoRead(process, name, quiet,watch);
		return process;
	}

	
	
	
	protected static class ConsoleWatcher implements IConsoleWatcher
	{
		public static final String FINISHED_MATCH_TEXT = "Simulator status event : FINISHED";

		public boolean matched;

		private String name;
		
		private final String matchString;

		public ConsoleWatcher(String name, String matchString)
		{
			this.name = name;
			this.matchString = matchString;
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
			if (line.contains(matchString))
			{
				matched = true;
			}	
		}
		
		/* (non-Javadoc)
		 * @see eu.compassresearch.core.interpreter.IConsoleWatcher#isMatched()
		 */
		@Override
		public boolean isMatched()
		{
			return matched;
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
	public void testSkip() throws Exception
	{
		String source = "src/test/resources/cosim/skip.cml";
		Process coordinator = setUpCoordinator(source, "P", "A");
		setUpClient(source, "A");

		waitForCompletion(coordinator, DEFAULT_TIMEOUT);

		Assert.assertFalse("Simulators did not finish successfully", isFinished());

	}
	
	@Test
	public void testSkipDual() throws Exception
	{
		String source = "src/test/resources/cosim/skip.cml";
		Process coordinator = setUpCoordinator(source, "P", "A,B");
		setUpClient(source, "A");
		setUpClient(source, "B");

		waitForCompletion(coordinator, DEFAULT_TIMEOUT);

		Assert.assertFalse("Simulators did not finish successfully", isFinished());

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
