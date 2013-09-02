package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AProductType;
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
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.ChannelEvent;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionFactory;
import eu.compassresearch.core.interpreter.api.transitions.CommunicationParameter;
import eu.compassresearch.core.interpreter.api.transitions.InputParameter;
import eu.compassresearch.core.interpreter.api.transitions.ObservableEvent;
import eu.compassresearch.core.interpreter.api.transitions.OutputParameter;
import eu.compassresearch.core.interpreter.api.transitions.SignalParameter;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.AnyValue;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class ActionInspectionVisitor extends CommonInspectionVisitor {

	private QuestionAnswerCMLAdaptor<Context, Inspection> statementInspectionVisitor;

	public ActionInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor) {
		super(ownerProcess, visitorAccess, parentVisitor);

		this.statementInspectionVisitor = new StatementInspectionVisitor(ownerProcess, visitorAccess, this);
	}

	/**
	 * Transition methods
	 */

	@Override
	public Inspection defaultSStatementAction(SStatementAction node,
			Context question) throws AnalysisException {

		return node.apply(statementInspectionVisitor,question);
	}

	@Override
	public Inspection defaultPAction(PAction node, Context question)
			throws AnalysisException {

		throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	/**
	 * This deals both with calls but also parametrised action reference, since the typechecker does not replace this node yet
	 * FIXME This might be changed! if the typechecker replaces the call node with a action reference node 
	 */
	@Override
	public Inspection caseACallStatementAction(
			final ACallStatementAction node, final Context question)
					throws AnalysisException {

		if(!owner.hasChildren())
		{
			final Value value = lookupName(node.getName(),question); 
			if(value instanceof CmlOperationValue)
				return node.apply(statementInspectionVisitor,question);
			else if (value instanceof ActionValue)
			{
				//first find the action value in the context
				final ActionValue actionVal = (ActionValue)value;

				return newInspection(createSilentTransition(actionVal.getActionDefinition().getAction(), null), 
						new AbstractCalculationStep(owner, visitorAccess) {

					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {

						return caseReferenceAction(node.getLocation(),node.getArgs(), actionVal, question);
					}
				});

			}
			else
				throw new InterpreterRuntimeException(InterpretationErrorMessages.FATAL_ERROR.customizeMessage());
		}
		else
		{
			return node.apply(statementInspectionVisitor,question); 
		}
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
	public Inspection caseACommunicationAction(
			final ACommunicationAction node, final Context question)
					throws AnalysisException {

		//FIXME: This should be a name so the conversion is avoided
		LexNameToken channelName = new LexNameToken("|CHANNELS|",node.getIdentifier().getName(),node.getIdentifier().getLocation(),false,true);
		//find the channel value
		CMLChannelValue chanValue = (CMLChannelValue)question.lookup(channelName);

		Set<CmlTransition> comset = new HashSet<CmlTransition>();

		//if there are no com params then we have a prefix event
		if(node.getCommunicationParameters().isEmpty())
		{
			comset.add(CmlTransitionFactory.newSynchronizationEvent(owner, chanValue));
		}
		//otherwise we convert the com params
		else
		{
			List<CommunicationParameter> params = new LinkedList<CommunicationParameter>();
			int comParamSize = node.getCommunicationParameters().size(); 
			for(int i = 0;i < comParamSize; i++)
			{
				PCommunicationParameter p = node.getCommunicationParameters().get(i);
				
				CommunicationParameter param = null;
				if(p instanceof ASignalCommunicationParameter)
				{
					ASignalCommunicationParameter signal = (ASignalCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
					param = new SignalParameter((ASignalCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AWriteCommunicationParameter)
				{
					AWriteCommunicationParameter signal = (AWriteCommunicationParameter)p;
					Value valueExp = signal.getExpression().apply(cmlExpressionVisitor,question);
					param = new OutputParameter((AWriteCommunicationParameter)p, valueExp);
				}
				else if(p instanceof AReadCommunicationParameter)
				{
					AReadCommunicationParameter readParam = (AReadCommunicationParameter)p;
					AnyValue val = null;
					if(comParamSize > 1)
					{
						//Must be a product type
						AProductType productType = (AProductType)((AChannelType)chanValue.getType()).getType();
						val = new AnyValue(productType.getTypes().get(i));
					}
					else
						val = new AnyValue(((AChannelType)chanValue.getType()).getType());
					
					Context constraintContext = CmlContextFactory.newContext(p.getLocation(),"Constraint evaluation context", question);
					param = new InputParameter(readParam,val,constraintContext);
				}

				params.add(param);
			}

			ObservableEvent observableEvent = CmlTransitionFactory.newCmlCommunicationEvent(owner, chanValue, params);
			comset.add(observableEvent);
		}
		//TODO: do the rest here

		return newInspection(new CmlAlphabet(comset).union(new CmlTock(owner)),
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				//At this point the supervisor has already given go to the event, or the event is hidden
				Value value = ((ChannelEvent)sve.selectedObservableEvent()).getValue();

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
								ILexNameToken name = ((AIdentifierPattern) pattern).getName();
								question.put(name, value);
							}

						}
					}
				}
				return new Pair<INode,Context>(node.getAction(), question);
			}
		});
	}

	/**
	 * External Choice section 7.5.4 D23.2
	 * 
	 * In terms of the alphabet, we have the following situations:
	 * 
	 *  External Choice Begin:
	 *  When no children exists, the External Choice Begin transition rule must be executed.
	 *  This is a silent transition and therefore the alphabet contains only tau event
	 *  
	 *  External Choice Silent:
	 *  If any of the actions can take a silent transition they will do it before getting here again. 
	 *  We therefore don't take this situation into account
	 *  
	 *  External Choice Skip:
	 *  If one the children is Skip we make a silent transition of the whole choice into skip.
	 *  We therefore just return the tau event
	 *  
	 *  External Choice End:
	 *  The alphabet contains an observable event for every child that can engaged in one.
	 *  
	 */
	@Override
	public Inspection caseAExternalChoiceAction(
			AExternalChoiceAction node, Context question)
					throws AnalysisException {

		return caseAExternalChoice(node,node.getLeft(),node.getRight(), question);
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
	public Inspection caseAInterleavingParallelAction(
			final AInterleavingParallelAction node, final Context question)
					throws AnalysisException {

		//TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"

		//if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if(!owner.hasChildren()){

			return newInspection(createSilentTransition(node, node, "Begin"),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {

					caseParallelBegin(node,question);
					//We push the current state, since this process will control the child processes created by it
					return new Pair<INode,Context>(node, question);
				}
			});

		}
		//the process has children and must now handle either termination or event sync
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			return newInspection(createSilentTransition(node, new ASkipAction(), "End"),caseParallelEnd(question));
		}
		else
		{
			return newInspection(syncOnTockAndJoinChildren(),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					//At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync 
					caseParallelNonSync();
					//We push the current state, 
					return new Pair<INode,Context>(node, question);
				}
			});
		}
	}

	/**
	 * internal choice - section 7.5.3 D23.2
	 * 
	 * An internal choice between two actions can evolve via a tau event into either of them
	 */
	@Override
	public Inspection caseAInternalChoiceAction(
			final AInternalChoiceAction node, final Context question)
					throws AnalysisException {

		final int rndChoice = this.rnd.nextInt(2);
		INode tmpNode = null;
		Context tmpContext = null;
		if(rndChoice == 0)
		{
			tmpNode = node.getLeft();
			tmpContext = this.visitorAccess.getChildContexts(question).first; 
		}
		else
		{
			tmpNode = node.getRight();
			tmpContext = this.visitorAccess.getChildContexts(question).second;
		}
		
		final INode nextNode = tmpNode;
		final Context nextContext = tmpContext;
		
		return newInspection(createSilentTransition(node,nextNode), 
				new AbstractCalculationStep(owner, visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				return new Pair<INode,Context>(nextNode, nextContext);
			}
		}); 
	}

	/**
	 * Recursion - section 7.5.9
	 * TODO Mutually recursive processes are not implemented yet
	 */
	@Override
	public Inspection caseAMuAction(final AMuAction node, final Context question)
			throws AnalysisException {

		return newInspection(createSilentTransition(node, null), 
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				///THIS IS NOT CORRECT sEMANTICALLY, 
				Context muContext = CmlContextFactory.newContext(node.getLocation(), "mu context", question);

				NameValuePairList nvpl = new NameValuePairList();

				Pair<INode,Context> res = null;

				for(int i = 0 ; i < node.getIdentifiers().size() ; i++)
				{
					ILexIdentifierToken id = node.getIdentifiers().get(i);

					ILexNameToken name = new LexNameToken("", id);

					PAction action = node.getActions().get(i);

					AActionDefinition actionDef = new AActionDefinition(node.getLocation(),
							NameScope.LOCAL,true,null,Pass.DEFS,null,action);

					nvpl.add(new NameValuePair(name, 
							new ActionValue(actionDef)));
					if(i == 0)
						res = new Pair<INode,Context>(action, muContext);
				}

				muContext.putAllNew(nvpl);

				return res;
			}
		});
	}

	private void caseParallelBegin(SParallelAction node, Context question) throws AnalysisException
	{
		PAction left = node.getLeftAction();
		PAction right = node.getRightAction();
		Pair<Context,Context> childContexts = visitorAccess.getChildContexts(question);
		CmlBehaviour leftInstance = 
				new ConcreteCmlBehaviour(left, childContexts.first.deepCopy(), 
						new LexNameToken(owner.name().getModule(),owner.name().getIdentifier().getName() + "|||" ,left.getLocation()),owner);

		CmlBehaviour rightInstance = 
				new ConcreteCmlBehaviour(right, childContexts.second.deepCopy(), 
						new LexNameToken(owner.name().getModule(),"|||" + owner.name().getIdentifier().getName(),right.getLocation()),owner);

		//add the children to the process graph
		visitorAccess.setLeftChild(leftInstance);
		visitorAccess.setRightChild(rightInstance);
	}

	/**
	 * State-based Choice - section 7.5.5 D23.2
	 * Guard
	 * Guarded actions are stuck, unless the guard is true.
	 * So If we ever execute this transition, the guard expression would already
	 * have been checked for being true.
	 */
	@Override
	public Inspection caseAGuardedAction(final AGuardedAction node,
			final Context question) throws AnalysisException {

		//First we evaluate the guard expression
		Value guardExp = node.getExpression().apply(cmlExpressionVisitor,question);

		CmlAlphabet alpha = null;

		//if the gaurd is true then we return the silent transition to the guarded action
		if(guardExp.boolValue(question))
			alpha = createSilentTransition(node, node.getAction());
		//else we return the empty alphabet since no transition is possible
		else
			alpha = new CmlAlphabet(new CmlTock(owner));

		return newInspection(alpha, 
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				return new Pair<INode,Context>(node.getAction(), question); 
			}
		});
	}

	/**
	 * Hiding - section 7.5.8 D23.2
	 * syntax : Action \\ channelsetExpression
	 */
	@Override
	public Inspection caseAHidingAction(final AHidingAction node,
			final Context question) throws AnalysisException {
		return caseHiding(node,node.getChansetExpression(),question);
//		//We do the hiding behavior as long as the Action is not terminated
//		if(!owner.getLeftChild().finished())
//		{
//			//first we convert the channelset expression into a Cmlalpabet
//			CmlAlphabet hidingAlpha = (CmlAlphabet)node.getChansetExpression().apply(cmlExpressionVisitor,question);
//			//next we inspect the action to get the current available transitions
//			CmlAlphabet alpha = owner.getLeftChild().inspect();
//			//Intersect the two to find which transitions should be converted to silents transitions
//			CmlAlphabet hiddenEvents = alpha.intersect(hidingAlpha);
//			//remove the events that has to be silent
//			CmlAlphabet resultAlpha = alpha.subtract(hiddenEvents);
//			//convert them into silent events and add the again
//			for(ObservableEvent obsEvent : hiddenEvents.getObservableEvents())
//				if(obsEvent instanceof ChannelEvent)
//					resultAlpha = resultAlpha.union(new HiddenEvent(owner,(ChannelEvent)obsEvent));	
//
//			return newInspection(resultAlpha,
//					new AbstractCalculationStep(owner, visitorAccess) {
//
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//						throws AnalysisException {
//					owner.getLeftChild().execute(supervisor());
//					return new Pair<INode,Context>(node, question);
//				}
//			});
//		}
//		//If the Action is terminated then it evolves into Skip
//		else
//			return newInspection(createSilentTransition(node, new ASkipAction()),
//					new AbstractCalculationStep(owner, visitorAccess) {
//
//				@Override
//				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
//						throws AnalysisException {
//					setLeftChild(null);
//					return new Pair<INode,Context>(new ASkipAction(), question);
//				}
//			});
	}

	/**
	 * Generalised Parallelism
	 * A [| cs |] B (no state) 
	 * 
	 * or
	 * 
	 * A [| ns1 | cs | ns2 |] B 
	 */
	@Override
	public Inspection caseAGeneralisedParallelismParallelAction(
			final AGeneralisedParallelismParallelAction node, final Context question)
					throws AnalysisException {

		return caseGeneralisedParallelismParallel(node,new parallelCompositionHelper() {

			@Override
			public void caseParallelBegin() throws AnalysisException {
				ActionInspectionVisitor.this.caseParallelBegin(node, question);
			}
		}, node.getChansetExpression(),question);
	}

	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. 
	 */
	@Override
	public Inspection caseAReferenceAction(final AReferenceAction node,
			final Context question) throws AnalysisException {
		//FIXME: the scoping is not correct, this should be done as described in the transition rule

		//FIXME: Consider: Instead of this might create a child process, and behave as this child until it terminates
		//CMLActionInstance refchild = new CMLActionInstance(node.getActionDefinition().getAction(), question, node.getName()); 
		final ActionValue actionValue = (ActionValue)question.check(node.getName()).deref();

		return newInspection(createSilentTransition(node,actionValue.getActionDefinition().getAction()),
				new AbstractCalculationStep(owner, visitorAccess) {

			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				return caseReferenceAction(node.getLocation(),node.getArgs(), actionValue, question);
			}
		});
	}

	protected Pair<INode,Context> caseReferenceAction(ILexLocation location,
			List<PExp> args,ActionValue actionValue,Context question) throws AnalysisException {

		//evaluate all the arguments
		NameValuePairMap evaluatedArgs = new NameValuePairMap();

		int paramIndex = 0;
		for(PParametrisation parameterization : actionValue.getActionDefinition().getDeclarations())
		{
			for(ILexIdentifierToken id : parameterization.getDeclaration().getIdentifiers())
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

				evaluatedArgs.put(new LexNameToken("",(ILexIdentifierToken)id.clone()), value);

				//update the index
				paramIndex++;
			}

		}

		Context refActionContext = CmlContextFactory.newContext(location, 
				"Parametrised reference action context", question);

		refActionContext.putAll(evaluatedArgs);

		return new Pair<INode,Context>(actionValue.getActionDefinition().getAction(), refActionContext); 
	}

	/**
	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
	 */
	@Override
	public Inspection caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
					throws AnalysisException {

		return caseASequentialComposition(node,node.getLeft(),node.getRight(),question);
	}

	@Override
	public Inspection caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException {

		//return the alphabet only containing tock since Skip allows for time to pass
		//return newInspection(new CmlAlphabet(new CmlTock(owner)),null);
		return newInspection(new CmlAlphabet(),null);
	}

	@Override
	public Inspection caseAStopAction(AStopAction node, Context question)
			throws AnalysisException {
		//return the alphabet only containing tock since Stop allows for time to pass
		return newInspection(new CmlAlphabet(new CmlTock(owner)),null);
	}
	
	@Override
	public Inspection caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node, Context question)
			throws AnalysisException {
		
		Context globalContext = question.getGlobal();
		List<ILexNameToken> channelNames = new LinkedList<ILexNameToken>();
		
		//Get all the channel objects
		for(Entry<ILexNameToken,Value> entry : globalContext.entrySet())
			if(entry.getValue() instanceof CMLChannelValue)
				channelNames.add(entry.getKey().clone());
				
		AFatEnumVarsetExpression varsetNode = new AFatEnumVarsetExpression(new LexLocation(), channelNames);
			
		AGeneralisedParallelismParallelAction nextNode = new AGeneralisedParallelismParallelAction(node.getLocation(), 
				node.getLeftAction().clone(),node.getLeftNamesetExpression(),node.getLeftNamesetExpression(), node.getRightAction().clone(),varsetNode);
		
		return caseAGeneralisedParallelismParallelAction(nextNode, question);
	}

	/**
	 * Timed actions
	 */

	@Override
	public Inspection caseATimeoutAction(final ATimeoutAction node,
			final Context question) throws AnalysisException {

		//Evaluate the expression into a natural number
		long val = node.getTimeoutExpression().apply(cmlExpressionVisitor,question).natValue(question);
		long startTimeVal = question.lookup(NamespaceUtility.getStartTimeName()).intValue(question);
		//If the left is Skip then the whole process becomes skip with the state of the left child
		if(owner.getLeftChild().finished())
		{
			return newInspection(createSilentTransition(node, owner.getLeftChild().getNextState().first,"Timeout: left behavior is finished"), 
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					CmlBehaviour leftChild = owner.getLeftChild();
					setLeftChild(null);
					return leftChild.getNextState();
				}
			});
		}
		//if the current time of the process has passed the limit (val) then process
		//behaves as the right process
		else if(owner.getCurrentTime() - startTimeVal >= val)
		{
			return newInspection(createSilentTransition(node, node.getRight(),"Timeout: time exceeded"), 
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					//We set the process to become the right behavior
					setLeftChild(null);
					//We need to return the outer context because of the extra context
					//containing the start time has been added in the setup visitor
					return new Pair<INode, Context>(node.getRight(), question.outer);
				}
			});

		}
		//if the current time of the process has not passed the limit (val) and the left process
		//makes an observable transition then the whole process behaves as the left process 
		else
		{
			final CmlBehaviour leftBehavior = owner.getLeftChild();
			return newInspection(leftBehavior.inspect(), 
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve) throws AnalysisException {
					
					leftBehavior.execute(supervisor());

					if(supervisor().selectedObservableEvent() instanceof ObservableEvent &&
						supervisor().selectedObservableEvent() instanceof ChannelEvent)
					{
						setLeftChild(null);
						return leftBehavior.getNextState();
					}
					else
						return new Pair<INode, Context>(node, question);
				}
			});


		}
	}

	@Override
	public Inspection caseAUntimedTimeoutAction(
			final AUntimedTimeoutAction node, final Context question)
					throws AnalysisException {

		//the alphabet still need to be calculated before this is done, so uncomment when done
		//If the left is Skip then the whole process becomes skip with the state of the left child
		if(owner.getLeftChild().finished())
		{
			return newInspection(createSilentTransition(node, owner.getLeftChild().getNextState().first),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					CmlBehaviour leftChild = owner.getLeftChild();
					setLeftChild(null);
					setRightChild(null);
					return leftChild.getNextState();
				}
			});
		}
		//Make a random decision whether the process should timeout and
		//behaves as the right process
		else if(this.rnd.nextBoolean())
		{
			return newInspection(createSilentTransition(node, node.getRight()), 
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					//We set the process to become the right behavior
					setLeftChild(null);
					return new Pair<INode, Context>(node.getRight(), question);
				}
			});
		}
		//if no timeout has occurred the whole process behaves as the left process
		else
		{
			return newInspection(owner.getLeftChild().inspect(), 
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					CmlBehaviour leftBehavior = owner.getLeftChild();
					owner.getLeftChild().execute(supervisor());

					if(supervisor().selectedObservableEvent() instanceof ObservableEvent &&
							supervisor().selectedObservableEvent() instanceof ChannelEvent)
					{
						setLeftChild(null);
						return new Pair<INode, Context>(leftBehavior.getNextState().first, leftBehavior.getNextState().second);
					}
					else
						return new Pair<INode, Context>(node, question);
				}
			});
		}
	}

	@Override
	public Inspection caseAWaitAction(AWaitAction node,
			final Context question) throws AnalysisException {

		//Evaluate the expression into a natural number
		long val = node.getExpression().apply(cmlExpressionVisitor,question).natValue(question);
		long startTime = question.lookup(NamespaceUtility.getStartTimeName()).intValue(question);
		long nTocks = owner.getCurrentTime() - startTime;
		
		//If the number of tocks exceeded val then we make a silent transition that ends the delay process
		if( nTocks >= val)
			return newInspection(createSilentTransition(node, new ASkipAction(),"Wait ended"),
					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					//We need to remove the added context from the setup visitor
					return new Pair<INode, Context>(new ASkipAction(), question.outer);
				}
			});
		else
			//If the number of tocks has not exceeded val then behave as Stop
			return newInspection(new CmlAlphabet(new CmlTock(owner,nTocks-val)), null);
	}
	/**
	 * Interrupt A /_\ B : The possible transitions from both A and B are exposed
	 * as long as A is not finished or an observable event from B occurs.
	 */
	@Override
	public Inspection caseAInterruptAction(final AInterruptAction node,
			final Context question) throws AnalysisException {
		if(owner.getLeftChild().finished())
		{
			return newInspection(createSilentTransition(node, owner.getLeftChild().getNextState().first) ,

					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					final Pair<INode,Context> state = owner.getLeftChild().getNextState();
					setLeftChild(null);
					setRightChild(null);
					return state;
				}
			});
		}
		else if(owner.getRightChild().getTraceModel().getLastTransition() instanceof ObservableEvent &&
				owner.getRightChild().getTraceModel().getLastTransition() instanceof ChannelEvent)
		{
			return newInspection(createSilentTransition(node, owner.getRightChild().getNextState().first) ,

					new AbstractCalculationStep(owner, visitorAccess) {

				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					final Pair<INode,Context> state = owner.getRightChild().getNextState();
					setLeftChild(null);
					setRightChild(null);
					return state;
				}
			});
		}
		else
		{
			return newInspection(syncOnTockAndJoinChildren(),

				new AbstractCalculationStep(owner, visitorAccess) {

					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {
						//setLeftChild(null);
						//setRightChild(null);
						caseParallelNonSync();
						return new Pair<INode, Context>(node, question);
					}
			});
		}
	}
}
