package eu.compassresearch.core.interpreter.cosim;

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
}
