package eu.compassresearch.core.interpreter.debug;

/**
 * Possible debugging commands
 * @author akm
 *
 */
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
