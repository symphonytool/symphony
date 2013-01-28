package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;

public class CmlTauEvent extends CmlSpecialEvent {

	protected static CmlTauEvent instance = null;
	final static String tauString = "\u03C4".toLowerCase();
	private final INode transitionSrcNode;
	private final INode transitionDstNode;
	private String transitionText = null;
	
	public static CmlTauEvent newTauEvent(INode transitionSrcNode, INode transitionDstNode)
	{
		return new CmlTauEvent(transitionSrcNode,transitionDstNode);  
	}
		
	public static CmlTauEvent referenceTauEvent()
	{
		if(instance == null)
			instance = new CmlTauEvent(null,null);
		
		return instance;
	}
		
	public CmlTauEvent(INode transitionSrcNode, INode transitionDstNode)
	{
		this.transitionSrcNode = transitionSrcNode;
		this.transitionDstNode = transitionDstNode;
	}
	
	public CmlTauEvent(String text)
	{
		this.transitionSrcNode = null;
		this.transitionDstNode = null;
		this.transitionText = text;
	}
	
	public void setTransitionText(String text)
	{
		transitionText = text;
	}
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		if(CmlRuntime.isShowHiddenEvents())
			return tauString + "(" + ( transitionSrcNode != null ? transitionSrcNode.getClass().getSimpleName(): "initial" ) 
				
					+ ( transitionText != null ? "--" + transitionText + "-->"  : "->" )
			
					+ ( transitionDstNode != null ? transitionDstNode.getClass().getSimpleName(): "" ) + ")";
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

	@Override
	public CmlAlphabet getAsAlphabet() {
		
		Set<CmlEvent> events = new HashSet<CmlEvent>();
		events.add(this);
		return new CmlAlphabet(events);
	}

}
