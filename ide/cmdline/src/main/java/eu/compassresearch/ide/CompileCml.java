/**
 *
 * Author: Rasmus Lauritsen
 * Create: 2012-06-06
 * Org: Aarhus University
 *
 *
 * This file is part of the CML compiler created for the FP7 EU
 * COMPASS project.
 *
 */

import java.io.File;
import java.io.FileReader;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import org.overture.ast.program.ASourcefileSourcefile;
import java.util.LinkedList;
import java.util.List;


public class CompileCml {

    private static final String CML_VERSION = "CMLv0.0";
    private static final String HELLO   = "COMPASS Cml Compiler";


    public static void main(String[] args)
    {
	try {
	     List<File> sources = null;
	     List<ASourcefileSourcefile> sourceForest = new LinkedList<ASourcefileSourcefile>();
	
	    // Say hello
	    System.out.println("COMPASS Cml Compiler " + CML_VERSION);
	
	    // inputs
	    if ( (sources = checkInput(args)) == null) return;
	
	    // build the forest
	    for(File source : sources)
		{
		    System.out.println("Compiling file: "+source);
		    ASourcefileSourcefile currentTree = new ASourcefileSourcefile();
		    FileReader input = new FileReader(source);
		    CmlLexer lexer = new CmlLexer(input);
		    CmlParser parser = new CmlParser(lexer);
		    parser.setDocument(currentTree);
		    if (!parser.parse())
			{ handleError(parser, source); return; }
		    else
			sourceForest.add(currentTree);
		
		}

	    // Run the analysis phase
	    runAnalysis(sourceForest);

	    // Done
	    return;
	} catch (Exception e)
	    {

	    }
    }


    private static void printUsage()
    {
	System.out.println("\nUsage: cmlc <file1>, ..., <fileN>\n");
    }
    private static List<File> checkInput(String[] args)
    {
	List<File> result = new LinkedList<File>();
	
	// No files provided
	if (args.length == 0)
	    { printUsage(); return null; }

	// Check args do point at readable files
	for(String arg : args)
	    {
		File f = new File(arg);
		if (f.canRead())
		    result.add(f);
		else
		    { handleError(null, f);return null; }
	    }

	// Okay, at least one file ready 
	return result;
    }

    private static void handleError(CmlParser parser, File input)
    {
	System.out.println("Errors in "+input);
    }

    /*************************************************************
     *
     * runAnalysis
     *
     ************************************************************/
    private static void runAnalysis(List<ASourcefileSourcefile> sources)
    {
	// Inform parsing went well and analysis has begon
	System.out.println(sources.size()+" file(s) successfully parsed, starting analysis.");

	// Run an empty analysis
	DepthFirstAnalysisAdaptor analysis = new DepthFirstAnalysisAdaptor();
	for(ASourcefileSourcefile source : sources) source.apply(analysis);
	
	// Type checking
	
	
	
    }
}
