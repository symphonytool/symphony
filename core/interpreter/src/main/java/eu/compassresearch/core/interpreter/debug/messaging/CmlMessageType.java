package eu.compassresearch.core.interpreter.debug.messaging;

public enum CmlMessageType {

	RESPONSE,REQUEST,STATUS,COMMAND;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
