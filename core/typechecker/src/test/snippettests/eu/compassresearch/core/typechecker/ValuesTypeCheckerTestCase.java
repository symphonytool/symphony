package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ValuesTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{
	static
	{
		// 0// int literal
		add("class test = begin values public a:int=42 end");
		// 1// bool literal
		add("class test = begin values public a:bool = false end");
		// 2//
		add("class test = begin values public a:bool =true end");
		// 3//
		add("class test = begin values public a:nat = 1 end");
		// 4//
		add("class test = begin values public a:nat1 = 1 end");
		// 5//
		add("class test = begin values public a:real = 1/3 end");
		// 6//
		add("class test = begin values public a:real = 22/7 end");
		// 7//
		add("class test = begin values public a:char = 'a' end");
		// 8//
		add("class test = begin values public a:token = mk_token(\"what up?\")  end");
		// 9// // Exponent literal
		add("class test = begin values public a:real = 1.25E+10  end");
		// 10//
		add("class test = begin values public a:real = 1.25E-10  end"); // token
																		// literal
		// 11//
		add("class test = begin values public a:real = 1.25e+10  end"); // token
																		// literal
		// 12//
		add("class test = begin values public a:real = 1.25e-10  end"); // token
																		// literal
		// 13//
		add("class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end"); // token
																						// literal
		// 14//
		add("class test = begin values public a:int = 0xDEADBEEF + 0xCAFEBABE end");
		// 15// // token literal
		add("class test = begin values public a:[seq of int] = nil end");
		// 16 //
		add("class test = begin values public a:char = '\\\\' end");
		// 17//
		add("class test = begin values public a:char = '\\r' end");
		// 18//
		add("class test = begin values public a:char = '\\n' end");
		// 19//
		add("class test = begin values public a:char = '\\t' end");
		// 20//
		add("class test = begin values public a:char = '\\f' end");
		// 21//
		add("class test = begin values public a:char = '\\e' end");
		// 22//
		add("class test = begin values public a:char = '\\a' end");
		// 23//
		add("class test = begin values public a:char = '\\x61' end");
		// 24//
		add("class test = begin values public a:char = '\\ucc61' end");
		// 25// Not all escape seq are okay yet
		add("class test = begin values public a:char = '\\cc' end", false, false);
		// 26// escape seq not ready yet
		add("class test = begin values public a:char = '\\\"' end", false, false);
		// 27//escape seq not ready yet
		add("class test = begin values public a:seq of char  = \"\\\\ \\r \\n \\t \\f \\e \\a \\x61 \\ucc61 \\cd \\\"  \" end", true, true);
		// 28//
		add("class test = begin values public a:char = <Test> end", false);
		// 29//
		add("class test = begin values a : int = let b:int = 42 rem 2 in b * 10 end");
		// 30//
		add("class test = begin values a : int = if (let b:int=10 in b > 10) then 42 else 24 end");
		// 31//
		add("class test = begin values a : bool = if true then 1 else 0 end", false);
		// 32//
		add("class test = begin values a : seq of char = if true then \"true\" else \"false\" end");
		// 33//
		add("class test = begin values b : int = 1 a : seq of char = if b = 2 then \"one\" elseif (b = 3) then \"two\" elseif b = 4 then \"three\" else \"above three\" end");
		// 34//
		add("class test = begin values a:int = cases 42 : 2 -> 3, 4 -> 5 end end");
		// 35//
		add("class test = begin values a:int = 2-(-2) end");
		// 36//
		add("class test = begin values a:int = abs -42 end ");
		// 37//
		add("class test = begin values a:int = floor 42.3 end ");
		// 38//
		add("class test = begin values a:bool = not true end ");
		// 39//
		add("class test = begin values a:int = card {1,3,4,5,1,2,3} end ");
		// 40//
		add("class test = begin values a:seq of int = power {1,2,3} end ", false);
		// 41//
		add("class test = begin values a:set of int = dunion {{-1,2,3},{1,2,3},{3,4,5}} end");
		// 42//
		add("class test = begin values a:set of int = dinter {{1,2,3},{1,2,3},{3,4,-5}} end");
		// 43//
		add("class test = begin values a:int = hd [1,2,3] end");
		// 44//
		add("class test = begin values a:bool = hd(tl([true,false])) end");
		// 45//
		add("class test = begin values a:int = len [1,2,3,4] end");
		// 46//
		add("class test = begin values a:set of int  = elems([1,2,3]) end");
		// 47//
		add("class test = begin values a:set of int = inds [1,2,3,4,5] end");
		// 48//
		add("class test = begin values a:seq of int = reverse [12,3,4,5] end");
		// 49//
		add("class test = begin values a:seq of int = conc [ [1],[2],[3] ] end");
		// 50//
		add("class test = begin values a:set of int = dom { 1 |-> 42, 2 |-> 42 } end");
		// 51//
		add("class test = begin values a:set of int = rng { 1 |-> 42, 2 |-> 42 } end");
		// 52//
		add("class test = begin values a:map int to map int to int = merge {  {0 |-> { 1 |-> 42}}, {1 |-> { 2 |-> 42 }}} end");
		// 53//
		add("class test = begin values a:map int to int = inverse { 1|->2, 2|->1} end");
		// 54//
		add("class test = begin values a:int = 10 + 10 end");
		// 55//
		add("class test = begin values a:int = 10 - 10 end");
		// 56//
		add("class test = begin values a:int = 10 div 10 end");
		// 57//
		add("class test = begin values b : int = 42 * 42 end");
		// 58//
		add("class test = begin values a:int = 10 mod 10 end");
		// 59//
		add("class test = begin values a:real = 10 div 10 end");
		// 60//
		add("class test = begin values a:int = 10 rem 10 end");
		// 61//
		add("class test = begin values a:int = 10 + 10 end");
		// 62//
		add("class test = begin values a:bool = 10 < 10 end");
		// 63//
		add("class test = begin values a:bool = 10 <= 10 end");
		// 64//
		add("class test = begin values a:bool = 10 > 10 end");
		// 65//
		add("class test = begin values a:bool = 10 >= 10 end");
		// 66//
		add("class test = begin values a:bool = 10 = 10 end");
		// 67//
		add("class test = begin values a:bool = 10 <> 10 end");
		// 68//
		add("class test = begin values a:bool = true or false end");
		// 69//
		add("class test = begin values a:bool = true and false end");
		// 70//
		add("class test = begin values a:bool = true => false end");
		// 71//
		add("class test = begin values a:bool = true <=> true end");
		add("class test = begin values a:bool = 10 in set { 1,2,3,4,5,6,7,8,9,0} end");
		add("class test = begin values a:bool = 10 not in set {1,2,3,4} end");
		// 74//
		add("class test = begin values a:bool = {1,2,3} subset {1,2,3,4,5} end");
		// 75//
		add("class test = begin values a:bool = {1,2,3} psubset {2,3,4,5} end");
		// 76//
		add("class test = begin values a:set of nat1 = {42} union {5} end");
		// 77//
		add("class test = begin values a:set of int = {1,2,3,4} \\ {1,2} end");
		// 78//
		add("class test = begin values a:set of int = {1,2,3} inter {3,4,5} end");
		// 79//
		add("class test = begin values a:seq of int = [1,2,3] ^ [4,5,6] end");
		// 80//
		add("class test = begin values a:seq of int = [1,2,3] ++ { 1|->4} end");
		// 81//
		add("class test = begin values a:map int to int = {1|->2} ++ {5|->6} end");
		// 82//
		add("class test = begin values a:map int to int = {1|->2} munion {5|->6} end");
		// 83//
		add("class test = begin values a:map int to int = {2} <: {5 |-> 6} end");
		// 84//
		add("class test = begin values a:map int to int =  {5|->6} :> {1} end");
		// 85//
		add("class test = begin values a:map int to int = {1|->2} :-> {5} end");
		// 86//
		add("class test = begin values a:map int to int = {1|->2} comp {5|->6} end");
		// 87//
		add("class test = begin values a:map int to int = {1|->2} ** 1 end");
		// 88//
		add("class test = begin values a:int = 2 end");
		// 89//
		add("class test = begin values a:bool = forall i in set {1,2,3} @ 2+1 > 10 end");
		// 90//
		add("class test = begin values a:bool= exists i in set {1,2,3} @ i+1 > 10 end");
		// 91//
		add("class test = begin values a:bool= exists1 i in set {1,2,3,9} @ i+1 > 10 end");
		// 92//
		add("class test = begin values a:int = iota i in set {1,2,3} @ i+1 > 10 end");
		// 93//
		add("class test = begin values a:set of int = {1,2,3} end");
		// 94//
		add("class test = begin values a:set of int = {a+1 | a in set {1,2,3}} end");
		// 95//
		add("class test = begin values a:set of int = {1,...,3} end");
		// 96//
		add("class test = begin values a:seq of int = [1,2,3] end");
		// 97//
		add("class test = begin values a:int = [1,2,3](1) end");
		// 98//
		add("class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ a > 1 ] end");
		// 99//
		add("class test = begin values a:int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1) end");
		// 100//
		add("class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ a > 1 ](1,...,2) end");
		// 101//
		add("class test = begin values a:seq of int = [ 10 + a | a in set {1,2,3} @ unexpectedid > 1 ](1,...,2) end", true, false);
		// 102//
		add("class test = begin values even:map int to bool = {1|->false, 2|->true, 3|->false, 4|->true}  end");
		// 103//
		add("class test = begin values even:map int to bool = { |-> }  end");
		// 104//
		add("class test = begin values even:map int to bool = { a|->b| a in set {1,2,3,4,5}, b in set {1,2,3,4,5} @ a <> b } end", true, false);
		// 105//
		add("class test = begin values even:int*int = mk_(12,12) end");
		// 106//
		add("class test = begin values even:donotexists = mk_donotexists(12,12) end", true, false);
		// 107//
		add("class test = begin values a:int = fn_int_to_int( 10 ) end", false);
		// 108//
		add("class test = begin values a:int = test.even + 1  end", false);
		// 109//
		add("class test = begin values one:int = mk_(1,2,3,4).#1 end");
		// 110//
		add("class test = begin values add42:int -> int = lambda x:int @ x + 42 end");
		// 111//
		add("class test = begin values one:test = self end", true, false);
		// 112//
		add("class test = begin values one:bool = is_test ( self ) end", true, false);
		// 113//
		add("class test = begin values one:bool = is_(self, test) end", true, false);
		// 114//
		add("class test = begin values one:bool = isofclass( test , self) end", false);
		// 115 TODO: So should we support unicode characters in identifiers?
		add("class evil_letters_æøå = begin values even:int = fn_int_to_int( 10 ) end", false, false);

		add("values a:int = 1 \n b : int = a ");

		add("values k : int = 2 class test = begin state a : int := k end");
	}

	public ValuesTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(ValuesTypeCheckerTestCase.class);
	}

}
