package eu.compassresearch.pog.tests.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.Assert;


/**
 * Helper Class for the POG test framework. Provides lists of test inputs 
 * (typically CML sources) and results (string/json files) .
 * @author ldc
 *
 */
public class TestFileProvider
{

	private final static String BASE_INPUTS_FOLDER = "src/test/resources/basetests/";
	private final static String RESULT_EXTENSION=".RESULT";
	private final static String BUG_REG_ROOT = "src/test/resources/bug-regression";

	public static Collection<Object[]> bugRegs() {
		File dir = new File(BUG_REG_ROOT);
		return files(dir);
	}
	
	private static Collection<Object[]> files(File dir) {
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter(
				"(.*)\\.cml"), DirectoryFileFilter.DIRECTORY);

		List<Object[]> paths = new Vector<Object[]>();

		for (File file : files) {
			paths.add(new Object[] { file.getPath(), file.getPath()+RESULT_EXTENSION});
		}

		return paths;
	}
	
/**
 * Provides the base test input and result files.
 * @return A list of test file paths represented as {<folder>, <input>, <result>}
 */
	public static Collection<Object[]> files(){ 
		List<Object[]> paths = new Vector<Object[]>();
		File folder = new File(BASE_INPUTS_FOLDER);

		
		// Don't grab result files
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return !(name.toUpperCase().endsWith(RESULT_EXTENSION));
			}
		};

		// Get the files that match the filter
		String[] children = folder.list(filter);

		if (children == null)
		{
			//This should not happen
			Assert.fail("Could not find test files in "+BASE_INPUTS_FOLDER);
		} else
		{
			for (int i = 0; i < children.length; i++)
			{
				// Get paths trio
				paths.add(new Object[] {folder.getPath() + File.separatorChar,  children[i], children[i]+RESULT_EXTENSION});
			}
		}
		
		return paths;
		
	} 
	

}
