package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;

@RunWith(value = Parameterized.class)
public class RunCmlExamplesTestCase {

	@Parameters
	public static Collection<Object[]> getData() {
		int i = 1;
		List<Object[]> files = new LinkedList<Object[]>();

		File examplesDir = new File("../../docs/cml-examples/");
		FileFilter cmlFiles = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".cml");
			}
		};
		for (File f : examplesDir.listFiles(cmlFiles)) {
			files.add(new Object[] { i++, f });
		}

		return files;
	}

	private static Map<String, List<String>> failingTC;
	static {
		failingTC = new HashMap<String, List<String>>();
		addFailingFile("isofclassexp.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		addFailingFile("process-intchoice.cml",
				TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
						.customizeMessage("RegisterProc"));
		addFailingFile("path-conversion-test-identifierStateDesignator2.cml",
				TypeErrorMessages.EXPECTED_TYPE_DEFINITION
						.customizeMessage("Byte"));
		addFailingFile("exp_inset.cml", "Argument of 'in set' is not a set.");
		addFailingFile("functions-implicit.cml",
				"Unable to resolve type name 'Byte'");
		addFailingFile("jpcw-register.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("REG"));
		addFailingFile("tokentype.cml", "Unable to resolve type name 'States'.");
		addFailingFile("class-values-pattern.cml",
				"Unable to resolve type name 'rec'.");
		addFailingFile("process-action-communication-multiwrite.cml",
				"The type init is undefined.");
		addFailingFile("process-action-alphabetisedParallelism.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-action-callstm.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		// addFailingFile("airline2.cml", "The type init is undefined.");
		addFailingFile("process-action-communication-multiwrite.cml",
				"The type load is undefined.");
		addFailingFile("process-action-whilestm.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("a"));
		addFailingFile("process-timed_interrupt.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL
						.customizeMessage("Default`A"));
		addFailingFile("process-replication-interleaving.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL
						.customizeMessage("Default`A"));
		addFailingFile("process-action-replicated-sequentialcomposition.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("A"));
		addFailingFile("process-alphabetised_parallelism.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL
						.customizeMessage("Default`A"));
		addFailingFile("process-startdeadline.cml",
				TypeErrorMessages.UNDEFINED_SYMBOL
						.customizeMessage("Default`A"));
	}

	private static void addFailingFile(String fileName, String... errors) {
		failingTC.put(fileName, Arrays.asList(errors));
	}

	private File file;
	private int number;

	public RunCmlExamplesTestCase(int i, File file) {

		this.number = i;
		this.file = file;
	}

	@Test
	public void test() throws IOException {

		System.out.print("#" + number + " " + file.getName());

		AFileSource source = new AFileSource();
		source.setFile(file);
		CmlParser parser = CmlParser.newParserFromSource(source);
		boolean parseOk = parser.parse();
		if (!parseOk)
			System.out.println();
		Assert.assertTrue("Parser failed", parseOk);

		List<PSource> cmlSources = new LinkedList<PSource>();
		cmlSources.add(source);
		VanillaCmlTypeChecker tc = ((VanillaCmlTypeChecker) VanillaFactory
				.newTypeChecker(cmlSources, null));
		boolean tcOK = tc.typeCheck();
		if (!failingTC.containsKey(file.getName())) {
			System.out.println("\t" + (tcOK ? "[OK]" : "[FAIL]"));
			Assert.assertTrue(buildErrorMessage(tc), tcOK);
		} else {
			HashSet<String> actualErrors = new HashSet<String>();
			for (CMLTypeError e : tc.getTypeErrors()) {
				actualErrors.add(e.getDescription());
			}

			List<String> expectedErrors = failingTC.get(file.getName());
			for (String expectedError : expectedErrors) {
				boolean found = actualErrors.contains(expectedError);
				if (!found)
					System.out.println(buildErrorMessage(tc));
				Assert.assertTrue("Expected Error:\n" + expectedError
						+ "\nbut it was not found.", found);
			}

			System.out.println("\t" + "[FAILED AS EXPECTED]");
		}
	}

	private String buildErrorMessage(VanillaCmlTypeChecker tc) {
		StringBuilder sb = new StringBuilder();
		sb.append("Expected type checking to be successful, the following errors were unexpected:\n");
		for (CMLTypeError error : tc.getTypeErrors())
			sb.append(error.toString() + "\n------\n");
		sb.append(file.getAbsolutePath());
		return sb.toString();
	}

}
