package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public abstract class CommunicationParameter {

	protected Value value;
	protected PCommunicationParameter param;
	
	protected CommunicationParameter(Value value)
	{
		this.value = value;
	}
	
	public Value getValue()
	{
		return value;
	}
	
}
