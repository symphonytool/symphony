package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;

public class TauTransition extends AbstractCmlTransition
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -230317087741433869L;

	protected static TauTransition instance = null;
	// public final static String tauString = "\u03C4".toLowerCase();
	private String transitionMessage = null;
	private final INode destinationNode;
	private final String kind = "tau";

	public TauTransition(CmlBehaviour eventSource, INode destinationNode,
			String transitionMessage)
	{
		super(eventSource);
		if (destinationNode == null)
		{
			throw new NullPointerException("A tau transition must have a destination node");
		}
		this.destinationNode = destinationNode;

		setTransitionMessage(transitionMessage);
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

	@Override
	public String toString()
	{
		if(this.getEventSources().isEmpty())
		{
			return kind+" "+transitionMessage+ " --- missing event source";
		}
		// We now that it always has one source because it an internal transition
		final CmlBehaviour source = this.getEventSources().iterator().next();
	
		if (CmlRuntime.expandShowHiddenEvents())
		{
			INode transitionSrcNode = source.getNextState().first;
			return kind
					+ "("
					+ formatNode(transitionSrcNode)
					+ (transitionMessage != null ? "--" + transitionMessage
							+ "->" : "->")

					+ formatNode(destinationNode) + ") : "
					+ getEventSources();
		} else
		{
			return kind;
		}
	}
	
	String formatNode(INode node)
	{
		String s ="'"+ (node+"").replace('\n', ' ').replace('\t', ' ')+"'";
		final int MAX=30;
		if(s.length()>MAX)
		{
			return s.substring(0,MAX-4)+"...'";
		}
		return s;
	}

	@Override
	public int hashCode()
	{
		return kind.hashCode() + destinationNode.hashCode()
				+ this.eventSources.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{

		if (!(obj instanceof TauTransition))
		{
			return false;
		} else
		{
			TauTransition otherSilent = (TauTransition) obj;
			return super.equals(otherSilent);
		}
	}
}
