package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

@RunWith(value = Parameterized.class)
public class ProcessCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {
	static {
		// 0//
		add("process p1 = begin @ Skip end");
		// 1//
		add("process p1 = a:int @ begin @ Skip end");
		// 2//
		add("process A = begin @ Skip end process B = begin @ Skip end process p1 = A [| channel1 |] B",
				false); // negative as channel1 is
		// 3//
		add("process A = begin @ Skip end process B = begin @ Skip end process p1 = A [ channel1 || channel2 ] B",
				false); // negative as channel1 and
		// 4//
		add("process A = begin @ Skip end process p1 = A || begin @ Skip end");
		// 5//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ (A \\\\ {| B |}) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 6//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ (A \\\\ {| channel1 |}) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 7//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 8//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ (A startsby 42) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 9//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ (A endsby 42) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });

		// 10//
		add(3,
				"process A = begin @ Skip end process p1 = begin @ A ; Skip end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		// 11//
		add("class processafterclass = begin types aunion = nat functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end process A = A ; B",
				true, false);
		// 12//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b; return a @ a := o(10) end");
		// 13//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a := b - a else a := 0 ; return a @ a := o(9) end");
		// 14//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif 1 then a := b - a else a := 0 ; return a @ a := o(9) end",
				true, false);
		// 15//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:='a' else a := 0 ; return a @ a := o(9) end",
				true, false);
		// 16//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 'l' ; return a @ a := o(9) end",
				true, false);
		// 17//
		add("process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 0 ; return a @ a := o('l') end",
				true, false);
		// 18//
		add("process A = begin actions B = val n:int @ Skip @ (||| i in set {1,2,3} @ [ { } ] B(i)) end");
		// 19//
		add("process A = begin @ A [[ init <- start ]] end ");
		// 20//
		add("process K = begin operations A:int*int ==> bool A(a,b) == return (a=b) @ A(2) end",
				true, false);
		// 21//
		add("process L = begin operations A:int*int*int ==> bool A(a,b,c) == o(0);return (a+b=c) @ A(1,mk_(0,2)) end",
				true, false);
		// 22//
		add("process L = begin state k : int operations K:int*int ==> int K(a,b) == for all i in set {1,2,3} do k := k + o @ Skip end",
				true, false);
		// 23//
		add("process L = begin state l : int @ || i in set {1,2,3} @ [{ }] l:=i end");
		// 24//
		add("process O = begin state o : int @ for all i in set {1,2,3} do o := o + i end");
		// 25//
		add("process M = begin @ Skip end process L = begin @ Stop end process K = || i in set {1,2,3} @ [{ }] (M [| {| inp.k | k in set {5,6,7} |} union {| out.k | k in set {5,6,7} |} |] L)");
		// 26// old-record in operation
		add("process p = begin types A :: a : int state aa:A operations o:int==>int o(i) == return (aa.a + i) post aa~.a = i @ o(2) end");
		//27 //check for variable name in action being the same as state variable
		add("process Q = begin state q : nat := 10 actions INIT = (dcl q : nat @ q := 10) @ INIT() end");
		//28
		add("process = begin @ Skip end", false, false);
		
	}

	public ProcessCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData.get(ProcessCmlTypeCheckerTestCase.class);
	}

}
