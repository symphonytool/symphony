package eu.compassresearch.core.analysis.pog.visitors;

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
import org.overture.pog.IProofObligationList;

import eu.compassresearch.ast.program.PSource;

@RunWith(value = Parameterized.class)
public class POGTestWithFiles {

    private String filePath;

    @Before
    public void setup() {

    }

    
    public static Object[] alarm  ={"src/test/resources/AlarmFromOverture" };
    public static Object[] webServer = {"src/test/resources/WebServerFromOverture"};
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(alarm);
	r.add(webServer);
	r.addAll(Utilities.getCmlExamplesFilePaths());
	//add test cases from further files here...
	
	return r;
    }

    public POGTestWithFiles(String source) {
	this.filePath = source;
    }

    @Test
    public void testGeneratePOsFromFile() throws IOException, AnalysisException {
	System.out.println("Testing on " + filePath);
	PSource psAux = (Utilities.makeSourceFromFile(filePath));
	
	ProofObligationGenerator pog = new ProofObligationGenerator(psAux);
	//TODO-ldc make asserts and PO comparisons
	IProofObligationList actual = pog.generatePOs(); 
	int posGenerated=0;
	if (actual!=null)
	    posGenerated=actual.size();
	System.out.println("Testing finished");
	fail("No asserts yet. "+ posGenerated + " POs generated for "+filePath);
    }
    
    
    
    
}
