package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.pog.obligation.NonEmptySetObligation;
import org.overture.pog.obligation.NonZeroObligation;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;

@RunWith(value = Parameterized.class)
public class POGTestWithStrings {

    private String sourceFromString;
    private String expectedPO;
    
    @Before
    public void setup() {

    }

    private static String divZeroPO = "";
    public static Object[] divZeroFunction ={  "class Alice = begin functions bob: int -> int bob(a) == 50/a end", divZeroPO};
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(divZeroFunction);
//	r.add(distInterFunction);
	//add test cases for further POs here...
	
	return r;
    }

    public POGTestWithStrings(String source, String expectedPO) {
	this.sourceFromString = source;
	this.expectedPO= expectedPO;
    }

    @Test
    public void testGeneratePOsfromSource() throws IOException, AnalysisException {
	PSource psAux = (Utilities.makeSourceFromString(sourceFromString));
	
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();

	// Haven't figured out a way to manually create expected POs (mostly
	// because of ContextStack and LexLocation)
	//For now we test po types
	assertEquals(expectedPO, actual.get(0).toString());

    }


    
    
}
