package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.ASkipStm;
import org.overture.interpreter.runtime.ClassContext;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.runtime.StateContext;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.api.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.CmlBehaviorState;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlTrace;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.TransitionEvent;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateEvent;
import eu.compassresearch.core.interpreter.api.events.CmlBehaviorStateObserver;
import eu.compassresearch.core.interpreter.api.events.EventFireMediator;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.EventSourceHandler;
import eu.compassresearch.core.interpreter.api.events.TraceEvent;
import eu.compassresearch.core.interpreter.api.events.TraceObserver;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.utility.Pair;

class ConcreteCmlBehaviour implements CmlBehaviour
{
	final static Logger logger = LoggerFactory.getLogger("cml-interpreter");
	
	private static int globalIdCount = 0;
	private static final long serialVersionUID = -4920762081111266274L;

	private static int getNextId()
	{
		return globalIdCount++;
	}

	/*
	 * Instance variables
	 */

	/**
	 * Name of the instance
	 */
	protected BehaviourName name;

	/**
	 * A factory for creating new Cmlbehavior instances
	 */
	final CmlBehaviorFactory cmlBehaviorFactory;
	/**
	 * Unique id for the process
	 */
	protected int processId = getNextId();
	/**
	 * The result of the last inspection performed on this object.
	 */
	protected Inspection lastInspection = null;

	// Process/Action Graph variables
	/**
	 * Parent behavior
	 */
	protected final CmlBehaviour parent;
	protected CmlBehaviour leftChild = null;
	protected CmlBehaviour rightChild = null;

	/*
	 * Visitor instances
	 */

	/**
	 * The setup visitor
	 */
	final transient QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> setupVisitor;

	/**
	 * The inspection visitor
	 */
	final transient QuestionAnswerCMLAdaptor<Context, Inspection> inspectionVisitor;

	/*
	 * Process/Action state variables
	 */
	/**
	 * The next INode to execute in the given Context
	 */
	Pair<INode, Context> next;
	/**
	 * This pair contains pre-constructed contexts for the child behaviors. They are only used when the child behaviors
	 * are created
	 */
	Pair<Context, Context> preConstructedChildContexts = null;
	
	CmlBehaviorState state = null;

	// This might get used to boost the performance
	// protected Map<INode,Object> localStore = new HashMap<INode,Object>();

	// Denotational semantics

	/**
	 * This contains the current trace of this behavior. This corresponds to the observation tr' in the CML semantics
	 */
	protected final CmlTrace trPrime = new CmlTrace();
	/**
	 * This is true when the process is waiting for the environment
	 */
	protected boolean waitPrime = false;

	protected EventSourceHandler<CmlBehaviorStateObserver, CmlBehaviorStateEvent> stateEventhandler = new EventSourceHandler<CmlBehaviorStateObserver, CmlBehaviorStateEvent>(this, new EventFireMediator<CmlBehaviorStateObserver, CmlBehaviorStateEvent>()
	{

		@Override
		public void fireEvent(CmlBehaviorStateObserver observer, Object source,
				CmlBehaviorStateEvent event)
		{
			observer.onStateChange(event);
		}
	});

	protected EventSourceHandler<TraceObserver, TraceEvent> traceEventHandler = new EventSourceHandler<TraceObserver, TraceEvent>(this, new EventFireMediator<TraceObserver, TraceEvent>()
	{

		@Override
		public void fireEvent(TraceObserver observer, Object source,
				TraceEvent event)
		{
			observer.onTraceChange(event);
		}
	});

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            set the parent here if any else set to null
	 */
	private ConcreteCmlBehaviour(CmlBehaviour parent, BehaviourName name,
			CmlBehaviorFactory cmlBehaviorFactory)
	{

		this.parent = parent;
		this.name = name;
		this.cmlBehaviorFactory = cmlBehaviorFactory;

		VisitorAccess visitorAccess = new VisitorAccess()
		{

			@Override
			public void setLeftChild(CmlBehaviour child)
			{
				final CmlBehaviorStateObserver obs = new CmlBehaviorStateObserver()
				{
					
					@Override
					public void onStateChange(CmlBehaviorStateEvent stateEvent)
					{
						//if(state == CmlBehaviorState.WAITING_CHILD)
							newTransitionEvent(TransitionEvent.CHILD_EVENT);
		
					}
				};
				
				if(child != null && child.onStateChanged() != null)
					child.onStateChanged().registerObserver(obs);
				else if(leftChild != null && leftChild.onStateChanged() != null)
				{
					leftChild.onStateChanged().unregisterObserver(obs);
				}
				
				ConcreteCmlBehaviour.this.setLeftChild(child);
				newTransitionEvent(TransitionEvent.CHILD_EVENT);
			}

			@Override
			public void setRightChild(CmlBehaviour child)
			{
							
				final CmlBehaviorStateObserver obs = new CmlBehaviorStateObserver()
				{
					
					@Override
					public void onStateChange(CmlBehaviorStateEvent stateEvent)
					{
						//if(state == CmlBehaviorState.WAITING_CHILD)
							newTransitionEvent(TransitionEvent.CHILD_EVENT);
		
					}
				};
				
				if(child != null && child.onStateChanged() != null)
					child.onStateChanged().registerObserver(obs);
				else if(rightChild != null &&  rightChild.onStateChanged() != null)
				{
					rightChild.onStateChanged().unregisterObserver(obs);
				}
				
				ConcreteCmlBehaviour.this.setRightChild(child);
				newTransitionEvent(TransitionEvent.CHILD_EVENT);
			}

			@Override
			public Pair<Context, Context> getChildContexts(Context context)
			{

				if (preConstructedChildContexts != null)
				{
					return preConstructedChildContexts;
				} else
				{
					return new Pair<Context, Context>(context, context);
				}
			}

			@Override
			public void setChildContexts(Pair<Context, Context> contexts)
			{

				ConcreteCmlBehaviour.this.preConstructedChildContexts = contexts;
			}

			@Override
			public void newTransitionEvent(TransitionEvent event)
			{
				triggerEvent(event);
			}
			
		};

		// Initialize the visitors
		setupVisitor = new CmlSetupVisitor(this, visitorAccess, this.cmlBehaviorFactory);
		inspectionVisitor = new CmlInspectionVisitor(this, this.cmlBehaviorFactory, visitorAccess);
	
		// must not notify before name is set
		state = CmlBehaviorState.RUNNING;
	}

	ConcreteCmlBehaviour(INode action, Context context, CmlBehaviour parent,
			CmlBehaviorFactory cmlBehaviorFactory) throws AnalysisException
	{
		this(parent, new BehaviourName("Child of " + parent.getName()), cmlBehaviorFactory);
		setNext(new Pair<INode, Context>(action, context));
	}

	ConcreteCmlBehaviour(INode action, Context context, BehaviourName name,
			CmlBehaviour parent, CmlBehaviorFactory cmlBehaviorFactory)
			throws AnalysisException
	{
		this(parent, name, cmlBehaviorFactory);
		setNext(new Pair<INode, Context>(action, context));
	}

	@Override
	public String nextStepToString()
	{

		if (hasChildren())
		{
			String stringRep = "(" + getLeftChild().nextStepToString() + ")"
					+ CmlOpsToString.toString(next.first);

			if (children().size() > 1)
			{
				stringRep += "(" + getRightChild().nextStepToString() + ")";
			}

			return stringRep;
		} else
		{
			return next.first.toString();
		}
	}

	@Override
	public String toString()
	{

		return name.toString();
	}

	/**
	 * Executes the current process behaviour
	 */
	@Override
	public void execute(CmlTransition selectedTransition) throws AnalysisException
	{
		/*
		 * If the selected transition is tock, then we need to execute the 
		 * children as well to make time pass in the entire process tree
		 */
		if (selectedTransition instanceof TimedTransition)
		{
			if (leftChild != null)
			{
				leftChild.execute(selectedTransition);
			}

			if (rightChild != null)
			{
				rightChild.execute(selectedTransition);
			}
		}
		// If the selected event is not tock then we can evaluate it
		else
		{
			waitPrime = false;
			setNext(lastInspection.getTransitionFunction().execute(selectedTransition));
		}
		performInspection();	
		performTransitionEvents();
		updateTrace(selectedTransition);
		
	}
	
	protected boolean setNext(Pair<INode, Context> newNext)
			throws AnalysisException
	{

		if (next == null || newNext.first != next.first && !hasChildren())
		{
			next = newNext.first.apply(setupVisitor, newNext.second);
			return true;
		} else
		{
			next = newNext;
			return false;
		}
	}
	
	private void performInspection() throws AnalysisException
	{
		if (lastInspection == null
				|| !lastInspection.getTrace().equals(this.getTraceModel()))
		{
			lastInspection = next.first.apply(inspectionVisitor, next.second);
		}
	}
	
	private void performTransitionEvents()
	{
		//if next node is skip we the state is finished
		
		if(state != CmlBehaviorState.STOPPED && isDeadlocked())
			triggerEvent(TransitionEvent.STOP);
		else if(state != CmlBehaviorState.WAITING_CHILD && isChildrenWaiting())
			triggerEvent(TransitionEvent.WAIT_CHILD);
	}

	@Override
	public CmlTransitionSet inspect() throws AnalysisException
	{
		performInspection();
		return lastInspection.getTransitions();
	}

	/**
	 * Update the trace and fires the trace event
	 * 
	 * @param The recent executed transition
	 */
	private void updateTrace(CmlTransition event)
	{
		trPrime.addEvent(event);
		notifyOnTraceChange(new TraceEvent(this, event));
	}

	private void triggerEvent(TransitionEvent event)
	{
		CmlBehaviorState oldState = state; 
		state = state.next(event); 
		
		if(oldState != state)
			notifyOnStateChange(state);
	}
	
	/*
	 * Execute region end
	 */

	@Override
	public BehaviourName getName()
	{
		return this.name;
	}

	@Override
	public int getId()
	{
		return this.processId;
	}
	
	@Override
	public int compareTo(CmlBehaviour o)
	{
		return Integer.compare(getId(), o.getId());
	}

	/**
	 * Process graph methods
	 */

	@Override
	public long level()
	{

		if (parent() == null)
		{
			return 0;
		} else
		{
			return parent().level() + 1;
		}
	}

	@Override
	public CmlBehaviour parent()
	{
		return parent;
	}

	@Override
	public List<CmlBehaviour> children()
	{
		List<CmlBehaviour> children = new LinkedList<CmlBehaviour>();

		if (leftChild != null)
		{
			children.add(leftChild);
		}

		if (rightChild != null)
		{
			children.add(rightChild);
		}

		return children;
	}

	@Override
	public CmlBehaviour getLeftChild()
	{
		return leftChild;
	}

	@Override
	public CmlBehaviour getRightChild()
	{
		return rightChild;
	}

	/**
	 * Sets the left child node
	 * 
	 * @param child
	 */
	protected void setLeftChild(CmlBehaviour child)
	{
		leftChild = child;
	}

	/**
	 * Sets the right child node
	 * 
	 * @param child
	 */
	protected void setRightChild(CmlBehaviour child)
	{
		rightChild = child;
	}

	@Override
	public boolean hasChildren()
	{
		return leftChild != null || rightChild != null;
	}

	/*
	 * State related methods
	 */

	@Override
	public boolean waiting()
	{
		return state == CmlBehaviorState.WAITING_CHILD || 
				state == CmlBehaviorState.WAITING_EVENT;
	}
	
	private boolean isChildrenWaiting()
	{
		boolean ret = false;

		if (getLeftChild() != null)
		{
			ret = getLeftChild().waiting();

			if (getRightChild() != null)
			{
				ret &= getRightChild().waiting();
			}
		}

		return ret;
	}

	@Override
	public boolean isDivergent()
	{
		return next.first instanceof ADivAction;
	}

	@Override
	public boolean finished()
	{
		//return state == CmlBehaviorState.FINISHED;
		return next.first instanceof ASkipAction || next.first instanceof ASkipStm;
	}

	@Override
	public boolean deadlocked() throws AnalysisException
	{
		return isDeadlocked();
	}
	
	private boolean isDeadlocked()
	{
		CmlTransitionSet alpha = lastInspection.getTransitions();
		
		return (!finished() && (
				alpha.isEmpty()
				||
				(
				alpha.size() <= 1 && 
				alpha.filterByType(TimedTransition.class).size() == 1) &&
				!((TimedTransition) alpha.filterByTypeAsSet(ObservableTransition.class).first()).hasTimeLimit())
				);
	}

	protected void notifyOnStateChange(CmlBehaviorState state)
	{
		stateEventhandler.fireEvent(new CmlBehaviorStateEvent(this, state));
		logger.trace(getName() + ":" + state.toString());
	}

	@Override
	public EventSource<CmlBehaviorStateObserver> onStateChanged()
	{
		return stateEventhandler;
	}

	@Override
	public Pair<INode, Context> getNextState()
	{
		return next;
	}
	
	@Override
	public CmlBehaviorState getState()
	{
		return state;
	}
	
	@Override
	public void replaceState(Context context) throws ValueException
	{

		// stuck onto the given context
		next = new Pair<INode, Context>(next.first, attachAdditionalContexts(next.second, context));

		if (leftChild != null)
		{
			leftChild.replaceState(next.second);
		}

		if (rightChild != null)
		{
			rightChild.replaceState(next.second);
		}
	}

	/*
	 * Denotational Semantics Information
	 */
	@Override
	public CmlTrace getTraceModel()
	{
		return trPrime;
	}

	@Override
	public long getCurrentTime()
	{
		long nTocks = 0;

		for (CmlTransition ev : getTraceModel().getTrace())
		{
			if (ev instanceof TimedTransition)
			{
				nTocks++;
			}
		}

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
	public EventSource<TraceObserver> onTraceChanged()
	{
		return traceEventHandler;
	}

	/*
	 * Private methods
	 */

	/**
	 * Copies and attaches any additional contexts from src to dst. Where additional means that if src is deeper than
	 * dst then src.depth() - dst.depth() additional contexts will be copied from src to dst
	 * 
	 * @param src
	 *            The contexts that are copied
	 * @param dst
	 *            The new outer context of the first copied context from src
	 * @return
	 */
	private Context attachAdditionalContexts(Context src, Context dst)
	{
		// now we collect all the context below the RootContext for both the copy and the current
		// First we collect the copy contexts
		List<Context> copyContexts = new LinkedList<Context>();
		Context tmp = src;
		while (tmp != null)
		{
			copyContexts.add(0, tmp);
			tmp = tmp.outer;
		}
		// Next we collect the current contexts
		List<Context> contexts = new LinkedList<Context>();
		tmp = dst;
		while (tmp != null)
		{
			contexts.add(0, tmp);
			tmp = tmp.outer;
		}
		Context newCurrent = contexts.get(contexts.size() - 1);
		// We know that the copy context must be at least as big as the current one so we iterate through those
		for (int i = contexts.size(); i < copyContexts.size(); i++)
		{
			Context iCopy = copyContexts.get(i);

			// newly Added contexts
			// FIXME this should not be created like that a more general solution to this must
			// be made. Eg. a method call that can clone the context with a new outer pointer
			if (iCopy instanceof ClassContext)
			{
				throw new InterpreterRuntimeException("Not yet implemented!");
			} else if (iCopy instanceof ObjectContext)
			{
				newCurrent = CmlContextFactory.newObjectContext(iCopy.location, iCopy.title, newCurrent, iCopy.getSelf());
			} else if (iCopy instanceof StateContext)
			{
				throw new InterpreterRuntimeException("Trying to merge a StateContext, this should never happen!");
			} else
			{
				newCurrent = CmlContextFactory.newContext(iCopy.location, iCopy.title, newCurrent);
			}

			for (Entry<ILexNameToken, Value> entry : iCopy.entrySet())
			{
				newCurrent.put(entry.getKey(), entry.getValue());
			}
		}

		return newCurrent;
	}
}
