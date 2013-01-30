package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import junit.framework.Assert;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.TestUtil;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class Utilities {

	private static boolean parseWithANTLR(PSource sourceIn) throws IOException
	{
		// RWL: Change to do parsing the ANTLR way :)
		ANTLRInputStream in = null;
		
		if (sourceIn instanceof AInputStreamSource)
			in = new ANTLRInputStream(((AInputStreamSource)sourceIn).getStream());
		
		if (sourceIn instanceof AFileSource)
			in = new ANTLRInputStream( new FileInputStream(((AFileSource)sourceIn).getFile()));
		
		CmlLexer lexer = new CmlLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmlParser parser = new CmlParser(tokens);

		try {
			sourceIn.setParagraphs(parser.source());
			return true;
		} catch (RecognitionException e) {
			e.printStackTrace();
			return false;
		}

	}
	
    public static PSource makeSourceFromString(String cmlSource)
	    throws IOException {
	InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
	AInputStreamSource source = new AInputStreamSource();
	source.setOrigin("Test Parameter");
	source.setStream(cmlSourceIn);

	Assert.assertTrue("Filed to parse", parseWithANTLR(source));

	// Type check
	CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
		Arrays.asList(new PSource[] { source }), null);
	boolean tcResult = cmlTC.typeCheck();
	assertTrue("Test failed on Typechecker", tcResult);

	return source;
    }

    public static PSource makeSourceFromFile(String filePath)
	    throws IOException, AnalysisException {
	File f = new File(filePath);
	AFileSource ast = new AFileSource();
	ast.setName(f.getName());
	ast.setFile(f);

	// Call factory method to build parser and lexer
	
	assertTrue("Test failed on parser", parseWithANTLR(ast));

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
