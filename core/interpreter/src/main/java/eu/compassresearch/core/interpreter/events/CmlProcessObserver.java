package eu.compassresearch.core.interpreter.events;


/**
 * Implementers of this interface can register to CmlProcess and get notified whenever the
 * state of the process changes
 * @author akm
 *
 */
public interface CmlProcessObserver {

	/**
	 * This event fires every times the process changes to a new state
	 * @param stateEvent
	 */
	public void onStateChange(CmlProcessStateEvent stateEvent);
	
	/**
	 * This event fires every times the trace is updated
	 * @param stateEvent
	 */
	public void onTraceChange(TraceEvent traceEvent);
}
