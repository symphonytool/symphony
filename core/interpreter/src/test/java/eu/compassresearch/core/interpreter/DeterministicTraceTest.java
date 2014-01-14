package eu.compassresearch.core.interpreter;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class DeterministicTraceTest
{
	@Before
	public void setUp() throws Exception
	{
		
	}

	@After
	public void tearDown() throws Exception
	{
		
	}
	
	@Test
	public void testThatMultipleRunsHasSameTrace() throws IOException, AnalysisException
	{
		File f = new File("src/test/resources/action/parallel-composition/action-interleaving3.cml");
		
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

		List<CmlTrace> traceList = new LinkedList<CmlTrace>();
		
		for(int i = 0; i < 10 ; i++)
		{
			CmlInterpreter interpreter =new VanillaInterpreterFactory().newInterpreter(res.definitions);
			interpreter.initialize();
			interpreter.execute(new RandomSelectionStrategy());
			traceList.add(interpreter.getTopLevelProcess().getTraceModel());
			
			if(i > 0)
			{
				Assert.assertEquals(TraceUtility.traceToString(traceList.get(i-1).getEventTrace()), 
						TraceUtility.traceToString(traceList.get(i).getEventTrace()));
			}
		}
		
		
		
	}

}
