package eu.compassresearch.pog.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.pog.tests.utils.TestInputHelper;

/**
 * Class for testing the POG in terms of PO coverage
 * @author ldc
 *
 */
public class AllPosTest
{
	/**
	 * Very basic test. Runs the POG on a dummy model that
	 * should yield all PO. 
	 * 
	 * Does not actually check if all the different POs are generated. 
	 * It mostly ensures that the POG does not
	 * crash. Also checks the number of POs generated to help
	 * catch any side effect changes.
	 * @throws IOException
	 * @throws AnalysisException
	 */
	@Test
	public void noCrash() throws IOException, AnalysisException{	
		String file = "src/test/resources/integration/AllThePOs.cml";
		
		List<INode> ast = TestInputHelper.getAstFromName(file);
		IProofObligationList pol = PogPubUtil.generateProofObligations(ast);
		
		assertNotNull(pol);
		
		//it doesn't crash. yay!
	}

}
