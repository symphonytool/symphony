package eu.compassresearch.core.interpreter.cml;

public interface CMLProcessBehaviour extends CMLDomain {
	
	public CMLBehaviourSignal execute(CMLSupervisorEnvironment supervisor);
	
}
