package eu.compassresearch.core.interpreter.cml;

import java.util.List;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.util.Pair;


public interface CmlBehaviourThread extends Transactable //, CmlBehaviour 
{
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
	
	/**
	 * Initializes the process
	 * @param env
	 */
	public void start(CmlSupervisorEnvironment env);
	
	/**
	 * 
	 * @return The current supervisor of this process
	 */
	public CmlSupervisorEnvironment supervisor();
	
	//public Reason abortReason();
	public void setAbort(Reason reason);
	
	/**
	 * This determines whether this process or an ancestor is registered to the given channel.
	 * 
	 * @param channel
	 * @return true if this or an ancestor is registered to 
	 */
	public boolean isRegistered(CmlChannel channel);
	
	/**
	 * Returns the current execution state of the process
	 * @return The current context
	 */
	public Pair<INode,Context> getExecutionState();
	
	/**
	 * Name of the process
	 * @return The name of the process
	 */
	public LexNameToken name();
	
	//public void signalTermination();
		
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
	public CmlBehaviourThread parent();
	public List<CmlBehaviourThread> children();
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
	public boolean deadlocked();
	
	/**
	 * @return The current state of the process
	 */
	public CmlProcessState getState();

	/**
	 * Register or unregister for the State Changed event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<CmlProcessStateObserver> onStateChanged();
	
	/**
	 * Denotational Semantics Information
	 */
	public CmlTrace getTraceModel();
	//public CSPFailures failuresModel();
	//public CSPDivergencies divergenciesModel();
	//public CSPFailuresDivergencies failuresDivergenciesModel();
	
	/**
	 * Register or unregister for the State Changed event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<CmlProcessTraceObserver> onTraceChanged();
	
}
