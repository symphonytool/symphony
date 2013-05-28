package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test regarding testing Actions (except statement actions) are placed here.
 * 
 * Snippet tests are for small CML-fragments that approximates units of the type
 * checker.
 * 
 * @author rwl
 * 
 */
@RunWith(value = Parameterized.class)
public class ActionsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {
	static {
		// 0//
		add("process A = begin actions B = Skip @ Skip end");
		// 1// Referencing a process inside an action should fail
		add("process A = begin @ Skip end process P1 = begin @ A ; Skip end",
				true, false);
		// 2// A is a process and cannot be in an action
		add("process A = begin @ Skip end process p1 = begin @ A [] Skip end",
				true, false);
		// 3// internal choice A is a process not an action
		add("process A = begin @ Skip end process p1 = begin @ A |~| Skip end",
				true, false);
		// 4// Action reference negative
		add("process p1 = begin @ UnknownAction end", true, false);
		// 5// Action reference positive
		add("process P = begin actions INIT = Skip @ INIT end");
		// 6// Lexical forward reference to action
		add("process P = begin actions A = B B = Skip @ A end");
		// 7// Self reference
		add("process P = begin actions A = Skip ; A @ Skip end");
	}

	public ActionsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData.get(ActionsCmlTypeCheckerTestCase.class);
	}
}
