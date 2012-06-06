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
		    ASourcefileSourcefile currentTree = new ASourcefileSourcefile();
		    FileReader input = new FileReader(source);
		    CmlLexer lexer = new CmlLexer(input);
		    CmlParser parser = new CmlParser(lexer);
		    if (parser.parse()) { runAnalysis(sourceForest); }
		    else { handleError(parser, source); return; }
		}


	    // Done
	    return;
	} catch (Exception e)
	    {


	    }
    }


    private static List<File> checkInput(String[] args)
    {
	List<File> result = new LinkedList<File>();
	for(String arg : args)
	    {
		File f = new File(arg);
		if (f.exists() && f.canRead())
		    result.add(f);
		else
		    { handleError(null, f);return null; }
	    }
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
	// Run an empty analysis
	DepthFirstAnalysisAdaptor analysis = new DepthFirstAnalysisAdaptor();
	for(ASourcefileSourcefile source : sources) source.apply(analysis);

	// Type checking
	

	
    }
}
