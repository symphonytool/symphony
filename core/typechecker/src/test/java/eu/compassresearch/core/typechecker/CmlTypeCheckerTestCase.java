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
		// 0
		addTestProgram(testData, "class test = begin values public a:int=42 end"); // int literal
		addTestProgram(testData, "class test = begin values public a:boolean =false end"); // bool literal
		addTestProgram(testData, "class test = begin values public a:boolean =true end"); // bool literal
		addTestProgram(testData, "class test = begin values public a:nat = 1 end"); // nat literal
		addTestProgram(testData, "class test = begin values public a:nat1 = 1 end"); // nat1 literal
		addTestProgram(testData, "class test = begin values public a:rat = 0.12 end"); // rat literal
		addTestProgram(testData, "class test = begin values public a:real = 22/7 end"); // real literal
		addTestProgram(testData, "class test = begin values public a:char = 'a' end"); // char literal
		addTestProgram(testData, "class test = begin values public a:token = mk_token(\"what up?\")  end"); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25E+10  end"); // Exponent literal
		// 10
		addTestProgram(testData, "class test = begin values public a:int = 1.25E-10  end"); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25e+10  end"); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 1.25e-10  end"); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end"); // token literal
		addTestProgram(testData, "class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end"); // token literal
		addTestProgram(testData, "class test = begin values public a:seq of int = nil end"); // token literal
		// escapes 16
		addTestProgram(testData, "class test = begin values public a:char = '\\\\' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\r' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\n' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\t' end"); // token literal
		// 20
		addTestProgram(testData, "class test = begin values public a:char = '\\f' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\e' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\a' end"); // token literal
		// 23
		addTestProgram(testData, "class test = begin values public a:char = '\\x61' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\ucc61' end"); // token literal
		// 25
		addTestProgram(testData, "class test = begin values public a:char = '\\cc' end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = '\\\"' end"); // token literal
		// 27
		addTestProgram(testData, "class test = begin values public a:seq of char  = \"\\\\ \\r \\n \\t \\f \\e \\a \\x61 \\ucc61 \\cd \\\"  \" end"); // token literal
		addTestProgram(testData, "class test = begin values public a:char = <Test> end"); // token literal
		
		// let
		addTestProgram(testData, "class test = begin values a : int = let b:int = 42 rem 2 in b * 10 end");
		// if 30
		addTestProgram(testData, "class test = begin values a : int = if (let b:int=10 in b > 10) then 42 else 24 end");
		addTestProgram(testData, "class test = begin values a : boolean = if true then 1 else 0 end");
		addTestProgram(testData, "class test = begin values a : seq of char = if true then \"true\" else \"false\" end");
		addTestProgram(testData, "class test = begin values b : int = 1; a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end");
		// cases 34
		addTestProgram(testData, "class test = begin values a:int = cases 42 : 2 -> 3; 4 -> 5; end end");
		// unary opers
		addTestProgram(testData, "class test = begin values a:int = 2-(-2) end");
		addTestProgram(testData, "class test = begin values a:int = abs -42 end ");
		addTestProgram(testData, "class test = begin values a:int = floor 42.3 end ");
		addTestProgram(testData, "class test = begin values a:boolean = not true end ");
		addTestProgram(testData, "class test = begin values a:int = card {1,3,4,5,1,2,3} end ");
		addTestProgram(testData, "class test = begin values a:seq of int = power {1,2,3} end ");
		addTestProgram(testData, "class test = begin values a:seq of int = dunion {{1,2,3},{1,2,3},{3,4,5}} end");
		addTestProgram(testData, "class test = begin values a:seq of int = dinter {{1,2,3},{1,2,3},{3,4,5}} end");
		addTestProgram(testData, "class test = begin values a:int = hd [1,2,3] end");
		addTestProgram(testData, "class test = begin values a:boolean = tl [true,false] end");
		addTestProgram(testData, "class test = begin values a:int = len [1,2,3,4] end");
		addTestProgram(testData, "class test = begin values a:seq of int  = elems [1,2,3] end");
		addTestProgram(testData, "class test = begin values a:set of int = inds [1,2,3,4,5] end");
		addTestProgram(testData, "class test = begin values a:seq of int = reverse [12,3,4,5] end");
		addTestProgram(testData, "class test = begin values a:seq of int = conc [ [1],[2],[3] ];");
		addTestProgram(testData, "class test = begin values a:set of int = dom { 1 |-> 42, 2 |-> 42 }");
		addTestProgram(testData, "class test = begin values a:set of int = rng { 1 |-> 42, 2 |-> 42 }");
		addTestProgram(testData, "class test = begin values a:set of int = merge { 0 |-> { 1 |-> 42}, 1 |-> { 2 |-> 42 }}");
		addTestProgram(testData, "class test = begin values a:set of int = inverse { 1|->2, 2->1}");
		// binary exp
		addTestProgram(testData, "class test = begin values a:int = 10 + 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 - 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 / 10 end");
		addTestProgram(testData, "class test = begin values b : int = 42 * 42 end");
		addTestProgram(testData, "class test = begin values a:int = 10 mod 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 div 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 rem 10 end");
		addTestProgram(testData, "class test = begin values a:int = 10 + 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 < 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 <= 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 > 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 >= 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 = 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 <> 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = true or false end");
		addTestProgram(testData, "class test = begin values a:boolean = true and false end");
		addTestProgram(testData, "class test = begin values a:boolean = true => false end");
		addTestProgram(testData, "class test = begin values a:boolean = true <=> true end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 in set { 1,2,3,4,5,6,7,8,9,0} end");
		addTestProgram(testData, "class test = begin values a:boolean = 10 not in set {1,2,3,4} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} subset {1,2,3,4,5} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} psubset {2,3,4,5} end");
		addTestProgram(testData, "class test = begin values a:boolean = {42} union {5} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3,4} \\ {1,2} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1,2,3} inter {3,4,5} end");
		addTestProgram(testData, "class test = begin values a:boolean = [1,2,3] ^ [4,5,6] end");
		addTestProgram(testData, "class test = begin values a:boolean = [1,2,3] ++ [4,5,6] end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} ++ {5|->6} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} munion {5|->6} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} <: {5|->6} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} :> {5|->6} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} :-> {5|->6} end");
		addTestProgram(testData, "class test = begin values a:boolean = {1|->2} comp {5|->6} end");
		addTestProgram(testData, "class test = begin values a:map int to int = {1|->2} ** 1 end");
		addTestProgram(testData, "class test = begin values a:int = 2 end");
		// quantified expressions
		addTestProgram(testData, "class test = begin values a:boolean = forall i in set {1,2,3} @ i+1 > 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = exists i in set {1,2,3} @ i+1 > 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = exists1 i in set {1,2,3,9} @ i+1 > 10 end");
		addTestProgram(testData, "class test = begin values a:boolean = iota i in set {1,2,3} @ i+1 > 10 end");
		// set expressions
		addTestProgram(testData, "class test = begin values a:set of int = {1,2,3} end");
		addTestProgram(testData, "class test = begin values a:set of int = {a+1 | a in set 1,2,3} end");
		addTestProgram(testData, "class test = begin values a:set of int = {1,...,3} end");
		// seq expressions
		addTestProgram(testData, "class test = begin values a:set of int = [1,2,3] end");
		addTestProgram(testData, "class test = begin values a:set of int = [ 10 + a | a in set {1,2,3} @ a > 1 ] end");
		addTestProgram(testData, "class test = begin values a:set of int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1,...,2) end");
		// map expressions
		addTestProgram(testData, "class test = begin values even:map int to boolean = {1|->false, 2|->true, 3|->false, 4|->true}  end");
		addTestProgram(testData, "class test = begin values even:map int to boolean = {|->}  end");
		addTestProgram(testData, "class test = begin values even:map int to boolean = { a|->b| a in set {1,2,3,4,5}, b in set {1,2,3,4,5} @ a <> b } end");
		// tuple
		addTestProgram(testData, "class test = begin values even:int*int = mk_(12,12) end");
		// record expression
		addTestProgram(testData, "class test = begin values even:donotexists = mk_donotexists(12,12) end");
		// apply expression
		addTestProgram(testData, "class test = begin values a:int = fn_int_to_int( 10 ) end");
		addTestProgram(testData, "class test = begin values a:int = test.even + 1  end");
		addTestProgram(testData, "class test = begin values one:int = mk_(1,2,3,4).#1 end");
		// lambda expression
		addTestProgram(testData, "class test = begin values add42:int -> int = lambda x @ x + 42 end");
		// self expressions
		addTestProgram(testData, "class test = begin values one:test = self end");
		// is experssion
		addTestProgram(testData, "class test = begin values one:boolean = is_self ( test ) end");
		addTestProgram(testData, "class test = begin values one:boolean = is_(self, test) end");
		// class Membership
		addTestProgram(testData, "class test = begin values one:boolean = isofclass( test, self ) end");
		
		addTestProgram(testData, "class evil_letters_æøå = begin values even:int = fn_int_to_int( 10 ) end");
		
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
		 if (!parser.parse()) throw new RuntimeException("Not parsing");
		
		CmlTypeChecker tc = new CmlTypeChecker(parser.getDocument());
		Assert.assertTrue(tc.typeCheck());
	}
}
