package eu.compassresearch.core.interpreter.utility.events;
/**
 * Generic interface to register/unregister for events on a specific event source
 * @author akm
 *
 * @param <O> The type of the observers
 */
public interface EventSource<O> {

	public void registerObserver(O observer);
	
	public void unregisterObserver(O observer);
	
	public boolean isRegistered(O observer);
}
