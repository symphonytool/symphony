package eu.compassresearch.core.interpreter.api.transitions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class HiddenTransition extends AbstractLabelledTransition implements SilentTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4227425913454070026L;
	private final LabelledTransition hiddenEvent;

	public HiddenTransition(CmlBehaviour eventSource, LabelledTransition hiddenEvent) {
		super(eventSource, hiddenEvent.getChannelName());
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}

	private HiddenTransition(Set<CmlBehaviour> eventSources, LabelledTransition hiddenEvent) {
		super(eventSources, hiddenEvent.getChannelName());
		this.hiddenEvent = hiddenEvent;
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}

	@Override
	public List<LabelledTransition> expand() {
		//TODO The expanded events should also be wrapped in a HiddenEvent
		List<LabelledTransition> result = new LinkedList<LabelledTransition>();
		for(LabelledTransition ce : getHiddenEvent().expand())
			result.add(new HiddenTransition(this.eventSources, ce));
		return result;
	}

	//	@Override
	//	public INode getDestinationNode()
	//	{
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

	@Override
	public String toString() {
		return InternalTransition.tauString + "(" + getHiddenEvent().toString() + ")";
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
		return this.getHiddenEvent().hashCode();
	}

	public LabelledTransition getHiddenEvent()
	{
		return hiddenEvent;
	}
}
