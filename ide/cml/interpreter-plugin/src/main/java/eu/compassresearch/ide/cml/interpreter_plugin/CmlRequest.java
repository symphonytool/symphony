package eu.compassresearch.ide.cml.interpreter_plugin;

public enum CmlRequest {

	CHOICE;
	
	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}
}
