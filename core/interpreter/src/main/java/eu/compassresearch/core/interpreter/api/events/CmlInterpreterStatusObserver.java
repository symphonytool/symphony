package eu.compassresearch.core.interpreter.api.events;

public interface CmlInterpreterStatusObserver {

	public void onStatusChanged(Object source, InterpreterStatusEvent event) ;
	
}
