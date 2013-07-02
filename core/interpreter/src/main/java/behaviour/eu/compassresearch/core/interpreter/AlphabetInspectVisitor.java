//package eu.compassresearch.core.interpreter;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.ast.expressions.PExp;
//import org.overture.ast.intf.lex.ILexIdentifierToken;
//import org.overture.ast.intf.lex.ILexLocation;
//import org.overture.ast.node.INode;
//import org.overture.interpreter.runtime.Context;
//import org.overture.interpreter.runtime.ValueException;
//import org.overture.interpreter.values.NameValuePairMap;
//import org.overture.interpreter.values.Value;
//
//import eu.compassresearch.ast.actions.ABlockStatementAction;
//import eu.compassresearch.ast.actions.ACallStatementAction;
//import eu.compassresearch.ast.actions.ACommunicationAction;
//import eu.compassresearch.ast.actions.AExternalChoiceAction;
//import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
//import eu.compassresearch.ast.actions.AGuardedAction;
//import eu.compassresearch.ast.actions.AHidingAction;
//import eu.compassresearch.ast.actions.AInterleavingParallelAction;
//import eu.compassresearch.ast.actions.AInternalChoiceAction;
//import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
//import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
//import eu.compassresearch.ast.actions.AReadCommunicationParameter;
//import eu.compassresearch.ast.actions.AReferenceAction;
//import eu.compassresearch.ast.actions.ASequentialCompositionAction;
//import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
//import eu.compassresearch.ast.actions.ASkipAction;
//import eu.compassresearch.ast.actions.AStopAction;
//import eu.compassresearch.ast.actions.ATimeoutAction;
//import eu.compassresearch.ast.actions.AValParametrisation;
//import eu.compassresearch.ast.actions.AWaitAction;
//import eu.compassresearch.ast.actions.AWhileStatementAction;
//import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
//import eu.compassresearch.ast.actions.PAction;
//import eu.compassresearch.ast.actions.PCommunicationParameter;
//import eu.compassresearch.ast.actions.PParametrisation;
//import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.ast.expressions.PVarsetExpression;
//import eu.compassresearch.ast.lex.LexNameToken;
//import eu.compassresearch.ast.process.AActionProcess;
//import eu.compassresearch.ast.process.AExternalChoiceProcess;
//import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
//import eu.compassresearch.ast.process.AInterleavingProcess;
//import eu.compassresearch.ast.process.AInternalChoiceProcess;
//import eu.compassresearch.ast.process.AReferenceProcess;
//import eu.compassresearch.ast.process.ASequentialCompositionProcess;
//import eu.compassresearch.ast.process.PProcess;
//import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
//import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
//import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
//import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
//import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
//import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
//import eu.compassresearch.core.interpreter.api.behaviour.CmlTrace;
//import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
//import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
//import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
//import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
//import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionFactory;
//import eu.compassresearch.core.interpreter.api.transitions.CommunicationParameter;
//import eu.compassresearch.core.interpreter.api.transitions.HiddenEvent;
//import eu.compassresearch.core.interpreter.api.transitions.InputParameter;
//import eu.compassresearch.core.interpreter.api.transitions.InternalTransition;
//import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
//import eu.compassresearch.core.interpreter.api.transitions.OutputParameter;
//import eu.compassresearch.core.interpreter.api.transitions.SignalParameter;
//import eu.compassresearch.core.interpreter.api.values.ActionValue;
//import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
//import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;
//import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
//import eu.compassresearch.core.interpreter.utility.Pair;
///**
// * This class inspects the immediate alphabet of a CmlBehavior
// * @author akm
// *
// */
//@SuppressWarnings("serial")
//class AlphabetInspectVisitor
//		extends
//		QuestionAnswerCMLAdaptor<Context, Inspection> {
//
//	// The process that contains this instance
//	private final CmlBehaviour 					owner;
//	private final CmlExpressionVisitor			cmlExpressionVisitor = new CmlExpressionVisitor();
//	/**
//	 * Interface that gives access to methods that access protected parts of a CmlBehaviour
//	 */
//	private VisitorAccess 						visitorAccess;
//	/**
//	 * 
//	 * @param ownerProcess
//	 */
//	public AlphabetInspectVisitor(CmlBehaviour ownerProcess, VisitorAccess visitorAccess)
//	{
//		this.owner = ownerProcess;
//		this.visitorAccess = visitorAccess;
//	}
//	
//	/**
//	 * Common Inspection
//	 */
//
//	/**
//	 * Common Helpers
//	 */
//	
//	private CmlAlphabet createSilentTransition(INode srcNode, INode dstNode, String transitionText)
//	{
//		return new CmlAlphabet(new CmlTock(owner),new InternalTransition(owner,srcNode,dstNode,transitionText));
//	}
//	
//	private CmlAlphabet createSilentTransition(INode srcNode, INode dstNode)
//	{
//		return createSilentTransition(srcNode,dstNode,null);
//	}
//	
//	protected Inspection newInspection(CmlAlphabet transitions,CmlCalculationStep step)
//	{
//		return new Inspection(new CmlTrace(owner.getTraceModel()), transitions,step);
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * Used for evaluating post conditions
//	 * @see org.overture.ast.analysis.QuestionAnswerAdaptor#defaultPExp(org.overture.ast.expressions.PExp, java.lang.Object)
//	 */
//	
//	@Override
//	public Inspection defaultPExp(final PExp node, final Context question)
//			throws AnalysisException {
//		
//		CmlCalculationStep step = new AbstractCalculationStep(owner,visitorAccess) {
//			
//			@Override
//			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//					throws AnalysisException {
//				
//				if(!node.apply(cmlExpressionVisitor,question).boolValue(question))
//				{
//					throw new ValueException(4061, question.prepostMsg, question);
//				}
//				return new Pair<INode, Context>(new ASkipAction(), question);
//			}
//		};
//		
//		return newInspection(createSilentTransition(node,null,"Post condition"),step);
//	}
//	
//	protected Pair<INode,Context> caseReferenceAction(ILexLocation location,
//			List<PExp> args,ActionValue actionValue,Context question) throws AnalysisException {
//
//			//evaluate all the arguments
//			NameValuePairMap evaluatedArgs = new NameValuePairMap();
//
//			int paramIndex = 0;
//			for(PParametrisation parameterization : actionValue.getActionDefinition().getDeclarations())
//			{
//				for(ILexIdentifierToken id : parameterization.getDeclaration().getIdentifiers())
//				{
//					//get and evaluate the i'th expression
//					PExp arg = args.get(paramIndex);
//					Value value = arg.apply(cmlExpressionVisitor,question);
//
//					//check whether the type is correct
//					//if(arg.getType().equals(o))
//					//error(node,"Arguments does not match the action parameterization");
//
//					//Decide whether the argument is updateable or not
//					if(parameterization instanceof AValParametrisation)
//						value = value.getConstant();
//					else {
//						value = value.getUpdatable(null);
//					}
//
//					evaluatedArgs.put(new LexNameToken("",(ILexIdentifierToken)id.clone()), value);
//
//					//update the index
//					paramIndex++;
//				}
//
//			}
//
//			Context refActionContext = CmlContextFactory.newContext(location, 
//					"Parametrised reference action context", question);
//
//			refActionContext.putAll(evaluatedArgs);
//
//			return new Pair<INode,Context>(actionValue.getActionDefinition().getAction(), refActionContext); 
//		}
//	
//	@Override
//	public Inspection caseACallStatementAction(final ACallStatementAction node,
//			final Context question) throws AnalysisException {
//		if(!owner.hasChildren())
//		{
//			return newInspection(createSilentTransition(node,node,"begin"),
//					new AbstractCalculationStep(owner,visitorAccess) {
//
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//						throws AnalysisException {
//
//					Value value = question.lookup(node.getName()); 
//
//					if(value instanceof CmlOperationValue)
//						//return node.apply(statementEvalVisitor,question);
//						return null;
//					else if (value instanceof ActionValue)
//					{
//						//first find the action value in the context
//						ActionValue actionVal = (ActionValue)question.check(node.getName());
//
//						return caseReferenceAction(node.getLocation(),node.getArgs(), actionVal, question);
//					}
//					else
//						throw new InterpreterRuntimeException(InterpretationErrorMessages.FATAL_ERROR.customizeMessage());
//				}
//			});
//		}
//		
//		if(!owner.getLeftChild().finished())
//		{
//			return newInspection(owner.getLeftChild().inspect(),
//					new AbstractCalculationStep(owner,visitorAccess) {
//
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//						throws AnalysisException {
//
//
//				}
//			});
//		}
//		else if(owner.getRightChild() != null)
//		{
//			return newInspection(owner.getRightChild().inspect(),
//					new AbstractCalculationStep(owner,visitorAccess) {
//
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//						throws AnalysisException {
//
//
//				}
//			});
//		}
//		else
//		{
//			return newInspection(createSilentTransition(node,new ASkipAction()),
//					new AbstractCalculationStep(owner,visitorAccess) {
//
//						@Override
//						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//								throws AnalysisException {
//
//
//						}
//					});
//		}
//	}
//	
//	/**
//	 * Internal choice
//	 */
//	
//	@Override
//	public Inspection caseAInternalChoiceAction(AInternalChoiceAction node,
//			Context question) throws AnalysisException {
//
//		return createSilentTransition(node,node.getLeft());
//	}
//	
//	@Override
//	public Inspection caseAInternalChoiceProcess(AInternalChoiceProcess node,
//			Context question) throws AnalysisException {
//
//		return createSilentTransition(node,node.getLeft());
//	}
//
//	/**
//	 * Parallel action
//	 * 
//	 * In general all the parallel action have three transition rules that can be invoked
//	 * Parallel Begin:
//	 * 	At this step the interleaving action are not yet created. So this will be a silent (tau) transition
//	 * 	where they will be created and started. So the alphabet returned here is {tau}
//	 * 
//	 * Parallel Sync/Non-sync:
//	 * 
//	 * Parallel End:
//	 *  At this step both child actions are in the FINISHED state and they will be removed from the running process network
//	 *  and this will make a silent transition into Skip. So the alphabet returned here is {tau}
//	 */
//	
//	/**
//	 * 
//	 * Private common helpers for Generalised Parallelism
//	 *
//	 */
//	private interface ParallelAction
//	{
//		public CmlAlphabet inspectChildren() throws AnalysisException;
//	}
//	
//	private CmlAlphabet caseAGeneralisedParallelismInspectChildren(PVarsetExpression channelsetExp, Context question) throws AnalysisException
//	{
//		//convert the channel set of the current node to a alphabet
//		CmlAlphabet cs =  ((CmlAlphabet)channelsetExp.apply(cmlExpressionVisitor,question)).union(new CmlTock());
//		
//		//Get all the child alphabets and add the events that are not in the channelset
//		CmlBehaviour leftChild = owner.getLeftChild();
//		CmlAlphabet leftChildAlphabet = leftChild.inspect();
//		CmlBehaviour rightChild = owner.getRightChild();
//		CmlAlphabet rightChildAlphabet = rightChild.inspect();
//		
//		//Find the intersection between the child alphabets and the channel set and join them.
//		//Then if both left and right have them the next step will combine them.
//		CmlAlphabet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));
//		
//		//combine all the common events that are in the channel set 
//		Set<CmlTransition> syncEvents = new HashSet<CmlTransition>();
//		for(ObservableEvent ref : cs.getObservableEvents())
//		{
//			CmlAlphabet commonEvents = syncAlpha.intersectImprecise(ref.getAsAlphabet());
//			if(commonEvents.getObservableEvents().size() == 2)
//			{
//				Iterator<ObservableEvent> it = commonEvents.getObservableEvents().iterator(); 
//				syncEvents.add( it.next().synchronizeWith(it.next()));
//			}
//		}
//		
//		/*
//		 *	Finally we create the returned alphabet by joining all the 
//		 *  Synchronized events together with all the event of the children 
//		 *  that are not in the channel set.
//		 */
//		CmlAlphabet resultAlpha = new CmlAlphabet(syncEvents).union(leftChildAlphabet.subtractImprecise(cs));
//		resultAlpha = resultAlpha.union(rightChildAlphabet.subtractImprecise(cs));
//		
//		return resultAlpha;
//	}
//	
//	public Inspection caseParallelAction(INode node, Context question,ParallelAction parallelAction)
//			throws AnalysisException {
//		
//		Inspection alpha = null;
//		
//		//If there are no children or the children has finished, then either the interleaving 
//		//is beginning or ending and we make a silent transition.
//		if(!owner.hasChildren())
//		{
//			alpha = createSilentTransition(node, node, "Begin");
//		}
//		else if(CmlBehaviourUtility.isAllChildrenFinished(owner))
//		{
//			alpha = createSilentTransition(node, new ASkipAction(), "End");
//		}
//		else
//		//if we are here at least one of the children is alive and we must inspect them
//		//and forward it.
//		{
//			alpha = newInspection(parallelAction.inspectChildren());
//		}
//		
//		return alpha;
//	}
//	
//	/**
//	 *  Generalised parallelism 
//	 *  
//	 *  This has three parts:
//	 * 
//	 * Parallel Begin: As the general case
//	 * 
//	 * Parallel sync/Non-sync:
//	 * 	At this step, the actions are each executed separately. Here the channel set will determine whether a
//	 *  Sync or non-sync transition occurs. The alphabet returned here is {alpha(left) union alpha(right)}
//	 * 
//	 * Parallel End: As the general case
//	 *  
//	 */
//	@Override
//	public Inspection caseAGeneralisedParallelismParallelAction(
//			AGeneralisedParallelismParallelAction node, Context question)
//					throws AnalysisException {
//
//		final AGeneralisedParallelismParallelAction internalNode = node;
//		final Context internalQuestion = question;
//		
//		return caseParallelAction(node,question,new ParallelAction()
//		{
//			@Override
//			public CmlAlphabet inspectChildren() throws AnalysisException{
//				
//				return caseAGeneralisedParallelismInspectChildren(internalNode.getChansetExpression(),internalQuestion);
//			}
//		});
//	}
//	
//	@Override
//	public Inspection caseAGeneralisedParallelismProcess(
//			final AGeneralisedParallelismProcess node, final Context question)
//			throws AnalysisException {
//
//		return caseParallelAction(node,question,new ParallelAction()
//		{
//			@Override
//			public CmlAlphabet inspectChildren() throws AnalysisException{
//				
//				return caseAGeneralisedParallelismInspectChildren(node.getChansetExpression(),question);
//			}
//		});
//	}
//	
//	/**
//	 * This returns the alphabet of a interleaving action/process. 
//	 * 
//	 * This has three parts:
//	 * 
//	 * Parallel Begin: As the general case
//	 * 
//	 * Parallel Non-sync:
//	 * 	At this step the actions are each executed separately. Since no sync shall takes place this Action just wait
//	 * 	for the child actions to be in the FINISHED state. So the alphabet returned here is {alpha(left) union alpha(right)}
//	 * 
//	 * Parallel End: As the general case
//	 * 
//	 */
//	
//	@Override
//	public Inspection caseAInterleavingParallelAction(
//			AInterleavingParallelAction node, Context question)
//			throws AnalysisException {
//		
//		return caseAInterleavingParallel(node,question);
//	}
//	
//	@Override
//	public Inspection caseAInterleavingProcess(AInterleavingProcess node,
//			Context question) throws AnalysisException {
//		
//		return caseAInterleavingParallel(node,question);
//	}
//	
//	private Inspection caseAInterleavingParallel(INode node, Context question) throws AnalysisException 
//	{
//		return caseParallelAction(node,question,new ParallelAction()
//		{
//			@Override
//			public CmlAlphabet inspectChildren() {
//
//				return syncOnTockAndJoinChildren();
//			}
//		});
//	}
//	
//	/**
//	 * External Choice section 7.5.4 D23.2
//	 * 
//	 * In terms of the alphabet, we have the following situations:
//	 * 
//	 *  External Choice Begin:
//	 *  When no children exists, the External Choice Begin transition rule must be executed.
//	 *  This is a silent transition and therefore the alphabet contains only tau event
//	 *  
//	 *  External Choice Silent:
//	 *  If any of the actions can take a silent transition they will do it before getting here again. 
//	 *  We therefore don't take this situation into account
//	 *  
//	 *  External Choice Skip:
//	 *  If one the children is Skip we make a silent transition of the whole choice into skip.
//	 *  We therefore just return the tau event
//	 *  
//	 *  External Choice End:
//	 *  The alphabet contains an observable event for every child that can engaged in one.
//	 *  
//	 */
//	@Override
//	public Inspection caseAExternalChoiceAction(AExternalChoiceAction node,
//			Context question) throws AnalysisException {
//
//		return caseAExternalChoice(node,question);
//	}
//	
//	@Override
//	public Inspection caseAExternalChoiceProcess(AExternalChoiceProcess node,
//			Context question) throws AnalysisException {
//
//		return caseAExternalChoice(node,question);
//	}
//	
//	private CmlAlphabet syncOnTockAndJoinChildren()
//	{
//		//even though they are external choice/interleaving they should always sync on tock
//		CmlAlphabet cs =  new CmlAlphabet(new CmlTock());
//		
//		//Get all the child alphabets 
//		CmlAlphabet leftChildAlphabet = owner.getLeftChild().inspect();
//		CmlAlphabet rightChildAlphabet = owner.getRightChild().inspect();
//		
//		//Find the intersection between the child alphabets and the channel set and join them.
//		//Then if both left and right have them the next step will combine them.
//		CmlAlphabet syncAlpha = leftChildAlphabet.intersectImprecise(cs).union(rightChildAlphabet.intersectImprecise(cs));
//		
//		//combine all the tock events 
//		if(syncAlpha.getObservableEvents().size() == 2)
//		{
//			Iterator<ObservableEvent> it = syncAlpha.getObservableEvents().iterator(); 
//			return leftChildAlphabet.union(rightChildAlphabet).subtract(syncAlpha).union(it.next().synchronizeWith(it.next()));
//		}
//		else
//		{
//			return leftChildAlphabet.union(rightChildAlphabet);
//		}
//	}
//	
//	private Inspection caseAExternalChoice(INode node,
//			Context question) throws AnalysisException {
//		
//		Inspection alpha = null;
//		
//		//if no children are present we make a silent transition to represent the
//		//external choice begin
//		if(!owner.hasChildren())
//			alpha = createSilentTransition(node, node,"Begin");
//		//if one child is finished external choice must end
//		else if (CmlBehaviourUtility.finishedChildExists(owner))
//			alpha = createSilentTransition(node, node,"end");
//		//else we join the childrens alphabets 
//		else
//		{
//			return newInspection(syncOnTockAndJoinChildren());
//		}
//		
//		return alpha;
//	}
//	
//	/*
//	 * Sequential composition
//	 */
//	
//	@Override
//	public Inspection caseASequentialCompositionAction(
//			ASequentialCompositionAction node, Context question)
//			throws AnalysisException {
//		
//		return caseASequentialComposition(node.getLeft(),node.getRight(),question);
//	}
//	
//	@Override
//	public Inspection caseASequentialCompositionProcess(
//			ASequentialCompositionProcess node, Context question)
//			throws AnalysisException {
//		
//		return caseASequentialComposition(node.getLeft(),node.getRight(),question);
//	}
//	
//	/**
//	 * Common handler method  of sequential composition for both action and processes
//	 * @param node The sequential composition node
//	 * @param leftNode The left child of the sequential composition 
//	 * @param question
//	 * @return
//	 * @throws AnalysisException
//	 */
//	private Inspection caseASequentialComposition(
//			final INode node, INode leftNode, final Context question)
//			throws AnalysisException {
//		
//		//If the left child of the sequential composition is 
//		//not terminated then we return the alphabet of that 
//		if(!owner.getLeftChild().finished())
//		{
//			CmlCalculationStep step = new AbstractCalculationStep(owner,null) {
//				
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve) throws AnalysisException {
//					
//					owner.getLeftChild().execute(sve);
//					return new Pair<INode, Context>(node, question);
//				}
//			};
//			
//			return newInspection(owner.getLeftChild().inspect());
//		}
//		else
//		{
////			CmlCalculationStep step = new AbstractCalculationStep(owner,null) {
////				
////				@Override
////				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve) throws AnalysisException {
////					
////					setLeftChild(null);
////					setRightChild(null);	
////					
////					return new Pair<INode, Context>(rightNode,question);
////				}
////			};
//			//If the left child is terminated, we make a silent transition into the right child
//			return createSilentTransition(node,leftNode);
//		}
//	}
//	
//	
//	/**
//	 * Reference action/process
//	 */
//	
//	@Override
//	public Inspection caseAReferenceAction(final AReferenceAction node,
//			final Context question) throws AnalysisException {
//		
//		ActionValue actionValue = (ActionValue)question.check(node.getName()).deref();
//		
//		return createSilentTransition(node,actionValue.getActionDefinition().getAction());
//	}
//	
//	@Override
//	public Inspection caseAReferenceProcess(AReferenceProcess node,
//			Context question) throws AnalysisException {
//		
//		ProcessObjectValue processValue = (ProcessObjectValue)question.lookup(node.getProcessName());
//		
//		return createSilentTransition(node,processValue.getProcessDefinition().getProcess());
//	}
//	
//	
//	/**
//	 * PAction Inspection
//	 */
//
//	@Override
//	public Inspection defaultPAction(PAction node, Context question)
//			throws AnalysisException {
//
//		return createSilentTransition(node,null);
//	}
//
//	
//	/**
//	 * Block Statement Action
//	 */
//	@Override
//	public Inspection caseABlockStatementAction(ABlockStatementAction node,
//			Context question) throws AnalysisException {
//
//		return createSilentTransition(node,node.getAction());
//	}
//	
//	@Override
//	public Inspection caseASkipAction(ASkipAction node, Context question)
//			throws AnalysisException {
//		if(owner.finished())
//			return newInspection(new CmlAlphabet(new CmlTock(owner)));
//		else
//			return defaultPAction(node,question);
//	}
//	
//	/**
//	 * Stop Action
//	 */
//	@Override
//	public Inspection caseAStopAction(AStopAction node, Context question)
//			throws AnalysisException {
//		//return the empty alphabet
//		return newInspection(new CmlAlphabet(new CmlTock(owner)));
//	}
//	
//	/**
//	 * Communication Action 
//	 */
//	@Override
//	public Inspection caseACommunicationAction(ACommunicationAction node,
//			Context question) throws AnalysisException {
//
//		//FIXME: This should be a name so the conversion is avoided
//		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier().getName(),node.getIdentifier().getLocation(),false,true);
//		//find the channel value
//		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);
//		
//		Set<CmlTransition> comset = new HashSet<CmlTransition>();
//		
//		//if there are no com params then we have a prefix event
//		if(node.getCommunicationParameters().isEmpty())
//		{
//			comset.add(CmlTransitionFactory.newSynchronizationEvent(owner, chanValue));
//		}
//		//otherwise we convert the com params
//		else
//		{
//			List<CommunicationParameter> params = new LinkedList<CommunicationParameter>();
//			for(PCommunicationParameter p : node.getCommunicationParameters())
//			{
//				CommunicationParameter param = null;
//				if(p instanceof ASignalCommunicationParameter)
//				{
//					ASignalCommunicationParameter signal = (ASignalCommunicationParameter)p;
//					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
//					param = new SignalParameter((ASignalCommunicationParameter)p, valueExp);
//				}
//				else if(p instanceof AWriteCommunicationParameter)
//				{
//					AWriteCommunicationParameter signal = (AWriteCommunicationParameter)p;
//					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
//					param = new OutputParameter((AWriteCommunicationParameter)p, valueExp);
//				}
//				else if(p instanceof AReadCommunicationParameter)
//				{
//					//TODO: At this point the 'in set exp' is not supported
//					AReadCommunicationParameter readParam = (AReadCommunicationParameter)p;
//					param = new InputParameter(readParam);
//				}
//				
//				params.add(param);
//			}
//			
//			ObservableEvent observableEvent = CmlTransitionFactory.newCmlCommunicationEvent(owner, chanValue, params);
//			comset.add(observableEvent);
//		}
//		//TODO: do the rest here
//		
//		return newInspection(new CmlAlphabet(comset).union(new CmlTock(owner)));
//	}
//	
//	/**
//	 * State-based Choice - section 7.5.5 D23.2
//	 * Guard
//	 * Guarded actions are stuck, unless the guard is true.
//	 * So here we return the silent event tau, if the expression evaluates 
//	 * to true in the current state else the empty alphabet
//	 */
//	@Override
//	public Inspection caseAGuardedAction(AGuardedAction node, Context question)
//			throws AnalysisException {
//
//		//First we evaluate the guard expression
//		Value guardExp = node.getExpression().apply(cmlExpressionVisitor,question);
//		
//		//if the gaurd is true then we return the silent transition to the guarded action
//		if(guardExp.boolValue(question))
//			return createSilentTransition(node, node.getAction());
//		//else we return the empty alphabet since no transition is possible
//		else
//			return newInspection(new CmlAlphabet(new CmlTock(owner)));
//		
//	}
//	
//	/**
//	 * Hiding - Defined in section 7.5.8 D23.2
//	 * 
//	 * The alphabet for hiding determined by the given channel set. If an event
//	 * is in it then a hidden transition will be made, if not then it is observable.
//	 * 
//	 * This is handled by giving each behaviourThread a hiding alphabet that when 
//	 * inspected converts all the hidden observable event into silent. So this method 
//	 */
//	
//	@Override
//	public Inspection caseAHidingAction(AHidingAction node, Context question)
//			throws AnalysisException {
//
//		if(!owner.getLeftChild().finished())
//		{
//			CmlAlphabet hidingAlpha = (CmlAlphabet)node.getChansetExpression().apply(cmlExpressionVisitor,question);
//
//			CmlAlphabet alpha = owner.getLeftChild().inspect();
//
//			CmlAlphabet hiddenEvents = alpha.intersect(hidingAlpha);
//
//			CmlAlphabet resultAlpha = alpha.subtract(hiddenEvents);
//
//			for(ObservableEvent obsEvent : hiddenEvents.getObservableEvents())
//				if(obsEvent instanceof ChannelEvent)
//					resultAlpha = resultAlpha.union(new HiddenEvent(owner,(ChannelEvent)obsEvent));	
//
//			return newInspection(resultAlpha);
//		}
//		else
//			return createSilentTransition(node, new ASkipAction());
//		
//		//FIXME This is actually not a tau transition. This should produced an entirely 
//		//different event which has no denotational trace but only for debugging
//		//return createSilentTransition(node, node.getLeft(), "Hiding (This should not be a tau)");
//	}
//	
//	/**
//	 * Non deterministic if randomly chooses between options whose expression are evaluated to true
//	 */
//	@Override
//	public Inspection caseANonDeterministicIfStatementAction(
//			ANonDeterministicIfStatementAction node, Context question)
//			throws AnalysisException {
//
//		int availCount = ActionVisitorHelper.findAllTrueAlternatives(
//				node.getAlternatives(),question,cmlExpressionVisitor).size();
//		
//		if(availCount > 0)
//			//FIXME this should point to the choosen action node
//			return createSilentTransition(node, null);
//		else
//			//were stuck so return empty alphabet
//			//FIXME actually this diverges
//			return newInspection(new CmlAlphabet());
//	}
//	
//	@Override
//	public Inspection caseANonDeterministicDoStatementAction(
//			ANonDeterministicDoStatementAction node, Context question)
//			throws AnalysisException {
//
//		int availCount = ActionVisitorHelper.findAllTrueAlternatives(
//				node.getAlternatives(),question,cmlExpressionVisitor).size();
//		
//		if(availCount > 0)
//			//FIXME this should point to the choosen action node
//			return createSilentTransition(node, null);
//		else
//			return createSilentTransition(node, new ASkipAction());
//	}
//	
//	@Override
//	public Inspection caseAWhileStatementAction(AWhileStatementAction node,
//			Context question) throws AnalysisException {
//		
//		if(node.getCondition().apply(cmlExpressionVisitor,question).boolValue(question))
//		{
//			//FIXME this should point to the choosen action node
//			return createSilentTransition(node, null);
//		}
//		else
//		{
//			return createSilentTransition(node, new ASkipAction());
//		}
//	}
//	
//	/**
//	 * Process inspection
//	 */
//	
//	/**
//	 * This creates a silent transition for all the processes that are not defined
//	 */
//	@Override
//	public Inspection defaultPProcess(PProcess node, Context question)
//			throws AnalysisException {
//		return createSilentTransition(node,null);
//	}
//	
//	@Override
//	public Inspection caseAActionProcess(AActionProcess node, Context question)
//			throws AnalysisException {
//		return createSilentTransition(node,node.getAction());
//	}
//	
//	@Override
//	public Inspection caseAWaitAction(AWaitAction node, Context question)
//			throws AnalysisException {
//		
//		//Evaluate the expression into a natural number
//		long val = node.getExpression().apply(cmlExpressionVisitor,question).natValue(question);
//		long nTocks = owner.getCurrentTime();
//		
//		//If the number of tocks exceeded val then we make a silent transition that ends the delay process
//		if( nTocks >= val)
//			return createSilentTransition(node, null,"Wait ended");
//		else
//		//If the number of tocks has not exceeded val then behave as Stop
//			return newInspection(new CmlAlphabet(new CmlTock(owner,nTocks-val)));
//	}
//	
//	@Override
//	public Inspection caseATimeoutAction(ATimeoutAction node, Context question)
//			throws AnalysisException {
//		
//		//Evaluate the expression into a natural number
//		long val = node.getTimeoutExpression().apply(cmlExpressionVisitor,question).natValue(question);
//		
//		//If the time exceeded val then we make a silent transition that ends the delay process
//		if(owner.getCurrentTime() >= val)
//			return createSilentTransition(node, null,"Timeout: time exceeded");
//		else if(owner.getLeftChild().finished())
//			return createSilentTransition(node, null,"Timeout: left behavior is finished");
//		else
//		//If time has not exceeded val then we offer the left process
//			return newInspection(owner.getLeftChild().inspect());
//		
//	}
//}
