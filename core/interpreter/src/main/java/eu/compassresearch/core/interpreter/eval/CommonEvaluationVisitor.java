package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.events.AbstractObservableEvent;
import eu.compassresearch.core.interpreter.eval.ActionEvaluationVisitor.parallelCompositionHelper;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.util.Pair;

public class CommonEvaluationVisitor extends AbstractEvaluationVisitor{

	public CommonEvaluationVisitor(AbstractEvaluationVisitor parentVisitor)
	{
		super(parentVisitor);
	}
	
	/**
	 * protected helper methods
	 */
	protected void caseASequentialComposition(INode leftNode, INode rightNode, Context question)
			throws AnalysisException 
	{
		//First push right and then left, so that left get executed first
		if(!ownerThread().hasChildren())
		{
			ownerThread().setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(leftNode,question, new LexNameToken("", "",new LexLocation()),ownerThread()));
			ownerThread().setRightChild(VanillaInterpreterFactory.newCmlBehaviour(rightNode,question, new LexNameToken("", "",new LexLocation()),ownerThread()));
		}
	}
	
	protected <V extends CmlBehaviour> void caseParallelBeginGeneral(V left, V right, Context question)
	{
		//add the children to the process graph
		ownerThread().setLeftChild(left);
		ownerThread().setRightChild(right);
	}
	
	protected Pair<INode,Context> caseGeneralisedParallelismParallel(INode node,parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, Context question) throws AnalysisException
	
	{
		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!ownerThread().hasChildren()){
			helper.caseParallelBegin();
			//We push the current state, since this process will control the child processes created by it
			return new Pair<INode,Context>(node, question);
		}
		else
		{
			caseParallelSyncOrNonsync(chansetExp, question);
			
			//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
			if (CmlBehaviourThreadUtility.isAllChildrenFinished(ownerThread()))
				caseParallelEnd(question);
			else
			//We push the current state,
				return new Pair<INode,Context>(node, question);
		}

	}
	
	protected Pair<INode,Context> caseParallelNonSync()
	{

		CmlBehaviour leftChild = children().get(0);
		CmlAlphabet leftChildAlpha = leftChild.inspect(); 
		CmlBehaviour rightChild = children().get(1);
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			return leftChild.execute(supervisor());
		}
		else if(rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			return rightChild.execute(supervisor());
		}
		else
		{
			return Pair<INode,Context>.FATAL_ERROR;
		}
	}
	
	protected Pair<INode,Context> caseParallelEnd(Context question)
	{
		removeTheChildren();
		
		//now this process evolves into Skip
		pushNext(new ASkipAction(), question);
		
		return Pair<INode,Context>.EXEC_SUCCESS;
	}
	
	protected Pair<INode,Context> caseParallelSyncOrNonsync(PVarsetExpression chansetExp, Context question) throws AnalysisException
	{
		//get the immediate alphabets of the left and right child
		CmlBehaviour leftChild = children().get(0);
		CmlAlphabet leftChildAlpha = leftChild.inspect(); 
		CmlBehaviour rightChild = children().get(1);
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		//if both contains the selected event it must be a sync event
		if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()) &&
				rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			if(leftChild.execute(supervisor()) != Pair<INode,Context>.EXEC_SUCCESS)
				return Pair<INode,Context>.FATAL_ERROR;
			
			return rightChild.execute(supervisor());
		}
		else if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			return leftChild.execute(supervisor());
		}
		else if(rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			return rightChild.execute(supervisor());
		}
		else
			//Something went wrong here
			return Pair<INode,Context>.FATAL_ERROR;
	}
	
	
	
	/**
	 *  Common transitions
	 */
	protected Pair<INode,Context> caseAExternalChoice(
			INode node, INode leftNode, LexNameToken leftName, INode rightNode, LexNameToken rightName, Context question)
			throws AnalysisException {
		
		Pair<INode,Context> result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!ownerThread().hasChildren()){
			
			//TODO: create a local copy of the question state for each of the actions

			//new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,left.getLocation()));
			if(rightNode instanceof AExternalChoiceAction || rightNode instanceof AExternalChoiceProcess)
				rightNode.apply(this,question);
			else
			{
				CmlBehaviour rightInstance = VanillaInterpreterFactory.newCmlBehaviourThread(rightNode, question.deepCopy(), rightName,this.ownerThread()); 
				addChild(rightInstance);
				
				//We push the current state, since this process will control the child processes created by it
				pushNext(node, question);
			}
			
			CmlBehaviour leftInstance = VanillaInterpreterFactory.newCmlBehaviourThread(leftNode, question.deepCopy(), leftName,this.ownerThread());
			addChild(leftInstance);
			
			//Now let this process wait for the children to get into a waitForEvent state
			result = Pair<INode,Context>.EXEC_SUCCESS;
			
		}
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if(CmlBehaviourThreadUtility.finishedChildExists(ownerThread()))
		{
			result = caseExternalChoiceSkip();
		}
		else
		{
			for(CmlBehaviour child : children())
			{
				if(child.inspect().containsImprecise(ownerThread().supervisor().selectedObservableEvent()))
				{
					if(ownerThread().supervisor().selectedObservableEvent() instanceof AbstractObservableEvent)
						result = caseExternalChoiceEnd(child);
					else
					{
						result = child.execute(ownerThread().supervisor());
						pushNext(node, question);
					}
				}
			}
		}
		
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
	protected void caseExternalChoiceBegin(CmlBehaviour leftInstance, CmlBehaviour rightInstance ,Context question)
	{
		//Add the children to the process graph
		ownerThread().setLeftChild(leftInstance);
		ownerThread().setRightChild(rightInstance);
		
	}
	
	/**
	 * Handles the External Choice Skip rule
	 * @return
	 */
	protected void caseExternalChoiceSkip()
	{
		//find the finished child
		CmlBehaviour skipChild = findFinishedChild();
		
		//FIXME: maybe the we should differentiate between actions and process instead of just having CmlProcess
		// 		Childerens. We clearly need it!
		//Extract the current Context of finished child action and use it as the Context
		//for the Skip action.
		mergeState(skipChild);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
	}
	
	/**
	 * Handles the external choice end rule
	 * @return
	 */
	protected Pair<INode,Context> caseExternalChoiceEnd(CmlBehaviour theChoosenOne)
	{
		//CmlBehaviourThread theChoosenOne = findTheChoosenChild((ObservableEvent)supervisor().selectedObservableEvent());
		
		//first we execute the child
		Pair<INode,Context> result = theChoosenOne.execute(ownerThread().supervisor());
		
		mergeState(theChoosenOne);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return result;
	}
	
	/**
	 * Finds the first finished child if any
	 * @return The first finished child, if none then null is returned
	 */
	protected CmlBehaviour findFinishedChild()
	{
		for(CmlBehaviour child : children())
		{
			if(child.finished())
				return child;
		}
		
		return null;
	}
		
	protected void killAndRemoveAllTheEvidenceOfTheChildren()
	{
		//Abort all the children of this action
		for(CmlBehaviour child : children())
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
