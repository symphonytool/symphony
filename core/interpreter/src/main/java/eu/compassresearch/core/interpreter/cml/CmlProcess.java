package eu.compassresearch.core.interpreter.cml;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;


public interface CmlProcess extends CmlProcessBehaviour{

	/**
	 * Initialises the process
	 * @param env
	 */
	public void start(CmlSupervisorEnvironment env);
	
	/**
	 * 
	 * @return The current supervisor of this process
	 */
	public CmlSupervisorEnvironment supervisor();
	
	/**
	 * Name of the process
	 * @return The name of the process
	 */
	public LexNameToken name();
	
	
	// Process Graph/Representation related methods
	public long level();
	public CmlProcess parent();
	public List<CmlProcess> children();
//	public CMLAlphabet childInspectedAlphabet(CMLProcessNew child);
//	public void setChildInspectedAlphabet(CMLProcessNew child, CMLAlphabet alpha);
	//public boolean hasChild(CMLProcess child, boolean recursive);
	public boolean hasChildren();
	
	/**
	 * Process state methods 
	 */
	public boolean started();
	public boolean running();
	public boolean finished();
	public boolean waiting();
	/**
	 * Determines if this process is deadlocked
	 * @return true if the process is deadlocked else false
	 */
	public boolean deadlocked();

	public void registerOnStateChanged(CmlProcessObserver observer);
	public void unregisterOnStateChanged(CmlProcessObserver observer);
	
	/**
	 * @return The current state of the process
	 */
	public CmlProcessState getState();
	
	/**
	 * Denotational Semantics Information
	 */
	public CmlTrace getTraceModel();
}
