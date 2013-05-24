package eu.compassresearch.theoremprover;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.PSource;

public class TPVisitorTest1 {
	
	public static String compoundTypesPath  = "src/test/resources/CompoundsTypes.cml";
	public static String basicTypesPath  = "src/test/resources/SimpleType.cml";
	
	@Test
	public void testBasicTypes() throws AnalysisException, IOException{
		List<String> expRes = new LinkedList<String>();
		Collections.addAll(expRes, "@nat", "@int", "@char");
		test(basicTypesPath, expRes);	
	}
	
	@Test
	public void testCompoundsTypes() throws AnalysisException, IOException{
		List<String> expRes = new LinkedList<String>();
		Collections.addAll(expRes, "@seq of @nat", "@set of @real", "@seq1 of @char");
		test(compoundTypesPath, expRes);	
	}
	
	

	public void test(String filePath, List<String> expRes) throws AnalysisException, IOException {
		TPVisitor tpVisitor = new TPVisitor();
		PSource ast = TPUtil.makeSourceFromFile(filePath);
		
		ast.apply(tpVisitor);
		List<String> actRes = tpVisitor.getTypeList();
		
		assertEquals(expRes, actRes);
		
	}

}

