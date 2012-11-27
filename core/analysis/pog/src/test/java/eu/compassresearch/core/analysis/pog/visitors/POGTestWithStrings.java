package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;

@RunWith(value = Parameterized.class)
public class POGTestWithStrings {

    private String sourceFromString;
    private String filePath;
    private Class<?> expectedPOClass;
    private int posGenerated;

    @Before
    public void setup() {

    }

    
    public static Object[] divZeroFunction ={"class c = begin functions fn: int -> int fn(a) == 50/ a end", NonZeroObligation.class};
    public static Object[] distInterFunction= {"class c = begin functions fn : set of set of int -> set of int fn(ss) == dinter ss end", NonEmptySetObligation.class};
    
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(divZeroFunction);
	r.add(distInterFunction);
	//add test cases for further POs here...
	
	return r;
    }

    public POGTestWithStrings(String source, Class<?> expectedPOClass) {
	this.sourceFromString = source;
	this.expectedPOClass= expectedPOClass;
    }

    @Test
    public void testGeneratePOsfromSource() throws IOException {
	PSource psAux = (TestUtil.makeSource(sourceFromString));
	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
	cmlp.parse();
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();

	// Haven't figured out a way to manually create expected POs (mostly
	// because of ContextStack and LexLocation)
	//For now we test po types
	assertEquals(expectedPOClass, actual.get(0).getClass());

    }
    
    @Test
    public void testGeneratePOsfromFile() throws IOException {
	PSource psAux = (TestUtil.makeSource(sourceFromString));
	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
	cmlp.parse();
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();

	fail("Not yet implemented");
    }

    
    
}
