package eu.compassresearch.core.interpreter.api.events;

/**
 * Implementers of this interface can register to CmlBehavior and get notified whenever the
 * state has changes
 * @author akm
 *
 */
public interface CmlBehaviorStateObserver {

	/**
	 * This event fires every times the process changes to a new state
	 * @param stateEvent
	 */
	public void onStateChange(CmlBehaviorStateEvent stateEvent);
}
