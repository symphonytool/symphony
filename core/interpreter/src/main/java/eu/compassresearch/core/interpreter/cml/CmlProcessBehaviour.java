package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;

public interface CmlProcessBehaviour{
	
	/**
	 * Executes the next process/action in the process stack
	 * @return
	 */
	public CmlBehaviourSignal execute(CmlSupervisorEnvironment supervisor);
	
	/**
	 * Returns the immediate alphabet of the process, meaning the next possible Communication Event
	 * @return The immediate alphabet of the process
	 */
	public CmlAlphabet inspect();
	
	
}
