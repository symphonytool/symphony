package eu.compassresearch.core.typechecker;


import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class OperationCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase{
	public OperationCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter() {
		return testData;
	}

	static {
		add("lass c = begin operations o:int ==> int o(n) == (return (1)) end");
		add("class c = begin state a:int := 10 operations o:int ==> int o(n) == (return (a + n)) end");
		add("class d = begin state a:int operations c: int ==> () c(v) == a := v end");
		add("class test = begin operations o:int ==> int o(a) == (return (\"ras\")) end");
		add("class test = begin operations o:int ==> int o(a) == (return (let a : int = 2 in \"42\")) end");
		add("process A = B ; C process D = begin @ Skip end");
		add("class test = begin operations o1:int ==> int o1(a) == let b : int = a+1 in return (c) end");
	}
}
