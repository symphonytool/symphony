package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CmlTauEvent extends SpecialEvent {

	protected static CmlTauEvent instance = null;
	final static String tauString = "\u03C4".toLowerCase();
	private final INode transitionNode;
	
	public static CmlTauEvent newTauEvent(INode transitionNode)
	{
		return new CmlTauEvent(transitionNode);  
	}
		
	public static CmlTauEvent referenceTauEvent()
	{
		if(instance == null)
			instance = new CmlTauEvent(null);
		
		return instance;
	}
	
	protected CmlTauEvent(INode transitionNode)
	{
		this.transitionNode = transitionNode;
	}
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		if(CmlRuntime.isShowHiddenEvents())
			return tauString + "(" + ( transitionNode != null ? transitionNode.toString() : "Ref" ) + ")";
		else
			return tauString;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof CmlTauEvent))
			return false;
		else
			return true;
	}

}
