package eu.compassresearch.core.interpreter.api;

import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;

public interface CmlInterpreterStatusObserver {

	public void onStatusChanged(Object source, InterpreterStatusEvent event);
	
}
