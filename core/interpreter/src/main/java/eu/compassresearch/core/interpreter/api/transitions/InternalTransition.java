package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class InternalTransition extends AbstractCmlTransition implements SilentTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -230317087741433869L;
	
	protected static InternalTransition instance = null;
	//public final static String tauString = "\u03C4".toLowerCase();
	public final static String tauString = "tau";
	private final INode transitionDstNode;
	private String transitionText = null;
						
	public InternalTransition(CmlBehaviour source, INode transitionDstNode, String transitionText)
	{
		super(source);
		this.transitionDstNode = transitionDstNode;
		this.transitionText = transitionText;
	}
	
	public InternalTransition(CmlBehaviour source, String transitionText)
	{
		super(source);
		this.transitionDstNode = null;
		this.transitionText = transitionText;
	}
	
	@Override
	public int hashCode() {
		return tauString.hashCode();
	}

	@Override
	public String toString() {
		//We now that it always has one source because it an internal transition
		INode transitionSrcNode = this.getEventSources().iterator().next().getNextState().first;
		if(CmlRuntime.expandShowHiddenEvents())
			return tauString + "(" + transitionSrcNode.getClass().getSimpleName() 
					+ ( transitionText != null ? "--" + transitionText + "-->"  : "->" )
			
					+ ( transitionDstNode != null ? transitionDstNode.getClass().getSimpleName(): "" ) + ") : " + getEventSources();
		else
			return tauString;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof InternalTransition))
			return false;
		else
		{
			InternalTransition otherSilent = (InternalTransition)obj;
			return super.equals(otherSilent);
		}
	}
}
