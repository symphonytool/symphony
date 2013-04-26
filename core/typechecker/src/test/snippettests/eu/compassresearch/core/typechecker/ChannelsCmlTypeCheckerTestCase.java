package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ChannelsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase{

	
	
	public ChannelsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}

	
	static {
		add("channels init process test = begin @ init -> Skip end");

		add("channels init:int\ntest process test = begin @ init?x -> Skip end");

		add("channels c: seq of char");
	}
	
}
