package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.api.TypeErrorMessages;

@RunWith(value = Parameterized.class)
public class ProcessCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {

	public ProcessCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}

	static {
		//0//
		add("process p1 = begin @ Skip end");
		//1//
		add("process p1 = a:int @ begin @ Skip end");
		//2//
		add("process A = begin @ Skip end process B = begin @ Skip end process p1 = A [| channel1 |] B",false); // negative as channel1 is
		//3//
		add("process A = begin @ Skip end process B = begin @ Skip end process p1 = A [ channel1 || channel2 ] B",false); // negative as channel1 and
		//4//
		add("process A = begin @ Skip end process p1 = A || begin @ Skip end");
		//5//
		add("process A = begin @ Skip end process p1 = begin @ (A \\\\ {| B |}) end",true,false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		//6//
		add("process A = begin @ Skip end process p1 = begin @ (A \\\\ {| channel1 |}) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		//7//
		add("process A = begin @ Skip end process p1 = begin @ A ; Skip end",true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		//8//
		add("process A = begin @ Skip end process p1 = begin @ (A startsby 42) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });
		//9//
		add("process A = begin @ Skip end process p1 = begin @ (A endsby 42) end",
				true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });

		//10//
		add("process A = begin @ Skip end process p1 = begin @ A ; Skip end",true, false,
				new String[] { TypeErrorMessages.EXPECTED_AN_ACTION
						.customizeMessage("A") });

		//11//
		add("class processafterclass = begin types aunion = nat          functions g:int -> aunion g(a) == if (a = 0) then  <None> else a end process A = A ; B", true, false);
	}
	
}
