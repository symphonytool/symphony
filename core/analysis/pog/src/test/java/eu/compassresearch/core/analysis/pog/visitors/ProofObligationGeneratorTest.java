package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
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

import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;


@RunWith(value = Parameterized.class)
public class ProofObligationGeneratorTest {

    private String sourceCode;
    private CMLProofObligation expectedPO;
    
    @Before
    public void setup(){
	
    }
    
    @Parameters
    public static Collection<Object[]> data(){
	List<Object[]> r = new LinkedList<Object[]>();
	Object[] a = new Object[] {"class c = begin functions fn: int -> int fn(a) == 50/ a end", null};
	r.add(a);
	return r;
    }
    
    public ProofObligationGeneratorTest(String source,
	    CMLProofObligation expectedPO) {
	this.sourceCode = source;
	this.expectedPO = expectedPO;
    }

    @Test
    public void testGetAnalysisName() {
	fail("Not yet implemented");
    }

    @Test
    public void testProofObligationGeneratorListOfPSource() {
	fail("Not yet implemented");
    }

    @Test
    public void testProofObligationGeneratorPSource() {
	fail("Not yet implemented");
    }

    @Test
    public void testGeneratePOs() throws IOException {
	PSource psAux = (Utilities.makeSource(sourceCode));
	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
	cmlp.parse();
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();
	
	assertEquals(1, actual.size());
	assertEquals(NonZeroObligation.class, actual.get(0).getClass());
//	for (ProofObligation opo : actual.get(0))
//	assertEquals
	
//	    public ProofObligationGenerator(PSource singleSource)
//	    {
//	        initialize()_original;
//	        this.sourceForest = new LinkedList<PSource>();
//	        this.sourceForest.add(singleSource);
//	    }
//	fail("Not yet implemented");
    }

    @Test
    public void testMain() throws IOException {
	ProofObligationGenerator.main(null);
	fail("Not yet implemented");
    }
    
    

}
