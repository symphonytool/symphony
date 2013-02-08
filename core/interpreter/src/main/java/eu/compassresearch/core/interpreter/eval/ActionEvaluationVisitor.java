package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.ConcreteBehaviourThread;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.ActionValue;

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
 * @author akm
 *
 */
public class ActionEvaluationVisitor extends CommonEvaluationVisitor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 993071972119803788L;
	
	private AbstractEvaluationVisitor statementEvalVisitor = new CmlStatementEvaluationVisitor();
	
	@Override
	public void init(ControlAccess controlAccess) {
		super.init(controlAccess);
		statementEvalVisitor.init(controlAccess);
	}
		
	/**
	 * Transition methods
	 */
	
	@Override
	public CmlBehaviourSignal defaultSStatementAction(SStatementAction node,
			Context question) throws AnalysisException {

		return node.apply(statementEvalVisitor,question);
	}
	
	@Override
	public CmlBehaviourSignal defaultPAction(PAction node, Context question)
			throws AnalysisException {

		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	/**
	 * Synchronisation and Communication D23.2 7.5.2
	 * 
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
		//At this point the supervisor has already given go to the event, or the event is hidden
		
		Value value = supervisor().selectedObservableEvent().getValue();
		
		if(node.getCommunicationParameters() != null && 
				node.getCommunicationParameters().size() > 1
				)
			throw new InterpreterRuntimeException("At the moment records and tuples are not supported");
		
		//FIXME this should be more general. It only support one com param at the moment
		for(PCommunicationParameter param : node.getCommunicationParameters())
		{
			if(param instanceof AReadCommunicationParameter)
			{
				PPattern pattern = ((AReadCommunicationParameter) param).getPattern();
				
				if(pattern instanceof AIdentifierPattern)
				{
					LexNameToken name = ((AIdentifierPattern) pattern).getName();
					
					question.putNew(new NameValuePair(name, value));
				}
				
			}
		}
		
		//TODO: input is still missing
		pushNext(node.getAction(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
		
		 
//		//so we can execute it immediately. We just have figure out which kind of event it is
//		if(CmlActionAssistant.isPrefixEvent(node))
//			result = casePrefixEvent(node, question);
//		//supervisor().clearSelectedCommunication();
	}
	
	/**
	 * Helper methods for Synchronisation and Communication transition rules
	 */
	
	
	private CmlBehaviourSignal casePrefixEvent(ACommunicationAction node, Context question) 
			throws AnalysisException 
	{
		pushNext(node.getAction(), question); 
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	
	/**
	 * External Choice D23.2 7.5.4
	 * 
	 *  There four transition rules for external choice:
	 *  
	 *  * External Choice Begin
	 *  
	 *  * External Choice Silent
	 *  
	 *  * External Choice SKIP
	 *  
	 *  * External Choice End
	 *  
	 */
	@Override
	public CmlBehaviourSignal caseAExternalChoiceAction(
			AExternalChoiceAction node, Context question)
			throws AnalysisException {
		
		return caseAExternalChoice(node,node.getLeft(),new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,node.getLeft().getLocation()),
				node.getRight(),new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),node.getRight().getLocation()),question);
				
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

		return caseASequentialComposition(node.getLeft(),node.getRight(),question);
	}

	/**
	 * Parallel composition D23.2 7.5.7
	 *  
	 */
	
	/**
	 * Generalised Parallelism
	 * A [| cs |] B (no state) 
	 * 
	 * or
	 * 
	 * A [| ns1 | cs | ns2 |] B 
	 */
	@Override
	public CmlBehaviourSignal caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, Context question)
			throws AnalysisException {

		final AGeneralisedParallelismParallelAction finalNode = node;
		final Context finalQuestion = question;
		
		return caseGeneralisedParallelismParallel(node,new parallelCompositionHelper() {
			
			@Override
			public CmlBehaviourSignal caseParallelBegin() {
				return ActionEvaluationVisitor.this.caseParallelBegin(finalNode, finalQuestion);
			}
		}, node.getChansetExpression(),question);
	}
	
	interface parallelCompositionHelper
	{
		CmlBehaviourSignal caseParallelBegin();
		
	}
		
	
	/**
	 * Interleaving
	 * A ||| B (no state)
	 * 
	 * or 
	 * 
	 * A [|| ns1 | ns2 ||] B
	 * 
	 * This has three parts:
	 * 
	 * Parallel Begin:
	 * 	At this step the interleaving action are not yet created. So this will be a silent (tau) transition
	 * 	where the left and right actions will be created and started.
	 * 
	 * Parallel Non-sync:
	 * 	At this step the actions are each executed separately. Since no sync shall stake place this Action just wait
	 * 	for the child actions to be in the FINISHED state. 
	 * 
	 * Parallel End:
	 *  At this step both child actions are in the FINISHED state and they will be removed from the running process network
	 *  and this will make a silent transition into Skip. 
	 */
	@Override
	public CmlBehaviourSignal caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Context question)
			throws AnalysisException {

		//TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"
		CmlBehaviourSignal result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			result = caseParallelBegin(node,question);
			//We push the current state, since this process will control the child processes created by it
			pushNext(node, question);

		}
		//At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync 
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(ownerThread()))
		{
			result = caseParallelSync();
			//We push the current state, 
			pushNext(node, question);
			
		}
		//the process has children and must now handle either termination or event sync
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(ownerThread()))
		{
			result = caseParallelEnd(question); 
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
	
	
	
	private CmlBehaviourSignal caseParallelBegin(SParallelAction node, Context question)
	{
		PAction left = node.getLeftAction();
		PAction right = node.getRightAction();
		
		//TODO: create a local copy of the question state for each of the actions
		ConcreteBehaviourThread leftInstance = 
				new ConcreteBehaviourThread(left, question, 
						new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),ownerThread());
		
		ConcreteBehaviourThread rightInstance = 
				new ConcreteBehaviourThread(right, question, 
						new LexNameToken(name.module,"|||" + name.getIdentifier().getName(),right.getLocation()),ownerThread());
		
		return caseParallelBeginGeneral(leftInstance,rightInstance,question);
	}
			
	@Override
	public CmlBehaviourSignal caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {

		//if we are hiding we need an extra silents transition to skip without hiding
		if(!getHidingAlphabet().isEmpty())
		{
			//set to an empty alphabet
			setHidingAlphabet(new CmlAlphabet());
			pushNext(new ASkipAction(), question);
		}
		else	
		{
			//if hasNext() is true then Skip is in sequential composition with next
			if(!hasNext())
				setState(CmlProcessState.FINISHED);
		}
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	

	/**
	 * internal choice - section 7.5.3 D23.2
	 * 
	 * An internal choice between two actions can evolve via a tau event into either of them
	 */
	@Override
	public CmlBehaviourSignal caseAInternalChoiceAction(
			AInternalChoiceAction node, Context question)
			throws AnalysisException {
			
		//For now we always pick the left action
		pushNext(node.getLeft(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * State-based Choice - section 7.5.5 D23.2
	 * Guard
	 * Guarded actions are stuck, unless the guard is true.
	 * So If we ever execute this transition, the guard expression would already
	 * have been checked for being true.
	 */
	@Override
	public CmlBehaviourSignal caseAGuardedAction(AGuardedAction node,
			Context question) throws AnalysisException {

		pushNext(node.getAction(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Hiding - section 7.5.8 D23.2
	 */
	@Override
	public CmlBehaviourSignal caseAHidingAction(AHidingAction node,
			Context question) throws AnalysisException {

		setHidingAlphabet((CmlAlphabet)node.getChansetExpression().apply(cmlEvaluator,question));

		pushNext(node.getLeft(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Recursion - section 7.5.9
	 * 
	 */
	@Override
	public CmlBehaviourSignal caseAMuAction(AMuAction node, Context question)
			throws AnalysisException {

		AMuAction muActionClone = node.clone();
		
		for(int i = node.getIdentifiers().size()-1; i >= 0 ; i--)
		{
			PAction action = muActionClone.getActions().get(i); 
			doMuReplace(action,muActionClone,
					node.getIdentifiers().get(i));
			pushNext(action, question);
		}
		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	private void doMuReplace(PAction action, final AMuAction muAction, final LexIdentifierToken id) throws AnalysisException
	{
		
		class ReplaceHelper extends DepthFirstAnalysisCMLAdaptor
		{
			@Override
			public void caseAReferenceAction(AReferenceAction node)
					throws AnalysisException {

				//If the identifier is equal we replace the node 
				//with a clone of the mu action
				if(node.getName().getIdentifier().equals(id))
				{
					AMuAction muClone = muAction.clone();
					muClone.setLocation(node.getLocation());
					node.parent().replaceChild(node, muClone);
				}
			}
		}
		
		action.apply(new ReplaceHelper());
	}
}
