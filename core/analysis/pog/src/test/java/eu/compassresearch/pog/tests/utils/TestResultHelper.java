package eu.compassresearch.pog.tests.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Helper Class for the POG test framework. Helps generate test results.
 * 
 * This classes does I/O and interaction
 * with the COMPASS TC and Parser.
 * @author ldc
 *
 */
public class TestResultHelper
{

	public static String getResultAsString(String resultPath) throws FileNotFoundException, IOException
	{
		String sResult = IOUtils.toString(new FileReader(resultPath));
		
		return sResult;
	}

}
