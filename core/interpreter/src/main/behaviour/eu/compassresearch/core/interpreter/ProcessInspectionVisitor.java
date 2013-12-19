package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ObjectContext;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class ProcessInspectionVisitor extends CommonInspectionVisitor
{
	public ProcessInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{
		super(ownerProcess, visitorAccess, parentVisitor);
	}

	/**
	 * Transition methods
	 */

	@Override
	public Inspection defaultPProcess(PProcess node, Context question)
			throws AnalysisException
	{
		throw new CmlInterpreterException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public Inspection caseAActionProcess(final AActionProcess node,
			final Context question) throws AnalysisException
	{
		return newInspection(createTauTransitionWithoutTime(node.getAction()), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				AProcessDefinition processDef;

				NameValuePairMap valueMap = new NameValuePairMap();

				// We have a named process
				if (node.parent() instanceof AProcessDefinition)
				{
					processDef = (AProcessDefinition) node.parent();
					if (processDef.getLocalState().size() > 0)
					{
						for (Entry<ILexNameToken, Value> entry : question.entrySet())
						{
							// valueMap.putNew(new
							// NameValuePair(entry.getKey().getModifiedName(processDef.getName().getSimpleName()),
							// entry.getValue()));
							valueMap.putNew(new NameValuePair(entry.getKey().getModifiedName(node.getActionDefinition().getName().getSimpleName()), entry.getValue()));
						}

					}
				}
				// Unnamed process
				else
				{
					AProcessDefinition pdef = node.getAncestor(AProcessDefinition.class);
					// We need to check whether the unnamed process is inside parameterised process, if it is then we
					// need
					// to add the parameters to this process since it cannot look outside the scope of itself
					if (pdef != null && pdef.getLocalState().size() > 0)
					{
						for (PParametrisation param : pdef.getLocalState())
						{
							ALocalDefinition decl = param.getDeclaration();
							// for (ILexIdentifierToken id : param.g decl.getIdentifiers())
							// {
							// ILexNameToken paramName = new CmlLexNameToken(pdef.getName().getSimpleName(),
							// id.clone());
							ILexNameToken paramName = decl.getName();
							Value val = question.lookup(paramName);
							valueMap.putNew(new NameValuePair(paramName.getModifiedName(node.getActionDefinition().getName().getSimpleName()), val));
							// }
						}
					}
				}

				// Create a temporary context to evaluate the definitions in
				Context tmpContext = null;
				AProcessDefinition processDefinition = node.getAncestor(AProcessDefinition.class);
				if (question.title.equals(CmlContextFactory.PARAMETRISED_PROCESS_CONTEXT_NAME))
				{
					tmpContext = CmlContextFactory.newObjectContext(node.getLocation(), "Tmp Action Process Context", question.outer, new ProcessObjectValue(processDefinition, node.getActionDefinition(), valueMap, null, null));
				} else
				{
					tmpContext = CmlContextFactory.newObjectContext(node.getLocation(), "Tmp Action Process Context", question, new ProcessObjectValue(processDefinition, node.getActionDefinition(), valueMap, null, null));
				}

				// Evaluate and add paragraph definitions and add the result to the state
				PExp processInv = null;
				for (PDefinition def : node.getActionDefinition().getDefinitions())
				{
					// Take out the invariant expression if any
					if (def instanceof AClassInvariantDefinition)
					{
						processInv = ((AClassInvariantDefinition) def).getExpression();
					}

					NameValuePairList nvps = def.apply(cmlDefEvaluator, tmpContext);
					tmpContext.putList(nvps);

					// set the correct module on the member names
					for (NameValuePair nvp : nvps)
					{
						ILexNameToken name = nvp.name.getModifiedName(node.getActionDefinition().getName().getSimpleName());
						valueMap.put(new NameValuePair(name, nvp.value));
					}
				}

				ProcessObjectValue self = new ProcessObjectValue(processDefinition, node.getActionDefinition(), valueMap, question.getSelf(), processInv);
				ObjectContext processObjectContext = null;

				// If params is defined in the above context them we need to add them to the created processContext
				// since it cannot look above that, meaning they won't be visible if we don't
				if (question.title.equals(CmlContextFactory.PARAMETRISED_PROCESS_CONTEXT_NAME))
				{
					processObjectContext = CmlContextFactory.newObjectContext(node.getLocation(), "Action Process Context", question.outer, self);
				} else
				{
					processObjectContext = CmlContextFactory.newObjectContext(node.getLocation(), "Action Process Context", question, self);
				}

				owner.getName().addAction(node.getAction());

				// push this node onto the execution stack again since this should execute
				// the action behavior until it terminates
				return new Pair<INode, Context>(node.getAction(), processObjectContext);
			}
		});
	}

	/**
	 * External Choice D23.2 7.5.4 There four transition rules for external choice: * External Choice Begin * External
	 * Choice Silent * External Choice SKIP * External Choice End
	 */
	@Override
	public Inspection caseAExternalChoiceProcess(
			final AExternalChoiceProcess node, final Context question)
			throws AnalysisException
	{

		// return caseAExternalChoice(node,node.getLeft(),node.getRight(), question);
		// if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if (!owner.hasChildren())
		{
			return newInspection(createTauTransitionWithTime(node, "Begin"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					setLeftChild(node.getLeft(), owner.getName().clone(true), question);

					setRightChild(node.getRight(), owner.getName().clone(true), question);
					// Now let this process wait for the children to get into a waitForEvent state
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		// with the state from the skip. After this all the children processes are terminated
		else if (CmlBehaviourUtility.finishedChildExists(owner))
		{
			return newInspection(createTauTransitionWithTime(node, "End"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return replaceWithChild(findFinishedChild());
				}
			});
		} else
		{
			return newInspection(syncOnTockAndJoinChildren(), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					for (CmlBehaviour child : children())
					{
						if (child.inspect().contains(selectedTransition))
						{
							// first we execute the child
							child.execute(selectedTransition);
							if (selectedTransition instanceof LabelledTransition)
							{
								return replaceWithChild(child);
							} else
							{
								return new Pair<INode, Context>(node, question);
							}
						}
					}

					throw new AnalysisException("Should not happen");
				}
			});
		}
	}

	/**
	 * Parallel process In general all the parallel processes have three transition rules that can be invoked Parallel
	 * Begin: At this step the interleaving action are not yet created. So this will be a silent (tau) transition where
	 * they will be created and started. So the alphabet returned here is {tau} Parallel Sync/Non-sync: Parallel End: At
	 * this step both child actions are in the FINISHED state and they will be removed from the running process network
	 * and this will make a silent transition into Skip. So the alphabet returned here is {tau}
	 */

	@Override
	public Inspection caseAAlphabetisedParallelismProcess(
			final AAlphabetisedParallelismProcess node, final Context question)
			throws AnalysisException
	{
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
					caseParallelProcessBegin(node, node.getLeft(), node.getRight(), "[cs||cs]", question);
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
			// fetch the already evaluated left and right channel sets
			ChannelNameSetValue leftChanset = (ChannelNameSetValue) question.lookup(NamespaceUtility.getLeftPrecalculatedChannetSet()); // eval(
																																		// node.getLeftChansetExpression(),
																																		// getChildContexts(owner.getLeftChild().getNextState().second).first);
			ChannelNameSetValue rightChanset = (ChannelNameSetValue) question.lookup(NamespaceUtility.getRightPrecalculatedChannetSet()); // eval(node.getRightChansetExpression(),getChildContexts(owner.getRightChild().getNextState().second).second);

			// next we find the intersection of of them
			ChannelNameSetValue intersectionChanset = new ChannelNameSetValue(leftChanset);
			intersectionChanset.retainAll(rightChanset);

			final CmlTransitionSet leftChildAlpha = owner.getLeftChild().inspect();
			final CmlTransitionSet rightChildAlpha = owner.getRightChild().inspect();

			CmlTransitionSet leftAllowedNonSyncTransitions = leftChildAlpha.retainByChannelNameSet(leftChanset).removeByChannelNameSet(intersectionChanset).union(leftChildAlpha.getSilentTransitions());
			CmlTransitionSet rightAllowedNonSyncTransitions = rightChildAlpha.retainByChannelNameSet(rightChanset).removeByChannelNameSet(intersectionChanset).union(rightChildAlpha.getSilentTransitions());

			// combine all the common channel events that are in the channel set
			CmlTransitionSet leftSync = leftChildAlpha.retainByChannelNameSet(intersectionChanset);
			CmlTransitionSet rightSync = rightChildAlpha.retainByChannelNameSet(intersectionChanset);
			Set<CmlTransition> syncEvents = new HashSet<CmlTransition>();
			// Find the intersection between the child alphabets and the channel set and join them.
			// Then if both left and right have them the next step will combine them.
			for (ObservableTransition leftTrans : leftSync.getObservableChannelEvents())
			{
				for (ObservableTransition rightTrans : rightSync.getObservableChannelEvents())
				{
					if (leftTrans.isComparable(rightTrans))
					{

						LabelledTransition leftChannelEvent = (LabelledTransition) leftTrans;
						LabelledTransition rightChannelEvent = (LabelledTransition) rightTrans;

						if (leftChannelEvent.getChannelName().isGTEQPrecise(rightChannelEvent.getChannelName())
								|| rightChannelEvent.getChannelName().isGTEQPrecise(leftChannelEvent.getChannelName()))
						{
							syncEvents.add(leftTrans.synchronizeWith(rightTrans));
						}
					}
				}
			}

			/*
			 * Finally we create the returned alphabet by joining all the Synchronized events together with all the
			 * event of the children that are not in the channel set.
			 */
			CmlTransitionSet resultAlpha = new CmlTransitionSet(syncEvents).union(leftAllowedNonSyncTransitions);
			resultAlpha = resultAlpha.union(rightAllowedNonSyncTransitions);

			return newInspection(resultAlpha, new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// if both contains the selected event it must be a sync event
					if (leftChildAlpha.contains(selectedTransition)
							&& rightChildAlpha.contains(selectedTransition))
					{
						owner.getLeftChild().execute(selectedTransition);
						owner.getRightChild().execute(selectedTransition);
					} else if (leftChildAlpha.contains(selectedTransition))
					{
						owner.getLeftChild().execute(selectedTransition);
					} else if (rightChildAlpha.contains(selectedTransition))
					{
						owner.getRightChild().execute(selectedTransition);
					} else
					{
						// Something went wrong here
						throw new CmlInterpreterException(node, InterpretationErrorMessages.FATAL_ERROR.customizeMessage(""));
					}

					// We push the current state,
					return new Pair<INode, Context>(node, question);
				}
			});

		}
	}

	@Override
	public Inspection caseAGeneralisedParallelismProcess(
			final AGeneralisedParallelismProcess node, final Context question)
			throws AnalysisException
	{

		return caseGeneralisedParallelismParallel(node, new parallelCompositionHelper()
		{

			@Override
			public void caseParallelBegin() throws AnalysisException
			{
				ProcessInspectionVisitor.this.caseParallelProcessBegin(node, node.getLeft(), node.getRight(), "[|cs|]", question);
			}
		}, node.getChansetExpression(), question);
	}

	/**
	 * There are no actual transition rule for this. The rule for interleaving action is that they evolve into Skip.
	 * However, this will just terminate successfully when all its children terminates successfully.
	 */
	@Override
	public Inspection caseAInterleavingProcess(final AInterleavingProcess node,
			final Context question) throws AnalysisException
	{

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

					caseParallelProcessBegin(node, node.getLeft(), node.getRight(), "|||", question);
					// We push the current state, since this process will control the child processes created by it
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// the process has children and must now handle either termination or event sync
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			ASkipAction dstNode = CmlAstFactory.newASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithTime(dstNode, "End"), caseParallelEnd(dstNode, question));

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

	// FIXME the operator string is only a tmp solution
	private void caseParallelProcessBegin(PProcess node, PProcess left,
			PProcess right, String operatorsign, Context question)
			throws AnalysisException
	{
		if (left == null || right == null)
		{
			throw new InterpreterRuntimeException(InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
		}

		setLeftChild(left, new CmlBehaviour.BehaviourName(node, owner.getName(), operatorsign, ""), question);
		setRightChild(right, new CmlBehaviour.BehaviourName(node, owner.getName(), "", operatorsign), question);
	}

	@Override
	public Inspection caseAInternalChoiceProcess(
			final AInternalChoiceProcess node, final Context question)
			throws AnalysisException
	{

		// we need to pass these on to the children if set
		final Pair<Context, Context> childContexts = getChildContexts(question);

		if (rnd.nextInt(2) == 0)
		{
			return newInspection(createTauTransitionWithTime(node.getLeft()), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(node.getLeft(), childContexts.first);
				}
			});
		} else
		{
			return newInspection(createTauTransitionWithTime(node.getRight()), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(node.getRight(), childContexts.second);
				}
			});
		}

	}

	/**
	 * This implements the 7.5.10 Action Reference transition rule in D23.2. (Even though this is a process I assume
	 * something similar will happen)
	 */
	@Override
	public Inspection caseAReferenceProcess(final AReferenceProcess node,
			final Context question) throws AnalysisException
	{

		return newInspection(createTauTransitionWithoutTime(node.getProcessDefinition().getProcess()), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{

				// evaluate all the arguments
				NameValuePairMap evaluatedArgs = new NameValuePairMap();

				int paramIndex = 0;
				for (PParametrisation param : node.getProcessDefinition().getLocalState())
				{
					// param.getDeclaration().getName()
					// for (ILexIdentifierToken id : decl.getIdentifiers())
					// {
					// get and evaluate the i'th expression
					PExp arg = node.getArgs().get(paramIndex);
					// There are always a val param so they must allways be constant
					Value value = arg.apply(cmlExpressionVisitor, question).getConstant();

					CmlLexNameToken argName = new CmlLexNameToken(node.getProcessDefinition().getName().getSimpleName(), (ILexIdentifierToken) param.getDeclaration().getName().clone());
					// LexNameToken argName = new LexNameToken("",(ILexIdentifierToken)id.clone());

					evaluatedArgs.put(argName, value);
					// update the index
					paramIndex++;
					// }
				}

				Context nextContext = null;

				// If the top process of the process definition is a action process we do not do
				// anything as the it will take care of everything in the action process because tech. reasons
				if (node.getProcessDefinition().getProcess() instanceof AActionProcess)
				{
					nextContext = CmlContextFactory.newContext(node.getLocation(), CmlContextFactory.PARAMETRISED_PROCESS_CONTEXT_NAME, question);
					nextContext.putAll(evaluatedArgs);
				}
				// if not then we create a new process object
				else
				{
					ProcessObjectValue self = new ProcessObjectValue(node.getProcessDefinition(), evaluatedArgs, question.getSelf());
					nextContext = CmlContextFactory.newObjectContext(node.getLocation(), "Process Context", question, self);
				}

				// Context refProcessContext =
				// refProcessContext.putAll(evaluatedArgs);
				owner.getName().addProcess(node.getProcessName().getName()+"#"+owner.getId());
				return new Pair<INode, Context>(node.getProcessDefinition().getProcess(), nextContext);
			}
		});
	}

	@Override
	public Inspection caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException
	{
		return caseASequentialComposition(node, node.getLeft(), node.getRight(), question);
	}

	@Override
	public Inspection caseATimeoutProcess(ATimeoutProcess node, Context question)
			throws AnalysisException
	{
		return caseATimeout(node, node.getLeft(), node.getRight(), node.getTimeoutExpression(), question);
	}

	@Override
	public Inspection caseATimedInterruptProcess(ATimedInterruptProcess node,
			Context question) throws AnalysisException
	{
		return caseATimedInterrupt(node, node.getLeft(), node.getRight(), node.getTimeExpression(), question);
	}

	@Override
	public Inspection caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			Context question) throws AnalysisException
	{
		return caseAUntimedTimeout(node, node.getRight(), question);
	}

	@Override
	public Inspection caseAInterruptProcess(AInterruptProcess node,
			Context question) throws AnalysisException
	{
		return caseAInterrupt(node, question);
	}

	/**
	 * Private helper methods
	 * 
	 * @throws AnalysisException
	 */

	@Override
	public Inspection caseAHidingProcess(AHidingProcess node, Context question)
			throws AnalysisException
	{
		return caseHiding(node, node.getChansetExpression(), question);
	}
}
