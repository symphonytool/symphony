package eu.compassresearch.core.parser.test;

import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.*;
import java.io.*;
import eu.compassresearch.ast.node.*;
import eu.compassresearch.ast.util.*;
import eu.compassresearch.ast.program.*;
import eu.compassresearch.core.lexer.*;
import eu.compassresearch.core.parser.*;

@RunWith(Parameterized.class)
public class ParseAllCmlExampleFilesTest {

    private static int testCounter = 0;

    private String filePath;

    public ParseAllCmlExampleFilesTest(String filePath)
    {
	this.filePath = filePath;
    }
        
    // @Parameters
    // public static Collection params() {
    // 	return new LinkedList<String>();
    // }

    //private static String watchedLog;
    
    @Rule
    public TestWatcher watchman = new TestWatcher() {
	    @Override
            protected void failed(Throwable e, Description d) {
		//watchedLog+= d + "\n";
		
		String name = d.toString();
		int index = Integer.parseInt(name.substring(name.indexOf("[")+1, name.indexOf("]")));
		System.out.println("in file: " + 
				   ((Object[])ParseAllCmlExampleFilesTest.getCmlfilePaths().toArray()[index])[0]);
		System.out.println();
	    }
	    
	    // @Override
            // protected void succeeded(Description d) {
	    // 	watchedLog+= d + " " + "success!\n";
	    // }
	};

    
    
    @Before
    public void setUp() {
    }

    @Test
    public void testParseCmlFile() throws IOException {
	System.out.println("Test "+testCounter+": "+filePath);
	testCounter++;
	File source = new File(filePath);
	PSource ast = new AFileSource(null,filePath);
	FileReader input = new FileReader(source);
	CmlLexer lexer = new CmlLexer(input);
	CmlParser parser = new CmlParser(lexer);
	parser.setDocument(ast);
	assertTrue(parser.parse());
    }

    @Parameters
    public static Collection getCmlfilePaths() {
	File dir = new File("../../docs/cml-examples");
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
	    for (int i=0; i<children.length; i++) {
		// Get filename of file or directory
		paths.add(new Object[]{dir.getPath() + "/" + children[i]});
	    }
	}
	
	return paths;
    }
}
