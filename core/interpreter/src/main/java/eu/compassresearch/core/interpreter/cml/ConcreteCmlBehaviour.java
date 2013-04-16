package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.events.AbstractObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEventFactory;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectVisitor;
import eu.compassresearch.core.interpreter.eval.CmlEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.CmlOpsToString;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.CmlProcessStateObserver;
import eu.compassresearch.core.interpreter.events.CmlProcessTraceObserver;
import eu.compassresearch.core.interpreter.events.EventFireMediator;
import eu.compassresearch.core.interpreter.events.EventSource;
import eu.compassresearch.core.interpreter.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.util.Pair;

public class ConcreteCmlBehaviour implements CmlBehaviour
	{
	
	private static final long 					serialVersionUID = -4920762081111266274L;
	
	/**
	 * Instance variables
	 */
	//name of the instance
	protected LexNameToken 						name;
	Pair<INode,Context>                         next;
	
	//Process/Action Graph variables
	protected final CmlBehaviour 				parent;
	protected CmlBehaviour						leftChild = null;
	protected CmlBehaviour						rightChild = null;
	
	//Process/Action state variables
	protected CmlProcessState 					state;
	protected boolean 							started = false;
	protected boolean							wait = false;
	
	//Current supervisor
	protected CmlSupervisorEnvironment 			env;
	
	//use for the hiding operator
	protected CmlAlphabet 						hidingAlphabet = new CmlAlphabet();
	
	//Denotational semantics
	protected final CmlTrace 					trace = new CmlTrace();
	
	//Helper to inspect the immediate Alphabet
	protected final AlphabetInspectVisitor 		alphabetInspectionVisitor = new AlphabetInspectVisitor(this);
	
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
	
	AbstractEvaluationVisitor cmlEvaluationVisitor = new CmlEvaluationVisitor();
	
	/**
	 * Constructor
	 * @param parent set the parent here if any else set to null
	 */
	private ConcreteCmlBehaviour(CmlBehaviour parent,LexNameToken name)
	{
		state = CmlProcessState.INITIALIZED;
		this.parent = parent;
		this.name = name;
		wait = false;
		started = false;
		
		cmlEvaluationVisitor.init(new AbstractEvaluationVisitor.ControlAccess() {
			
			@Override
			public void setHidingAlphabet(CmlAlphabet alpha) {
				ConcreteCmlBehaviour.this.setHidingAlphabet(alpha);
				
			}
			
			@Override
			public CmlBehaviour ownerThread() {
				return ConcreteCmlBehaviour.this;
			}
			
			@Override
			public void mergeState(CmlBehaviour other) {
				ConcreteCmlBehaviour.this.replaceState((ConcreteCmlBehaviour)other);
				
			}
			
			@Override
			public CmlAlphabet getHidingAlphabet() {
				return hidingAlphabet;
			}
		});
	}
	
//	public ConcreteCmlBehaviour(INode action,Context context, CmlBehaviour parent, CmlBehaviour left, CmlBehaviour right)
//	{
//		this(null,name);
//	}
	
	public ConcreteCmlBehaviour(INode action,Context context, LexNameToken name)
	{
		this(null,name);
		next = new Pair<INode, Context>(action, context);
	}
	
	public ConcreteCmlBehaviour(INode action,Context context, LexNameToken name, CmlBehaviour parent)
	{
		this(parent,name);
		next = new Pair<INode, Context>(action, context);
	}
	
	@Override
	public String nextStepToString() {

		if(hasChildren())
		{
			CmlBehaviour leftChild = children().get(0);

			String stringRep = "(" + leftChild.nextStepToString() + ")" + CmlOpsToString.toString(next.first);

			if(children().size() > 1)
			{
				CmlBehaviour rightChild = children().get(1);
				stringRep += "(" + rightChild.nextStepToString()+")";
			}

			return stringRep;
		}
		else{
			return next.first.toString();
		}
	}
	
	@Override
	public String toString() {
	
		return name.toString();
	}
	
	protected void replaceState(ConcreteCmlBehaviour other)
	{
//			replaceExistingContexts(other.next.second);
//			//get the state replace the current state
//			next = other.getExecutionStack();
//			for(Pair<INode,Context> state : other.getExecutionStack())
//				pushNext(state.first,state.second);
//		}
//		else
//			throw new RuntimeException("bye bye");
////		{
////			pushNext(other.prevState().first, 
////					other.prevState().second);
////		}
	}
	
	//we need to replace the existing contexts from top down, 
	//making sure we don't add any extra ones from the newContext to the 
	private void replaceExistingContexts(Context newContext)
	{
//		for(Pair<INode,Context> pair : executionStack)
//		{
//			int index = executionStack.indexOf(pair);
//			executionStack.setElementAt(new Pair<INode, Context>(pair.first,replaceFrame(pair.second,newContext)), index);
//		}
	}
	
	private Context replaceFrame(Context oldContext, Context newContext)
	{
		//extract the contexts of the old
		List<Context> oldContexts= new LinkedList<Context>();

		Context tmp = oldContext;
		while(tmp != null)
		{
			oldContexts.add(0,tmp);
			tmp = tmp.outer;
		}
		
		List<Context> newContexts = new LinkedList<Context>();
		tmp = newContext;
		while(tmp != null)
		{
			newContexts.add(0,tmp);
			tmp = tmp.outer;
		}
		//FIXME This is not always the case. The scoping rules are not
		Context result = newContexts.get(oldContexts.size()-1);
		
		return result;
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
	public void execute(CmlSupervisorEnvironment env) 
	{
		this.started = true;
		this.env= env;

		//inspect if there are any immediate events
		CmlAlphabet alpha = inspect();
				
		try
		{
			if(alpha.isEmpty())
			{
				setState(CmlProcessState.STOPPED);
				wait = false;
			}
			else 
			{	
				/**
				 *	If the selected event is valid and is in the immediate alphabet of the process 
				 *	then we can continue.
				 *  
				 */
				//
				if(env.isSelectedEventValid() &&  
						alpha.containsImprecise(env.selectedObservableEvent()))
				{
					wait = false;
					next = next.first.apply(cmlEvaluationVisitor,next.second);
					updateTrace(env.selectedObservableEvent());
				}
				//if no communication is selected by the supervisor or we cannot sync the selected events
				//then we go to wait state and wait for channelEvent
				else 
				{
					setState(CmlProcessState.WAIT);
					wait = true;
				}
			}
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
			CmlAlphabet alpha = next.first.apply(alphabetInspectionVisitor,next.second);

			//we have to check for hidden event and convert them into tau events before we return the next alpha
			//return alpha;
			return HandleHiding(alpha);
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
	
	private CmlAlphabet HandleHiding(CmlAlphabet alpha)
	{
		CmlAlphabet hiddenEvents = alpha.intersect(hidingAlphabet);
		
		CmlAlphabet resultAlpha = alpha.subtract(hiddenEvents);
		
		for(AbstractObservableEvent obsEvent : hiddenEvents.getObservableEvents())
			resultAlpha = resultAlpha.union(CmlEventFactory.newTauEvent(this,null,null," hiding " + obsEvent.toString()));	
		return resultAlpha;
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
	
	/*
	 * Execute region end
	 */
	
	@Override
	public CmlSupervisorEnvironment supervisor() {
		return env;
	}
	
	@Override
	public Pair<INode, Context> getExecutionState() {
		return next;
	}
	
	protected boolean aborted = false;
	
	@Override
	public void setAbort(Reason reason) {

		//abort all the children
		for(CmlBehaviour child : children())
			child.setAbort(reason);
		
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
	public CmlBehaviour parent() {
		return parent;
	}

	@Override
	public List<CmlBehaviour> children() {
		List<CmlBehaviour> children = new LinkedList<CmlBehaviour>();
		if(leftChild != null)
			children.add(leftChild);
		else if (rightChild != null)
			children.add(rightChild);
		
		return children;
	}
	
	@Override
	public CmlBehaviour getLeftChild() {
		return leftChild;
	}

	@Override
	public CmlBehaviour getRightChild() {
		return rightChild;
	}

	@Override
	public void setLeftChild(CmlBehaviour child) {
		leftChild = child;
	}

	@Override
	public void setRightChild(CmlBehaviour child) {
		rightChild = child;		
	}

	@Override
	public boolean hasChildren() {
		return children().size() > 0;
	}

	/**
	 * 
	 * State related methods
	 * 
	 */
	@Override
	public boolean started() {
		return started;
	}

	@Override public boolean waiting() {
		return wait;
	}
	
	@Override
	public boolean finished() {
		return !hasChildren() && 
				next.first instanceof ASkipAction;
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
			this.state = state;
			notifyOnStateChange(new CmlProcessStateEvent(this, this.state, state));
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
	 * common helper methods
	 */
	
	/*
	 * Child support -- we must help the children
	 */
	
	protected void addChild(CmlBehaviour child)
	{
		//Add the child to the process graph
		children().add(child);
		supervisor().addPupil(child);
	}
	
}
