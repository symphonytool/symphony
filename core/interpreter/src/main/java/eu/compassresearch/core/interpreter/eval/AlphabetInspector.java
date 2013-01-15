package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.CommunicationParameter;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.OutputParameter;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.cml.events.SignalParameter;
import eu.compassresearch.core.interpreter.cml.events.SynchronisationEvent;
import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
/**
 * This class inspects the immediate alphabet of the current state of a CmlProcess
 * @author akm
 *
 */
@SuppressWarnings("serial")
public class AlphabetInspector
		extends
		QuestionAnswerCMLAdaptor<Context, eu.compassresearch.core.interpreter.cml.CmlAlphabet> {

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
	public CmlAlphabet defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CmlTauEvent.newTauEvent(node));
		return new CmlAlphabet(specialEvents);
	}
	
	@Override
	public CmlAlphabet defaultPAction(PAction node, Context question)
			throws AnalysisException {

		HashSet<CmlEvent> specialEvents = new HashSet<CmlEvent>();
		specialEvents.add(CmlTauEvent.newTauEvent(node));
		return new CmlAlphabet(specialEvents);
	}
	
	/**
	 * The alphabet of a block action is just the internal action
	 */
	@Override
	public CmlAlphabet caseABlockStatementAction(ABlockStatementAction node,
			Context question) throws AnalysisException {

		//return defaultPAction(node, question);
		return node.getAction().apply(this,question);
	}
	
	@Override
	public CmlAlphabet caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier());
		
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		
		if(CmlActionAssistant.isPrefixEvent(node))
		{
			ObservableEvent observableEvent = new PrefixEvent(ownerProcess, chanValue);
			comset.add(observableEvent);
		}
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
					//FIXME: This should be done using the type of the channel
					//long val = valueExp.intValue(question);
				}
				else if(p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter signal = (AWriteCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlEvaluator,question);
					param = new OutputParameter((AWriteCommunicationParameter)p, valueExp);
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
			Context question) throws AnalysisException {

		CmlAlphabet alpha = null;
		
		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!ownerProcess.hasChildren() ||
				CmlBehaviourThreadUtility.existsAFinishedChild(ownerProcess))
		{
			alpha = defaultPAction(node,question);
		}
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
	public CmlAlphabet caseAGuardedAction(AGuardedAction node, Context question)
			throws AnalysisException {

		Value guardExp = node.getExpression().apply(cmlEvaluator,question);
		
		if(guardExp.boolValue(question))
			return defaultPAction(node,question);
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
		public CmlAlphabet inspectChildren();
	}
	
	public CmlAlphabet caseParallelAction(PAction node, Context question,ParallelAction parallelAction)
			throws AnalysisException {
		
		CmlAlphabet alpha = null;
		
		//If there are no children or the children has finished, then either the interleaving 
		//is beginning or ending and we make a silent transition.
		if(!ownerProcess.hasChildren() || CmlBehaviourThreadUtility.isAllChildrenFinished(ownerProcess))
		{
			alpha = defaultPAction(node,question);
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
			AInterleavingParallelAction node, Context question)
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
			AGeneralisedParallelismParallelAction node, Context question)
					throws AnalysisException {

		final AGeneralisedParallelismParallelAction internalNode = node;
		final Context internalQuestion = question;
		
		return caseParallelAction(node,question,new ParallelAction()
		{
			@Override
			public CmlAlphabet inspectChildren() {
				
				//convert the channelset of the current node to a alphabet
				CmlAlphabet cs = CmlBehaviourThreadUtility.convertChansetExpToAlphabet(null,
						internalNode.getChansetExpression(),internalQuestion);
				
				//Get all the child alphabets and add the events that are not in the channelset
				CmlBehaviourThread leftChild = ownerProcess.children().get(0);
				CmlAlphabet leftChildAlphabet = leftChild.inspect();
				CmlBehaviourThread rightChild = ownerProcess.children().get(1);
				CmlAlphabet rightChildAlphabet = rightChild.inspect();
				
				CmlAlphabet resultAlpha = leftChildAlphabet.intersectRefsAndJoin(rightChildAlphabet).intersectRefsAndJoin(cs);
				
				//convert all the common events into sync events
				Set<CmlEvent> syncEvents = new HashSet<CmlEvent>();
				for(ObservableEvent ref : resultAlpha.getReferenceEvents())
				{
					syncEvents.add(new SynchronisationEvent(ownerProcess, resultAlpha.getObservableEventsByRef(ref)) );
				}
				
				resultAlpha = new CmlAlphabet(syncEvents).union(leftChildAlphabet.substract(cs));
				resultAlpha = resultAlpha.union(rightChildAlphabet.substract(cs));
				
				return resultAlpha;
			}
		});
	}
	
	
	
}
