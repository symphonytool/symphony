package eu.compassresearch.core.interpreter.cosim;

import eu.compassresearch.core.interpreter.cosim.communication.ConnectionThread;

/**
 * Interface for accessing delegates using in co-simulation
 * 
 * @author kel
 */
public interface IProcessBehaviourDelegationManager
{

	/**
	 * Add a new delegate to the delegate store
	 * 
	 * @param delegate
	 */
	public void addDelegate(IProcessDelegate delegate);

	/**
	 * Check is a delegate exists for a certain process
	 * 
	 * @param name
	 *            a process name
	 * @return
	 */
	public boolean hasDelegate(String name);

	/**
	 * Get a delegate for a certain process. One should check using
	 * {@link IProcessBehaviourDelegationManager#hasDelegate(String)} to see if it exists
	 * 
	 * @param name
	 *            a process name
	 * @return
	 */
	public IProcessDelegate getDelegate(String name);

	/**
	 * Registers the use of a delegate. This prevents it from being given to others
	 * 
	 * @param delegate
	 * @return true if successful
	 */
	public boolean registerUse(IProcessDelegate delegate);

	/**
	 * Notify the manager about an error that caused a client to abort.
	 * 
	 * @param connectionThread
	 * @param registeredProcessName
	 * @param errorCode
	 * @param message
	 */
	public void abortedBy(ConnectionThread connectionThread,
			String registeredProcessName, int errorCode, String message);
}
