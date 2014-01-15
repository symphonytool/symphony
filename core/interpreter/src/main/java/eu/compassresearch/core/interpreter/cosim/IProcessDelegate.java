package eu.compassresearch.core.interpreter.cosim;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;

/**
 * Delegate interface for remote co-simulation clients
 * 
 * @author kel
 */
public interface IProcessDelegate
{
	/**
	 * Returns the immediate alphabet of the process, meaning the next possible cml event including tau
	 * 
	 * @return The immediate alphabet of the process
	 * @throws Exception
	 */
	public CmlTransitionSet inspect() throws Exception;

	/**
	 * Executes the behavior of this process
	 * 
	 * @return
	 * @throws Exception
	 */
	public void execute(CmlTransition transition) throws Exception;

	/**
	 * return the process name which this delegate is a delegate for
	 * 
	 * @return
	 */
	public String getProcessName();

	/**
	 * Returns true if the process execution has finished
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isFinished() throws Exception;

	/**
	 * Disconnects the delegate from the client
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception;
}
