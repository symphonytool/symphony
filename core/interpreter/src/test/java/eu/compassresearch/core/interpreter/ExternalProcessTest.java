package eu.compassresearch.core.interpreter;

import java.io.BufferedReader;
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

import eu.compassresearch.core.interpreter.cosim.communication.Utils;

public class ExternalProcessTest
{
	
	protected static interface IConsoleWatcher
	{

		public abstract void match(String line);

		public abstract boolean isMatched();

	}

	protected static final int DEFAULT_TIMEOUT = 60 * 1000;
	protected boolean quiet = false;
	protected Set<Process> processes = new HashSet<Process>();
	protected Integer port = null;
	Set<IConsoleWatcher> watched = new HashSet<IConsoleWatcher>();

	public static int findAvailablePort(int fromPort, int toPort)
	{
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



	protected void waitForCompletion(final Process coordinator, final int timeout)
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

	protected boolean isFinished()
	{
		boolean result = true;
		for (IConsoleWatcher watch : watched)
		{
			result &= watch.isMatched();
		}
		return result;
	}

	protected static void startAutoRead(Process serverProcess, final String name, final boolean quiet,final IConsoleWatcher... watch)
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
						if (!quiet)
						{
							System.out.println("Stdout(" + name + "): " + line);
						}
	
						for (IConsoleWatcher w : watch)
						{
							w.match(line);
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

	public ExternalProcessTest()
	{
		super();
	}
	
	@Before
	public void setUp()
	{
		processes = new HashSet<Process>();
		port = findAvailablePort(7000, 9000);
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

}