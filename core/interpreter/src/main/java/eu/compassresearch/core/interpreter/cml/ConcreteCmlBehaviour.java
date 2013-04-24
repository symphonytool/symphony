package eu.compassresearch.core.interpreter.cml;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.ClassContext;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEventFactory;
import eu.compassresearch.core.interpreter.cml.events.CmlTock;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.AbstractEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.AbstractSetupVisitor;
import eu.compassresearch.core.interpreter.eval.ActionSetupVisitor;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectVisitor;
import eu.compassresearch.core.interpreter.eval.CmlEvaluationVisitor;
import eu.compassresearch.core.interpreter.eval.CmlOpsToString;
import eu.compassresearch.core.interpreter.eval.VisitorAccess;
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
	protected boolean							waitPrime = false;
	
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
	
	final AbstractEvaluationVisitor cmlEvaluationVisitor;
	final AbstractSetupVisitor cmlSetupVisitor;
	
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
		
		VisitorAccess visitorAccess = new VisitorAccess() {
			
			@Override
			public void setHidingAlphabet(CmlAlphabet alpha) {
				ConcreteCmlBehaviour.this.setHidingAlphabet(alpha);
				
			}
									
			@Override
			public void mergeState(CmlBehaviour other) {
				ConcreteCmlBehaviour.this.replaceState((ConcreteCmlBehaviour)other);
				
			}
			
			@Override
			public CmlAlphabet getHidingAlphabet() {
				return hidingAlphabet;
			}

			@Override
			public void setLeftChild(CmlBehaviour child) {
				ConcreteCmlBehaviour.this.setLeftChild(child);
			}

			@Override
			public void setRightChild(CmlBehaviour child) {
				ConcreteCmlBehaviour.this.setRightChild(child);				
			}
		};
		
		cmlEvaluationVisitor = new CmlEvaluationVisitor(null,this,visitorAccess);
		
		cmlSetupVisitor = new ActionSetupVisitor(this, visitorAccess);
	}
	
	public ConcreteCmlBehaviour(INode action,Context context, LexNameToken name) throws AnalysisException
	{
		this(null,name);
		setNext(new Pair<INode, Context>(action, context));
	}
	
	public ConcreteCmlBehaviour(INode action,Context context, LexNameToken name, CmlBehaviour parent) throws AnalysisException
	{
		this(parent,name);
		setNext(new Pair<INode, Context>(action, context));
	}
	
	protected void setNext(Pair<INode, Context> newNext) throws AnalysisException
	{
		if(next == null || (newNext.first != next.first && !hasChildren()))
		{
			newNext.first.apply(cmlSetupVisitor,newNext.second);
			started = false;
		}
		next = newNext;
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
	
	/**
	 * Executes the current process behaviour
	 */
	@Override
	public void execute(CmlSupervisorEnvironment env) throws AnalysisException
	{
		this.env= env;

		//inspect if there are any immediate events
		CmlAlphabet alpha = inspect();

		started = true;

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
				//If the selected event is not a tock event then we can evaluate
				if(!(env.selectedObservableEvent() instanceof CmlTock))
					setNext(next.first.apply(cmlEvaluationVisitor,next.second));
				else
				{
					if(leftChild != null)
						leftChild.execute(supervisor());
					
					if(rightChild != null)
						rightChild.execute(supervisor());
				}
				
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
		
		for(ObservableEvent obsEvent : hiddenEvents.getObservableEvents())
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
		
		if (rightChild != null)
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

	protected void setLeftChild(CmlBehaviour child) {
		leftChild = child;
	}

	protected void setRightChild(CmlBehaviour child) {
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
		return !finished() && inspect().isEmpty();
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

	@Override
	public void replaceState(Context context) throws ValueException {
		
		//FIXME if this process has a deeper level of contexts, then these needs to be
		//stuck onto the given context
		
		next = new Pair<INode, Context>(next.first, attachAdditionalContexts(next.second,context));
		
		if(leftChild != null)
			leftChild.replaceState(next.second);
		
		if(rightChild != null)
			rightChild.replaceState(next.second);
	}
	
	private Context attachAdditionalContexts(Context src, Context dst)
	{
		//now we collect all the context below the RootContext for both the copy and the current
		//First we collect the copy contexts
		List<Context> copyContexts = new LinkedList<Context>();
		Context tmp = src;
		while(tmp != null)
		{
			copyContexts.add(0,tmp);
			tmp = tmp.outer;
		}
		//Next we collect the current contexts
		List<Context> contexts = new LinkedList<Context>();
		tmp = dst;
		while(tmp != null)
		{
			contexts.add(0,tmp);
			tmp = tmp.outer;
		}
		Context newCurrent = contexts.get(contexts.size()-1);
		//We know that the copy context must be at least as big as the current one so we iterate through those
		for(int i = contexts.size() ; i < copyContexts.size();i++)
		{
			Context iCopy = copyContexts.get(i);

			//newly Added contexts
			//FIXME this should not be created like that a more general solution to this must
			//be made. Eg. a method call that can clone the context with a new outer pointer
			if(iCopy instanceof ClassContext)
				throw new InterpreterRuntimeException("Not yet implemented!");
			else if(iCopy instanceof ObjectContext)
				newCurrent = CmlContextFactory.newObjectContext(iCopy.location, iCopy.title, newCurrent, iCopy.getSelf());
			else if(iCopy instanceof StateContext)
				throw new InterpreterRuntimeException("Trying to merge a StateContext, this should never happen!");
			else
				newCurrent = CmlContextFactory.newContext(iCopy.location, iCopy.title, newCurrent);


			for(Entry<LexNameToken,Value> entry : iCopy.entrySet())
			{
				newCurrent.put(entry.getKey(), entry.getValue());
			}
		}
		
		return newCurrent;
	}
}
