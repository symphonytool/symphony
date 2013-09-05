package eu.compassresearch.core.analysis.theoremprover;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import isabelle.Outer_Syntax;
import isabelle.Session;
import isabelle.Thy_Load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

public class IsabelleTheoryTests {

	public static int isabelleTimeout = 30000;
	public static int proofTimeout = 5000;
    public static int waitInterval = 200;
	
	public static String emptyThyPath  = "src/test/resources/Empty.thy";
	public static String twoLemmasThyPath  = "src/test/resources/TwoLemmas.thy";

	public Session startIsabelleHOL() throws InterruptedException {
		System.out.print("Isabelle/HOL loading...");
		Session s = IsabelleTheory.createHOLSession();
		int t = isabelleTimeout;
		while (!s.is_ready() && t > 0) {
			System.out.print(".");
			Thread.sleep(waitInterval);
			t -= waitInterval;
		}
		System.out.println("done");
		return s;
	}
	
	@Test
	public void testEmptyThy() throws AnalysisException, IOException{
		Session s = IsabelleTheory.createHOLSession();
		
		IsabelleTheory ithy = new IsabelleTheory(s, "Empty", "src/test/resources");
		
		test(emptyThyPath, ithy);	
	}

	@Test
	public void startStopIsabelle() throws AnalysisException, IOException, InterruptedException{
		Session s = startIsabelleHOL();
		s.stop();
	}

	@Test
	public void createIsabelleTheory() throws AnalysisException, IOException, InterruptedException{
		Session s = startIsabelleHOL();
		IsabelleTheory ithy = new IsabelleTheory(s, "Empty", "src/test/resources");
		ithy.init();
		s.stop();
	}

	
	
	
	@Test
	public void testTwoLemmas() throws AnalysisException, IOException, InterruptedException {		
		Session s = startIsabelleHOL();
		
		IsabelleTheory ithy = new IsabelleTheory(s, "TwoLemmas", "src/test/resources");
		ithy.init();
		ithy.addThm(ithy.new IsabelleTheorem("Lemma1", "True", "simp"));
		ithy.addThm(ithy.new IsabelleTheorem("Lemma2", "False", "simp"));
		
		int t = proofTimeout;
		
		System.out.print("Waiting for proof to complete...");
		while (!ithy.thmIsProved("Lemma1") && t > 0)
		{			
			System.out.print(".");
			Thread.sleep(waitInterval);
			t -= waitInterval;
		}
		
		System.out.println("done");
		
		boolean l1 = ithy.thmIsProved("Lemma1");		
		boolean l2 = ithy.thmIsProved("Lemma2");
		
		assertTrue(l1);
		assertFalse(l2);

		s.stop();
		
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
