package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.secondedition.TestConverter;

@RunWith(value = Parameterized.class)
public class TypesCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{
	static
	{
		// 0//
		add("types A = real");
		// 1//
		add("types A = rat");
		// 2//
		add("types A = int");
		// 3//
		add("types A = nat");
		// 4//
		add("types A = nat1");
		// 5//
		add("types A = char");
		// 6//
		add("types A = bool");
		// 7//
		add("types A = token");
		// 8//
		add("types A = (token)");
		// 9//
		add("types A = <SomeQuote>");
		// 10//
		add("types A = compose rec of real rat int nat nat1 char bool (token) token <SomeQuote> end");
		// 11//
		add("types A = compose rec of a:real b:rat c:int end");
		// 12// Negative test, infinite type
		add("/*Negative test, infinite type*/ types A = compose rec of c:A end", true, false, true);
		// 13//
		add("types A = token | real | rat | int | nat | nat1 | char | bool | (token) | <SomeQuote>");
		// 14//
		add("types A = token * real * rat");
		// 15//
		add("types A = [compose rec of k:int end]");
		// 16//
		add("types A = set of int");
		// 17//
		add("types A = seq of int");
		// 18//
		add("types A = seq1 of int");
		// 19//
		add("types A = map int to rat");
		// 20//
		add("types A = inmap int to int");
		// 21//
		add("types B = int A = B");
		// 22//
		add("types R  :: a : int b : rat");
		// 23//
		add("types K :: a :- int");
		// 24//
		add("types F = int -> int");
		// 25//
		add("types F = int +> int");
		// 26// TODO RWL: TC Cannot handle classes in type defs at top level
		add("/*TODO RWL: TC Cannot handle classes in type defs at top level*/ class C = begin state a : int end types A = C");
		// 27//
		add("types A = int B = A | real");
		// 28//
		add("types A = int class test = begin state k:A end");
		// 29// -- complex example taken from Emergency Response Case Study
		add("/*complex example taken from Emergency Response Case Study*/ types Id :: type : (<ERU> | <CC>) identifier : token ERUId = Id Location = token Criticality = nat inv c == c < 4 String = seq of char RescueDetails :: target : Location criticality : Criticality Message ::sender: Id	destn : Id message : String Log :: 	eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails values	functions  compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> [] stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true ", true, true, false);
		// /--------------------------------------------------\
		// | Types with invariants |
		// \--------------------------------------------------/
		// 30//
		add("types R = real inv r == r > 3.1415 and r < 19.82", true, true, false);
		// 31//
		add("types R = rat inv r == r > 22/7", true, true, false);
		// 32//
		add("types I = int inv i == i < 0", true, true, false);
		// 33//
		add("types N = nat inv n == n <= 255", true, true, false);
		// 34//
		add("types N = nat1 inv n == n <= 1", true, true, false);
		// 35//
		add("types C = char inv c == c = 'a'", true, true, false);
		// 36//
		add("types B = bool inv b == b and true", true, true, false);
		// 37//
		add("types T = token inv t == t <> mk_token(\"42\")", true, true, false);
		// 38//
		add("types Q1 = <A> Q = <A> inv q == is_Q1(q)", true, true, false);
		// 39//
		add("types T = compose REC of a:int b:rat end inv t == t.a < t.b", true, true, false);
		// 40//
		add("types A = int | token inv a == a > 0", true, true, false);
		// 41//
		add("types T = int * rat * real inv t == t.#1 > 0 and t.#2 < 22/7 and 3.1415", true, false);
		// 42//
		add("types T=[int] inv t == t <> nil", true, true, false);
		// 43//
		add("types A = set of int inv s == card s > 0", true, true, false);
		// 44//
		add("types A = seq of int inv s == len s > 0", true, true, false);
		// 45//
		add("types A = seq1 of int inv a == len a < 100", true, true, false);
		// 46//
		add("types A = map int to int inv m == dom(m) <> {}", true, true, false);
		// 47//
		add("types A = map int to int inv m == rng(m) <> {}", true, true, false);
		// 48//
		add("types A = inmap int to int inv im == card rng(im) > 0", true, true, false);
		// 49//
		add("types A = map int to int B = A inv b == card dom(b) > 0", true, true, false);
		// 50// Failing because of LexNameToken conflict
		add("/*Failing because of LexNameToken conflict*/ types R :: r : int inv r == r.r>0", true, true, false);
		// 51// set type w. invariant
		add("/*set type w. invariant*/ types C = set of int inv c == c = {1,2,3}", true, true, false);
		// 52 //Negative test: Check for duplicated type defs
		add("/*Negative test: Check for duplicated type defs*/ types A = nat A = bool B = bool B = rat", true, false);
		// 53 //Multiple type defs
		add("/*Multiple type defs*/ types A = nat B = bool C = bool");
		// 54 Ticket: #89 TypeChecker too aggressive with tuple selection
		add("/*Ticket: #89 TypeChecker too aggressive with tuple selection*/ types A = (int*int) | int inv a == a.#1 >0", true, true, false);
		// 55 public visibility automatically assigned to B in global types
		add("/*public visibility automatically assigned to B in global types*/ types public A = nat B = map nat to nat process C = begin functions public F : A * B -> bool F(a, b) == true @ Skip end ", true, true, false);

		// 56 Negative test: use of public keyword in global types
		 add("/*Negative test: use of public keyword in global types*/ types public A = nat public B = map nat to nat process C = begin functions public F : A * B -> bool F(a, b) == true @ Skip end ",
		 true, true, false);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(TypesCmlTypeCheckerTestCase.class);
	}

	public TypesCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}
	
	
	static int index = 0;
	static final  String SUITE_NAME = "types";
	
	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, String[] err)
	{
		AbstractTypeCheckerTestCase.add(stack,src,parseok,tcok,err);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, boolean expectNoWarnings, String[] err)
	{
		AbstractTypeCheckerTestCase.add(stack,src,parseok,tcok,expectNoWarnings,err);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoWarnings, String[] err, String name)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,expectNoWarnings,err,name);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			String[] err, String name)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,err,name);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoParam)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,expectNoParam);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src)
	{
		add(4, src, true, true, true, new String[0]);
	}

	protected static void add(String src, boolean tcok)
	{
		add(4, src, true, tcok, true, new String[0]);
	}

	protected static void add(String src, boolean tcok, String[] errmsg)
	{
		add(4, src, true, tcok, true, errmsg);
	}
	

}
