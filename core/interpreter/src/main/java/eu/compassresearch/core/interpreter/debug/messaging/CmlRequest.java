package eu.compassresearch.core.interpreter.debug.messaging;

public enum CmlRequest
{

	CHOICE, SETUP, GET_STACK_FRAMES, GET_CONTEXT_PROPERTIES;

	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
