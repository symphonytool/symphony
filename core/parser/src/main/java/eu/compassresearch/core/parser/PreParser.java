package eu.compassresearch.core.parser;

import java.io.InputStream;
import java.util.Scanner;



/**
 * Utility class for preparing CML files
 * @author kel
 *
 */
public class PreParser
{
	public enum StreamType
	{
		Plain, Latex
	};

	public static StreamType detectStreamType(InputStream input)
	{
		Scanner s = new Scanner(input);
		try
		{

			// latex \begin{cml}
			// regex \\begin\{cml\}
			// java \\\\begin\\{cml\\}
			if (s.findWithinHorizon("\\\\begin\\{cml\\}", 0) != null)
			{
				return StreamType.Latex;
			} else
			{
				return StreamType.Plain;
			}

		} finally
		{
			s.close();
		}
	}
	
	
	public static InputStream getInputStream(InputStream input,StreamType type)
	{
		switch (type)
		{
			case Latex:
				return new LatexCmlEnvInputStream(input);
			case Plain:
			default:
				return input;
		}
	}
}
