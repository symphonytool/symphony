package eu.compassresearch.pog.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.pog.tests.utils.TestInputHelper;

/**
 * Class for testing the POG in terms of preserving the AST.
 * @author ldc
 *
 */
public class AstIntegrityTest
{

	@Test
	public void preservesDwarf() throws IOException, AnalysisException{
		run("src/test/resources/integration/Dwarf.cml");
	}
	
	@Test
	public void preserveAllPOs() throws IOException, AnalysisException{
		run("src/test/resources/integration/AllThePOs.cml");
	}
	
	
	
	private void run(String file) throws IOException, AnalysisException{
		List<INode> ast = TestInputHelper.getAstFromName(file);
		IProofObligationList pol = PogPubUtil.generateProofObligations(ast);
		IProofObligationList pol2 = PogPubUtil.generateProofObligations(ast);
		
		assertEquals("Different number of POs generated.", pol.size(), pol2.size());
	}

}
