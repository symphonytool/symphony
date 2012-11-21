package eu.compassresearch.core.interpreter.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

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
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.interpreter.api.InterpreterStatus;
import eu.compassresearch.core.interpreter.runtime.VanillaCmlInterpreter;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;

@RunWith(Parameterized.class)
public class InterpretAllCmlFilesTest {

	private String filePath;

	public InterpretAllCmlFilesTest(String filePath) {
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

		// Call factory method to build parser and lexer
		CmlParser parser = CmlParser.newParserFromSource(ast);

		assertTrue(parser.parse());

		// Type check
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				Arrays.asList(new PSource[] { ast }), null);

		// assertTrue(cmlTC.typeCheck());
		// For now it does not have to typecheck
		cmlTC.typeCheck();

		CmlInterpreter interpreter = new VanillaCmlInterpreter(ast);

		interpreter.execute();

		checkResult(testResult, interpreter.getStatus());
	}

	private void checkResult(TestResult testResult, InterpreterStatus status) {
		assertTrue(testResult.getVisibleTrace()
				.equals(status.getVisibleTrace()));

	}

	@Parameters
	public static Collection getCmlfilePaths() {

		File dir = new File("src/test/resources/process/");
		List<Object[]> paths = new Vector<Object[]>();

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".cml");
			}
		};

		String[] children = dir.list(filter);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				paths.add(new Object[] { dir.getPath() + "/" + children[i] });
			}
		}

		return paths;
	}
}
