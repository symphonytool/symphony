package eu.compassresearch.pog.tests;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligationList;
import org.overture.pog.utility.POException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.pog.tests.utils.TestFileProvider;
import eu.compassresearch.pog.tests.utils.TestInputHelper;
import eu.compassresearch.pog.tests.utils.TestResultHelper;

/**
 * Basic tests the new POG. Consist of micro models that exercise 1 PO at a time (or when that's impossible, the bare
 * minimum). Results files should also be supplied and so as to make the tests useful.
 * 
 * @author ldc
 */
@RunWith(Parameterized.class)
public class BasicTest
{
	private String micromodel;
	private String resultpath;

	/**
	 * Constructor for the test. Initialized with parameters from {@link #testData()}.
	 * 
	 * @param testParameter
	 *            filename for the model to test
	 * @param resultParameter
	 *            test result file
	 */

	public BasicTest(String _, String testParameter, String resultParameter)
	{
		this.micromodel = testParameter;
		this.resultpath = resultParameter;
	}

	/**
	 * Generate the test data. Actually just fetches it from {@link TestFileProvider}.
	 * 
	 * @return test data.
	 */
	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData()
	{
		return TestFileProvider.basics();
	}

	/**
	 * A poor test. Runs the POG and checks POs against stored result
	 */
	@Test
	public void testCompare() throws IOException, AnalysisException
	{
		try
		{
			List<INode> ast = TestInputHelper.getAstFromName(micromodel);
			IProofObligationList polist = PogPubUtil.generateProofObligations(ast);

			Gson gson = new Gson();
			String json = IOUtils.toString(new FileReader(resultpath));
			Type datasetListType = new TypeToken<Collection<PoResult>>()
			{
			}.getType();
			List<PoResult> results = gson.fromJson(json, datasetListType);

			TestResultHelper.checkSameElements(results, polist);

		} catch (POException e)
		{
			Assert.fail("POG crashed on " + micromodel);
			throw e;
		}
	}
}
