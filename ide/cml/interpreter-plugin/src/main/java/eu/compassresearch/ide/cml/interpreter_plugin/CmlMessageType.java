package eu.compassresearch.ide.cml.interpreter_plugin;

public enum CmlMessageType {

	RESPONSE,REQUEST,STATUS,COMMAND;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
