package eu.compassresearch.core.typechecker;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class FunctionsCmlTypeCheckerTests extends
		AbstractResultBasedCmlTypeCheckerTestCase
{
	public FunctionsCmlTypeCheckerTests(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return combine(collectResourcesTestData("functions", TestType.POSITIVE, TestType.COMPARE_RECORDRD), collectResourcesTestData("functions", TestType.NEGATIVE, TestType.COMPARE_RECORDRD));
	}

	@Override
	protected String getPropertyId()
	{
		return "functions";
	}

}
