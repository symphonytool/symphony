//package eu.compassresearch.core.interpreter.eval;
//
//import java.util.List;
//import java.util.Random;
//import java.util.Stack;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.definitions.PDefinition;
//import org.overture.ast.expressions.PExp;
//import org.overture.ast.lex.LexNameToken;
//import org.overture.ast.node.INode;
//import org.overture.interpreter.runtime.ValueException;
//import org.overture.interpreter.values.Value;
//import org.overture.interpreter.values.ValueList;
//
//import eu.compassresearch.ast.actions.ABlockStatementAction;
//import eu.compassresearch.ast.actions.ACallStatementAction;
//import eu.compassresearch.ast.actions.ACommunicationAction;
//import eu.compassresearch.ast.actions.AElseIfStatementAction;
//import eu.compassresearch.ast.actions.AExternalChoiceAction;
//import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
//import eu.compassresearch.ast.actions.AGuardedAction;
//import eu.compassresearch.ast.actions.AHidingAction;
//import eu.compassresearch.ast.actions.AIfStatementAction;
//import eu.compassresearch.ast.actions.AInterleavingParallelAction;
//import eu.compassresearch.ast.actions.AInternalChoiceAction;
//import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
//import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
//import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
//import eu.compassresearch.ast.actions.AReferenceAction;
//import eu.compassresearch.ast.actions.ASequentialCompositionAction;
//import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
//import eu.compassresearch.ast.actions.ASkipAction;
//import eu.compassresearch.ast.actions.AWhileStatementAction;
//import eu.compassresearch.ast.actions.PAction;
//import eu.compassresearch.ast.actions.SParallelAction;
//import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.ast.types.AActionType;
//import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
//import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
//import eu.compassresearch.core.interpreter.cml.CmlAction;
//import eu.compassresearch.core.interpreter.cml.CmlAlphabet;
//import eu.compassresearch.core.interpreter.cml.CmlBehaviourSignal;
//import eu.compassresearch.core.interpreter.cml.CmlBehaviourThread;
//import eu.compassresearch.core.interpreter.cml.CmlProcessState;
//import eu.compassresearch.core.interpreter.cml.events.CmlEvent;
//import eu.compassresearch.core.interpreter.cml.events.ObservableEvent;
//import eu.compassresearch.core.interpreter.runtime.CmlContext;
//import eu.compassresearch.core.interpreter.util.CmlActionAssistant;
//import eu.compassresearch.core.interpreter.util.CmlBehaviourThreadUtility;
//import eu.compassresearch.core.interpreter.util.Pair;
//import eu.compassresearch.core.interpreter.values.ActionValue;
//import eu.compassresearch.core.interpreter.values.CmlOperationValue;
//import eu.compassresearch.core.interpreter.values.CmlValue;
//
//public class ActionVisitor extends
//		QuestionAnswerCMLAdaptor<CmlContext, Stack<Pair<INode, CmlContext>>> {
//
//	
//	interface VisitorAccess
//	{
//		void setState(CmlProcessState state);
//		boolean hasNext();
//		void setHidingAlphabet(CmlAlphabet alpha);
//		CmlAlphabet getHidingAlphabet();
//		void addChild(CmlBehaviourThread child);
//		CmlBehaviourSignal executeChildAsSupervisor(CmlBehaviourThread child);
//		void removeTheChildren();
//	};
//	
//	
//	private CmlEvaluator 						cmlEvaluator = new CmlEvaluator();
//	private CmlBehaviourThread 					executingThread;
//	//use for making random but deterministic decisions
//	protected Random 							rnd = new Random(9784345);
//	private VisitorAccess						visitorAccess;
//	
//	/**
//	 * Transition methods
//	 */
//		
//	@Override
//	public Stack<Pair<INode,CmlContext>> defaultPAction(PAction node, CmlContext question)
//			throws AnalysisException {
//
//		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
//	}
//	
//	private Stack<Pair<INode,CmlContext>> currentStack = new Stack<Pair<INode,CmlContext>>();
//	
//	private void pushNext(INode node, CmlContext question)
//	{
//		currentStack.push(new Pair<INode, CmlContext>(node, question));
//	}
//	
//	private Stack<Pair<INode,CmlContext>> result()
//	{
//		Stack<Pair<INode,CmlContext>> tmp = currentStack; 
//		currentStack = new Stack<Pair<INode,CmlContext>>();
//		return tmp;
//	}
//	
//	/**
//	 * The action inside a block is executed directly, since it has no semantic meaning.
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>> caseABlockStatementAction(
//			ABlockStatementAction node, CmlContext question)
//			throws AnalysisException {
//		
//		CmlContext blockContext = new CmlContext(node.getLocation(), "block context", question);
//		
//		//add the assignements defs to the context
//		if(node.getDeclareStatement() != null)
//		{
//			for(PDefinition def : node.getDeclareStatement().getAssignmentDefs())
//					def.apply(cmlEvaluator,blockContext);
//		}
//		
//		pushNext(node.getAction(), blockContext); 
//		return result();
//	}
//	
//	@Override
//	public Stack<Pair<INode,CmlContext>> caseAIfStatementAction(AIfStatementAction node,
//			CmlContext question) throws AnalysisException {
//
//		try
//		{
//    		if (node.getIfExp().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
//    		{
//    			pushNext(node.getThenStm(), question);
//    		}
//    		else
//    		{
//    			boolean foundElseIf = false;
//    			for (AElseIfStatementAction elseif: node.getElseIf())
//    			{
//    				if(elseif.getElseIf().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
//    				{
//    					pushNext(elseif.getThenStm(), question);
//    					foundElseIf = true;
//    					break;
//    				}
//    			}
//
//    			if (node.getElseStm() != null && !foundElseIf)
//    			{
//    				pushNext(node.getElseStm(), question);
//    			}
//
//    			return result();
//    		}
//        }
//        catch (ValueException e)
//        {
//        	//TODO find a better way to report errors
//        	e.printStackTrace();
//        	//return VdmRuntimeError.abort(node.getLocation(),e);
//        }
//		
//		return result();
//	}
//	
//	/* 
//	 * FIXME This is a first attempt, arguments and returns are still not supported on void functions.
//	 * (non-Javadoc)
//	 * @see eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor#caseACallStatementAction(eu.compassresearch.ast.actions.ACallStatementAction, java.lang.Object)
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseACallStatementAction(
//			ACallStatementAction node, CmlContext question)
//			throws AnalysisException {
//
//		//first find the operation value in the context
//		CmlOperationValue opVal = question.lookup(node.getName()); 
//		
//		
//		ValueList argValues = new ValueList();
//
//		//evaluate the arguments
//		for (PExp arg: node.getArgs())
//		{
//			argValues.add(arg.apply(cmlEvaluator,question));
//		}
//		
//		// Note args cannot be Updateable, so we convert them here. This means
//		// that TransactionValues pass the local "new" value to the far end.
//		ValueList constValues = argValues.getConstant();
//
//		if (opVal.getBody() == null)
//		{
//			opVal.abort(4066, "Cannot call implicit operation: " + executingThread.name(), question.getVdmContext());
//		}
//		
//		//TODO add the arg patterns with the results to the context here
//		
//		//TODO maybe this context should be a different one
//		CmlContext callContext = new CmlContext(node.getLocation(), "Op call", question);
//		
//		pushNext(opVal.getBody(), callContext);
//		
//		return result();
//		
//	}
//
//	/**
//	 * Synchronisation and Communication D23.2 7.5.2
//	 * 
//	 * This transition can either be
//	 * Simple prefix   	: a -> A
//	 * Synchronisation 	: a.1 -> A
//	 * Output			: a!2 -> A
//	 * Input			: a?x -> A
//	 * As defined in 7.5.2 in D23.2
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseACommunicationAction(
//			ACommunicationAction node, CmlContext question)
//			throws AnalysisException {
//		//At this point the supervisor has already given go to the event, or the event is hidden
//		//TODO: input is still missing
//		pushNext(node.getAction(), question); 
//		
//		return result();
//		
//		 
////		//so we can execute it immediately. We just have figure out which kind of event it is
////		if(CmlActionAssistant.isPrefixEvent(node))
////			result = casePrefixEvent(node, question);
////		//supervisor().clearSelectedCommunication();
//	}
//	
//	/**
//	 * Helper methods for Synchronisation and Communication transition rules
//	 */
//	
//	
//	private Stack<Pair<INode,CmlContext>>casePrefixEvent(ACommunicationAction node, CmlContext question) 
//			throws AnalysisException 
//	{
//		pushNext(node.getAction(), question); 
//		return result();
//	}
//	
//	
//	/**
//	 * External Choice D23.2 7.5.4
//	 * 
//	 *  There four transition rules for external choice:
//	 *  
//	 *  * External Choice Begin
//	 *  
//	 *  * External Choice Silent
//	 *  
//	 *  * External Choice SKIP
//	 *  
//	 *  * External Choice End
//	 *  
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAExternalChoiceAction(
//			AExternalChoiceAction node, CmlContext question)
//			throws AnalysisException {
//		
//		Stack<Pair<INode,CmlContext>>result = null;
//		
//		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
//		if(!executingThread.hasChildren()){
//			result = caseExternalChoiceBegin(node,question);
//		}
//		//If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
//		//with the state from the skip. After this all the children processes are terminated
//		else if(CmlBehaviourThreadUtility.existsAFinishedChild(executingThread))
//		{
//			result = caseExternalChoiceSkip();
//		}
//		//if this is true, then we can resolve the choice to the event
//		//of one of the children that are waiting for events
//		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(executingThread))
//		{
//			result = caseExternalChoiceEnd();
//		}
//		else
//			result = null;
//		
//		return result;
//	}
//	
//	/**
//	 * External Choice helper methods
//	 */
//
//	/**
//	 * handles the External Choice Begin Rule
//	 * @param node
//	 * @param question
//	 * @return
//	 */
//	private Stack<Pair<INode,CmlContext>>caseExternalChoiceBegin(AExternalChoiceAction node,CmlContext question)
//	{
//		PAction left = node.getLeft();
//		PAction right = node.getRight();
//		LexNameToken name = executingThread.name();
//		//TODO: create a local copy of the question state for each of the actions
//		CmlAction leftInstance = 
//				new CmlAction(left, question, 
//						new LexNameToken(name.module,name.getIdentifier().getName() + "[]" ,left.getLocation()),(CmlAction)executingThread);
//		
//		CmlAction rightInstance = 
//				new CmlAction(right, question, 
//						new LexNameToken(name.module,"[]" + name.getIdentifier().getName(),right.getLocation()),(CmlAction)executingThread);
//		
//		//Add the children to the process graph
//		visitorAccess.addChild(leftInstance);
//		visitorAccess.addChild(rightInstance);
//		
//		//Now let this process wait for the children to get into a waitForEvent state
//		visitorAccess.setState(CmlProcessState.WAIT_CHILD);
//		
//		//We push the current state, since this process will control the child processes created by it
//		pushNext(node, question);
//		
//		return result();
//	}
//	
//	/**
//	 * Handles the External Choice Skip rule
//	 * @return
//	 */
//	private Stack<Pair<INode,CmlContext>>caseExternalChoiceSkip()
//	{
//		//find the finished child
//		CmlBehaviourThread skipChild = findFinishedChild();
//		
//		//FIXME: maybe the we should differentiate between actions and process instead of just having CmlProcess
//		// 		Childerens. We clearly need it!
//		//we know its an action
//		CmlAction childAction = (CmlAction)skipChild; 
//		
//		//Extract the current CmlContext of finished child action and use it as the CmlContext
//		//for the Skip action.
//		pushNext(new ASkipAction(), childAction.prevState().second);
//		
//		//mmmmuhuhuhahaha kill all the children
//		killAndRemoveAllTheEvidenceOfTheChildren();
//		
//		return result();
//	}
//	
//	private Stack<Pair<INode,CmlContext>>caseExternalChoiceEnd()
//	{
//		AbstractBehaviourThread<PAction> theChoosenOne = findTheChoosenChild(executingThread.supervisor().selectedObservableEvent());
//		
//		//first we execute the child
//		Stack<Pair<INode,CmlContext>>result = executeChildAsSupervisor(theChoosenOne);
//		
//		if(theChoosenOne.hasNext())
//		{	//get the state replace the current state
//			//FIXME: this is really really ugly
//			for(Pair<PAction,CmlContext> state : theChoosenOne.getExecutionStack())
//			{
//				pushNext(state.first, 
//						state.second);
//			}
//		}
//		else
//		{
//			pushNext(theChoosenOne.prevState().first, 
//					theChoosenOne.prevState().second);
//		}
//		visitorAccess.setState(CmlProcessState.RUNNING);
//		
//		//mmmmuhuhuhahaha kill all the children
//		killAndRemoveAllTheEvidenceOfTheChildren();
//		
//		return result;
//	}
//	
//	/**
//	 * Finds the first finished child if any
//	 * @return The first finished child, if none then null is returned
//	 */
//	private CmlBehaviourThread findFinishedChild()
//	{
//		for(CmlBehaviourThread child : executingThread.children())
//		{
//			if(child.finished())
//				return child;
//		}
//		
//		return null;
//	}
//	
//	/**
//	 * 
//	 * @param event
//	 * @return
//	 */
//	private AbstractBehaviourThread<PAction> findTheChoosenChild(ObservableEvent event)
//	{
//		for(CmlBehaviourThread child : executingThread.children())
//		{
//			if(child.waiting() && child.inspect().containsObservableEvent(event))
//				return child;
//		}
//		
//		return null;
//	}
//	
//	private void killAndRemoveAllTheEvidenceOfTheChildren()
//	{
//		//Abort all the children of this action
//		for(CmlBehaviourThread child : executingThread.children())
//		{
//			child.setAbort(null);
//		}
//		
//		//Remove them from the supervisor
//		visitorAccess.removeTheChildren();
//	}
//	
//	/**
//	 * External Choice  
//	 * End of region
//	 * 
//	*/
//	
//	/**
//	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAReferenceAction(AReferenceAction node,
//			CmlContext question) throws AnalysisException {
//		//FIXME: the scoping is not correct, this should be done as described in the transition rule
//		
//		//FIXME: Consider: Instead of this might create a child process, and behave as this child until it terminates
//		//CMLActionInstance refchild = new CMLActionInstance(node.getActionDefinition().getAction(), question, node.getName()); 
//		
//		ActionValue actionValue = (ActionValue)question.lookup(node.getName());
//		
//		pushNext(actionValue.getActionDefinition().getAction(), question); 
//		return result();
//	}
//	
//	/**
//	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseASequentialCompositionAction(
//			ASequentialCompositionAction node, CmlContext question)
//			throws AnalysisException {
//
//		//First push right and then left, so that left get executed first
//		pushNext(node.getRight(), question);
//		pushNext(node.getLeft(), question);
//		
//		return result();
//	}
//
//	/**
//	 * Parallel composition D23.2 7.5.7
//	 *  
//	 */
//	
//	/**
//	 * Generalised Parallelism
//	 * A [| cs |] B (no state) 
//	 * 
//	 * or
//	 * 
//	 * A [| ns1 | cs | ns2 |] B 
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAGeneralisedParallelismParallelAction(
//			AGeneralisedParallelismParallelAction node, CmlContext question)
//			throws AnalysisException {
//	
//		//TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"
//		Stack<Pair<INode,CmlContext>>result = null;
//		
//		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
//		if(!executingThread.hasChildren()){
//			result = caseParallelBegin(node,question);
//			//We push the current state, since this process will control the child processes created by it
//			pushNext(node, question);
//		}
//		//The process has children and they have all evolved into Skip so now the parallel end rule will be invoked 
//		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(executingThread))
//		{
//			result = caseParallelEnd(question); 
//		}
//		//At least one child is not finished and waiting for event, this will either invoke the Parallel Non-sync or Sync rule
//		else if(CmlBehaviourThreadUtility.isAllChildrenFinishedOrWaitingForEvent(executingThread))
//		{
//			//convert the channelset of the current node to a alphabet
//			CmlAlphabet cs =  ((CmlValue)node.getChansetExpression().
//					apply(cmlEvaluator,question)).cmlAlphabetValue(question);
//			
//			//get the immediate alphabets of the left and right child
//			CmlBehaviourThread leftChild = executingThread.children().get(0);
//			CmlAlphabet leftChildAlpha = leftChild.inspect().flattenSyncEvents(); 
//			CmlBehaviourThread rightChild = executingThread.children().get(1);
//			CmlAlphabet rightChildAlpha = rightChild.inspect().flattenSyncEvents();
//
//			//convert the selected event to a CmlAlphabet
//			CmlAlphabet selectedEventAlpha = executingThread.supervisor().selectedObservableEvent().getAsAlphabet();
//			//now make the intersection between the selectedEventAlpha and the children's alpha
//			CmlAlphabet leftOption = selectedEventAlpha.intersect(leftChildAlpha);
//			CmlAlphabet rightOption = selectedEventAlpha.intersect(rightChildAlpha);
//			
//			//if both intersections are non empty it must be a sync event
//			if(!leftOption.isEmpty() &&
//					!rightOption.isEmpty())
//			{
//				//supervisor().setSelectedObservableEvent(leftOption.getObservableEvents().iterator().next());
//				visitorAccess.executeChildAsSupervisor(leftChild);
//				//supervisor().setSelectedObservableEvent(rightOption.getObservableEvents().iterator().next());
//				visitorAccess.executeChildAsSupervisor(rightChild);
//				result = result();
//			}
//			else if(!leftOption.isEmpty())
//			{
//				if(visitorAccess.executeChildAsSupervisor(leftChild) != CmlBehaviourSignal.EXEC_SUCCESS)
//					return null;
//					
//			}
//			else if(!rightOption.isEmpty())
//			{
//				if(visitorAccess.executeChildAsSupervisor(rightChild) != CmlBehaviourSignal.EXEC_SUCCESS)
//					return null;
//			}
//			else
//			{
//				result = null;
//			}
//			
//			//We push the current state, 
//			pushNext(node, question);
//		}
//		
//		
//		return result();
//	}
//	
//	/**
//	 * Interleaving
//	 * A ||| B (no state)
//	 * 
//	 * or 
//	 * 
//	 * A [|| ns1 | ns2 ||] B
//	 * 
//	 * This has three parts:
//	 * 
//	 * Parallel Begin:
//	 * 	At this step the interleaving action are not yet created. So this will be a silent (tau) transition
//	 * 	where the left and right actions will be created and started.
//	 * 
//	 * Parallel Non-sync:
//	 * 	At this step the actions are each executed separately. Since no sync shall stake place this Action just wait
//	 * 	for the child actions to be in the FINISHED state. 
//	 * 
//	 * Parallel End:
//	 *  At this step both child actions are in the FINISHED state and they will be removed from the running process network
//	 *  and this will make a silent transition into Skip. 
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAInterleavingParallelAction(
//			AInterleavingParallelAction node, CmlContext question)
//			throws AnalysisException {
//
//		//TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"
//		Stack<Pair<INode,CmlContext>>result = null;
//		
//		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
//		if(!executingThread.hasChildren()){
//			result = caseParallelBegin(node,question);
//			//We push the current state, since this process will control the child processes created by it
//			pushNext(node, question);
//
//		}
//		//At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync 
//		else if(CmlBehaviourThreadUtility.isAtLeastOneChildWaitingForEvent(executingThread))
//		{
//			result = caseParallelSync();
//			//We push the current state, 
//			pushNext(node, question);
//			
//		}
//		//the process has children and must now handle either termination or event sync
//		else if (CmlBehaviourThreadUtility.isAllChildrenFinished(executingThread))
//		{
//			result = caseParallelEnd(question); 
//		}
//		//else if ()
//		
//		return result;
//	}
//	
//	/**
//	 * Parallel composition Helper methods
//	 */
//	
//	/**
//	 * This method introduces a local state for each parallel action which is the source state component
//	 * restricted by the nameset expressions
//	 * @param question
//	 * @return
//	 */
//	
//	
//	
//	private Stack<Pair<INode,CmlContext>>caseParallelBegin(SParallelAction node, CmlContext question)
//	{
//		PAction left = node.getLeftAction();
//		PAction right = node.getRightAction();
//		
//		LexNameToken name = executingThread.name();
//		
//		//TODO: create a local copy of the question state for each of the actions
//		CmlAction leftInstance = 
//				new CmlAction(left, question, 
//						new LexNameToken(name.module,name.getIdentifier().getName() + "|||" ,left.getLocation()),(CmlAction)executingThread);
//		
//		CmlAction rightInstance = 
//				new CmlAction(right, question, 
//						new LexNameToken(name.module,"|||" + name.getIdentifier().getName(),right.getLocation()),(CmlAction)executingThread);
//		
//		return caseParallelBeginGeneral(leftInstance,rightInstance,question);
//	}
//			
//	protected Stack<Pair<INode,CmlContext>>caseParallelEnd(CmlContext question)
//	{
//		visitorAccess.removeTheChildren();
//		
//		//now this process evolves into Skip
//		pushNext(new ASkipAction(), question);
//		
//		return result();
//	}
//	
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseASkipAction(ASkipAction node, CmlContext question)
//			throws AnalysisException {
//
//		//if we are hiding we need an extra silents transition to skip without hiding
//		if(!visitorAccess.getHidingAlphabet().isEmpty())
//		{
//			//set to an empty alphabet
//			visitorAccess.setHidingAlphabet(new CmlAlphabet());
//			pushNext(new ASkipAction(), question);
//		}
//		else	
//		{
//			//if hasNext() is true then Skip is in sequential composition with next
//			if(!visitorAccess.hasNext())
//				visitorAccess.setState(CmlProcessState.FINISHED);
//		}
//		return result();
//	}
//	
//
//	/**
//	 * internal choice - section 7.5.3 D23.2
//	 * 
//	 * An internal choice between two actions can evolve via a tau event into either of them
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAInternalChoiceAction(
//			AInternalChoiceAction node, CmlContext question)
//			throws AnalysisException {
//			
//		//For now we always pick the left action
//		pushNext(node.getLeft(), question);
//		
//		return result();
//	}
//	
//	/**
//	 * Assignment - section 7.5.1 D23.2
//	 * 
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseASingleGeneralAssignmentStatementAction(
//			ASingleGeneralAssignmentStatementAction node, CmlContext question)
//					throws AnalysisException {
////		question.putNew(new NameValuePair(new LexNameToken("", new LexIdentifierToken("a", false, new LexLocation())), new IntegerValue(2)));
//		Value expValue = node.getExpression().apply(cmlEvaluator,question);
//		
//		//TODO Change this to deal with it in general
//		LexNameToken stateDesignatorName = CmlActionAssistant.extractNameFromStateDesignator(node.getStateDesignator());
//		CmlContext nameContext = (CmlContext)question.locate(stateDesignatorName);
//		
//		nameContext.put(stateDesignatorName, expValue);
//		
//		System.out.println(stateDesignatorName + " = " + expValue);
//		
//		//now this process evolves into Skip
//		pushNext(new ASkipAction(node.getLocation(),new AActionType()), question);
//		
//		return result();
//	}
//	
//	/**
//	 * State-based Choice - section 7.5.5 D23.2
//	 * Guard
//	 * Guarded actions are stuck, unless the guard is true.
//	 * So If we ever execute this transition, the guard expression would already
//	 * have been checked for being true.
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAGuardedAction(AGuardedAction node,
//			CmlContext question) throws AnalysisException {
//
//		pushNext(node.getAction(), question); 
//		
//		return result();
//	}
//	
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAHidingAction(AHidingAction node,
//			CmlContext question) throws AnalysisException {
//
//		visitorAccess.setHidingAlphabet((CmlAlphabet)node.getChansetExpression().apply(cmlEvaluator,question));
//
//		pushNext(node.getLeft(), question); 
//		
//		return result();
//	}
//	
//	/**
//	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseANonDeterministicIfStatementAction(
//			ANonDeterministicIfStatementAction node, CmlContext question)
//			throws AnalysisException {
//
//		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
//				node.getAlternatives(),question,cmlEvaluator);
//		//if we got here we already now that the must at least be one available action
//		//so this should pose no risk of exception
//		pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
//		 
//		return result();
//	}
//
//	/**
//	 * 
//	 * //TODO no semantics defined, resolve this!
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseANonDeterministicDoStatementAction(
//			ANonDeterministicDoStatementAction node, CmlContext question)
//			throws AnalysisException {
//
//		List<ANonDeterministicAltStatementAction> availableAlts = CmlActionAssistant.findAllTrueAlts(
//				node.getAlternatives(),question,cmlEvaluator);
//		
//		
//		if(availableAlts.size() > 0)
//		{
//			//first we push the do node on the execution stack to get it sequentially composed with the
//			//picked alternative
//			pushNext(node, question);		
//			//if we got here we already now that the must at least be one available action
//			//so this should pose no risk of exception
//			pushNext(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction(),question);
//		}
//		else
//			pushNext(new ASkipAction(), question);
//			
//		return result();
//	}
//	
//	/**
//	 * 
//	 * //TODO no semantics defined, resolve this!
//	 */
//	@Override
//	public Stack<Pair<INode,CmlContext>>caseAWhileStatementAction(
//			AWhileStatementAction node, CmlContext question)
//			throws AnalysisException {
//
//		if(node.getCondition().apply(cmlEvaluator,question).boolValue(question.getVdmContext()))
//		{
//			//first we push the while node so that we get back to this point
//			pushNext(node, question);
//			//then we push the first action of the loop
//			pushNext(node.getAction(), question);
//		}
//		else
//		{
//			//if the condition is false then the While evolves into Skip
//			pushNext(new ASkipAction(), question);
//		}
//		
//		
//		return result();
//	}
//	
//	
//}
