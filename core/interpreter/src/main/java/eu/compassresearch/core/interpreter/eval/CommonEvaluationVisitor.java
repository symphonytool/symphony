package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.ConcreteBehaviourThread;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.ActionEvaluationVisitor.parallelCompositionHelper;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;

public class CommonEvaluationVisitor extends AbstractEvaluationVisitor{

	/**
	 * protected helper methods
	 */
	protected CmlBehaviourSignal caseASequentialComposition(INode leftNode, INode rightNode, Context question)
			throws AnalysisException 
	{
		//First push right and then left, so that left get executed first
		pushNext(rightNode, question);
		pushNext(leftNode, question);
			
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected <V extends CmlBehaviourThread> CmlBehaviourSignal  caseParallelBeginGeneral(V left, V right, Context question)
	{
		//add the children to the process graph
		addChild(left);
		addChild(right);

		//Now let this process wait for the children to get into a waitForEvent state
		setState(CmlProcessState.WAIT_CHILD);

		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected CmlBehaviourSignal caseGeneralisedParallelismParallel(INode node,parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, Context question) throws AnalysisException
	
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
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(ownerThread()))
		{
			result = caseParallelEnd(question); 
		}
		//At least one child is not finished and waiting for event, this will either invoke the Parallel Non-sync or Sync rule
		else if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrStoppedOrWaitingForEvent(ownerThread()))
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
	
	protected CmlBehaviourSignal caseParallelEnd(Context question)
	{
		removeTheChildren();
		
		//now this process evolves into Skip
		pushNext(new ASkipAction(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected CmlBehaviourSignal caseParallelSyncOrNonsync(PVarsetExpression chansetExp, Context question) throws AnalysisException
	{
		//convert the channelset of the current node to a alphabet
		CmlAlphabet cs =  ((CmlAlphabet)chansetExp.
				apply(cmlValueEvaluator,question));
		
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
	
	
	
	/**
	 *  Common transitions
	 */
	protected CmlBehaviourSignal caseAExternalChoice(
			INode node, INode leftNode, LexNameToken leftName, INode rightNode, LexNameToken rightName, Context question)
			throws AnalysisException {
		
		CmlBehaviourSignal result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			
			//TODO: create a local copy of the question state for each of the actions

			//new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,left.getLocation()));
			if(rightNode instanceof AExternalChoiceAction || rightNode instanceof AExternalChoiceProcess)
				rightNode.apply(this,question);
			else
			{
				CmlBehaviourThread rightInstance = createChild(rightNode, question.deepCopy(),rightName); 
				//new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),right.getLocation()));
				addChild(rightInstance);
				
				
				//result = caseExternalChoiceBegin(leftInstance,rightInstance,question);
				
				//We push the current state, since this process will control the child processes created by it
				pushNext(node, question);
			}
			
			CmlBehaviourThread leftInstance = createChild(leftNode, question.deepCopy(), leftName);
			addChild(leftInstance);
			
			//Now let this process wait for the children to get into a waitForEvent state
			setState(CmlProcessState.WAIT_CHILD);
			result = CmlBehaviourSignal.EXEC_SUCCESS;
			
		}
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if(CmlBehaviourThreadUtility.existsAFinishedChild(ownerThread()))
		{
			result = caseExternalChoiceSkip();
		}
		//if this is true, then we can resolve the choice to the event
		//of one of the children that are waiting for events
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(ownerThread()))
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
	protected CmlBehaviourSignal caseExternalChoiceBegin(CmlBehaviourThread leftInstance, CmlBehaviourThread rightInstance ,Context question)
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
	protected CmlBehaviourSignal caseExternalChoiceSkip()
	{
		//find the finished child
		CmlBehaviourThread skipChild = findFinishedChild();
		
		//FIXME: maybe the we should differentiate between actions and process instead of just having CmlProcess
		// 		Childerens. We clearly need it!
		//Extract the current Context of finished child action and use it as the Context
		//for the Skip action.
		mergeState(skipChild);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	protected CmlBehaviourSignal caseExternalChoiceEnd()
	{
		ConcreteBehaviourThread theChoosenOne = (ConcreteBehaviourThread)findTheChoosenChild(supervisor().selectedObservableEvent());
		
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
	protected CmlBehaviourThread findFinishedChild()
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
	protected CmlBehaviourThread findTheChoosenChild(ObservableEvent event)
	{
		for(CmlBehaviourThread child : children())
		{
			if(child.waiting() && !child.inspect().intersectEqualOrMorePrecise(event.getAsAlphabet()).isEmpty())
				return child;
		}
		
		return null;
	}
	
	protected void killAndRemoveAllTheEvidenceOfTheChildren()
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
