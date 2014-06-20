package eu.compassresearch.pog.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.pog.tests.utils.TestFileProvider;
import eu.compassresearch.pog.tests.utils.TestResultHelper;

@RunWith(Parameterized.class)
public class BugRegressionTest
{

	private String modelPath;
	private String resultpath;

	@Before
	public void setup()
	{
	}

	public BugRegressionTest(String _, String model, String result)
	{
		this.modelPath = model;
		this.resultpath = result;
	}

	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData()
	{
		return TestFileProvider.bugRegs();
	}

	@Test
	public void testWithCompare() throws AnalysisException, IOException,
			URISyntaxException
	{
		TestResultHelper.testCompare(modelPath, resultpath);
	}

}
