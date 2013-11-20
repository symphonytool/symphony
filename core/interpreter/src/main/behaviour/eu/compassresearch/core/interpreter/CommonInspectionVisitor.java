package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.HiddenTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.values.CMLChannelValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.interpreter.utility.Pair;

@SuppressWarnings("serial")
class CommonInspectionVisitor extends AbstractInspectionVisitor
{

	interface parallelCompositionHelper
	{
		void caseParallelBegin() throws AnalysisException;
	}

	public CommonInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{
		super(ownerProcess, visitorAccess, parentVisitor);
	}

	/**
	 * This synchronizes any tock event from the children if both are running an and joins all the rest of the events
	 * 
	 * @return The joined transitions of the children syncing on tock if possible
	 * @throws AnalysisException
	 */
	protected CmlTransitionSet syncOnTockAndJoinChildren()
			throws AnalysisException
	{
		// Get all the child alphabets
		CmlTransitionSet leftChildAlphabet = owner.getLeftChild().inspect();
		CmlTransitionSet rightChildAlphabet = owner.getRightChild().inspect();

		// if both are running and they both have tock event we sync them
		if (!owner.getLeftChild().finished()
				&& !owner.getRightChild().finished()
				&& leftChildAlphabet.hasTockEvent()
				&& rightChildAlphabet.hasTockEvent())
		{
			// get the tocks
			TimedTransition leftTock = leftChildAlphabet.getTockEvent();
			TimedTransition rightTock = rightChildAlphabet.getTockEvent();

			// sync them
			CmlTransitionSet returnAlpha = new CmlTransitionSet(leftTock.synchronizeWith(rightTock));

			// remove the old tocks and add the synced one to the result
			return returnAlpha.union(leftChildAlphabet.subtract(leftTock).union(rightChildAlphabet.subtract(rightTock)));
		}
		// else we just joins all the event from both
		else
		{
			return leftChildAlphabet.union(rightChildAlphabet);
		}
	}

	/**
	 * Handles the external choice end rule
	 * 
	 * @return
	 */
	protected Pair<INode, Context> caseExternalChoiceEnd(
			CmlBehaviour theChoosenOne, Context context) throws ValueException
	{
		Context copyContext = theChoosenOne.getNextState().second;
		Context newCurrentContext = CmlBehaviourUtility.mergeAndReplaceState(context, copyContext);
		// Context newCurrentContext = copyContext;

		if (theChoosenOne.getLeftChild() != null)
			theChoosenOne.getLeftChild().replaceState(newCurrentContext);

		visitorAccess.setLeftChild(theChoosenOne.getLeftChild());

		if (theChoosenOne.getRightChild() != null)
			theChoosenOne.getRightChild().replaceState(newCurrentContext);

		visitorAccess.setRightChild(theChoosenOne.getRightChild());

		return new Pair<INode, Context>(theChoosenOne.getNextState().first, newCurrentContext);
	}

	protected Inspection caseAExternalChoice(final INode node,
			final INode leftNode, final INode rightNode, final Context question)
			throws AnalysisException
	{

		// if no children are present we make a silent transition to represent the
		// external choice begin
		if (!owner.hasChildren())
		{
			return newInspection(createTauTransitionWithoutTime(node, "Begin"), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					Pair<Context, Context> childContexts = visitorAccess.getChildContexts(question);

					String module = name().getModule();
					String nameStr = name().getIdentifier().getName();

					setLeftChild(new ConcreteCmlBehaviour(leftNode, CmlBehaviourUtility.deepCopyProcessContext(childContexts.first), new CmlLexNameToken(module, nameStr
							+ "[]", LocationExtractor.extractLocation(leftNode)), this.owner));

					setRightChild(new ConcreteCmlBehaviour(rightNode, CmlBehaviourUtility.deepCopyProcessContext(childContexts.second), new CmlLexNameToken(module, "[]"
							+ nameStr, LocationExtractor.extractLocation(rightNode)), this.owner));

					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// if one child is finished external choice must end
		// If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		// with the state from the skip. After this all the children processes are terminated
		else if (CmlBehaviourUtility.finishedChildExists(owner))
			return newInspection(createTauTransitionWithoutTime(node, "end"), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return caseExternalChoiceEnd(findFinishedChild(), question);
				}
			});
		// else we join the childrens alphabets
		else
		{
			return newInspection(syncOnTockAndJoinChildren(), new AbstractCalculationStep(owner, visitorAccess)
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
								return caseExternalChoiceEnd(child, question);
							else
								return new Pair<INode, Context>(node, question);
						}
					}

					throw new AnalysisException("should not happen");
				}
			});
		}
	}

	/**
	 * Private common helpers for Generalised Parallelism
	 */

	protected Inspection caseGeneralisedParallelismParallel(final INode node,
			final parallelCompositionHelper helper,
			PVarsetExpression chansetExp, final Context question)
			throws AnalysisException
	{
		// TODO: This only implements the "A [| cs |] B (no state)" and not "A [| ns1 | cs | ns2 |] B"

		// if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if (!owner.hasChildren())
		{
			return newInspection(createTauTransitionWithTime(node, "Begin"),

			new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					helper.caseParallelBegin();
					// We push the current state, since this process will control the child processes created by it
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// The process has children and they have all evolved into Skip so now the parallel end rule will be invoked
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			return newInspection(createTauTransitionWithoutTime(new ASkipAction(), "End"), caseParallelEnd(question));
		} else
		{
			return caseParallelSyncOrNonsync(node, chansetExp, question);
		}
	}

	private Inspection caseParallelSyncOrNonsync(final INode node,
			PVarsetExpression chansetExp, final Context question)
			throws AnalysisException
	{

		// convert the channel set of the current node to a alphabet
		ChannelNameSetValue cs = (ChannelNameSetValue) chansetExp.apply(cmlExpressionVisitor, question);

		// Get all the child alphabets and add the events that are not in the channelset
		final CmlBehaviour leftChild = owner.getLeftChild();
		final CmlTransitionSet leftChildAlphabet = leftChild.inspect();
		final CmlBehaviour rightChild = owner.getRightChild();
		final CmlTransitionSet rightChildAlphabet = rightChild.inspect();

		// combine all the common channel events that are in the channel set
		CmlTransitionSet leftSync = leftChildAlphabet.retainByChannelNameSet(cs);
		CmlTransitionSet rightSync = rightChildAlphabet.retainByChannelNameSet(cs);
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
						syncEvents.add(leftTrans.synchronizeWith(rightTrans));
				}
			}
		}

		TimedTransition leftTock = leftChildAlphabet.getTockEvent();
		TimedTransition rightTock = rightChildAlphabet.getTockEvent();
		if (leftTock != null && rightTock != null)
			syncEvents.add(leftTock.synchronizeWith(rightTock));

		/*
		 * Finally we create the returned alphabet by joining all the Synchronized events together with all the event of
		 * the children that are not in the channel set.
		 */
		CmlTransitionSet resultAlpha = new CmlTransitionSet(syncEvents).union(leftChildAlphabet.removeByChannelNameSet(cs));
		resultAlpha = resultAlpha.union(rightChildAlphabet.removeByChannelNameSet(cs));

		return newInspection(resultAlpha, new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// if both contains the selected event it must be a sync event
				if (leftChildAlphabet.contains(selectedTransition)
						&& rightChildAlphabet.contains(selectedTransition))
				{
					leftChild.execute(selectedTransition);
					rightChild.execute(selectedTransition);
				} else if (leftChildAlphabet.contains(selectedTransition))
				{
					leftChild.execute(selectedTransition);
				} else if (rightChildAlphabet.contains(selectedTransition))
				{
					rightChild.execute(selectedTransition);
				} else
					// Something went wrong here
					throw new CmlInterpreterException(node, InterpretationErrorMessages.FATAL_ERROR.customizeMessage(""));

				// We push the current state,
				return new Pair<INode, Context>(node, question);
			}
		});

	}

	protected CmlCalculationStep caseParallelEnd(final Context question)
	{
		return new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{

				setLeftChild(null);
				setRightChild(null);

				// now this process evolves into Skip
				return new Pair<INode, Context>(new ASkipAction(), question);
			}
		};
	}

	/**
	 * Common Hiding handler methods
	 * 
	 * @throws AnalysisException
	 */
	protected Inspection caseHiding(final INode node,
			PVarsetExpression chansetExpression, final Context question)
			throws AnalysisException
	{
		// We do the hiding behavior as long as the Action is not terminated
		if (!owner.getLeftChild().finished())
		{
			// first we convert the channelset expression into a channelNameSetValue
			ChannelNameSetValue cs = (ChannelNameSetValue) chansetExpression.apply(cmlExpressionVisitor, question);
			// next we inspect the action to get the current available transitions
			final CmlTransitionSet alpha = owner.getLeftChild().inspect();
			// Intersect the two to find which transitions should be converted to silents transitions
			CmlTransitionSet hiddenEvents = alpha.retainByChannelNameSet(cs);
			// remove the events that has to be silent
			CmlTransitionSet resultAlpha = alpha.subtract(hiddenEvents);
			// convert them into silent events and add the again
			for (ObservableTransition obsEvent : hiddenEvents.getObservableChannelEvents())
				resultAlpha = resultAlpha.union(new HiddenTransition(owner, node, (LabelledTransition) obsEvent));

			return newInspection(resultAlpha, new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					if (selectedTransition instanceof HiddenTransition
							&& alpha.contains(((HiddenTransition) selectedTransition).getHiddenEvent()))
						selectedTransition = ((HiddenTransition) selectedTransition).getHiddenEvent();

					owner.getLeftChild().execute(selectedTransition);
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// If the Action is terminated then it evolves into Skip
		else
			return newInspection(createTauTransitionWithTime(new ASkipAction()), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					setLeftChild(null);
					return new Pair<INode, Context>(new ASkipAction(), question);
				}
			});
	}

	protected Inspection caseAUntimedTimeout(final INode node,
			final INode rightNode, final Context question)
			throws AnalysisException
	{
		// the alphabet still need to be calculated before this is done, so uncomment when done
		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					CmlBehaviour leftChild = owner.getLeftChild();
					setLeftChild(null);
					setRightChild(null);
					return leftChild.getNextState();
				}
			});
		}
		// Make a random decision whether the process should timeout and
		// behaves as the right process
		else if (this.rnd.nextBoolean())
		{
			return newInspection(createTauTransitionWithTime(rightNode), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We set the process to become the right behavior
					setLeftChild(null);
					return new Pair<INode, Context>(rightNode, question);
				}
			});
		}
		// if no timeout has occurred the whole process behaves as the left process
		else
		{
			return newInspection(owner.getLeftChild().inspect(), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					CmlBehaviour leftBehavior = owner.getLeftChild();
					owner.getLeftChild().execute(selectedTransition);

					if (selectedTransition instanceof ObservableTransition
							&& selectedTransition instanceof LabelledTransition)
					{
						setLeftChild(null);
						return new Pair<INode, Context>(leftBehavior.getNextState().first, leftBehavior.getNextState().second);
					} else
						return new Pair<INode, Context>(node, question);
				}
			});
		}
	}

	protected Inspection caseATimeout(final INode node, final INode leftNode,
			final INode rightNode, PExp timeoutExpression,
			final Context question) throws ValueException, AnalysisException
	{
		// Evaluate the expression into a natural number
		long val = timeoutExpression.apply(cmlExpressionVisitor, question).natValue(question);
		long startTimeVal = question.lookup(NamespaceUtility.getStartTimeName()).intValue(question);
		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first, "Timeout: left behavior is finished"), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					return replaceWithChild(owner.getLeftChild());
				}
			});
		}
		// if the current time of the process has passed the limit (val) then process
		// behaves as the right process
		else if (owner.getCurrentTime() - startTimeVal >= val)
		{
			return newInspection(createTauTransitionWithoutTime(rightNode, "Timeout: time exceeded"), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We set the process to become the right behavior
					setLeftChild(null);
					// We need to return the outer context because of the extra context
					// containing the start time has been added in the setup visitor
					return new Pair<INode, Context>(rightNode, question.outer);
				}
			});

		}
		// if the current time of the process has not passed the limit (val) and the left process
		// makes an observable transition then the whole process behaves as the left process
		else
		{
			//
			final CmlBehaviour leftBehavior = owner.getLeftChild();

			CmlTransitionSet resultAlpha = null;
			CmlTransitionSet leftAlpha = leftBehavior.inspect();
			// If time can pass in the left, we need to put the remaining time of the timeout
			if (leftAlpha.hasTockEvent())
			{
				TimedTransition leftTimeTransition = leftAlpha.getTockEvent();
				resultAlpha = leftAlpha.subtract(leftTimeTransition);
				long limit = val - (owner.getCurrentTime() - startTimeVal);
				resultAlpha = resultAlpha.union(leftTimeTransition.synchronizeWith(new TimedTransition(owner, limit)));
			} else
				resultAlpha = leftAlpha;

			return newInspection(resultAlpha, new AbstractCalculationStep(owner, visitorAccess)
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					leftBehavior.execute(selectedTransition);

					if (selectedTransition instanceof ObservableTransition
							&& selectedTransition instanceof LabelledTransition)
					{
						return replaceWithChild(leftBehavior);
					} else
						return new Pair<INode, Context>(node, question);
				}
			});

		}
	}

	protected Inspection caseAInterrupt(final INode node, final Context question)
			throws AnalysisException
	{

		// If the left child is Skip then the while interrupt construct is Skip
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first),

			new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					return replaceWithChild(owner.getLeftChild());
				}
			});
		}
		// If the right action has taken a labelled transition then the whole becomes the right action
		else if (owner.getRightChild().getTraceModel().getLastTransition() instanceof ObservableTransition
				&& owner.getRightChild().getTraceModel().getLastTransition() instanceof LabelledTransition)
		{
			return newInspection(createTauTransitionWithTime(owner.getRightChild().getNextState().first),

			new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return replaceWithChild(owner.getRightChild());
				}
			});
		} else
		{
			return newInspection(syncOnTockAndJoinChildren(),

			new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					caseParallelNonSync(selectedTransition);
					return new Pair<INode, Context>(node, question);
				}
			});
		}

	}

	/**
	 * 
	 */
	protected AFatEnumVarsetExpression getAllChannelsAsFatEnum(
			ILexLocation location, Context question) throws AnalysisException
	{

		// TODO Change AFatEnumVarsetExpression expects List of ANameChannelExp instead of List of ILexNameToken
		Context globalContext = question.getGlobal();
		List<ANameChannelExp> channelNames = new LinkedList<ANameChannelExp>();

		// Get all the channel name exps objects
		for (Entry<ILexNameToken, Value> entry : globalContext.entrySet())
			if (entry.getValue() instanceof CMLChannelValue)
				channelNames.add(new ANameChannelExp(entry.getKey().getLocation(), entry.getKey().clone(), new LinkedList<PExp>()));

		return new AFatEnumVarsetExpression(location, channelNames);
	}

	/**
	 * Common Sequential composition handler methods
	 */
	protected Inspection caseASequentialComposition(final INode node,
			INode leftNode, final INode rightNode, final Context question)
			throws AnalysisException
	{
		// we execute the left action until it successfully terminates
		if (!owner.getLeftChild().finished())
		{
			return newInspection(owner.getLeftChild().inspect(), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					owner.getLeftChild().execute(selectedTransition);
					return new Pair<INode, Context>(node, question);
				}
			});

		}
		// if the left action is successfully finished then this node becomes the right action
		else
		{
			return newInspection(createTauTransitionWithoutTime(leftNode), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					setLeftChild(null);
					setRightChild(null);

					return new Pair<INode, Context>(rightNode, visitorAccess.getChildContexts(question).second);
				}
			});

		}
	}

}
