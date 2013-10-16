package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests that checks bindings in any shape or form in any context can be put here if testing the binding type is correct
 * is the main purpose of the test.
 * 
 * @author rwl
 */
@RunWith(value = Parameterized.class)
public class BindCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{

	static
	{
		// 0// Check set binds in external choice (so i should have the right
		// type)
		add("process P = begin actions A = val j :bool @ Skip @ ([] i in set {1,2,3} @ A(i > 2)) end");
		add("process P = begin actions A = Skip @ A end");
	}

	public BindCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(BindCmlTypeCheckerTestCase.class);
	}

}
