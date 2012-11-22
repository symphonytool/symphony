package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;

@RunWith(value = Parameterized.class)
public class CmlTypeCheckerTestCase extends TestCase {

	private PSource source;
	private final boolean postM12;
	private final boolean expectedParserOk;
	private final boolean expectedTypesOk;
	private String[] errorMessages;

	@Before
	public void setup() {

	}

	@Parameters
	public static Collection<Object[]> parameter() {

		List<Object[]> testData = new LinkedList<Object[]>();

		// /--------------------------------------------------------------------------
		// | Context free expressions
		// \--------------------------------------------------------------------------
		// 0
		addTestProgram(testData,
				"class test = begin values public a:int=42 end", false, true,
				true, new String[0]); // int
		// literal
		addTestProgram(testData,
				"class test = begin values public a:bool = false end", false,
				true, true, new String[0]); // bool literal
		addTestProgram(testData,
				"class test = begin values public a:bool =true end", false,
				true, true, new String[0]); // bool literal
		addTestProgram(testData,
				"class test = begin values public a:nat = 1 end", false, true,
				true, new String[0]); // nat
		// literal
		addTestProgram(testData,
				"class test = begin values public a:nat1 = 1 end", false, true,
				true, new String[0]); // nat1 literal
		// 5
		addTestProgram(testData,
				"class test = begin values public a:rat = 1/3 end", false,
				true, true, new String[0]); // rat literal
		addTestProgram(testData,
				"class test = begin values public a:real = 22/7 end", false,
				true, true, new String[0]); // real literal
		addTestProgram(testData,
				"class test = begin values public a:char = 'a' end", false,
				true, true, new String[0]); // char literal
		addTestProgram(
				testData,
				"class test = begin values public a:token = mk_token(\"what up?\")  end",
				false, true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25E+10  end",
				false, true, true, new String[0]); // Exponent literal
		// 10
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25E-10  end",
				false, true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25e+10  end",
				false, true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25e-10  end",
				false, true, true, new String[0]); // token literal
		addTestProgram(
				testData,
				"class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",
				false, true, true, new String[0]); // token literal
		addTestProgram(
				testData,
				"class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",
				false, true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:seq of int = nil end",
				false, true, true, new String[0]); // token literal
		// escapes 16
		addTestProgram(testData,
				"class test = begin values public a:char = '\\\\' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\r' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\n' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\t' end", false,
				true, true, new String[0]); // token literal
		// 20
		addTestProgram(testData,
				"class test = begin values public a:char = '\\f' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\e' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\a' end", false,
				true, true, new String[0]); // token literal
		// 23
		addTestProgram(testData,
				"class test = begin values public a:char = '\\x61' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\ucc61' end",
				false, true, true, new String[0]); // token literal
		// 25
		addTestProgram(testData,
				"class test = begin values public a:char = '\\cc' end", false,
				true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = '\\\"' end", false,
				true, true, new String[0]); // token literal
		// 27
		addTestProgram(
				testData,
				"class test = begin values public a:seq of char  = \"\\\\ \\r \\n \\t \\f \\e \\a \\x61 \\ucc61 \\cd \\\"  \" end",
				false, true, true, new String[0]); // token literal
		addTestProgram(testData,
				"class test = begin values public a:char = <Test> end", false,
				true, false, new String[0]); // token literal

		// let
		addTestProgram(
				testData,
				"class test = begin values a : int = let b:int = 42 rem 2 in b * 10 end",
				false, true, true, new String[0]);
		// if 30
		addTestProgram(
				testData,
				"class test = begin values a : int = if (let b:int=10 in b > 10) then 42 else 24 end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a : bool = if true then 1 else 0 end",
				false, true, false, new String[0]);
		// 32
		addTestProgram(
				testData,
				"class test = begin values a : seq of char = if true then \"true\" else \"false\" end",
				false, true, true, new String[0]);
		// 33 TODO: RWL Fix this
		addTestProgram(
				testData,
				"class test = begin values b : int = 1; a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end",
				true, true, true, new String[0]);
		// cases 34
		addTestProgram(
				testData,
				"class test = begin values a:int = cases 42 : 2 -> 3, 4 -> 5 end end",
				false, true, true, new String[0]);
		// unary opers
		addTestProgram(testData,
				"class test = begin values a:int = 2-(-2) end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = abs -42 end ", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = floor 42.3 end ", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = not true end ", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = card {1,3,4,5,1,2,3} end ",
				false, true, true, new String[0]);
		// 40
		addTestProgram(testData,
				"class test = begin values a:seq of int = power {1,2,3} end ",
				false, true, false, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = dunion {{-1,2,3},{1,2,3},{3,4,5}} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = dinter {{1,2,3},{1,2,3},{3,4,-5}} end",
				false, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = hd [1,2,3] end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = hd(tl([true,false])) end",
				false, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = len [1,2,3,4] end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:set of int  = elems([1,2,3]) end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = inds [1,2,3,4,5] end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = reverse [12,3,4,5] end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = conc [ [1],[2],[3] ] end",
				false, true, true, new String[0]);
		// 50
		addTestProgram(
				testData,
				"class test = begin values a:set of int = dom { 1 |-> 42, 2 |-> 42 } end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = rng { 1 |-> 42, 2 |-> 42 } end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = merge {  {0 |-> { 1 |-> 42}}, {1 |-> { 2 |-> 42 }}} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = inverse { 1|->2, 2|->1} end",
				false, true, true, new String[0]);
		// binary exp
		addTestProgram(testData,
				"class test = begin values a:int = 10 + 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = 10 - 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = 10 / 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values b : int = 42 * 42 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = 10 mod 10 end", false, true,
				true, new String[0]);
		// 59
		addTestProgram(testData,
				"class test = begin values a:real = 10 div 10 end", false,
				true, true, new String[0]);
		// 60
		addTestProgram(testData,
				"class test = begin values a:int = 10 rem 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = 10 + 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 < 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 <= 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 > 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 >= 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 = 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = 10 <> 10 end", false, true,
				true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = true or false end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = true and false end", false,
				true, true, new String[0]);
		// 70
		addTestProgram(testData,
				"class test = begin values a:bool = true => false end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:bool = true <=> true end", false,
				true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:bool = 10 in set { 1,2,3,4,5,6,7,8,9,0} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:bool = 10 not in set {1,2,3,4} end",
				false, true, true, new String[0]);
		// 74
		addTestProgram(
				testData,
				"class test = begin values a:bool = {1,2,3} subset {1,2,3,4,5} end",
				false, true, true, new String[0]);
		// 75
		addTestProgram(
				testData,
				"class test = begin values a:bool = {1,2,3} psubset {2,3,4,5} end",
				false, true, true, new String[0]);
		// 76
		addTestProgram(testData,
				"class test = begin values a:set of nat1 = {42} union {5} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = {1,2,3,4} \\ {1,2} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = {1,2,3} inter {3,4,5} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [1,2,3] ^ [4,5,6] end",
				false, true, true, new String[0]);
		// 80
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [1,2,3] ++ { 1|->4} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} ++ {5|->6} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} munion {5|->6} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {2} <: {5 |-> 6} end",
				false, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:seq of int=  [1] :>  {5|->6} end",
				false, true, true, new String[0]);
		// 85
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} :-> {5} end",
				false, true, false, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} comp {5|->6} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} ** 1 end",
				false, true, true, new String[0]);
		addTestProgram(testData, "class test = begin values a:int = 2 end",
				false, true, true, new String[0]);
		// quantified expressions
		addTestProgram(
				testData,
				"class test = begin values a:bool = forall i in set {1,2,3} @ 2+1 > 10 end",
				false, true, true, new String[0]);
		// 90 TODO: Fix this
		addTestProgram(
				testData,
				"class test = begin values a:bool= exists i in set {1,2,3} @ i+1 > 10 end",
				true, true, true, new String[0]);
		// 91 TODO: Fix this
		addTestProgram(
				testData,
				"class test = begin values a:bool= exists1 i in set {1,2,3,9} @ i+1 > 10 end",
				true, true, true, new String[0]);
		// 92 TODO Fix this
		addTestProgram(
				testData,
				"class test = begin values a:int = iota i in set {1,2,3} @ i+1 > 10 end",
				true, true, true, new String[0]);
		// 93 set expressions
		addTestProgram(testData,
				"class test = begin values a:set of int = {1,2,3} end", false,
				true, true, new String[0]);
		// 94 TODO: Fix this
		addTestProgram(
				testData,
				"class test = begin values a:set of int = {a+1 | a in set {1,2,3}} end",
				true, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:set of int = {1,...,3} end",
				false, true, true, new String[0]);
		// seq expressions 96
		addTestProgram(testData,
				"class test = begin values a:seq of int = [1,2,3] end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:seq of int = [1,2,3](1) end",
				true, true, true, new String[0]);
		// 98 TODO
		addTestProgram(
				testData,
				"class test = begin values a:int = [ 10 + a | a in set {1,2,3} @ a > 1 ] end",
				true, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1) end",
				true, true, true, new String[0]);
		// 100
		addTestProgram(
				testData,
				"class test = begin values a:set of int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1,...,2) end",
				true, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = [ 10 + a | a in set {1,2,3} unexpectedid a > 1 ](1,...,2) end",
				true, false, true, new String[0]);
		// map expressions
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = {1|->false, 2|->true, 3|->false, 4|->true}  end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = { |-> }  end",
				false, true, true, new String[0]);
		// 104 TODO: Fix this
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = { a|->b| a in set {1,2,3,4,5}, b in set {1,2,3,4,5} @ a <> b } end",
				true, true, true, new String[0]);
		// tuple
		// TODO 105
		addTestProgram(testData,
				"class test = begin values even:int*int = mk_(12,12) end",
				true, true, true, new String[0]);
		// record expression
		addTestProgram(
				testData,
				"class test = begin values even:donotexists = mk_donotexists(12,12) end",
				false, true, false, new String[0]);
		// apply expression
		// 107
		addTestProgram(testData,
				"class test = begin values a:int = fn_int_to_int( 10 ) end",
				true, true, false, new String[0]);
		// 108 TODO
		addTestProgram(testData,
				"class test = begin values a:int = test.even + 1  end", true,
				true, false, new String[0]);
		addTestProgram(testData,
				"class test = begin values one:int = mk_(1,2,3,4).#1 end",
				true, true, true, new String[0]);
		// lambda expression 110
		// 110 TODO
		addTestProgram(
				testData,
				"class test = begin values add42:int -> int = lambda x:int @ x + 42 end",
				true, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values one:test = self end", false, true,
				false, new String[0]);
		// is expression
		// 112 TODO Overture assistant chokes
		addTestProgram(testData,
				"class test = begin values one:bool = is_self ( test ) end",
				true, true, true, new String[0]);
		// 113 TODO Overture assistant chokes
		addTestProgram(testData,
				"class test = begin values one:bool = is_(self, test) end",
				true, true, true, new String[0]);
		// class Membership
		// 114 TODO Reference to the name of a Class inside it.
		addTestProgram(
				testData,
				"class test = begin values one:bool = isofclass( test, self ) end",
				false, true, false, new String[0]);

		// 115
		addTestProgram(
				testData,
				"class evil_letters_æøå = begin values even:int = fn_int_to_int( 10 ) end",
				true, true, true, new String[0]);

		// /--------------------------------------------------------------------------
		// | Expressions with context
		// \--------------------------------------------------------------------------
		// 116
		addTestProgram(
				testData,
				"class c = begin functions fn: int -> int fn(a) == \"Wrong type\" + a end",
				false, true, false, new String[0]);
		// 117
		// TODO Function arguments are making trouble
		addTestProgram(
				testData,
				"class test = begin functions fn: int -> int fn(a) == a + 2 end",
				false, true, false, new String[0]);
		// 118
		addTestProgram(
				testData,
				"class c = begin state public a : int; operations o:int ==> int o(n) == (skip) end",
				true, true, true, new String[0]);
		// 119
		addTestProgram(
				testData,
				"class c = begin state a:int operations o:int ==> int o(n) == (return (a + n)) end",
				true, true, true, new String[0]);
		// 120
		addTestProgram(
				testData,
				"class c = begin state a:int; operations c: int ==> c c(v) == a := v end",
				true, true, true, new String[0]);

		// '--------------------------------------------------------------------------
		// | Processes
		// \--------------------------------------------------------------------------
		// 121
		addTestProgram(testData, "process p1 = begin @ skip end", true, true,
				true, new String[0]);
		// 122
		addTestProgram(testData, "process p1 = a:int @ begin @ skip end", true,
				true, true, new String[0]);
		// 123
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A ; skip end",
				true, true, true, new String[0]);
		// 124
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A [] skip end",
				true, true, true, new String[0]);
		// 125
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A |~| skip end",
				true, true, true, new String[0]);
		// 126
		// TODO: Hard Exception (illegal argument in constructors)
		addTestProgram(
				testData,
				"process A = begin @ skip end process B = begin @ skip end process p1 = A [| channel1 |] B",
				true, true, false, new String[0]); // negative as channel1 is
													// undefined TC
		// should
		// find that
		// 127
		// TODO: Hard Exception (illegal argument in constructors)
		addTestProgram(
				testData,
				"process A = begin @ Skip end process B = begin @ Skip end process p1 = A [ channel1 || channel2 ] B",
				true, true, false, new String[0]); // negative as channel1 and
													// channel2 are
		// undefined TC should find that
		// 128
		// TODO: Hard Exception (illegal argument in constructors)
		addTestProgram(testData,
				"process A = begin @ Skip end process p1 = A || Skip", true,
				true, true, new String[0]);
		// 129
		// TODO: Hard Exception (illegal argument in constructors)
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A / 42 \\ skip end",
				true, true, true, new String[0]);
		// 130
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A \\ channel1 end",
				true, true, true, new String[0]);
		// 131
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A ; skip end",
				true, true, true, new String[0]);
		// 132
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A startsby 42 end",
				true, true, true, new String[0]);
		// 133
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A endsby 42 end",
				true, true, true, new String[0]);

		// 134
		addTestProgram(
				testData,
				"process A = begin @ skip end process p1 = begin @ A ; skip end",
				true, true, true, new String[0]);

		// 135 TODO: Function arguments are handled wrong
		addTestProgram(
				testData,
				"class test = begin functions public plus: int * int -> int plus(a,b) == (0 + a) + b end",
				false, true, false, new String[0]);
		// 136 TODO: Fix this top level values referencing eachother
		addTestProgram(testData, "values a:int = 1; b : int = a ", false, true,
				true, new String[0]);

		return testData;
	}

	public CmlTypeCheckerTestCase(String cmlSource, boolean postM12,
			boolean parsesOk, boolean typesOk, String[] errorMessages) {
		this.postM12 = postM12;
		this.expectedParserOk = parsesOk;
		this.expectedTypesOk = typesOk;
		this.errorMessages = errorMessages;
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin("Test Parameter");
		source.setStream(cmlSourceIn);
		this.source = source;
	}

	@Test
	public void lexParseTypeCheck() throws IOException, AnalysisException {
		Assume.assumeTrue(!postM12);

		CmlParser parser = CmlParser.newParserFromSource(source);
		boolean parserOk = parser.parse();
		Assert.assertTrue("Expected parser to "
				+ (expectedParserOk ? "success" : "fail") + " but it did'nt.",
				parserOk == expectedParserOk);

		VanillaCmlTypeChecker tc = new VanillaCmlTypeChecker(
				parser.getDocument(), VanillaFactory.newCollectingIssueHandle());

		boolean typeCheckOk = tc.typeCheck();
		assertEquals(buildErrorMessage(tc), expectedTypesOk, typeCheckOk);

	}

	private String buildErrorMessage(VanillaCmlTypeChecker tc) {
		StringBuilder sb = new StringBuilder();
		if (expectedTypesOk) {
			sb.append("Expected type checking to be successful, the following errors were unexpected:\n");
			for (CMLTypeError error : tc.getTypeErrors())
				sb.append(error.toString() + "\n------\n");
		} else {
			sb.append("Expected type checking to fail but it was successful.");
		}
		return sb.toString();
	}
}
