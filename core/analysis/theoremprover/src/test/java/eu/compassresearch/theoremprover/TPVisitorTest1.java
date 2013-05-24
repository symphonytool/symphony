package eu.compassresearch.theoremprover;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.PSource;

public class TPVisitorTest1 {
	
	public static String simpleTypePath  = "src/test/resources/SimpleType.cml";
	
	@Test
	public void testSimpleType() throws AnalysisException, IOException{
		test(simpleTypePath);
	}
	
	
	
	

	public void test(String filePath) throws AnalysisException, IOException {
		TPVisitor tpVisitor = new TPVisitor();
		PSource ast = TPUtil.makeSourceFromFile(filePath);
		
		ast.apply(tpVisitor);
		
		List<String> expRes = new LinkedList<String>();		
		expRes.add("basic");
		expRes.add("basic2");
		
		List<String> actRes = tpVisitor.getTypeList();
		
		assertEquals(expRes, actRes);
		
	}

}

