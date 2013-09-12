package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class InternalTransition extends AbstractCmlTransition implements SilentTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -230317087741433869L;
	
	protected static InternalTransition instance = null;
	//public final static String tauString = "\u03C4".toLowerCase();
	public final static String tauString = "tau";
	private final INode transitionSrcNode;
	private final INode transitionDstNode;
	private String transitionText = null;
						
	public InternalTransition(CmlBehaviour source, INode transitionSrcNode, INode transitionDstNode, String transitionText)
	{
		super(source);
		this.transitionSrcNode = transitionSrcNode;
		this.transitionDstNode = transitionDstNode;
		this.transitionText = transitionText;
	}
	
	public InternalTransition(CmlBehaviour source, String transitionText)
	{
		super(source);
		this.transitionSrcNode = null;
		this.transitionDstNode = null;
		this.transitionText = transitionText;
	}
	
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		if(CmlRuntime.expandShowHiddenEvents())
			return tauString + "(" + ( transitionSrcNode != null ? transitionSrcNode.getClass().getSimpleName(): "initial" ) 
				
					+ ( transitionText != null ? "--" + transitionText + "-->"  : "->" )
			
					+ ( transitionDstNode != null ? transitionDstNode.getClass().getSimpleName(): "" ) + ") : " + getEventSources();
		else
			return tauString;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof SilentTransition))
			return false;
		else
			return super.equals(obj);
	}

	@Override
	public CmlTransitionSet getAsAlphabet() {
		
		Set<CmlTransition> events = new HashSet<CmlTransition>();
		events.add(this);
		return new CmlTransitionSet(events);
	}

}
