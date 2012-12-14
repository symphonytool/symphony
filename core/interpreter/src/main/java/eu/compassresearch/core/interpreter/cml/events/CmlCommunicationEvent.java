package eu.compassresearch.core.interpreter.cml.events;

import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationType;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlCommunicationEvent extends ObservableValueCmlEvent {

	private List<CmlCommunicationEvent> synchronisations = new LinkedList<CmlCommunicationEvent>();
	
	
	public CmlCommunicationEvent(CmlProcess source, CMLChannelValue channel, Value value)
	{
		super(source,channel,value);
	}
	
	private CmlCommunicationEvent(CmlProcess source, CmlCommunicationEvent left, CmlCommunicationEvent right, Value value)
	{
		super(source,left.getChannel(),value);
		this.synchronisations.add(left);
		this.synchronisations.add(right);
	}
	
	@Override 
	public String toString() 
	{
		return channel.getName() + (getValue() != null ? "" : "." + value);
	};
	
	@Override 
	public int hashCode() {
		
		return this.toString().hashCode() + this.eventSource.hashCode();
	};
	
	/**
	 * This merges the sources of the this and other object.
	 * @param other
	 * @return
	 */
	public CmlCommunicationEvent createSyncEventWith(CmlProcess source, CmlCommunicationEvent other, Value value)
	{
		if(equalsIqnoreSource(other))
		{
			//
			return new CmlCommunicationEvent(source,this,other,value);
		}
		else
			return null;
	}
	
	public boolean isSynchronisationEvent()
	{
		return synchronisations.size() > 1;
	}
	
	public boolean hasSource()
	{
		return this.eventSource != null;
	}
	
	public boolean equalsIqnoreSource(CmlCommunicationEvent other)
	{
		return other.getChannel().equals(getChannel());
	}
	
	@Override
	public boolean equals(Object obj) {

		CmlCommunicationEvent other = null;
		
		if(!(obj instanceof CmlCommunicationEvent))
			return false;
		
		other = (CmlCommunicationEvent)obj;
		
		return other.getChannel().equals(getChannel()) && other.getEventSource().equals(getEventSource());
	}

	@Override
	public void handleChannelEventRegistration(ChannelObserver observer) {
		// TODO Auto-generated method stub
		
	}
}
