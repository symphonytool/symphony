package eu.compassresearch.core.interpreter;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;

/**
 * interface for a listener that listens for inspection of behaviors
 * 
 * @author kel
 */
public interface IInspectListener
{
	/**
	 * Notify method called when an inspection operation is performed on a behavior
	 * 
	 * @param behaviour
	 */
	void inspectStarted(CmlBehaviour behaviour);
}
