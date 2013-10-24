package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.secondedition.TestConverter;

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
	
	
	static int index = 0;
	static final  String SUITE_NAME = "environment";
	
	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, String[] err)
	{
		AbstractTypeCheckerTestCase.add(stack,src,parseok,tcok,err);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, boolean expectNoWarnings, String[] err)
	{
		AbstractTypeCheckerTestCase.add(stack,src,parseok,tcok,expectNoWarnings,err);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoWarnings, String[] err, String name)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,expectNoWarnings,err,name);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			String[] err, String name)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,err,name);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoParam)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok,expectNoParam);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src, boolean parseok, boolean tcok)
	{
		AbstractTypeCheckerTestCase.add(src,parseok,tcok);
		TestConverter.convert("src/test/resources/", SUITE_NAME, index++, src,parseok,tcok);
	}

	protected static void add(String src)
	{
		add(4, src, true, true, true, new String[0]);
	}

	protected static void add(String src, boolean tcok)
	{
		add(4, src, true, tcok, true, new String[0]);
	}

	protected static void add(String src, boolean tcok, String[] errmsg)
	{
		add(4, src, true, tcok, true, errmsg);
	}
	

}
