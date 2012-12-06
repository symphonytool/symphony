package eu.compassresearch.ide.cml.interpreter_plugin.messaging;

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
