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
		return filter(collectTestData("../../docs/cml-examples/", TestType.ANY, TestType.ANY.endswith));
	}

	private static Collection<Object[]> filter(
			Collection<Object[]> collectTestData)
	{
		Object[] r  = null;
		for (Object[] o : collectTestData)
		{
			if(o[0].toString().contains("fail.cml"))
			{
				 r = o;
			}
		}
		
		collectTestData.remove(r);
		return collectTestData;
	}

	// @Before
	// public void setup()
	// {
	// Properties.recordTestResults = true;
	// }

}
