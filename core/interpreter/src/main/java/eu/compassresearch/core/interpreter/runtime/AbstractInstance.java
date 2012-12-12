package eu.compassresearch.core.interpreter.runtime;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.cml.CmlTrace;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluator;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.EventObserver;
import eu.compassresearch.core.interpreter.events.TraceEvent;

public abstract class AbstractInstance<T extends INode> extends AbstractEvaluator<T>
		implements CmlProcess , EventObserver<CmlChannelEvent> {
	
	protected CmlProcessState 			state;
	protected List<CmlProcess> 			children = new LinkedList<CmlProcess>();
	protected CmlProcess 				parent;
	protected CmlSupervisorEnvironment 	env;
	protected CmlTrace 					trace = new CmlTrace();
	protected List<CmlProcessObserver>  stateObservers = new LinkedList<CmlProcessObserver>();
	protected List<CmlProcessObserver>  traceObservers = new LinkedList<CmlProcessObserver>();

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
					updateTrace(env.selectedCommunication());
				}
				//if no communication is selected by the supervisor or we cannot sync the selected events
				//then we go to wait state and wait for channelEvent
				else 
				{
					setState(CmlProcessState.WAIT_EVENT);
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
	
	private void updateTrace(CmlEvent event)
	{
		trace.addEvent(event);
		notifyOnTraceChange(new TraceEvent(this,event));
	}
		
	private void registerChannelsInAlpha(CmlAlphabet alpha)
	{
		for(CmlCommunicationEvent com : alpha.getCommunicationEvents())
		{
			switch(com.getCommunicationType())
			{
			case SIGNAL:
				com.getChannel().onChannelSignal().registerObserver(this);
				break;
			case WRITE:
				com.getChannel().onChannelWrite().registerObserver(this);
				break;
			case READ:
				com.getChannel().onChannelRead().registerObserver(this);
				break;
			}
		}
	}
	
	@Override
	public CmlSupervisorEnvironment supervisor() {
		return env;
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
		for(CmlProcessObserver o : new LinkedList<CmlProcessObserver>(stateObservers))
			o.onStateChange(event);
	}
	
	@Override
	public void registerOnStateChanged(CmlProcessObserver observer) {
		stateObservers.add(observer);
	}
	
	@Override
	public void unregisterOnStateChanged(CmlProcessObserver observer) {
		stateObservers.remove(observer);
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
		for(CmlProcessObserver o : new LinkedList<CmlProcessObserver>(traceObservers))
			o.onTraceChange(traceEvent);
	}
	
	@Override
	public void registerOnTraceChanged(CmlProcessObserver observer) {
		traceObservers.add(observer);
	}
	
	@Override
	public void unregisterOnTraceChanged(CmlProcessObserver observer) {
		traceObservers.remove(observer);
	}
	
	/**
	 * ChannelListener interface method.
	 * Here the process is notified when a registered channel is signalled by the supervisor
	 */
	@Override
	public void onEvent(Object source, CmlChannelEvent event) {
		//enable the processthread to run again and unregister from the channel
		if(level() == 0)
			setState(CmlProcessState.RUNNABLE);
		
		switch(event.getEventType())
		{
		case READ:
			event.getSource().onChannelRead().unregisterObserver(this);
			break;
		case WRITE:
			event.getSource().onChannelWrite().unregisterObserver(this);
			break;
		case SIGNAL:
			event.getSource().onChannelSignal().unregisterObserver(this);
			break;
		
		}
	}
	
}
