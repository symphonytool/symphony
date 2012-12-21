package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlCommunicationEvent extends ObservableEvent {

	final protected List<CommunicationParameter> params;
	
	public CmlCommunicationEvent(CmlProcess source, CMLChannelValue channel, List<CommunicationParameter> params)
	{
		super(source,channel);
		this.params = params;
	}
	
//	public Value getValue()
//	{
//		return value;
//	}
	
	@Override 
	public String toString() 
	{
		StringBuilder strBuilder = new StringBuilder(channel.getName());
		for(CommunicationParameter param : params)
			strBuilder.append(param);
		
		return strBuilder.toString();
	};
	
	@Override 
	public int hashCode() {
		
		return this.toString().hashCode() + (this.eventSource != null ? this.eventSource.hashCode() : "null".hashCode());
	};
	
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
		
		return other.getChannel().equals(getChannel()) && 
				other.getEventSource() == getEventSource();
	}

	@Override
	public void handleChannelEventRegistration(ChannelObserver observer) {

		for(CommunicationParameter param : params)
			param.handleChannelEventRegistration(channel, observer);

	}

	@Override
	public ObservableEvent getReferenceEvent() {
		return new CmlCommunicationEvent(null, channel, params);
	}

	@Override
	public void handleChannelEventUnregistration(ChannelObserver observer) {

		for(CommunicationParameter param : params)
			param.handleChannelEventUnregistration(channel, observer);
		
	}
	
}
