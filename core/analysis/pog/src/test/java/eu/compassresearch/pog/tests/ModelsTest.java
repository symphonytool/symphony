package eu.compassresearch.pog.tests;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.pog.tests.utils.TestFileProvider;

/**
 * Integrations tests the new POG. Runs the POG on test models, mostly extracted from the
 * COMPASs project. Results files should also be supplied and so as to make the tests useful.
 * 
 * @author ldc
 */
@RunWith(Parameterized.class)
public class ModelsTest extends BasicTest
{
	public ModelsTest(String _, String testParameter, String resultParameter)
	{
		super(_, testParameter, resultParameter);
	}


	/**
	 * Generate the test data. Actually just fetches it from {@link TestFileProvider}.
	 * 
	 * @return test data.
	 */
	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> modelstestData()
	{
		return TestFileProvider.models();
	}

	

}
