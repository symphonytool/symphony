package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.interpreter.CmlBehaviourUtility;
import eu.compassresearch.core.interpreter.Pair;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.ActionEvaluationVisitor.parallelCompositionHelper;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.core.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.core.CmlBehaviour;
import eu.compassresearch.core.interpreter.cml.transitions.ChannelEvent;

class CommonEvaluationVisitor extends AbstractEvaluationVisitor{

	public CommonEvaluationVisitor(AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(parentVisitor,owner,visitorAccess);
	}
	
	/**
	 * protected helper methods
	 */
	protected Pair<INode,Context> caseASequentialComposition(INode node, INode leftNode, INode rightNode, Context question)
			throws AnalysisException 
	{
		//execution
		if(!owner.getLeftChild().finished())
		{
			owner.getLeftChild().execute(supervisor());
			return new Pair<INode, Context>(node, question);
		}
		else 
		{
			setLeftChild(null);
			setRightChild(null);	
			
			return new Pair<INode, Context>(rightNode,question);
		}
	}
	
	protected <V extends CmlBehaviour> void caseParallelBeginGeneral(V left, V right, Context question)
	{
		//add the children to the process graph
		setLeftChild(left);
		setRightChild(right);
	}
	
	protected Pair<INode,Context> caseGeneralisedParallelismParallel(INode node,parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, Context question) throws AnalysisException
	
	{
		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!owner.hasChildren()){
			helper.caseParallelBegin();
			//We push the current state, since this process will control the child processes created by it
			return new Pair<INode,Context>(node, question);
		}
		else
		{
			caseParallelSyncOrNonsync(chansetExp, question);
			
			//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
			if (CmlBehaviourUtility.isAllChildrenFinished(owner))
				return caseParallelEnd(question);
			else
				//We push the current state,
				return new Pair<INode,Context>(node, question);
		}

	}
	
	protected void caseParallelNonSync() throws AnalysisException
	{

		CmlBehaviour leftChild =  owner.getLeftChild();
		CmlAlphabet leftChildAlpha = owner.getLeftChild().inspect(); 
		CmlBehaviour rightChild = owner.getRightChild();
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			leftChild.execute(supervisor());
		}
		else if(rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			rightChild.execute(supervisor());
		}
		else
		{
			throw new InterpreterRuntimeException("");
		}
	}
	
	protected Pair<INode,Context> caseParallelEnd(Context question)
	{
		removeTheChildren();
		
		setLeftChild(null);
		setRightChild(null);
		
		//now this process evolves into Skip
		return new Pair<INode,Context>(new ASkipAction(), question);
		
	}
	
	protected void caseParallelSyncOrNonsync(PVarsetExpression chansetExp, Context question) throws AnalysisException
	{
		//get the immediate alphabets of the left and right child
		CmlBehaviour leftChild = owner.getLeftChild();
		CmlAlphabet leftChildAlpha = leftChild.inspect(); 
		CmlBehaviour rightChild = owner.getRightChild();
		CmlAlphabet rightChildAlpha = rightChild.inspect();

		//if both contains the selected event it must be a sync event
		if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()) &&
				rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			leftChild.execute(supervisor());
			rightChild.execute(supervisor());
		}
		else if(leftChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			leftChild.execute(supervisor());
		}
		else if(rightChildAlpha.containsImprecise(supervisor().selectedObservableEvent()))
		{
			rightChild.execute(supervisor());
		}
		else
			//Something went wrong here
			throw new InterpreterRuntimeException("");
	}
	
	
	
	/**
	 *  Common transitions
	 */
	protected Pair<INode,Context> caseAExternalChoice(
			INode node, INode leftNode, ILexNameToken leftName, INode rightNode, ILexNameToken rightName, Context question)
			throws AnalysisException {
		
		Pair<INode,Context> result = null;
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!owner.hasChildren()){
			
			CmlBehaviour leftInstance = VanillaInterpreterFactory.newCmlBehaviour(leftNode, question.deepCopy(), leftName,this.owner);
			setLeftChild(leftInstance);
			
			CmlBehaviour rightInstance = VanillaInterpreterFactory.newCmlBehaviour(rightNode, question.deepCopy(), rightName,this.owner); 
			setRightChild(rightInstance);

			//Now let this process wait for the children to get into a waitForEvent state
			result = new Pair<INode, Context>(node, question);
			
		}
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if(CmlBehaviourUtility.finishedChildExists(owner))
		{
			result = caseExternalChoiceEnd(findFinishedChild(),question);
		}
		else
		{
			for(CmlBehaviour child : children())
			{
				if(child.inspect().containsImprecise(supervisor().selectedObservableEvent()))
				{
					if(supervisor().selectedObservableEvent() instanceof ChannelEvent)
					{
						//first we execute the child
						child.execute(supervisor());
						result = caseExternalChoiceEnd(child,question);
					}
					else
					{
						child.execute(supervisor());
						result = new Pair<INode, Context>(node, question);
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
		setLeftChild(leftInstance);
		setRightChild(rightInstance);
		
	}
	
	/**
	 * Handles the external choice end rule
	 * @return
	 */
	protected Pair<INode,Context> caseExternalChoiceEnd(CmlBehaviour theChoosenOne, Context context) throws ValueException
	{
		//FIXME the children contexts also needs to be replaced!!!!!!
		Context copyContext = theChoosenOne.getExecutionState().second;
		Context newCurrentContext = CmlBehaviourUtility.mergeState(context,copyContext);
		
		if(theChoosenOne.getLeftChild() != null)
			theChoosenOne.getLeftChild().replaceState(newCurrentContext);

		setLeftChild(theChoosenOne.getLeftChild());
		
		if(theChoosenOne.getRightChild() != null)
			theChoosenOne.getRightChild().replaceState(newCurrentContext);
		
		setRightChild(theChoosenOne.getRightChild());
		
		return new Pair<INode, Context>(theChoosenOne.getExecutionState().first,newCurrentContext);
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
	
	/**
	 * External Choice  
	 * End of region
	 * 
	*/
}
