package eu.compassresearch.pog.tests;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;
import org.overture.pog.utility.POException;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.pog.tests.utils.TestFileProvider;
import eu.compassresearch.pog.tests.utils.TestInputHelper;
import eu.compassresearch.pog.tests.utils.TestResultHelper;

/**
 * Main class for the new POG test framework.
 * 
 * It's responsible for running the base tests of the POG. These tests consists
 * of micro models that exercise 1 PO at a time (or when that's impossible, the
 * bare minimum).
 * 
 * Results files should also be supplied and so as to make the tests useful.
 * 
 * @author ldc
 * 
 */
@RunWith(Parameterized.class)
public class BasicTests {
	// FIXME Use JSon stuff from Overture POG tests
	private String micromodel;
	private String poresult;
	private String testfolder;

	/**
	 * Constructor for the test. Initialized with parameters from
	 * {@link #testData()}.
	 * 
	 * @param testParameter
	 *            filename for the model to test
	 * @param resultParameter
	 *            test result file
	 */

	public BasicTests(String testFolder, String testParameter,
			String resultParameter) {
		this.testfolder = testFolder;
		this.micromodel = testParameter;
		this.poresult = resultParameter;
	}

	/**
	 * Generate the test data. Actually just fetches it from
	 * {@link TestFileProvider}.
	 * 
	 * @return test data.
	 */
	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> testData() {
		return TestFileProvider.files();
	}

	/**
	 * A poor test. Runs the POG and checks that pos exist
	 */
	@Test
	public void printPOs() throws IOException, AnalysisException {
		String modelpath = testfolder + micromodel;
		String resultpath = testfolder + poresult;

		String testResult;

		testResult = "N/A yet";

		try {
			List<INode> ast = TestInputHelper.getAstFromName(modelpath);
			IProofObligationList polist = PogPubUtil
					.generateProofObligations(ast);

			if (polist.isEmpty()) {
				System.out.println("No proof obligations.");
			}

			for (IProofObligation po : polist) {
				assertNotNull(po);
			}
		}

		catch (POException e) {
			Assert.fail("POG crashed on " + micromodel);
			throw e;
		}

	}

}
