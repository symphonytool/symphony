package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.analysis.AnalysisException;

public interface CMLProcessBehaviour{
	
	public CMLBehaviourSignal execute(CMLSupervisorEnvironment supervisor) throws AnalysisException;;
	public CMLAlphabet inspect() throws AnalysisException;;
	
	
}
