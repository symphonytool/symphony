package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ActionsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {

	
	public ActionsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}
	
	static {
		add("process A = begin actions B = Skip @ Skip end");
		add("process A = begin @ Skip end process p1 = begin @ A ; Skip end");
		// 124 // A is a process and cannot be in an action
		add("process A = begin @ Skip end process p1 = begin @ A [] Skip end");
		// 125
		add("process A = begin @ Skip end process p1 = begin @ A |~| Skip end");
		add("process p1 = begin @ UnknownAction end");
		add("process P = begin actions INIT = Skip @ INIT end");
	}
}
