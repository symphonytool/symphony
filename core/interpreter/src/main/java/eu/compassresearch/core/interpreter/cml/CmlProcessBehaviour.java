package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;

public interface CmlProcessBehaviour{
	
	public CmlBehaviourSignal execute(CmlSupervisorEnvironment supervisor) throws AnalysisException;;
	public CmlAlphabet inspect() throws AnalysisException;;
	
	
}
