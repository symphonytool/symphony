package eu.compassresearch.core.typechecker.secondedition;

import java.io.File;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class GeneralCmlTypeCheckerTests extends
		AbstractResultBasedCmlTypeCheckerTestCase
{
	public GeneralCmlTypeCheckerTests(File file, String name, TestType type)
	{
		super(file, name, type);
	}

	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getData()
	{
		return combine(collectResourcesTestData("general"),collectResourcesTestData("general",TestType.POSITIVE));
	}

}
