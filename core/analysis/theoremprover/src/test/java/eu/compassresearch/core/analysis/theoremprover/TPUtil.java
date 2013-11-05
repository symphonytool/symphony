package eu.compassresearch.core.analysis.theoremprover;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;

public class TPUtil {
//
//	public static PSource makeSourceFromFile(String filePath)
//    	    throws IOException, AnalysisException {
//    	File f = new File(filePath);
//    	AFileSource ast = new AFileSource();
//    	ast.setName(f.getName());
//    	ast.setFile(f);
//
//    	// Call factory method to build parser and lexer
//    	
//    	assertTrue("Test failed on parser", parseWithANTLR(ast));
//
//    	// Type check
//    	ITypeIssueHandler errors = VanillaFactory.newCollectingIssueHandle();
//    	CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
//    		Arrays.asList(new PSource[] { ast }), errors);
//    	boolean tcResult = cmlTC.typeCheck();
//
//    	assertTrue("Test failed on typechcker", tcResult);
//    	return ast;
//        }
//
//

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
	


}
