package eu.compassresearch.core.interpreter.eval;

import java.util.HashSet;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.events.CmlCommunicationEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
import eu.compassresearch.core.interpreter.cml.events.CmlTauEvent;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.cml.events.PrefixEvent;
import eu.compassresearch.core.interpreter.cml.events.SynchronisationEvent;
import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
import eu.compassresearch.core.interpreter.util.CmlProcessUtil;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;
/**
 * This class inspects the immediate alphabet of the current state of a CmlProcess
 * @author akm
 *
 */
public class AlphabetInspectionVisitor
		extends
		QuestionAnswerCMLAdaptor<Context, eu.compassresearch.core.interpreter.cml.CmlAlphabet> {

	private final CmlProcess ownerProcess;
	
	public AlphabetInspectionVisitor(CmlProcess ownerProcess)
	{
		this.ownerProcess = ownerProcess;
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
	
	@Override
	public CmlAlphabet caseACommunicationAction(ACommunicationAction node,
			Context question) throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("Default",node.getIdentifier());
		
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
		
//		node.getCommunicationParameters()
		ObservableEvent observableEvent = null;
		
		if(CmlActionAssistant.isPrefixEvent(node))
			observableEvent = new PrefixEvent(ownerProcess, chanValue);
		//TODO: do the rest here
			
		Set<CmlEvent> comset = new HashSet<CmlEvent>();
		comset.add(observableEvent);
		return new CmlAlphabet(comset);
	}
	
	
	@Override
	public CmlAlphabet caseAExternalChoiceAction(AExternalChoiceAction node,
			Context question) throws AnalysisException {

		CmlAlphabet alpha = null;
		
		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!ownerProcess.hasChildren() ||
				CmlProcessUtil.existsAFinishedChild(ownerProcess))
		{
			alpha = defaultPAction(node,question);
		}
		else if(CmlProcessUtil.isAtLeastOneChildWaitingForEvent(ownerProcess))
		{
			for(CmlProcess child : ownerProcess.children())
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
		if(!ownerProcess.hasChildren() || CmlProcessUtil.isAllChildrenFinished(ownerProcess))
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
				for(CmlProcess child : ownerProcess.children())
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
				CmlAlphabet cs = CmlProcessUtil.convertChansetExpToAlphabet(null,
						internalNode.getChanSetExpression(),internalQuestion);
				
				//Get all the child alphabets and add the events that are not in the channelset
				CmlProcess leftChild = ownerProcess.children().get(0);
				CmlAlphabet leftChildAlphabet = leftChild.inspect();
				CmlProcess rightChild = ownerProcess.children().get(1);
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
