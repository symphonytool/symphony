package eu.compassresearch.core.typechecker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.test.framework.Properties;
import org.overture.test.framework.TestResourcesResultTestCase4;
import org.overture.test.framework.results.IMessage;
import org.overture.test.framework.results.Message;
import org.overture.test.framework.results.Result;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLIssue;

@RunWith(value = Parameterized.class)
public abstract class AbstractResultBasedCmlTypeCheckerTestCase extends
		TestResourcesResultTestCase4<Boolean>// extends RunCmlExamplesTestCase
{
	protected static final String SRC_TEST_RESOURCES = "src/test/resources/";
	private static final String TESTS_TC_PROPERTY_PREFIX = "tests.tc.override.";
	private final TestType type;
	List<File> inputFiles = null;

	public AbstractResultBasedCmlTypeCheckerTestCase(File file, String name,
			TestType type)
	{
		super(file);
		this.type = type;
	}

	public AbstractResultBasedCmlTypeCheckerTestCase(File file,
			List<File> files, String name, TestType type)
	{
		super(file);
		this.type = type;
		this.inputFiles = files;
	}

	// @Parameters(name = "{index} : {1}")
	// public static Collection<Object[]> getData()
	// {
	// return collectResourcesTestData("general");
	// }

	public static Collection<Object[]> combine(Collection<Object[]> a,
			Collection<Object[]> b)
	{
		Collection<Object[]> tests = new LinkedList<Object[]>();
		tests.addAll(a);
		tests.addAll(b);
		return tests;
	}

	public static Collection<Object[]> collectResourcesTestData(String path)
	{
		return collectResourcesTestData(SRC_TEST_RESOURCES + path, TestType.ANY);
	}

	public static Collection<Object[]> collectResourcesTestData(String path,
			String endswith)
	{
		return collectTestData(SRC_TEST_RESOURCES + path, TestType.ANY, endswith);
	}

	public enum TestType
	{
		SKIP("*.cml"), ANY(".cml"), POSITIVE("-pos.cml"), NEGATIVE("-neg.cml"), COMPARE_RECORDRD(
				".cml");
		public final String endswith;

		TestType(String endswith)
		{
			this.endswith = endswith;
		}
	};

	public static Collection<Object[]> collectResourcesTestData(String path,
			TestType type)
	{
		return collectTestData(SRC_TEST_RESOURCES + path, type, type.endswith);
	}

	public static Collection<Object[]> collectResourcesTestData(String path,
			TestType type, TestType compareType)
	{
		return collectTestData(SRC_TEST_RESOURCES + path, compareType, type.endswith);
	}

	public static Collection<Object[]> collectTestData(String path,
			TestType type, TestType compareType)
	{
		return collectTestData(path, compareType, type.endswith);
	}

	public static Collection<Object[]> collectTestData(String path,
			TestType type, final String endswith)
	{
		List<Object[]> files = new LinkedList<Object[]>();

		File examplesDir = new File(path);
		FileFilter cmlFiles = new FileFilter()
		{

			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(endswith);
			}
		};

		File[] theFiles = examplesDir.listFiles(cmlFiles);
		if (theFiles != null)
		{
			Arrays.sort(theFiles);

			for (File f : theFiles)
			{
				files.add(new Object[] { f,
						f.getPath().substring(path.length()), type });
			}
		}

		return files;
	}

	public static Collection<Object[]> collectTestDataMultipleFiles(
			String path, TestType type, TestType compareType)
	{
		return collectTestDataMultipleFiles(path, compareType, type.endswith);
	}

	public static Collection<Object[]> collectTestDataMultipleFiles(
			String path, TestType type, final String endswith)
	{
		Collection<Object[]> data = collectTestData(path, type, endswith);

		List<File> files = new Vector<File>();
		for (Object[] objects : data)
		{
			if (objects[0] instanceof File)
			{
				files.add((File) objects[0]);
			}
		}

		List<Object[]> result = new LinkedList<Object[]>();
		result.add(new Object[] { new File(path), files, path, type });
		return result;
	}

	@Test
	public void test() throws IOException
	{
		configureResultGeneration();
		// Properties.recordTestResults = false;
		TestUtil.TypeCheckerResult res = null;

		if (inputFiles == null)
		{
			res = TestUtil.runTypeChecker(file.getAbsolutePath());
		} else
		{
			List<String> paths = new Vector<String>();
			for (File f : inputFiles)
			{
				paths.add(f.getAbsolutePath());
			}
			res = TestUtil.runTypeChecker(paths.toArray(new String[paths.size()]));
		}

		Assume.assumeFalse("Explicitly selected to skip", type == TestType.SKIP);

		if (!res.parsedOk)
		{
			// System.err.println("Parse errors in specification");
			// System.err.println(res.parseErrors);
			Assert.fail("Contained parser errors\n\n" + res.parseErrors);
			return;
		}

		Result<Boolean> result = new Result<Boolean>(res.tcOk, convert(res.issueHandler.getTypeWarnings()), convert(res.issueHandler.getTypeErrors()));

		if (type == TestType.POSITIVE && res.issueHandler.hasErrors())
		{
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintWriter out = new PrintWriter(outStream);
			res.issueHandler.printErrors(out);
			out.flush();
			Assert.assertTrue("Unexpected type errors in model.\n\n"
					+ outStream.toString(), !res.issueHandler.hasErrors());

		} else if (type == TestType.NEGATIVE && !res.issueHandler.hasErrors())
		{
			Assert.assertTrue("Unexpected success of type checking", res.issueHandler.hasErrors());
		}

		if (type == TestType.COMPARE_RECORDRD)
		{
			compareResults(result, file.getAbsolutePath());
		}
		Properties.recordTestResults = false;
	}

	private void configureResultGeneration()
	{
		if (System.getProperty(TESTS_TC_PROPERTY_PREFIX + "all") != null
				|| getPropertyId() != null
				&& System.getProperty(TESTS_TC_PROPERTY_PREFIX
						+ getPropertyId()) != null)
		{
			Properties.recordTestResults = true;
		}

	}

	protected String getPropertyId()
	{
		return null;
	}

	public static List<IMessage> convert(List<? extends CMLIssue> list)
	{
		List<IMessage> testMessages = new Vector<IMessage>();

		for (CMLIssue msg : list)
		{
			ILexLocation location = msg.getLocation();
			testMessages.add(new Message(location.getFile().getName(), msg.getNumber(), location.getStartLine(), location.getStartPos(), msg.getMessage()));
		}

		return testMessages;
	}

	@Override
	public void encondeResult(Boolean result, Document doc,
			Element resultElement)
	{
	}

	@Override
	public Boolean decodeResult(Node node)
	{
		return false;
	}

	@Override
	protected boolean assertEqualResults(Boolean expected, Boolean actual,
			PrintWriter out)
	{
		return true;
	}

	@Override
	protected File createResultFile(String filename)
	{
		return new File(filename + ".result");
	}

	@Override
	protected File getResultFile(String filename)
	{
		return new File(filename + ".result");
	}

	protected String getResultPath(String filename, String relativePath,
			String folder)
	{
		return SRC_TEST_RESOURCES + folder + "/"
				+ filename.substring(relativePath.length() + 1);
	}
}
