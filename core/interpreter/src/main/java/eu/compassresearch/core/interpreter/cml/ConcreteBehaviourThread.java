package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.AlphabetInspector;
import eu.compassresearch.core.interpreter.eval.CmlEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.CmlOpsToString;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.CmlChannelEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.util.Pair;

public class ConcreteBehaviourThread implements CmlBehaviourThread , 
				ChannelObserver, CmlProcessStateObserver, CmlProcessTraceObserver 
	{
	
	private static final long 					serialVersionUID = -4920762081111266274L;
	
	/**
	 * Instance variables
	 */
	//name of the instance
	protected LexNameToken 						name;
	
	//Stack machine variables
	private Stack<Pair<INode,CmlContext>> 		executionStack = new Stack<Pair<INode,CmlContext>>();
	private Pair<INode,CmlContext> 				prevExecution = null;
	
	//Process/Action Graph variables
	protected CmlBehaviourThread 	parent;
	protected List<ConcreteBehaviourThread> 	children = new LinkedList<ConcreteBehaviourThread>();
	
	//Process/Action state variables
	protected CmlProcessState 					state;
	
	//Current supervisor
	protected CmlSupervisorEnvironment 			env;
	
	//use for the hiding operator
	protected CmlAlphabet 						hidingAlphabet = new CmlAlphabet();
	
	//Denotational semantics
	protected CmlTrace 							trace = new CmlTrace();
	
	//Helper to inspect the immediate Alphabet
	protected AlphabetInspector 				alphabetInspectionVisitor = new AlphabetInspector(this);
	
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
	
	//resstore point
	protected RestorePoint lastRestorePoint = null;
	
	AbstractEvaluationVisitor cmlEvaluationVisitor = new CmlEvaluationVisitor();
	
	/**
	 * Constructor
	 * @param parent set the parent here if any else set to null
	 */
	public ConcreteBehaviourThread(CmlBehaviourThread parent,LexNameToken name)
	{
		state = CmlProcessState.INITIALIZED;
		this.parent = parent;
		this.name = name;
		
		
		cmlEvaluationVisitor.init(new AbstractEvaluationVisitor.ControlAccess() {
			
			@Override
			public void setState(CmlProcessState state) {
				ConcreteBehaviourThread.this.setState(state);
				
			}
			
			@Override
			public void setHidingAlphabet(CmlAlphabet alpha) {
				ConcreteBehaviourThread.this.setHidingAlphabet(alpha);
				
			}
			
			@Override
			public void removeTheChildren() {
				ConcreteBehaviourThread.this.removeTheChildren();				
			}
			
			@Override
			public void pushNext(INode node, CmlContext context) {
				ConcreteBehaviourThread.this.pushNext(node, context);
			}
			
			@Override
			public CmlBehaviourThread ownerThread() {
				return ConcreteBehaviourThread.this;
			}
			
			@Override
			public void mergeState(CmlBehaviourThread other) {
				ConcreteBehaviourThread.this.mergeState((ConcreteBehaviourThread)other);
				
			}
			
			@Override
			public boolean hasNext() {
				return ConcreteBehaviourThread.this.hasNext();
			}
			
			@Override
			public CmlAlphabet getHidingAlphabet() {
				return hidingAlphabet;
			}
			
			@Override
			public CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child) {
				return ConcreteBehaviourThread.this.executeChildAsSupervisor(child);
			}
			
			@Override
			public CmlBehaviourThread createChild(INode node, CmlContext question,
					LexNameToken name) {
				
				return new ConcreteBehaviourThread(node, question, name, ConcreteBehaviourThread.this);
			}
			
			@Override
			public void addChild(CmlBehaviourThread child) {
				ConcreteBehaviourThread.this.addChild(child);
				
			}
		});
	}
	
	public ConcreteBehaviourThread(INode action,CmlContext context, LexNameToken name)
	{
		this(null,name);
		pushNext(action, context);
	}
	
	public ConcreteBehaviourThread(INode action,CmlContext context, LexNameToken name, CmlBehaviourThread parent)
	{
		this(parent,name);
		pushNext(action, context);
	}
	
	@Override
	public void start(CmlSupervisorEnvironment env) {
		
		this.env = env;
		state = CmlProcessState.RUNNABLE;
		env.addPupil(this);
	}
	
	@Override
	public String nextStepToString() {
		
		if(hasNext())
		{
			if(hasChildren())
			{
				CmlBehaviourThread leftChild = children().get(0);
				CmlBehaviourThread rightChild = children().get(1);
				
				return "(" + leftChild.nextStepToString() + ")" + CmlOpsToString.toString(nextState().first) + "(" + rightChild.nextStepToString()+")";
			}
			else{
				return nextState().first.toString();
			}
		}
		else
			return "Finished";
	}
	
	@Override
	public String toString() {
	
		return name.toString();
	}
	
	/**
	 * CmlProcessTraceObserver interface 
	 */
	
	/**
	 * This will provide the traces from all the child actions
	 */
	@Override
	public void onTraceChange(TraceEvent traceEvent) {
		
		this.trace.addEvent(traceEvent.getEvent());
		notifyOnTraceChange(TraceEvent.createRedirectedEvent(this, traceEvent));
	}
	
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
	
	protected  Pair<INode,CmlContext> prevState()
	{
		return prevExecution;
	}
	
	protected  Pair<INode,CmlContext> nextState()
	{
		return executionStack.peek();
	}
	
	protected List<Pair<INode,CmlContext>> getExecutionStack()
	{
		return executionStack;
	}
	
	protected void pushNext(INode node, CmlContext context)
	{
		executionStack.push(new Pair<INode, CmlContext>(node, context));
	}
	
	protected void mergeState(ConcreteBehaviourThread other)
	{
		
		if(other.hasNext())
		{	//get the state replace the current state
			//FIXME: this is really really ugly
			for(Pair<INode,CmlContext> state : other.getExecutionStack())
			{
				pushNext(state.first, 
						state.second);
			}
		}
		else
		{
			pushNext(other.prevState().first, 
					other.prevState().second);
		}
	}
	
	/*
	 * 
	 * Stack machine methods end
	 * 
	 */
	
	/*
	 * Execute region start
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
			//execute silently if the current alphabet contains is a silent action
			if(alpha.containsSpecialEvent(CmlTauEvent.referenceTauEvent())){
				//FIXME: this might not be the best idea to get the special event
				updateTrace(alpha.getSpecialEvents().iterator().next());
				ret = executeNext();
			}
			else 
			{	
				//If the selected event is in the immediate alphabet then we can continue
				if(env.isObservableEventSelected() &&  
						!alpha.flattenSyncEvents().intersectEqualOrMorePrecise(env.selectedObservableEvent().getAsAlphabet()).isEmpty())
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
	
	@Override
	public CmlAlphabet inspect()
	{
		try
		{
			if(hasNext())
			{
				Pair<INode,CmlContext> next = nextState();
				
				CmlAlphabet alpha = next.first.apply(alphabetInspectionVisitor,next.second);
			
				//we have to check for hidden event and convert them into tau events
				CmlAlphabet hiddenEvents = alpha.intersectRefsAndJoin(hidingAlphabet);
				
				CmlAlphabet returnAlpha = alpha.substract(hiddenEvents);
				
				for(ObservableEvent obsEvent : hiddenEvents.getObservableEvents())
					returnAlpha = returnAlpha.union(new CmlTauEvent(" hiding " + obsEvent.toString()));
				
			
				return returnAlpha;
			}
			//if the process is done we return the empty alphabet
			else
			{
				return new CmlAlphabet();
			}
		}
		catch(AnalysisException ex)
		{
			CmlRuntime.logger().throwing(this.toString(),"inspect()", ex);
			throw new InterpreterRuntimeException(InterpretationErrorMessages.FATAL_ERROR.customizeMessage(),ex);
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
			Pair<INode,CmlContext> next = executionStack.pop();
			prevExecution = next;
			return next.first.apply(cmlEvaluationVisitor,next.second);
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
	public Pair<INode, CmlContext> getExecutionState() {
		if(hasNext())
			return nextState();
		else
			return prevState();
	}
	
	protected boolean aborted = false;
	
	@Override
	public void setAbort(Reason reason) {

		//abort all the children
		for(CmlBehaviourThread child : children())
			child.setAbort(reason);
		
		//unregister all the channels
		for(ObservableEvent oe : registredEvents)
			oe.getChannel().onSelect().unregisterObserver(this);
		
		aborted = true;
		
		setState(CmlProcessState.FINISHED);
	}
	
	@Override
	public LexNameToken name() {
		return this.name;
	}
	
	/**
	 * Hiding methods
	 */
	
	protected void setHidingAlphabet(CmlAlphabet alphabet)
	{
		this.hidingAlphabet = alphabet;
	}
	
	protected CmlAlphabet getHidingAlphabet()
	{
		return this.hidingAlphabet;
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
	
	/**
	 * Process state methods 
	 */
	@Override
	public CmlProcessState getState() {
		return state;
	}

	protected void setState(CmlProcessState state) {
		
		if(getState() != state)
		{
			CmlProcessStateEvent ev = new CmlProcessStateEvent(this, this.state, state);
			this.state = state;
			notifyOnStateChange(ev);
			CmlRuntime.logger().finest(name() + ":" + state.toString());
		}
	}
	
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
	
	/**
	 * CmlProcessStateObserver interface 
	 */
	
	@Override
	public void onStateChange(CmlProcessStateEvent stateEvent) {

		switch(stateEvent.getTo())
		{
		case WAIT_CHILD:
		case RUNNING:
			//If a child is either in RUNNING or WAIT_CHILD state then we wait for all the children
			//to get into wait_event state
			setState(CmlProcessState.WAIT_CHILD);
			break;
		case WAIT_EVENT:
			//if at least one child are waiting for an event this process must invoke 
			//either Parallel Non-sync or sync
			if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrWaitingForEvent(this))
				setState(CmlProcessState.RUNNABLE);
			break;
		case FINISHED:
			//for any child that finishes, we unregister it since it has terminated successfully and no state change will happen again.
			stateEvent.getSource().onStateChanged().unregisterObserver(this);
			
			//if all the children are finished this process can continue and evolve into skip
			if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrWaitingForEvent(this))
				setState(CmlProcessState.RUNNABLE);
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * common helper methods
	 */
	
	/*
	 * Child support -- we must help the children
	 */
	
	/**
	 * Executes the next state of the child process silently, meaning that the trace event
	 * is disabled since the patent processes (this process) already have the event in the trace
	 * since its supervising the child processes
	 * @param child
	 * @return
	 */
	protected CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child)
	{
		child.onTraceChanged().unregisterObserver(this);
		CmlBehaviourSignal result = child.execute(supervisor());
		child.onTraceChanged().registerObserver(this);
		
		return result;
	}
	
	protected void addChild(CmlBehaviourThread child)
	{
		//Add the child to the process graph
		children().add(child);
		//Register for state change and trace change events
		child.onStateChanged().registerObserver(this);
		child.onTraceChanged().registerObserver(this);
		
		child.start(supervisor());
	}
	
	protected void removeTheChildren()
	{
		for(Iterator<CmlBehaviourThread> iterator = children().iterator(); iterator.hasNext(); )
		{
			CmlBehaviourThread child = iterator.next();
			supervisor().removePupil(child);
			iterator.remove();
		}
	}

	@Override
	public void setRestorePoint() {

		lastRestorePoint = new RestorePoint(executionStack, prevExecution, parent, children, state, env, hidingAlphabet, 
											trace, registredEvents, stateEventhandler, traceEventHandler);
		
//		//set restore point for all the children
//		for(CmlBehaviourThread child : children())
//			child.setRestorePoint();
		
		parent = null;
		stateEventhandler = new EventSourceHandler<CmlProcessStateObserver,CmlProcessStateEvent>(this,
				new EventFireMediator<CmlProcessStateObserver,CmlProcessStateEvent>() {

			@Override
			public void fireEvent(CmlProcessStateObserver observer,
					Object source, CmlProcessStateEvent event) {
				observer.onStateChange(event);
			}
		});
		
		traceEventHandler = new EventSourceHandler<CmlProcessTraceObserver,TraceEvent>(this,
				new EventFireMediator<CmlProcessTraceObserver,TraceEvent>() {

			@Override
			public void fireEvent(CmlProcessTraceObserver observer,
					Object source, TraceEvent event) {
				observer.onTraceChange(event);
			}
		});
		
		Stack<Pair<INode,CmlContext>> copyStack = new Stack<Pair<INode,CmlContext>>();
		
		for(Pair<INode,CmlContext> pair : this.executionStack)
			copyStack.add(0, new Pair<INode,CmlContext>(pair.first,pair.second.deepCopy()));
		
		this.executionStack = copyStack;		
		
		this.children = new LinkedList<ConcreteBehaviourThread>(children);
		this.hidingAlphabet = (CmlAlphabet) hidingAlphabet.clone();
		this.trace = new CmlTrace(trace);
		this.registredEvents = new LinkedList<ObservableEvent>(registredEvents);
		
		
		CmlRuntime.logger().fine("\nSetting Restore point for " + name + "\n");
	}

	@Override
	public void revertToRestorePoint() {

		if(lastRestorePoint != null)
		{
			executionStack = lastRestorePoint.executionStack; 
			prevExecution = lastRestorePoint.prevExecution;
			parent = lastRestorePoint.parent;
			children = lastRestorePoint.children;
			env = lastRestorePoint.env;
			hidingAlphabet = lastRestorePoint.hidingAlphabet; 
			trace = lastRestorePoint.trace;
			registredEvents = lastRestorePoint.registredEvents;
			stateEventhandler = lastRestorePoint.stateEventhandler; 
			traceEventHandler = lastRestorePoint.traceEventHandler;
			state = lastRestorePoint.state;
			//setState(lastRestorePoint.state);
			
//			//set restore point for all the children
//			for(CmlBehaviourThread child : children())
//				child.revertToRestorePoint();
			
			CmlRuntime.logger().fine("\n" + name + " restored\n");
			lastRestorePoint = null;
		}
		
	}

	@Override
	public boolean inBactrackMode() {
		return lastRestorePoint != null;
	}
	
}
