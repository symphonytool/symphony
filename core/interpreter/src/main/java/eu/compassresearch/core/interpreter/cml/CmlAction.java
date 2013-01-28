package eu.compassresearch.core.interpreter.cml;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
import eu.compassresearch.core.interpreter.eval.CmlOpsToString;
import eu.compassresearch.core.interpreter.events.CmlProcessStateEvent;
import eu.compassresearch.core.interpreter.events.TraceEvent;
import eu.compassresearch.core.interpreter.runtime.CmlContext;
import eu.compassresearch.core.interpreter.runtime.CmlRuntime;
import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
import eu.compassresearch.core.interpreter.util.Pair;
import eu.compassresearch.core.interpreter.values.ActionValue;
import eu.compassresearch.core.interpreter.values.CmlOperationValue;
import eu.compassresearch.core.interpreter.values.CmlValue;

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
public class CmlAction extends AbstractBehaviourThread<PAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 993071972119803788L;
	
	public CmlAction(PAction action,CmlContext context, LexNameToken name)
	{
		super(null,name);
		pushNext(action, context);
	}
	
	public CmlAction(PAction action,CmlContext context, LexNameToken name, CmlAction parent)
	{
		super(parent,name);
		pushNext(action, context);
	}
	
	@Override
	public void start(CmlSupervisorEnvironment env) {
		
		this.env = env; 
		
		//If it has no parent it is controlled by a CmlProcess object as the main process behaviour
		//Therefore it should not be added as a pupil
		if(parent() != null)
			supervisor().addPupil(this);
		
		setState(CmlProcessState.RUNNABLE);
	}

	@Override
	public String nextStepToString() {
		
		if(hasNext())
		{
			if(hasChildren())
			{
				CmlBehaviourThread leftChild = children().get(0);
				CmlBehaviourThread rightChild = children().get(1);
				
				return "(" + leftChild.nextStepToString() + ")" + CmlOpsToString.toString(nextState().first) + "(" + rightChild.nextStepToString()+")";
			}
			else{
				return nextState().first.toString();
			}
		}
		else
			return "Finished";
	}
	
	@Override
	public String toString() {
	
		return name.toString();
	}
	
	/**
	 * Process state methods 
	 */
	@Override
	public CmlProcessState getState() {
		return state;
	}

	@Override
	protected void setState(CmlProcessState state) {
		
		if(getState() != state)
		{
			CmlProcessStateEvent ev = new CmlProcessStateEvent(this, this.state, state);
			this.state = state;
			notifyOnStateChange(ev);
			CmlRuntime.logger().finest(name() + ":" + state.toString());
		}
	}
	
	

	/**
	 * CmlProcessTraceObserver interface 
	 */
	
	/**
	 * This will provide the traces from all the child actions
	 */
	@Override
	public void onTraceChange(TraceEvent traceEvent) {
		
		this.trace.addEvent(traceEvent.getEvent());
		notifyOnTraceChange(TraceEvent.createRedirectedEvent(this, traceEvent));
	}
	
	/**
	 * Private helper methods
	 */
	
		
	/**
	 * Transition methods
	 */
	
	@Override
	public CmlBehaviourSignal defaultPAction(PAction node, CmlContext question)
			throws AnalysisException {

		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}
	
	/**
	 * The action inside a block is executed directly, since it has no semantic meaning.
	 */
	@Override
	public CmlBehaviourSignal caseABlockStatementAction(
			ABlockStatementAction node, CmlContext question)
			throws AnalysisException {
		
		CmlContext blockContext = new CmlContext(node.getLocation(), "block context", question);
		
		//add the assignements defs to the context
		if(node.getDeclareStatement() != null)
		{
			for(PDefinition def : node.getDeclareStatement().getAssignmentDefs())
					def.apply(cmlEvaluator,blockContext);
		}
		
		pushNext(node.getAction(), blockContext); 
		return CmlBehaviourSignal.EXEC_SUCCESS;
		//return node.getAction().apply(this,blockContext);
	}
	
	@Override
	public CmlBehaviourSignal caseAIfStatementAction(AIfStatementAction node,
			CmlContext question) throws AnalysisException {

		try
		{
    		if (node.getIfExp().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
    		{
    			pushNext(node.getThenStm(), question);
    		}
    		else
    		{
    			boolean foundElseIf = false;
    			for (AElseIfStatementAction elseif: node.getElseIf())
    			{
    				if(elseif.getElseIf().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
    				{
    					pushNext(elseif.getThenStm(), question);
    					foundElseIf = true;
    					break;
    				}
    			}

    			if (node.getElseStm() != null && !foundElseIf)
    			{
    				pushNext(node.getElseStm(), question);
    			}

    			return CmlBehaviourSignal.EXEC_SUCCESS;
    		}
        }
        catch (ValueException e)
        {
        	//TODO find a better way to report errors
        	e.printStackTrace();
        	//return VdmRuntimeError.abort(node.getLocation(),e);
        }
		
		return CmlBehaviourSignal.FATAL_ERROR;
	}
	
	/* 
	 * FIXME This is a first attempt, arguments and returns are still not supported on void functions.
	 * (non-Javadoc)
	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseACallStatementAction(eu.compassresearch.ast.actions.ACallStatementAction, java.lang.Object)
	 */
	@Override
	public CmlBehaviourSignal caseACallStatementAction(
			ACallStatementAction node, CmlContext question)
			throws AnalysisException {

		//first find the operation value in the context
		CmlOperationValue opVal = question.lookup(node.getName()); 
		
		
		ValueList argValues = new ValueList();

		//evaluate the arguments
		for (PExp arg: node.getArgs())
		{
			argValues.add(arg.apply(cmlEvaluator,question));
		}
		
		// Note args cannot be Updateable, so we convert them here. This means
		// that TransactionValues pass the local "new" value to the far end.
		ValueList constValues = argValues.getConstant();

		if (opVal.getBody() == null)
		{
			opVal.abort(4066, "Cannot call implicit operation: " + name, question.getVdmContext());
		}
		
		//TODO maybe this context should be a different one
		CmlContext callContext = new CmlContext(node.getLocation(), "Op call", question);
		
		if (argValues.size() != opVal.getParamPatterns().size())
		{
			opVal.abort(4068, "Wrong number of arguments passed to " + name.name, question.getVdmContext());
		}
		
		ListIterator<Value> valIter = argValues.listIterator();
		Iterator<PType> typeIter = opVal.getType().getParameters().iterator();
		NameValuePairMap args = new NameValuePairMap();

		for (PPattern p : opVal.getParamPatterns())
		{
			try
			{
				// Note values are assumed to be constant, as enforced by eval()
				Value pv = valIter.next().convertTo(typeIter.next(), question.getVdmContext());

				for (NameValuePair nvp : PPatternAssistantInterpreter.getNamedValues(p,pv, question.getVdmContext()))
				{
					Value v = args.get(nvp.name);

					if (v == null)
					{
						args.put(nvp);
					}
					else	// Names match, so values must also
					{
						if (!v.equals(nvp.value))
						{
							opVal.abort(4069,	"Parameter patterns do not match arguments", question.getVdmContext());
						}
					}
				}
			}
			catch (PatternMatchException e)
			{
				opVal.abort(e.number, e, question.getVdmContext());
			}
		}
		
		// Note: arg name/values hide member values
		callContext.putAll(args);
		
		//TODO add the arg patterns with the results to the context here
		
		
		pushNext(opVal.getBody(), callContext);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
		
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
			ACommunicationAction node, CmlContext question)
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
	
	
	private CmlBehaviourSignal casePrefixEvent(ACommunicationAction node, CmlContext question) 
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
			AExternalChoiceAction node, CmlContext question)
			throws AnalysisException {
		
		CmlBehaviourSignal result = null;
		
		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!hasChildren()){
			result = caseExternalChoiceBegin(node,question);
		}
		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		//with the state from the skip. After this all the children processes are terminated
		else if(CmlBehaviourThreadUtility.existsAFinishedChild(this))
		{
			result = caseExternalChoiceSkip();
		}
		//if this is true, then we can resolve the choice to the event
		//of one of the children that are waiting for events
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(this))
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
	private CmlBehaviourSignal caseExternalChoiceBegin(AExternalChoiceAction node,CmlContext question)
	{
		PAction left = node.getLeft();
		PAction right = node.getRight();
		
		//TODO: create a local copy of the question state for each of the actions
		CmlAction leftInstance = 
				new CmlAction(left, question, 
						new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,left.getLocation()),this);
		
		CmlAction rightInstance = 
				new CmlAction(right, question, 
						new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),right.getLocation()),this);
		
		//Add the children to the process graph
		addChild(leftInstance);
		addChild(rightInstance);
		
		//Now let this process wait for the children to get into a waitForEvent state
		setState(CmlProcessState.WAIT_CHILD);
		
		//We push the current state, since this process will control the child processes created by it
		pushNext(node, question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Handles the External Choice Skip rule
	 * @return
	 */
	private CmlBehaviourSignal caseExternalChoiceSkip()
	{
		//find the finished child
		CmlBehaviourThread skipChild = findFinishedChild();
		
		//FIXME: maybe the we should differentiate between actions and process instead of just having CmlProcess
		// 		Childerens. We clearly need it!
		//we know its an action
		CmlAction childAction = (CmlAction)skipChild; 
		
		//Extract the current CmlContext of finished child action and use it as the CmlContext
		//for the Skip action.
		pushNext(new ASkipAction(), childAction.prevState().second);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	private CmlBehaviourSignal caseExternalChoiceEnd()
	{
		AbstractBehaviourThread<PAction> theChoosenOne = findTheChoosenChild(supervisor().selectedObservableEvent());
		
		//first we execute the child
		CmlBehaviourSignal result = executeChildAsSupervisor(theChoosenOne);
		
		if(theChoosenOne.hasNext())
		{	//get the state replace the current state
			//FIXME: this is really really ugly
			for(Pair<PAction,CmlContext> state : theChoosenOne.getExecutionStack())
			{
				pushNext(state.first, 
						state.second);
			}
		}
		else
		{
			pushNext(theChoosenOne.prevState().first, 
					theChoosenOne.prevState().second);
		}
		setState(CmlProcessState.RUNNING);
		
		//mmmmuhuhuhahaha kill all the children
		killAndRemoveAllTheEvidenceOfTheChildren();
		
		return result;
	}
	
	/**
	 * Finds the first finished child if any
	 * @return The first finished child, if none then null is returned
	 */
	private CmlBehaviourThread findFinishedChild()
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
	private AbstractBehaviourThread<PAction> findTheChoosenChild(ObservableEvent event)
	{
		for(AbstractBehaviourThread<PAction> child : children)
		{
			if(child.waiting() && child.inspect().containsObservableEvent(event))
				return child;
		}
		
		return null;
	}
	
	private void killAndRemoveAllTheEvidenceOfTheChildren()
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
	
	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 */
	@Override
	public CmlBehaviourSignal caseAReferenceAction(AReferenceAction node,
			CmlContext question) throws AnalysisException {
		//FIXME: the scoping is not correct, this should be done as described in the transition rule
		
		//FIXME: Consider: Instead of this might create a child process, and behave as this child until it terminates
		//CMLActionInstance refchild = new CMLActionInstance(node.getActionDefinition().getAction(), question, node.getName()); 
		
		ActionValue actionValue = (ActionValue)question.lookup(node.getName());
		
		pushNext(actionValue.getActionDefinition().getAction(), question); 
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
	 */
	@Override
	public CmlBehaviourSignal caseASequentialCompositionAction(
			ASequentialCompositionAction node, CmlContext question)
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
			AGeneralisedParallelismParallelAction node, CmlContext question)
			throws AnalysisException {

		final AGeneralisedParallelismParallelAction finalNode = node;
		final CmlContext finalQuestion = question;
		
		return caseGeneralisedParallelismParallel(node,new parallelCompositionHelper() {
			
			@Override
			public CmlBehaviourSignal caseParallelBegin() {
				return CmlAction.this.caseParallelBegin(finalNode, finalQuestion);
			}
		}, new ASkipAction(),node.getChansetExpression(),question);
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
			AInterleavingParallelAction node, CmlContext question)
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
		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(this))
		{
			result = caseParallelSync();
			//We push the current state, 
			pushNext(node, question);
			
		}
		//the process has children and must now handle either termination or event sync
		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(this))
		{
			result = caseParallelEnd(new ASkipAction(), question); 
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
	
	
	
	private CmlBehaviourSignal caseParallelBegin(SParallelAction node, CmlContext question)
	{
		PAction left = node.getLeftAction();
		PAction right = node.getRightAction();
		
		//TODO: create a local copy of the question state for each of the actions
		CmlAction leftInstance = 
				new CmlAction(left, question, 
						new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),this);
		
		CmlAction rightInstance = 
				new CmlAction(right, question, 
						new LexNameToken(name.module,"|||" + name.getIdentifier().getName(),right.getLocation()),this);
		
		return caseParallelBeginGeneral(leftInstance,rightInstance,question);
	}
			
	@Override
	public CmlBehaviourSignal caseASkipAction(ASkipAction node, CmlContext question)
			throws AnalysisException {

		//if we are hiding we need an extra silents transition to skip without hiding
		if(!hidingAlphabet.isEmpty())
		{
			//set to an empty alphabet
			hidingAlphabet = new CmlAlphabet();
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
			AInternalChoiceAction node, CmlContext question)
			throws AnalysisException {
			
		//For now we always pick the left action
		pushNext(node.getLeft(), question);
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Assignment - section 7.5.1 D23.2
	 * 
	 */
	@Override
	public CmlBehaviourSignal caseASingleGeneralAssignmentStatementAction(
			ASingleGeneralAssignmentStatementAction node, CmlContext question)
					throws AnalysisException {
//		question.putNew(new NameValuePair(new LexNameToken("", new LexIdentifierToken("a", false, new LexLocation())), new IntegerValue(2)));
		Value expValue = node.getExpression().apply(cmlEvaluator,question);
		
		//TODO Change this to deal with it in general
		LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getStateDesignator());
		CmlContext nameContext = (CmlContext)question.locate(stateDesignatorName);
		
		nameContext.put(stateDesignatorName, expValue);
		
		System.out.println(stateDesignatorName + " = " + expValue);
		
		//now this process evolves into Skip
		pushNext(new ASkipAction(node.getLocation(),new AActionType()), question);
		
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
			CmlContext question) throws AnalysisException {

		pushNext(node.getAction(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	@Override
	public CmlBehaviourSignal caseAHidingAction(AHidingAction node,
			CmlContext question) throws AnalysisException {

		setHidingAlphabet((CmlAlphabet)node.getChansetExpression().apply(cmlEvaluator,question));

		pushNext(node.getLeft(), question); 
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public CmlBehaviourSignal caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, CmlContext question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
				node.getAlternatives(),question,cmlEvaluator);
		//if we got here we already now that the must at least be one available action
		//so this should pose no risk of exception
		pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
		 
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}

	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public CmlBehaviourSignal caseANonDeterministicDoStatementAction(
			ANonDeterministicDoStatementAction node, CmlContext question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
				node.getAlternatives(),question,cmlEvaluator);
		
		
		if(availableAlts.size() > 0)
		{
			//first we push the do node on the execution stack to get it sequentially composed with the
			//picked alternative
			pushNext(node, question);		
			//if we got here we already now that the must at least be one available action
			//so this should pose no risk of exception
			pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
		}
		else
			pushNext(new ASkipAction(), question);
			
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
	
	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public CmlBehaviourSignal caseAWhileStatementAction(
			AWhileStatementAction node, CmlContext question)
			throws AnalysisException {

		if(node.getCondition().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
		{
			//first we push the while node so that we get back to this point
			pushNext(node, question);
			//then we push the first action of the loop
			pushNext(node.getAction(), question);
		}
		else
		{
			//if the condition is false then the While evolves into Skip
			pushNext(new ASkipAction(), question);
		}
		
		
		return CmlBehaviourSignal.EXEC_SUCCESS;
	}
}
