package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AAtomicStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallObjectStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStm;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.statements.AAltNonDeterministicStm;
import eu.compassresearch.ast.statements.ADoNonDeterministicStm;
import eu.compassresearch.ast.statements.AIfNonDeterministicStm;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.CmlCalculationStep;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class StatementInspectionVisitor extends AbstractInspectionVisitor
{

	public StatementInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			CmlBehaviorFactory cmlBehaviorFactory,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{

		super(ownerProcess, visitorAccess, cmlBehaviorFactory, parentVisitor);
	}

	@Override
	public Inspection defaultPStm(PStm node, Context question)
			throws AnalysisException
	{
		throw new CmlInterpreterException(node, InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	@Override
	public Inspection caseALetStm(final ALetStm node, final Context question)
			throws AnalysisException
	{

		return newInspection(createTauTransitionWithoutTime(node.getStatement()), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// We only create a new context if any vars are declared
				Context blockContext = question;

				// add the assignment definitions to the block context
				if (node.getLocalDefs() != null)
				{
					blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);

					for (PDefinition def : node.getLocalDefs())
					{
						NameValuePair nvp = def.apply(cmlDefEvaluator, blockContext).get(0);
						blockContext.put(nvp.name, nvp.value.getUpdatable(null));
					}
				}

				return new Pair<INode, Context>(node.getStatement(), blockContext);
			}
		});
	}

	@Override
	public Inspection caseABlockSimpleBlockStm(final ABlockSimpleBlockStm node,
			final Context question) throws AnalysisException
	{
		final PStm nextStm = node.getStatements().get(0);

		return newInspection(createTauTransitionWithoutTime(nextStm), new CmlCalculationStep()
		{
			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				Context blockContext = question;
				// add the assignment definitions to the block context
				if (node.getAssignmentDefs() != null)
				{
					blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);

					for (PDefinition def : node.getAssignmentDefs())
					{
						NameValuePair nvp = def.apply(cmlDefEvaluator, question).get(0);
						blockContext.put(nvp.name, nvp.value.getUpdatable(null));
					}
				}

				return new Pair<INode, Context>(nextStm, blockContext);
			}
		});
	}

	@Override
	public Inspection caseACallObjectStm(final ACallObjectStm node,
			final Context question) throws AnalysisException
	{
		return newInspection(createTauTransitionWithoutTime(node), new CmlCalculationStep()
		{
			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				node.apply(cmlExpressionVisitor, question);
				return new Pair<INode, Context>(CmlAstFactory.newASkipAction(node.getLocation()), question);
			}

		});
	}

	/*
	 * 
	 */
	@Override
	public Inspection caseACallStm(final ACallStm node, final Context question)
			throws AnalysisException
	{

		// if (!owner.hasChildren())
		// {

		return newInspection(createTauTransitionWithoutTime(node), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				// first find the operation value in the context
				OperationValue opVal = (OperationValue) question.lookup(node.getName()).deref();

				if (opVal.body == null)
				{
					throw new CmlInterpreterException(node, InterpretationErrorMessages.EVAL_OF_IMPLICIT_OP.customizeMessage(node.getName().toString()));
				}

				// evaluate all the arguments
				ValueList argValues = new ValueList();
				for (PExp arg : node.getArgs())
				{
					argValues.add(arg.apply(cmlExpressionVisitor, question));
				}

				// Note args cannot be Updateable, so we convert them here. This means
				// that TransactionValues pass the local "new" value to the far end.
				// ValueList constValues = argValues.getConstant();

				opVal.eval(node.getLocation(), argValues, question);

				return new Pair<INode, Context>(CmlAstFactory.newASkipAction(node.getLocation()), question);
			}
		});

	}

	@Override
	public Inspection caseAIfStm(final AIfStm node, final Context question)
			throws AnalysisException
	{

		if (node.getIfExp().apply(cmlExpressionVisitor, question).boolValue(question))
		{
			return newInspection(createTauTransitionWithTime(node.getThenStm()), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(node.getThenStm(), question);
				}
			});
		} else
		{
			for (final AElseIfStm elseif : node.getElseIf())
			{
				if (elseif.getElseIf().apply(cmlExpressionVisitor, question).boolValue(question))
				{
					return newInspection(createTauTransitionWithTime(elseif.getThenStm()), new CmlCalculationStep()
					{

						@Override
						public Pair<INode, Context> execute(
								CmlTransition selectedTransition)
								throws AnalysisException
						{
							return new Pair<INode, Context>(elseif.getThenStm(), question);
						}
					});
				}
			}

			if (node.getElseStm() != null)
			{
				return newInspection(createTauTransitionWithTime(node.getElseStm()), new CmlCalculationStep()
				{

					@Override
					public Pair<INode, Context> execute(
							CmlTransition selectedTransition)
							throws AnalysisException
					{
						return new Pair<INode, Context>(node.getElseStm(), question);
					}
				});
			}
		}

		final ASkipAction skipAction = CmlAstFactory.newASkipAction(node.getLocation());
		return newInspection(createTauTransitionWithoutTime(skipAction), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return new Pair<INode, Context>(skipAction, question);
			}
		});
	}

	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public Inspection caseAIfNonDeterministicStm(AIfNonDeterministicStm node,
			final Context question) throws AnalysisException
	{

		List<AAltNonDeterministicStm> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(node.getAlternatives(), question, cmlExpressionVisitor);

		if (availableAlts.size() > 0)
		{
			final INode next = availableAlts.get(rnd.nextInt(availableAlts.size())).getAction();

			return newInspection(createTauTransitionWithTime(next), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					// if we got here we already now that the must at least be one available action
					// so this should pose no risk of exception
					return new Pair<INode, Context>(next, question);

				}
			});
		}
		// If no alternative are true then the whole thing diverges
		else
		{
			@SuppressWarnings("deprecation")
			final ADivAction divAction = new ADivAction(node.getLocation());
			return newInspection(createTauTransitionWithoutTime(divAction), new CmlCalculationStep()
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(divAction, question);
				}
			});
		}
	}

	/**
	 * 
	 */
	@Override
	public Inspection caseADoNonDeterministicStm(
			final ADoNonDeterministicStm node, final Context question)
			throws AnalysisException
	{

		List<AAltNonDeterministicStm> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(node.getAlternatives(), question, cmlExpressionVisitor);

		if (availableAlts.size() > 0)
		{
			// first we push the do node on the execution stack to get it sequentially composed with the
			// picked alternative
			// if we got here we already now that the must at least be one available action
			// so this should pose no risk of exception
			@SuppressWarnings("deprecation")
			final INode nextNode = new ASequentialCompositionAction(node.getLocation(), ActionVisitorHelper.wrapStatement(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction().clone()), ActionVisitorHelper.wrapStatement(node.clone()));
			return newInspection(createTauTransitionWithTime(nextNode), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					return new Pair<INode, Context>(nextNode, question);
				}
			});

		} else
		{
			final ASkipAction skipAction = CmlAstFactory.newASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithoutTime(skipAction), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(skipAction, question);
				}
			});
		}

	}

	/**
	 * Assignment - section 7.5.1 D23.2
	 */
	@Override
	public Inspection caseAAssignmentStm(final AAssignmentStm node,
			final Context question) throws AnalysisException
	{
		final INode skipNode = CmlAstFactory.newASkipAction(node.getLocation());
		// FIXME according to the semantics this should be performed instantly so time is not
		// allowed to pass
		return newInspection(createTauTransitionWithoutTime(skipNode), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return evalSingleAssignmentStatement(node, question, skipNode, true);
			}
		});
	}

	@Override
	public Inspection caseAAtomicStm(final AAtomicStm node,
			final Context question) throws AnalysisException
	{
		final INode skipNode = CmlAstFactory.newASkipAction(node.getLocation());
		// FIXME according to the semantics this should be performed instantly so time is not
		// allowed to pass
		return newInspection(createTauTransitionWithoutTime(skipNode), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				Pair<INode, Context> result = null;
				for (Iterator<AAssignmentStm> itr = node.getAssignments().iterator(); itr.hasNext();)
				{
					result = evalSingleAssignmentStatement(itr.next(), question, skipNode, !itr.hasNext());
				}
				return result;
			}
		});
	}

	private Pair<INode, Context> evalSingleAssignmentStatement(
			final AAssignmentStm node, final Context question,
			final INode skipNode, boolean checkInv) throws AnalysisException,
			ValueException
	{
		Value expValue = node.getExp().apply(cmlExpressionVisitor, question);
		Value oldVal = node.getTarget().apply(cmlExpressionVisitor, question);
		oldVal.set(node.getLocation(), expValue, question);

		PExp invExp = null;
		if (question.getSelf() instanceof ProcessObjectValue)
		{
			invExp = ((ProcessObjectValue) question.getSelf()).getInvariantExpression();
		}

		if (invExp != null && checkInv)
		{

			Context invContext = CmlContextFactory.newContext(invExp.getLocation(), "Process "
					+ question.getSelf() + " invariant context", question);
			invContext.setPrepost(0, "Process invariant for '"
					+ ((ProcessObjectValue) question.getSelf()).getProcessDefinition()
					+ "' is violated");

			return new Pair<INode, Context>(invExp, invContext);
		} else
		{
			// now this process evolves into Skip
			return new Pair<INode, Context>(skipNode, question);
		}
	}

	@Override
	public Inspection caseAForPatternBindStm(final AForPatternBindStm node,
			final Context question) throws AnalysisException
	{

		final ValueList v = question.lookup(NamespaceUtility.getSeqForName()).seqValue(question);

		// if the sequence is empty we're done and evolve into skip
		if (v.isEmpty() && owner.hasChildren()
				&& owner.getLeftChild().finished())
		{
			final ASkipAction skipAction = CmlAstFactory.newASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithTime(skipAction), new CmlCalculationStep()
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// clear the child nodes
					clearLeftChild();
					return new Pair<INode, Context>(skipAction, question.outer);
				}
			});
		}

		// if the sequence is non empty and we either have no or a finished child
		// we need to create a new one
		else if (!owner.hasChildren() || owner.getLeftChild().finished())
		{
			// put the front element in scope of the action
			Value x = v.firstElement();
			v.remove(x);

			if (node.getPatternBind().getPattern() != null)
			{
				try
				{
					question.putList(PPatternAssistantInterpreter.getNamedValues(node.getPatternBind().getPattern(), x, question));
				} catch (PatternMatchException e)
				{
					// Ignore mismatches
				}
			}

			setLeftChild(node.getStatement(), question);
		}

		return newInspection(owner.getLeftChild().inspect(), new CmlCalculationStep()
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{

				owner.getLeftChild().execute(selectedTransition);

				return new Pair<INode, Context>(node, question);
			}
		});

	}

	/**
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public Inspection caseAWhileStm(final AWhileStm node, final Context question)
			throws AnalysisException
	{

		if (node.getExp().apply(cmlExpressionVisitor, question).boolValue(question))
		{
			// the next step is a sequential composition of the action and this node
			return newInspection(createTauTransitionWithTime(node), new CmlCalculationStep()
			{

				@SuppressWarnings("deprecation")
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(new ASequentialCompositionAction(node.getStatement().getLocation(), ActionVisitorHelper.wrapStatement(node.getStatement().clone()), ActionVisitorHelper.wrapStatement(node.clone())), question);
				}
			});
		} else
		{
			// if the condition is false then the While evolves into Skip
			final INode skipNode = CmlAstFactory.newASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithTime(skipNode), new CmlCalculationStep()
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(skipNode, question);
				}
			});
		}

	}
}
