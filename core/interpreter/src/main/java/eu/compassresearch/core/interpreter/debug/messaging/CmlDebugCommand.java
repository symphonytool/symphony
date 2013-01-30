package eu.compassresearch.core.interpreter.debug.messaging;

public enum CmlDebugCommand {

	START,
	STOP,
	SUSPEND,
	RESUME,
	DISCONNECT;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
