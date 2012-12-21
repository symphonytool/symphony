package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.Reason;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.util.Pair;

public abstract class AbstractInstance<T extends INode> extends AbstractEvaluator<T>
		implements CmlProcess , ChannelObserver {
	
	/**
	 * 
	 */
	private static final long 			serialVersionUID = -4920762081111266274L;
	protected CmlProcessState 			state;
	protected List<CmlProcess> 			children = new LinkedList<CmlProcess>();
	protected CmlProcess 				parent;
	protected CmlSupervisorEnvironment 	env;
	protected CmlTrace 					trace = new CmlTrace();
	protected List<ObservableEvent>     registredEvents = new LinkedList<ObservableEvent>();
	
	protected EventSourceHandler<CmlProcessStateObserver,CmlProcessStateEvent>  stateEventhandler = 
			new EventSourceHandler<CmlProcessStateObserver,CmlProcessStateEvent>(this,
					new EventFireMediator<CmlProcessStateObserver,CmlProcessStateEvent>() {

				@Override
						public void fireEvent(CmlProcessStateObserver observer,
								Object source, CmlProcessStateEvent event) {
							observer.onStateChange(event);
						}
					});
	
	protected EventSourceHandler<CmlProcessTraceObserver,TraceEvent>  traceEventHandler = 
			new EventSourceHandler<CmlProcessTraceObserver,TraceEvent>(this,
					new EventFireMediator<CmlProcessTraceObserver,TraceEvent>() {

				@Override
						public void fireEvent(CmlProcessTraceObserver observer,
								Object source, TraceEvent event) {
							observer.onTraceChange(event);
						}
					});
	
	public AbstractInstance(CmlProcess parent)
	{
		state = CmlProcessState.INITIALIZED;
		this.parent = parent;
	}
	
	/**
	 * Executes the current process behaviour
	 */
	@Override
	public CmlBehaviourSignal execute(CmlSupervisorEnvironment env) 
	{
		this.env= env;

		//inspect if there are any immediate events
		CmlAlphabet alpha = inspect();
		CmlBehaviourSignal ret = null;

		try
		{
			//execute silently if the next is a silent action
			if(alpha.containsSpecialEvent(CmlTauEvent.referenceTauEvent())){
				setState(CmlProcessState.RUNNING);
				//FIXME: this might not be the best idea to get the special event
				updateTrace(alpha.getSpecialEvents().iterator().next());
				ret = executeNext();
			}
			else 
			{	
				//If the selected event is in the immediate alphabet then we can continue
				if(env.communicationSelected() && alpha.containsCommunication(env.selectedCommunication()))
				{
					setState(CmlProcessState.RUNNING);
					ret = executeNext();
					unregisterChannel(env.selectedCommunication());
					updateTrace(env.selectedCommunication());
				}
				//if no communication is selected by the supervisor or we cannot sync the selected events
				//then we go to wait state and wait for channelEvent
				else 
				{
					setState(CmlProcessState.WAIT_EVENT);
					
					if(level() == 0)
						registerChannelsInAlpha(alpha);
					
					ret = CmlBehaviourSignal.EXEC_SUCCESS;
				}
			}

			return ret;
		}
		catch(AnalysisException ex)
		{
			CmlRuntime.logger.throwing(this.toString(),"execute", ex);
			throw new InterpreterRuntimeException(ex);
		}
	}
	
	/**
	 * Execute private helper methods
	 */
	
	/**
	 * Update the trace and fires the trace event
	 * @param The next event in the trace
	 */
	private void updateTrace(CmlEvent event)
	{
		trace.addEvent(event);
		notifyOnTraceChange(new TraceEvent(this,event));
	}
	
	private void unregisterChannel(ObservableEvent event)
	{
		event.handleChannelEventUnregistration(this);
		registredEvents.remove(event);
	}
	
	private void registerChannelsInAlpha(CmlAlphabet alpha)
	{
		for(ObservableEvent com : alpha.getObservableEvents())
		{
			registredEvents.add(com);
			com.handleChannelEventRegistration(this);
		}
	}
	
	/**
	 * Execute region end
	 */
	
	@Override
	public CmlSupervisorEnvironment supervisor() {
		return env;
	}
	
	@Override
	public Pair<T, Context> getExecutionState() {
		if(hasNext())
			return nextState();
		else
			return prevState();
	}
	
	@Override
	public void setAbort(Reason reason) {

		//abort all the children
		for(CmlProcess child : children())
			child.setAbort(reason);
		
		//unregister all the channels
		for(ObservableEvent oe : registredEvents)
			oe.handleChannelEventUnregistration(this);
		
		setState(CmlProcessState.FINISHED);
	}
	
	/**
	 * Process graph methods
	 */
	
	@Override
	public long level() {

		if(parent() == null)
			return 0;
		else 
			return parent().level() + 1;
	}

	@Override
	public CmlProcess parent() {
		return parent;
	}

	@Override
	public List<CmlProcess> children() {
		return children;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/**
	 * State methods
	 */
	@Override
	public boolean started() {
		return getState() == CmlProcessState.RUNNABLE || 
				running() ||
				waiting();
	}

	@Override public boolean waiting() {
		return getState() == CmlProcessState.WAIT_EVENT ||
				getState() == CmlProcessState.WAIT_CHILD;
	}
	
	@Override
	public boolean running() {
		return getState() == CmlProcessState.RUNNING;
	}

	@Override
	public boolean finished() {
		return getState() == CmlProcessState.FINISHED;
	}
	
	@Override
	public boolean deadlocked() {
		
		//A Process is deadlocked if its immediate alphabet is empty
		return inspect().isEmpty();
	}
	
	protected void notifyOnStateChange(CmlProcessStateEvent event)
	{
		stateEventhandler.fireEvent(event);
	}
	
	@Override
	public EventSource<CmlProcessStateObserver> onStateChanged()
	{
		return stateEventhandler;
	}
	
	protected abstract void setState(CmlProcessState state);
	
	/**
	 * Denotational Semantics Information
	 */
	@Override
	public CmlTrace getTraceModel() {
		return trace;
	}
	
	/**
	 * Denotational Semantics Event handling methods
	 */
	
	protected void notifyOnTraceChange(TraceEvent traceEvent)
	{
		traceEventHandler.fireEvent(traceEvent);
	}
	
	@Override
	public EventSource<CmlProcessTraceObserver> onTraceChanged()
	{
		return traceEventHandler;
	}
	
	/**
	 * ChannelListener interface method.
	 * Here the process is notified when a registered channel is signalled 
	 */
	@Override
	public void onChannelEvent(Object source, CmlChannelEvent event) {
		//enable the processthread to run again and unregister from the channel
		//if(level() == 0)
			setState(CmlProcessState.RUNNABLE);
	}
	
}
