package eu.compassresearch.core.analysis.pog.obligations;

public abstract class CMLPOContext {

	abstract public String getGUIContext();
	abstract public String getIsabelleContext();

	//TODO Confirm need for this
	public String getName()
	{
		return "";		// Overridden in PONameContext
	}
}
