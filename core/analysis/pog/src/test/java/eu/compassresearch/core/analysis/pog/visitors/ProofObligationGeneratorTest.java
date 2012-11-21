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
import org.overture.pog.obligation.NonZeroObligation;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;

@RunWith(value = Parameterized.class)
public class ProofObligationGeneratorTest {

    private String sourceCode;

    @Before
    public void setup() {

    }

    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();
	Object[] a = new Object[] {
		"class c = begin functions fn: int -> int fn(a) == 50/ a end",
		null };
	r.add(a);
	
	//add test cases for further POs here...
	
	return r;
    }

    public ProofObligationGeneratorTest(String source,
	    CMLProofObligation expectedPO) {
	this.sourceCode = source;
    }

    @Test
    public void testGeneratePOs() throws IOException {
	PSource psAux = (TestUtil.makeSource(sourceCode));
	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
	cmlp.parse();
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();

	// Haven't figured out a way to manually create expected POs (mostly
	// because of ContextStack and LexLocation)
	//For now we test amount and types
	assertEquals(1, actual.size());
	assertEquals(NonZeroObligation.class, actual.get(0).getClass());

    }

}
