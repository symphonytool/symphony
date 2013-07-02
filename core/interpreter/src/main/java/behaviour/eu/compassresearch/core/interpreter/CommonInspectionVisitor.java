package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.ActionEvaluationVisitor.parallelCompositionHelper;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.utility.Pair;

public class CommonInspectionVisitor extends AbstractInspectionVisitor {

	public CommonInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor) {
		super(ownerProcess, visitorAccess, parentVisitor);
	}
	
	protected CmlAlphabet syncOnTockAndJoinChildren()
	{
		//even though they are external choice/interleaving they should always sync on tock
		CmlAlphabet cs =  new CmlAlphabet(new CmlTock());
		
		//Get all the child alphabets 
		CmlAlphabet leftChildAlphabet = owner.getLeftChild().inspect();
		CmlAlphabet rightChildAlphabet = owner.getRightChild().inspect();
		
		//Find the intersection between the child alphabets and the channel set and join them.
		//Then if both left and right have them the next step will combine them.
		CmlAlphabet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));
		
		//combine all the tock events 
		if(syncAlpha.getObservableEvents().size() == 2)
		{
			Iterator<ObservableEvent> it = syncAlpha.getObservableEvents().iterator(); 
			return leftChildAlphabet.union(rightChildAlphabet).subtract(syncAlpha).union(it.next().synchronizeWith(it.next()));
		}
		else
		{
			return leftChildAlphabet.union(rightChildAlphabet);
		}
	}
	
	protected Inspection caseAExternalChoice(final INode node, final INode leftNode, final INode rightNode,
			final Context question) throws AnalysisException {
		
		//if no children are present we make a silent transition to represent the
		//external choice begin
		if(!owner.hasChildren())
		{
			return newInspection(createSilentTransition(node, node,"Begin"),
					new AbstractCommonCalculationStep(owner,visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							CmlBehaviour leftInstance = new ConcreteCmlBehaviour(leftNode, question.deepCopy(), new LexNameToken(name().getModule(),name().getIdentifier().getName() + "[]" , new LexLocation()),this.owner);
							setLeftChild(leftInstance);
							
							CmlBehaviour rightInstance = new ConcreteCmlBehaviour(rightNode, question.deepCopy(), new LexNameToken(name().getModule(),"[]" + name().getIdentifier().getName(), new LexLocation()),this.owner); 
							setRightChild(rightInstance);

							//Now let this process wait for the children to get into a waitForEvent state
							return new Pair<INode, Context>(node, question);
						}
					});
		}
		//if one child is finished external choice must end
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if (CmlBehaviourUtility.finishedChildExists(owner))
			return newInspection(createSilentTransition(node, node,"end"), 
					new AbstractCommonCalculationStep(owner,visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					return caseExternalChoiceEnd(findFinishedChild(),question);
				}
			});
		//else we join the childrens alphabets 
		else
		{
			return newInspection(syncOnTockAndJoinChildren(),
					new AbstractCommonCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					
					for(CmlBehaviour child : children())
					{
						if(child.inspect().containsImprecise(supervisor().selectedObservableEvent()))
						{
							if(supervisor().selectedObservableEvent() instanceof ChannelEvent)
							{
								//first we execute the child
								child.execute(supervisor());
								return caseExternalChoiceEnd(child,question);
							}
							else
							{
								child.execute(supervisor());
								return new Pair<INode, Context>(node, question);
							}
						}
					}
					
					throw new AnalysisException("should not happen");
				}
			});
		}
	}
	/**
	 * 
	 * Private common helpers for Generalised Parallelism
	 *
	 */
//	private interface ParallelAction
//	{
//		public CmlAlphabet inspectChildren() throws AnalysisException;
//	}
	
//	protected Inspection caseParallelAction(final INode node, Context question,ParallelAction parallelAction)
//			throws AnalysisException {
//		
//		
//		//If there are no children or the children has finished, then either the interleaving 
//		//is beginning or ending and we make a silent transition.
//		if(!owner.hasChildren())
//		{
//			return newInspection(createSilentTransition(node, node, "Begin"),
//					new AbstractCommonCalculationStep(owner, visitorAccess) {
//						
//						@Override
//						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//								throws AnalysisException {
//							
//							caseParallelBegin(node,node.getLeft(),node.getRight(),question);
//							//We push the current state, since this process will control the child processes created by it
//							return new Pair<INode,Context>(node, question);
//						}
//					});
//		}
//		else if(CmlBehaviourUtility.isAllChildrenFinished(owner))
//		{
//			return newInspection(createSilentTransition(node, new ASkipAction(), "End"), 
//					); 
//		}
//		else
//		//if we are here at least one of the children is alive and we must inspect them
//		//and forward it.
//		{
//			return parallelAction.inspectChildren();
//		}
//	}
	
	protected void caseParallelBegin(PProcess node, PProcess left, PProcess right, Context question) throws AnalysisException
	{
		if(left == null || right == null)
			throw new InterpreterRuntimeException(
					InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		
		ILexNameToken name = owner.name();
		
		//TODO: create a local copy of the question state for each of the actions
		CmlBehaviour leftInstance = 
				new ConcreteCmlBehaviour(left,question,new LexNameToken(name.getModule(),name.getIdentifier().getName() + "|||" ,left.getLocation()),owner);		
		CmlBehaviour rightInstance = 
				new ConcreteCmlBehaviour(right,question,new LexNameToken(name.getModule(),"|||" + name.getIdentifier().getName() ,right.getLocation()),owner);
		
		//add the children to the process graph
		visitorAccess.setLeftChild(leftInstance);
		visitorAccess.setRightChild(rightInstance);
	}
	
	protected Inspection caseGeneralisedParallelismParallel(final INode node,final parallelCompositionHelper helper, 
			PVarsetExpression chansetExp, final Context question) throws AnalysisException
	
	{
		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!owner.hasChildren()){
			return newInspection(createSilentTransition(node, node, "Begin"),
					
					new AbstractCalculationStep(owner, visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							helper.caseParallelBegin();
							//We push the current state, since this process will control the child processes created by it
							return new Pair<INode,Context>(node, question);
						}
					});
		}
		//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			return newInspection(createSilentTransition(node, new ASkipAction(), "End"), 
					caseParallelEnd(question));
		}
		else
		{
			return caseParallelSyncOrNonsync(node,chansetExp, question);
		}

	}
	
	protected CmlCalculationStep caseParallelEnd(final Context question)
	{
		return new AbstractCommonCalculationStep(owner, visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				removeTheChildren();
				
				setLeftChild(null);
				setRightChild(null);
				
				//now this process evolves into Skip
				return new Pair<INode,Context>(new ASkipAction(), question);
			}
		};
	}
	
	
	protected Inspection caseParallelSyncOrNonsync(final INode node, PVarsetExpression chansetExp, final Context question) throws AnalysisException
	{
		//convert the channel set of the current node to a alphabet
		CmlAlphabet cs =  ((CmlAlphabet)chansetExp.apply(cmlExpressionVisitor,question)).union(new CmlTock());
		
		//Get all the child alphabets and add the events that are not in the channelset
		final CmlBehaviour leftChild = owner.getLeftChild();
		final CmlAlphabet leftChildAlphabet = leftChild.inspect();
		final CmlBehaviour rightChild = owner.getRightChild();
		final CmlAlphabet rightChildAlphabet = rightChild.inspect();
		
		//Find the intersection between the child alphabets and the channel set and join them.
		//Then if both left and right have them the next step will combine them.
		CmlAlphabet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));
		
		//combine all the common events that are in the channel set 
		Set<CmlTransition> syncEvents = new HashSet<CmlTransition>();
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
		
		return newInspection(resultAlpha, 
				new AbstractCommonCalculationStep(owner, visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				//if both contains the selected event it must be a sync event
				if(leftChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()) &&
						rightChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					leftChild.execute(sve);
					rightChild.execute(sve);
				}
				else if(leftChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					leftChild.execute(sve);
				}
				else if(rightChildAlphabet.containsImprecise(owner.supervisor().selectedObservableEvent()))
				{
					rightChild.execute(sve);
				}
				else
					//Something went wrong here
					throw new InterpreterRuntimeException("");
				
				//We push the current state,
				return new Pair<INode,Context>(node, question);
			}
		});
		
	}
	
	
	protected Inspection caseASequentialComposition(final INode node, INode leftNode, final INode rightNode, final Context question)
			throws AnalysisException 
	{
		//execution
		if(!owner.getLeftChild().finished())
		{
			return newInspection(owner.getLeftChild().inspect(), 
					new AbstractCalculationStep(owner,visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							owner.getLeftChild().execute(supervisor());
							return new Pair<INode, Context>(node, question);
						}
					});
			
			
		}
		else 
		{
			return newInspection(createSilentTransition(node,leftNode),
			new AbstractCalculationStep(owner, visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					
					setLeftChild(null);
					setRightChild(null);	
					
					return new Pair<INode, Context>(rightNode,question);
				}
			});
			
		}
	}

}
