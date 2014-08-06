package eu.compassresearch.core.interpreter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.util.HackLexNameToken;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.core.interpreter.api.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.api.transitions.HiddenTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.transitions.ObservableTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;
import eu.compassresearch.core.interpreter.api.transitions.UpdateTimeLimit;
import eu.compassresearch.core.interpreter.api.transitions.ops.Filter;
import eu.compassresearch.core.interpreter.api.transitions.ops.MapOperation;
import eu.compassresearch.core.interpreter.api.transitions.ops.RemoveChannelNames;
import eu.compassresearch.core.interpreter.api.transitions.ops.RetainChannelNames;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlChannel;
import eu.compassresearch.core.interpreter.api.values.NameValue;
import eu.compassresearch.core.interpreter.api.values.RenamingValue;
import eu.compassresearch.core.interpreter.runtime.DelayedWriteContext;
import eu.compassresearch.core.interpreter.runtime.DelayedWriteContext.INameFilter;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.interpreter.utility.Pair;

class CommonInspectionVisitor extends AbstractInspectionVisitor
{

	interface parallelCompositionHelper
	{
		void caseParallelBegin() throws AnalysisException;
	}

	public CommonInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess, CmlBehaviorFactory cmlBehaviorFactory,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{
		super(ownerProcess, visitorAccess, cmlBehaviorFactory, parentVisitor);
	}

	/**
	 * This returns a new CmlTransitionSet calculated from the current child behaviors. If possible it synchronizes tock
	 * transitions but just joins the others.
	 * 
	 * @return The joined child transitions where tock is synched if possible
	 * @throws AnalysisException
	 */
	protected CmlTransitionSet syncOnTimeAndJoinChildren()
			throws AnalysisException
	{
		return owner.getLeftChild().inspect().synchronizeOn(owner.getRightChild().inspect(), new SetValue(), true);
	}

	protected Inspection caseChannelRenaming(final INode node,
			final Context question) throws AnalysisException
	{

		final CmlBehaviour leftChild = owner.getLeftChild();

		if (!leftChild.finished())
		{
			RenamingValue rv = (RenamingValue) question.lookup(NamespaceUtility.getRenamingValueName());
			CmlTransitionSet childTransitions = leftChild.inspect();
			final HashMap<CmlTransition, CmlTransition> newtoOld = new HashMap<CmlTransition, CmlTransition>();
			for (Entry<ChannelValue, ChannelValue> pair : rv.renamingMap().entrySet())
			{
				CmlTransitionSet transitionsToBeRenamed = childTransitions.filter(new RetainChannelNames(pair.getKey()));
				// if this is true then we have remove the from channel and need to add the
				for (LabelledTransition toBeRenamed : transitionsToBeRenamed.filterByTypeAsSet(LabelledTransition.class))
				{
					childTransitions = childTransitions.filter(new RemoveChannelNames(pair.getKey()));
					LabelledTransition renamedtransition = toBeRenamed.rename(pair.getValue());
					childTransitions = childTransitions.union(renamedtransition);
					newtoOld.put(renamedtransition, toBeRenamed);
				}
			}

			return newInspection(childTransitions, new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					if (newtoOld.containsKey(selectedTransition))
					{
						leftChild.execute(newtoOld.get(selectedTransition));
					} else
					{
						leftChild.execute(selectedTransition);
					}

					return new Pair<INode, Context>(node, question);
				}
			});

		} else
		{
			final INode skipNode = CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node));
			return newInspection(createTauTransitionWithoutTime(skipNode), new CmlCalculationStep()
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					clearLeftChild();
					return new Pair<INode, Context>(skipNode, question.outer);
				}
			});
		}
	}

	protected Inspection caseAlphabetisedParallelism(final INode node,
			final parallelCompositionHelper helper, final Context question)
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
					helper.caseParallelBegin();
					// We push the current state, since this process will control the child processes created by it
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// the process has children and must now handle either termination or event sync
		else if (CmlBehaviourUtility.isAllChildrenFinished(owner))
		{
			ASkipAction dstNode = CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node));
			return newInspection(createTauTransitionWithoutTime(dstNode, "End"), caseParallelEnd(dstNode, question));
		} else
		{
			// The left and right channel sets has already been evaluated in the setup visitor and put in the context,
			// so we just fetch them.
			SetValue leftChanset = (SetValue) question.lookup(NamespaceUtility.getLeftPrecalculatedChannetSet());
			SetValue rightChanset = (SetValue) question.lookup(NamespaceUtility.getRightPrecalculatedChannetSet());

			// next we find the intersection of of them, since these are the ones that left and right must sync on
			SetValue intersectionChanset = new SetValue();
			
			for (Value lv : leftChanset.values)
			{
				ChannelValue clv = (ChannelValue) lv;
				for (Value rv : rightChanset.values)
				{
					ChannelValue crv = (ChannelValue) rv;
					if (clv.isComparable(crv)) {
						intersectionChanset.values.add(clv.meet(crv));
					}
				}
			}
			

			final CmlTransitionSet leftChildAlpha = owner.getLeftChild().inspect();
			final CmlTransitionSet rightChildAlpha = owner.getRightChild().inspect();

			/*
			 * First constrain the left/right children's alphabets down to only those that are actually allowed by the
			 * alphabetised parallel construct
			 */
			CmlTransitionSet constLeftChildAlpha = leftChildAlpha.constrainedExpand(leftChanset);
			CmlTransitionSet constRightChildAlpha = rightChildAlpha.constrainedExpand(rightChanset);

			/*
			 * The independent transitions are the ones that are defined in the corresponding channel set which is not
			 * in the intersection of the left and right channel set. This is calculated by taking the each child
			 * alphabet and first retain corresponding channel set and then remove the intersection.
			 */
			CmlTransitionSet leftIndependentTransitions = constLeftChildAlpha.filter(new RemoveChannelNames(intersectionChanset));
			leftIndependentTransitions = leftIndependentTransitions.removeAllType(TimedTransition.class);
			CmlTransitionSet rightIndependentTransitions = constRightChildAlpha.filter(new RemoveChannelNames(intersectionChanset));
			rightIndependentTransitions = rightIndependentTransitions.removeAllType(TimedTransition.class);

			// combine all the common channel events that are in the channel set
			CmlTransitionSet leftSyncTransitions = leftChildAlpha.filter(new RetainChannelNames(intersectionChanset));
			CmlTransitionSet rightSyncTransitions = rightChildAlpha.filter(new RetainChannelNames(intersectionChanset));
			SortedSet<CmlTransition> availSyncTransitions = new TreeSet<CmlTransition>();
			// Find the intersection between the child alphabets and the channel set and join them.
			// Then if both left and right have them the next step will combine them.
			for (ObservableTransition leftTrans : leftSyncTransitions.filterByTypeAsSet(ObservableTransition.class))
			{
				for (ObservableTransition rightTrans : rightSyncTransitions.filterByTypeAsSet(ObservableTransition.class))
				{
					if (leftTrans.isSynchronizableWith(rightTrans))
					{
						final ObservableTransition mergedTransition = leftTrans.synchronizeWith(rightTrans);
						
						//maybe this could be null
						availSyncTransitions.add(mergedTransition);
					}
				}
			}

			/*
			 * Finally we create the returned alphabet by joining all the Synchronized events together with all the
			 * event of the children that are not in the channel set.
			 */
			CmlTransitionSet resultAlpha = new CmlTransitionSet(availSyncTransitions).dunion(leftIndependentTransitions, rightIndependentTransitions);
			resultAlpha = resultAlpha.compress();

			return newInspection(resultAlpha, new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// if both contains the selected event it must be a sync event
					if (leftChildAlpha.containsEqualOrSyncPart(selectedTransition)
							&& rightChildAlpha.containsEqualOrSyncPart(selectedTransition))
					{
						owner.getLeftChild().execute(selectedTransition);
						owner.getRightChild().execute(selectedTransition);
					} else if (leftChildAlpha.containsEqualOrSyncPart(selectedTransition))
					{
						owner.getLeftChild().execute(selectedTransition);
					} else if (rightChildAlpha.containsEqualOrSyncPart(selectedTransition))
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

	/**
	 * Handles the external choice end rule
	 * 
	 * @return
	 * @throws AnalysisException
	 */
	protected Pair<INode, Context> caseExternalChoiceEnd(
			CmlBehaviour theChoosenOne, Context context)
			throws AnalysisException
	{
		Context newCurrentContext = theChoosenOne.getNextState().second;

		// TODO some how it is not the outer one in issue 235
		applyChangesInDelayedContext(newCurrentContext);

		if (theChoosenOne.getLeftChild() != null)
		{
			theChoosenOne.getLeftChild().replaceState(newCurrentContext);
		}

		setLeftChild(theChoosenOne.getLeftChild());

		if (theChoosenOne.getRightChild() != null)
		{
			theChoosenOne.getRightChild().replaceState(newCurrentContext);
		}

		setRightChild(theChoosenOne.getRightChild());

		/*
		 * The setup visitor CommonSetupVisitor#caseReplicated sets the field
		 * ConcreteCmlBehaviour.this.preConstructedChildContexts but if a recursion happens after the external choice
		 * then this pre-calculated field must be cleared before since setup relies on it being null at entry
		 */
		CommonInspectionVisitor.this.visitorAccess.setChildContexts(null);

		return new Pair<INode, Context>(theChoosenOne.getNextState().first, newCurrentContext);
	}

	/**
	 * Applies changes from the first active {@link DelayedWriteContext} and disables it.
	 * 
	 * @param delayedCtxt
	 * @param filter
	 * @throws ValueException
	 * @throws AnalysisException
	 */
	protected void applyChangesInDelayedContext(Context delayedCtxt,
			INameFilter filter) throws ValueException, AnalysisException
	{
		while (delayedCtxt != null)
		{
			if (delayedCtxt instanceof DelayedWriteContext)
			{
				DelayedWriteContext delayed = ((DelayedWriteContext) delayedCtxt);
				if (!delayed.isDisabled())
				{
					delayed.writeChanges(filter);
					break;
				}
			}
			delayedCtxt = delayedCtxt.outer;
		}
	}

	/**
	 * Applies changes from the first active {@link DelayedWriteContext} and disables it.
	 * 
	 * @param delayedCtxt
	 * @throws ValueException
	 * @throws AnalysisException
	 */
	protected void applyChangesInDelayedContext(Context delayedCtxt)
			throws ValueException, AnalysisException
	{
		applyChangesInDelayedContext(delayedCtxt, null);
	}

	/**
	 * External Choice section 7.5.4 D23.2 In terms of the alphabet, we have the following situations:
	 * <ul>
	 * <li>External Choice Begin: When no children exists, the External Choice Begin transition rule must be executed.
	 * This is a silent transition and therefore the alphabet contains only tau event</li>
	 * <li>External Choice Silent: If any of the actions can take a silent transition they will do it before getting
	 * here again. We therefore don't take this situation into account</li>
	 * <li>External Choice Skip: If one of the children is Skip we make a silent transition of the whole choice into
	 * skip. We therefore just return the tau event</li>
	 * <li>External Choice End: The alphabet contains an observable event for every child that can engaged in one.</li>
	 * </ul>
	 */
	protected Inspection caseAExternalChoice(final INode node,
			final INode leftNode, final INode rightNode, final Context question)
			throws AnalysisException
	{
		// if no children are present we make a silent transition to represent the
		// external choice begin
		if (!owner.hasChildren())
		{
			return newInspection(createTauTransitionWithoutTime(node, "Begin"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					Pair<Context, Context> childContexts = getChildContexts(question);

					setLeftChild(leftNode, name().clone(true), childContexts.first);

					setRightChild(rightNode, name().clone(true), childContexts.second);

					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// if one child is finished external choice must end
		// If this is true, the Skip rule is instantiated. This means that the entire choice evolves into Skip
		// with the state from the skip. After this all the children processes are terminated
		else if (CmlBehaviourUtility.finishedChildExists(owner))
		{
			return newInspection(createTauTransitionWithoutTime(node, "end"), new CmlCalculationStep()
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
		} else
		{
			return newInspection(syncOnTimeAndJoinChildren(), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					for (CmlBehaviour child : children())
					{
						if (child.inspect().containsEqualOrSyncPart(selectedTransition))
						{
							// first we execute the child
							child.execute(selectedTransition);
							if (selectedTransition instanceof LabelledTransition)
							{
								return caseExternalChoiceEnd(child, question);
							} else
							{
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
	 * Finds the first finished child if any
	 * 
	 * @return The first finished child, if none then null is returned
	 */
	protected CmlBehaviour findFinishedChild()
	{
		for (CmlBehaviour child : owner.children())
		{
			if (child.finished())
			{
				return child;
			}
		}

		return null;
	}

	protected void caseParallelNonSync(CmlTransition selectedTransition)
			throws AnalysisException
	{
		CmlBehaviour leftChild = owner.getLeftChild();
		CmlTransitionSet leftChildAlpha = owner.getLeftChild().inspect();
		CmlBehaviour rightChild = owner.getRightChild();
		CmlTransitionSet rightChildAlpha = rightChild.inspect();

		if (leftChildAlpha.containsEqualOrSyncPart(selectedTransition))
		{
			leftChild.execute(selectedTransition);
		} else if (rightChildAlpha.containsEqualOrSyncPart(selectedTransition))
		{
			rightChild.execute(selectedTransition);
		} else
		{
			throw new CmlInterpreterException("A selected event that should have affected either left or right");
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
		// if true this means that this is the first time here, so the Parallel Begin rule is invoked.
		if (!owner.hasChildren())
		{
			return newInspection(createTauTransitionWithoutTime(node, "Begin"),

			new CmlCalculationStep()
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
			ASkipAction dstNode = CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node));
			return newInspection(createTauTransitionWithoutTime(dstNode, "End"), caseParallelEnd(dstNode, question));
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
		SetValue cs = eval(chansetExp, question);

		// Get all the child alphabets and add the events that are not in the channel set
		final CmlBehaviour leftChild = owner.getLeftChild();
		final CmlTransitionSet leftChildAlphabet = leftChild.inspect();
		final CmlBehaviour rightChild = owner.getRightChild();
		final CmlTransitionSet rightChildAlphabet = rightChild.inspect();

		CmlTransitionSet resultAlpha = leftChildAlphabet.synchronizeOn(rightChildAlphabet, cs);

		return newInspection(resultAlpha, new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// if both contains the selected event it must be a sync event
				if (leftChildAlphabet.containsEqualOrSyncPart(selectedTransition)
						&& rightChildAlphabet.containsEqualOrSyncPart(selectedTransition))
				{
					leftChild.execute(selectedTransition);
					rightChild.execute(selectedTransition);
				} else if (leftChildAlphabet.containsEqualOrSyncPart(selectedTransition))
				{
					leftChild.execute(selectedTransition);
				} else if (rightChildAlphabet.containsEqualOrSyncPart(selectedTransition))
				{
					rightChild.execute(selectedTransition);
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

	protected CmlCalculationStep caseParallelEnd(final INode node,
			final Context question)
	{
		final boolean isProcess = (this instanceof ProcessInspectionVisitor);
		return new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				Context leftChildContext = owner.getLeftChild().getNextState().second;
				Context rightChildContext = owner.getRightChild().getNextState().second;

				INameFilter leftNameFilter = null;
				INameFilter rightNameFilter = null;

				final SetValue leftNameset = (SetValue) leftChildContext.check(NamespaceUtility.getNamesetName());
				Set<ILexNameToken> leftNames = new HashSet<ILexNameToken>();
				if (leftNameset != null)
				{
					leftNames = copyValues(leftNameset, leftChildContext, question);
				}

				leftNameFilter = new NameSetFilter(leftNames);

				SetValue rightNameset = (SetValue) rightChildContext.check(NamespaceUtility.getNamesetName());

				Set<ILexNameToken> rightNames = new HashSet<ILexNameToken>();

				if (rightNameset != null)
				{
					rightNames = copyValues(rightNameset, rightChildContext, question);
				}

				rightNameFilter = new NameSetFilter(rightNames);

				if (!isProcess)
				{
					applyChangesInDelayedContext(leftChildContext, leftNameFilter);
					applyChangesInDelayedContext(rightChildContext, rightNameFilter);

					applyChangesInDelayedContext(question);
				}

				clearLeftChild();
				clearRightChild();

				// now this process evolves into Skip
				return new Pair<INode, Context>(node, question);
			}

			/**
			 * Utility method to copy named from one context to an other, where the names are given by the nameset and
			 * all names are returned after
			 * 
			 * @param nameset
			 *            the names to copy
			 * @param fromCtxt
			 *            the context where the names exist
			 * @param toCtxt
			 *            the context to copy to
			 * @return a set of all names
			 * @throws ValueException
			 *             if the name faild to evaulate
			 * @throws AnalysisException
			 */
			protected Set<ILexNameToken> copyValues(final SetValue nameset,
					Context fromCtxt, final Context toCtxt)
					throws ValueException, AnalysisException
			{
				Set<ILexNameToken> leftNames = new HashSet<ILexNameToken>();
				for (Value val : nameset.values)
				{
					if (val instanceof NameValue)
					{
						ILexNameToken name = ((NameValue) val).name;
						leftNames.add(name);
						toCtxt.lookup(name).set(name.getLocation(), fromCtxt.lookup(name), toCtxt);
					} else
					{
						throw new InterpreterRuntimeException("Only "
								+ NameValue.class.getSimpleName()
								+ " must be present in a name value set. Actual: "
								+ val);
					}
				}
				return leftNames;
			}
		};
	}

	private static class NameSetFilter implements INameFilter
	{
		private Set<ILexNameToken> names;

		public NameSetFilter(Set<ILexNameToken> names)
		{
			this.names = names;
		}

		@Override
		public boolean accept(ILexNameToken name)
		{
			if (names != null)
			{
				for (ILexNameToken n : names)
				{
					if (HackLexNameToken.isEqual(n, name))
					{
						return true;
					}
				}
			}
			return false;
		}
	}

	protected SetValue eval(PVarsetExpression chansetExpression,
			Context question) throws AnalysisException
	{
		return (SetValue) chansetExpression.apply(cmlExpressionVisitor, question);
	}

	/**
	 * Common Hiding handler methods
	 * 
	 * @throws AnalysisException
	 */
	protected Inspection caseHiding(final INode node,
			final PVarsetExpression chansetExpression, final Context question)
			throws AnalysisException
	{
		// We do the hiding behavior as long as the Action is not terminated
		if (!owner.getLeftChild().finished())
		{
			// next we inspect the action to get the current available transitions
			final CmlTransitionSet childTransitions = owner.getLeftChild().inspect();
			CmlTransitionSet resultAlpha = childTransitions.map(new MapOperation()
			{
				// evaluate the hidden channel set and initialize the filter with it which
				// determine whether a given transition is covered by the hidden channel set or not
				Filter filter = new RetainChannelNames(eval(chansetExpression, question));

				@Override
				public CmlTransition apply(CmlTransition transition)
				{
					/*
					 * determine if this is in the hidden channel set and if so we convert it into a hiddenTransition
					 * otherwise we just keep it as it is
					 */
					if (filter.isAccepted(transition))
					{
						return new HiddenTransition(owner, node, (LabelledTransition) transition);
					} else
					{
						return transition;
					}
				}
			});

			return newInspection(resultAlpha, new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					if (selectedTransition instanceof HiddenTransition
							&& childTransitions.containsEqualOrSyncPart(((HiddenTransition) selectedTransition).getHiddenEvent()))
					{
						selectedTransition = ((HiddenTransition) selectedTransition).getHiddenEvent();
					}

					owner.getLeftChild().execute(selectedTransition);
					return new Pair<INode, Context>(node, question);
				}
			});
		}
		// If the Action is terminated then it evolves into Skip
		else
		{
			return newInspection(createTauTransitionWithTime(CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node))), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					clearLeftChild();
					return new Pair<INode, Context>(CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node)), question);
				}
			});
		}
	}

	protected Inspection caseAUntimedTimeout(final INode node,
			final INode rightNode, final Context question)
			throws AnalysisException
	{
		// the alphabet still need to be calculated before this is done, so uncomment when done
		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					CmlBehaviour leftChild = owner.getLeftChild();
					clearLeftChild();
					clearRightChild();
					return leftChild.getNextState();
				}
			});
		}
		// Make a random decision whether the process should timeout and
		// behaves as the right process
		else if (this.rnd.nextBoolean())
		{
			return newInspection(createTauTransitionWithTime(rightNode), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We set the process to become the right behavior
					clearLeftChild();
					return new Pair<INode, Context>(rightNode, question);
				}
			});
		}
		// if no timeout has occurred the whole process behaves as the left process
		else
		{
			return newInspection(owner.getLeftChild().inspect(), new CmlCalculationStep()
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
						clearLeftChild();
						return new Pair<INode, Context>(leftBehavior.getNextState().first, leftBehavior.getNextState().second);
					} else
					{
						return new Pair<INode, Context>(node, question);
					}
				}
			});
		}
	}

	/**
	 * Timed interrupt is implemented according to the following rules in the operational semantics D23.4 R3.21 (Timed
	 * Interrupt progress left) R3.22 (Timed Interrupt Skip left) R3.23 (Timed Interrupt time up) R3.24 (Timed Interrupt
	 * timed)
	 * 
	 * @param node
	 *            The action/process timed interrupt node
	 * @param leftNode
	 *            the left action/process
	 * @param rightNode
	 *            the right action/process
	 * @param timeoutExpression
	 *            the timeout expression
	 * @param question
	 * @return
	 * @throws AnalysisException
	 */
	protected Inspection caseATimedInterrupt(final INode node, INode leftNode,
			final INode rightNode, PExp timeoutExpression,
			final Context question) throws AnalysisException
	{
		// Evaluate the expression into a natural number
		long val = timeoutExpression.apply(cmlExpressionVisitor, question).natValue(question);
		long startTimeVal = question.lookup(NamespaceUtility.getStartTimeName()).intValue(question);

		// R3.22: If left is Skip then the whole becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithoutTime(owner.getLeftChild().getNextState().first, "timed interrupt: left behavior is finished"), new CmlCalculationStep()
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
		// R3.23: if the current time of the process has passed the limit (val) then process
		// behaves as the right process
		else if (owner.getCurrentTime() - startTimeVal >= val)
		{
			return newInspection(createTauTransitionWithoutTime(rightNode, "Timeout: time exceeded"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We set the process to become the right behavior
					clearLeftChild();
					// We need to return the outer context because of the extra context
					// containing the start time has been added in the setup visitor
					return new Pair<INode, Context>(rightNode, question.outer);
				}
			});

		}
		// if the current time of the process has not passed the limit (val) it behaves as the left process
		else
		{
			CmlTransitionSet leftAlpha = owner.getLeftChild().inspect();
			return newInspection(leftAlpha, new CmlCalculationStep()
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
	}

	/**
	 * This implements the startsby operator which is defined as. So assume we have A startsby e then A must execute an
	 * observable event within e time units. Otherwise, the process is infeasible. In other words it throws a
	 * postcondition Exception if the process has no observable behavior within e timeunits
	 * 
	 * @throws AnalysisException
	 */
	protected Inspection caseStartDeadline(final INode node,
			final INode leftNode, PExp timeExpression, final Context question)
			throws AnalysisException
	{
		// Evaluate the expression into a natural number
		final long val = timeExpression.apply(cmlExpressionVisitor, question).natValue(question);
		final long startTimeVal = question.lookup(NamespaceUtility.getStartsByTimeName()).natValue(question);

		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first, "Timeout: left behavior is finished"), new CmlCalculationStep()
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
		// if the current time of the process has passed the limit (val) then
		// a post condition exception is thrown
		else if (owner.getCurrentTime() - startTimeVal >= val)
		{
			throw new ValueException(4072, "Postcondition failure: This process is infeasable since it exceeded the start deadline without any observable events", question);
		}
		// if the current time of the process has not passed the limit (val) and the left process
		// makes an observable transition then the whole process behaves as the left process
		else
		{
			//
			final CmlBehaviour leftBehavior = owner.getLeftChild();

			// If time can pass in the left, we need to put the remaining time of the timeout
			CmlTransitionSet resultAlpha = leftBehavior.inspect().map(new UpdateTimeLimit(owner, val, startTimeVal));

			return newInspection(resultAlpha, new CmlCalculationStep()
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
					{
						return new Pair<INode, Context>(node, question);
					}
				}
			});
		}
	}

	/**
	 * @throws AnalysisException
	 */
	protected Inspection caseEndDeadline(final INode node,
			final INode leftNode, PExp timeExpression, final Context question)
			throws AnalysisException
	{
		// Evaluate the expression into a natural number
		final long val = timeExpression.apply(cmlExpressionVisitor, question).natValue(question);
		final long startTimeVal = question.lookup(NamespaceUtility.getEndsByTimeName()).natValue(question);

		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first, "Timeout: left behavior is finished"), new CmlCalculationStep()
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
		// if the current time of the process has passed the limit (val) then
		// a post condition exception is thrown
		else if (owner.getCurrentTime() - startTimeVal >= val)
		{
			throw new ValueException(4072, "Postcondition failure: This process is infeasable since it did not successfully terminate before the deadline", question);
		}
		// if the current time of the process has not passed the limit (val) and the left process
		// makes an observable transition then the whole process behaves as the left process
		else
		{
			//
			final CmlBehaviour leftBehavior = owner.getLeftChild();

			// If time can pass in the left, we need to put the remaining time of the timeout
			CmlTransitionSet resultAlpha = leftBehavior.inspect().map(new UpdateTimeLimit(owner, val, startTimeVal));

			return newInspection(resultAlpha, new CmlCalculationStep()
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					leftBehavior.execute(selectedTransition);
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
		long startTimeVal = question.lookup(NamespaceUtility.getStartTimeName()).natValue(question);
		// If the left is Skip then the whole process becomes skip with the state of the left child
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithTime(owner.getLeftChild().getNextState().first, "Timeout: left behavior is finished"), new CmlCalculationStep()
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
			return newInspection(createTauTransitionWithoutTime(rightNode, "Timeout: time exceeded"), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// We set the process to become the right behavior
					clearLeftChild();
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
			if (leftAlpha.hasType(TimedTransition.class))
			{
				TimedTransition leftTimeTransition = leftAlpha.firstOfType(TimedTransition.class);
				resultAlpha = leftAlpha.subtract(leftTimeTransition);
				long limit = val - (owner.getCurrentTime() - startTimeVal);
				resultAlpha = resultAlpha.union(leftTimeTransition.synchronizeWith(new TimedTransition(owner, limit)));
			} else
			{
				resultAlpha = leftAlpha;
			}

			return newInspection(resultAlpha, new CmlCalculationStep()
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
					{
						return new Pair<INode, Context>(node, question);
					}
				}
			});

		}
	}

	protected Inspection caseAInterrupt(final INode node, final Context question)
			throws AnalysisException
	{

		// If the left child is Skip then the interrupt construct is Skip
		if (owner.getLeftChild().finished())
		{
			return newInspection(createTauTransitionWithoutTime(owner.getLeftChild().getNextState().first),

			new CmlCalculationStep()
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
		// If the right action has taken a labeled transition then the whole becomes the right action
		else if (owner.getRightChild().getTraceModel().getLastTransition() instanceof LabelledTransition)
		{
			return newInspection(createTauTransitionWithoutTime(owner.getRightChild().getNextState().first),

			new CmlCalculationStep()
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
			return newInspection(syncOnTimeAndJoinChildren(),

			new CmlCalculationStep()
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
		{
			if (entry.getValue() instanceof CmlChannel)
			{
				channelNames.add(CmlAstFactory.newANameChannelExp(entry.getKey().getLocation(), entry.getKey().clone(), new LinkedList<PExp>()));
			}
		}

		return CmlAstFactory.newAFatEnumVarsetExpression(location, channelNames);
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
			return newInspection(owner.getLeftChild().inspect(), new CmlCalculationStep()
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
			return newInspection(createTauTransitionWithoutTime(leftNode), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					clearLeftChild();
					clearRightChild();

					return new Pair<INode, Context>(rightNode, getChildContexts(question).second);
				}
			});

		}
	}

}
