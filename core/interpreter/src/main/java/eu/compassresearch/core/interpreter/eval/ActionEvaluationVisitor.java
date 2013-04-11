package eu.compassresearch.core.interpreter.eval;

import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ACallStatementAction;
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
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.core.interpreter.CmlContextFactory;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.values.ActionValue;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;

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
	
	private AbstractEvaluationVisitor statementEvalVisitor = new CmlStatementEvaluationVisitor(this);
	
	public ActionEvaluationVisitor(AbstractEvaluationVisitor parentVisitor)
	{
		super(parentVisitor);
	}
	
	
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
	 * Synchronization and Communication D23.2 7.5.2
	 * 
	 * This transition can either be
	 * Simple prefix   	: a -> A
	 * Synchronization 	: a.1 -> A
	 * Output			: a!2 -> A
	 * Input			: a?x -> A
	 * As defined in 7.5.2 in D23.2
	 */
	@Override
	public CmlBehaviourSignal caseACommunicationAction(
			ACommunicationAction node, Context question)
			throws AnalysisException {
		
		//At this point the supervisor has already given go to the event, or the event is hidden
		Value value = ((ObservableEvent)supervisor().selectedObservableEvent()).getValue();
		
//		if(node.getCommunicationParameters() != null && 
//				node.getCommunicationParameters().size() > 1
//				)
//			throw new InterpreterRuntimeException("At the moment records and tuples are not supported");
		
		if(node.getCommunicationParameters() != null)
		{

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
		}
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
	 * This deals both with calls but also parametrised action reference, since the typechecker does not replace this node yet
	 * FIXME This might be changed! if the typechecker replaces the call node with a action reference node 
	 */
	@Override
	public CmlBehaviourSignal caseACallStatementAction(
			ACallStatementAction node, Context question)
			throws AnalysisException {

		Value value = question.lookup(node.getName()); 
		
		if(value instanceof CmlOperationValue)
			return node.apply(statementEvalVisitor,question);
		else if (value instanceof ActionValue)
		{
			//first find the action value in the context
			ActionValue actionVal = (ActionValue)question.check(node.getName());
			
			return caseReferenceAction(node.getLocation(),node.getArgs(), actionVal, question);
		}
		else
			return CmlBehaviourSignal.FATAL_ERROR;
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
		ActionValue actionValue = (ActionValue)question.check(node.getName()).deref();
		
		return caseReferenceAction(node.getLocation(),node.getArgs(), actionValue, question);
		
	}
	
	protected CmlBehaviourSignal caseReferenceAction(LexLocation location,
		List<PExp> args,ActionValue actionValue,Context question) throws AnalysisException {

		//evaluate all the arguments
		NameValuePairMap evaluatedArgs = new NameValuePairMap();

		int paramIndex = 0;
		for(PParametrisation parameterization : actionValue.getActionDefinition().getDeclarations())
		{
			for(LexIdentifierToken id : parameterization.getDeclaration().getIdentifiers())
			{
				//get and evaluate the i'th expression
				PExp arg = args.get(paramIndex);
				Value value = arg.apply(cmlExpressionVisitor,question);

				//check whether the type is correct
				//if(arg.getType().equals(o))
				//error(node,"Arguments does not match the action parameterization");

				//Decide whether the argument is updateable or not
				if(parameterization instanceof AValParametrisation)
					value = value.getConstant();
				else {
					value = value.getUpdatable(null);
				}

				evaluatedArgs.put(new LexNameToken("",(LexIdentifierToken)id.clone()), value);

				//update the index
				paramIndex++;
			}

		}

		Context refActionContext = CmlContextFactory.newContext(location, 
				"Parametrised reference action context", question);

		refActionContext.putAll(evaluatedArgs);

		pushNext(actionValue.getActionDefinition().getAction(), refActionContext); 
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
		else if(CmlBehaviourThreadUtility.childWaitingForEventExists(ownerThread()))
		{
			result = caseParallelNonSync();
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
		CmlBehaviourThread leftInstance = 
				VanillaInterpreterFactory.newCmlBehaviourThread(left, question, 
						new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),ownerThread());
		
		CmlBehaviourThread rightInstance = 
				VanillaInterpreterFactory.newCmlBehaviourThread(right, question, 
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

		setHidingAlphabet((CmlAlphabet)node.getChansetExpression().apply(cmlExpressionVisitor,question));

		pushNext(node.getLeft(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Recursion - section 7.5.9
	 * TODO Mutually recursive processes are not implemented yet
	 */
	@Override
	public CmlBehaviourSignal caseAMuAction(AMuAction node, Context question)
			throws AnalysisException {

		
		///THIS IS NOT CORRECT sEMANTICALLY, 
		Context muContext = CmlContextFactory.newContext(node.getLocation(), "mu context", question);
		
		NameValuePairList nvpl = new NameValuePairList();
		
		for(int i = 0 ; i < node.getIdentifiers().size() ; i++)
		{
			LexIdentifierToken id = node.getIdentifiers().get(i);
			
			LexNameToken name = new LexNameToken("", id);
			
			PAction action = node.getActions().get(i);
			
			AActionDefinition actionDef = new AActionDefinition(node.getLocation(),
					NameScope.LOCAL,true,null,Pass.DEFS,null,action);
			
			nvpl.add(new NameValuePair(name, 
					new ActionValue(actionDef)));
			if(i == 0)
				pushNext(action, muContext);
		}
		
		muContext.putAllNew(nvpl);

		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
//	private void doMuReplace(PAction action, final AMuAction muAction, final LexIdentifierToken id) throws AnalysisException
//	{
//		
//		class ReplaceHelper extends DepthFirstAnalysisCMLAdaptor
//		{
//			@Override
//			public void caseAReferenceAction(AReferenceAction node)
//					throws AnalysisException {
//
//				//If the identifier is equal we replace the node 
//				//with a clone of the mu action
//				if(node.getName().getIdentifier().equals(id))
//				{
//					AMuAction muClone = muAction.clone();
//					muClone.setLocation(node.getLocation());
//					node.parent().replaceChild(node, muClone);
//				}
//			}
//		}
//		
//		action.apply(new ReplaceHelper());
//	}
}
