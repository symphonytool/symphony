package eu.compassresearch.core.interpreter.cml;

public interface CMLBehavior {

	/**
	 * 
	 * @return The immediate available CMLChannelEvents
	 */
	public CMLAlphabet inspect();
	
	public CMLBehaviorSignal execute(CMLSupervisorEnvironment supervisor);
	
}
