package eu.compassresearch.core.interpreter;


public class CmlRuntime
{
	private static boolean showHiddenEvents = true;
	public static boolean consoleMode = true;


	public static boolean expandShowHiddenEvents()
	{
		return showHiddenEvents;
	}

	public static void expandHiddenEvents(boolean showHiddenEvents)
	{
		CmlRuntime.showHiddenEvents = showHiddenEvents;
	}

}
