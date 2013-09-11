package eu.compassresearch.core.interpreter.api.behaviour;

import java.io.Serializable;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.TraceObserver;
import eu.compassresearch.core.interpreter.utility.Pair;

/**
 * This interfaces specifies a specific process behavior.
 * E.g: 
 * 	prefix : a -> P
 * 
 * 	CmlBehaviour.inspect() = {a}
 * 	CmlBehaviour.execute() :  trace: a
 * 	CmlBehaviour.inspect() = alpha(P)
 *   
 * @author akm
 *
 */
public interface CmlBehaviour extends Serializable //extends Transactable 
{
	/**
	 * Executes the behaviour of this process
	 * @return
	 */
	public void execute(CmlSupervisorEnvironment supervisor) throws AnalysisException;
	
	/**
	 * Returns the immediate alphabet of the process, meaning the next possible cml event including tau
	 * @return The immediate alphabet of the process
	 */
	public CmlTransitionSet inspect() throws AnalysisException;
	
	/**
	 * 
	 * @return The current supervisor of this process
	 */
	public CmlSupervisorEnvironment supervisor();
	
	//public Reason abortReason();
	public void setAbort(Reason reason);
	
	/**
	 * Returns the current execution state of the process
	 * @return The current context
	 */
	public Pair<INode,Context> getNextState();
	public void replaceState(Context context) throws ValueException;
	//public Pair<INode,Context> getPostCondition();
	//public setPostCondition(Pair<INode,Context> postcondition);
	
	/**
	 * Name of the process
	 * @return The name of the process
	 */
	public ILexNameToken name();
	
	/**
	 * This constructs a string representing the next execution step of this process
	 * @return
	 */
	public String nextStepToString();
	
	// Process Graph/Representation related methods
	/**
	 * The level of this object in the process network.
	 * @return return 0 if this is the root, 1 if this is a child of the root etc.
	 */
	public long level();
	public CmlBehaviour parent();
	public List<CmlBehaviour> children();
	public CmlBehaviour getLeftChild();
	public CmlBehaviour getRightChild();
//	public CMLAlphabet childInspectedAlphabet(CMLProcessNew child);
//	public void setChildInspectedAlphabet(CMLProcessNew child, CMLAlphabet alpha);
	//public boolean hasChild(CMLProcess child, boolean recursive);
	public boolean hasChildren();
	
	/**
	 * Process state methods 
	 */
	
	/**
	 * Determines whether the process is started
	 * @return true if the process has been started, meaning the start method has been invoked
	 * else false
	 */
	public boolean started();
	public boolean finished();
	
	/**
	 * Determines whether the process is in a waiting state.
	 * @return true if the process is either waiting for a child or an event to occur else false
	 */
	public boolean waiting();
	
	/**
	 * Determines whether this process is deadlocked
	 * @return true if the process is deadlocked else false
	 */
	public boolean deadlocked() throws AnalysisException;
	
	/**
	 * @return The current state of the process
	 */
	public CmlBehaviorState getState();

	/**
	 * Register or unregister for the State Changed event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<CmlBehaviorStateObserver> onStateChanged();
	
	/**
	 * Denotational Semantics Information
	 */
	public CmlTrace getTraceModel();
	//public CSPFailures failuresModel();
	//public CSPDivergencies divergenciesModel();
	//public CSPFailuresDivergencies failuresDivergenciesModel();
	public long getCurrentTime();
	
	/**
	 * Register or unregister for the State Changed event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<TraceObserver> onTraceChanged();
	
}
