package eu.compassresearch.ide.cml.interpreter_plugin;

public enum CmlDebugCommand {

	START,STOP,SUSPEND;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
