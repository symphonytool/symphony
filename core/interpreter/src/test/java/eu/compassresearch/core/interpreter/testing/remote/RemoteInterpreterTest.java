package eu.compassresearch.core.interpreter.testing.remote;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.interpreter.InterpretAllCmlFilesTest;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.remote.IRemoteControl;
import eu.compassresearch.core.interpreter.remote.IRemoteInterpreter;
import eu.compassresearch.core.interpreter.remote.RemoteInterpreter;

/**
 * Test class running tests using the {@link IRemoteControl} interpreter feature
 * 
 * @author kel
 */
public class RemoteInterpreterTest extends InterpretAllCmlFilesTest
{

	public RemoteInterpreterTest(String filePath, String name)
	{
		super(filePath, name);
	}

	@Override
	protected void execute(CmlInterpreter interpreter) throws Exception
	{
		IRemoteInterpreter remoteInterpreter = new RemoteInterpreter(interpreter);
		new InternalRemoteTester().run(remoteInterpreter);
	}

	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getCmlfilePaths()
	{

		final String initialPath = "src/test/resources/remoteinterpreter";
		List<Object[]> paths = findAllCmlFiles(initialPath);

		List<Object[]> tests = new Vector<Object[]>();

		for (Object[] p : paths)
		{
			String path = p[0].toString();
			String name = path.substring(initialPath.length() + 1);
			tests.add(new Object[] { path, name });
		}

		return tests;
	}

}
