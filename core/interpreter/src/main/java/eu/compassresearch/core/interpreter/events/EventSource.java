package eu.compassresearch.core.interpreter.events;
/**
 * Generic interface for register/unregister to a specific source
 * @author akm
 *
 * @param <O> The type of the observers
 */
public interface EventSource<O> {

	public void registerObserver(O observer);
	
	public void unregisterObserver(O observer);
	
	public boolean isRegistered(O observer);
}
