package eu.compassresearch.core.interpreter.cml.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class HiddenEvent extends AbstractChannelEvent implements CmlTau,
		ChannelEvent {

	private ChannelEvent hiddenEvent;
	
	public HiddenEvent(CmlBehaviour eventSource, ChannelEvent hiddenEvent) {
		super(eventSource, hiddenEvent.getChannel());
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}
	
	public HiddenEvent(Set<CmlBehaviour> eventSources, ChannelEvent hiddenEvent) {
		super(eventSources, hiddenEvent.getChannel());
		this.hiddenEvent = hiddenEvent; 
		this.eventSources.addAll(hiddenEvent.getEventSources());
	}

	@Override
	public Value getValue() {
		return hiddenEvent.getValue();
	}

	@Override
	public void setValue(Value value) {
		hiddenEvent.setValue(value);
	}

	@Override
	public boolean isPrecise() {
		return hiddenEvent.isPrecise();
	}

	@Override
	public List<ChannelEvent> expand() {
		//TODO The expanded events should also be wrapped in a HiddenEvent
		List<ChannelEvent> result = new LinkedList<ChannelEvent>();
		for(ChannelEvent ce : hiddenEvent.expand())
			result.add(new HiddenEvent(this.eventSources, ce));
		return result;
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		//TODO the result must also be hidden
		return hiddenEvent.meet(other);
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		return new CmlAlphabet(this);
	}
	
	@Override
	public String toString() {
		return "\\(" + hiddenEvent.toString() + ")";
	}

}
