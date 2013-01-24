package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.channels.CmlChannel;
import eu.compassresearch.core.interpreter.util.AbstractValueInterpreter;

public class SynchronizedCommunicationEvent extends ObservableValueEvent {

	ObservableValueEvent first;
	ObservableValueEvent second;
	
	public SynchronizedCommunicationEvent(CmlBehaviourThread eventSource,
			CmlChannel channel, ObservableValueEvent first, ObservableValueEvent second) {
		super(eventSource, channel);

		this.first = first;
		this.second = second;
	}

	@Override
	public Value getValue() {
		return AbstractValueInterpreter.meet(first.getValue(), second.getValue());
	}

	@Override
	public void setMostPreciseValue(Value value) {
		first.setMostPreciseValue(AbstractValueInterpreter.meet(getValue(), value));
		second.setMostPreciseValue(AbstractValueInterpreter.meet(getValue(), value));
	}

	@Override
	public boolean isValuePrecise() {
		return AbstractValueInterpreter.isValuePrecise(getValue());
	}

	@Override
	public ObservableEvent getReferenceEvent() {
		return first.getAsAlphabet().getObservableEvents().iterator().next();
	}

	@Override
	public CmlAlphabet getAsAlphabet() {
		return new CmlAlphabet().union(first).union(second);
	}

	@Override
	public ObservableEvent synchronizeWith(CmlBehaviourThread source,
			ObservableEvent syncEvent) {
		return new SynchronizedCommunicationEvent(source, channel, this, (ObservableValueEvent)syncEvent);
	}

	@Override
	public int hashCode() {
		return first.hashCode() + second.hashCode();
	}

	@Override
	public String toString() {
		return "sync : "+ getChannel().getName() + "..";
	}

	@Override
	public boolean equals(Object obj) {
		
		if((obj instanceof SynchronizedCommunicationEvent))
		{
			SynchronizedCommunicationEvent other = (SynchronizedCommunicationEvent)obj;
			return other.getChannel().equals(getChannel()) && 
					first.equals(other.first) || first.equals(other.second) &&
					second.equals(other.first) || second.equals(other.second);
		}
		else
			return false;
		
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		// TODO Auto-generated method stub
		return null;
	}

}
