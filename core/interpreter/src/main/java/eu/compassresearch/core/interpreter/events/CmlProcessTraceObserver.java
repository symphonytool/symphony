package eu.compassresearch.core.interpreter.events;

/**
 * Implementers of this interface can register to CmlProcess and get notified whenever the
 * trace of the process changes
 * @author akm
 *
 */
public interface CmlProcessTraceObserver {

	/**
	 * This event fires every times the trace is updated
	 * @param stateEvent
	 */
	public void onTraceChange(TraceEvent traceEvent);
}
