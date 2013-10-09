package eu.compassresearch.pog.tests;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.obligations.CmlPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.pog.tests.utils.TestInputHelper;
/**
 * Simple test class to play around with the POG without having
 * to waste time running the IDE tool.
 * @author ldc
 *
 */
public class AdHocTest {

	/**
	 * Main test method. Runs a test on the adhoc/test.cml file. Place whatever 
	 * model you wish to test there. That file is treated as a sandbox so
	 * do not expect anything you place there to remain there.
	 * @throws AnalysisException
	 * @throws IOException
	 */
	@Test
	public void testCmlPog() throws AnalysisException, IOException {
		String file = "src/test/resources/adhoc/test.cml";
		System.out.println("Processing " + file);
		IProofObligationList poList = buildPosFromFile(file);

		System.out.println("\n------------------------");
		System.out.println("POS FOR: "+file);
				
		for (IProofObligation po : poList) {
			System.out.println("------------------------");	
			String preamble = getPreamble(po);
			String pretty = preamble + po.getValueTree().toString();
			System.out.println(pretty);

		}
		System.out.println("--------DONE-----------");

	}

	private String getPreamble(IProofObligation po)
	
	{
		StringBuilder sb = new StringBuilder();
		if (po instanceof CmlProofObligation)
		{
			sb.append(((CmlProofObligation) po).cmltype.toString());
		}
		else {
			sb.append(po.getKind().toString());
		}
		
		sb.append(" obligation // \n");
		return sb.toString();
	}

	private IProofObligationList buildPosFromFile(String file) throws IOException,
			AnalysisException {

		CmlProofObligationList poList = new CmlProofObligationList();
		List<INode> ast = TestInputHelper.getAstFromName(file);
		for (INode node : ast) {
			poList.addAll(node.apply(new ProofObligationGenerator(),
					new CmlPOContextStack()));
		}
		return poList;
	}


}
