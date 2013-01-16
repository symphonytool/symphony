package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.AlphabetInspector;
import eu.compassresearch.core.interpreter.eval.CmlEvaluator;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.Pair;

abstract class AbstractBehaviourThread<T extends INode> extends QuestionAnswerCMLAdaptor<Context, CmlBehaviourSignal>
		implements CmlBehaviourThread , ChannelObserver {
	
	private static final long 					serialVersionUID = -4920762081111266274L;
	
	/**
	 * Instance variables
	 */
	//Stack machine variables
	private Stack<Pair<T,Context>> 				executionStack = new Stack<Pair<T,Context>>();
	private  Pair<T,Context> 					prevExecution = null;
	
	//Process/Action Graph variables
	protected AbstractBehaviourThread<T> 		parent;
	protected List<AbstractBehaviourThread<T>> 	children = new LinkedList<AbstractBehaviourThread<T>>();
	
	//Process/Action state variables
	protected CmlProcessState 					state;
	
	//Current supervisor
	protected CmlSupervisorEnvironment 			env;
	
	//Denotational semantics
	protected CmlTrace 							trace = new CmlTrace();
	
	//Evaluation of CML expression and etc.
	protected CmlEvaluator 						cmlEvaluator = new CmlEvaluator();
	
	//Helper to inspect the immediate Alphabet
	protected AlphabetInspector 				alphabetInspectionVisitor = new AlphabetInspector(this,cmlEvaluator);
	
	//Event handling variable, we need to keep track if the events because of external choice
	//
	protected List<ObservableEvent>     		registredEvents = new LinkedList<ObservableEvent>();
	
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
	
	/**
	 * Constructor
	 * @param parent set the parent here if any else set to null
	 */
	public AbstractBehaviourThread(AbstractBehaviourThread<T> parent)
	{
		state = CmlProcessState.INITIALIZED;
		this.parent = parent;
	}
	
	/*
	 * 
	 * Public Methods
	 * 
	 */
	
	/*
	 * 
	 * Stack machine methods start
	 * 
	 */
	
	/**
	 * Determines whether there is a next execution pair
	 * @return true if the execution stack is non empty
	 */
	protected  boolean hasNext()
	{
		return !executionStack.isEmpty();
	}
	
	protected  boolean hasPrev()
	{
		return prevExecution != null;
	}
	
	protected  Pair<T,Context> prevState()
	{
		return prevExecution;
	}
	
	protected  Pair<T,Context> nextState()
	{
		return executionStack.peek();
	}
	
	protected List<Pair<T,Context>> getExecutionStack()
	{
		return executionStack;
	}
	
	protected void pushNext(T node, Context context)
	{
		executionStack.push(new Pair<T, Context>(node, context));
	}
	
	/*
	 * 
	 * Stack machine methods end
	 * 
	 */
	
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
				//FIXME: this might not be the best idea to get the special event
				updateTrace(alpha.getSpecialEvents().iterator().next());
				ret = executeNext();
			}
			else 
			{	
				//If the selected event is in the immediate alphabet then we can continue
				if(env.isObservableEventSelected() &&  
						!alpha.flattenSyncEvents().intersect(env.selectedObservableEvent().getAsAlphabet()).isEmpty())
				{
					ret = executeNext();
					unregisterChannel(env.selectedObservableEvent());
					updateTrace(env.selectedObservableEvent());
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
			CmlRuntime.logger().throwing(this.toString(),"execute", ex);
			throw new InterpreterRuntimeException(ex);
		}
	}
	
	/**
	 * Execute private helper methods
	 */
	
	private CmlBehaviourSignal executeNext() throws AnalysisException
	{
		if(hasNext())
		{
			setState(CmlProcessState.RUNNING);
			Pair<T,Context> next = executionStack.pop();
			prevExecution = next;
			return next.first.apply(this,next.second);
		}
		else{
			throw new InterpreterRuntimeException("Trying to execute a finished Process...THIS SHOULD BE CHANGE INTO A DIFFERENT EXCEPTION");
		}
		
	}
	
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
		event.getChannel().onSelect().unregisterObserver(this);
		registredEvents.remove(event);
	}
	
	private void registerChannelsInAlpha(CmlAlphabet alpha)
	{
		for(ObservableEvent com : alpha.getObservableEvents())
		{
			registredEvents.add(com);
			com.getChannel().onSelect().registerObserver(this);
		}
	}
	
	/*
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
		for(CmlBehaviourThread child : children())
			child.setAbort(reason);
		
		//unregister all the channels
		for(ObservableEvent oe : registredEvents)
			oe.getChannel().onSelect().unregisterObserver(this);
		
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
	public CmlBehaviourThread parent() {
		return parent;
	}

	@Override
	public List<CmlBehaviourThread> children() {
		
		return (List)children;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/**
	 * 
	 * State related methods
	 * 
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
	
	public boolean waitingForChild()
	{
		return getState() == CmlProcessState.WAIT_CHILD;
	}
	
	public boolean waitingForEvent()
	{
		return getState() == CmlProcessState.WAIT_EVENT; 
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

		//if the channel sends a select event then it means that we have a go to execute the
		//communicationAction transition. So we set the state to RUNNABLE so the scheduler will execute it
		if(event.getEventType() == CmlCommunicationType.SELECT)
			setState(CmlProcessState.RUNNABLE);
	}
	
}
