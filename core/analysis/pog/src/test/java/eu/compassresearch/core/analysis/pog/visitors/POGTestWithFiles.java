package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;

@RunWith(value = Parameterized.class)
public class POGTestWithFiles {

    private String filePath;

    @Before
    public void setup() {

    }

    
    public static Object[] alarmProof  ={"src/test/resources/AlarmFromOverture" };
    
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(alarmProof);
	
	//add test cases from further files here...
	
	return r;
    }

    public POGTestWithFiles(String source) {
	this.filePath = source;
    }

    @Test
    public void testGeneratePOsFromFile() throws IOException, AnalysisException {
	PSource psAux = (Utilities.makeSourceFromFile(filePath));
	CmlParser cmlp = CmlParser.newParserFromSource(psAux);
	assertTrue("Test failed on parse.", cmlp.parse());
	
	CmlTypeChecker tc = VanillaFactory.newTypeChecker(
		Arrays.asList(new PSource[] { psAux }), null);
	
	//TODO-ldc add typecheker verification when it's working 100%
	tc.typeCheck();
//	assertTrue("Test Failed on typecheck.", tc.typeCheck()); We cannot properly type check
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	
	//TODO-ldc make asserts and PO comparisons
	//CMLProofObligationList actual = pog.generatePOs(); 
	fail("No asserts yet");

    }
    
    
    
}
