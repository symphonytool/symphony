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
		List<ThmType> expRes = new LinkedList<ThmType>();
		Collections.addAll(expRes, new ThmTypeAbbrev("basic1", "@nat")
		                         , new ThmTypeAbbrev("basic2", "@int")
		                         , new ThmTypeAbbrev("basic3", "@char"));
		test(basicTypesPath, expRes);	
	}
	
	@Test
	public void testCompoundsTypes() throws AnalysisException, IOException{
		List<ThmType> expRes = new LinkedList<ThmType>();
		Collections.addAll(expRes, new ThmTypeAbbrev("seqnat", "@seq of @nat")
								 , new ThmTypeAbbrev("setreal", "@set of @real")
		                         , new ThmTypeAbbrev("seq1char", "@seq1 of @char"));
		test(compoundTypesPath, expRes);	
	}
	
	

	public void test(String filePath, List<ThmType> expRes) throws AnalysisException, IOException {
	//	TPVisitor tpVisitor = new TPVisitor();
	//	PSource ast = TPUtil.makeSourceFromFile(filePath);
		
	//	ast.apply(tpVisitor);
	//	List<ThmType> actRes = tpVisitor.getTypeList();
		
	//	assertEquals(expRes.toString(), actRes.toString());
		
	}

}

