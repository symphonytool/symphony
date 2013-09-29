package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;


public abstract class AbstractSilentTransition extends AbstractCmlTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2234924498812985671L;
	private String transitionMessage = null;
	private final INode destinationNode;
	private final String kind;
	
	public AbstractSilentTransition(CmlBehaviour eventSource,INode destinationNode, String kind)
	{
		super(eventSource);
		if(destinationNode == null)
			throw new NullPointerException("A silent transition must have a destination node");
		this.destinationNode = destinationNode;
		this.kind = kind;
	}
		
	public String getTransitionMessage()
	{
		return transitionMessage;
	}

	protected void setTransitionMessage(String transitionMessage)
	{
		this.transitionMessage = transitionMessage;
	}

	public INode getDestinationNode()
	{
		return destinationNode;
	}
	
	public String getKind()
	{
		return kind;
	}
	
	@Override
	public String toString()
	{
		//We now that it always has one source because it an internal transition
		INode transitionSrcNode = this.getEventSources().iterator().next().getNextState().first;
		if(CmlRuntime.expandShowHiddenEvents())
			return kind + "(" + transitionSrcNode.getClass().getSimpleName() 
					+ ( transitionMessage != null ? "--" + transitionMessage + "->"  : "->" )

					+ destinationNode.getClass().getSimpleName() + ") : " + getEventSources();
		else
			return kind;
	}

	@Override
	public int hashCode() {
		return kind.hashCode() + destinationNode.hashCode() + this.eventSources.hashCode();
	}
	
}
