package eu.compassresearch.core.interpreter.cml;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;


public interface CmlProcess extends CMLProcessBehaviour{

	/**
	 * Initializes the process
	 * @param env
	 */
	public void start(CMLSupervisorEnvironment env);
	
	/**
	 * Executes the next process/action in the process stack
	 * @return
	 * @throws AnalysisException 
	 */
	public CMLBehaviourSignal execute(CMLSupervisorEnvironment env) throws AnalysisException;
	
	/**
	 * Returns the immediate alphabet of the process, meaning the next possible Communication Event
	 * @return The immediate alphabet of the process
	 * @throws AnalysisException 
	 */
	public CMLAlphabet inspect() throws AnalysisException;
	
	/**
	 * 
	 * @return The current state of the process
	 */
	public ProcessState getState();
		
	/**
	 * 
	 * @return The current supervisor of this process
	 */
	public CMLSupervisorEnvironment supervisor();
	
	/**
	 * Name of the process
	 * @return The name of the process
	 */
	public LexNameToken name();
	
	
	// Process Graph/Representation related methods
//	public long level();
	public CmlProcess parent();
//	public CMLDomain domain();
//	public CMLProcessBehaviour behaviour();
	public List<CmlProcess> children();
//	public CMLAlphabet childInspectedAlphabet(CMLProcessNew child);
//	public void setChildInspectedAlphabet(CMLProcessNew child, CMLAlphabet alpha);
	//public boolean hasChild(CMLProcess child, boolean recursive);
	public boolean hasChildren();
	
	// Process state methods 
	public boolean started();
	public boolean running();
	public boolean finished();
	public boolean waiting();
	public boolean deadlocked();
	
	// Denotational Semantics Information
	public CmlTrace getTraceModel();
}
