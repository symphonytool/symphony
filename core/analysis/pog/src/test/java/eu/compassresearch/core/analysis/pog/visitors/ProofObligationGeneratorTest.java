package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.pog.obligation.NonEmptySetObligation;
import org.overture.pog.obligation.NonZeroObligation;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLNonEmptySetObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLNonZeroObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
//Can't get it to compile - can't find this class??
//import eu.compassresearch.core.typechecker.TestUtil;

@RunWith(value = Parameterized.class)
public class ProofObligationGeneratorTest {

    private String sourceCode;
    private Class<?> expectedPOClass;
    private int posGenerated;

    @Before
    public void setup() {

    }

    
    public static Object[] divZeroFunction ={"class c = begin functions fn: int -> int fn(a) == 50/ a end", CMLNonZeroObligation.class}
    ;
    public static Object[] distInterFunction= {"class c = begin functions fn : set of set of int -> set of int fn(ss) == dinter ss end", CMLNonEmptySetObligation.class};
    
    
    
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(divZeroFunction);
	r.add(distInterFunction);
	//add test cases for further POs here...
	
	return r;
    }

    public ProofObligationGeneratorTest(String source, Class<?> expectedPOClass) {
	this.sourceCode = source;
	this.expectedPOClass= expectedPOClass;
    }

    @Test
    public void testGeneratePOs() throws IOException {
//	PSource psAux = (TestUtil.makeSource(sourceCode));
//	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
//	cmlp.parse();
//	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
//	CMLProofObligationList actual = pog.generatePOs();
//
//	// Haven't figured out a way to manually create expected POs (mostly
//	// because of ContextStack and LexLocation)
//	//For now we test po types
//	assertEquals(expectedPOClass, actual.get(0).getClass());
//
    }

}
