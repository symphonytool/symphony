package eu.compassresearch.core.typechecker;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import eu.compassresearch.ast.program.PSource;

@RunWith(value = Parameterized.class)
public class EnvironmentCmlTypeCheckTestCase extends AbstractTypeCheckerTestCase {

	static {
		
	}
	
	
	protected EnvironmentCmlTypeCheckTestCase(List<PSource> cmlSources,
			boolean parsesOk, boolean typesOk, String[] errorMessages) {
		super(cmlSources, parsesOk, typesOk, errorMessages);
	}

}
