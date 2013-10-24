package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.secondedition.TestConverter;

/**
 * Test regarding testing Actions (except statement actions) are placed here. Snippet tests are for small CML-fragments
 * that approximates units of the type checker.
 * 
 * @author rwl
 */
@RunWith(value = Parameterized.class)
public class ActionsCmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{
	static
	{
		// 0//
		add("process A = begin actions B = Skip @ Skip end");
		// 1// Referencing a process inside an action should fail
		add("/*Referencing a process inside an action should fail*/ process A = begin @ Skip end process P1 = begin @ A ; Skip end", true, false);
		// 2// A is a process and cannot be in an action
		add("/*A is a process and cannot be in an action*/ process A = begin @ Skip end process p1 = begin @ A [] Skip end", true, false);
		// 3// internal choice A is a process not an action
		add("/*internal choice A is a process not an action*/ process A = begin @ Skip end process p1 = begin @ A |~| Skip end", true, false);
		// 4// Action reference negative
		add("/*Action reference negative*/ process p1 = begin @ UnknownAction end", true, false);
		// 5// Action reference positive
		add("/*Action reference positive*/ process P = begin actions INIT = Skip @ INIT end");
		// 6// Lexical forward reference to action
		add("/*Lexical forward reference to action*/ process P = begin actions A = B B = Skip @ A end");
		// 7// Self reference
		add("process P = begin actions A = Skip ; A @ Skip end");
		// 8//Sequential Composition Replicated Action with seq
		add("/*Sequential Composition Replicated Action with seq*/ process P = begin operations A : nat ==> () A(a)== Skip  @ ;i in seq [2,3] @ A(i) end", true, true, false);
		// 9//Negative test: Sequential Composition Replicated Action with set
		add("/*Negative test: Sequential Composition Replicated Action with set*/ process P = begin @ ; i in seq {2,3} @ A(i) end", true, false);
	}

	public ActionsCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNoWarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNoWarnings, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(ActionsCmlTypeCheckerTestCase.class);
	}
	
	static int index = 0;
	static final  String SUITE_NAME = "actions";
	
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
