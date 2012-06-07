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
import java.util.Collections;
import java.lang.reflect.*;

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
		    System.out.println("Parsing file: "+source);
		    ASourcefileSourcefile currentTree = new ASourcefileSourcefile();
		    currentTree.setName(source.getName());
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
	PARSE_ONLY("po", "Parse Only, stop analysis after the parse phase.", false),
	    TYPE_CHECK_ONLY("tco","Type Check Only, stop checking after the type checking phase.", false),
	    NOTC("notc", "No type checking, the type checking phase is omitted.", false),
	    COE("coe", "Continue on Exception, analysis continues even if an exception occurs.", false),
	    SOE("soe", "Silence on Exception, supress exceptions in analysis.", false),
	    EMPTY("empty", "Empty Analysis, run the empty analysis (good for debugging).", false),
	    ;

	// Switch state
	private String sw;
	private String description;
	private String value;
	private boolean expectsValue;

	/** constructor 
	 * @param sw - Switch Symbol 
	 * @param description - Switch Description
	 * @param expectsValue - boolean whether or not the switch requires an argument.
	 *
	 */
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
				sw.value += swval[1];
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
	    List<String> toStrs = new LinkedList<String>();
	    for(Switch sw : Switch.values())
		toStrs.add(sw.toString());

	    Collections.sort(toStrs);

	    StringBuilder sb = new StringBuilder();
	    for(String sw : toStrs)
		sb.append("\t"+sw+"\n");
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
	System.out.println("\nUsage: cmlc [switches] <file1>, ...,<fileN>\nSwitches:");
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
     * Analysis
     *
     *
     ************************************************************/

    private static String getAnalysisName(IAnalysis a)
    {
	String res = "";
	if (a == null) return res;

	res = a.getClass().getCanonicalName();
	try 
	    {
		Class<?> clz = a.getClass();
		Method getAnalysisName = clz.getMethod("getAnalysisName", new Class<?>[] {});
		res = (String)getAnalysisName.invoke(a, new Object[] {});
		
	    } catch (Exception e)
	    {
		return res;
	    }
	return res;
    }

    /**
     *
     * Helper methods run analysis controlling propergation of exceptions
     *
     */
    private static boolean runAnalysis(Input input, IAnalysis analysis, List<ASourcefileSourcefile> sources)
    {
	boolean continueOnException = input.isSwitchOn(Switch.COE);
	boolean silentOnException = input.isSwitchOn(Switch.SOE);
	
	for(ASourcefileSourcefile source : sources)
	    {
		try {
		    System.out.println(" Running "+getAnalysisName(analysis)+" on "+source.getName());
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

    /**
     * EXTENSION POINT: This is where you want to add more analys
     * phases. That is, add a bit of code to the end this
     * method. First we see what inputs you have can work with then
     * follows an example.
     *
     * @param input - input contains the command line switches and the
     * source files.
     *
     * @param sources - result from parsing the source files in
     * input. Aka. a forest of ASTs.
     *
     * <b>An Example: </b><br></br>
     * Supposed you have something called MyAnalysis implementing
     * IAnalysis we can extend this method with:
     *
     * <code>
     * if (runAnalysis(input, new MyAnalysis(), sources))
     *    System.out.println("MyAnalysis did not throw any exceptions.");
     * else
     *    System.out.println("MyAnalysis threw exceptions.");
     * </code>
     *
     * Then an instance of MyAnalysis will be applied to all source
     * files parsed from command line arguments.
     *
     * Notice it should be possible to disable any phase with a
     * switch, except the parse phase. To add a switch for disabling
     * MyAnalysis we can do:
     *
     * <code>
     * ....
     * private enum Switch {
     *    .... ,
     *    DMYA("dmya", "Disable My Anaylsis, do not run My Analysis", false);
     *    ....
     *  }
     * ... 
     *
     * if (!input.isSwitchOn(DMYA)) 
     *    if (runAnalysis(input, new MyAnalysis(), sources))
     *       System.out.println("MyAnalysis did not throw any exceptions.");
     *    else
     *       System.out.println("MyAnalysis threw exceptions.");
     *
     * Have fun :)
     */
    private static void runAllAnalysis(Input input, List<ASourcefileSourcefile> sources)
    {
	// Check The Parse Only Switch
	if (input.isSwitchOn(Switch.PARSE_ONLY)) return;

	// Inform parsing went well and analysis has begon
	System.out.println(sources.size()+" file(s) successfully parsed. Starting analysis:");

	// Run the empty analysis
	if (input.isSwitchOn(Switch.EMPTY))
	    {
		IAnalysis empty = new DepthFirstAnalysisAdaptor();
		runAnalysis(input, empty, sources);
	    }
	
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
