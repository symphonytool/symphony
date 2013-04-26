package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(value = Parameterized.class)
public class ClassCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase{

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}
	
	public ClassCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	static {
		add("class A = begin end class B = begin end class C is subclass of A = begin end");
	}	
}
