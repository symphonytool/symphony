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
import org.overture.ast.analysis.AnalysisException;
import org.overture.pog.obligation.NonEmptySetObligation;
import org.overture.pog.obligation.NonZeroObligation;

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;

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
	cmlp.parse();
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	CMLProofObligationList actual = pog.generatePOs();

	// Haven't figured out a way to manually create expected POs (mostly
	// because of ContextStack and LexLocation)
	//For now we test po types
	fail("No asserts yet");

    }
    
    
    
}
