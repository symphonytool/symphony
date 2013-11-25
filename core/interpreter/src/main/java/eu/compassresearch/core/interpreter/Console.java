package eu.compassresearch.core.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;
import org.overture.parser.lex.LexException;
import org.overture.parser.messages.VDMErrorsException;
import org.overture.parser.syntax.ParserException;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.AbstractValueInterpreter;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.utility.ValueParser;

public class Console
{
	private static class SilentOutputStream extends OutputStream
	{
		@Override
		public void write(int b) throws IOException
		{
		}

	}

	public static PrintStream out;
	public static PrintStream err;
	public static PrintStream debug;

	static
	{
		out = System.out;
		err = System.err;
		debug = System.out;
	}

	public static void quiet()
	{
		out = new PrintStream(new SilentOutputStream());
		err = new PrintStream(new SilentOutputStream());
		debug = new PrintStream(new SilentOutputStream());
	}

	public static void enableDebug(boolean enable)
	{
		if (enable)
		{
			debug = System.out;
		} else
		{
			debug = new PrintStream(new SilentOutputStream());
		}
	}

	public static void enableOut(boolean enable)
	{
		if (enable)
		{
			out = System.out;
		} else
		{
			out = new PrintStream(new SilentOutputStream());
		}
	}

	public static void readChannelNameValues(LabelledTransition chosenEvent)
	{
		ChannelNameValue channnelName = chosenEvent.getChannelName();

		for (int i = 0; i < channnelName.getValues().size(); i++)
		{
			Value currentValue = channnelName.getValues().get(i);

			if (!AbstractValueInterpreter.isValueMostPrecise(currentValue))
			{
				
				Value val = null;
				try
				{
					PType expectedType = channnelName.getChannel().getValueTypes().get(i);
					Context ctxt = chosenEvent.getEventSources().iterator().next().getNextState().second;
					
					while (val == null)
					{
						try
						{
							Console.out.println("Enter value : ");
							String expressionString = new BufferedReader(new InputStreamReader(System.in)).readLine();
							val = ValueParser.parse(expectedType, ctxt, expressionString);
						} catch (LexException e)
						{
							Console.out.println("Parse error input: "
									+ e.toString()+"\n");
						} catch (InterpreterRuntimeException|VDMErrorsException|ParserException e)
						{
							Console.out.println(e.toString()+"\n");
						}
					}
					channnelName.updateValue(i, val);
//					return;
				} catch (AnalysisException e)
				{
					throw new InterpreterRuntimeException("Analysis error in read user value", e);
				} catch (IOException e)
				{
					throw new InterpreterRuntimeException("IO error in read user value", e);
				} catch (Exception e)
				{
					if (e instanceof InterpreterRuntimeException)
					{
						throw (InterpreterRuntimeException) e;
					}
					throw new InterpreterRuntimeException("Unknown internal error in read user value", e);
				}
			}
		}
	}

}
