package eu.compassresearch.core.parser.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

public class ParserUnitTest {

    @Before
    public void setUp() {
    }

    private void parseCml(String cml) throws IOException
    {
	String charset = "utf-8"; // your charset
	byte[] bytes = cml.getBytes(charset);
	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	InputStreamReader isr = new InputStreamReader(bais);
	//File source = new File(filePath);
	PSource ast = new AFileSource(null,"Test");
	//FileReader input = new FileReader(source);
	CmlLexer lexer = new CmlLexer(isr);
	CmlParser parser = new CmlParser(lexer);
	parser.setDocument(ast);
	assertTrue(parser.parse());
	
    }

    @Test
    public void testProcessReplicationSemi() throws IOException {
	parseCml("process test = ;i in set {2,3} @ A(i)");
    }

    
}
