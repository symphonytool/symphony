package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CMLTauEvent extends SpecialEvent {

	protected static CMLTauEvent instance = null;
	final static String tauString = "\u03C4".toLowerCase();
	private final INode transitionNode;
	
	public static CMLTauEvent newTauEvent(INode transitionNode)
	{
		return new CMLTauEvent(transitionNode);  
	}
		
	public static CMLTauEvent referenceTauEvent()
	{
		if(instance == null)
			instance = new CMLTauEvent(null);
		
		return instance;
	}
	
	protected CMLTauEvent(INode transitionNode)
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
		
		if(!(obj instanceof CMLTauEvent))
			return false;
		else
			return true;
	}

}
