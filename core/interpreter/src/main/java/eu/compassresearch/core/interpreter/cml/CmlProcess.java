package eu.compassresearch.core.interpreter.cml;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;

import eu.compassresearch.core.interpreter.events.CmlProcessObserver;


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
	
	/**
	 * This constructs a string representing the next execution step of this process
	 * @return
	 */
	public String nextStepToString();
	
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
	/**
	 * @return The current state of the process
	 */
	public CmlProcessState getState();

	public void registerOnStateChanged(CmlProcessObserver observer);
	public void unregisterOnStateChanged(CmlProcessObserver observer);
	
	/**
	 * Denotational Semantics Information
	 */
	public CmlTrace getTraceModel();
	
	public void registerOnTraceChanged(CmlProcessObserver observer);
	public void unregisterOnTraceChanged(CmlProcessObserver observer);
}
