package eu.compassresearch.pog.tests.utils;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * Helper Class for the POG test framework. Provides lists of test inputs (typically CML sources) and results
 * (string/json files) .
 * 
 * @author ldc
 */
public class TestFileProvider
{

	private final static String BASE_POS = "src/test/resources/basic/pos";
	private final static String BASE_CTXTS = "src/test/resources/basic/ctxts";
	private final static String BUG_REG_ROOT = "src/test/resources/bug-regression";
	private final static String INTEG_ROOT = "src/test/resources/integration";
	private final static String RESULT_EXTENSION = ".RESULT";

	public static Collection<Object[]> bugRegs()
	{
		File dir = new File(BUG_REG_ROOT);
		return files(dir);
	}
	
	public static Collection<Object[]> models()
	{
		File dir = new File(INTEG_ROOT);
		return files(dir);
	}

	public static Collection<Object[]> basics()
	{
		
		File dir = new File(BASE_POS);
		Collection<Object[]> r = files(dir);
		dir= new File(BASE_CTXTS);
		r.addAll(files(dir));
		return r;

	}

	private static Collection<Object[]> files(File dir)
	{
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter("(.*)\\.cml"), DirectoryFileFilter.DIRECTORY);

		List<Object[]> paths = new Vector<Object[]>();

		for (File file : files)
		{
			paths.add(new Object[] { file.getName(), file.getPath(),
					file.getPath() + RESULT_EXTENSION });
		}

		return paths;
	}


}
