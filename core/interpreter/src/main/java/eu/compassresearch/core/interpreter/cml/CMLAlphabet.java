package eu.compassresearch.core.interpreter.cml;

import java.util.HashSet;
import java.util.Set;

public class CMLAlphabet {

	private Set<CMLCommunication> alphabet;
	private boolean hasTau;
	public CMLAlphabet()
	{
		this.alphabet = new HashSet<CMLCommunication>();
		hasTau = false;
	}
	
	public CMLAlphabet(boolean hasTau)
	{
		this.alphabet = new HashSet<CMLCommunication>();
		this.hasTau = hasTau;
	}
	
	public CMLAlphabet(Set<CMLCommunication> comms)
	{
		this.alphabet = comms;
	}
	
	public Set<CMLCommunication> getAllCommunications()
	{
		return new HashSet<CMLCommunication>(alphabet);
	}
	
	public boolean containsCommunication(CMLCommunication com)
	{
		return alphabet.contains(com);
	}
	
	/**
	 * This determins whether the alphabet contains the tau event
	 * @return true if the tau event is contained else false
	 */
	public boolean containsTau()
	{
		return hasTau;
	}
	
	
}
