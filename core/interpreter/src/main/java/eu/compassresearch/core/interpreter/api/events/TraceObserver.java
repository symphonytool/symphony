package eu.compassresearch.core.interpreter.api.events;

/**
 * Implementers of this interface can register to CmlBehavior and get notified whenever the
 * trace has changes
 * @author akm
 *
 */
public interface TraceObserver {

	/**
	 * This event fires every times the trace is updated
	 * @param stateEvent
	 */
	public void onTraceChange(TraceEvent traceEvent);
}
