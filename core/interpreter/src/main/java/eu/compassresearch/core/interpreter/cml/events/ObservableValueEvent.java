package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;

public abstract class ObservableValueEvent extends ObservableEvent {

	public ObservableValueEvent(CmlBehaviourThread eventSource,
			CmlChannel channel) {
		super(eventSource, channel);
	}

	public abstract Value getValue();
	
	public abstract void setMostPreciseValue(Value value);
	
	public abstract boolean isValuePrecise();

}
