package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
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
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.values.CmlValue;
/**
 * This class inspects the immediate alphabet of the current state of a CmlProcess
 * @author akm
 *
 */
@SuppressWarnings("serial")
public class AlphabetInspector
		extends
		QuestionAnswerCMLAdaptor<CmlContext, eu.compassresearch.core.interpreter.cml.CmlAlphabet> {

	// The process that contains this instance
	private final CmlBehaviourThread 		ownerProcess;
	private final CmlEvaluator				cmlEvaluator;
	
	/**
	 * 
	 * @param ownerProcess
	 */
	public AlphabetInspector(CmlBehaviourThread ownerProcess,CmlEvaluator cmlEvalutor)
	{
		this.ownerProcess = ownerProcess;
		this.cmlEvaluator = cmlEvalutor;
	}
	
	@Override
	public CmlAlphabet defaultPProcess(PProcess node, CmlContext question)
			throws AnalysisException {
		return createSilentTransition(node,null);
	}
	
	@Override
	public CmlAlphabet defaultPAction(PAction node, CmlContext question)
			throws AnalysisException {

		return createSilentTransition(node,null);
	}
	
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
	
	/**
	 * The alphabet of a block action is just the internal action
	 */
	@Override
	public CmlAlphabet caseABlockStatementAction(ABlockStatementAction node,
			CmlContext question) throws AnalysisException {

		//return defaultPAction(node, question);
		return node.getAction().apply(this,question);
	}
			
	@Override
	public CmlAlphabet caseASequentialCompositionAction(
			ASequentialCompositionAction node, CmlContext question)
			throws AnalysisException {
		return createSilentTransition(node,node.getLeft());
	}
	
	@Override
	public CmlAlphabet caseAReferenceAction(AReferenceAction node,
			CmlContext question) throws AnalysisException {
		return createSilentTransition(node,node.getActionDefinition().getAction());
	}
	
	@Override
	public CmlAlphabet caseACommunicationAction(ACommunicationAction node,
			CmlContext question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier());
		
		//find the channel value
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		
		//if there are no com params then we hav a prefix event
		if(CmlActionAssistant.isPrefixEvent(node))
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
			CmlContext question) throws AnalysisException {

		CmlAlphabet alpha = null;
		
		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!ownerProcess.hasChildren())
			alpha = createSilentTransition(node, node,"Begin");
		else if(CmlBehaviourThreadUtility.existsAFinishedChild(ownerProcess))
			alpha = createSilentTransition(node, node,"end");
		//If there are children we just return the union of the child alphabets
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(ownerProcess))
		{
			for(CmlBehaviourThread child : ownerProcess.children())
			{
				if(alpha == null)
					alpha = child.inspect();
				else
					alpha = alpha.union(child.inspect());
			}
		}
		
		return alpha;
	}
	
	/**
	 * State-based Choice - section 7.5.5 D23.2
	 * Guard
	 * Guarded actions are stuck, unless the guard is true.
	 * So here we return the silent event tau, if the expression evaluates 
	 * to true in the current state else the empty alphabet
	 */
	@Override
	public CmlAlphabet caseAGuardedAction(AGuardedAction node, CmlContext question)
			throws AnalysisException {

		//First we evaluate the guard expression
		Value guardExp = node.getExpression().apply(cmlEvaluator,question);
		
		//if the gaurd is true then we return the silent transition to the guarded action
		if(guardExp.boolValue(question.getVdmContext()))
			return createSilentTransition(node, node.getAction());
		//else we return the empty alphabet since no transition is possible
		else
			return new CmlAlphabet();
		
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
	private interface ParallelAction
	{
		public CmlAlphabet inspectChildren() throws AnalysisException;
	}
	
	public CmlAlphabet caseParallelAction(INode node, CmlContext question,ParallelAction parallelAction)
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
	 * This returns the alphabet of a interleaved action. 
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
			AInterleavingParallelAction node, CmlContext question)
			throws AnalysisException {
		
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
	 *  This returns the alphabet of a generalised parallel action. 
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
			AGeneralisedParallelismParallelAction node, CmlContext question)
					throws AnalysisException {

		final AGeneralisedParallelismParallelAction internalNode = node;
		final CmlContext internalQuestion = question;
		
		return caseParallelAction(node,question,new ParallelAction()
		{
			@Override
			public CmlAlphabet inspectChildren() throws AnalysisException{
				
				//convert the channelset of the current node to a alphabet
				//TODO: The convertChansetExpToAlphabet method is only a temp solution. 
				//		This must be evaluated differently
				CmlAlphabet cs =  ((CmlValue)internalNode.getChansetExpression().
						apply(cmlEvaluator,internalQuestion)).cmlAlphabetValue(internalQuestion);
				
				//Get all the child alphabets and add the events that are not in the channelset
				CmlBehaviourThread leftChild = ownerProcess.children().get(0);
				CmlAlphabet leftChildAlphabet = leftChild.inspect();
				CmlBehaviourThread rightChild = ownerProcess.children().get(1);
				CmlAlphabet rightChildAlphabet = rightChild.inspect();
				
				//Find the intersection between the child alphabets and the channel set
				CmlAlphabet syncAlpha = leftChildAlphabet.intersectRefsAndJoin(rightChildAlphabet).intersectRefsAndJoin(cs);
				
				//convert all the common events that are in the channel set into SynchronisationEvent instances
				Set<CmlEvent> syncEvents = new HashSet<CmlEvent>();
				for(ObservableEvent ref : syncAlpha.getReferenceEvents())
				{
					Iterator<ObservableEvent> it = syncAlpha.getObservableEventsByRef(ref).iterator(); 
					syncEvents.add( it.next().synchronizeWith(ownerProcess, it.next()));
				}
				
				/*
				 *	Finally we create the returned alphabet by joining all the synchronised events together with
				 *	all the event af the children that are not in the channel set
				 */
				CmlAlphabet resultAlpha = new CmlAlphabet(syncEvents).union(leftChildAlphabet.substract(cs));
				resultAlpha = resultAlpha.union(rightChildAlphabet.substract(cs));
				
				return resultAlpha;
			}
		});
	}
	
	/**
	 * Process inspection
	 */
	
	
	@Override
	public CmlAlphabet caseAActionProcess(AActionProcess node, CmlContext question)
			throws AnalysisException {
		return createSilentTransition(node,node.getAction());
	}
	
	@Override
	public CmlAlphabet caseAInterleavingProcess(AInterleavingProcess node,
			CmlContext question) throws AnalysisException {
		
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
}
