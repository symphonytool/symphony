package eu.compassresearch.core.interpreter.api.transitions;

import java.util.List;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class HiddenTransition extends TauTransition //implements LabelledTransition
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4227425913454070026L;
	private final LabelledTransition hiddenEvent;

	public HiddenTransition(CmlBehaviour eventSource, INode transitionDstNode,
			LabelledTransition hiddenTransition)
	{
		// super(eventSource, hiddenEvent.getChannelName());
		super(eventSource, transitionDstNode, "hidden");
		this.hiddenEvent = hiddenTransition;
		this.eventSources.addAll(hiddenTransition.getEventSources());
	}

	@Override
	public boolean equals(Object obj)
	{

		// FIXME It might not be the best idea to make a hidden event equal to what it hides
		if (obj instanceof HiddenTransition)
		{
			return this.getHiddenEvent().equals(((HiddenTransition) obj).getHiddenEvent());
		} else
		{
			return this.getHiddenEvent().equals(obj);
		}

	}

	@Override
	public int hashCode()
	{
		return super.hashCode() + this.getHiddenEvent().hashCode();
	}

	public LabelledTransition getHiddenEvent()
	{
		return hiddenEvent;
	}

//	@Override
//	public ChannelNameValue getChannelName()
//	{
//		return hiddenEvent.getChannelName();
//	}
//
//	@Override
//	public List<LabelledTransition> expand()
//	{
//		return hiddenEvent.expand();
//	}
//
//	@Override
//	public LabelledTransition rename(ChannelNameValue value)
//	{
//		return hiddenEvent.rename(value);
//	}
}
