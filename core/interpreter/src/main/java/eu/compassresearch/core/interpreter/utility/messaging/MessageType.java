package eu.compassresearch.core.interpreter.utility.messaging;

public enum MessageType {

	RESPONSE,REQUEST,STATUS,COMMAND;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
