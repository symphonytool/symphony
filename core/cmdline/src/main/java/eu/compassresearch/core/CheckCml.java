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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IProofObligation;
import org.overture.pog.pub.IProofObligationList;

import eu.compassresearch.core.analysis.pog.utility.PogPubUtil;
import eu.compassresearch.core.interpreter.CMLJ;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.ConsoleSelectionStrategy;
import eu.compassresearch.core.parser.ParserUtil;
import eu.compassresearch.core.parser.ParserUtil.ParserResult;
import eu.compassresearch.core.typechecker.VanillaFactory;
import eu.compassresearch.core.typechecker.api.ICmlTypeChecker;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler.CMLTypeWarning;

//import eu.compassresearch.examples.DivWarnAnalysis;

public class CheckCml
{

	private static class Input
	{
		Input()
		{
			this.sourceFiles = new LinkedList<File>();
			this.switches = new LinkedList<Switch>();
		};

		List<File> sourceFiles;

		List<Switch> switches;

		boolean isSwitchOn(Switch s)
		{
			return switches.contains(s);
		}
	};

	/**
	 * Switches and parsing input<br>
	 * Register switches the program accepts
	 */
	private static enum Switch
	{
		DOT("dot",
			"Dot Graph, -dot=<out> write ast dot graph to file <out>.",
			true),

		DOT_PATH("dotpath",
			"Path to dot, -dotpath=<path> a path where dot can be found.",
			true),

		POG("pog",
			"Proof Obligation Generator, the proof obligation generator",
			false),

		INTER("i", "Interactive mode", false),

		EXEC("e",
			"Simulation, -e=<processID>, simulate the process identified by <processID>",
			true),

		NO_TC("no-tc", "Disable typechecking", false);

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
		 */
		Switch(String sw, String description, boolean expectsValue)
		{
			this.sw = sw;
			this.description = description;
			this.expectsValue = expectsValue;
		}

		public String getValue()
		{
			return value;
		}

		public String toString()
		{
			return String.format("-%-10s -  %s", sw, description);
		}

		public static Switch fromString(String arg, StringBuilder error)
		{
			if (!arg.startsWith("-"))
			{
				return null;
			}
			String swandval = arg.substring(1);
			String[] swval = swandval.split("=");
			for (Switch sw : Switch.values())
			{
				if (sw.sw.equals(swval[0]))
				{
					// The switch exists, requies an argument and there is an
					// arguemtn for it
					if (swval.length > 1 && sw.expectsValue)
					{
						if (sw.value == null)
						{
							sw.value = swval[1];
						} else
						{
							sw.value += swval[1];
						}
						return sw;
					} else
					// The switch exists, requires no argument and there is none
					if (swval.length == 1 && !sw.expectsValue)
					{
						return sw;
					} else
					{
						// Expected argument and actual argument not matching up
						error.append("Switch argument mismatch. The given switch "
								+ swval[0]
								+ " requires "
								+ (sw.expectsValue ? "one" : "no")
								+ " argument.");
						return null;
					}

				}
			}
			error.append("No such switch :" + swval[0]);
			return null;
		}

		public static String listSwitches()
		{
			List<String> toStrs = new LinkedList<String>();
			for (Switch sw : Switch.values())
			{
				toStrs.add(sw.toString());
			}

			Collections.sort(toStrs);

			StringBuilder sb = new StringBuilder();
			for (String sw : toStrs)
			{
				sb.append("  " + sw + "\n");
			}
			return sb.toString();
		}
	};

	private static final String HELLO = "Symphony command line CML Checker";

	public static void main(String[] args)
	{
		try
		{
			Input input;
			List<PDefinition> sources = new LinkedList<PDefinition>();

			// Say hello
			System.out.println(HELLO);// + " - " + ParserUtil.getLanguageVersion());

			// inputs
			if ((input = checkInput(args)) == null)
			{
				return;
			}

			// Two modes of operation, Interactive or Batch mode on files.
			if (input.isSwitchOn(Switch.INTER))
			{
				String buf = readInputStreamAsString(System.in);
				ParserResult res = ParserUtil.parse(new File("console"), buf, "utf-8");
				if (res.errors.isEmpty())
				{
					sources.addAll(res.definitions);
				} else
				{
					res.printErrors(System.err);
				}

			} else
			{
				// build the forest
				System.out.println("Parsing files: " + input.sourceFiles);

				ParserResult res = ParserUtil.parse(input.sourceFiles);
				if (res.errors.isEmpty())
				{
					sources.addAll(res.definitions);
				} else
				{
					res.printErrors(System.err);
				}
			}

			if (!input.isSwitchOn(Switch.NO_TC))
			{
				typeCheck(sources);
			}

			if (input.isSwitchOn(Switch.DOT))
			{
				try
				{
					if (input.isSwitchOn(Switch.DOT_PATH))
					{
						org.overture.ast.preview.Main.dot = new File(Switch.DOT_PATH.getValue());
						org.overture.ast.preview.Main.makeImage(sources, "svg", new File(Switch.DOT.getValue()
								+ ".svg"));
					} else
					{
						System.err.println("Dot path must be given for dot to run.");
					}

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			// POG Analysis
			if (input.isSwitchOn(Switch.POG))
			{
				// define pog object

				List<INode> nodes = new Vector<INode>();
				nodes.addAll(sources);

				IProofObligationList pos = PogPubUtil.generateProofObligations(nodes);
				for (IProofObligation po : pos)
				{
					System.out.println(po);
				}
			}

			// Interpreter
			if (input.isSwitchOn(Switch.EXEC))
			{

				String processName = Switch.EXEC.getValue();
				try
				{
					CMLJ.interpret(null, new ConsoleSelectionStrategy(), processName, sources, new File("console"));
				} catch (CmlInterpreterException e)
				{
					e.printStackTrace();
				}
			}

			// Done
			return;
		} catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void typeCheck(List<PDefinition> sources)
	{
		final ITypeIssueHandler issueHandler = VanillaFactory.newCollectingIssueHandle();
		final ICmlTypeChecker typeChecker = VanillaFactory.newTypeChecker(sources, issueHandler);

		System.out.println("Type checking");
		typeChecker.typeCheck();
		if (issueHandler.hasErrors() || issueHandler.hasWarnings())
		{
			for (ITypeIssueHandler.CMLTypeError e : issueHandler.getTypeErrors())
			{
				System.out.println("\t" + e);
			}
			for (CMLTypeWarning e : issueHandler.getTypeWarnings())
			{
				System.out.println("\t" + e);
			}
		} else
		{
			System.out.println("[model types are ok]");
		}

	}

	private static void printUsage()
	{
		System.out.println("\nUsage: cmlc [switches] <file1> ... <fileN>\nSwitches:");
		System.out.println(Switch.listSwitches());
	}

	private static Input checkInput(String[] args)
	{
		StringBuilder switchError = new StringBuilder();
		Input r = new Input();

		// Check args do point at readable files
		for (String arg : args)
		{
			// is it a switch or a file
			if (arg.startsWith("-"))
			{
				Switch sw = Switch.fromString(arg, switchError);
				if (sw == null)
				{
					System.out.println(switchError.toString());
					printUsage();
					return null;
				}
				r.switches.add(sw);
			} else
			{
				// it must be a file
				File f = new File(arg);
				if (f.canRead())
				{
					r.sourceFiles.add(f);
				} else
				{
					return null;
				}
			}
		}

		// No files provided
		if (r.sourceFiles.size() == 0 && !r.isSwitchOn(Switch.INTER))
		{
			printUsage();
			return null;
		}

		// Okay, at least one file ready
		return r;
	}

	static String readInputStreamAsString(InputStream in) throws IOException
	{

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1)
		{
			byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		return buf.toString();
	}
}
