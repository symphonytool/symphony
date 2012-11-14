package eu.compassresearch.core.parser.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.preview.DotGraphVisitor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class ParserUnitTest {

    @Before
    public void setUp() {
    }

    private void parseCml(String cml) throws IOException, AnalysisException, InterruptedException
    {
    	String charset = "utf-8"; // your charset
    	byte[] bytes = cml.getBytes(charset);
    	
//    	File source = new File(filePath);
//    	FileReader input = new FileReader(source);
    	bytes = Files.readAllBytes(Paths.get("/home/akm/sandbox/overture_cml/docs/cml-examples/process-action-generalisedParallelism.cml"));
    	
    	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    	InputStreamReader isr = new InputStreamReader(bais);
    	PSource ast = new AFileSource(null,"Test");
    	CmlLexer lexer = new CmlLexer(isr);
    	CmlParser parser = new CmlParser(lexer);
    	parser.setDebugLevel(1);
    	parser.setDocument(ast);
    	assertTrue(parser.parse());

    	
    	//remove when done
    	DotGraphVisitor dgv = new DotGraphVisitor();
    	parser.getDocument().apply(dgv,null);
    	writeGraphResult(dgv,"out.gv");
    	
    	String[] cmd = {"/bin/sh", "-c",  "dot -Tpng out.gv > out.png && eog out.png"};
    	Process p = Runtime.getRuntime().exec(cmd);
    	//p.waitFor();
    }
    
    private static void writeGraphResult(DotGraphVisitor dga, String fileName) throws IOException {

    	File outFile = new File(fileName);
    	FileWriter fw = new FileWriter(outFile);
    	fw.write(dga.getResultString());
    	fw.close();
	}

    @Test
    public void testVarious() throws IOException, AnalysisException, InterruptedException {
    	parseCml("class Test = begin functions fn: int -> int fn() == 42 values a = fn() end");
    }
    
//  @Test
//  public void testProcessReplicationSemi() throws IOException, AnalysisException, InterruptedException {
//	parseCml("process test = ;i in set {2,3} @ A(i)");
//  }

    
}
