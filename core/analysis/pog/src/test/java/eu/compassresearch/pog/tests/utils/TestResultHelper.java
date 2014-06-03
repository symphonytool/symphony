package eu.compassresearch.pog.tests.utils;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.pog.tests.PoResult;

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
	
	public static void checkSameElements(List<PoResult> pRL,
			IProofObligationList ipol)
	{

		List<PoResult> prl_actual = new LinkedList<PoResult>();
		for (IProofObligation ipo : ipol)
		{
			prl_actual.add(new PoResult(ipo.getKindString(), ipo.getValue()));
		}

		Collection<PoResult> stored_notfound = CollectionUtils.removeAll(pRL, prl_actual);
		Collection<PoResult> found_notstored = CollectionUtils.removeAll(prl_actual, pRL);

		if (stored_notfound.isEmpty() && found_notstored.isEmpty())
		{
			// do nothing
		} else
		{
			StringBuilder sb = new StringBuilder();
			if (!stored_notfound.isEmpty())
			{
				sb.append("Expected (but not found) POS: " + "\n");
				for (PoResult pr : stored_notfound)
				{
					sb.append(pr.toString() + "\n");
				}
			}
			if (!found_notstored.isEmpty())
			{
				sb.append("Found (but not expected) POS: " + "\n");
				for (PoResult pr : found_notstored)
				{
					sb.append(pr.toString() + "\n");
				}
			}
			fail(sb.toString());
		}
	}

}
