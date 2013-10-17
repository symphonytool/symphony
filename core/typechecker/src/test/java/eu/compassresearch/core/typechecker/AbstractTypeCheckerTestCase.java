package eu.compassresearch.core.typechecker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeError;

/**
 * Abstract Test Case for the COMPASS Modelling Language Type Checker. This file is super class for parameterized junit
 * classes. Each parameterized test consists of: [1] A list of sources [2] Whether the parser is expected to be
 * successful [3] Whether the type check is expected to be successful [4] A list of expected error messages if [3] is
 * false
 * 
 * @author rwl
 */
public abstract class AbstractTypeCheckerTestCase
{

	// Collected test from static initializer in subclasses
	protected static Map<Class<?>, List<Object[]>> testData = new HashMap<Class<?>, List<Object[]>>();

	// The parameters of each test
	protected List<PSource> sources;
	protected final boolean expectedParserOk;
	protected final boolean expectedTypesOk;
	protected final boolean expectedNoWarnings;
	protected String[] errorMessages;

	// A number to uniquely identify each test-method
	private static int no;

	// Get class of caller, add test data entry if not already existing and
	// return it
	private static List<Object[]> getTestDataForCallerClass(int a)
	{
		try
		{
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			String callerClassFQCN = stack[a].getClassName();
			Class<?> clz = AbstractTypeCheckerTestCase.class.getClassLoader().loadClass(callerClassFQCN);
			if (testData.containsKey(clz))
				return testData.get(clz);
			else
				testData.put(clz, new LinkedList<Object[]>());
			return testData.get(clz);
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Add a test scenario based on a single cml-source-text E.g: static { add("types A = int",true,true,new String[0]);
	 * } Adds a test with "types A = int" as the cml-source to test.
	 * 
	 * @param src
	 *            - cml source text
	 * @param parseok
	 *            - should it parse?
	 * @param tcok
	 *            - should it tc?
	 * @param err
	 *            - which errors in case of !tc
	 */
	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, String[] err)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(stack), src, new Object[] {
				parseok, tcok, true, err });
	}

	/**
	 * Add a test scenario based on a single cml-source-text E.g: static { add("types A = int",true,true,new String[0]);
	 * } Adds a test with "types A = int" as the cml-source to test.
	 * 
	 * @param src
	 *            - cml source text
	 * @param parseok
	 *            - should it parse?
	 * @param tcok
	 *            - should it tc?
	 * @param expectNoWarnings
	 *            - should there be no warnings?
	 * @param err
	 *            - which errors in case of !tc
	 */
	protected static void add(int stack, String src, boolean parseok,
			boolean tcok, boolean expectNoWarnings, String[] err)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(stack), src, new Object[] {
				parseok, tcok, expectNoWarnings, err });
	}

	/**
	 * Like {@code add} above but where the test can be given a name E.g: static { add("types A = int",true,true,new
	 * String[0],"Testing def of named int type"); } Adds a test with "types A = int" as the cml-source to test with an
	 * informative name.
	 * 
	 * @param src
	 * @param parseok
	 * @param tcok
	 * @param expectNoWarnings
	 * @param err
	 * @param name
	 */
	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoWarnings, String[] err, String name)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(4), src, new Object[] {
				parseok, tcok, expectNoWarnings, err, name });
	}

	/**
	 * Like {@code add} above but where the test can be given a name E.g: static { add("types A = int",true,true,new
	 * String[0],"Testing def of named int type"); } Adds a test with "types A = int" as the cml-source to test with an
	 * informative name.
	 * 
	 * @param src
	 * @param parseok
	 * @param tcok
	 * @param err
	 * @param name
	 */
	protected static void add(String src, boolean parseok, boolean tcok,
			String[] err, String name)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(4), src, new Object[] {
				parseok, tcok, true, err, name });
	}

	/**
	 * Shortcut for the above E.g.: static { add("types A = int",true,true); }
	 * 
	 * @param src
	 * @param parseok
	 * @param tcok
	 */
	protected static void add(String src, boolean parseok, boolean tcok,
			boolean expectNoParam)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(3), src, new Object[] {
				parseok, tcok, expectNoParam, new String[0] });
	}

	/**
	 * Shortcut for the above E.g.: static { add("types A = int",true,true); }
	 * 
	 * @param src
	 * @param parseok
	 * @param tcok
	 */
	protected static void add(String src, boolean parseok, boolean tcok)
	{
		TestUtil.addTestProgram(getTestDataForCallerClass(3), src, new Object[] {
				parseok, tcok, true, new String[0] });
	}

	/**
	 * Even shorter short cut see above.
	 * 
	 * @param src
	 */
	protected static void add(String src)
	{
		add(4, src, true, true, true, new String[0]);
	}

	/**
	 * Short cut method see above. Only source and tcok provided.
	 * 
	 * @param src
	 * @param tcok
	 */
	protected static void add(String src, boolean tcok)
	{
		add(4, src, true, tcok, true, new String[0]);
	}

	/**
	 * The last short cut method see above. Only source (src), whether Type check should succeed (tcok) and error
	 * messages provided
	 * 
	 * @param src
	 * @param tcok
	 * @param errmsg
	 */
	protected static void add(String src, boolean tcok, String[] errmsg)
	{
		add(4, src, true, tcok, true, errmsg);
	}

	/**
	 * Given a list of PSources generate a complex test that type checks all of those source together. static {
	 * add(psourceList,true,true) } This is useful if psourceList is the result of parsing a number of files from e.g. a
	 * directory structure. See {@link CaseStudiesTestCase}.
	 * 
	 * @param src
	 * @param parsesok
	 * @param tcok
	 */
	protected static void add(List<PSource> src, boolean parsesok,
			boolean tcok, boolean expectNoWarning)
	{
		Object[] o = new Object[5];
		o[0] = src;
		o[1] = parsesok;
		o[2] = tcok;
		o[3] = expectNoWarning;
		o[4] = new String[0];
		getTestDataForCallerClass(3).add(o);
	}

	/**
	 * Create a singleton-list from a cml-source-text in cmlSource. The single element in the result is a
	 * AInputStreamSource that has the given name and the bytes from the cmlSource String as its data.
	 * 
	 * @param cmlSource
	 * @param name
	 * @return
	 */
	protected static List<PSource> fromStringContent(String cmlSource,
			String name)
	{
		InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
		AInputStreamSource source = new AInputStreamSource();
		source.setOrigin(name);
		source.setStream(cmlSourceIn);
		List<PSource> res = new LinkedList<PSource>();
		res.add(source);
		return res;
	}

	/**
	 * Create a test based on a cmlStource using {@code no} to create a unique name for the test.
	 * 
	 * @param cmlSource
	 * @param parsesOk
	 * @param typesOk
	 * @param errorMessages
	 */
	protected AbstractTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectedNoWarnings, String[] errorMessages)
	{
		this(cmlSource, parsesOk, typesOk, expectedNoWarnings, errorMessages, "Test #"
				+ (no++));
	}

	/**
	 * Create a test using the {@code fromStringContent} method to produce the sources of the test.
	 * 
	 * @param cmlSource
	 * @param parsesOk
	 * @param typesOk
	 * @param errorMessages
	 * @param name
	 */
	protected AbstractTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, boolean expectedNoWarnings,
			String[] errorMessages, String name)
	{
		this(fromStringContent(cmlSource, name), parsesOk, typesOk, expectedNoWarnings, errorMessages);
	}

	/**
	 * Simple assigning only constructor, nothing but assigning inputs to fields is done here.
	 * 
	 * @param cmlSources
	 * @param parsesOk
	 * @param typesOk
	 * @param errorMessages
	 */
	protected AbstractTypeCheckerTestCase(List<PSource> cmlSources,
			boolean parsesOk, boolean typesOk, boolean expectedNoWarnings,
			String[] errorMessages)
	{
		expectedParserOk = parsesOk;
		expectedTypesOk = typesOk;
		this.expectedNoWarnings = expectedNoWarnings;
		this.errorMessages = errorMessages;
		this.sources = cmlSources;
	}

	/**
	 * The Test ! It proceeds as follows: [1] Parse and Type check all sources [2] Check parser result and report errors
	 * if any [3] Check type checker result and report error if any unexpected ones [4] Check the expected errors are
	 * there (if any)
	 * 
	 * @throws IOException
	 * @throws AnalysisException
	 */
	@Test
	public void lexParseTypeCheck() throws IOException, AnalysisException
	{
		// don't do anything if we have no sources to work on.
		if (sources.size() == 0 || sources == null)
			return;

		// [1]
		System.out.println(sources);
		TestUtil.TypeCheckerResult res = TestUtil.runTypeChecker(sources);

		// [2]
		boolean parserOk = res.parsedOk;
		if (!parserOk)
		{
			StringBuilder parseMessages = new StringBuilder();
			for (String s : res.parseErrors)
				parseMessages.append(s);
			Assert.assertSame(parseMessages.toString(), this.expectedParserOk, parserOk);
		}

		// [3]
		ITypeIssueHandler issueHandler = res.issueHandler;
		boolean typeCheckOk = res.tcOk;

		Assert.assertEquals(TestUtil.buildErrorMessage(issueHandler, expectedTypesOk), expectedTypesOk, typeCheckOk);

		if (issueHandler != null)
		{
			boolean hasWarnings = issueHandler.hasWarnings();
			Assert.assertEquals(TestUtil.buildWarningMessage(issueHandler, expectedNoWarnings), expectedNoWarnings, !hasWarnings);
		}

		// [4]
		if (parserOk && errorMessages != null && errorMessages.length > 0)
		{

			Set<String> actualErrors = new HashSet<String>();
			for (CMLTypeError e : issueHandler.getTypeErrors())
				actualErrors.add(e.getDescription());

			for (String s : errorMessages)
				Assert.assertTrue("Expected error message:\n" + s
						+ "\nBut it was not found.", actualErrors.contains(s));
		}

	}
}
