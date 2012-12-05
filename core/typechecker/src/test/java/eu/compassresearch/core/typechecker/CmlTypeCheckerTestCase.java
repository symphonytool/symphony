package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
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
		// 11
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25e+10  end",
				false, true, true, new String[0]); // token literal
		// 12
		addTestProgram(testData,
				"class test = begin values public a:real = 1.25e-10  end",
				false, true, true, new String[0]); // token literal
		// 13
		addTestProgram(
				testData,
				"class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",
				false, true, true, new String[0]); // token literal
		// 14
		addTestProgram(
				testData,
				"class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end",
				false, true, true, new String[0]); // token literal
		// 15
		addTestProgram(testData,
				"class test = begin values public a:[seq of int] = nil end",
				false, true, true, new String[0]); // token literal
		// 16
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
		// 28
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
		// 33
		addTestProgram(
				testData,
				"class test = begin values b : int = 1; a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end",
				false, true, true, new String[0]);
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
		// 41
		addTestProgram(
				testData,
				"class test = begin values a:set of int = dunion {{-1,2,3},{1,2,3},{3,4,5}} end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:set of int = dinter {{1,2,3},{1,2,3},{3,4,-5}} end",
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
		// 51
		addTestProgram(
				testData,
				"class test = begin values a:set of int = rng { 1 |-> 42, 2 |-> 42 } end",
				false, true, true, new String[0]);
		// 52
		addTestProgram(
				testData,
				"class test = begin values a:map int to map int to int = merge {  {0 |-> { 1 |-> 42}}, {1 |-> { 2 |-> 42 }}} end",
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
		// 81
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} ++ {5|->6} end",
				false, true, true, new String[0]);
		// 82
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} munion {5|->6} end",
				false, true, true, new String[0]);
		// 83
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {2} <: {5 |-> 6} end",
				false, true, true, new String[0]);
		// 84
		addTestProgram(testData,
				"class test = begin values a:seq of int=  [1] :>  {5|->6} end",
				false, true, true, new String[0]);
		// 85
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} :-> {5} end",
				false, true, false, new String[0]);
		// 86
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} comp {5|->6} end",
				false, true, true, new String[0]);
		// 87
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} ** 1 end",
				false, true, true, new String[0]);
		// 88
		addTestProgram(testData, "class test = begin values a:int = 2 end",
				false, true, true, new String[0]);
		// 89
		addTestProgram(
				testData,
				"class test = begin values a:bool = forall i in set {1,2,3} @ 2+1 > 10 end",
				false, true, true, new String[0]);
		// 90
		addTestProgram(
				testData,
				"class test = begin values a:bool= exists i in set {1,2,3} @ i+1 > 10 end",
				false, true, true, new String[0]);
		// 91
		addTestProgram(
				testData,
				"class test = begin values a:bool= exists1 i in set {1,2,3,9} @ i+1 > 10 end",
				false, true, true, new String[0]);
		// 92
		addTestProgram(
				testData,
				"class test = begin values a:int = iota i in set {1,2,3} @ i+1 > 10 end",
				false, true, true, new String[0]);
		// 93 set expressions
		addTestProgram(testData,
				"class test = begin values a:set of int = {1,2,3} end", false,
				true, true, new String[0]);
		// 94
		addTestProgram(
				testData,
				"class test = begin values a:set of int = {a+1 | a in set {1,2,3}} end",
				false, true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:set of int = {1,...,3} end",
				false, true, true, new String[0]);
		// 96
		addTestProgram(testData,
				"class test = begin values a:seq of int = [1,2,3] end", false,
				true, true, new String[0]);
		addTestProgram(testData,
				"class test = begin values a:int = [1,2,3](1) end", false,
				true, true, new String[0]);
		// 98
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ a > 1 ] end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1) end",
				false, true, true, new String[0]);
		// 100
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1,...,2) end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} unexpectedid a > 1 ](1,...,2) end",
				false, false, true, new String[0]);
		// map expressions
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = {1|->false, 2|->true, 3|->false, 4|->true}  end",
				false, true, true, new String[0]);
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = { |-> }  end",
				false, true, true, new String[0]);
		// 104
		addTestProgram(
				testData,
				"class test = begin values even:map int to bool = { a|->b| a in set {1,2,3,4,5}, b in set {1,2,3,4,5} @ a <> b } end",
				false, true, false, new String[0]);
		// 105
		addTestProgram(testData,
				"class test = begin values even:int*int = mk_(12,12) end",
				false, true, true, new String[0]);
		// record expression
		addTestProgram(
				testData,
				"class test = begin values even:donotexists = mk_donotexists(12,12) end",
				false, true, false, new String[0]);
		// 107
		addTestProgram(testData,
				"class test = begin values a:int = fn_int_to_int( 10 ) end",
				false, true, false, new String[0]);
		// 108
		addTestProgram(testData,
				"class test = begin values a:int = test.even + 1  end", false,
				true, false, new String[0]);
		// 109
		addTestProgram(testData,
				"class test = begin values one:int = mk_(1,2,3,4).#1 end",
				false, true, true, new String[0]);
		// 110
		addTestProgram(
				testData,
				"class test = begin values add42:int -> int = lambda x:int @ x + 42 end",
				false, true, true, new String[0]);
		// 111
		addTestProgram(testData,
				"class test = begin values one:test = self end", false, true,
				false, new String[0]);
		// 112
		addTestProgram(testData,
				"class test = begin values one:bool = is_self ( test ) end",
				false, true, false, new String[0]);
		// 113
		addTestProgram(testData,
				"class test = begin values one:bool = is_(self, test) end",
				false, true, false, new String[0]);
		// 114
		addTestProgram(
				testData,
				"class test = begin values one:bool = isofclass( test , self) end",
				false, true, false, new String[0]);

		// 115 TODO: So should we support unicode characters in identifiers?
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
		addTestProgram(
				testData,
				"class test = begin functions fn: int -> int fn(a) == a + 2 end",
				false, true, false, new String[0]);
		// 118
		addTestProgram(
				testData,
				"class c = begin operations o:int ==> int o(n) == (return (1)) end",
				false, true, true, new String[0]);
		// 119
		addTestProgram(
				testData,
				"class c = begin state a:int := 10 operations o:int ==> int o(n) == (return (a + n)) end",
				false, true, true, new String[0]);
		// 120
		addTestProgram(
				testData,
				"class c = begin state a:int operations c: int ==> c c(v) == a := v end",
				false, true, true, new String[0]);

		// '--------------------------------------------------------------------------
		// | Processes
		// \--------------------------------------------------------------------------
		// 121
		addTestProgram(testData, "process p1 = begin @ Skip end", false, true,
				true, new String[0]);
		// 122
		addTestProgram(testData, "process p1 = a:int @ begin @ Skip end",
				false, true, true, new String[0]);
		// 123 // A is a process and cannot be in action
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				false, true, false, new String[0]);
		// 124 // A is a process and cannot be in an action
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A [] Skip end",
				false, true, false, new String[0]);
		// 125
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A |~| Skip end",
				false, true, false, new String[0]);
		// 126
		addTestProgram(
				testData,
				"process A = begin @ skip end process B = begin @ skip end process p1 = A [| channel1 |] B",
				false, true, false, new String[0]); // negative as channel1 is
													// undefined TC
		// 127
		addTestProgram(
				testData,
				"process A = begin @ Skip end process B = begin @ Skip end process p1 = A [ channel1 || channel2 ] B",
				false, true, false, new String[0]); // negative as channel1 and
													// channel2 are
		// undefined TC should find that
		// 128
		addTestProgram(testData,
				"process A = begin @ Skip end process p1 = A || Skip", true,
				false, true, new String[0]);
		// 129
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A \\\\ {| B |}) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 130
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A \\\\ {| channel1 |}) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 131
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 132
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A startby 42) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 133
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A endsby 42) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });

		// 134
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });

		// 135
		addTestProgram(
				testData,
				"class test = begin functions public plus: int * int -> int plus(a,b) == (0 + a) + b end",
				false, true, false, new String[0]);
		// 136
		addTestProgram(testData, "values a:int = 1; b : int = a ", false, true,
				true, new String[0]);

		// 137
		addTestProgram(testData, "process p1 = begin @ UnknownAction end",
				false, true, false, new String[0]);

		// ------------------ TYPES Section -------------------------
		// 138
		addTestProgram(testData, "types A = int; B = A | real", false, true,
				true, new String[0]);
		// 139
		addTestProgram(testData,
				"types A = int; class test = begin state k:A end", false, true,
				true, new String[0]);

		// 140
		addTestProgram(testData,
				"values k : int = 2 class test = begin state a : int := k end",
				false, true, true, new String[0]);

		// 141
		addTestProgram(testData,
				"channels init process test = begin @ [init] -> Skip end",
				false, true, true, new String[0]);

		// 142
		addTestProgram(
				testData,
				"channels init:int\ntest process test = begin @ [init?x] -> Skip end",
				false, true, true, new String[0]);

		addTestProgram(testData,
				"process P = begin actions INIT = Skip @ INIT end", false,
				true, true, new String[0]);

		return testData;
	}

	static int no = 0;

	public CmlTypeCheckerTestCase(String cmlSource, boolean postM12,
			boolean parsesOk, boolean typesOk, String[] errorMessages) {
		this.postM12 = postM12;
		this.expectedParserOk = parsesOk;
		this.expectedTypesOk = typesOk;
		this.errorMessages = errorMessages;
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin("Test #" + (no++));
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

		TypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
		VanillaCmlTypeChecker tc = new VanillaCmlTypeChecker(
				parser.getDocument(), errors);

		boolean typeCheckOk = tc.typeCheck();
		assertEquals(TestUtil.buildErrorMessage(errors, expectedTypesOk), expectedTypesOk, typeCheckOk);

		if (parserOk && errorMessages != null && errorMessages.length > 0) {
			int expectedNumberOfErrors = errorMessages.length;
			int actualNumberOfErrors = tc.getTypeErrors().size();
			Assert.assertEquals("Wrong number of error. Expected "
					+ expectedNumberOfErrors + " Actual "
					+ actualNumberOfErrors, expectedNumberOfErrors,
					actualNumberOfErrors);

			Set<String> actualErrors = new HashSet<String>();
			for (CMLTypeError e : tc.getTypeErrors())
				actualErrors.add(e.getDescription());

			for (String s : errorMessages)
				Assert.assertTrue("Expected error message:\n" + s
						+ "\nBut it was not found.", actualErrors.contains(s));
		}

	}

}
