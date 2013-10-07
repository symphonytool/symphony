package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class InternalTransition extends AbstractSilentTransition
{
	private static final String internalString = "internal";

	public InternalTransition(CmlBehaviour source, INode transitionDstNode,
			String transitionText)
	{
		super(source, transitionDstNode, "internal");
		setTransitionMessage(transitionText);
	}

	// public InternalTransition(CmlBehaviour source, String transitionText)
	// {
	// super(source,transitionText);
	// }

	@Override
	public boolean equals(Object obj)
	{

		if (!(obj instanceof InternalTransition))
			return false;
		else
		{
			InternalTransition otherSilent = (InternalTransition) obj;
			return super.equals(otherSilent);
		}
	}

}
