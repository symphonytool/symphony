package eu.compassresearch.core;

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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IAnalysis;
import org.overture.ast.node.INode;
import org.overture.pog.PogAssistantFactory;
import org.overture.pog.POContextStack;

import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.preview.DotGraphVisitor;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.analysis.pog.visitors.ProofObligationGenerator;
import eu.compassresearch.core.interpreter.RandomSelectionStrategy;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterException;
import eu.compassresearch.core.parser.CmlLexer;
import eu.compassresearch.core.parser.CmlParser;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.examples.DivWarnAnalysis;

public class CheckCml {

	private static final String HELLO = "COMPASS command line CML Checker";

	// Point of Entry
	public static void main(String[] args) {
		try {
			Input inp;
			List<PSource> sourceForest = new LinkedList<PSource>();

			// Say hello
			System.out.println(HELLO + " - " + CmlParser.CML_LANG_VERSION);

			// inputs
			if ((inp = checkInput(args)) == null)
				return;

			// Two modes of operation, Interactive or Batch mode on files.
			if (inp.isSwitchOn(Switch.INTER)) {
				AInputStreamSource currentTree = new AInputStreamSource();
				currentTree.setOrigin("standard input");
				currentTree.setStream(System.in);
				
				ANTLRInputStream in = new ANTLRInputStream(currentTree.getStream());
				
				CmlLexer lexer = new CmlLexer(in);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				CmlParser parser = new CmlParser(tokens);
			
					try {
						currentTree.setParagraphs(parser.source());
					} catch (RecognitionException e) {
						e.printStackTrace();
					}
					
					sourceForest.add(currentTree);
				
			} else
				// build the forest
				for (File source : inp.sourceFiles) {
					System.out.println("Parsing file: " + source);
					AFileSource currentTree = new AFileSource();
					currentTree.setName(source.getName());
					ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(source));
					CmlLexer lexer = new CmlLexer(in);
					CmlParser parser = new CmlParser(new CommonTokenStream(lexer));
					try {
					currentTree.setParagraphs(parser.source());
					} catch (RecognitionException e)
					{
						e.printStackTrace();
					}
					sourceForest.add(currentTree);
				}

			// Run the analysis phase
			runAllAnalysis(inp, sourceForest);

			// Done
			return;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*************************************************************
	 ** Switches and parsing input
	 *************************************************************/
	// Register switches the program accepts
	private enum Switch {
		PARSE_ONLY("po", "Parse Only, stop analysis after the parse phase.",
				false), TYPE_CHECK_ONLY(
				"tco",
				"Type Check Only, stop checking after the type checking phase.",
				false), NOTC("notc",
				"No type checking, the type checking phase is omitted.", false), COE(
				"coe",
				"Continue on Exception, analysis continues even if an exception occurs.",
				false), SOE("soe",
				"Silence on Exception, supress exceptions in analysis.", false), EMPTY(
				"empty",
				"Empty Analysis, run the empty analysis (good for debugging).",
				false), DOTG("dotg",
				"Dot Graph, -dotg=<out> write ast dot graph to file <out>.",
				true), DWA("dwa", "Example, the Div Warn Analysis example",
				false), POG("pog",
				"Proof Obligation Generator, the proof obligation generator",
				false), INTER("i", "Interactive mode", false), EXEC(
				"e",
				"Simulation, -e=<processID>, simulate the process identified by <processID>",
				true);

		// Switch state
		private String sw;
		private String description;
		private String value;
		private boolean expectsValue;

		/**
		 * constructor
		 * 
		 * @param sw
		 *            - Switch Symbol
		 * @param description
		 *            - Switch Description
		 * @param expectsValue
		 *            - boolean whether or not the switch requires an argument.
		 * 
		 */
		Switch(String sw, String description, boolean expectsValue) {
			this.sw = sw;
			this.description = description;
			this.expectsValue = expectsValue;
		}

		public String getSw() {
			return sw;
		}

		public String getValue() {
			return value;
		}

		public String toString() {
			return "-" + sw + " \t - \t " + description;
		}

		public static Switch fromString(String arg, StringBuilder error) {
			if (!arg.startsWith("-"))
				return null;
			String swandval = arg.substring(1);
			String[] swval = swandval.split("=");
			for (Switch sw : Switch.values())
				if (sw.sw.equals(swval[0])) {
					// The switch exists, requies an argument and there is an
					// arguemtn for it
					if (swval.length > 1 && sw.expectsValue) {
						if (sw.value == null)
							sw.value = swval[1];
						else
							sw.value += swval[1];
						return sw;
					} else
					// The switch exists, requires no argument and there is none
					if (swval.length == 1 && !sw.expectsValue) {
						return sw;
					} else {
						// Expected argument and actual argument not matching up
						error.append("Switch argument mismatch. The given switch "
								+ swval[0]
								+ " requires "
								+ (sw.expectsValue ? "one" : "no")
								+ " argument.");
						return null;
					}

				}
			error.append("No such switch :" + swval[0]);
			return null;
		}

		public static String listSwitches() {
			List<String> toStrs = new LinkedList<String>();
			for (Switch sw : Switch.values())
				toStrs.add(sw.toString());

			Collections.sort(toStrs);

			StringBuilder sb = new StringBuilder();
			for (String sw : toStrs)
				sb.append("\t" + sw + "\n");
			return sb.toString();
		}
	};

	private static class Input {
		Input() {
			this.sourceFiles = new LinkedList<File>();
			this.switches = new LinkedList<Switch>();
		};

		List<File> sourceFiles;

		List<Switch> switches;

		boolean isSwitchOn(Switch s) {
			return switches.contains(s);
		}
	};

	private static void printUsage() {
		System.out
				.println("\nUsage: cmlc [switches] <file1>, ...,<fileN>\nSwitches:");
		System.out.println(Switch.listSwitches());
	}

	private static Input checkInput(String[] args) {
		StringBuilder switchError = new StringBuilder();
		Input r = new Input();

		// Check args do point at readable files
		for (String arg : args) {
			// is it a switch or a file
			if (arg.startsWith("-")) {
				Switch sw = Switch.fromString(arg, switchError);
				if (sw == null) {
					System.out.println(switchError.toString());
					printUsage();
					return null;
				}
				r.switches.add(sw);
			} else {
				// it must be a file
				File f = new File(arg);
				if (f.canRead())
					r.sourceFiles.add(f);
				else {
					return null;
				}
			}
		}

		// No files provided
		if (r.sourceFiles.size() == 0 && !r.isSwitchOn(Switch.INTER)) {
			printUsage();
			return null;
		}

		// Okay, at least one file ready
		return r;
	}



	/*************************************************************
	 * 
	 * Analysis
	 * 
	 * 
	 ************************************************************/

	private static String getAnalysisName(Object a) {
		String res = "";
		if (a == null)
			return res;

		res = a.getClass().getCanonicalName();
		try {
			Class<?> clz = a.getClass();
			Method getAnalysisName = clz.getMethod("getAnalysisName",
					new Class<?>[] {});
			getAnalysisName.setAccessible(true);
			res = (String) getAnalysisName.invoke(a, new Object[] {});

		} catch (Exception e) {
		    e.printStackTrace();
			return " e "+res;
		}
		return res;
	}

	/**
	 * 
	 * Helper methods run analysis controlling propergation of exceptions
	 * 
	 */

	private static boolean runAnalysis(Input input,
			AnalysisRunAdaptor analysis, List<PSource> sources) {
		boolean continueOnException = input.isSwitchOn(Switch.COE);
		boolean silentOnException = input.isSwitchOn(Switch.SOE);

		for (PSource source : sources) {
			try {
				System.out.print(" Running " + getAnalysisName(analysis)
						+ " on " + source.toString() + " ");
				analysis.apply(source);
				System.out.println();
			} catch (Exception e) {
				if (!silentOnException) {
					e.printStackTrace();
				}

				if (!continueOnException) {
					return false;
				}

			}
		}
		return true;
	}

	private static abstract class AnalysisRunAdaptor {

		private Object analysis;

		public AnalysisRunAdaptor(Object o) {
			this.analysis = o;
		}

		public String getAnalysisName() {
			return CheckCml.getAnalysisName(analysis);
		}

		public abstract void apply(INode node) throws AnalysisException;
	};

	private static void writeGraphResult(DotGraphVisitor dga, String fileName) {

		try {
			File outFile = new File(fileName);
			FileWriter fw = new FileWriter(outFile);
			fw.write(dga.getResultString());
			fw.close();
		} catch (Exception e) {
			System.out.println("\tAnalysis failed to write file: "
					+ e.getMessage());
		}
	}

	/**
	 * EXTENSION POINT: This is where you want to add more analys phases. That
	 * is, add a bit of code to the end this method. First we see what inputs
	 * you have to work with then follows an example.
	 * 
	 * @param input
	 *            - input contains the command line switches and the source
	 *            files.
	 * 
	 * @param sources
	 *            - result from parsing the source files in input. Aka. a forest
	 *            of ASTs.
	 * 
	 *            <b>An Example: </b><br>
	 *            </br> Supposed you have something called MyAnalysis
	 *            implementing IAnalysis we can extend this method with:
	 * 
	 *            <code>
	 * if (runAnalysis(input, new MyAnalysis(), sources))
	 *    System.out.println("MyAnalysis did not throw any exceptions.");
	 * else
	 *    System.out.println("MyAnalysis threw exceptions.");
	 * </code>
	 * 
	 *            Then an instance of MyAnalysis will be applied to all source
	 *            files parsed from command line arguments.
	 * 
	 *            Notice it should be possible to disable any phase with a
	 *            switch, except the parse phase. To add a switch for disabling
	 *            MyAnalysis we can do:
	 * 
	 *            <code>
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
	private static void runAllAnalysis(Input input, List<PSource> sources)

	{
		// Check The Parse Only Switch
		if (input.isSwitchOn(Switch.PARSE_ONLY))
			return;

		// Inform parsing went well and analysis has begun
		System.out.println(sources.size()
				+ " file(s) successfully parsed. Starting analysis:");

		if (!input.isSwitchOn(Switch.NOTC)) // check no type checking switch
		{
			final TypeIssueHandler issueHandler = VanillaFactory
					.newCollectingIssueHandle();
			final CmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(
					sources, issueHandler);

			AnalysisRunAdaptor r = new AnalysisRunAdaptor(typeChecker) {
				public void apply(INode root) throws AnalysisException {

					if (!(typeChecker.typeCheck())) {
						for (TypeIssueHandler.CMLTypeError e : issueHandler
								.getTypeErrors())
							System.out.println("\t" + e);
					}
					else
					    {
						System.out.println("[model types are ok]");
					    }
				}
			};
			runAnalysis(input, r, sources);
		}

		// Check The Type Check Only Switch
		if (input.isSwitchOn(Switch.TYPE_CHECK_ONLY))
			return;

		if (input.isSwitchOn(Switch.EMPTY)) {
			final IAnalysis empty = new DepthFirstAnalysisCMLAdaptor();
			AnalysisRunAdaptor r = new AnalysisRunAdaptor(empty) {
				public void apply(INode root) throws AnalysisException {
					root.apply(empty);
				}
			};
			runAnalysis(input, r, sources);
		}

		if (input.isSwitchOn(Switch.DOTG)) {
			final DotGraphVisitor dga = new DotGraphVisitor();
			AnalysisRunAdaptor r = new AnalysisRunAdaptor(dga) {

				@Override
				public void apply(INode node) throws AnalysisException {
					node.apply((IAnalysis) dga);
					writeGraphResult(dga, Switch.DOTG.getValue());
				}
			};
			runAnalysis(input, r, sources);
		}

		// Example Analysis DivWarnAnalysis
		if (input.isSwitchOn(Switch.DWA)) {
			final DivWarnAnalysis dwa = new DivWarnAnalysis();
			AnalysisRunAdaptor r = new AnalysisRunAdaptor(dwa) {

				@Override
				public void apply(INode node) throws AnalysisException {
					node.apply(dwa);
				}
			};
			runAnalysis(input, r, sources);
			for (String thing : dwa.getWarnings())
				System.out.println("\t" + thing);
		}

		// POG Analysis
		if (input.isSwitchOn(Switch.POG)) {
			// define pog object
			final ProofObligationGenerator pog = new ProofObligationGenerator(
					sources);

			System.out.println(pog.getAnalysisName());

			// create analysis run adaptor object of type AnalysisRunAdaptor,
			// supplying pog
			// object.
			AnalysisRunAdaptor r = new AnalysisRunAdaptor(pog) {
				public void apply(INode root) throws AnalysisException {
					POContextStack question = new POContextStack();
					root.apply(pog, question);
				}
			};

			// invoke runAnalysis method, giving switch input, run adaptor, and
			// source files
			runAnalysis(input, r, sources);

			//pog.getResults();
		}

		// Interpreter
		if (input.isSwitchOn(Switch.EXEC)) {
			if (input.isSwitchOn(Switch.NOTC))
				System.out.println("You can only interpret typechecked models!");
			else {

				try {

					final CmlInterpreter interpreter = VanillaInterpreterFactory.newInterpreter(sources);


					AnalysisRunAdaptor re = new AnalysisRunAdaptor(null) {
						public void apply(INode root) throws AnalysisException {
							try {
								interpreter.setDefaultName(Switch.EXEC.getValue());
								
								CmlSupervisorEnvironment sve = 
										VanillaInterpreterFactory.newDefaultCmlSupervisorEnvironment(new RandomSelectionStrategy());
								
								interpreter.execute(sve);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					};
					runAnalysis(input, re, sources);
				} catch (InterpreterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		// Add more analysis here ...
	}
}
