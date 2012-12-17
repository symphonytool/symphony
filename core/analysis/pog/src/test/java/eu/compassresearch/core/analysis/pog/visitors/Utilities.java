package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class Utilities {

    public static PSource makeSourceFromString(String cmlSource)
	    throws IOException {
	InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
	AInputStreamSource source = new AInputStreamSource();
	source.setOrigin("Test Parameter");
	source.setStream(cmlSourceIn);
	CmlParser parser = CmlParser.newParserFromSource(source);
	assertTrue("Test failed on parser", parser.parse());

	// Type check
	CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
		Arrays.asList(new PSource[] { source }), null);
	assertTrue("Test failed on Typechecker", cmlTC.typeCheck());

	return source;
    }

    public static PSource makeSourceFromFile(String filePath)
	    throws IOException, AnalysisException {
	File f = new File(filePath);
	AFileSource ast = new AFileSource();
	ast.setName(f.getName());
	ast.setFile(f);

	// Call factory method to build parser and lexer
	CmlParser parser = CmlParser.newParserFromSource(ast);
	assertTrue("Test failed on parser", parser.parse());

	// Type check
	TypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
	CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
		Arrays.asList(new PSource[] { ast }), errors);
	boolean tcResult = cmlTC.typeCheck();

	assertTrue(TestUtil.buildErrorMessage(errors, true), tcResult);
	return ast;
    }

    // Reusing code from Parser with a small change due to directory structures.
    public static Collection<Object[]> getCmlExamplesFilePaths() {
	File dir = new File("../../../docs/cml-examples");
	List<Object[]> paths = new Vector<Object[]>();

	FilenameFilter filter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".cml")
			&& !name.toLowerCase().contains("fail");
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
