package eu.compassresearch.core.interpreter.runtime;

import java.util.Iterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlProcessObserver;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.cml.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.eval.AlphabetInspectionVisitor;
import eu.compassresearch.core.interpreter.util.Pair;

/**
 *  This class represents a running CML Action. It represents a specific node as specified in D23.2 section 7.4.2,
 *  where a node is specified as a tuple (w,s,a) where w is the set of variables, s is the state values and a is the 
 *  current action.
 *  w and s are stored in the current Context object and a is represented by storing the next action AST node to be executed.
 * 
 * 	The possible transitions are handled in the visitor case methods.
 * 
 *  Therefore this Class should be fully consistent with the operational semantics described in D23.2 chapter 7.
 * 
 * 
 * @author akm
 *
 */
public class CmlActionInstance extends AbstractInstance<PAction> implements CmlProcessObserver {

	private LexNameToken name;
	private AlphabetInspectionVisitor alphabetInspectionVisitor = new AlphabetInspectionVisitor(this); 
	//private Process
	
	public CmlActionInstance(PAction action,Context context, LexNameToken name)
	{
		super(null);
		this.name = name;
		pushNext(action, context);
		//executionStack.push(new Pair<PAction, Context>(action, context));
	}
	
	public CmlActionInstance(PAction action,Context context, LexNameToken name, CmlProcess parent)
	{
		super(parent);
		this.name = name;
		pushNext(action, context);
		//executionStack.push(new Pair<PAction, Context>(action, context));
	}
	
	@Override
	public void start(CmlSupervisorEnvironment env) {
		this.env= env; 
		
		if(parent() != null)
			supervisor().addPupil(this);
		
		setState(CmlProcessState.RUNNABLE);
	}

	@Override
	public CmlAlphabet inspect() throws AnalysisException 
	{
		Pair<PAction,Context> next = nextState();
		return next.first.apply(alphabetInspectionVisitor,next.second);
	}

	@Override
	public LexNameToken name() {
		return this.name;
	}
	
	@Override
	public String toString() {
	
		if(hasNext())
			return nextState().first.toString();
		else
			return "Finished";
		
	}
	
	/**
	 * Process state methods 
	 */
	@Override
	public CmlProcessState getState() {
		return state;
	}

	@Override
	protected void setState(CmlProcessState state) {
		
		if(getState() != state)
		{
			CmlProcessStateEvent ev = new CmlProcessStateEvent(this, this.state, state);
			this.state = state;
			notifyOnStateChange(ev);
		}
	}
	
	/**
	 * CmlProcessObserver interface methods
	 */
	
	@Override
	public void onStateChange(CmlProcessStateEvent stateEvent) {

		switch(stateEvent.getTo())
		{
//		case WAIT_EVENT:
//			//if all the children are waiting for events this process can continue
//			if(isAllChildrenWaitingForEvent())
//				setState(CmlProcessState.RUNNABLE);
//			
//			break;
		case FINISHED:
			stateEvent.getSource().unregisterOnStateChanged(this);
			
			//if all the children are finished this process can continue and evolve into skip
			if(isAllChildrenFinished())
				setState(CmlProcessState.RUNNABLE);
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * Private helper methods
	 */
	private boolean isAllChildrenFinished()
	{
		boolean isAllFinished = true;
		for(CmlProcess child : children())
		{
			isAllFinished &= child.finished();
		}
		return isAllFinished;
	}
	
	private boolean isAllChildrenWaitingForEvent()
	{
		boolean isAllWaitingForEvent = true;
		for(CmlProcess child : children())
		{
			isAllWaitingForEvent &= child.waiting();
		}
		return isAllWaitingForEvent;
	}
	
	
		
	/**
	 * Transition cases
	 */

	/**
	 * This transition can either be
	 * Simple prefix   	: a -> A
	 * Synchronisation 	: a.1 -> A
	 * Output			: a!2 -> A
	 * Input			: a?x -> A
	 * As defined in 7.5.2 in D23.2
	 */
	@Override
	public CmlBehaviourSignal caseACommunicationAction(
			ACommunicationAction node, Context question)
			throws AnalysisException {
		
		CmlBehaviourSignal result = null;
		
		//At this point the supervisor has already given go to the event, 
		//so we can execute it immediately. We just have figure out which kind of event it is
		if(isSimplePrefix(node))
			result = caseSimplePrefix(node, question);
	
		//supervisor().clearSelectedCommunication();
		
		return result;
	}
	
	/**
	 * Helper methods for Synchronisation and Communication transition rules
	 */
	
	/**
	 * Determines if the communication AST node is a simple prefix
	 * @param node
	 * @return
	 */
	private boolean isSimplePrefix(ACommunicationAction node)
	{
		return node.getCommunicationParameters().isEmpty();
	}
	
	private CmlBehaviourSignal caseSimplePrefix(ACommunicationAction node, Context question) 
			throws AnalysisException 
	{
		pushNext(node.getAction(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 */
	@Override
	public CmlBehaviourSignal caseAReferenceAction(AReferenceAction node,
			Context question) throws AnalysisException {
		//FIXME: the scoping is not correct, this should be done as described in the transition rule
		
		//FIXME: Consider: Instead of this might create a child process, and behave as this child until it terminates
		//CMLActionInstance refchild = new CMLActionInstance(node.getActionDefinition().getAction(), question, node.getName()); 
		
		pushNext(node.getActionDefinition().getAction(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
	 */
	@Override
	public CmlBehaviourSignal caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {

		//First push right and then left, so that left get executed first
		pushNext(node.getRight(), question);
		pushNext(node.getLeft(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}

	/**
	 * Parallel composition D23.2 7.5.7
	 *  
	 */
	
	/**
	 * Interleaving
	 * A ||| B
	 */
	@Override
	public CmlBehaviourSignal caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Context question)
			throws AnalysisException {

		CmlBehaviourSignal result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			result = caseParallelBegin(node.getLeftAction(),node.getRightAction(),question);
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);
			
		}
		//the process has children and must now handle either termination or event sync
		else if (isAllChildrenFinished())
		{
			for(Iterator<CmlProcess> iterator = children().iterator(); iterator.hasNext(); )
			{
				CmlProcess child = iterator.next();
				supervisor().removePupil(child);
				iterator.remove();
			}
			
			pushNext(new ASkipAction(), question);
			result = CmlBehaviourSignal.EXEC_SUCCESS;
		}
		//else if ()
		
		return result;
	}
	
	/**
	 * Parallel composition Helper methods
	 */
	
	/**
	 * This method introduces a local state for each parallel action which is the source state component
	 * restricted by the nameset expressions
	 * @param question
	 * @return
	 */
	private CmlBehaviourSignal caseParallelBegin(PAction left, PAction right, Context question)
	{
		//TODO: create a local copy of the question state for each of the actions
		CmlActionInstance leftInstance = 
				new CmlActionInstance(left, question, 
						new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),this);
		
		CmlActionInstance rightInstance = 
				new CmlActionInstance(right, question, 
						new LexNameToken(name.module,"|||" + name.getIdentifier().getName(),right.getLocation()),this);
		
		children().add(leftInstance);
		leftInstance.registerOnStateChanged(this);
		children().add(rightInstance);
		rightInstance.registerOnStateChanged(this);
		
		rightInstance.start(supervisor());
		leftInstance.start(supervisor());
		
		//Now let this process wait for the children to get into a waitForEvent state
		setState(CmlProcessState.WAIT_CHILD);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CmlBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {

		//if hasNext() is true then Skip is in sequential composition with next
		if(!hasNext())
			setState(CmlProcessState.FINISHED);
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
}
