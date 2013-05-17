package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test regarding testing channels are placed here.
 * 
 * Snippet tests are for small CML-fragments that approximates units of the type
 * checker.
 * 
 * @author rwl
 * 
 */
@RunWith(value = Parameterized.class)
public class ChannelsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {
	static {
		// 0//
		add("channels init process test = begin @ init -> Skip end");
		// 1//
		add("channels init:int\ntest process test = begin @ init?x -> Skip end");
		// 2//
		add("channels c: seq of char");
		// 3//
		add("channels a : int process A =  begin state b : int := 2 actions INIT = a!(b+2) -> Skip @ INIT end");
		// 4//
		add("channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = C C = Skip  @ Skip end");
		// 5//
		add("channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = $T(target(C)) @ Skip end",
				true, false);
		// 6//
		add("channels a, b process A = begin actions INIT = (a -> b -> Skip) \\\\ {|b|} @ INIT end");
		// 7// This test checks that an explicit operation body of type nat1 can
		// be declared to return int.
		add("channels a, b process A = begin functions test : int +> int test(x) == 2  @ a -> Skip end");
		// 8// This test was reported by AKM and checks that functions can be
		// invoked from Guards
		add("channels a process A = begin state x : nat := 3 functions isHigherThanTwo : (int) +> bool isHigherThanTwo(y) == y > 2 @ [isHigherThanTwo(x)] & a -> Skip end");
		// 9// Test that the dot '.' prefix on channels works
		add("channels a: int * int process A = begin state istate : int jstate : int @ a?i?j -> a.i.j -> (istate := i ; jstate := j) end");
	}

	public ChannelsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData.get(ChannelsCmlTypeCheckerTestCase.class);
	}

}
