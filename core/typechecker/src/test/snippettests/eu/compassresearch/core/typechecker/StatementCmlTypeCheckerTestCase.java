package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class StatementCmlTypeCheckerTestCase extends
		AbstractTypeCheckerTestCase {
	static {
		// 0// Test test checks that the declared section of an dcl is checked.
		add("process P = begin @ (dcl z:int := \"wrong type\" @ Skip) end",
				true, false);
		// 1// This test was reported by AKM and checks that operations cannot
		// be invoked from dcl's
		add("channels a : int process A = begin state v : int := 2 operations Test : int ==> int Test(x) == return x + v @ (dcl z : int := test(2) @ a!(z) -> Skip ) end",
				false);
		// 2//Negative test. This test was reported by AKM and checks that do statements only contain
		// boolean types. 
		add("process A = begin @ do [] -> Skip end end", false);
		// 3// This test was reported by AKM and checks that do statements only contain
		// boolean types
		add("process A = begin @ do true -> Skip | false -> Skip end end");
		// 3// Test the lookup in for statements and pattern binds
		add("channels  a : int process A = begin @ for x in [1,2,3,4] do a.x -> Skip end");
	}

	public StatementCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData.get(StatementCmlTypeCheckerTestCase.class);
	}

}
