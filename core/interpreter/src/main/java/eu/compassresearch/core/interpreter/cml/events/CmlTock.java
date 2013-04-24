package eu.compassresearch.core.interpreter.cml.events;

import java.util.HashSet;
import java.util.Set;

import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class CmlTock extends AbstractCmlEvent implements ObservableEvent {

	public CmlTock(CmlBehaviour eventSource) {
		super(eventSource);
	}

	@Override
	public ObservableEvent synchronizeWith(ObservableEvent other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isComparable(ObservableEvent other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrecise() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ObservableEvent meet(ObservableEvent other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmlAlphabet getAsAlphabet() {

		Set<CmlEvent> events = new HashSet<CmlEvent>();
		events.add(this);
		return new CmlAlphabet(events);
	}
	
}
