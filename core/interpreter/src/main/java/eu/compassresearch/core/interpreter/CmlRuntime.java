package eu.compassresearch.core.interpreter;

public class CmlRuntime
{
	private static boolean showHiddenEvents = true;
	public static boolean consoleMode = true;
	
	/**
	 * The basic random seed used by the interpreter
	 */
	public static int randomSeed = 9784345;

	public static boolean expandShowHiddenEvents()
	{
		return showHiddenEvents;
	}

	public static void expandHiddenEvents(boolean showHiddenEvents)
	{
		CmlRuntime.showHiddenEvents = showHiddenEvents;
	}

}
