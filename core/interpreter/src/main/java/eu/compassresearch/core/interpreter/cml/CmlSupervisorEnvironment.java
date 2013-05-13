package eu.compassresearch.core.interpreter.cml;

import java.util.List;

import eu.compassresearch.core.interpreter.cml.events.CmlTransition;

public interface CmlSupervisorEnvironment {

	// Java related
	//public String asString();
	//public boolean equals(Object o);
	// Access methods
	//public long id();
	//public CMLProcessNetwork network();
	/**
	 * Returns the selected CMLCommunicationSelectionStrategy object for the supervisor instance
	 * @return
	 */
	public SelectionStrategy decisionFunction();
	//public void setDecisionFunction(CMLCommunicationSelectionStrategy cdf)
	//throws NullPointerException;
	// Pupil Processes related methods
	public void addPupil(CmlBehaviour process);
	public void removePupil(CmlBehaviour process);
	public void clearPupils();
	public List<CmlBehaviour> getPupils();
	public CmlBehaviour findNamedProcess(String name);
	
	// Supervision related methods
	/**
	 * Determines whether there is a currently selected communication 
	 * @return true of there is a communication selected else false
	 */
	public boolean isSelectedEventValid();
	/**
	 * returns the selected communication
	 * @return The selected CMLCommunication if communicationSelected() is true else null
	 */
	public CmlTransition selectedObservableEvent();
	public void setSelectedObservableEvent(CmlTransition comm);
	/**
	 * Clears the currently selected CMLCommunication
	 */
	public void clearSelectedCommunication();
	//public CMLAlphabet controllerAlphabet(CMLProcessNew process);
	//public CMLSupervisionSignal barrier(boolean preBarrier, CMLProcessNew process);
	//public CMLSupervisionSignal inspect(CMLProcessNew process);
	//public CMLProcessNew notifyBacktrack(CMLProcessNew process);
	//public void notifyProcessExecutionFinished(CMLProcessNew process, CMLBehaviourSignal executionSignal);
	
	// Debugging related methods
	//public void dump() throws IOException;//to STDOUT!
	//public void dumpTo(Writer w) throws IOException;
	
	// Layer integration methods
	//public Iterator supervisionHistory(CMLProcessNew process);
	//public CSPSupervisionFrame supervisionFrame(CMLProcessNew process);
	//public boolean hasSupervisionFrame(CMLProcessNew process);
	//public CSPSupervisorInspectionSolver inspectionSolver();
	//public void associateIntegrationPoint(CSPLayerIntegratorPoint lip, CSPAlphabet normalizedAlpha);
	//public CSPLayerIntegratorPoint createLayerIntegrationPoint(CMLProcessNew process,
	//CSPCommunication selComm);
	
}
