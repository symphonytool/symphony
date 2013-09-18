package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class HiddenTransition extends AbstractSilentTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4227425913454070026L;
	private final LabelledTransition hiddenEvent;

	public HiddenTransition(CmlBehaviour eventSource, INode transitionDstNode, LabelledTransition hiddenEvent) {
		//super(eventSource, hiddenEvent.getChannelName());
		super(eventSource,transitionDstNode,"hidden");
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}

	@Override
	public boolean equals(Object obj) {

		//FIXME It might not be the best idea to make a hidden event equal to what it hides
		if((obj instanceof HiddenTransition))
			return this.getHiddenEvent().equals(((HiddenTransition)obj).getHiddenEvent());
		else 
			return this.getHiddenEvent().equals(obj);

	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.getHiddenEvent().hashCode();
	}

	public LabelledTransition getHiddenEvent()
	{
		return hiddenEvent;
	}
}
