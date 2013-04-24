package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class CmlTock extends AbstractCmlEvent implements ObservableEvent {

	public CmlTock(CmlBehaviour eventSource) {
		super(eventSource);
	}
	
	public CmlTock() {
		super(new HashSet<CmlBehaviour>());
	}
	
	protected CmlTock(Set<CmlBehaviour> eventSources) {
		super(eventSources);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent) {
		
//		if(!isComparable(other))
//			throw new NotComparableException();
		
		Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(syncEvent.getEventSources());
		
		return new CmlTock(sources);
	}

	@Override
	public boolean isComparable(ObservableEvent other) {

		return equals(other);
	}

	@Override
	public boolean isPrecise() {
		//TODO I suspect that this is not always true of the refusals are derived 
		//from a imprecise event
		return true;
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		//TODO I suspect that this is not always true of the refusals are derived 
		//from a imprecise event
		return this;
	}

	@Override
	public CmlAlphabet getAsAlphabet() {

		Set<CmlEvent> events = new HashSet<CmlEvent>();
		events.add(this);
		return new CmlAlphabet(events);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof CmlTock))
			return false;
		
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "tock";
	}
	
	@Override
	public int hashCode() {
		return "tock".hashCode();
	}
	
}
