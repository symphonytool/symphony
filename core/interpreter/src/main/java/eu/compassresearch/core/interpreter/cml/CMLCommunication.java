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
	
}
