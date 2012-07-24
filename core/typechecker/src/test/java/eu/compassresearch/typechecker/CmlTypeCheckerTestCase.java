package eu.compassresearch.typechecker;

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
		addTestProgram(testData, "class test = begin values public a:int=42 end");
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
