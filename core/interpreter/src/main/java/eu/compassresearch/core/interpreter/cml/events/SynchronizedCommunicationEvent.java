package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;

public class SynchronizedCommunicationEvent extends ObservableValueEvent {

	public SynchronizedCommunicationEvent(CmlBehaviourThread eventSource,
			CmlChannel channel) {
		super(eventSource, channel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Value getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMostPreciseValue(Value value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValuePrecise() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ObservableEvent getReferenceEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableEvent synchronizeWith(CmlBehaviourThread source,
			ObservableEvent syncEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
