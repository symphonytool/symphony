package eu.compassresearch.core.typechecker.secondedition;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class RunCmlExamplesTestCase extends
		AbstractResultBasedCmlTypeCheckerTestCase
{

	public RunCmlExamplesTestCase(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return collectTestData("../../docs/cml-examples/", TestType.ANY, TestType.ANY.endswith);
	}

	// @Before
	// public void setup()
	// {
	// Properties.recordTestResults = true;
	// }

}
