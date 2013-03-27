package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.CommunicationParameter;
import eu.compassresearch.core.interpreter.cml.events.InputParameter;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.OutputParameter;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.cml.events.SignalParameter;
import eu.compassresearch.core.interpreter.util.ActionVisitorHelper;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.ActionValue;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;
/**
 * This class inspects the immediate alphabet of the current state of a CmlProcess
 * @author akm
 *
 */
@SuppressWarnings("serial")
public class AlphabetInspectVisitor
		extends
		QuestionAnswerCMLAdaptor<Context, CmlAlphabet> {

	// The process that contains this instance
	private final CmlBehaviourThread 			ownerProcess;
	private final CmlExpressionVisitor			cmlEvaluator = new CmlExpressionVisitor();
	
	/**
	 * 
	 * @param ownerProcess
	 */
	public AlphabetInspectVisitor(CmlBehaviourThread ownerProcess)
	{
		this.ownerProcess = ownerProcess;
	}
	
	/**
	 * Common Inspection
	 */

	/**
	 * Common Helpers
	 */
	
	private CmlAlphabet createSilentTransition(INode srcNode, INode dstNode, String transitionText)
	{
		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		CmlTauEvent tau = CmlTauEvent.newTauEvent(srcNode,dstNode);
		tau.setTransitionText(transitionText);
		specialEvents.add(tau);
		return new CmlAlphabet(specialEvents);
	}
	
	private CmlAlphabet createSilentTransition(INode srcNode, INode dstNode)
	{
		return createSilentTransition(srcNode,dstNode,null);
	}
	
	/*
	 * (non-Javadoc)
	 * Used for evaluating post conditions
	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#defaultPExp(org.overture.ast.expressions.PExp, java.lang.Object)
	 */
	
	@Override
	public CmlAlphabet defaultPExp(PExp node, Context question)
			throws AnalysisException {
		
		return createSilentTransition(node,null,"Post condition");
	}
	
	/**
	 * Internal choice
	 */
	
	@Override
	public CmlAlphabet caseAInternalChoiceAction(AInternalChoiceAction node,
			Context question) throws AnalysisException {

		return createSilentTransition(node,node.getLeft());
	}
	
	@Override
	public CmlAlphabet caseAInternalChoiceProcess(AInternalChoiceProcess node,
			Context question) throws AnalysisException {

		return createSilentTransition(node,node.getLeft());
	}

	/**
	 * Parallel action
	 * 
	 * In general all the parallel action have three transition rules that can be invoked
	 * Parallel Begin:
	 * 	At this step the interleaving action are not yet created. So this will be a silent (tau) transition
	 * 	where they will be created and started. So the alphabet returned here is {tau}
	 * 
	 * Parallel Sync/Non-sync:
	 * 
	 * Parallel End:
	 *  At this step both child actions are in the FINISHED state and they will be removed from the running process network
	 *  and this will make a silent transition into Skip. So the alphabet returned here is {tau}
	 */
	
	/**
	 * 
	 * Private common helpers for Generalised Parallelism
	 *
	 */
	private interface ParallelAction
	{
		public CmlAlphabet inspectChildren() throws AnalysisException;
	}
	
	private CmlAlphabet caseAGeneralisedParallelismInspectChildren(PVarsetExpression channelsetExp, Context question) throws AnalysisException
	{
		//convert the channelset of the current node to a alphabet
		//TODO: The convertChansetExpToAlphabet method is only a temp solution. 
		//		This must be evaluated differently
		CmlAlphabet cs =  ((CmlAlphabet)channelsetExp.
				apply(cmlEvaluator,question));
		
		//Get all the child alphabets and add the events that are not in the channelset
		CmlBehaviourThread leftChild = ownerProcess.children().get(0);
		CmlAlphabet leftChildAlphabet = leftChild.inspect();
		CmlBehaviourThread rightChild = ownerProcess.children().get(1);
		CmlAlphabet rightChildAlphabet = rightChild.inspect();
		
		//Find the intersection between the child alphabets and the channel set
		CmlAlphabet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));
		
		//convert all the common events that are in the channel set into SynchronisationEvent instances
		Set<CmlEvent> syncEvents = new HashSet<CmlEvent>();
		for(ObservableEvent ref : cs.getObservableEvents())
		{
			CmlAlphabet commonEvents = syncAlpha.intersectImprecise(ref.getAsAlphabet());
			if(commonEvents.getObservableEvents().size() == 2)
			{
				Iterator<ObservableEvent> it = commonEvents.getObservableEvents().iterator(); 
				syncEvents.add( it.next().synchronizeWith(it.next()));
			}
		}
		/*
		 *	Finally we create the returned alphabet by joining all the 
		 *  Synchronized events together with all the event of the children 
		 *  that are not in the channel set.
		 */
		CmlAlphabet resultAlpha = new CmlAlphabet(syncEvents).union(leftChildAlphabet.subtractImprecise(cs));
		resultAlpha = resultAlpha.union(rightChildAlphabet.subtractImprecise(cs));
		
		return resultAlpha;
	}
	
	public CmlAlphabet caseParallelAction(INode node, Context question,ParallelAction parallelAction)
			throws AnalysisException {
		
		CmlAlphabet alpha = null;
		
		//If there are no children or the children has finished, then either the interleaving 
		//is beginning or ending and we make a silent transition.
		if(!ownerProcess.hasChildren())
		{
			alpha = createSilentTransition(node, node, "Begin");
		}
		else if(CmlBehaviourThreadUtility.isAllChildrenFinished(ownerProcess))
		{
			alpha = createSilentTransition(node, new ASkipAction(), "End");
		}
		else
		//if we are here at least one of the children is alive and we must inspect them
		//and forward it.
		{
			alpha = parallelAction.inspectChildren();
		}
		
		return alpha;
	}
	
	/**
	 *  Generalised parallelism 
	 *  
	 *  This has three parts:
	 * 
	 * Parallel Begin: As the general case
	 * 
	 * Parallel sync/Non-sync:
	 * 	At this step, the actions are each executed separately. Here the channel set will determine whether a
	 *  Sync or non-sync transition occurs. The alphabet returned here is {alpha(left) union alpha(right)}
	 * 
	 * Parallel End: As the general case
	 *  
	 */
	@Override
	public CmlAlphabet caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, Context question)
					throws AnalysisException {

		final AGeneralisedParallelismParallelAction internalNode = node;
		final Context internalQuestion = question;
		
		return caseParallelAction(node,question,new ParallelAction()
		{
			@Override
			public CmlAlphabet inspectChildren() throws AnalysisException{
				
				return caseAGeneralisedParallelismInspectChildren(internalNode.getChansetExpression(),internalQuestion);
			}
		});
	}
	
	@Override
	public CmlAlphabet caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, Context question)
			throws AnalysisException {

		final AGeneralisedParallelismProcess internalNode = node;
		final Context internalQuestion = question;
		
		return caseParallelAction(node,question,new ParallelAction()
		{
			@Override
			public CmlAlphabet inspectChildren() throws AnalysisException{
				
				return caseAGeneralisedParallelismInspectChildren(internalNode.getChansetExpression(),internalQuestion);
			}
		});
	}
	
	/**
	 * This returns the alphabet of a interleaving action/process. 
	 * 
	 * This has three parts:
	 * 
	 * Parallel Begin: As the general case
	 * 
	 * Parallel Non-sync:
	 * 	At this step the actions are each executed separately. Since no sync shall takes place this Action just wait
	 * 	for the child actions to be in the FINISHED state. So the alphabet returned here is {alpha(left) union alpha(right)}
	 * 
	 * Parallel End: As the general case
	 * 
	 */
	
	@Override
	public CmlAlphabet caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Context question)
			throws AnalysisException {
		
		return caseAInterleavingParallel(node,question);
	}
	
	@Override
	public CmlAlphabet caseAInterleavingProcess(AInterleavingProcess node,
			Context question) throws AnalysisException {
		
		return caseAInterleavingParallel(node,question);
	}
	
	private CmlAlphabet caseAInterleavingParallel(INode node, Context question) throws AnalysisException 
	{
		return caseParallelAction(node,question,new ParallelAction()
		{
			@Override
			public CmlAlphabet inspectChildren() {
				CmlAlphabet alpha = null;
				for(CmlBehaviourThread child : ownerProcess.children())
				{
					if(alpha == null)
						alpha = child.inspect();
					else
						alpha = alpha.union(child.inspect());
				}
				return alpha;
			}
		});
	}
	
	/**
	 * External Choice section 7.5.4 D23.2
	 * 
	 * In terms of the alphabet, we have the following situations:
	 * 
	 *  External Choice Begin:
	 *  When no children exists, the External Choice Begin transition rule must be executed.
	 *  This is a silent transition and therefore the alphabet contains only tau event
	 *  
	 *  External Choice Silent:
	 *  If any of the actions can take a silent transition they will do it before getting here again. 
	 *  We therefore don't take this situation into account
	 *  
	 *  External Choice Skip:
	 *  If one the children is Skip we make a silent transition of the whole choice into skip.
	 *  We therefore just return the tau event
	 *  
	 *  External Choice End:
	 *  The alphabet contains an observable event for every child that can engaged in one.
	 *  
	 */
	@Override
	public CmlAlphabet caseAExternalChoiceAction(AExternalChoiceAction node,
			Context question) throws AnalysisException {

		return caseAExternalChoice(node,question);
	}
	
	@Override
	public CmlAlphabet caseAExternalChoiceProcess(AExternalChoiceProcess node,
			Context question) throws AnalysisException {

		return caseAExternalChoice(node,question);
	}
	
	private CmlAlphabet caseAExternalChoice(INode node,
			Context question) throws AnalysisException {
		
		CmlAlphabet alpha = null;
		
		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!ownerProcess.hasChildren())
			alpha = createSilentTransition(node, node,"Begin");
		//if all children are waiting for events or are finished then we how to investigate further
		else if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrStoppedOrWaitingForEvent(ownerProcess))
		{
			//if there exist a finished child then the external choice ends with a silent transition
			//where the state of the finished is used
			if(CmlBehaviourThreadUtility.finishedChildExists(ownerProcess))
				alpha = createSilentTransition(node, node,"end");
			else
			{
				//If all the children are waiting for events, we must try to let them
				for(CmlBehaviourThread child : ownerProcess.children())
				{
					if(alpha == null)
						alpha = calculateDeadlockFreeChildAlphabet(child);
					else
						alpha = alpha.union(calculateDeadlockFreeChildAlphabet(child));
				}
			}
		}
		
		return alpha;
	}
	
	/**
	 * This calculate the alphabet of a child process by 
	 * @param child
	 * @return
	 */
	private CmlAlphabet calculateDeadlockFreeChildAlphabet(CmlBehaviourThread child)
	{
		//child.onStateChanged().unregisterObserver(ownerProcess);

		CmlAlphabet alpha = child.inspect();

		
//		if(!ownerProcess.inBactrackMode())
//		{
//			//Set the restore point
//			child.setRestorePoint();
//			
//			for(ObservableEvent ev : alpha.getObservableEvents())
//			{	
//				final Scheduler tmpScheduler = VanillaInterpreterFactory.newScheduler(new FCFSPolicy());
//				CmlSupervisorEnvironment tmpEnv = VanillaInterpreterFactory.newCmlSupervisorEnvironment(
//						new CmlCommunicationSelectionStrategy() {
//
//							@Override
//							public ObservableEvent select(CmlAlphabet availableChannelEvents) {
//
//								tmpScheduler.stop();
//
//								return null;
//							}
//						}, 
//						tmpScheduler);
//
//				try 
//				{
//					//Level lvl = CmlRuntime.logger().getLevel();
//					//CmlRuntime.logger().setLevel(Level.OFF);
//					tmpEnv.setSelectedObservableEvent(ev);
//					tmpScheduler.setCmlSupervisorEnvironment(tmpEnv);
//					child.start(tmpEnv);
//					tmpScheduler.start();
//					//CmlRuntime.logger().setLevel(lvl);
//				} catch (InterpreterRuntimeException e) {
//					alpha = alpha.substract(ev.getAsAlphabet()); 
//				} 
//			}
//			
//			child.revertToRestorePoint();
//		}
		
		
			
		return alpha;
	}

	/**
	 * Sequential composition
	 */
	@Override
	public CmlAlphabet caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {
		return createSilentTransition(node,node.getLeft());
	}
	
	@Override
	public CmlAlphabet caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {
		return createSilentTransition(node,node.getLeft());
	}
	
	
	/**
	 * Reference action/process
	 */
	
	@Override
	public CmlAlphabet caseAReferenceAction(AReferenceAction node,
			Context question) throws AnalysisException {
		
		ActionValue actionValue = (ActionValue)question.check(node.getName()).deref();
		
		return createSilentTransition(node,actionValue.getActionDefinition().getAction());
	}
	
	@Override
	public CmlAlphabet caseAReferenceProcess(AReferenceProcess node,
			Context question) throws AnalysisException {
		
		ProcessObjectValue processValue = (ProcessObjectValue)question.lookup(node.getProcessName());
		
		return createSilentTransition(node,processValue.getProcessDefinition().getProcess());
	}
	
	
	/**
	 * PAction Inspection
	 */

	@Override
	public CmlAlphabet defaultPAction(PAction node, Context question)
			throws AnalysisException {

		return createSilentTransition(node,null);
	}

	
	/**
	 * Block Statement Action
	 */
	@Override
	public CmlAlphabet caseABlockStatementAction(ABlockStatementAction node,
			Context question) throws AnalysisException {

		return createSilentTransition(node,node.getAction());
	}
	
	/**
	 * Stop Action
	 */
	@Override
	public CmlAlphabet caseAStopAction(AStopAction node, Context question)
			throws AnalysisException {
		//return the empty alphabet
		return new CmlAlphabet();
	}
	
	/**
	 * Communication Action 
	 */
	@Override
	public CmlAlphabet caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier().getName(),node.getIdentifier().getLocation(),false,true);
		//find the channel value
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		
		//if there are no com params then we have a prefix event
		if(node.getCommunicationParameters().isEmpty())
		{
			comset.add(new PrefixEvent(ownerProcess, chanValue));
		}
		//otherwise we convert the com params
		else
		{
			List<CommunicationParameter> params = new LinkedList<CommunicationParameter>();
			for(PCommunicationParameter p : node.getCommunicationParameters())
			{
				CommunicationParameter param = null;
				if(p instanceof ASignalCommunicationParameter)
				{
					ASignalCommunicationParameter signal = (ASignalCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlEvaluator,question);
					param = new SignalParameter((ASignalCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter signal = (AWriteCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlEvaluator,question);
					param = new OutputParameter((AWriteCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AReadCommunicationParameter)
				{
					//TODO: At this point the 'in set exp' is not supported
					AReadCommunicationParameter readParam = (AReadCommunicationParameter)p;
					param = new InputParameter(readParam);
				}
				
				params.add(param);
			}
			
			ObservableEvent observableEvent = new CmlCommunicationEvent(ownerProcess, chanValue, params);
			comset.add(observableEvent);
		}
		//TODO: do the rest here
		
		return new CmlAlphabet(comset);
	}
	
	/**
	 * State-based Choice - section 7.5.5 D23.2
	 * Guard
	 * Guarded actions are stuck, unless the guard is true.
	 * So here we return the silent event tau, if the expression evaluates 
	 * to true in the current state else the empty alphabet
	 */
	@Override
	public CmlAlphabet caseAGuardedAction(AGuardedAction node, Context question)
			throws AnalysisException {

		//First we evaluate the guard expression
		Value guardExp = node.getExpression().apply(cmlEvaluator,question);
		
		//if the gaurd is true then we return the silent transition to the guarded action
		if(guardExp.boolValue(question))
			return createSilentTransition(node, node.getAction());
		//else we return the empty alphabet since no transition is possible
		else
			return new CmlAlphabet();
		
	}
	
	/**
	 * Hiding - Defined in section 7.5.8 D23.2
	 * 
	 * The alphabet for hiding determined by the given channel set. If an event
	 * is in it then a hidden transition will be made, if not then it is observable.
	 * 
	 * This is handled by giving each behaviourThread a hiding alphabet that when 
	 * inspected converts all the hidden observable event into silent. So this method 
	 */
	
	@Override
	public CmlAlphabet caseAHidingAction(AHidingAction node, Context question)
			throws AnalysisException {

		//FIXME This is actually not a tau transition. This should produced an entirely 
		//different event which has no denotational trace but only for debugging
		return createSilentTransition(node, node.getLeft(), "Hiding (This should not be a tau)");
	}
	
	/**
	 * Non deterministic if randomly chooses between options whose expression are evaluated to true
	 */
	@Override
	public CmlAlphabet caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, Context question)
			throws AnalysisException {

		int availCount = ActionVisitorHelper.findAllTrueAlternatives(
				node.getAlternatives(),question,cmlEvaluator).size();
		
		if(availCount > 0)
			//FIXME this should point to the choosen action node
			return createSilentTransition(node, null);
		else
			//were stuck so return empty alphabet
			return new CmlAlphabet();
	}
	
	@Override
	public CmlAlphabet caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, Context question)
			throws AnalysisException {

		int availCount = ActionVisitorHelper.findAllTrueAlternatives(
				node.getAlternatives(),question,cmlEvaluator).size();
		
		if(availCount > 0)
			//FIXME this should point to the choosen action node
			return createSilentTransition(node, null);
		else
			return createSilentTransition(node, new ASkipAction());
	}
	
	@Override
	public CmlAlphabet caseAWhileStatementAction(AWhileStatementAction node,
			Context question) throws AnalysisException {
		
		if(node.getCondition().apply(cmlEvaluator,question).boolValue(question))
		{
			//FIXME this should point to the choosen action node
			return createSilentTransition(node, null);
		}
		else
		{
			return createSilentTransition(node, new ASkipAction());
		}
	}
	
	/**
	 * Process inspection
	 */
	
	/**
	 * This creates a silent transition for all the processes that are not defined
	 */
	@Override
	public CmlAlphabet defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		return createSilentTransition(node,null);
	}
	
	@Override
	public CmlAlphabet caseAActionProcess(AActionProcess node, Context question)
			throws AnalysisException {
		return createSilentTransition(node,node.getAction());
	}
	
}
