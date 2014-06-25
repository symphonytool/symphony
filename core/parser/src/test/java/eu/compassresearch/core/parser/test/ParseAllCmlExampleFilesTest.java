package eu.compassresearch.core.parser.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.List;
import java.util.Vector;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;

@RunWith(Parameterized.class)
public class ParseAllCmlExampleFilesTest
{

	private String filePath;

	public ParseAllCmlExampleFilesTest(String filePath)
	{
		this.filePath = filePath;
	}

	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> getTests()
	{
		return getCmlfilePaths("../../docs/cml-examples");
	}

	public static Collection<Object[]> getCmlfilePaths(String path)
	{
		File dir = new File(path);
		List<Object[]> paths = new Vector<Object[]>();

		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.toLowerCase().endsWith(".cml")
                                    && !name.toLowerCase().contains("fail")
                                    && !name.toLowerCase().contains("-neg");
			}
		};

		String[] children = dir.list(filter);
		if (children == null)
		{
			// Either dir does not exist or is not a directory
		} else
		{
			for (int i = 0; i < children.length; i++)
			{
				// Get filename of file or directory
				paths.add(new Object[] { dir.getPath() + "/" + children[i] });
			}
		}

		return paths;
	}

	/*
	 * TODO: it would be good to reinstate a testwatcher that prints out a bunch of information extracted from the
	 * exception thrown. -jwc/13May2013
	 */
	// @Rule
	// public TestWatcher watchman = new TestWatcher() {
	// @Override
	// protected void failed(Throwable e, Description d) {
	// String name = d.toString();
	// int index = Integer.parseInt(name.substring(name.indexOf("[")+1, name.indexOf(":")));
	// System.out.println("in file: " +
	// ((Object[])ParseAllCmlExampleFilesTest.getCmlfilePaths().toArray()[index])[0]);
	// System.out.println();
	// }
	// };

	@Test
	public void testParseCmlFile() throws Exception
	{
//		FileInputStream source = new FileInputStream(filePath);
//		ANTLRInputStream stream = new ANTLRInputStream(source);
//		CmlLexer lexer = new CmlLexer(stream);
//		CommonTokenStream tokens = new CommonTokenStream(lexer);
//		CmlParser parser = new CmlParser(tokens);
//
//		parser.source(); // parser.source() returns the parse tree, but we never check it here
		
		ParserResult result = ParserUtil.parse(new File(filePath));
		Assert.assertTrue("There are parse failures: \n"+result, result.errors.isEmpty());
	}

}
