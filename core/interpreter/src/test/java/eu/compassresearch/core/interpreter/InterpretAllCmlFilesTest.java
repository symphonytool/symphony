package eu.compassresearch.core.interpreter;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

@RunWith(Parameterized.class)
public class InterpretAllCmlFilesTest
{

	private String filePath;

	public InterpretAllCmlFilesTest(String filePath, String name)
	{
		this.filePath = filePath;
	}

	// @Parameters
	// public static Collection params() {
	// return new LinkedList<String>();
	// }

	// private static String watchedLog;

	@Rule
	public TestWatcher watchman = new TestWatcher()
	{

		@Override
		protected void failed(Throwable e, Description d)
		{
			// watchedLog+= d + "\n";

			System.err.println("Test failed in : " + d.getMethodName() + " : "
					+ filePath);
			System.err.println(e);
		}

		@Override
		protected void starting(Description description)
		{

			System.out.println("Test started : " + description.getMethodName()
					+ " : " + filePath);
			super.starting(description);
		}

		@Override
		protected void succeeded(Description d)
		{

			System.out.println(d.getMethodName() + " : '" + filePath
					+ "' completed succesfully");
		}
	};

	@Before
	public void setUp()
	{

	}

	@Test
	public void testInterpretCmlFile() throws IOException, AnalysisException,
			CmlInterpreterException
	{

		File f = new File(filePath);
		// AFileSource ast = new AFileSource();
		// ast.setName(f.getName());
		// ast.setFile(f);

		String resultPath = filePath.split("[.]")[0] + ".result";

		// ExpectedTestResult testResult =(new File(resultPath).exists()?
		// ExpectedTestResult.parseTestResultFile(resultPath):null);

		// if(testResult == null)
		// Assert.fail("The testResult is not formatted correctly");

		ParserResult res = ParserUtil.parse(f);

		// assertTrue(CmlParserUtil.parseSource(ast));
		if (!res.errors.isEmpty())
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			res.printErrors(ps);
			Assume.assumeTrue("Parser errors", false);
			assertTrue("Parser Errors: \n\n" + baos, res.errors.isEmpty());
			return;
		}

		// Type check
		ITypeIssueHandler tcIssue = VanillaFactory.newCollectingIssueHandle();
		ICmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(res.definitions, tcIssue);

		boolean isTypechecked = cmlTC.typeCheck();

		if (!isTypechecked)
		{
			System.err.println(tcIssue.getTypeErrors());
		}

		if (!isTypechecked)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(baos);
			tcIssue.printErrors(pw);
			Assume.assumeTrue("Type check errors", false);
			assertTrue("Type Check failed\n\n" + baos, isTypechecked);
			return;
		}

		CmlInterpreter interpreter = new VanillaInterpreterFactory().newInterpreter(res.definitions);

		Exception exception = null;
		try
		{
			interpreter.initialize();
			execute(interpreter);
		} catch (Exception ex)
		{
			exception = ex;
		}
		ExpectedTestResult testResult = ExpectedTestResult.parseTestResultFile(resultPath);
		checkResult(testResult, interpreter, exception);
	}

	protected void execute(CmlInterpreter interpreter)
			throws AnalysisException, Exception
	{
		interpreter.execute(new RandomSelectionStrategy());
	}

	private void checkResult(ExpectedTestResult testResult,
			CmlInterpreter interpreter, Exception exception)
	{

		CmlBehaviour topProcess = interpreter.getTopLevelProcess();

		// Exceptions check
		// testResult.throwsException() => exception != null
		assertTrue("The test was expected to throw an exception but did not!", !testResult.throwsException()
				|| exception != null);
		// !testResult.throwsException() => exception == null
		assertTrue("The test threw an unexpected exception : " + exception, testResult.throwsException()
				|| exception == null);

		// events
		String eventTrace = "";
		if (null != topProcess)
		{
			eventTrace = TraceUtility.traceToString(topProcess.getTraceModel().getEventTrace());
		}
		Pattern trace = testResult.getExpectedEventTracePattern();
		Matcher matcher = trace.matcher(eventTrace);
		assertTrue(testResult.getExpectedEventTracePattern() + " != "
				+ eventTrace, matcher.matches());

		// TimedTrace
		if (testResult.hasTimedTrace())
		{
			// Convert the trace into a list of strings to compare it with the expected
			String timedTrace = TraceUtility.traceToString(topProcess.getTraceModel().getObservableTrace());

			matcher = testResult.getExpectedTimedTracePattern().matcher(timedTrace);
			assertTrue(testResult.getExpectedTimedTracePattern() + " != "
					+ timedTrace, matcher.matches());
		}

		// Interpreter state
		Assert.assertEquals(testResult.getInterpreterState(), interpreter.getState());
	}

	@Parameters(name = "{index} : {1}")
	public static Collection<Object[]> getCmlfilePaths()
	{

		final String initialPath = "src/test/resources/standard";
		List<Object[]> paths = findAllCmlFiles(initialPath);

		// List<Object[]> paths = findAllCmlFiles("src/test/resources/action/parallel-composition");

		// findAllCmlFiles("src/test/resources/action/");
		// paths.addAll(findAllCmlFiles("src/test/resources/process/"));
		// paths.addAll(findAllCmlFiles("src/test/resources/examples/"));
		// paths.addAll(findAllCmlFiles("src/test/resources/classes/"));
		// paths.addAll(findAllCmlFiles("src/test/resources/action/replicated/"));

		List<Object[]> tests = new Vector<Object[]>();

		for (Object[] p : paths)
		{
			String path = p[0].toString();
			String name = path.substring(initialPath.length() + 1);
			tests.add(new Object[] { path, name });
		}

		return tests;
	}

	protected static List<Object[]> findAllCmlFiles(String folderPath)
	{
		List<Object[]> paths = new Vector<Object[]>();
		File folder = new File(folderPath);

		paths.addAll(addFilesInFolder(folder));

		for (File subfolder : findSubfolders(folder))
		{
			paths.addAll(addFilesInFolder(subfolder));
		}

		return paths;
	}

	private static List<File> findSubfolders(File folder)
	{
		List<File> subfolders = new LinkedList<File>();

		subfolders.addAll(Arrays.asList(folder.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir, name).isDirectory();
			}
		})));

		List<File> subsubfolders = new LinkedList<File>();
		for (File sub : subfolders)
		{
			subsubfolders.addAll(findSubfolders(sub));
		}

		subfolders.addAll(subsubfolders);

		return subfolders;
	}

	private static List<Object[]> addFilesInFolder(File folder)
	{

		// Make filter to only get the files that ends with '.cml'
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.toLowerCase().endsWith(".cml");
			}
		};

		// Add the folders to search in
		String[] children = folder.list(filter);

		List<Object[]> paths = new Vector<Object[]>();

		if (children == null)
		{
			// Either dir does not exist or is not a directory
		} else
		{
			for (int i = 0; i < children.length; i++)
			{
				// Get filename of file or directory
				paths.add(new Object[] { folder.getPath() + File.separatorChar
						+ children[i] });
			}
		}
		return paths;
	}
}
