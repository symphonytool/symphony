package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.secondedition.TestConverter;

@RunWith(value = Parameterized.class)
public class StatementCmlTypeCheckerTestCase extends
		AbstractTypeCheckerTestCase
{
	static
	{
		// 0// Test test checks that the declared section of an dcl is checked.
		add("/*Test test checks that the declared section of an dcl is checked.*/ process P = begin @ (dcl z:int := \"wrong type\" @ Skip) end", true, false);
		// 1// This test was reported by AKM and checks that operations cannot
		// be invoked from dcl's
		add("/*This test was reported by AKM and checks that operations cannot be invoked from dcl's*/ channels a : int process A = begin state v : int := 2 operations Test : int ==> int Test(x) == return x + v @ (dcl z : int := test(2) @ a!(z) -> Skip ) end", false);
		// 2//Negative test. This test was reported by AKM and checks that do statements only contain
		// boolean types.
		add("/*Negative test. This test was reported by AKM and checks that do statements only contain boolean types.*/ process A = begin @ do [] -> Skip end end", false);
		// 3// This test was reported by AKM and checks that do statements only contain
		// boolean types
		add("/*This test was reported by AKM and checks that do statements only contain boolean types*/ process A = begin @ do true -> Skip | false -> Skip end end");
		// 4 // Test the lookup in for statements and pattern binds
		add("/*Test the lookup in for statements and pattern binds*/ channels  a : int process A = begin @ for x in [1,2,3,4] do a.x -> Skip end");
	}

	public StatementCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(StatementCmlTypeCheckerTestCase.class);
	}
	
	
	
	static int index = 0;
	static final  String SUITE_NAME = "statements";
	
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
