package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;

class CmlTauEvent extends AbstractCmlEvent implements CmlSpecialEvent {

	protected static CmlTauEvent instance = null;
	final static String tauString = "\u03C4".toLowerCase();
	private final INode transitionSrcNode;
	private final INode transitionDstNode;
	private String transitionText = null;
						
	public CmlTauEvent(CmlBehaviourThread source, INode transitionSrcNode, INode transitionDstNode, String transitionText)
	{
		super(source);
		this.transitionSrcNode = transitionSrcNode;
		this.transitionDstNode = transitionDstNode;
		this.transitionText = transitionText;
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
