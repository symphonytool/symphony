package eu.compassresearch.core.typechecker.secondedition;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ActionsCmlTypeCheckerTests extends
		AbstractResultBasedCmlTypeCheckerTestCase
{
	public ActionsCmlTypeCheckerTests(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return combine(collectResourcesTestData("actions", TestType.POSITIVE, TestType.COMPARE_RECORDRD), collectResourcesTestData("actions", TestType.NEGATIVE, TestType.COMPARE_RECORDRD));
	}

	@Override
	protected String getPropertyId()
	{
		return "actions";
	}

}
