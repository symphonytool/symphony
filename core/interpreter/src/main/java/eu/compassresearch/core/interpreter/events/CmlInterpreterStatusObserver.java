package eu.compassresearch.core.interpreter.events;

public interface CmlInterpreterStatusObserver {

	public void onStatusChanged(Object source, InterpreterStatusEvent event);
	
}
