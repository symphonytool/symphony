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
import org.junit.Assume;
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
	private final boolean postM12;
	private final boolean expectedParserOk;
	private final boolean expectedTypesOk;

	@Before
	public void setup()
	{

	}


	@Parameters
	public static Collection<Object[]> parameter()
	{

		List<Object[]> testData = new LinkedList<Object[]>();
		// 0
		addTestProgram(testData, "class test = begin values public a:int=42 end",false, true, true); // int literal
		addTestProgram(testData, "class test = begin values public a:boolean =false end",false, true, true); // bool literal
		addTestProgram(testData, "class test = begin values public a:boolean =true end",false, true, true); // bool literal
		addTestProgram(testData, "class test = begin values public a:nat = 1 end",false, true, true); // nat literal
		addTestProgram(testData, "class test = begin values public a:nat1 = 1 end",false, true, true); // nat1 literal
		addTestProgram(testData, "class test = begin values public a:rat = 0.12 end",false, true, true); // rat literal
		addTestProgram(testData, "class test = begin values public a:real = 22/7 end",false, true, true); // real literal
		addTestProgram(testData, "class test = begin values public a:char = 'a' end",false, true, true); // char literal
		addTestProgram(testData, "class test = begin values public a:token = mk_token(\"what up?\")  end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25E+10  end",false, true, true); // Exponent literal
		// 10
		addTestProgram(testData, "class test = begin values public a:int = 1.25E-10  end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25e+10  end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25e-10  end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:seq of int = nil end",false, true, true); // token literal
		// escapes 16
		addTestProgram(testData, "class test = begin values public a:char = '\\\\' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\r' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\n' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\t' end",false, true, true); // token literal
		// 20
		addTestProgram(testData, "class test = begin values public a:char = '\\f' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\e' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\a' end",false, true, true); // token literal
		// 23
		addTestProgram(testData, "class test = begin values public a:char = '\\x61' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\ucc61' end",false, true, true); // token literal
		// 25
		addTestProgram(testData, "class test = begin values public a:char = '\\cc' end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\\"' end",false, true, true); // token literal
		// 27
		addTestProgram(testData, "class test = begin values public a:seq of char  = \"\\\\ \\r \\n \\t \\f \\e \\a \\x61 \\ucc61 \\cd \\\"  \" end",false, true, true); // token literal
		addTestProgram(testData, "class test = begin values public a:char = <Test> end",false, true, true); // token literal

		// let
		addTestProgram(testData, "class test = begin values a : int = let b:int = 42 rem 2 in b * 10 end",false, true, true);
		// if 30
		addTestProgram(testData, "class test = begin values a : int = if (let b:int=10 in b > 10) then 42 else 24 end",false, true, true);
		addTestProgram(testData, "class test = begin values a : boolean = if true then 1 else 0 end",false, true, true);
		addTestProgram(testData, "class test = begin values a : seq of char = if true then \"true\" else \"false\" end",false, true, true);
		addTestProgram(testData, "class test = begin values b : int = 1; a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end",false, true, true);
		// cases 34
		addTestProgram(testData, "class test = begin values a:int = cases 42 : 2 -> 3; 4 -> 5; end end",false, true, true);
		// unary opers
		addTestProgram(testData, "class test = begin values a:int = 2-(-2) end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = abs -42 end ",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = floor 42.3 end ",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = not true end ",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = card {1,3,4,5,1,2,3} end ",false, true, true);
		// 40
		addTestProgram(testData, "class test = begin values a:seq of int = power {1,2,3} end ",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = dunion {{1,2,3},{1,2,3},{3,4,5}} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = dinter {{1,2,3},{1,2,3},{3,4,5}} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = hd [1,2,3] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = tl [true,false] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = len [1,2,3,4] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int  = elems [1,2,3] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = inds [1,2,3,4,5] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = reverse [12,3,4,5] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = conc [ [1],[2],[3] ] end",false, true, true);
		// 50
		addTestProgram(testData, "class test = begin values a:set of int = dom { 1 |-> 42, 2 |-> 42 } end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = rng { 1 |-> 42, 2 |-> 42 } end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = merge { 0 |-> { 1 |-> 42}, 1 |-> { 2 |-> 42 }} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = inverse { 1|->2, 2|->1} end",false, true, true);
		// binary exp
		addTestProgram(testData, "class test = begin values a:int = 10 + 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 10 - 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 10 / 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values b : int = 42 * 42 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 10 mod 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 10 div 10 end",false, true, true);
		// 60
		addTestProgram(testData, "class test = begin values a:int = 10 rem 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 10 + 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 < 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 <= 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 > 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 >= 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 = 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 <> 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = true or false end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = true and false end",false, true, true);
		// 70
		addTestProgram(testData, "class test = begin values a:boolean = true => false end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = true <=> true end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 in set { 1,2,3,4,5,6,7,8,9,0} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = 10 not in set {1,2,3,4} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} subset {1,2,3,4,5} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} psubset {2,3,4,5} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {42} union {5} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3,4} \\ {1,2} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} inter {3,4,5} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = [1,2,3] ^ [4,5,6] end",false, true, true);
		// 80
		addTestProgram(testData, "class test = begin values a:boolean = [1,2,3] ++ [4,5,6] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} ++ {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} munion {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} <: {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} :> {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} :-> {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} comp {5|->6} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:map int to int = {1|->2} ** 1 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = 2 end",false, true, true);
		// quantified expressions
		addTestProgram(testData, "class test = begin values a:boolean = forall i in set {1,2,3} & i+1 > 10 end",false, true, true);
		// 90
		addTestProgram(testData, "class test = begin values a:boolean = exists i in set {1,2,3} & i+1 > 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = exists1 i in set {1,2,3,9} & i+1 > 10 end",false, true, true);
		addTestProgram(testData, "class test = begin values a:boolean = iota i in set {1,2,3} & i+1 > 10 end",false, true, true);
		// set expressions
		addTestProgram(testData, "class test = begin values a:set of int = {1,2,3} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = {a+1 | a in set {1,2,3}} end",false, true, true);
		addTestProgram(testData, "class test = begin values a:set of int = {1,...,3} end",false, true, true);
		// seq expressions 96
		addTestProgram(testData, "class test = begin values a:seq of int = [1,2,3] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = [1,2,3](1) end",true, true, true);
		addTestProgram(testData, "class test = begin values a:int = [ 10 + a | a in set {1,2,3} & a > 1 ] end",false, true, true);
		addTestProgram(testData, "class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} & a > 1 ](1) end",true, true, true);
		// 100
		addTestProgram(testData, "class test = begin values a:set of int = [ 10 + a | a in set {1,2,3} & a > 1 ](1,...,2) end",true, true, true);
		// map expressions
		addTestProgram(testData, "class test = begin values even:map int to boolean = {1|->false, 2|->true, 3|->false, 4|->true}  end",false, true, true);
		addTestProgram(testData, "class test = begin values even:map int to boolean = { |-> }  end",false, true, true);
		addTestProgram(testData, "class test = begin values even:map int to boolean = { a|->b| a in set {1,2,3,4,5}, b in set {1,2,3,4,5} & a <> b } end",false, true, true);
		// tuple
		addTestProgram(testData, "class test = begin values even:int*int = mk_(12,12) end",false, true, true);
		// record expression 105
		addTestProgram(testData, "class test = begin values even:donotexists = mk_donotexists(12,12) end",false, true, true);
		// apply expression
		addTestProgram(testData, "class test = begin values a:int = fn_int_to_int( 10 ) end",false, true, true);
		addTestProgram(testData, "class test = begin values a:int = test.even + 1  end",false, true, true);
		addTestProgram(testData, "class test = begin values one:int = mk_(1,2,3,4).#1 end",true, true, true);
		// lambda expression
		addTestProgram(testData, "class test = begin values add42:int -> int = lambda x:int & x + 42 end",false, true, true);
		// self expressions 110
		addTestProgram(testData, "class test = begin values one:test = self end",false, true, true);
		// is experssion
		addTestProgram(testData, "class test = begin values one:boolean = is_self ( test ) end",false, true, true);
		addTestProgram(testData, "class test = begin values one:boolean = is_(self, test) end",false, true, true);
		// class Membership
		addTestProgram(testData, "class test = begin values one:boolean = isofclass( test, self ) end",false, true, true);

		addTestProgram(testData, "class evil_letters_æøå = begin values even:int = fn_int_to_int( 10 ) end",true, true, true);

		return testData;
	}

	public CmlTypeCheckerTestCase(String cmlSource, boolean postM12, boolean parsesOk, boolean typesOk)
	{
		this.postM12 = postM12;
		this.expectedParserOk = parsesOk;
		this.expectedTypesOk = typesOk;
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin("Test Parameter");
		source.setStream(cmlSourceIn);
		this.source = source;
	}


	@Test 
	public void lexingParsingTest() throws IOException, AnalysisException
	{
		Assume.assumeTrue(!postM12);
		
		CmlParser parser = CmlParser.newParserFromSource(source);
		boolean parserOk = parser.parse();
		Assert.assertTrue("Expected parser to "+(expectedParserOk ? "success" : "fail") + " but it did'nt.", 
				parserOk == expectedParserOk);


		CmlTypeChecker tc = new CmlTypeChecker(parser.getDocument());
		Assert.assertTrue("Expected type checker to "+(expectedParserOk ? "success" : "fail") + " but it did'nt.",
				tc.typeCheck() == expectedTypesOk);

	}
}
