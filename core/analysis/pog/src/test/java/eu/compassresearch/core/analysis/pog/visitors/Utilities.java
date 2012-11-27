package eu.compassresearch.core.analysis.pog.visitors;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;

public class Utilities {

    public static PSource makeSourceFromString(String cmlSource)
    {
      InputStream cmlSourceIn = new ByteArrayInputStream(cmlSource.getBytes());
      AInputStreamSource source = new AInputStreamSource();
      source.setOrigin("Test Parameter");
      source.setStream(cmlSourceIn);
      return source;
    }
    
    public static PSource makeSourceFromFile(String filePath) throws IOException, AnalysisException{
	File f = new File(filePath);
	AFileSource ast = new AFileSource();
	ast.setName(f.getName());
	ast.setFile(f);

	
	// Call factory method to build parser and lexer
	CmlParser parser = CmlParser.newParserFromSource(ast);
	assertTrue(parser.parse());

	// Type check
	CmlTypeChecker cmlTC = VanillaFactory.newTypeChecker(
			Arrays.asList(new PSource[] { ast }), null);

	// assertTrue(cmlTC.typeCheck());
	// For now it does not have to typecheck
	cmlTC.typeCheck();

	return ast;
    }
    
}
