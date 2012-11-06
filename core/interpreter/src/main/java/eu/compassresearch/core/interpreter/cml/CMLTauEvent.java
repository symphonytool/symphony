package eu.compassresearch.core.interpreter.cml;

public class CMLTauEvent extends SpecialEvent {

	protected static CMLTauEvent instance = null;
	
	public static CMLTauEvent instance()
	{
		if(instance == null)
			instance = new CMLTauEvent(); 
		
		return instance; 
	}
		
	final String tauString = "\u03C4".toLowerCase();
	
	protected CMLTauEvent()
	{}
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		return tauString;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof CMLTauEvent))
			return false;
		else
			return true;
	}

}
