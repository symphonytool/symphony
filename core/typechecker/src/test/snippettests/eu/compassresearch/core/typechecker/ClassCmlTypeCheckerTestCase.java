package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test regarding testing classes are placed here. Snippet tests are for small CML-fragments that approximates units of
 * the type checker.
 * 
 * @author rwl
 */

@RunWith(value = Parameterized.class)
public class ClassCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{

	static
	{
		// 0// Inheritance is not implement yet
		add("class A = begin end class B = begin end class C is subclass of A = begin end", false, false);
		add("class C = begin values k : int = 42 end functions f: int -> C f(v) == C.k + v", true, false);
		add("class C = begin functions AFunc: nat -> nat AFunc(avar) == 1 BFunc : () -> nat BFunc() == AFunc(2) end", true, true);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(ClassCmlTypeCheckerTestCase.class);
	}

	public ClassCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

}
