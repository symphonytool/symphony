package eu.compassresearch.pog.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.pog.tests.utils.TestFileProvider;
import eu.compassresearch.pog.tests.utils.TestInputHelper;

/**
 * All examples Test ensures that the POG does not crash on the standard
 * CML examples. The test sources for these examples need to be
 * manually updated.
 *  
 * 
 * @author ldc
 * 
 */

@RunWith(Parameterized.class)
public class AllExamplesTest {
	
	private String modelpath;

	@Before
	public void setup(){
	}
	


	public AllExamplesTest(String _, String modelpathP) {
		this.modelpath = modelpathP;
	}

	/**
	 * Generate the test data. Actually just fetches it from
	 * {@link BaseInputProvider}.
	 * @return 
	 */
	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData() {
		return TestFileProvider.allExamples();
	}

	// Run the POG to ensure it does not crash
	@Test
	public void testNoCrash() throws IOException, AnalysisException {

		List<INode> ast = TestInputHelper.getAstFromName(modelpath);
		IProofObligationList ipol = PogPubUtil
				.generateProofObligations(ast);
		assertNotNull(ipol);
	}

	


}
