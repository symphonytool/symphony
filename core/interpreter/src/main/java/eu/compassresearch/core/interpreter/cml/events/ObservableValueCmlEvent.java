package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public abstract class ObservableValueCmlEvent extends ObservableCmlEvent {

	final protected Value value;
	
	public ObservableValueCmlEvent(CmlProcess eventSource, CMLChannelValue channel, Value value) {
		super(eventSource, channel);
		this.value = value;
	}
	
	public Value getValue()
	{
		return value;
	}


}
