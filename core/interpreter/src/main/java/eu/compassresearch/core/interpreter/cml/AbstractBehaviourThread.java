package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAction.parallelCompositionHelper;
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
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.CmlValue;

abstract class AbstractBehaviourThread extends QuestionAnswerCMLAdaptor<CmlContext, CmlBehaviourSignal>
		implements CmlBehaviourThread , ChannelObserver, CmlProcessStateObserver, CmlProcessTraceObserver {
	
	private static final long 					serialVersionUID = -4920762081111266274L;
	
	/**
	 * Instance variables
	 */
	//name of the instance
	protected LexNameToken 						name;
	
	//Stack machine variables
	private Stack<Pair<INode,CmlContext>> 			executionStack = new Stack<Pair<INode,CmlContext>>();
	private Pair<INode,CmlContext> 					prevExecution = null;
	
	//Process/Action Graph variables
	protected final AbstractBehaviourThread 	parent;
	protected List<AbstractBehaviourThread> 	children = new LinkedList<AbstractBehaviourThread>();
	
	//Process/Action state variables
	protected CmlProcessState 					state;
	
	//Current supervisor
	protected CmlSupervisorEnvironment 			env;
	
	//use for making random but deterministic decisions
	protected Random 							rnd = new Random(9784345);
	
	//use for the hiding operator
	protected CmlAlphabet 						hidingAlphabet = new CmlAlphabet();
	
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
	public AbstractBehaviourThread(AbstractBehaviourThread parent,LexNameToken name)
	{
		state = CmlProcessState.INITIALIZED;
		this.parent = parent;
		this.name = name;
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
	
	protected void mergeState(AbstractBehaviourThread other)
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
	
	protected abstract INode createSkip();
	
	protected abstract CmlBehaviourThread createChild(INode node, CmlContext question, LexNameToken name);
			
		
	protected CmlBehaviourSignal caseASequentialComposition(INode leftNode, INode rightNode, CmlContext question)
			throws AnalysisException 
	{
		//First push right and then left, so that left get executed first
		pushNext(rightNode, question);
		pushNext(leftNode, question);
			
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected <V extends CmlBehaviourThread> CmlBehaviourSignal  caseParallelBeginGeneral(V left, V right, CmlContext question)
	{
		//add the children to the process graph
		addChild(left);
		addChild(right);

		//Now let this process wait for the children to get into a waitForEvent state
		setState(CmlProcessState.WAIT_CHILD);

		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected CmlBehaviourSignal caseGeneralisedParallelismParallel(INode node,parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, CmlContext question) throws AnalysisException
	
	{
		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"
		CmlBehaviourSignal result = CmlBehaviourSignal.FATAL_ERROR;

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			result = helper.caseParallelBegin();
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);
		}
		//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(this))
		{
			result = caseParallelEnd(question); 
		}
		//At least one child is not finished and waiting for event, this will either invoke the Parallel Non-sync or Sync rule
		else if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrWaitingForEvent(this))
		{
			result = caseParallelSyncOrNonsync(chansetExp, question);
			//We push the current state, 
			pushNext(node, question);
		}

		return result;
	}
	
	protected CmlBehaviourSignal caseParallelSync()
	{

		CmlBehaviourThread leftChild = children().get(0);
		CmlAlphabet leftChildAlpha = leftChild.inspect(); 
		CmlBehaviourThread rightChild = children().get(1);
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		if(leftChildAlpha.containsObservableEvent(supervisor().selectedObservableEvent()) )
		{
			return executeChildAsSupervisor(leftChild);
		}
		else if(rightChildAlpha.containsObservableEvent(supervisor().selectedObservableEvent()) )
		{
			return executeChildAsSupervisor(rightChild);
		}
		else
		{
			return CmlBehaviourSignal.FATAL_ERROR;
		}
	}
	
	protected CmlBehaviourSignal caseParallelEnd(CmlContext question)
	{
		removeTheChildren();
		
		//now this process evolves into Skip
		pushNext(createSkip(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected CmlBehaviourSignal caseParallelSyncOrNonsync(PVarsetExpression chansetExp, CmlContext question) throws AnalysisException
	{
		//convert the channelset of the current node to a alphabet
		CmlAlphabet cs =  ((CmlValue)chansetExp.
				apply(cmlEvaluator,question)).cmlAlphabetValue(question);
		
		//get the immediate alphabets of the left and right child
		CmlBehaviourThread leftChild = children().get(0);
		CmlAlphabet leftChildAlpha = leftChild.inspect().flattenSyncEvents(); 
		CmlBehaviourThread rightChild = children().get(1);
		CmlAlphabet rightChildAlpha = rightChild.inspect().flattenSyncEvents();

		//convert the selected event to a CmlAlphabet
		CmlAlphabet selectedEventAlpha = supervisor().selectedObservableEvent().getAsAlphabet();
		//now make the intersection between the selectedEventAlpha and the children's alpha
		CmlAlphabet leftOption = selectedEventAlpha.intersect(leftChildAlpha);
		CmlAlphabet rightOption = selectedEventAlpha.intersect(rightChildAlpha);
		
		//if both intersections are non empty it must be a sync event
		if(!leftOption.isEmpty() &&
				!rightOption.isEmpty())
		{
			//supervisor().setSelectedObservableEvent(leftOption.getObservableEvents().iterator().next());
			executeChildAsSupervisor(leftChild);
			//supervisor().setSelectedObservableEvent(rightOption.getObservableEvents().iterator().next());
			executeChildAsSupervisor(rightChild);
			return CmlBehaviourSignal.EXEC_SUCCESS;
		}
		else if(!leftOption.isEmpty())
		{
			return executeChildAsSupervisor(leftChild);
		}
		else if(!rightOption.isEmpty())
		{
			return executeChildAsSupervisor(rightChild);
		}
		
		return CmlBehaviourSignal.FATAL_ERROR;
	}
	
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
	
	/**
	 *  Common transitions
	 */
	protected CmlBehaviourSignal caseAExternalChoice(
			INode node, INode leftNode, LexNameToken leftName, INode rightNode, LexNameToken rightName, CmlContext question)
			throws AnalysisException {
		
		CmlBehaviourSignal result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			
			//TODO: create a local copy of the question state for each of the actions
			CmlBehaviourThread leftInstance = createChild(leftNode, question, leftName);
					//new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,left.getLocation()));
			
			CmlBehaviourThread rightInstance = createChild(rightNode, question,rightName); 
					//new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),right.getLocation()));
			
			result = caseExternalChoiceBegin(leftInstance,rightInstance,question);
			
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);
		}
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if(CmlBehaviourThreadUtility.existsAFinishedChild(this))
		{
			result = caseExternalChoiceSkip();
		}
		//if this is true, then we can resolve the choice to the event
		//of one of the children that are waiting for events
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(this))
		{
			result = caseExternalChoiceEnd();
		}
		else
			result = CmlBehaviourSignal.FATAL_ERROR;
		
		return result;
		
	}
	
	/**
	 * External Choice helper methods
	 */

	/**
	 * handles the External Choice Begin Rule
	 * @param node
	 * @param question
	 * @return
	 */
	private CmlBehaviourSignal caseExternalChoiceBegin(CmlBehaviourThread leftInstance, CmlBehaviourThread rightInstance ,CmlContext question)
	{
		//Add the children to the process graph
		addChild(leftInstance);
		addChild(rightInstance);
		
		//Now let this process wait for the children to get into a waitForEvent state
		setState(CmlProcessState.WAIT_CHILD);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Handles the External Choice Skip rule
	 * @return
	 */
	private CmlBehaviourSignal caseExternalChoiceSkip()
	{
		//find the finished child
		AbstractBehaviourThread skipChild = (AbstractBehaviourThread)findFinishedChild();
		
		//FIXME: maybe the we should differentiate between actions and process instead of just having CmlProcess
		// 		Childerens. We clearly need it!
		//Extract the current CmlContext of finished child action and use it as the CmlContext
		//for the Skip action.
		pushNext(createSkip(), skipChild.prevState().second);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	private CmlBehaviourSignal caseExternalChoiceEnd()
	{
		AbstractBehaviourThread theChoosenOne = findTheChoosenChild(supervisor().selectedObservableEvent());
		
		//first we execute the child
		CmlBehaviourSignal result = executeChildAsSupervisor(theChoosenOne);
		
		mergeState(theChoosenOne);
		
		setState(CmlProcessState.RUNNING);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return result;
	}
	
	/**
	 * Finds the first finished child if any
	 * @return The first finished child, if none then null is returned
	 */
	private CmlBehaviourThread findFinishedChild()
	{
		for(CmlBehaviourThread child : children())
		{
			if(child.finished())
				return child;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	private AbstractBehaviourThread findTheChoosenChild(ObservableEvent event)
	{
		for(AbstractBehaviourThread child : children)
		{
			if(child.waiting() && child.inspect().containsObservableEvent(event))
				return child;
		}
		
		return null;
	}
	
	private void killAndRemoveAllTheEvidenceOfTheChildren()
	{
		//Abort all the children of this action
		for(CmlBehaviourThread child : children())
		{
			child.setAbort(null);
		}
		
		//Remove them from the supervisor
		removeTheChildren();
	}
	
	/**
	 * External Choice  
	 * End of region
	 * 
	*/
	
	
	
}
