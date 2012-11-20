package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CmlCommunication extends CmlEvent {

	private CMLChannelValue channel;
	private Value value = null;
	private CmlCommunicationType commtype;
	
	public CmlCommunication(CMLChannelValue channel)
	{
		this.channel = channel;
		this.commtype = CmlCommunicationType.SIGNAL;
	}
	
	public CmlCommunication(CMLChannelValue channel, Value value)
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

		CmlCommunication other = null;
		
		if(!(obj instanceof CmlCommunication))
			return false;
		
		other = (CmlCommunication)obj;
		
		return other.getChannel().equals(getChannel());
	}
}
