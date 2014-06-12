package eu.compassresearch.core.interpreter;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IssuesTest extends InterpretAllCmlFilesTest
{

	public IssuesTest(String filePath, String name)
	{
		super(filePath, name);
	}
	
	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getCmlfilePaths()
	{
		final String initialPath = "src/test/resources/issues";
		return collectTests(initialPath);
	}

}
