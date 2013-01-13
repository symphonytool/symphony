package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlIOChannel;

public class CmlCommunicationEvent extends ObservableEvent {

	final protected List<CommunicationParameter> params;
	
	public CmlCommunicationEvent(CmlBehaviourThread source, CmlIOChannel<Value> channel, List<CommunicationParameter> params)
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
	
	@SuppressWarnings("unchecked")
	@Override
	public ObservableEvent getReferenceEvent() {
		return new CmlCommunicationEvent(null, (CmlIOChannel<Value>)channel, params);
	}

}
