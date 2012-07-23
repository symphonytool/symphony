package eu.compassresearch.transformation;
import static eu.compassresearch.transformation.TestUtil.*;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.definitions.AExplicitFunctionDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AValueDefinition;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;

@RunWith(value=Parameterized.class)
public class CmlAstToOvertureAstTestCase {

	
	public String getName() { return "Hu";}

	private PSource source;
	private String cmlSource;
	private Class<?> start;
	
	
	@Parameters()
	public static Collection<Object[]> getParams() throws IOException
	{
		List<Object[]> sources = new LinkedList<Object[]>();
		addTestProgram(sources, "class test = begin values public a : int = 42 end", AValueDefinition.class);
		addTestProgram(sources, "class test = begin values public b : seq of char = \"test\" end", AValueDefinition.class);
		addTestProgram(sources, "class test = begin values protected c : bool = false end", AValueDefinition.class);
		addTestProgram(sources, "class test = begin functions fn: int -> int fn(a) == 42 end", AExplicitFunctionDefinition.class);
		addTestProgram(sources, "class test = begin operations o: int ==> int o(n) == return n + 1 end", AExplicitOperationDefinition.class);
		addTestProgram(sources, "class test = begin state a : int operations public test: () ==> test test() == a := 42 end", AExplicitOperationDefinition.class);
		addFileProgram(sources, "not.cml", AValueDefinition.class);
		addFileProgram(sources, "binexp_plus.cml", AValueDefinition.class);
		addFileProgram(sources, "binexp_mod.cml", AValueDefinition.class);
		addFileProgram(sources, "binexp_subtract.cml", AValueDefinition.class);
		addFileProgram(sources, "binexp_times.cml", AValueDefinition.class);
		addFileProgram(sources, "binexp_divide.cml", AValueDefinition.class);
		
		return sources;
	}
	
	public CmlAstToOvertureAstTestCase(String cmlSource, Class<? extends eu.compassresearch.ast.node.INode> start) throws Throwable
	{
		this.cmlSource = cmlSource;
		this.start = start;
	}
	
	@Before
	public void setup()
	{
		this.source = makeSource(cmlSource);
	}
	
	
	
	@Test
	public void convertSmallCmlProgram() throws AnalysisException, IOException {
		
		CmlParser parser = CmlParser.newParserFromSource(source);
		Assert.assertTrue(parser.parse());
		
		CmlAstToOvertureAst transform = new CmlAstToOvertureAst();
		eu.compassresearch.ast.node.INode node = (eu.compassresearch.ast.node.INode)findFirst(start, source);
		node.apply(transform);
	}
	
}
