package eu.compassresearch.core.interpreter.debug.messaging;

public enum CmlRequest {

	CHOICE,
	SETUP;
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
