package eu.compassresearch.core.parser.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import org.overture.ast.definitions.PDefinition;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;

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
    //  return new LinkedList<String>();
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
            //  watchedLog+= d + " " + "success!\n";
            // }
        };



    @Before
    public void setUp() {
    }

    @Test
    public void testParseCmlFile() throws Exception {
        System.out.println("Test "+testCounter+": "+filePath);
        testCounter++;

        FileInputStream source = new FileInputStream(filePath);
        ANTLRInputStream stream = new ANTLRInputStream(source);
        CmlLexer lexer = new CmlLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CmlParser parser = new CmlParser(tokens);

        List<PDefinition> ast = parser.source();
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
