package eu.compassresearch.core.typechecker;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.typechecker.secondedition.TestConverter;

@RunWith(value = Parameterized.class)
public class OperationCmlTypeCheckerTestCase extends
		AbstractTypeCheckerTestCase
{

	static
	{
		// 0//
		add("class c = begin operations o:int ==> int o(n) == (return (1)) end");
		// 1//
		add("class c = begin state a:int := 10 operations o:int ==> int o(n) == (return (a + n)) end");
		// 2//
		add("class d = begin state a:int operations c: int ==> () c(v) == a := v end");
		// 3//
		add("class test = begin operations o:int ==> int o(a) == (return (\"ras\")) end", true, false);
		// 4//
		add("class test = begin operations o:int ==> int o(a) == (return (let a : int = 2 in \"42\")) end", true, false, false);
		// 5//
		add("process A = B ; C process D = begin @ Skip end", false, false);
		// 6//
		add("class test = begin operations o1:int ==> int o1(a) == let b : int = a+1 in return (c) end", true, false);
		// 7// This test checks the positive case for invoking a Cml Operation
		add("/*This test checks the positive case for invoking a Cml Operation*/ process K = begin state f : int := 0 operations op1: int ==> int op1(a) == return (a+1) @ f := op1(10) end");
		// 8//
		add("class test = begin operations o: int ==> int o(a) == return a pre a > 0 post a~ = a end ", true, true, false);
		// 9//
		add("class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a end", true, true, false);
		// 10// Fix me the parser cannot handle the b.a in the pre condition ?
		add("/*Fix me the parser cannot handle the b.a in the pre condition ?*/ class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a and 'b.a' = b~.a end", true, false, false);
		// 11// op-call
		add("class test = begin operations op1: int ==> int op1(a) == return (a+1) values k : int = op1(10) end", true, true);
		// 12//Negative Test check on parameter list with product type.
		add("/*Negative Test check on parameter list with product type.*/ class A = begin operations addCD: (seq of char * (seq of Track)) ==> () addCD(title, tracks) == Skip end", true, false);
		// 13
		add("class A = begin operations op1: () ==> () op1() == Skip end", true, true);
		// 14
		add("class A = begin operations op1: () ==> () op1() == dlfk->Skip end", true, false);
		// 15
		add("class A = begin operations op1: () ==> () op1() == Skip ; Skip end", true, true);
		// 16
		add("class A = begin operations op1: () ==> () op1() == is subclass responsibility end", true, true);
		// 17 Ticket: #90 Type Checker cannot determine type for pattern of 1 in cases statement
		add("/*Ticket: #90 Type Checker cannot determine type for pattern of 1 in cases statement*/ class A = begin operations op1: int ==> () op1(v) == cases v: 1 -> Skip end end", true, true);
		// 18
		add("/*dont allow operations in pre and post*/ class A = begin operations op1: () ==> () op1() == Skip pre op1() end", true, false);
		add("class A = begin operations op1: () ==> bool op1() == return true pre op1() end", true, false);
		// 19 Ticket: https://sourceforge.net/p/compassresearch/tickets/103/
		add("/*Ticket: https://sourceforge.net/p/compassresearch/tickets/103/*/ process A = begin operations op1: () ==> bool op1() == return true pre op1() @ Skip end", true, false);
	}

	public OperationCmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectNowarnings, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, expectNowarnings, errorMessages);
	}

	@Parameters
	public static Collection<Object[]> parameter()
	{
		return testData.get(OperationCmlTypeCheckerTestCase.class);
	}
	
	
	static int index = 0;
	static final  String SUITE_NAME = "operations";
	
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
