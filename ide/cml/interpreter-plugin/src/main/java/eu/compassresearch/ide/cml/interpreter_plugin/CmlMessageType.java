package eu.compassresearch.ide.cml.interpreter_plugin;

public enum CmlMessageType {

	RESPONSE,REQUEST,STATUS;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
