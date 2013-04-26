package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class FunctionCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase{

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}

	
	public FunctionCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}
	
	static {
		add("class c = begin functions fn: int -> int fn(a) == \"Wrong type\" + a end");
		add("class test = begin functions fn: int -> int fn(a) == a + 2 end");
		add("class test = begin functions public plus: int * int -> int plus(a,b) == (0 + a) + b end");
		add("class Thing = begin values public Douglas : int = 42 end class test = begin values a: int = 0 functions g: int -> int g(i) == a + Thing`Douglas end");
		add("class test = begin values a : int = 0	functions f:int -> int f(k) == k+a	end");
		add("class Stuff = begin functions operations o : () ==> int o() == (return (let a:int = 2 in  \"abcd\")) end");
		add("class test = begin functions f: int -> int f(a) == a+10 end");
		add("class exlicitfunction   = begin types aunion = nat | <None> functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end");
		add("class implicitFuncTest = begin types Message :: sender: Id destn : Id message : String;	Log :: eru : ERUId	oldRescue : RescueDetails newRescue : RescueDetails; Id :: type : (<ERU> | <CC>) identifier : token; ERUId = Id RescueDetails :: criticality : int ; String = seq of char functions compareCriticalityFunction: RescueDetails * RescueDetails -> bool compareCriticalityFunction(r, r2) == r.criticality > r2.criticality rescueDetailsToString(r : RescueDetails) s: String post s <> []  stringToRescueDetails (s: String) r : RescueDetails pre s <> [] post true end");
	}

}
