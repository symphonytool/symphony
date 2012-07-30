package eu.compassresearch.core.parser.test;

import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;
import eu.compassresearch.ast.node.*;
import eu.compassresearch.ast.util.*;
import eu.compassresearch.ast.program.*;
import eu.compassresearch.core.lexer.*;
import eu.compassresearch.core.parser.*;

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
	parseCml("process test = ;i in {2,3} @ A(i)");
    }

    
}
