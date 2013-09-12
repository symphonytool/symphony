package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.api.behaviour.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class CmlTock extends AbstractCmlTransition implements ObservableEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5584770741085270746L;
	private final long timeLimit;
	
	public CmlTock(CmlBehaviour eventSource, long limit) {
		super(eventSource);
		this.timeLimit = limit;
	}
	
	public CmlTock(CmlBehaviour eventSource) {
		this(eventSource,0);
	}
	
	public CmlTock() {
		super(new HashSet<CmlBehaviour>());
		timeLimit = 0;
	}
	
	protected CmlTock(Set<CmlBehaviour> eventSources, long timeLimit) {
		super(eventSources);
		this.timeLimit = timeLimit;
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent syncEvent) {
		
//		if(!isComparable(other))
//			throw new NotComparableException();
		CmlTock otherTock = (CmlTock)syncEvent;
		Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(otherTock.getEventSources());
		
		return new CmlTock(sources, Math.min(this.timeLimit,otherTock.timeLimit));
	}

	@Override
	public boolean isComparable(ObservableEvent other) {

		return other instanceof CmlTock;
	}
	
	public long getTimeLimit()
	{
		return timeLimit;
	}
	
	public boolean hasTimeLimit()
	{
		return timeLimit != 0;
	}

//	@Override
//	public CmlTransitionSet getAsAlphabet() {
//
//		Set<CmlTransition> events = new HashSet<CmlTransition>();
//		events.add(this);
//		return new CmlTransitionSet(events);
//	}
	
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
