package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AAtomicStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetStm;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.statements.PStm;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.VoidValue;

import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.statements.AAltNonDeterministicStm;
import eu.compassresearch.ast.statements.ADoNonDeterministicStm;
import eu.compassresearch.ast.statements.AIfNonDeterministicStm;
import eu.compassresearch.ast.statements.ANewStm;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;
import eu.compassresearch.core.interpreter.api.values.ProcessObjectValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class StatementInspectionVisitor extends AbstractInspectionVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatementInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor)
	{

		super(ownerProcess, visitorAccess, parentVisitor);
	}

	@Override
	public Inspection defaultPStm(PStm node,
			Context question) throws AnalysisException
	{
		throw new CmlInterpreterException(node, InterpretationErrorMessages.CASE_NOT_IMPLEMENTED.customizeMessage(node.getClass().getSimpleName()));
	}

	/**
	 * This methods splits the assignment call statement into the call and the assignment statements and.
	 */
	public Inspection caseAssignmentCall(
			final AAssignmentStm node, final Context question)
			throws AnalysisException
	{

		return newInspection(createTauTransitionWithTime(node, null), new AbstractCalculationStep(owner, visitorAccess)
		{
			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				AApplyExp apply = (AApplyExp)node.getExp();
				// put return value in a new context
				Context resultContext = CmlContextFactory.newContext(node.getLocation(), "Call Result Context", question);
				// put return value in upper context if the parent is a AAssignmentCallStatementAction
				resultContext.putNew(new NameValuePair(CmlOperationValue.ReturnValueName(), new UndefinedValue()));

				// To access the result we put it in a Value named "|CALL|.|CALLRETURN|" this can never be created
				// in a cml model. This is a little ugly but it works and statys until something better comes up.
				@SuppressWarnings("deprecation")
				AVariableExp varExp = new AVariableExp(node.getType(), node.getLocation(), CmlOperationValue.ReturnValueName(), "", null);
				// Next we create the assignment statement with the expressions that graps the result
				@SuppressWarnings("deprecation")
				AAssignmentStm assignmentNode = new AAssignmentStm(node.getLocation(), node.getTarget().clone(), varExp);

				PExp root = apply.getRoot();
				ACallStm call = new ACallStm(apply.getLocation(),null,apply.getArgs());
				
				// We now compose the call statement and assignment statement into sequential composition
				@SuppressWarnings("deprecation")
				INode seqComp = new ASequentialCompositionAction(node.getLocation(),ActionVisitorHelper.wrapStatement(call), ActionVisitorHelper.wrapStatement(assignmentNode.clone()));
				return new Pair<INode, Context>(seqComp, resultContext);
			}
		});

	}

	@Override
	public Inspection caseALetStm(
			final ALetStm node, final Context question)
			throws AnalysisException
	{

		return newInspection(createTauTransitionWithoutTime(node.getStatement()), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				//We only create a new context if any vars are declared
				Context blockContext = question;

				// add the assignment definitions to the block context
				if (node.getLocalDefs() != null)
				{
					blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);

					for (PDefinition def : node.getLocalDefs())
					{
						NameValuePair nvp = def.apply(cmlDefEvaluator, question).get(0);
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
		
		return newInspection(createTauTransitionWithoutTime(nextStm), new AbstractCalculationStep(owner, visitorAccess)
		{
			@Override
			public Pair<INode, Context> execute(
					CmlTransition selectedTransition)
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

	/*
	 * 
	 */
	@Override
	public Inspection caseACallStm(final ACallStm node,
			final Context question) throws AnalysisException
	{

		if (!owner.hasChildren())
		{

			return newInspection(createTauTransitionWithoutTime(node), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// first find the operation value in the context
					CmlOperationValue opVal = (CmlOperationValue) lookupName(node.getName(), question);

					if (opVal.getBody() == null)
						throw new CmlInterpreterException(node, InterpretationErrorMessages.EVAL_OF_IMPLICIT_OP.customizeMessage(node.getName().toString()));

					// evaluate all the arguments
					ValueList argValues = new ValueList();
					for (PExp arg : node.getArgs())
					{
						argValues.add(arg.apply(cmlExpressionVisitor, question));
					}

					// Note args cannot be Updateable, so we convert them here. This means
					// that TransactionValues pass the local "new" value to the far end.
					ValueList constValues = argValues.getConstant();
					
					opVal.eval(node.getLocation(), argValues, question);
					
//					// find the self object
//					ObjectValue self = null;
//					if (opVal.getSelf() != null)
//						self = opVal.getSelf();
//					else
//						self = question.getSelf();
//
//					// Create a new object context to perform the operation call
//					Context callContext = CmlContextFactory.newObjectContext(opVal.getBody().getLocation(), "CML Operation Call", question, self);
//
//					if (constValues.size() != opVal.getParamPatterns().size())
//					{
//						opVal.abort(4068, "Wrong number of arguments passed to "
//								+ node.getName(), question);
//					}
//
//					ListIterator<Value> valIter = constValues.listIterator();
//					Iterator<PType> typeIter = opVal.getType().getParameters().iterator();
//					NameValuePairMap args = new NameValuePairMap();
//
//					for (PPattern p : opVal.getParamPatterns())
//					{
//						try
//						{
//							// Note values are assumed to be constant, as enforced by eval()
//							Value pv = valIter.next().convertTo(typeIter.next(), question);
//
//							for (NameValuePair nvp : PPatternAssistantInterpreter.getNamedValues(p, pv, question))
//							{
//								Value v = args.get(nvp.name);
//
//								if (v == null)
//								{
//									args.put(nvp);
//								} else
//								// Names match, so values must also
//								{
//									if (!v.equals(nvp.value))
//									{
//										opVal.abort(4069, "Parameter patterns do not match arguments", question);
//									}
//								}
//							}
//						} catch (PatternMatchException e)
//						{
//							opVal.abort(e.number, e, question);
//						}
//					}
//
//					// Note: arg name/values hide member values
//					callContext.putAll(args);
//
//					//opVal.eval(node.getLocation(), argValues, callContext);
//
//					// push the pre condition on the execution stack so it gets executed first
//					if (opVal.getPrecondition() != null)
//					{
//
//						PExp preExpNode = opVal.getPrecondition();
//						Context preConditionContext = CmlContextFactory.newContext(preExpNode.getLocation(), "Operation "
//								+ node.getName() + " precondition context", callContext);
//
//						callContext.setPrepost(0, "precondition violated for call "
//								+ node.getName());
//
//						if (!preExpNode.apply(cmlExpressionVisitor, preConditionContext).boolValue(question))
//						{
//							throw new ValueException(4061, "precondition violated for call "
//									+ node.getName(), question);
//						}
//
//					}
//
//					if (opVal.getPostcondition() != null)
//					{
//
//						PExp postExpNode = opVal.getPostcondition();
//
//						Context postConditionContext = CmlContextFactory.newContext(postExpNode.getLocation(), "Operation "
//								+ node.getName() + " postcondition context", callContext);
//
//						// set the old values
//						for (NameValuePair nvp : postConditionContext.getSelf().getMemberValues().asList())
//							if (UpdatableValue.class.isAssignableFrom(nvp.value.getClass()))
//							{
//								// FIXME it does not work when the module is there
//								LexNameToken oldName = new LexNameToken("", (ILexIdentifierToken) nvp.name.getOldName().getIdentifier().clone());
//								postConditionContext.putNew(new NameValuePair(oldName, nvp.value.getConstant()));
//							}
//
//						postConditionContext.setPrepost(0, "postcondition violated for "
//								+ node.getName());
//
//						setRightChild(new ConcreteCmlBehaviour(postExpNode, postConditionContext, owner));
//
//						// We now compose the call statement and post condition check into sequential composition
//						// INode seqComp = new ASequentialCompositionAction(node.getLocation(), opVal.getBody(),
//						// assignmentNode);
//						// return new Pair<INode, Context>(seqComp,resultContext);
//					}
//					// the left child is the actual call executing
//					setLeftChild(new ConcreteCmlBehaviour(opVal.getBody(), callContext, owner));
					return new Pair<INode, Context>(new ASkipAction(node.getLocation()), question);
				}
			});

		}
		// The left child is the actual call so if it is not terminated then we execute
		// the next action of it
		else if (!owner.getLeftChild().finished())
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
		// The right child contains the postcondition of the call, so if we are here and its
		// non-empty then we execute it
		else if (owner.getRightChild() != null)
		{
			return newInspection(owner.getRightChild().inspect(), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					owner.getRightChild().execute(selectedTransition);
					setLeftChild(null);
					setRightChild(null);
					return new Pair<INode, Context>(new ASkipAction(), question);
				}
			});

		} else
		{
			final INode skipNode = new ASkipAction();
			return newInspection(createTauTransitionWithoutTime(skipNode), new AbstractCalculationStep(owner, visitorAccess)
			{

				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{

					setLeftChild(null);
					return new Pair<INode, Context>(skipNode, question);
				}
			});
		}
	}

	@Override
	public Inspection caseAIfStm(final AIfStm node,
			final Context question) throws AnalysisException
	{

		if (node.getIfExp().apply(cmlExpressionVisitor, question).boolValue(question))
		{
			return newInspection(createTauTransitionWithTime(node.getThenStm()), new AbstractCalculationStep(owner, visitorAccess)
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
					return newInspection(createTauTransitionWithTime(elseif.getThenStm()), new AbstractCalculationStep(owner, visitorAccess)
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
				return newInspection(createTauTransitionWithTime(node.getElseStm()), new AbstractCalculationStep(owner, visitorAccess)
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

		@SuppressWarnings("deprecation")
		final ASkipAction skipAction = new ASkipAction(node.getLocation());
		return newInspection(createTauTransitionWithoutTime(skipAction), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return new Pair<INode, Context>(skipAction, question);
			}
		});
	}

//	@Override
//	public Inspection caseALetStatementAction(final ALetStatementAction node,
//			final Context question) throws AnalysisException
//	{
//
//		return newInspection(createTauTransitionWithTime(node.getAction()), new AbstractCalculationStep(owner, visitorAccess)
//		{
//
//			@Override
//			public Pair<INode, Context> execute(CmlTransition selectedTransition)
//					throws AnalysisException
//			{
//
//				// Create a new context for the let statement
//				Context letContext = CmlContextFactory.newContext(node.getLocation(), "let action context", question);
//
//				for (PDefinition localDef : node.getLocalDefinitions())
//					letContext.putList(localDef.apply(cmlDefEvaluator, letContext));
//
//				return new Pair<INode, Context>(node.getAction(), letContext);
//			}
//		});
//	}

	@Override
	public Inspection caseANewStm(final ANewStm node,
			final Context question) throws AnalysisException
	{
		ILexNameToken name = new CmlLexNameToken(node.getClassName().getName(),
											 node.getClassName().getName(),
												node.getLocation());
		@SuppressWarnings("deprecation")
		final ACallStm callStm = new ACallStm(name.getLocation(), name, node.getArgs());

		return newInspection(createTauTransitionWithTime(callStm), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{

				ObjectValue classValue = CmlValueFactory.createClassValue(node, question);
				Context ctorContext = CmlContextFactory.newObjectContext(node.getLocation(), "Class Constructor context", question, classValue);
				Value oldVal = node.getDestination().apply(cmlExpressionVisitor, question);
				oldVal.set(node.getLocation(), classValue, question);

				return new Pair<INode, Context>(callStm, ctorContext);
			}
		});
	}

	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public Inspection caseAIfNonDeterministicStm(
			AIfNonDeterministicStm node, final Context question)
			throws AnalysisException
	{

		List<AAltNonDeterministicStm> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(node.getAlternatives(), question, cmlExpressionVisitor);

		if (availableAlts.size() > 0)
		{
			final INode next = availableAlts.get(rnd.nextInt(availableAlts.size())).getAction();

			return newInspection(createTauTransitionWithTime(next), new AbstractCalculationStep(owner, visitorAccess)
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
			return newInspection(createTauTransitionWithoutTime(divAction), new AbstractCalculationStep(owner, visitorAccess)
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
			final ADoNonDeterministicStm node,
			final Context question) throws AnalysisException
	{

		List<AAltNonDeterministicStm> availableAlts = ActionVisitorHelper.findAllTrueAlternatives(node.getAlternatives(), question, cmlExpressionVisitor);

		if (availableAlts.size() > 0)
		{
			// first we push the do node on the execution stack to get it sequentially composed with the
			// picked alternative
			// if we got here we already now that the must at least be one available action
			// so this should pose no risk of exception
			@SuppressWarnings("deprecation")
			final INode nextNode = new ASequentialCompositionAction(node.getLocation(), 
					ActionVisitorHelper.wrapStatement(availableAlts.get(rnd.nextInt(availableAlts.size())).getAction().clone()), 
					ActionVisitorHelper.wrapStatement(node.clone()));
			return newInspection(createTauTransitionWithTime(nextNode), new AbstractCalculationStep(owner, visitorAccess)
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
			@SuppressWarnings("deprecation")
			final ASkipAction skipAction = new ASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithoutTime(skipAction), new AbstractCalculationStep(owner, visitorAccess)
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

	@Override
	public Inspection caseAReturnStm(
			final AReturnStm node, final Context question)
			throws AnalysisException
	{

		return newInspection(createTauTransitionWithoutTime(new ASkipAction()), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				Context nameContext = (Context) question.locate(CmlOperationValue.ReturnValueName());
				if (nameContext != null)
				{
					if (node.getExpression() != null)
						nameContext.put(CmlOperationValue.ReturnValueName(), node.getExpression().apply(cmlExpressionVisitor, question));
					else
						nameContext.put(CmlOperationValue.ReturnValueName(), new VoidValue());
				}

				return new Pair<INode, Context>(new ASkipAction(), question);
			}
		});
	}

	/**
	 * Assignment - section 7.5.1 D23.2
	 */
	@Override
	public Inspection caseAAssignmentStm(
			final AAssignmentStm node,
			final Context question) throws AnalysisException
	{
		@SuppressWarnings("deprecation")
		final INode skipNode = new ASkipAction(node.getLocation(), new AVoidType());
		// FIXME according to the semantics this should be performed instantly so time is not
		// allowed to pass
		return newInspection(createTauTransitionWithoutTime(skipNode), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				return evalSingleAssignmentStatement(node, question, skipNode,true);
			}
		});
	}
	
	@Override
	public Inspection caseAAtomicStm(
			final AAtomicStm node,final Context question)
			throws AnalysisException
	{
		@SuppressWarnings("deprecation")
		final INode skipNode = new ASkipAction(node.getLocation(), new AVoidType());
		// FIXME according to the semantics this should be performed instantly so time is not
		// allowed to pass
		return newInspection(createTauTransitionWithoutTime(skipNode), new AbstractCalculationStep(owner, visitorAccess)
		{

			@Override
			public Pair<INode, Context> execute(CmlTransition selectedTransition)
					throws AnalysisException
			{
				Pair<INode, Context> result = null;
				for (Iterator<AAssignmentStm> itr = node.getAssignments().iterator(); itr.hasNext();)
				{
					result = evalSingleAssignmentStatement(itr.next(), question, skipNode,!itr.hasNext());
				}
				return result;
			}
		});
	}
	
	private Pair<INode, Context> evalSingleAssignmentStatement(
			final AAssignmentStm node,
			final Context question, final INode skipNode,boolean checkInv)
			throws AnalysisException, ValueException
	{
		Value expValue = node.getExp().apply(cmlExpressionVisitor, question);
		Value oldVal = node.getTarget().apply(cmlExpressionVisitor, question);
		oldVal.set(node.getLocation(), expValue, question);

		PExp invExp = null;
		if (question.getSelf() instanceof ProcessObjectValue)
			invExp = ((ProcessObjectValue) question.getSelf()).getInvariantExpression();

		if (invExp != null &&checkInv)
		{

			Context invContext = CmlContextFactory.newContext(invExp.getLocation(), "Process "
					+ question.getSelf() + " invariant context", question);
			invContext.setPrepost(0, "Process invariant for '"
					+ ((ProcessObjectValue) question.getSelf()).getProcessDefinition()
					+ "' is violated");

			return new Pair<INode, Context>(invExp, invContext);
		} else
			// now this process evolves into Skip
			return new Pair<INode, Context>(skipNode, question);
	}

	@Override
	public Inspection caseAForPatternBindStm(
			final AForPatternBindStm node, final Context question)
			throws AnalysisException
	{

		final ValueList v = question.lookup(NamespaceUtility.getSeqForName()).seqValue(question);

		// if the sequence is empty we're done and evolve into skip
		if (v.isEmpty() && owner.hasChildren()
				&& owner.getLeftChild().finished())
		{
			@SuppressWarnings("deprecation")
			final ASkipAction skipAction = new ASkipAction(node.getLocation());
			return newInspection(createTauTransitionWithTime(skipAction), new AbstractCalculationStep(owner, visitorAccess)
			{
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					// clear the child nodes
					setLeftChild(null);
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

			visitorAccess.setLeftChild(new ConcreteCmlBehaviour(node.getStatement(), question, owner));
		}

		return newInspection(owner.getLeftChild().inspect(), new AbstractCalculationStep(owner, visitorAccess)
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
	public Inspection caseAWhileStm(
			final AWhileStm node, final Context question)
			throws AnalysisException
	{

		if (node.getExp().apply(cmlExpressionVisitor, question).boolValue(question))
		{
			// the next step is a sequential composition of the action and this node
			return newInspection(createTauTransitionWithTime(node), new AbstractCalculationStep(owner, visitorAccess)
			{

				@SuppressWarnings("deprecation")
				@Override
				public Pair<INode, Context> execute(
						CmlTransition selectedTransition)
						throws AnalysisException
				{
					return new Pair<INode, Context>(new ASequentialCompositionAction(node.getStatement().getLocation(), 
							ActionVisitorHelper.wrapStatement(node.getStatement().clone()), 
							ActionVisitorHelper.wrapStatement(node.clone())), question);
				}
			});
		} else
		{
			// if the condition is false then the While evolves into Skip
			final INode skipNode = new ASkipAction();
			return newInspection(createTauTransitionWithTime(skipNode), new AbstractCalculationStep(owner, visitorAccess)
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
