package eu.compassresearch.core.interpreter;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import junit.framework.Assert;

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
import eu.compassresearch.core.interpreter.CmlParserUtil;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
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

		ExpectedTestResult testResult = ExpectedTestResult.parseTestResultFile(resultPath);

		assertTrue(CmlParserUtil.parseSource(ast));

		// Type check
		TypeIssueHandler tcIssue = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				Arrays.asList(new PSource[] { ast }), tcIssue);

		boolean isTypechecked = cmlTC.typeCheck();
		
		if(!isTypechecked)
			System.out.println(tcIssue.getTypeErrors());
			
		
		assertTrue(isTypechecked);

		CmlInterpreter interpreter = VanillaInterpreterFactory.newInterpreter(ast);

		CmlSupervisorEnvironment sve = 
				VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());
		
		Exception exception = null;
		try{
			interpreter.execute(sve);
		}
		catch(Exception ex)
		{
			exception = ex;
		}

		checkResult(testResult, interpreter.getStatus(), exception);
	}
	
	private void checkResult(ExpectedTestResult testResult, InterpreterStatus status, Exception exception) {

		//Exceptions check
		//testResult.throwsException() => exception != null
		assertTrue("The test was expected to throw an exception but did not!",!testResult.throwsException() || exception != null);
		//!testResult.throwsException() => exception == null
		assertTrue("The test threw an unexpected exception : " + exception,testResult.throwsException() || exception == null);
				
		//Events 
		if(!testResult.isInterleaved())
		{
			assertTrue(testResult.getFirstEventTrace() + " != " +status.getToplevelProcessInfo().getVisibleTrace() ,testResult.getFirstEventTrace()
					.equals(status.getToplevelProcessInfo().getVisibleTrace()));
		}
		else
		{
			boolean foundMatch = false;
			//If we have interleaving it must be one of the possible traces
			List<String> resultTrace = status.getToplevelProcessInfo().getVisibleTrace();
			for(List<String> trace : testResult.getEventTraces())
			{
				foundMatch |= trace.equals(resultTrace);
				
			}
			
			assertTrue(foundMatch);
		}
		
		//TimedTrace
		
		//Interpreter state
		Assert.assertEquals(testResult.getInterpreterState(), status.getInterpreterState());
	}

	@Parameters
	public static Collection<Object[]> getCmlfilePaths() {

		List<Object[]> paths = addFilesInFolder("src/test/resources/action/");
		paths.addAll(addFilesInFolder("src/test/resources/process/"));
		paths.addAll(addFilesInFolder("src/test/resources/examples/"));
		paths.addAll(addFilesInFolder("src/test/resources/classes/"));
		
		return paths;
	}
	
	private static List<Object[]> addFilesInFolder(String folderPath)
	{
		
		//Make filter to only get the files that ends with '.cml'
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cml");
			}
		};
		
		//Add the folders to search in
		File folder = new File(folderPath);
		String[] children = folder.list(filter);
		
		List<Object[]> paths = new Vector<Object[]>();
		
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(new Object[] { folder.getPath() + "/" + children[i] });
			}
		}
		
		return paths;
	}
}
