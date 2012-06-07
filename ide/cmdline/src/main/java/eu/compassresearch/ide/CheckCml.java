/**
 *
 * Author: Rasmus Lauritsen
 * Create: 2012-06-06
 * Org: Aarhus University
 *
 *
 * This file is part of the CML checker created for the FP7 EU
 * COMPASS project.
 *
 */

import java.io.File;
import java.io.FileReader;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.analysis.intf.IAnalysis; 
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.CmlTypeChecker;
import org.overture.ast.program.ASourcefileSourcefile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CheckCml {


    private static final String HELLO   = "COMPASS command line CML Checker";

    // Point of Entry 
    public static void main(String[] args)
    {
	try {
	    Input inp;
	     List<ASourcefileSourcefile> sourceForest = new LinkedList<ASourcefileSourcefile>();
	
	    // Say hello
	    System.out.println(HELLO + " - " + CmlParser.Info.CML_LANG_VERSION);
	
	    // inputs
	    if ( (inp = checkInput(args)) == null) return;
	
	    // build the forest
	    for(File source : inp.sourceFiles)
		{
		    System.out.println("Checking file: "+source);
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
	    runAllAnalysis(inp, sourceForest);

	    // Done
	    return;
	} catch (Exception e)
	    {
		System.out.println("Error: "+e.getMessage());
	    }
    }

    /*************************************************************
     ** Switches and parsing input
     *************************************************************/
    // Register switches the program accepts
    private enum Switch {
	// Swich Symbol, Switch Description and boolean whether or not
	// the switch requires an argument.
	PARSE_ONLY("po", "Parse Only, stop analysis after the parse phace.", false),
	    TYPE_CHECK_ONLY("tco","Type Check Only, stop checking after the type checking phase.", false),
	    NOTC("notc", "No type checking, the type checking phase is omitted.", false),
	    COE("coe", "Continue on Exception, even if an exception is thrown we continue", false),
	    SOE("soe", "Silence on Exception, if an exception occurs in an analysis supress its output.", false),
	    ;

	// Switch state
	private String sw;
	private String description;
	private String value;
	private boolean expectsValue;

	// constructor 
	Switch(String sw, String description, boolean expectsValue)
	    {
		this.sw = sw;
		this.description = description;
		this.expectsValue = expectsValue;
	    }

	public String getSw() { return sw; }
	public String getValue() { return value; }
	
	public  String toString() {
	    return "-"+sw + " \t - \t " + description;
	}

	public static Switch fromString(String arg, StringBuilder error)
	{
	    if (!arg.startsWith("-")) return null;
	    String swandval = arg.substring(1);
	    String[] swval = swandval.split("=");
	    for( Switch sw : Switch.values())
		if (sw.sw.equals(swval[0]))
		    {
			// The switch exists, requies an argument and there is an arguemtn for it
			if (swval.length > 1 && sw.expectsValue)
			    {
				sw.value = swval[1];
				return sw;
			    }
			else
			    // The switch exists, requires no argument and there is none
			    if (swval.length == 1 && ! sw.expectsValue )
				{
				    return sw;
				}
			    else
				{
				    // Expected argument and actual argument not matching up
				    error.append("Switch argument mismatch. The given switch "+swval[0]+" requires "+(sw.expectsValue?"one":"no") + " argument." );
				    return null;
				}

		    }
	    error.append("No such switch :"+swval[0]);
	    return null;
	}

	public static String listSwitches()
	{
	    StringBuilder sb = new StringBuilder();
	    for(Switch sw : Switch.values())
		sb.append("\t"+sw.toString()+"\n");
	    return sb.toString();
	}
    };

    private static class Input {
	Input() {
	    this.sourceFiles = new LinkedList<File>();
	    this.switches    = new LinkedList<Switch>();
	};

	List<File> sourceFiles;

	List<Switch> switches;

	boolean isSwitchOn(Switch s) {
	    return switches.contains(s);
	}
    };


    private static void printUsage()
    {
	System.out.println("\nUsage: cmlc [Switches] <file1>, ..., <fileN>\n[Switches]:");
	System.out.println(Switch.listSwitches());
    }

    private static Input checkInput(String[] args)
    {
	StringBuilder switchError = new StringBuilder();
	Input r = new Input();
	
	// Check args do point at readable files
	for(String arg : args)
	    {
		// is it a switch or a file
		if (arg.startsWith("-"))
		    {
			Switch sw = Switch.fromString(arg, switchError);
			if (sw == null)
			    { 
				System.out.println(switchError.toString());
				printUsage(); return null; 
			    }
			r.switches.add(sw);
		    }
		else
		    {
			// it must be a file
			File f = new File(arg);
			if (f.canRead())
			    r.sourceFiles.add(f);
			else
			    { handleError(null, f);return null; }
		    }
	    }
	
	// No files provided
	if (r.sourceFiles.size() == 0)
	    { printUsage(); return null; }


	// Okay, at least one file ready 
	return r;
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

    private static boolean runAnalysis(Input input, IAnalysis analysis, List<ASourcefileSourcefile> sources)
    {
	boolean continueOnException = input.isSwitchOn(Switch.COE);
	boolean silentOnException = input.isSwitchOn(Switch.SOE);
	
	for(ASourcefileSourcefile source : sources)
	    {
		try {
		    source.apply(analysis);
		}
		catch (Exception e)
		    {
			if (!silentOnException)
			    {
				e.printStackTrace();
			    }
			
			if (!continueOnException) { 
			    return false;
			}
			 
		    }
	    }
	return true;
    }

    private static void runAllAnalysis(Input input, List<ASourcefileSourcefile> sources)
    {
	// Inform parsing went well and analysis has begon
	System.out.println(sources.size()+" file(s) successfully parsed, starting analysis.");

	// Check The Parse Only Switch
	if (input.isSwitchOn(Switch.PARSE_ONLY)) return;

	// Run an empty analysis
	IAnalysis empty = new DepthFirstAnalysisAdaptor();
	runAnalysis(input, empty, sources);
	
	// Type checking
	if (!input.isSwitchOn(Switch.NOTC)) // check no type checking switch
	    {
		IAnalysis typeChecker = new CmlTypeChecker();
		runAnalysis(input, typeChecker, sources);
	    }
	
	// Check The Type Check Only Switch
	if (input.isSwitchOn(Switch.TYPE_CHECK_ONLY)) return;	

	// Add more analysis here ...
    }
}
