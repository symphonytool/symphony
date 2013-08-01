package eu.compassresearch.pog.tests;

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
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.obligations.CmlPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

public class AdHocTest {

	@Test
	public void testPog() throws AnalysisException, IOException {
		String file = "src/test/resources/adhoc/test.cml";
		System.out.println("Processing " + file);
		IProofObligationList poList = runPog(file);

		for (IProofObligation po : poList) {
			String pretty = po.getValueTree().toString();
			System.out.println(pretty);
		}

	}

	private IProofObligationList runPog(String file) throws IOException,
			AnalysisException {

		CmlProofObligationList poList = new CmlProofObligationList();
		List<INode> ast = parseAndTC(file);
		for (INode node : ast) {
			poList.addAll(node.apply(new ProofObligationGenerator(),
					new CmlPOContextStack()));
		}
		return poList;
	}

	private static List<INode> parseAndTC(String filePath) throws IOException,
			AnalysisException {

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
		assertTrue("Test failed on TC errors", tcResult);

		List<INode> r = new LinkedList<INode>();
		for (INode n : ast.getParagraphs()) {
			r.add(n);
		}
		return r;
	}

	private static boolean parseWithANTLR(PSource sourceIn) throws IOException {
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
