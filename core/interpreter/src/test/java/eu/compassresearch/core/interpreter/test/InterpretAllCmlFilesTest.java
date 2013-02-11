package eu.compassresearch.core.interpreter.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.scheduler.FCFSPolicy;
import eu.compassresearch.core.interpreter.scheduler.Scheduler;
import eu.compassresearch.core.interpreter.util.CmlUtil;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@RunWith(Parameterized.class)
public class InterpretAllCmlFilesTest {

	private String filePath;

	public InterpretAllCmlFilesTest(String filePath) {
		CmlRuntime.logger().setLevel(Level.OFF);
		this.filePath = filePath;
	}

	// @Parameters
	// public static Collection params() {
	// return new LinkedList<String>();
	// }

	// private static String watchedLog;

	@Rule
	public TestWatcher watchman = new TestWatcher() {

		@Override
		protected void failed(Throwable e, Description d) {
			// watchedLog+= d + "\n";

			System.out.println("Test failed in : " + d.getMethodName() + " : "
					+ filePath);
			System.out.println(e);
		}
		
		@Override
		protected void starting(Description description) {

			System.out.println("Test started : " + description.getMethodName() + " : "
					+ filePath);
			super.starting(description);
		}

		@Override
		protected void succeeded(Description d) {

			System.out.println(d.getMethodName() + " : '" + filePath
					+ "' completed succesfully");
		}
	};

	@Before
	public void setUp() {
		
	}

	@Test
	public void testParseCmlFile() throws IOException, AnalysisException,
			InterpreterException {

		File f = new File(filePath);
		AFileSource ast = new AFileSource();
		ast.setName(f.getName());
		ast.setFile(f);

		String resultPath = filePath.split("[.]")[0] + ".result";

		TestResult testResult = TestResult.parseTestResultFile(resultPath);

		assertTrue(CmlUtil.parseSource(ast));

		// Type check
		TypeIssueHandler tcIssue = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				Arrays.asList(new PSource[] { ast }), tcIssue);

		boolean isTypechecked = cmlTC.typeCheck();
		
		if(!isTypechecked)
			System.out.println(tcIssue.getTypeErrors());
			
		
		assertTrue(isTypechecked);

		CmlInterpreter interpreter = VanillaInterpreterFactory.newInterpreter(ast);

		Scheduler scheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newCmlSupervisorEnvironment(new RandomSelectionStrategy(), scheduler);
		
		interpreter.execute(sve,scheduler);

		checkResult(testResult, interpreter.getStatus());
	}
	
	private void checkResult(TestResult testResult, InterpreterStatus status) {
		if(!testResult.isInterleaved())
		{
			assertTrue(testResult.getFirstVisibleTrace()
					.equals(status.getToplevelProcessInfo().getVisibleTrace()));
		}
		else
		{
			boolean foundMatch = false;
			//If we have interleaving it must be one of the possible traces
			List<String> resultTrace = status.getToplevelProcessInfo().getVisibleTrace();
			for(List<String> trace : testResult.getVisibleTraces())
			{
				foundMatch |= trace.equals(resultTrace);
				
			}
			
			assertTrue(foundMatch);
		}

	}

	@Parameters
	public static Collection getCmlfilePaths() {

		//first add the actuin tests
		File ActionDir = new File("src/test/resources/action/");
		List<Object[]> paths = new Vector<Object[]>();

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cml");
			}
		};
		
		String[] children = ActionDir.list(filter);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(new Object[] { ActionDir.getPath() + "/" + children[i] });
			}
		}
		
		//next the process tests
		File processDir = new File("src/test/resources/process/");
		children = processDir.list(filter);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(new Object[] { processDir.getPath() + "/" + children[i] });
			}
		}

		return paths;
	}
}
