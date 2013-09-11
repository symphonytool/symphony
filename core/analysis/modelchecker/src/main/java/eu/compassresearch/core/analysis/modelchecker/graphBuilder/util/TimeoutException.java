package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

/**
 * Thrown when a process being executed exceeds the maximum amount of time
 * allowed for it to complete.
 */
public class TimeoutException extends Exception {
	/**
	 * All serializable objects should have a stable serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TimeoutException() {
		super();
	}

	public TimeoutException(String message) {
		super(message);
	}
}