package eu.compassresearch.core.interpreter.api.transitions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.node.INode;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class HiddenEvent extends AbstractChannelEvent implements SilentTransition,
		ChannelEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4227425913454070026L;
	private ChannelEvent hiddenEvent;
	
	public HiddenEvent(CmlBehaviour eventSource, ChannelEvent hiddenEvent) {
		super(eventSource, hiddenEvent.getChannelName());
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}
	
	private HiddenEvent(Set<CmlBehaviour> eventSources, ChannelEvent hiddenEvent) {
		super(eventSources, hiddenEvent.getChannelName());
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}

	@Override
	public List<ChannelEvent> expand() {
		//TODO The expanded events should also be wrapped in a HiddenEvent
		List<ChannelEvent> result = new LinkedList<ChannelEvent>();
		for(ChannelEvent ce : hiddenEvent.expand())
			result.add(new HiddenEvent(this.eventSources, ce));
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
		return InternalTransition.tauString + "(" + hiddenEvent.toString() + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		//FIXME It might not be the best idea to make a hidden event equal to what it hides
		if((obj instanceof HiddenEvent))
			return this.hiddenEvent.equals(((HiddenEvent)obj).hiddenEvent);
		else 
			return this.hiddenEvent.equals(obj);
			
	}
	
	@Override
	public int hashCode() {
		return this.hiddenEvent.hashCode();
	}
}
