package eu.compassresearch.pog.tests.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.parser.PreParser;
import eu.compassresearch.core.parser.PreParser.StreamType;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

/**
 * Helper Class for the POG test framework. Helps process test inputs. This
 * class handles I/O and interaction with the COMPASS TC and Parser.
 * 
 * @author ldc
 */
public class TestInputHelper {

	/**
	 * Parse and type check a CML source. Parser and TC failures will lead to
	 * test failures.
	 * 
	 * @param name
	 *            the filepath for the model to be Parsed and TCed
	 * @return the list of {@link INode} corresponding to the AST for the model
	 */
	public static List<INode> getAstFromName(String name) throws IOException {
		File f = new File(name);
		AFileSource ast = new AFileSource();
		ast.setName(f.getName());
		ast.setFile(f);

		// Call factory method to build parser and lexer
		assertTrue("Test failed while parsing " + name, parse(ast));

		// Type check
		ITypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
		ICmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
				ast.getParagraphs(), errors);
		boolean tcResult = cmlTC.typeCheck();
		// Probably want to ignore warnings
		// assertFalse("Test failed with typechecker warnings",
		// errors.hasWarnings());
		assertFalse("Test failed with type checker errors", errors.hasErrors());
		assertTrue("Test somehow failed while type checking " + name, tcResult);

		List<INode> r = new LinkedList<INode>();
		for (INode n : ast.getParagraphs()) {
			r.add(n);
		}
		return r;
	}

	/**
	 * Construct an AST for a submitted expression in CML syntax. Syntax errors
	 * will lead to test failure. The resulting expression is not type checked.
	 * 
	 * @param exp
	 *            the CML syntax expression to be converted
	 * @return a {@link PExp} node for the submitted expression
	 */
	public static PExp string2Pexp(String exp) throws IOException,
			RecognitionException {
		CmlLexer lexer = null;
		CmlParser parser = null;
		InputStream stream = new ByteArrayInputStream(
				exp.getBytes(StandardCharsets.UTF_8));

		lexer = new CmlLexer(new ANTLRInputStream(PreParser.getInputStream(
				stream, StreamType.Plain)));

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		parser = new CmlParser(tokens);
		parser.sourceFileName = lexer.sourceFileName;

		return parser.expression().exp;
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
			fail("Test failed while parsing");
			return false;
		}

	}

}
