package eu.compassresearch.core.typechecker;



import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class TypesCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase{
	static {
		//0//
		add("types A = real");
		//1//
		add("types A = rat");
		//2//
		add("types A = int");
		//3//
		add("types A = nat");
		//4//
		add("types A = nat1");
		//5//
		add("types A = char");
		//6//
		add("types A = bool");
		//7//
		add("types A = token");
		//8//
		add("types A = (token)");
		//9//
		add("types A = <SomeQuote>");
		//10//
		add("types A = compose rec of real rat int nat nat1 char bool (token) token <SomeQuote> end");
		//11//
		add("types A = compose rec of a:real b:rat c:int end");
		//12//
		add("types A = compose rec of c:A end");
		//13//
		add("types A = token | real | rat | int | nat | nat1 | char | bool | (token) | <SomeQuote>");
		//14//
		add("types A = token * real * rat");
		//15//
		add("types A = [compose rec of k:int end]");
		//16//
		add("types A = set of int");
		//17//
		add("types A = seq of int");
		//18//
		add("types A = seq1 of int");
		//19//
		add("types A = map int to rat");
		//20//
		add("types A = inmap int to int");
		//21//
		add("types B = int A = B");
		//22//
		add("types R  :: a : int b : rat");
		//23//
		add("types K :: a :- int");
		//24//
		add("types F = int -> int");
		//25//
		add("types F = int +> int");
		//26//
		add("class C = begin state a : int end types A = C");
		//27//
		add("types A = int B = A | real");
		//28//
		add("types A = int class test = begin state k:A end");
		//29// -- complex example taken from Emergency Response Case Study
		add("types Id :: type : (<ERU> | <CC>) identifier : token ERUId = Id Location = token Criticality = nat inv c == c < 4 String = seq of char RescueDetails :: target : Location criticality : Criticality Message ::sender: Id	destn : Id message : String Log :: 	eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails values	functions  compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> [] stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true ");
		// /--------------------------------------------------\
		// | Types with invariants                            |
		// \--------------------------------------------------/
		//30//
		add("types R = real inv r == r > 3.1415 and r < 19.82");
		//31//
		add("types R = rat inv r == r > 22/7");
		//32//
		add("types I = int inv i == i < 0");
		//33//
		add("types N = nat inv n == n <= 255");
		//34//
		add("types N = nat1 inv n == n <= 1");
		//35//
		add("types C = char inv c == c = 'a'");
		//36//
		add("types B = bool inv b == b and true");
		//37//
		add("types T = token inv t == t <> mk_token(\"42\")");
		//38//
		add("types Q1 = <A> Q = <A> inv q == is_Q1(q)");
		//39//
		add("types T = compose REC of a:int b:rat end inv t == t.a < t.b");
		//40// 
		add("types A = int | token inv a == a > 0");
		//41//
		add("types T = int * rat * real inv t == t.#1 > 0 and t.#2 < 22/7 and 3.1415");
		//42//
		add("types T=[int] inv t == t <> nil");
		//43//
		add("types A = set of int inv s == card s > 0");
		//44//
		add("types A = seq of int inv s == len s > 0");
		//45//
		add("types A = seq1 of int inv a == len a < 100");
		//46//
		add("types A = map int to int inv m == dom(m) <> {}");
		//47//
		add("types A = map int to int inv m == rng(m) <> {}");
		//48//
		add("types A = inmap int to int inv im == card rng(im) > 0");
		//49//
		add("types A = map int to int B = A inv b == card dom(b) > 0");
		//50// Failing because of LexNameToken conflict
		add("types R :: r : int inv r == r.r>0");
		//51// set type w. invariant 
		add("types C = set of int inv c == c = {1,2,3}");

	}


	@Parameters
	public static Collection<Object[]> parameter() {
		return testData.get(TypesCmlTypeCheckerTestCase.class);
	}
	
	public TypesCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}
	
	
}
