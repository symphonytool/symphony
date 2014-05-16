package eu.compassresearch.core.parser.test;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IssuesTest extends ParseAllCmlExampleFilesTest
{

	public IssuesTest(String filePath)
	{
		super(filePath);
	}
	
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> getTests()
	{
		return getCmlfilePaths("src/test/resources/issues/");
	}

}
