package eu.compassresearch.core.interpreter;


public enum CoSimulationMode
{
	Standard("std"), CoSimCoordinator("server"), CoSimClient("client");
	public final String tag;

	CoSimulationMode(String tag)
	{
		this.tag = tag;
	}

	public static CoSimulationMode fromString(String string)
	{
		for (CoSimulationMode mode : CoSimulationMode.values())
		{
			if (mode.tag.equals(string))
			{
				return mode;
			}
		}

		return null;
	}
}
