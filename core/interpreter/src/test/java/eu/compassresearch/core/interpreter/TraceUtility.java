package eu.compassresearch.core.interpreter;

import java.util.List;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;

public class TraceUtility
{
	public static String traceToString(List<CmlTransition> trace)
	{
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < trace.size(); i++)
		{
			CmlTransition e = trace.get(i);
			if (i > 0)
			{
				result.append(",");
			}

			result.append(e.toString());
		}

		return result.toString();
	}

}
