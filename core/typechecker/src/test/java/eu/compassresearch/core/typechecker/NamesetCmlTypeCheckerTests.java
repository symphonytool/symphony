package eu.compassresearch.core.typechecker;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class NamesetCmlTypeCheckerTests extends
		AbstractResultBasedCmlTypeCheckerTestCase
{
	public NamesetCmlTypeCheckerTests(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> getData()
	{
		return combine(collectResourcesTestData("namesets", TestType.POSITIVE, TestType.POSITIVE), collectResourcesTestData("namesets", TestType.NEGATIVE, TestType.NEGATIVE));
	}

	@Override
	protected String getPropertyId()
	{
		return "nameset";
	}

}
