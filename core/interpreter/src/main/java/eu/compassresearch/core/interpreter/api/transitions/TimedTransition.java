package eu.compassresearch.core.interpreter.api.transitions;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class TimedTransition extends AbstractCmlTransition implements ObservableTransition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5584770741085270746L;
	private final long timeLimit;
	
	public TimedTransition(CmlBehaviour eventSource, long limit) {
		super(eventSource);
		this.timeLimit = limit;
	}
	
	public TimedTransition(CmlBehaviour eventSource) {
		this(eventSource,0);
	}
	
	public TimedTransition() {
		super(new HashSet<CmlBehaviour>());
		timeLimit = 0;
	}
	
	protected TimedTransition(Set<CmlBehaviour> eventSources, long timeLimit) {
		super(eventSources);
		this.timeLimit = timeLimit;
	}

	@Override
	public ObservableTransition synchronizeWith(ObservableTransition syncEvent) {
		
//		if(!isComparable(other))
//			throw new NotComparableException();
		TimedTransition otherTock = (TimedTransition)syncEvent;
		Set<CmlBehaviour> sources = new HashSet<CmlBehaviour>();
		sources.addAll(this.getEventSources());
		sources.addAll(otherTock.getEventSources());
		
		return new TimedTransition(sources, Math.min(this.timeLimit,otherTock.timeLimit));
	}
	
	//@Override
	public Set<INode> getDestinationNodes()
	{
		return this.getSourceNodes();
	}

	@Override
	public boolean isComparable(ObservableTransition other) {

		return other instanceof TimedTransition;
	}
	
	public long getTimeLimit()
	{
		return timeLimit;
	}
	
	public boolean hasTimeLimit()
	{
		return timeLimit != 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof TimedTransition))
			return false;
		
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "tock";
	}
	
	@Override
	public int hashCode() {
		return "tock".hashCode() + this.eventSources.hashCode();
	}

//	@Override
//	public int compareTo(CmlTransition o)
//	{
//		if(o instanceof CmlTock)
//			return 0;
//		else
//			return -1;
//	}
	
}
