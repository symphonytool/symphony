package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class CMLCommunication {

	private CMLChannelValue channel;
	private Value value = null;
	private CMLCommunicationType commtype;
	
	public CMLCommunication(CMLChannelValue channel)
	{
		this.channel = channel;
		this.commtype = CMLCommunicationType.SIGNAL;
	}
	
	public CMLCommunication(CMLChannelValue channel, Value value)
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
	
	public CMLCommunicationType getCommunicationType()
	{
		return this.commtype;
	}
	
	public boolean isSignal()
	{
		return this.commtype == CMLCommunicationType.SIGNAL;
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

		CMLCommunication other = null;
		
		if(!(obj instanceof CMLCommunication))
			return false;
		
		other = (CMLCommunication)obj;
		
		return other.getChannel().equals(getChannel());
	}
}
