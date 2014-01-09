package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.ASkipStm;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.ACallAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
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
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.actions.SParallelAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionFactory;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.ActionValue;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;
import eu.compassresearch.core.interpreter.api.values.ExpressionConstraint;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.NamesetValue;
import eu.compassresearch.core.interpreter.api.values.NoConstraint;
import eu.compassresearch.core.interpreter.api.values.UnresolvedExpressionValue;
import eu.compassresearch.core.interpreter.api.values.ValueConstraint;
import eu.compassresearch.core.interpreter.utility.Pair;

public class ActionInspectionVisitor extends CommonInspectionVisitor
{

	private QuestionAnswerCMLAdaptor<Context, Inspection> statementInspectionVisitor;

	public ActionInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{
		super(ownerProcess, visitorAccess, parentVisitor);

		this.statementInspectionVisitor = new StatementInspectionVisitor(ownerProcess, visitorAccess, this);
	}

	/**
	 * Transition methods
	 */

	@Override
	public Inspection defaultPStm(PStm node, Context question)
			throws AnalysisException
	{
		return node.apply(statementInspectionVisitor, question);
	}

	@Override
	public Inspection defaultPAction(PAction node, Context question)
			throws AnalysisException
	{

		throw new CmlInterpreterException(node, InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public Inspection caseAActionStm(AActionStm node, Context question)
			throws AnalysisException
	{
		return node.getAction().apply(this.parentVisitor, question);
	}

	@Override
	public Inspection caseAStmAction(AStmAction node, Context question)
			throws AnalysisException
	{
		return node.getStatement().apply(this.parentVisitor, question);
	}

	@Override
	public Inspection caseACallAction(final ACallAction node,
			final Context question) throws AnalysisException
	{
		final Value value = lookupName(node.getName(), question);
		if (value instanceof ActionValue)
		{
			// first find the action value in the context
			final ActionValue actionVal = (ActionValue) value;

			return newInspection(createTauTransitionWithoutTime(actionVal.getActionDefinition().getAction(), null), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					return caseReferenceAction(node.getLocation(), node.getArgs(), actionVal, question);
				}
			});

		} else
		{
			throw new CmlInterpreterException(node, InterpretationErrorMessages.FATAL_ERROR.customizeMessage());
		}
	}

	// /**
	// * This deals both with calls but also parametrised action reference, since the typechecker does not replace this
	// * node yet FIXME This might be changed! if the typechecker replaces the call node with a action reference node
	// */
	// @Override
	// public Inspection caseACallStm(final ACallStm node, final Context question)
	// throws AnalysisException
	// {
	//
	// if (!owner.hasChildren())
	// {
	// final Value value = lookupName(node.getName(), question);
	// if (value instanceof CmlOperationValue)
	// return node.apply(statementInspectionVisitor, question);
	// else if (value instanceof ActionValue)
	// {
	// // first find the action value in the context
	// final ActionValue actionVal = (ActionValue) value;
	//
	// return newInspection(createTauTransitionWithoutTime(actionVal.getActionDefinition().getAction(), null), new
	// AbstractCalculationStep(owner, visitorAccess)
	// {
	//
	// @Override
	// public Pair<INode, Context> execute(
	// CmlTransition selectedTransition)
	// throws AnalysisException
	// {
	//
	// return caseReferenceAction(node.getLocation(), node.getArgs(), actionVal, question);
	// }
	// });
	//
	// } else
	// throw new CmlInterpreterException(node, InterpretationErrorMessages.FATAL_ERROR.customizeMessage());
	// } else
	// {
	// return node.apply(statementInspectionVisitor, question);
	// }
	// }

	/**
	 * Synchronization and Communication D23.2 7.5.2 This transition can either be Simple prefix : a -> A
	 * Synchronization : a.1 -> A Output : a!2 -> A Input : a?x -> A As defined in 7.5.2 in D23.2
	 */
	@Override
	public Inspection caseACommunicationAction(final ACommunicationAction node,
			final Context question) throws AnalysisException
	{

		setWaiting();

		// create the channel name
		ILexNameToken channelName = NamespaceUtility.createChannelName(node.getIdentifier());
		// find the channel value
		CMLChannelValue chanValue = (CMLChannelValue) question.lookup(channelName);

		Set<CmlTransition> comset = new HashSet<CmlTransition>();
		List<Value> values = new LinkedList<Value>();
		List<ValueConstraint> constraints = new LinkedList<ValueConstraint>();
		boolean hasLooseValue = false;
		for (int i = 0; i < node.getCommunicationParameters().size(); i++)
		{
			PCommunicationParameter p = node.getCommunicationParameters().get(i);
			if (p instanceof ASignalCommunicationParameter
					|| p instanceof AWriteCommunicationParameter)
			{
				Value valueExp = null;
				// if we have had any loose values we need to make sure that it can be evaluated
				// and if not we create a UnresolvedExpressionValue to defer the evaluation
				if (hasLooseValue)
				{
					try
					{
						valueExp = p.getExpression().apply(cmlExpressionVisitor, question);
					} catch (ContextException e)
					{
						valueExp = new UnresolvedExpressionValue(p.getExpression(), question);
					}
				} else
				{
					valueExp = p.getExpression().apply(cmlExpressionVisitor, question);
				}
				// Deref the variable if updatable since this could
				// change the trace at a latter point
				if (valueExp instanceof UpdatableValue)
				{
					values.add(valueExp.deref());
				} else
				{
					values.add(valueExp);
				}
				constraints.add(new NoConstraint());
			} else if (p instanceof AReadCommunicationParameter)
			{
				// This value can be any value of the given type so we need to set it
				// to the latticeTopElement which means exactly that
				values.add(new LatticeTopValue(chanValue.getValueTypes().get(i)));
				// Add constraints if any
				AReadCommunicationParameter readParam = (AReadCommunicationParameter) p;
				if (readParam.getExpression() != null)
				{
					Context constraintContext = CmlContextFactory.newContext(p.getLocation(), "Constraint evaluation context", question);
					constraints.add(new ExpressionConstraint(readParam, constraintContext));
				} else
				{
					constraints.add(new NoConstraint());
				}

				hasLooseValue = true;
			}
		}

		ObservableTransition observableEvent = CmlTransitionFactory.newObservableChannelEvent(owner, new ChannelNameValue(chanValue, values, constraints));
		comset.add(observableEvent);

		return newInspection(new CmlTransitionSet(comset).union(new TimedTransition(owner)), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// At this point the supervisor has already given go to the event, or the event is hidden
				ChannelNameValue channelNameValue = ((LabelledTransition) selectedTransition).getChannelName();

				Context nextContext = question;

				if (node.getCommunicationParameters() != null)
				{
					for (int i = 0; i < node.getCommunicationParameters().size(); i++)
					{
						PCommunicationParameter param = node.getCommunicationParameters().get(i);

						if (param instanceof AReadCommunicationParameter)
						{
							PPattern pattern = ((AReadCommunicationParameter) param).getPattern();
							Value value = channelNameValue.getValues().get(i);

							/*
							 * Create s new context for the input params. We only want to create one new context no
							 * matter the number of params so we check for equality.
							 */
							if (nextContext == question)
							{
								nextContext = CmlContextFactory.newContext(node.getAction().getLocation(), "input communication context", question);
							}

							nextContext.putList(PPatternAssistantInterpreter.getNamedValues(pattern, value, nextContext));
						}
					}
				}
				return new Pair<INode, Context>(node.getAction(), nextContext);
			}
		});
	}

	/**
	 * This implements divergens. As described in the operational semantics of D23.4: Div -tau-> Div
	 */
	@Override
	public Inspection caseADivAction(final ADivAction node,
			final Context question) throws AnalysisException
	{
		return newInspection(createTauTransitionWithoutTime(node), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// TODO Auto-generated method stub
				return new Pair<INode, Context>(node, question);
			}
		});
	}

	/**
	 * External Choice section 7.5.4 D23.2 In terms of the alphabet, we have the following situations: External Choice
	 * Begin: When no children exists, the External Choice Begin transition rule must be executed. This is a silent
	 * transition and therefore the alphabet contains only tau event External Choice Silent: If any of the actions can
	 * take a silent transition they will do it before getting here again. We therefore don't take this situation into
	 * account External Choice Skip: If one the children is Skip we make a silent transition of the whole choice into
	 * skip. We therefore just return the tau event External Choice End: The alphabet contains an observable event for
	 * every child that can engaged in one.
	 */
	@Override
	public Inspection caseAExternalChoiceAction(AExternalChoiceAction node,
			Context question) throws AnalysisException
	{

		return caseAExternalChoice(node, node.getLeft(), node.getRight(), question);
	}

	/**
	 * Interleaving A ||| B (no state) or A [|| ns1 | ns2 ||] B This has three parts: Parallel Begin: At this step the
	 * interleaving action are not yet created. So this will be a silent (tau) transition where the left and right
	 * actions will be created and started. Parallel Non-sync: At this step the actions are each executed separately.
	 * Since no sync shall stake place this Action just wait for the child actions to be in the FINISHED state. Parallel
	 * End: At this step both child actions are in the FINISHED state and they will be removed from the running process
	 * network and this will make a silent transition into Skip.
	 */
	@Override
	public Inspection caseAInterleavingParallelAction(
			final AInterleavingParallelAction node, final Context question)
			throws AnalysisException
	{

		// TODO: This only implements the "A ||| B (no state)" and not "A [|| ns1 | ns2 ||] B"

		/*
		 * This is a little hack to come around that the cmlExpressionVisitor does not now if it has to proc
		 */
		// Context varsetContext = CmlContextFactory.newContext(node.getLocation(), "varset expression context",
		// question);
		// varsetContext.putNew(new NameValuePair(NamespaceUtility.getVarExpContextName(), new BooleanValue(true)));

		// if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if (!owner.hasChildren())
		{

			return newInspection(createTauTransitionWithoutTime(node, "Begin"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					NamesetValue leftNamesetValue = null;
					NamesetValue rightNamesetValue = null;

					if (node.getLeftNamesetExpression() != null)
					{
						leftNamesetValue = (NamesetValue)node.getLeftNamesetExpression().apply(cmlExpressionVisitor, question);
					}
					if (node.getRightNamesetExpression() != null)
					{
						rightNamesetValue = (NamesetValue)node.getRightNamesetExpression().apply(cmlExpressionVisitor, question);
					}

					caseParallelBegin(node, leftNamesetValue, rightNamesetValue, question);
					// We push the current state, since this process will control the child processes created by it
					return new Pair<INode, Context>(node, question);
				}
			});

		}
		// the process has children and must now handle either termination or event sync
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			ASkipAction dstNode = CmlAstFactory.newASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithoutTime(dstNode, "End"), caseParallelEnd(dstNode, question));
		} else
		{
			return newInspection(syncOnTockAndJoinChildren(), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// At least one child is not finished and waiting for event, this will invoke the Parallel Non-sync
					caseParallelNonSync(selectedTransition);
					// We push the current state,
					return new Pair<INode, Context>(node, question);
				}
			});
		}
	}

	/**
	 * internal choice - section 7.5.3 D23.2 An internal choice between two actions can evolve via a tau event into
	 * either of them
	 */
	@Override
	public Inspection caseAInternalChoiceAction(
			final AInternalChoiceAction node, final Context question)
			throws AnalysisException
	{

		final int rndChoice = this.rnd.nextInt(2);
		INode tmpNode = null;
		Context tmpContext = null;
		if (rndChoice == 0)
		{
			tmpNode = node.getLeft();
			tmpContext = getChildContexts(question).first;
		} else
		{
			tmpNode = node.getRight();
			tmpContext = getChildContexts(question).second;
		}

		final INode nextNode = tmpNode;
		final Context nextContext = tmpContext;

		return newInspection(createTauTransitionWithTime(nextNode), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return new Pair<INode, Context>(nextNode, nextContext);
			}
		});
	}

	/**
	 * Recursion - section 7.5.9 TODO Mutually recursive processes are not implemented yet
	 */
	@Override
	public Inspection caseAMuAction(final AMuAction node, final Context question)
			throws AnalysisException
	{

		return newInspection(createTauTransitionWithTime(node, null), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// /THIS IS NOT CORRECT sEMANTICALLY,
				Context muContext = CmlContextFactory.newContext(node.getLocation(), "mu context", question);

				NameValuePairList nvpl = new NameValuePairList();

				Pair<INode, Context> res = null;

				for (int i = 0; i < node.getIdentifiers().size(); i++)
				{
					ILexIdentifierToken id = node.getIdentifiers().get(i);

					ILexNameToken name = new CmlLexNameToken("", id);

					PAction action = node.getActions().get(i);

					AActionDefinition actionDef = CmlAstFactory.newAActionDefinition(node.getLocation(), NameScope.LOCAL, true, null, Pass.DEFS, null, action);

					nvpl.add(new NameValuePair(name, new ActionValue(actionDef)));
					if (i == 0)
					{
						res = new Pair<INode, Context>(action, muContext);
					}
				}

				muContext.putAllNew(nvpl);

				return res;
			}
		});
	}
	
	private void caseParallelBegin(SParallelAction node, NamesetValue leftNameset, NamesetValue rightNameset, Context question)
			throws AnalysisException
	{
		PAction left = node.getLeftAction();
		PAction right = node.getRightAction();
		Pair<Context, Context> childContexts = getChildContexts(question);
		
		Context leftCopy = childContexts.first.deepCopy();
		
		if(leftNameset != null)
			leftCopy.putNew(new NameValuePair(NamespaceUtility.getNamesetName(), leftNameset));
		
		CmlBehaviour leftInstance = new ConcreteCmlBehaviour(left, leftCopy, new CmlLexNameToken(owner.name().getModule(), owner.name().getIdentifier().getName()
				+ "|||", left.getLocation()), owner);
		
		Context rightCopy = childContexts.second.deepCopy();
		
		if(rightNameset != null)
			rightCopy.putNew(new NameValuePair(NamespaceUtility.getNamesetName(), rightNameset));
		
		CmlBehaviour rightInstance = new ConcreteCmlBehaviour(right, rightCopy, new CmlLexNameToken(owner.name().getModule(), "|||"
				+ owner.name().getIdentifier().getName(), right.getLocation()), owner);

		// add the children to the process graph
		setLeftChild(leftInstance);
		setRightChild(rightInstance);
	}

	/**
	 * State-based Choice - section 7.5.5 D23.2 Guard Guarded actions are stuck, unless the guard is true. So If we ever
	 * execute this transition, the guard expression would already have been checked for being true.
	 */
	@Override
	public Inspection caseAGuardedAction(final AGuardedAction node,
			final Context question) throws AnalysisException
	{

		// First we evaluate the guard expression
		Value guardExp = node.getExpression().apply(cmlExpressionVisitor, question);

		CmlTransitionSet alpha = null;

		// if the gaurd is true then we return the silent transition to the guarded action
		if (guardExp.boolValue(question))
		{
			alpha = createTauTransitionWithTime(node.getAction());
			// else we return the empty alphabet since no transition is possible
		} else
		{
			alpha = new CmlTransitionSet(new TimedTransition(owner));
		}

		return newInspection(alpha, new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return new Pair<INode, Context>(node.getAction(), question);
			}
		});
	}

	/**
	 * Hiding - section 7.5.8 D23.2 syntax : Action \\ channelsetExpression
	 */
	@Override
	public Inspection caseAHidingAction(final AHidingAction node,
			final Context question) throws AnalysisException
	{
		return caseHiding(node, node.getChansetExpression(), question);
	}

	/**
	 * Generalised Parallelism A [| cs |] B (no state) or A [| ns1 | cs | ns2 |] B
	 */
	@Override
	public Inspection caseAGeneralisedParallelismParallelAction(
			final AGeneralisedParallelismParallelAction node,
			final Context question) throws AnalysisException
	{

		return caseGeneralisedParallelismParallel(node, new parallelCompositionHelper()
		{

			@Override
			public void caseParallelBegin() throws AnalysisException
			{
				NamesetValue leftNamesetValue = null;
				NamesetValue rightNamesetValue = null;

				if (node.getLeftNamesetExpression() != null)
				{
					leftNamesetValue = (NamesetValue)node.getLeftNamesetExpression().apply(cmlExpressionVisitor, question);
				}
				if (node.getRightNamesetExpression() != null)
				{
					rightNamesetValue = (NamesetValue)node.getRightNamesetExpression().apply(cmlExpressionVisitor, question);
				}
				
				ActionInspectionVisitor.this.caseParallelBegin(node,leftNamesetValue,rightNamesetValue, question);
			}
		}, node.getChansetExpression(), question);
	}

	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2.
	 */
	@Override
	public Inspection caseAReferenceAction(final AReferenceAction node,
			final Context question) throws AnalysisException
	{
		// FIXME: the scoping is not correct, this should be done as described in the transition rule

		// FIXME: Consider: Instead of this might create a child process, and behave as this child until it terminates
		// CMLActionInstance refchild = new CMLActionInstance(node.getActionDefinition().getAction(), question,
		// node.getName());
		final ActionValue actionValue = (ActionValue) question.check(node.getName()).deref();

		return newInspection(createTauTransitionWithoutTime(actionValue.getActionDefinition().getAction()), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return caseReferenceAction(node.getLocation(), node.getArgs(), actionValue, question);
			}
		});
	}

	protected Pair<INode, Context> caseReferenceAction(ILexLocation location,
			List<PExp> args, ActionValue actionValue, Context question)
			throws AnalysisException
	{

		// evaluate all the arguments
		NameValuePairMap evaluatedArgs = new NameValuePairMap();

		int paramIndex = 0;
		for (PParametrisation parameterization : actionValue.getActionDefinition().getDeclarations())
		{
			ILexNameToken id = parameterization.getDeclaration().getName();

			// get and evaluate the i'th expression
			PExp arg = args.get(paramIndex);
			Value value = arg.apply(cmlExpressionVisitor, question);

			// check whether the type is correct
			// if(arg.getType().equals(o))
			// error(node,"Arguments does not match the action parameterization");

			// Decide whether the argument is updateable or not
			if (parameterization instanceof AValParametrisation)
			{
				value = value.getConstant();
			} else
			{
				value = value.getUpdatable(null);
			}

			evaluatedArgs.put(new CmlLexNameToken("", (ILexIdentifierToken) id.clone()), value);

			// update the index
			paramIndex++;
		}

		Context refActionContext = CmlContextFactory.newContext(location, "Parametrised reference action context", question);

		refActionContext.putAll(evaluatedArgs);

		return new Pair<INode, Context>(actionValue.getActionDefinition().getAction(), refActionContext);
	}

	/**
	 * This implements the 7.5.6 Sequential Composition transition rules in D23.2.
	 */
	@Override
	public Inspection caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException
	{

		return caseASequentialComposition(node, node.getLeft(), node.getRight(), question);
	}

	@Override
	public Inspection caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException
	{

		// return the alphabet only containing tock since Skip allows for time to pass
		// return newInspection(new CmlAlphabet(new CmlTock(owner)),null);
		return newInspection(new CmlTransitionSet(), null);
	}

	@Override
	public Inspection caseASkipStm(ASkipStm node, Context question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return newInspection(new CmlTransitionSet(), null);
	}

	@Override
	public Inspection caseAStopAction(AStopAction node, Context question)
			throws AnalysisException
	{
		// return the alphabet only containing tock since Stop allows for time to pass
		return newInspection(new CmlTransitionSet(new TimedTransition(owner)), null);
	}

	/**
	 * Timed actions
	 * 
	 * @throws AnalysisException
	 * @throws ValueException
	 */

	@Override
	public Inspection caseATimeoutAction(final ATimeoutAction node,
			final Context question) throws AnalysisException
	{

		return caseATimeout(node, node.getLeft(), node.getRight(), node.getTimeoutExpression(), question);
	}

	@Override
	public Inspection caseAUntimedTimeoutAction(
			final AUntimedTimeoutAction node, final Context question)
			throws AnalysisException
	{
		return caseAUntimedTimeout(node, node.getRight(), question);
	}

	@Override
	public Inspection caseAWaitAction(final AWaitAction node,
			final Context question) throws AnalysisException
	{

		// Evaluate the expression into a natural number
		long val = node.getExpression().apply(cmlExpressionVisitor, question).natValue(question);
		long startTime = question.lookup(NamespaceUtility.getStartTimeName()).intValue(question);
		long nTocks = owner.getCurrentTime() - startTime;

		// If the number of tocks exceeded val then we make a silent transition that ends the delay process
		if (nTocks >= val)
		{
			return newInspection(createTauTransitionWithTime(CmlAstFactory.newASkipAction(node.getLocation()), "Wait ended"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We need to remove the added context from the setup visitor
					return new Pair<INode, Context>(CmlAstFactory.newASkipAction(node.getLocation()), question.outer);
				}
			});
		} else
		{
			// If the number of tocks has not exceeded val then behave as Stop
			return newInspection(new CmlTransitionSet(new TimedTransition(owner, val
					- nTocks)), null);
		}
	}

	/**
	 * Interrupt A /_\ B : The possible transitions from both A and B are exposed as long as A is not finished or an
	 * observable event from B occurs.
	 */
	@Override
	public Inspection caseAInterruptAction(final AInterruptAction node,
			final Context question) throws AnalysisException
	{

		return caseAInterrupt(node, question);
	}
	
	@Override
	public Inspection caseATimedInterruptAction(ATimedInterruptAction node,
			Context question) throws AnalysisException
	{
		return caseATimedInterrupt(node, node.getLeft(), node.getRight(), node.getTimeExpression(), question);
	}
}
