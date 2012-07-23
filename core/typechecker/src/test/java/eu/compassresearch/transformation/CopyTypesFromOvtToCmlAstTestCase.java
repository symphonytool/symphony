package eu.compassresearch.transformation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static eu.compassresearch.transformation.TestUtil.*;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AValueDefinition;
import eu.compassresearch.ast.expressions.AVariableExp;
import eu.compassresearch.ast.node.INode;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.ABooleanBasicType;
import eu.compassresearch.ast.types.AFunctionType;
import eu.compassresearch.ast.types.AIntNumericBasicType;
import eu.compassresearch.ast.types.AOperationType;
import eu.compassresearch.ast.types.ASeqSeqType;
import eu.compassresearch.ast.types.ASetType;
import eu.compassresearch.core.parser.CmlParser;

@RunWith(value=Parameterized.class)
public class CopyTypesFromOvtToCmlAstTestCase {

	
	private String cmlSource;
	private Class<? extends INode> start;
	private PSource source;
	private Class<?> type;


	@Parameters
	public static Collection<Object[]> getParams() throws IOException
	{
		List<Object[]> testData = new LinkedList<Object[]>();
		// testData, cml string, first occurance where analysis will start, expected type
		addTestProgram(testData, "class test = begin functions fn: int -> int fn(a) == a+42; end", AExplicitFunctionDefinition.class, AFunctionType.class);
		addTestProgram(testData, "class test = begin operations oper: bool ==> int oper(b) == return if (b) then 42 else 21 end", AExplicitOperationDefinition.class, AOperationType.class);
		// testData, file name, first occurance where analysis will start, expected type
		addFileProgram(testData, "not.cml", AValueDefinition.class, ABooleanBasicType.class);
		addFileProgram(testData,"binexp_plus.cml", AValueDefinition.class, AIntNumericBasicType.class);
		addFileProgram(testData,"binexp_subtract.cml", AValueDefinition.class, AIntNumericBasicType.class);
		addFileProgram(testData,"binexp_div.cml", AValueDefinition.class, AIntNumericBasicType.class);
		addFileProgram(testData,"binexp_divide.cml", AValueDefinition.class, AIntNumericBasicType.class);
		addFileProgram(testData,"binexp_times.cml", AValueDefinition.class, AIntNumericBasicType.class);
		addFileProgram(testData, "subseq.cml", AValueDefinition.class, ASeqSeqType.class);
		addFileProgram(testData, "applyexp.cml", AValueDefinition.class, AIntNumericBasicType.class);
		return testData;
	}
	

	public CopyTypesFromOvtToCmlAstTestCase(String cmlSource, Class<? extends eu.compassresearch.ast.node.INode> start, Class<?> expectedType) throws Throwable
	{
		this.cmlSource = cmlSource;
		this.start = start;
		this.type=expectedType;
	}
	
	@Before
	public void setup()
	{
		this.source = TestUtil.makeSource(cmlSource);
	}

	
	@Test
	public void test() throws AnalysisException, IOException, org.overture.ast.analysis.AnalysisException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		CmlParser parser = CmlParser.newParserFromSource(source);
		Assert.assertTrue(parser.parse());
		
		CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
		eu.compassresearch.ast.node.INode node = (eu.compassresearch.ast.node.INode)findFirst(start, source);
		org.overture.ast.node.INode ovtNode = node.apply(transform);
		
		CopyTypesFromOvtToCmlAst copier = new CopyTypesFromOvtToCmlAst(transform.getNodeMap());
   	    INode res = copier.defaultINode(ovtNode);
		
   	    Method getType = start.getMethod("getType", new Class<?>[0]);
   	    
   	    Assert.assertNotNull(getType.invoke(res, new Object[0]));
   	    Assert.assertTrue(getType.invoke(res,  new Object[0]).getClass() == type);
	}
}
