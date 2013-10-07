package eu.compassresearch.core.interpreter.api.events;

public interface CmlInterpreterStateObserver
{

	public void onStateChanged(Object source, InterpreterStateChangedEvent event);

}
