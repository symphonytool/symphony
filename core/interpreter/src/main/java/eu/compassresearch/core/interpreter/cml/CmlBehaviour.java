package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;

/**
 * This determines the behaviour of the process
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
