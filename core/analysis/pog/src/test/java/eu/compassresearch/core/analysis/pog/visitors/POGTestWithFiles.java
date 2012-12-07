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

import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@RunWith(value = Parameterized.class)
public class POGTestWithFiles {

    private String filePath;

    @Before
    public void setup() {

    }

    
    public static Object[] alarm  ={"src/test/resources/AlarmFromOverture" };
    public static Object[] webServer = {"src/test/resources/WebServerFromOverture"};
    public static Object[] telephoneExchange = {"src/test/resources/TelephoneExchange"};
    public static Object[] cashDispenser = {"src/test/resources/CashDispenser"};
    
  //add test cases for further POs here...
	

    
    @Parameters
    public static Collection<Object[]> data() {
	List<Object[]> r = new LinkedList<Object[]>();

	r.add(alarm);
	r.add(webServer);
	r.add(telephoneExchange);
	r.add(cashDispenser);
	r.addAll(Utilities.getParserTestFilePaths());
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
	CMLProofObligationList actual = pog.generatePOs(); 
	int posGenerated=0;
	if (actual!=null)
	    posGenerated=actual.size();
	System.out.println("Testing finished");
	fail("No asserts yet. "+ posGenerated + " POs generated for "+filePath);
    }
    
    
    
    
}
