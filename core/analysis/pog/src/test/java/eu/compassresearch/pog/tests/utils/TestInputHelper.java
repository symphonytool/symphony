package eu.compassresearch.pog.tests.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

/**
 * Helper Class for the POG test framework. Helps generate test inputs.
 * 
 * This classes I/O and interaction
 * with the COMPASS TC and Parser.
 * @author ldc
 *
 */
public class TestInputHelper
{
	/** 
	 * Main helper method. Take a file name and runs it through the COMPASS
	 * parser and typechecker. Also causes a test failure if the
	 * model cannot be analyzed.
	 * <br>
	 * <br>
	 * One day I would like to replace the I/O stuff with Mocks...
	 * 
	 * @param name the filepath for the model to be Parsed and TCed
	 * @return the list of {@link INode} corresponding to the AST for the model
	 * @throws IOException file could not be found or some other I/O error.
	 */
	public static List<INode> getAstFromName(String name) throws IOException{
		File f = new File(name);
		AFileSource ast = new AFileSource();
		ast.setName(f.getName());
		ast.setFile(f);

		// Call factory method to build parser and lexer
		assertTrue("Test failed while parsing " + name, parse(ast));

		// Type check
		TypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
		CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				Arrays.asList(new PSource[] { ast }), errors);
		boolean tcResult = cmlTC.typeCheck();
		assertTrue("Test failed while typechecking " +name, tcResult);

		List<INode> r = new LinkedList<INode>();
		for (INode n : ast.getParagraphs()) {
			r.add(n);
		}
		return r;
	}

	private static boolean parse(PSource sourceIn) throws IOException {
		ANTLRInputStream in = null;

		if (sourceIn instanceof AInputStreamSource)
			in = new ANTLRInputStream(
					((AInputStreamSource) sourceIn).getStream());

		if (sourceIn instanceof AFileSource)
			in = new ANTLRInputStream(new FileInputStream(
					((AFileSource) sourceIn).getFile()));

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

}
