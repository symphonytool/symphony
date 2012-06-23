package eu.compassresearch.ide;
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
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import org.overture.ast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.analysis.intf.IAnalysis; 
import org.overture.transforms.DotGraphVisitor;
import eu.compassresearch.core.lexer.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
// import eu.compassresearch.core.typechecker.CmlTypeChecker; 
// import eu.compassresearch.core.typechecker.TypeCheckInfo;
// import eu.compassresearch.examples.DivWarnAnalysis;
import org.overture.ast.program.ASourcefileSourcefile;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.lang.reflect.*;
import org.overture.ast.node.INode;
import org.overture.ast.util.ClonableFile;

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

	    // Two modes of operation, Interactive or Batch mode on files.
	    if (inp.isSwitchOn(Switch.INTER))
		{
		    ASourcefileSourcefile currentTree = new ASourcefileSourcefile();
		    Reader input = new BufferedReader( new InputStreamReader( System.in ) );
		    currentTree.setName("standard input");
		    currentTree.setFile(new ClonableFile(new File("-")));
		    CmlLexer lexer = new CmlLexer(input);
		    CmlParser parser = new CmlParser(lexer);
		    parser.setDocument(currentTree);
		    if (!parser.parse())
			{ handleError(parser, new File("-")); return; }
		    else
			sourceForest.add(currentTree);	    
		}
	    else
	    // build the forest
	    for(File source : inp.sourceFiles)
		{
		    System.out.println("Parsing file: "+source);
		    ASourcefileSourcefile currentTree = new ASourcefileSourcefile();
		    currentTree.setName(source.getName());
		    currentTree.setFile(new ClonableFile(source));
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
	    DOTG("dotg", "Dot Graph, -dotg=<out> write ast dot graph to file <out>.", true),
	    DWA("dwa", "Example, the Div Warn Analysis example", false),
	    INTER("i", "Interactive mode", false)
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
				if (sw.value == null)
				    sw.value = swval[1];
				else
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

    private static String getAnalysisName(Object a)
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
    private static boolean runAnalysis(Input input, AnalysisRunAdaptor analysis, List<ASourcefileSourcefile> sources)
    {
	boolean continueOnException = input.isSwitchOn(Switch.COE);
	boolean silentOnException = input.isSwitchOn(Switch.SOE);
	
	for(ASourcefileSourcefile source : sources)
	    {
		try {
		    System.out.println(" Running "+getAnalysisName(analysis)+" on "+source.getName());
		    analysis.apply(source);
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
    
    private static abstract class AnalysisRunAdaptor  {
	
	private Object analysis;
	public AnalysisRunAdaptor(Object o)
	{
	    this.analysis = o;
	}

	public String getAnalysisName()
	{
	    return  CheckCml.getAnalysisName(analysis);
	}

	public abstract void apply(INode node);
    };

    private static void writeGraphResult(DotGraphVisitor dga, String fileName)
    {

	try{
	    File outFile = new File(fileName);
	    FileWriter fw = new FileWriter(outFile);
	    fw.write(dga.getResultString());
	    fw.close();
	} catch (Exception e)
	    {
		System.out.println("\tAnalysis failed to write file: "+e.getMessage());
	    }
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
		final IAnalysis empty = new DepthFirstAnalysisAdaptor();
		AnalysisRunAdaptor r = new AnalysisRunAdaptor(empty) {
			public void apply(INode root) { root.apply(empty); }
		    };
		runAnalysis(input, r, sources);
	    }
	
	// Dot Graph Analysis
	if (input.isSwitchOn(Switch.DOTG))
	    {
		final DotGraphVisitor dga = new DotGraphVisitor();
		AnalysisRunAdaptor r = new AnalysisRunAdaptor(dga) {
			public void apply(INode root)
			{
			    root.apply(dga, null);
			}
		    };
		runAnalysis(input, r, sources);
		writeGraphResult(dga, Switch.DOTG.getValue());
	    }

	// Example Analysis DivWarnAnalysis
	// if (input.isSwitchOn(Switch.DWA))
	//     {
	// 	final DivWarnAnalysis dwa = new DivWarnAnalysis();
	// 	AnalysisRunAdaptor r = new AnalysisRunAdaptor(dwa) {
	// 		public void apply(INode root) { root.apply( dwa ); }
	// 	    };
	// 	runAnalysis(input, r, sources);
	// 	for(String s : dwa.getWarnings())
	// 	    {
	// 		System.out.println("\t"+s);
	// 	    }
	//     }

	// Type checking
	// if (!input.isSwitchOn(Switch.NOTC)) // check no type checking switch
	//     {
	// 	final CmlTypeChecker typeChecker = new CmlTypeChecker();

	// 	AnalysisRunAdaptor r = new AnalysisRunAdaptor(typeChecker) {
	// 		public void apply(INode root)
	// 		{
	// 		    root.apply(typeChecker, new TypeCheckInfo());
	// 		}
	// 	    };
	// 	runAnalysis(input, r , sources);
	//     }
	
	// Check The Type Check Only Switch
	if (input.isSwitchOn(Switch.TYPE_CHECK_ONLY)) return;	

	// Add more analysis here ...
    }
}
