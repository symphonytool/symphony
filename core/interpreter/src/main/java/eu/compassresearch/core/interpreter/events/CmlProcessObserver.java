package eu.compassresearch.core.interpreter.events;


/**
 * Implementers of this interface can register to CmlProcess and get notified whenever the
 * state of the process changes
 * @author akm
 *
 */
public interface CmlProcessObserver {

	public void onStateChange(CmlProcessStateEvent stateEvent);
}
