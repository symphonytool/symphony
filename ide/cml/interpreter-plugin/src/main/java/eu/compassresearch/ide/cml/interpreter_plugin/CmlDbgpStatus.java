package eu.compassresearch.ide.cml.interpreter_plugin;

public enum CmlDbgpStatus
{
	STARTING, RUNNING, BREAK, STOPPING, STOPPED, CONNECTION_CLOSED, WAITING_FOR_CONNECTION;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}