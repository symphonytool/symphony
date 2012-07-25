package eu.compassresearch.core.typechecker;

import static eu.compassresearch.transformation.TestUtil.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.definitions.AValueDefinition;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.types.ABooleanBasicType;
import eu.compassresearch.ast.types.AIntNumericBasicType;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.CmlTypeChecker;
import eu.compassresearch.transformation.TestUtil;

import junit.framework.TestCase;

@RunWith( value = Parameterized.class)
public class CmlTypeCheckerTestCase extends TestCase {

	
	private PSource source;
	
	@Before
	public void setup()
	{
		
	}
	
	@Parameters
	public static Collection<Object[]> parameter()
	{
		List<Object[]> testData = new LinkedList<Object[]>();
		// let expressions
		addTestProgram(testData, "class test = begin values a : int = let b:int = 42 rem 2 in b * 10 end");
		addTestProgram(testData, "class test = begin values a : int = let b:int = 10 divide 2, c: int = 10 divide 5 in b * c end");
		addTestProgram(testData, "class test = begin values a : int = if (let b:int=10 in b > 10) then 42 else 24 end");
		addTestProgram(testData, "class test = begin values a : boolean = if true then 1 else 0 end");
		addTestProgram(testData, "class test = begin values a : seq of char = if true then \"true\" else \"false\" end");
		addTestProgram(testData, "class test = begin values b:int=2, a : seq of char = if b = 1 then \"one\" elseif b = 2 then \"two\" elseif b = 3 then \"three\" else \"above three\" end");
		addTestProgram(testData, "class test = begin values a:int = cases 1 : 1 -> 2 2 -> 3 other -> 666 end end");
		// int expressions
		addTestProgram(testData, "class test = begin values public a:int=42 end"); // int literal
		addTestProgram(testData, "class test = begin values a:int = 10 + 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 - 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 / 10 end");
		addTestProgram(testData, "class test = begin values b : int = 42 * 42 end"); // times
		addTestProgram(testData, "class test = begin values a:int = 10 mod 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 div 10 end");
		
		addTestProgram(testData, "class test = begin functions public b: int -> int b(a) == a+42 end");
		return testData;
	}
	
	public CmlTypeCheckerTestCase(String cmlSource)
	{
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin("Test Parameter");
		source.setStream(cmlSourceIn);
		this.source = source;
	}
	
	
	@Test 
	public void lexingParsingTest() throws IOException, AnalysisException
	{
		CmlParser parser = CmlParser.newParserFromSource(source);
		Assert.assertTrue(parser.parse());
		
		CmlTypeChecker tc = new CmlTypeChecker(parser.getDocument());
		Assert.assertTrue(tc.typeCheck());
	}
}
