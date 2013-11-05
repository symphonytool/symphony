package eu.compassresearch.core.typechecker;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CompleteCmlTypeCheckerTests extends
		AbstractResultBasedCmlTypeCheckerTestCase
{
	public CompleteCmlTypeCheckerTests(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return collectResourcesTestData("complete", TestType.POSITIVE, TestType.COMPARE_RECORDRD);
	}

	@Override
	protected String getPropertyId()
	{
		return "complete";
	}

}
