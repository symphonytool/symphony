package eu.compassresearch.theoremprover;

import static org.junit.Assert.assertEquals;

import isabelle.Outer_Syntax;
import isabelle.Session;
import isabelle.Thy_Load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

public class IsabelleTheoryTests {

	public static String emptyThyPath  = "src/test/resources/Empty.thy";

	@Test
	public void testEmptyThy() throws AnalysisException, IOException{
		Session sess = new Session(new Thy_Load(null, Outer_Syntax.empty()));
		
		IsabelleTheory ithy = new IsabelleTheory(sess, "Empty", "src/test/resources");
		
		test(emptyThyPath, ithy);	
	}

	
	public void test(String filePath, IsabelleTheory ithy)
			throws AnalysisException, IOException {
    	BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = null;
    	StringBuilder sb = new StringBuilder();
    	String ls = System.getProperty("line.separator");

        while( ( line = br.readLine() ) != null ) {
            sb.append(line);
            sb.append(ls);
        }
		
        br.close();
        
        assertEquals(sb.toString(), ithy.toString());

	}

	
}
