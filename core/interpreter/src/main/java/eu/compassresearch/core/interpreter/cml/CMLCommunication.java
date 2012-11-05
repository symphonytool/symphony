package eu.compassresearch.core.interpreter.cml;

import org.overture.interpreter.values.Value;

public class CMLCommunication {

	private CMLChannel channel;
	private Value value = null;
	
	public CMLCommunication(CMLChannel channel)
	{
		this.channel = channel;
	}
	
	public CMLCommunication(CMLChannel channel, Value value)
	{
		this.channel = channel;
		this.value = value;
	}
	
	public CMLChannel getChannel()
	{
		return channel;
	}
	
	public Value getValue()
	{
		return value;
	}
	
	public boolean isSignal()
	{
		return channel == null;
	}
	
	@Override public String toString() 
	{
		return channel.getName() + (!isSignal() ? "" : "." + value);
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
