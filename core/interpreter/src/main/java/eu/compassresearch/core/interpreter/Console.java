package eu.compassresearch.core.interpreter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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

}
