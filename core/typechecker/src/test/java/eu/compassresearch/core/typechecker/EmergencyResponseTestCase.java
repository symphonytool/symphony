package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.TestUtil.TypeCheckerResult;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class EmergencyResponseTestCase {

	private boolean expertLedFound = false;
	private File expertLedDir;
	private File rulesLedDir;
	private boolean rulesLedFound = false;



	@Before
	public void setup()
	{		
		expertLedDir = new File("../../docs/cml-examples/EmergencyResponse/Expert-Led/model");
		rulesLedDir =  new File("../../docs/cml-examples/EmergencyResponse/Rules-Led/model");
		expertLedFound = expertLedDir.exists() && expertLedDir.isDirectory();
		rulesLedFound  = rulesLedDir.exists() && rulesLedDir.isDirectory();
	}


	@Test
	public void expertLed() throws FileNotFoundException, IOException
	{
		Assume.assumeTrue(expertLedFound);

		List<PSource> sources = new LinkedList<PSource>();
		for(File f : expertLedDir.listFiles())
		{
			AFileSource current = new AFileSource();
			current.setFile(f);
			current.setName(f.getName());
			sources.add(current);
		}

		for(PSource source : sources)
		{
			TypeCheckerResult res = TestUtil.parse(source);
			Assert.assertTrue("Failed to parse file "+source, res.parsedOk);
		}

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker tc = VanillaFactory.newTypeChecker(sources, issueHandler );
		boolean tcOk = tc.typeCheck();
		Assert.assertTrue(TestUtil.buildErrorMessage(issueHandler, true), tcOk);
	}

	@Test
	public void rulesLed() throws FileNotFoundException, IOException
	{
		Assume.assumeTrue(rulesLedFound);

		List<PSource> sources = new LinkedList<PSource>();
		for(File f : rulesLedDir.listFiles())
		{
			AFileSource current = new AFileSource();
			current.setFile(f);
			current.setName(f.getName());
			sources.add(current);
		}

		for(PSource source : sources)
		{
			TypeCheckerResult res = TestUtil.parse(source);
			String errMsg = "";
			if (res.parseErrors != null)
			{
				for(String s : res.parseErrors)
					errMsg += s + "\n";
				Assert.assertTrue("Failed to parse file "+source+".\n"+errMsg, res.parsedOk);
			}
		}

		TypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker tc = VanillaFactory.newTypeChecker(sources, issueHandler );
		boolean tcOk = tc.typeCheck();
		Assert.assertTrue(TestUtil.buildErrorMessage(issueHandler, true), tcOk);
	}

}
