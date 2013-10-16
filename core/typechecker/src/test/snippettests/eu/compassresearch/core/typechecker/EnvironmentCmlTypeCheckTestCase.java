package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class EnvironmentCmlTypeCheckTestCase extends
		AbstractTypeCheckerTestCase
{

	static
	{
		add("class K = begin state a: int functions f: int -> int f(x) == let y : int = 10 in x + y + a end", true, true, false);
		add("values a : int = 2 class K = begin functions f: int -> int f(x) == let y : int = 10 in x + y + a end", true, true, false);
	}

	// public EnvironmentCmlTypeCheckTestCase() {
	// super(new LinkedList<PSource>(), true, true, new String[0]);
	// }

	public EnvironmentCmlTypeCheckTestCase(String source, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(source, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(EnvironmentCmlTypeCheckTestCase.class);
	}

}
