package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class FunctionCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{
	static
	{
		// 0//
		add("class K = begin functions fn: int -> int fn(a) == \"Wrong type\" + a end", true, false, false);
		// 1//
		add("class test = begin functions fn: int -> int fn(a) == a + 2 end", true , true, false);
		// 2//
		add("class test = begin functions public plus: int * int -> int plus(a,b) == (0 + a) + b end", true , true, false);
		// 3//
		add("class Thing = begin values public Douglas : int = 42 end class test = begin values a: int = 0 functions g: int -> int g(i) == a + Thing`Douglas end", true , true, false);
		// 4//
		add("class test = begin values a : int = 0	functions f:int -> int f(k) == k+a	end", true , true, false);
		// 5//
		add("class Stuff = begin functions operations o : () ==> int o() == (return (let a:int = 2 in  \"abcd\")) end", true, false, false);
		// 6//
		add("class test = begin functions f: int -> int f(a) == a+10 end", true , true, false);
		// 7//
		add("class exlicitfunction   = begin types aunion = nat | <None> functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end", true , true, false);
		// 8//
		add("class implicitFuncTest = begin types Message :: sender: Id destn : Id message : String	Log :: eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails Id :: type : (<ERU> | <CC>) identifier : token ERUId = Id RescueDetails :: criticality : int  String = seq of char functions compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> []  stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true end", true , true, false);
		// 9//
		add("functions test: int * int -> bool test(a,b) == true channels InOut: int * int * int process A = begin state b:int actions A = InOut?a!b?c -> test(a,c) @ Skip end", true , true, false);
		// 10// implicit function
		add("process T = begin functions f(a:int) r:int pre true post true @ f(2) end", true , true, false);
		// 11
		add("functions ExeBorrow2: (int * rat * real) -> int ExeBorrow2(t) == t.#1  f: int * int -> int f(x,y) == x + y", true , true, false);
		// 12 //negative test. Pattern and parameter mismatch
		add("functions free: int * int +> int free(status,number,subs) == is not yet specified", true, false);
		// 13
		add("class test = begin functions fn: int -> int fn(a) == a + 2 pre op1() operations op1: () ==> bool op1()==return true end", true, false, false);
		// 14
		// add("class Test = begin functions fn: int -> int fn(a) == is not yet specified operations t : () ==> int t()== return Test`fn(4) end",true,true);
		// FIXME this case should be ok
		add("class Test = begin operations Test : ()==> Test Test()==Skip public fn: int ==> int fn(a) == is not yet specified operations t : () ==> int t()==( dcl out : Test  @ out := new Test(); return out.fn(4)) end", true, false);
		// 15
		add("process PatientRegister = begin state reg : nat functions moreroom: () -> nat moreroom() == reg @ Skip end", true, false);
		// 16 negative test, strange post 
		add("functions max (els :set of ID) maxel:ID post max = forall el in set els @ maxel >= el", true, false, true);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(FunctionCmlTypeCheckerTestCase.class);
	}

	public FunctionCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

}
