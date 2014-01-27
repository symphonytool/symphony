package eu.compassresearch.core.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.compassresearch.core.interpreter.cosim.communication.Utils;

public class CoSimulationIntegrationTest
{
	private static final int DEFAULT_TIMEOUT = 30 * 1000;
	private boolean quit = false;
	Set<ConsoleWatcher> watched = new HashSet<ConsoleWatcher>();

	Set<Process> processes = new HashSet<Process>();
	
	Integer port=null;

	@Before
	public void setUp()
	{
		processes = new HashSet<Process>();
		port = findAvailablePort(7000, 9000);
	}
	
	public static int findAvailablePort(int fromPort, int toPort) {
		if (fromPort > toPort) {
			throw new IllegalArgumentException(
					"startPortShouldBeLessThanOrEqualToEndPort");
		}

		int port = fromPort;
		ServerSocket socket = null;
		while (port <= toPort) {
			try {
				socket = new ServerSocket(port);
				return port;
			} catch (IOException e) {
				++port;
			} finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}

		return -1;
	}

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

	@After
	public void tearDown() throws Exception
	{
		for (Process p : processes)
		{
			try
			{
				p.destroy();
			} catch (Exception e)
			{

			}
		}
	}

	private void waitForCompletion(final Process coordinator, final int timeout)
			throws InterruptedException
	{
		Thread timer = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Utils.milliPause(timeout);
				coordinator.destroy();
				Assert.fail("Simulation timeout reached. Value = " + timeout);
			}
		});

		timer.setDaemon(true);
		timer.start();
		coordinator.waitFor();
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

	private boolean isFinished()
	{
		boolean result = true;
		for (ConsoleWatcher watch : watched)
		{
			result &= watch.finished;
		}
		return result;
	}

	private static class ConsoleWatcher
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

	}

	protected static void startAutoRead(Process serverProcess,
			final String name, final ConsoleWatcher watch, final boolean quit)
	{
		InputStream stdout = serverProcess.getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
		new Thread(new Runnable()
		{
			String line;

			@Override
			public void run()
			{
				try
				{
					while ((line = reader.readLine()) != null)
					{
						if (!quit)
						{
							System.out.println("Stdout(" + name + "): " + line);
						}

						if (line.contains(watch.FINISHED_MATCH_TEXT))
						{
							watch.finished = true;
						}
					}
				} catch (IOException e)
				{
				}
			}
		}).start();
	}

	public static Process startSecondJVM(Class<?> main, String[] args)
			throws Exception
	{
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String path = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		List<String> arguments = new Vector<String>();

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(true);
		arguments.addAll(Arrays.asList(new String[] { path, "-cp", classpath,
				main.getName() }));
		arguments.addAll(Arrays.asList(args));
		processBuilder.command(arguments);
		Process process = processBuilder.start();

		// process.waitFor();
		return process;
	}

}
