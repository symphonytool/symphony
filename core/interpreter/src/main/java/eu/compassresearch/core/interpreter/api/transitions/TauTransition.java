package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class TauTransition extends AbstractSilentTransition
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -230317087741433869L;

	protected static TauTransition instance = null;
	// public final static String tauString = "\u03C4".toLowerCase();
	private final static String tauKind = "tau";

	public TauTransition(CmlBehaviour source, INode transitionDstNode,
			String transitionMessage)
	{
		super(source, transitionDstNode, tauKind);
		setTransitionMessage(transitionMessage);
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
