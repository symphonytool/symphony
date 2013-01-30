package eu.compassresearch.core.interpreter.events;

/**
 * Implementers of this interface can register to CmlProcess and get notified whenever the
 * state of the process changes
 * @author akm
 *
 */
public interface CmlProcessStateObserver {

	/**
	 * This event fires every times the process changes to a new state
	 * @param stateEvent
	 */
	public void onStateChange(CmlProcessStateEvent stateEvent);
}
