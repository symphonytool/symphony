package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlCommunicationType;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlCommunicationEvent extends CmlEvent {

	private CMLChannelValue channel;
	private Value value = null;
	private CmlCommunicationType commtype;
	
	public CmlCommunicationEvent(CMLChannelValue channel)
	{
		this.channel = channel;
		this.commtype = CmlCommunicationType.SIGNAL;
	}
	
	public CmlCommunicationEvent(CMLChannelValue channel, Value value)
	{
		this.channel = channel;
		this.value = value;
	}
	
	public CMLChannelValue getChannel()
	{
		return channel;
	}
	
	public Value getValue()
	{
		return value;
	}
	
	public CmlCommunicationType getCommunicationType()
	{
		return this.commtype;
	}
	
	public boolean isSignal()
	{
		return this.commtype == CmlCommunicationType.SIGNAL;
	}
	
	@Override public String toString() 
	{
		return channel.getName() + (isSignal() ? "" : "." + value);
	};
	
	@Override public int hashCode() {
		
		return this.toString().hashCode();
	};
	
	@Override
	public boolean equals(Object obj) {

		CmlCommunicationEvent other = null;
		
		if(!(obj instanceof CmlCommunicationEvent))
			return false;
		
		other = (CmlCommunicationEvent)obj;
		
		return other.getChannel().equals(getChannel());
	}
}
