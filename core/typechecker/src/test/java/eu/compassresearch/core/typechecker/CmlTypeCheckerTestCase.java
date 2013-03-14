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
				"class test = begin values public a:real = 1/3 end", false,
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
		// 26
		addTestProgram(testData,
				"class test = begin values public a:char = '\\\"' end", true,
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
				"class test = begin values b : int = 1 a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end",
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
		// 53
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = inverse { 1|->2, 2|->1} end",
				false, true, true, new String[0]);
		// binary exp
		// 54
		addTestProgram(testData,
				"class test = begin values a:int = 10 + 10 end", false, true,
				true, new String[0]);
		// 55
		addTestProgram(testData,
				"class test = begin values a:int = 10 - 10 end", false, true,
			 	true, new String[0]);
		// 56
		addTestProgram(testData,
				"class test = begin values a:int = 10 div 10 end", false, true,
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
				"class test = begin values a:map int to int =  {5|->6} :> {1} end",
				false, true, true, new String[0]);
		// 85
		addTestProgram(
				testData,
				"class test = begin values a:map int to int = {1|->2} :-> {5} end",
				false, true, true, new String[0]);
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
		// 101
		addTestProgram(
				testData,
				"class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ unexpectedid > 1 ](1,...,2) end",
				false, true, false, new String[0]);
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
				false, new String[] { "self not in scope.", "Paragraph \"test\" has type errors." });
		// 112
		addTestProgram(testData,
				"class test = begin values one:bool = is_test ( self ) end",
				false, true, false, new String[] {"self not in scope.","Paragraph \"test\" has type errors."});
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
				false, true, true, new String[0]);
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
				"class d = begin state a:int operations c: int ==> () c(v) == a := v end",
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
				"process A = begin @ Skip end process B = begin @ Skip end process p1 = A [| channel1 |] B",
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
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });
		// 130
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A \\\\ {| channel1 |}) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });
		// 131
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });
		// 132
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A startsby 42) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });
		// 133
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ (A endsby 42) end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });

		// 134
		addTestProgram(
				testData,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				false, true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION_OR_OPERATION
						.customizeMessage("A") });

		// 135
		addTestProgram(
				testData,
				"class test = begin functions public plus: int * int -> int plus(a,b) == (0 + a) + b end",
				false, true, true, new String[0]);
		// 136
		addTestProgram(testData, "values a:int = 1 \n b : int = a ", false, true,
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
				"channels init process test = begin @ init -> Skip end",
				false, true, true, new String[0]);

		// 142
		addTestProgram(
				testData,
				"channels init:int\ntest process test = begin @ init?x -> Skip end",
				false, true, true, new String[0]);
		// 143
		addTestProgram(testData,
				"process P = begin actions INIT = Skip @ INIT end", false,
				true, true, new String[0]);
		// 144
		addTestProgram(testData, "class Thing = begin values public Douglas : int = 42 end class test = begin values a: int = 0 functions g: int -> int g(i) == a + Thing`Douglas end",
				false,true,true,new String[0]);
		
		// 145
		addTestProgram(testData, "class test = begin values a : int = 0	functions f:int -> int f(k) == k+a	end",
				false,true,true,new String[0]);
		
		addTestProgram(testData, "class test = begin operations o:int ==> int o(a) == (return (\"ras\")) end", false,true,false,new String[0]);
		addTestProgram(testData, "class test = begin operations o:int ==> int o(a) == (return (let a : int = 2 in \"42\")) end",false,true,false,new String[0]);
		addTestProgram(testData, "class Stuff = begin functions operations o : () ==> int o() == (return (let a:int = 2 in  \"abcd\")) end",false,true,false,new String[0]);
		addTestProgram(testData, "class test = begin functions f: int -> int f(a) == a+10 end", false, true,true,new String[0]);
		// 150
		addTestProgram(testData, "channels c: String", false, true,false,new String[0]);
		addTestProgram(testData, "class test = begin operations o1:int ==> int o1(a) == let b : int = a+1 in return (c) end", false, true, false, new String[0]);
		addTestProgram(testData, "process A = B ; C process D = begin @ Skip end", false, true, false, new String[0]);
		
		addTestProgram(testData, "class exlicitfunction   = begin types aunion = nat | <None> functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end", false, true,true,new String[0]);
		addTestProgram(testData, "class processafterclass = begin types aunion = nat          functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end process A = A ; B", false, true, false, new String[0]);
		
		addTestProgram(testData, "class implicitFuncTest = begin types Message :: sender: Id destn : Id message : String;	Log :: eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails; Id :: type : (<ERU> | <CC>) identifier : token; ERUId = Id RescueDetails :: criticality : int ; String = seq of char functions compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> []  stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true end", false, true,true, new String[0]);
		
		addTestProgram(testData, "types Id :: 	type : (<ERU> | <CC>) identifier : token; ERUId = Id; Location = token; Criticality = nat inv c == c < 4; --(<Red> | <Yellow> | <Green> | <White>); red = 3, yellow = 2, green = 1, white = 0 String = seq of char;	RescueDetails :: 	target : Location criticality : Criticality;Message :: 	sender: Id	destn : Id message : String; Log :: 	eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails; values	functions  compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> [] stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true ", false, true, true, new String[0]);

		addTestProgram(testData, "functions test: int * int -> bool test(a,b) == true channels InOut: int * int * int process A = begin state b:int actions A = InOut?a!b?c -> test(a,c) @ Skip end", false, true, true, new String[0]);
		addTestProgram(testData, "channels a : int process A =  begin state b : int := 2 actions INIT = a!(b+2) -> Skip @ INIT end", false, true, true, new String[0]);
		addTestProgram(testData, "process A = begin actions B = val n:int @ Skip @ (||| i in set {1,2,3} @ [ { } ] B(i)) end", false, true, true, new String[0]);
		// 159
		addTestProgram(testData, "process A = begin @ A [[ init <- start ]] end ", false, true, true, new String[0]);
		// 160
		addTestProgram(testData, "channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = C C = Skip  @ Skip end",false, true, true, new String[0]);
		// 161
		addTestProgram(testData, "channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = $T(target(C))", false, true, true, new String[0]);
		// 162
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b; return a @ a := o(10) end", false, true, true, new String[0]);
		// 163
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a := b - a else a := 0 ; return a @ a := o(9) end", false, true, true, new String[0]);
		// 164
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif 1 then a := b - a else a := 0 ; return a @ a := o(9) end", false, true, false, new String[0]);
		// 165
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:='a' else a := 0 ; return a @ a := o(9) end", false, true, false, new String[0]);
		// 166
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 'l' ; return a @ a := o(9) end", false, true, false, new String[0]);
		// 167
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 0 ; return a @ a := o('l') end", false, true, false, new String[0]);
		// 168
		addTestProgram(testData, "channels a, b process A = begin actions INIT = (a -> b -> Skip) \\\\ {|b|} @ INIT end", false, true, true , new String[0]);
		// This test checks that an explicit operation body of type nat1 can be declared to return int.
		// 169
		addTestProgram(testData, "channels a, b process A = begin functions test : int +> int test(x) == 2  @ a -> Skip end", false, true, true, new String[0]);
		
		// This test was reported by AKM and checks that functions can be invoked from Guards
		// 170
		addTestProgram(testData, "channels a process A = begin state x : nat := 3 functions isHigherThanTwo : (int) +> bool isHigherThanTwo(y) == y > 2 @ [isHigherThanTwo(x)] & a -> Skip end", false, true, true, new String[0]);
		
		// Test test checks that the declared section of an dcl is checked.
		// 171
		addTestProgram(testData, "process P = begin @ (dcl z:int := \"wrong type\" @ Skip) end", false, true, false, new String[0]);
		
		// This test was reported by AKM and checks that operations cannot be invoked from dcl's
		// 172
		addTestProgram(testData, "channels a : int process A = begin state v : int := 2 operations Test : int ==> int Test(x) == return x + v @ (dcl z : int := test(2) @ a!(z) -> Skip ) end", false, true,false,new String[0]);
		
		// This test checks the positive case for invoking a Cml Operation
		// 173
		addTestProgram(testData, "process K = begin state f : int := 0 operations op1: int ==> int op1(a) == return (a+1) @ f := op1(10) end", false, true, true, new String[0]);
		
		// This test checks that operations cannot be invoked from values
		// 174 //
		addTestProgram(testData, "class test = begin operations op1: int ==> int op1(a) == return (a+1) values k : int = op1(10) end", false, true, false, new String[0]);
		// 175 //
		addTestProgram(testData, "process T = begin functions f(a:int) r:int pre true post true @ f(2) end", false, true, true, new String[0]);
		// 176 //
		addTestProgram(testData, "class test = begin operations o: int ==> int o(a) == return a pre a > 0 post a~ = a end ", false, true, true, new String[0]);
		// 177 //
		addTestProgram(testData, "class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a end", false, true, true, new String[0]);
		// 178 //
		addTestProgram(testData, "class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a and 'b.a' = b~.a end", false, true, false, new String[0]);
		// 179 //
		addTestProgram(testData, "class t = begin types A :: a : int state aa : A operations o:int==>int o(i) == return (aa.a + i) pre i > 0 post 0 = i end", false, true, true, new String[0]);
		// 180 //
		addTestProgram(testData, "process p = begin types A :: a : int state aa:A operations o:int==>int o(i) == return (aa.a + i) post aa~.a = i @ o(2) end",false,true,true,new String[0]);
		// 181
		addTestProgram(testData, "types C = set of int inv c == c = {1,2,3}", false, true, true, new String[0]);
		// 182
		addTestProgram(testData, "class T = begin end class N = begin end types TorN = T | N", false, true, true, new String[0]);
		// 183
		addTestProgram(testData, "process T = begin operations o:int*int ==> int o(a,b) == return a+b @ o(10,10) end", false, true, true, new String[0]);
		// 184
		addTestProgram(testData, "types I = int process T = begin operations o:I*I ==> int o(a,b) == return a+b @ o(10,10) end", false, true, true, new String[0]);
		// 185
		addTestProgram(testData, "types R :: a : int b : int " +
				                  "process P = " +
				                  "  " +
				                  " " +
				                  "begin " +
				                  "  state " +
				                  "    a : int " +
				                  "    k : R   " +
				                  "  actions " +
				                  "    A = cases k : mk_R(a1,b1) -> a:=a1+b1 end" +
				                  " @ A " +
				                  "end", false, true,true, new String[0]);
		// 186
		addTestProgram(testData, "types Value = int ID = nat process P = begin operations CheckMac: Value * Value * ID ==> bool CheckMac(a,b,c) == return (a*b=c) Me: () ==> bool Me() == (dcl a : Value := 2 @ return CheckMac(a,2,4)) @ Skip end ",false,true,true, new String[0]);
		// 187
		addTestProgram(testData, "process K = begin operations A:int*int ==> bool A(a,b) == return (a=b) @ A(2) end", false, true, false, new String[0]);
		// 188
		addTestProgram(testData, "process L = begin operations A:int*int*int ==> bool A(a,b,c) == o(0);return (a+b=c) @ A(1,mk_(0,2)) end",false,true,false, new String[0]);
		// 189
		addTestProgram(testData, "process L = begin state k : int operations K:int*int ==> int K(a,b) == for all i in set {1,2,3} do k := k + o @ Skip end", false, true, false, new String[0]);
		// 190
		addTestProgram(testData, "process L = begin state l : int @ || i in set {1,2,3} @ [{ }] l:=i end",false, true, true, new String[0]);
		// 191
		addTestProgram(testData, "process O = begin state o : int @ for all i in set {1,2,3} do o := o + i end", false, true, true, new String[0]);
		// 192
		addTestProgram(testData, "process M = begin @ Skip end process L = begin @ Stop end process K = || i in set {1,2,3} @ [{ }] (M [| {| inp.k | k in set {5,6,7} |} union {| out.k | k in set {5,6,7} |} |] L)",false, true,true,new String[0]);
		// 193
		addTestProgram(testData, "channels out: nat1 process S1 = || k in set {1,2,3} @ [{ }] begin @ out!k -> Skip end", false, true, true,new String[0]);
		// 194
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {  } |] i in set {1,2,3} @ [{ c1 }] c1!i -> Skip end",false, true, false, new String[0]);
		// 195
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {a } |] i in set {1,2,3} @ [{    }] c1!i -> Skip end",false, true, false, new String[0]);
		// 196
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {c1} |] i in set {1,2,3} @ [{  a }] c1!i -> Skip end",false, true, true, new String[0]);
		// 197
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{   }] Skip end",false, true, true, new String[0]);
		// 198
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {c1, a} |] i in set {1,2,3} @ [{   }] c1!i -> Skip end",false, true, false, new String[0]);
		// 199
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  c1, a }] c1!i -> Skip end",false, true, false, new String[0]);
		// 200
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  a  }] c1!a -> Skip end",false, true, false, new String[0]);
		// 201
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { c1 } |] i in set {1,2,3} @ [{   }] c1!a -> Skip end",false, true, false, new String[0]);
		// 202
		addTestProgram(testData, "process T = begin operations O: () ==> () O() == Skip @ O() end",false, true, true, new String[0]);
		// 203
		addTestProgram(testData, "process A = begin @ let b = 2 in [b = 2] & Skip end",false,true,true,new String[0]);
		// 204
		addTestProgram(testData, "values a : int = 42 process A = begin values b : int = 43 @Skip end", false, true, true, new String[0]);
		// 205
		addTestProgram(testData, "channels c process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end",false, true, false, new String[0]);
		// 207
		addTestProgram(testData, "channels c:nat1 process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end",false, true, true, new String[0]);
		// 208
		addTestProgram(testData,"process K = begin actions INIT = Skip @ INIT() end",false,true,true,new String[0]);
		// 209
		addTestProgram(testData,"process K = begin operations INIT: () ==> () INIT() == Skip @ INIT() end",false,true,true,new String[0]);
		// 210
		addTestProgram(testData,"values m: map int to int = {  1 |-> 2 } process K = begin state l:int functions f:map int to int -> int f(m) == m(42) @ l := f(m) end",false,true,true,new String[0]);
		// 211
		addTestProgram(testData,"values m = {  1 |-> 2 } process K = begin state l:int operations f:map int to int ==> int f(m) == return m(42) @ l := f(m) end",false,true,true,new String[0]);
		// 212
		addTestProgram(testData, "channels a: int types book = token values mbook = { mk_token(\"Book\") |-> 1} process P = begin @  a!(mbook(book))->Skip end", false, true,true, new String[0]);
		// 213
		addTestProgram(testData, "types k = int channels a class A = begin end", false, true, true, new String[0]);
		// 214
		addTestProgram(testData, "types S = seq of char RescueDetails::k : int functions rescueDetailsToString(r : RescueDetails) s: S post s <> [] process P = begin actions MERGE2 = val eru: ERUId @ (dcl s: S,r:RescueDetails @ s := rescueDetailsToString(r); Skip ) @ Skip end", false, true, true, new String[0]);
		// 215
		addTestProgram(testData, "channels c1: int process P = begin actions A = val r : int @ c1!r -> Skip @ Skip end", false, true, true, new String[0]);
		// 216
		addTestProgram(testData, "types Id ::   type : (<ERU> | <CC>) identifier : token ERUId = Id Location = token Criticality = nat inv c == c < 4 String = seq of char RescueDetails :: target : Location criticality : Criticality process CallCentreProc = begin state erus: set of ERUId eruRescues: map ERUId to RescueDetails inv dom eruRescues subset erus and (forall i in set erus @ i.type = <ERU>) operations reAllocateERU(eru : ERUId, r : RescueDetails) frame wr eruRescues : map ERUId to RescueDetails rd erus: set of ERUId pre eru in set erus and eru in set dom eruRescues and eruRescues(eru) <> r post eru in set dom eruRescues and eruRescues(eru) = r actions FORK1 = (dcl eru : ERUId @ (dcl r : RescueDetails @ (dcl oldr: RescueDetails @  reAllocateERU(eru,r)))) @ Skip end",false,true,true,new String[0]);
		// 217
		addTestProgram(testData, "types SUBS = token STATUS = ( <ringing> | <speech> | <suspended>) values Connected = {<ringing>,<speech>,<suspended>} functions connected: (map SUBS to STATUS) * (map SUBS to SUBS) +> (inmap SUBS to SUBS) connected(status,number) == {} free: (map SUBS to STATUS) * (map SUBS to SUBS) * (set of SUBS) +> (set of SUBS) free(status,number,subs) == subs \\ dom(status) \\ rng(connected(status,number)) class Exchange = begin end", false, true, true, new String[0]);
		// 218
		addTestProgram(testData, "channels a:int process P = ||| i in set {1,2,3} @ begin @ a!i -> Skip end",false,true,true,new String[0]);
		// 219
		addTestProgram(testData, "class T = begin state a : int functions f:int * int -> int f(x,y) == a + x + y end", false, true, false, new String[0]);
		// 220
		
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

		TestUtil.TypeCheckerResult res = TestUtil.runTypeChecker(source);
		
		boolean parserOk = res.parsedOk;
		if (!parserOk)
		{
		StringBuilder parseMessages = new StringBuilder();
		for(String s : res.parseErrors)
			parseMessages.append(s);
		Assert.assertSame(parseMessages.toString(),this.expectedParserOk, parserOk);
		}
		
		
		TypeIssueHandler errors = res.issueHandler;

		boolean typeCheckOk = res.tcOk;
		
		assertEquals(TestUtil.buildErrorMessage(errors, expectedTypesOk), expectedTypesOk, typeCheckOk);

		if (parserOk && errorMessages != null && errorMessages.length > 0) {

			Set<String> actualErrors = new HashSet<String>();
			for (CMLTypeError e : errors.getTypeErrors())
				actualErrors.add(e.getDescription());

			for (String s : errorMessages)
				Assert.assertTrue("Expected error message:\n" + s
						+ "\nBut it was not found.", actualErrors.contains(s));
		}

	}

}
