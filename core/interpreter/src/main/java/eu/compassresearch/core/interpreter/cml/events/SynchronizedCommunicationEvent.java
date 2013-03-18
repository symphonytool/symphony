package eu.compassresearch.core.interpreter.cml.events;

import java.util.Arrays;
import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlChannel;
import eu.compassresearch.core.interpreter.values.AbstractValueInterpreter;

public class SynchronizedCommunicationEvent extends ObservableEvent {

	ObservableEvent first;
	ObservableEvent second;
	
	public SynchronizedCommunicationEvent(CmlBehaviourThread eventSource,
			CmlChannel channel, ObservableEvent first, ObservableEvent second) {
		super(eventSource, channel);

		this.first = first;
		this.second = second;
	}

	@Override
	public Value getValue() {
		return AbstractValueInterpreter.meet(first.getValue(), second.getValue());
	}

	@Override
	public void setValue(Value value) {
		first.setValue(value);
		second.setValue(value);
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
		return new SynchronizedCommunicationEvent(source, channel, this, syncEvent);
	}

	@Override
	public int hashCode() {
		return first.hashCode() + second.hashCode();
	}

	@Override
	public String toString() {
		return getChannel().getName() + "." + getValue();
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
	
	@Override
	public List<ObservableEvent> expand() {
		return Arrays.asList((ObservableEvent)this);
	}

}
