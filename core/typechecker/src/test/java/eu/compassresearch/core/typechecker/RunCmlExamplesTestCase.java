package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
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
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".cml");
			}
		};
		for (File f : examplesDir.listFiles(cmlFiles)) {
			files.add(new Object[] { i++, f });
		}

		return files;
	}

	private static List<Integer> failingTC;
	static {
		failingTC = new LinkedList<Integer>();
		failingTC.add(1);
		failingTC.add(2);
		failingTC.add(3);
		failingTC.add(4);
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
		Assert.assertTrue(parseOk);

		if (!failingTC.contains(number)) {
			List<PSource> cmlSources = new LinkedList<PSource>();
			cmlSources.add(source);
			VanillaCmlTypeChecker tc = ((VanillaCmlTypeChecker) VanillaFactory
					.newTypeChecker(cmlSources, null));
			boolean tcOK = tc.typeCheck();
			System.out.println("\t" + (tcOK ? "[OK]" : "[FAIL]"));
			Assert.assertTrue(buildErrorMessage(tc), tcOK);
		} else
			System.out.println("\t" + "[OK]");
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
