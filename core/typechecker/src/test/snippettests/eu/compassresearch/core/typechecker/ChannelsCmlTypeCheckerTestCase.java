package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test regarding testing channels are placed here. Snippet tests are for small CML-fragments that approximates units of
 * the type checker.
 * 
 * @author rwl
 */
@RunWith(value = Parameterized.class)
public class ChannelsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{
	static
	{
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
		add("channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = $T(target(C)) @ Skip end", true, false);
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
		// 10// test that constraints on input communications
		add("channels a: int process A = begin @ a? x : (x in set {2,3}) -> Skip end");
		// 11// test that constraints on input communications
		add("channels a: int process A = begin @ a? x : (x > 3) -> Skip end");
		// 12// test that constraints on input communications
		add("channels a: int process A = begin @ a? x : ({2,3}) -> Skip end", false); // negative test
		// 13// test warning if channels is used without being declared
		add("channels a: int process A = begin @ b? x : ({2,3}) -> Skip end", false); // negative test
		// 14// duplication of channel name
		add("channels a : nat a : seq of char", false); // negative test
		// 15
		add("types private Day = nat t = nat * nat channels d,book: (Day * Day * nat) process hotel2spec =  begin actions HOTEL =  book?mk_(s,e,n) -> Skip  @ Skip	end");

	}

	public ChannelsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(ChannelsCmlTypeCheckerTestCase.class);
	}

}
