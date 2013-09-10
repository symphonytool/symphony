package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.interpreter.assistant.pattern.PPatternAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.PatternMatchException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.VoidValue;

import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.core.interpreter.api.CmlSupervisorEnvironment;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.behaviour.CmlAlphabet;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.behaviour.Inspection;
import eu.compassresearch.core.interpreter.api.transitions.CmlTock;
import eu.compassresearch.core.interpreter.api.transitions.InternalTransition;
import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;
import eu.compassresearch.core.interpreter.utility.Pair;

public class StatementInspectionVisitor extends AbstractInspectionVisitor {

	public StatementInspectionVisitor(CmlBehaviour ownerProcess,
			VisitorAccess visitorAccess,
			QuestionAnswerCMLAdaptor<Context, Inspection> parentVisitor) {

		super(ownerProcess, visitorAccess, parentVisitor);
	}

	/**
	 * This methods splits the assignment call statement into the call and 
	 * the assignment statements and.
	 */
	@Override
	public Inspection caseAAssignmentCallStatementAction(
			final AAssignmentCallStatementAction node, final Context question)
			throws AnalysisException {

		return newInspection(createSilentTransition(node, null), 
				new AbstractCalculationStep(owner, visitorAccess) {
					
					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {
						//put return value in a new context
						Context resultContext = CmlContextFactory.newContext(node.getLocation(), "Call Result Context", question);
						//put return value in upper context if the parent is a AAssignmentCallStatementAction
						resultContext.putNew(new NameValuePair(CmlOperationValue.ReturnValueName(), new UndefinedValue()));
								
						//To access the result we put it in a Value named "|CALL|.|CALLRETURN|" this can never be created
						//in a cml model. This is a little ugly but it works and statys until something better comes up.
						AVariableExp varExp = new AVariableExp(node.getType(), 
								node.getCall().getLocation(),
								CmlOperationValue.ReturnValueName(), 
								"", 
								null);
						//Next we create the assignment statement with the expressions that graps the result 
						ASingleGeneralAssignmentStatementAction assignmentNode =
								new ASingleGeneralAssignmentStatementAction(node.getLocation(),	
										node.getType(),
										node.getDesignator().clone(),
										varExp);

						//We now compose the call statement and assignment statement into sequential composition
						INode seqComp = new ASequentialCompositionAction(node.getLocation(), node.getCall().clone(), assignmentNode.clone());
						return new Pair<INode, Context>(seqComp,resultContext);
					}
				});
		
		
	}
	
	@Override
	public Inspection caseABlockStatementAction(
			final ABlockStatementAction node, final Context question)
			throws AnalysisException {
		
		return newInspection(createSilentTransition(node, node.getAction()), 
				new AbstractCalculationStep(owner, visitorAccess) {
					
					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {
						
						Context blockContext = CmlContextFactory.newContext(node.getLocation(), "block context", question);
						
						//add the assignment definitions to the block context
						if(node.getDeclareStatement() != null)
						{
							for(PDefinition def : node.getDeclareStatement().getAssignmentDefs())
							{
								NameValuePair nvp = def.apply(cmlDefEvaluator,question).get(0);
								blockContext.put(nvp.name, nvp.value.getUpdatable(null));
							}
						}
						
						return new Pair<INode, Context>(node.getAction(), blockContext);
					}
				});
	}
	
	/*
	 * 
	 */
	@Override
	public Inspection caseACallStatementAction(
			final ACallStatementAction node, final Context question)
					throws AnalysisException {
		
		if(!owner.hasChildren()){
			
			return newInspection(createSilentTransition(node, node), 
					new AbstractCalculationStep(owner, visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							//first find the operation value in the context
							CmlOperationValue opVal = (CmlOperationValue)lookupName(node.getName(),question); 
							
							//evaluate all the arguments
							ValueList argValues = new ValueList();
							for (PExp arg: node.getArgs())
							{
								argValues.add(arg.apply(cmlExpressionVisitor,question));
							}

							// Note args cannot be Updateable, so we convert them here. This means
							// that TransactionValues pass the local "new" value to the far end.
							ValueList constValues = argValues.getConstant();

							//find the self object
							ObjectValue self = null;
							if(opVal.getSelf() != null)
								self = opVal.getSelf();
							else
								self = question.getSelf();
							
							//Create a new object context to perform the operation call 
							Context callContext = CmlContextFactory.newObjectContext(node.getLocation(), "CML Operation Call", question, self);

							if (argValues.size() != opVal.getParamPatterns().size())
							{
								opVal.abort(4068, "Wrong number of arguments passed to " + node.getName(), question);
							}

							ListIterator<Value> valIter = argValues.listIterator();
							Iterator<PType> typeIter = opVal.getType().getParameters().iterator();
							NameValuePairMap args = new NameValuePairMap();

							for (PPattern p : opVal.getParamPatterns())
							{
								try
								{
									// Note values are assumed to be constant, as enforced by eval()
									Value pv = valIter.next().convertTo(typeIter.next(), question);

									for (NameValuePair nvp : PPatternAssistantInterpreter.getNamedValues(p,pv, question))
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
												opVal.abort(4069,	"Parameter patterns do not match arguments", question);
											}
										}
									}
								}
								catch (PatternMatchException e)
								{
									opVal.abort(e.number, e, question);
								}
							}

							// Note: arg name/values hide member values
							callContext.putAll(args);

							if (opVal.getBody() == null)
							{
								opVal.abort(4066, "Cannot call implicit operation: " + node.getName(), question);
							}

							//push the pre condition on the execution stack so it gets executed first
							if(opVal.getPrecondition() != null){

								PExp preExpNode = opVal.getPrecondition();
								Context preConditionContext = CmlContextFactory.newContext(preExpNode.getLocation(), 
										"Operation " + node.getName() + " precondition context", callContext);
								
								callContext.setPrepost(0, "precondition violated for call " + node.getName());

								if(!preExpNode.apply(cmlExpressionVisitor, preConditionContext).boolValue(question))
								{
									throw new ValueException(4061, "precondition violated for call " + node.getName(), question);
								}

							}

							if(opVal.getPostcondition() != null){

								PExp postExpNode = opVal.getPostcondition();

								Context postConditionContext = CmlContextFactory.newContext(postExpNode.getLocation(), 
										"Operation " + node.getName() + " postcondition context", callContext);

								//set the old values
								for(NameValuePair nvp : postConditionContext.getSelf().getMemberValues().asList())
									if(UpdatableValue.class.isAssignableFrom(nvp.value.getClass()))
									{
										//FIXME it does not work when the module is there
										LexNameToken oldName = new LexNameToken("",(ILexIdentifierToken)nvp.name.getOldName().getIdentifier().clone());
										postConditionContext.putNew(new NameValuePair(oldName, nvp.value.getConstant()));
									}

								postConditionContext.setPrepost(0, "postcondition violated for " + node.getName());

								setRightChild(new ConcreteCmlBehaviour(postExpNode, postConditionContext, owner));

								//We now compose the call statement and post condition check into sequential composition
								//INode seqComp = new ASequentialCompositionAction(node.getLocation(), opVal.getBody(), assignmentNode);
								//return new Pair<INode, Context>(seqComp,resultContext);
							}
							//the left child is the actual call executing
							setLeftChild(new ConcreteCmlBehaviour(opVal.getBody(), callContext, owner));
							return new Pair<INode, Context>(node, question);
						}
					});
			
			
		}
		//The left child is the actual call so if it is not terminated then we execute 
		//the next action of it
		else if(!owner.getLeftChild().finished())
		{
			return newInspection(owner.getLeftChild().inspect(), 
					new AbstractCalculationStep(owner, visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							owner.getLeftChild().execute(supervisor());
							return new Pair<INode, Context>(node, question);
						}
					});
		}
		//The right child contains the postcondition of the call, so if we are here and its
		//non-empty then we execute it
		else if(owner.getRightChild() != null)
		{
			return newInspection(owner.getRightChild().inspect(), 
			new AbstractCalculationStep(owner, visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					
					owner.getRightChild().execute(supervisor());
					setLeftChild(null);
					setRightChild(null);
					return new Pair<INode, Context>(new ASkipAction(), question);
				}
			});
			
		}
		else
		{
			final INode skipNode = new ASkipAction();
			return newInspection(createSilentTransition(node, skipNode), 
					new AbstractCalculationStep(owner, visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							
							setLeftChild(null);
							return new Pair<INode, Context>(skipNode, question);
						}
					});
		}
	}
	
	@Override
	public Inspection caseAIfStatementAction(final AIfStatementAction node,
			final Context question) throws AnalysisException {

		if (node.getIfExp().apply(cmlExpressionVisitor,question).boolValue(question))
		{
			return newInspection(createSilentTransition(node,node.getThenStm()), 
					new AbstractCalculationStep(owner, visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							return new Pair<INode, Context>(node.getThenStm(), question);
						}
					});
		}
		else
		{
			for (final AElseIfStatementAction elseif: node.getElseIf())
			{
				if(elseif.getElseIf().apply(cmlExpressionVisitor,question).boolValue(question))
				{
					return newInspection(createSilentTransition(node, elseif.getThenStm()), 
							new AbstractCalculationStep(owner, visitorAccess) {
								
								@Override
								public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
										throws AnalysisException {
									return new Pair<INode, Context>(elseif.getThenStm(), question);
								}
							});
				}
			}

			if (node.getElseStm() != null)
			{
				return newInspection(createSilentTransition(node, node.getElseStm()), 
						new AbstractCalculationStep(owner, visitorAccess) {
							
							@Override
							public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
									throws AnalysisException {
								return new Pair<INode, Context>(node.getElseStm(), question);
							}
						});
			}
		}
		
		final ASkipAction skipAction = new ASkipAction(node.getLocation());
		return newInspection(createSilentTransition(node,skipAction),
				new AbstractCalculationStep(owner, visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				return new Pair<INode, Context>(skipAction, question);
			}
		});
	}
	
	@Override
	public Inspection caseALetStatementAction(final ALetStatementAction node,
			final Context question) throws AnalysisException {
	
		return newInspection(createSilentTransition(node, node.getAction()), 
		new AbstractCalculationStep(owner, visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				
				//Create a new context for the let statement
				Context letContext = CmlContextFactory.newContext(node.getLocation(), "let action context", question);

				for(PDefinition localDef :node.getLocalDefinitions())
					letContext.putList(localDef.apply(cmlDefEvaluator,letContext));
				
				return new Pair<INode, Context>(node.getAction(), letContext);
			}
		});
	}
	
	@Override
	public Inspection caseANewStatementAction(final ANewStatementAction node,
			final Context question) throws AnalysisException {

		ILexNameToken name = node.getClassdef().getName().clone();//new LexNameToken(node.getClassdef().getName().getName(), node.getClassName().getIdentifier().getName(), node.getLocation());
		final ACallStatementAction callStm = new ACallStatementAction(name.getLocation(), name, node.getArgs());
		
		return newInspection(createSilentTransition(node, callStm),
				new AbstractCalculationStep(owner,visitorAccess) {
					
					@Override
					public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
							throws AnalysisException {
						
						ObjectValue classValue = CmlValueFactory.createClassValue(node, question);
						Context ctorContext = CmlContextFactory.newObjectContext(node.getLocation(), "Class Constructor context", question, classValue);
						Value oldVal = node.getDestination().apply(cmlExpressionVisitor,question);
						oldVal.set(node.getLocation(), classValue, question);
						
						return new Pair<INode, Context>(callStm, ctorContext);
					}
				});
	}
	
	/**
	 * Non deterministic if randomly chooses between options whose guard are evaluated to true
	 */
	@Override
	public Inspection caseANonDeterministicIfStatementAction(
			ANonDeterministicIfStatementAction node, final Context question)
			throws AnalysisException {

		
		final List<ANonDeterministicAltStatementAction> availableAlts = 
				ActionVisitorHelper.findAllTrueAlternatives(
				node.getAlternatives(),question,cmlExpressionVisitor);
		
		if(availableAlts.size() > 0)
		{
			final INode next = availableAlts.get(rnd.nextInt(availableAlts.size())).getAction();
			
			return newInspection(createSilentTransition(node, next),
					new AbstractCalculationStep(owner,visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							
							//if we got here we already now that the must at least be one available action
							//so this should pose no risk of exception
							return new Pair<INode,Context>(next,question);
							
						}
					});
		}
		else
		{
			//were stuck so return empty alphabet
			//FIXME actually this diverges
			return newInspection(new CmlAlphabet(),null);
		}
	}

	/**
	 * 
	 */
	@Override
	public Inspection caseANonDeterministicDoStatementAction(
			final ANonDeterministicDoStatementAction node, final Context question)
			throws AnalysisException {

		List<ANonDeterministicAltStatementAction> availableAlts = 
				ActionVisitorHelper.findAllTrueAlternatives(node.getAlternatives(),question,cmlExpressionVisitor);
		
		if(availableAlts.size() > 0)
		{
			//first we push the do node on the execution stack to get it sequentially composed with the
			//picked alternative
			//if we got here we already now that the must at least be one available action
			//so this should pose no risk of exception
			final INode nextNode = new ASequentialCompositionAction(node.getLocation(), 
					availableAlts.get(rnd.nextInt(availableAlts.size())).getAction().clone(), 
						node.clone());
			return newInspection(createSilentTransition(node, nextNode), 
					new AbstractCalculationStep(owner,visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							
							return new Pair<INode, Context>(nextNode,question);
						}
					});
			
		}
		else
		{
			return newInspection(createSilentTransition(node, new ASkipAction()),
					new AbstractCalculationStep(owner,visitorAccess) {
						
						@Override
						public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
								throws AnalysisException {
							return new Pair<INode,Context>(new ASkipAction(node.getLocation()), question);
						}
					});
		}
			
			
	}
	
	@Override
	public Inspection caseAReturnStatementAction(
			final AReturnStatementAction node, final Context question)
			throws AnalysisException {

		return newInspection(createSilentTransition(node,new ASkipAction()), 
				new AbstractCalculationStep(owner,visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				Context nameContext = (Context)question.locate(CmlOperationValue.ReturnValueName());
				if(nameContext != null)
				{
					if(node.getExp() != null)
						nameContext.put(CmlOperationValue.ReturnValueName(), 
								node.getExp().apply(cmlExpressionVisitor,question));
					else
						nameContext.put(CmlOperationValue.ReturnValueName(),
								new VoidValue());
				}
				
				return new Pair<INode,Context>(new ASkipAction(),question);
			}
		});
	}
	
	/**
	 * Assignment - section 7.5.1 D23.2
	 * 
	 */
	@Override
	public Inspection caseASingleGeneralAssignmentStatementAction(
			final ASingleGeneralAssignmentStatementAction node, final Context question)
					throws AnalysisException {
		final INode skipNode = new ASkipAction(node.getLocation(),new AActionType());
		//FIXME according to the semantics this should be performed instantly so time is not
		//allowed to pass
		return newInspection(new CmlAlphabet(new InternalTransition(owner,node,skipNode,null)),
				new AbstractCalculationStep(owner,visitorAccess) {
			
			@Override
			public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
					throws AnalysisException {
				
				Value expValue = node.getExpression().apply(cmlExpressionVisitor,question);
				Value oldVal = node.getStateDesignator().apply(cmlExpressionVisitor,question);
				oldVal.set(node.getLocation(), expValue, question);
				
				//System.out.println(stateDesignatorName + " = " + expValue);
				
				//now this process evolves into Skip
				return new Pair<INode,Context>(skipNode, question);
			}
		});
	}
	
	@Override
	public Inspection caseAForSequenceStatementAction(
			final AForSequenceStatementAction node, final Context question)
			throws AnalysisException {

		final ValueList v = question.lookup(NamespaceUtility.getSeqForName()).seqValue(question);
		
		if(v.isEmpty())
		{
			final ASkipAction skipAction = new ASkipAction(node.getLocation());
			return newInspection(createSilentTransition(node,skipAction),
					new AbstractCalculationStep(owner,visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					
					return new Pair<INode,Context>(skipAction, question.outer);
				}
			}); 
		}
		else
		{
			
			final Inspection actionInspection = node.getAction().apply(parentVisitor,question);
			return newInspection(actionInspection.getTransitions(),
					new AbstractCalculationStep(owner,visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					
					Value x = v.firstElement();
					v.remove(x);
					
					if (node.getPatternBind().getPattern() != null)
					{
							try
							{
								question.putList(PPatternAssistantInterpreter.getNamedValues(node.getPatternBind().getPattern(),x, question));
								actionInspection.getNextStep().execute(sve);

							}
							catch (PatternMatchException e)
							{
								// Ignore mismatches
							}
					}
					
					return new Pair<INode, Context>(node,question);
					
				}
			}); 
		}
		
	}
	/**
	 * 
	 * //TODO no semantics defined, resolve this!
	 */
	@Override
	public Inspection caseAWhileStatementAction(
			final AWhileStatementAction node, final Context question)
			throws AnalysisException {
				
		if(node.getCondition().apply(cmlExpressionVisitor,question).boolValue(question))
		{
			//the next step is a sequential composition of the action and this node
			return newInspection(createSilentTransition(node, null), 
					new AbstractCalculationStep(owner,visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					return new Pair<INode,Context>(
							new ASequentialCompositionAction(node.getAction().getLocation(), node.getAction().clone(), node.clone()),question);
				}
			});
		}
		else
		{
			//if the condition is false then the While evolves into Skip
			final INode skipNode = new ASkipAction();
			return newInspection(createSilentTransition(node, skipNode),
					new AbstractCalculationStep(owner,visitorAccess) {
				
				@Override
				public Pair<INode, Context> execute(CmlSupervisorEnvironment sve)
						throws AnalysisException {
					return new Pair<INode,Context>(skipNode, question);
				}
			});
		}
		
	}
}
