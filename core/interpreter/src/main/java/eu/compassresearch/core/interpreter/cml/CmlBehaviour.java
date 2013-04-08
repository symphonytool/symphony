package eu.compassresearch.core.interpreter.cml;


/**
 * This interfaces specifies a specific process behavior.
 * E.g: 
 * 	prefix : a -> P
 * 
 * 	CmlBehaviour.inspect() = {a}
 * 	CmlBehaviour.execute() :  trace: a
 * 	CmlBehaviour.inspect() = alpha(P)
 *   
 * @author akm
 *
 */
public interface CmlBehaviour{
	
	/**
	 * Executes the behaviour of this process
	 * @return
	 */
	public CmlBehaviourSignal execute(CmlSupervisorEnvironment supervisor);
	
	/**
	 * Returns the immediate alphabet of the process, meaning the next possible cml event including tau
	 * @return The immediate alphabet of the process
	 */
	public CmlAlphabet inspect();
	
	
}
