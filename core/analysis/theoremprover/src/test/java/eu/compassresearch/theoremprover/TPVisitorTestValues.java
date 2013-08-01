package eu.compassresearch.theoremprover;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.PSource;

public class TPVisitorTestValues {

	public static String simpleValuesPath  = "src/test/resources/SimpleValue.cml";
	
	@Test
	public void testBasicValues() throws AnalysisException, IOException{
		List<ThmValue> expRes = new LinkedList<ThmValue>();
		Collections.addAll(expRes, new ThmValue("basic_t_nat", "\\<langle>7\\<rangle>")
		                         , new ThmValue("basic_ut_nat", "\\<langle>5\\<rangle>")
		                         , new ThmValue("basic_str", "''hello''"));
		test(simpleValuesPath, expRes);	
	}

	
	public void test(String filePath, List<ThmValue> expRes)
			throws AnalysisException, IOException {
	//	TPVisitor tpVisitor = new TPVisitor();
	//	PSource ast = TPUtil.makeSourceFromFile(filePath);

	//	ast.apply(tpVisitor);
	//	List<ThmValue> actRes = tpVisitor.getValueList();

	//	for (int i = 0; i < actRes.size(); i++) {
	//		assertEquals(expRes.get(i).toString(), actRes.get(i).toString());
	//	}
		
		

	}

}
