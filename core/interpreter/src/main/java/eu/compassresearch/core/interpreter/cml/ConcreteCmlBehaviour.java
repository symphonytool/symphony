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

	/*
	 * Instance variables
	 */
	
	/**
	 * Name of the instance
	 */
	protected LexNameToken 						name;
	
	//Process/Action Graph variables
	protected final CmlBehaviour 				parent;
	protected CmlBehaviour						leftChild = null;
	protected CmlBehaviour						rightChild = null;

	/*
	 * Visitor instances
	 */
	
	/**
	 * The evaluation visitor
	 */
	final AbstractEvaluationVisitor cmlEvaluationVisitor;
	/**
	 * The setup visitor
	 */
	final AbstractSetupVisitor cmlSetupVisitor;
	
	//Process/Action state variables
	
	protected boolean 							started = false;
	protected boolean							waitPrime = false;
	protected boolean 							aborted = false;
	//The next INode to execute in the given Context
	Pair<INode,Context>                         next;
	
	//Current supervisor
	protected CmlSupervisorEnvironment 			env;

	//This might get used to boost the performance
	//protected Map<INode,Object>                	localStore = new HashMap<INode,Object>();
	
	//Denotational semantics
	//This contains the current trace of this process
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

	protected EventSourceHandler<CmlProcessTraceObserver,TraceEvent>  			traceEventHandler = 
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
	private ConcreteCmlBehaviour(CmlBehaviour parent,LexNameToken name)
	{
		notifyOnStateChange(CmlProcessState.INITIALIZED);
		this.parent = parent;
		this.name = name;
		waitPrime = false;
		started = false;

		VisitorAccess visitorAccess = new VisitorAccess() {

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
			String stringRep = "(" + getLeftChild().nextStepToString() + ")" + CmlOpsToString.toString(next.first);

			if(children().size() > 1)
			{
				stringRep += "(" + getRightChild().nextStepToString()+")";
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

		/*
		 *	If the selected event is valid and is in the immediate alphabet of the process 
		 *	then we can continue.
		 *  
		 */
		if(env.isSelectedEventValid() &&  
				alpha.containsImprecise(env.selectedObservableEvent()))
		{
			
			//If the selected event is not a tock event then we can evaluate
			if(!(env.selectedObservableEvent() instanceof CmlTock))
			{
				waitPrime = false;
				setNext(next.first.apply(cmlEvaluationVisitor,next.second));
			}
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
			waitPrime = true;
		}
	}

	@Override
	public CmlAlphabet inspect() 
	{
		try
		{
			return next.first.apply(alphabetInspectionVisitor,next.second);
		}
		catch(AnalysisException ex)
		{
			CmlRuntime.logger().throwing(this.toString(),"inspect()", ex);
			throw new InterpreterRuntimeException(InterpretationErrorMessages.FATAL_ERROR.customizeMessage(),ex);
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

	@Override
	public void setAbort(Reason reason) {

		//abort all the children
		for(CmlBehaviour child : children())
			child.setAbort(reason);

		aborted = true;

		notifyOnStateChange(CmlProcessState.FINISHED);
	}

	@Override
	public LexNameToken name() {
		return this.name;
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

	/**
	 * Sets the left child node
	 * @param child
	 */
	protected void setLeftChild(CmlBehaviour child) {
		leftChild = child;
	}

	/**
	 * Sets the right child node
	 * @param child
	 */
	protected void setRightChild(CmlBehaviour child) {
		rightChild = child;		
	}

	@Override
	public boolean hasChildren() {
		return leftChild != null || rightChild != null;
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
		return waitPrime;
	}

	@Override
	public boolean finished() {
		return !hasChildren() && 
				next.first instanceof ASkipAction;
	}

	@Override
	public boolean deadlocked() {

		if(!finished())
		{
			CmlAlphabet alpha = inspect();

			//A Process is deadlocked if its immediate alphabet is only tock with no limit
			if(alpha.getAllEvents().size() == 1 && alpha.getObservableEvents().size() == 1)
			{
				ObservableEvent obsEvent = alpha.getObservableEvents().iterator().next();
				return (obsEvent instanceof CmlTock) && !((CmlTock) obsEvent).hasLimit();
			}
			else
				return false;
		}
		else
			return false;
	}

	protected void notifyOnStateChange(CmlProcessState state)
	{
		stateEventhandler.fireEvent(new CmlProcessStateEvent(this, state));
		CmlRuntime.logger().finest(name() + ":" + state.toString());
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
		if(finished())
			return CmlProcessState.FINISHED;
		else if(deadlocked())
			return CmlProcessState.STOPPED;
		else
			return null;
	}

	/*
	 * Denotational Semantics Information
	 */
	@Override
	public CmlTrace getTraceModel() {
		return trace;
	}
	
	@Override
	public long getCurrentTime() {
		long nTocks = 0;

		for(CmlEvent ev : getTraceModel().getTrace())
			if(ev instanceof CmlTock)
				nTocks++;

		return nTocks;
	}

	/*
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
	
	/*
	 * Private methods
	 */

	@Override
	public void replaceState(Context context) throws ValueException {

		//stuck onto the given context
		next = new Pair<INode, Context>(next.first, attachAdditionalContexts(next.second,context));

		if(leftChild != null)
			leftChild.replaceState(next.second);

		if(rightChild != null)
			rightChild.replaceState(next.second);
	}

	/**
	 * Copies and attaches any additional contexts from src to dst. Where additional means that if 
	 * src is deeper than dst then src.depth() - dst.depth() additional contexts will be copied from src to dst
	 * @param src The contexts that are copied
	 * @param dst The new outer context of the first copied context from src
	 * @return
	 */
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
