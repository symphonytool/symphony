package eu.compassresearch.core.interpreter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.SNumericBasicType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.core.interpreter.api.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.CmlBehaviour.BehaviourName;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;
import eu.compassresearch.core.interpreter.api.values.CmlSetQuantifier;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.RenamingValue;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.interpreter.utility.Pair;
import eu.compassresearch.core.interpreter.utility.SetMath;

class CommonSetupVisitor extends AbstractSetupVisitor
{

	public CommonSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess,
			CmlBehaviorFactory cmlBehaviorFactory)
	{
		super(owner, visitorAccess, cmlBehaviorFactory);
	}

	@SuppressWarnings("rawtypes")
	protected ChannelNameSetValue eval(PVarsetExpression chansetExpression,
			Context question) throws AnalysisException
	{
		Value val = chansetExpression.apply(cmlExpressionVisitor, question);
		if (val instanceof ChannelNameSetValue)
		{
			return (ChannelNameSetValue) val;
		} else if (val instanceof Set && ((Set) val).isEmpty())
		{
			return new ChannelNameSetValue(new HashSet<ChannelValue>());
		}

		throw new CmlInterpreterException(chansetExpression, InterpretationErrorMessages.FATAL_ERROR.customizeMessage("Failed to evaluate chanset expression"));
	}

	public Pair<INode, Context> caseAlphabetisedParallelism(INode node,
			PVarsetExpression leftChansetExpression,
			PVarsetExpression rightChansetExpression, Context question)
			throws AnalysisException
	{
		// evaluate the children in the their own context
		ChannelNameSetValue leftChanset = eval(leftChansetExpression, getChildContexts(question).first);
		ChannelNameSetValue rightChanset = eval(rightChansetExpression, getChildContexts(question).second);

		Context chansetContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Alphabetised parallelism precalcualted channelsets", question);

		chansetContext.put(NamespaceUtility.getLeftPrecalculatedChannetSet(), leftChanset);
		chansetContext.put(NamespaceUtility.getRightPrecalculatedChannetSet(), rightChanset);

		return new Pair<INode, Context>(node, chansetContext);
	}

	protected Pair<INode, Context> caseAInterrupt(INode node, INode leftNode,
			INode rightNode, Context question) throws AnalysisException
	{
		// TODO create proper names!!
		setLeftChild(leftNode, new BehaviourName("left /_\\", owner.getName(), "", ""), question);
		setRightChild(rightNode, new BehaviourName("/_\\ right", owner.getName(), "", ""), question);

		return new Pair<INode, Context>(node, question);
	}

	protected Pair<INode, Context> caseChannelRenaming(INode node,
			SRenameChannelExp renameExpression, INode subNode, Context question)
			throws AnalysisException
	{
		Context rnContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Renaming context", question);
		RenamingValue rv = (RenamingValue) renameExpression.apply(this.cmlExpressionVisitor, question);
		rnContext.putNew(new NameValuePair(NamespaceUtility.getRenamingValueName(), rv));

		setLeftChild(subNode, question);

		return new Pair<INode, Context>(node, rnContext);
	}

	/*
	 * Sequential Composition
	 */
	protected Pair<INode, Context> caseASequentialComposition(INode node,
			INode leftNode, Context question) throws AnalysisException
	{
		// We set up the left child of the sequential process/action before entering. The right will not
		// be touched before the left has terminated
		setLeftChild(leftNode, new CmlBehaviour.BehaviourName(owner.getName().clone()), question);
		return new Pair<INode, Context>(node, question);
	}

	protected Pair<INode, Context> caseATimedInterrupt(INode node,
			INode leftNode, Context question) throws AnalysisException
	{
		Context context = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Timed Interrupt context", question);
		context.putNew(new NameValuePair(NamespaceUtility.getStartTimeName(), new IntegerValue(owner.getCurrentTime())));

		// We setup the child nodes
		setLeftChild(leftNode, question);
		return new Pair<INode, Context>(node, context);
	}

	protected Pair<INode, Context> caseAUntimedTimeout(INode node,
			INode leftNode, Context question) throws AnalysisException
	{

		// We setup the child nodes
		setLeftChild(leftNode, question);
		return new Pair<INode, Context>(node, question);
	}

	protected Pair<INode, Context> setupTimedOperator(INode node,
			INode leftNode, ILexNameToken startTimeValueName, Context question)
			throws AnalysisException
	{

		Context context = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), node.getClass().getName()
				+ " context", question);
		try
		{
			context.putNew(new NameValuePair(startTimeValueName, new NaturalValue(owner.getCurrentTime())));
		} catch (Exception e)
		{
			throw new ValueException(0, e.getMessage(), question);
		}

		// We setup the child nodes
		setLeftChild(leftNode, question);
		return new Pair<INode, Context>(node, context);

	}

	// protected Pair<INode, Context> caseATimeout(INode node, INode leftNode,
	// Context question) throws AnalysisException
	// {
	//
	// Context context = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Timeout context",
	// question);
	// try
	// {
	// context.putNew(new NameValuePair(NamespaceUtility.getStartTimeName(), new IntegerValue(owner.getCurrentTime())));
	// } catch (Exception e)
	// {
	// throw new ValueException(0, e.getMessage(), question);
	// }
	// // We setup the child nodes
	// setLeftChild(leftNode, question);
	// return new Pair<INode, Context>(node, context);
	//
	// }

	// @Override
	// public Pair<INode, Context> caseAStartDeadlineAction(
	// AStartDeadlineAction node, Context question)
	// throws AnalysisException
	// {
	// Context context = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Timeout context",
	// question);
	// try
	// {
	// context.putNew(new NameValuePair(NamespaceUtility.getStartsByTimeName(), new
	// NaturalValue(owner.getCurrentTime())));
	//
	// } catch (Exception e)
	// {
	// throw new ValueException(0, e.getMessage(), question);
	// }
	// setLeftChild(node.getLeft(), question);
	// return new Pair<INode, Context>(node, context);
	// }

	/*
	 * Non public replication helper methods -- Start
	 */

	/*
	 * Replication
	 */

	abstract class AbstractReplicationFactory
	{
		private final INode node;

		public AbstractReplicationFactory(INode node)
		{
			this.node = node;
		}

		/**
		 * This creates the next node in the replication when more than 2 replication values are remaining
		 * 
		 * @return The replicated construct where the replicated action/process is left and and the replication node is
		 *         the right
		 */
		abstract INode createNextReplication();

		/**
		 * This creates the last node in the replication
		 * 
		 * @return The last replication node
		 */
		abstract INode createLastReplication();

		Context createReplicationChildContext(NameValuePairList npvl,
				INode node, Context outer)
		{
			Context childContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "", outer);
			childContext.putAllNew(npvl);
			return childContext;
		}

		Context createOperatorContext(INode node, CmlSetQuantifier remaining,
				Context question)
		{
			return question;
		}

		/**
		 * This creates the node for a single replicated action/process
		 * 
		 * @return
		 */
		INode getReplicatedNode()
		{
			if (node instanceof SReplicatedAction)
			{
				return ((SReplicatedAction) node).getReplicatedAction();
			} else
			{
				return ((SReplicatedProcess) node).getReplicatedProcess();
			}
		}
	}

	protected Pair<INode, Context> caseReplicated(INode node,
			List<PSingleDeclaration> decls, AbstractReplicationFactory factory,
			Context question) throws AnalysisException
	{
		// The name of the value holding the state of the remaining values of the replication
		ILexNameToken replicationContextValueName = NamespaceUtility.getReplicationNodeName(node);
		CmlSetQuantifier ql = (CmlSetQuantifier) question.check(replicationContextValueName);
		// if null then this is the first action of the replication
		// then we need to evaluate the
		boolean firstRun = false;
		if (ql == null)
		{
			firstRun = true;
			// Make a set of tuples
			// nextContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node),
			// "replication contexts", question);
			ql = createQuantifierList(decls, question);
			// nextContext.putNew(new NameValuePair(replicationContextValueName, ql));
		}

		INode nextNode;
		Iterator<NameValuePairList> itQuantifiers = ql.iterator();

		// if no replicated Value exists we return skip since this it what all the operators should return except
		// for external choice which should be Stop, this is handled specifically in that case
		if (!itQuantifiers.hasNext())
		{
			return new Pair<INode, Context>(CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node)), question);
		}
		// fetch the left value and remove it from the list
		NameValuePairList nextValue = itQuantifiers.next();
		itQuantifiers.remove();
		// if no more rep values exists
		// and this is the first run then we do no replication and just returns the action/process
		if (!itQuantifiers.hasNext() && firstRun)
		{
			nextNode = factory.getReplicatedNode();
			return new Pair<INode, Context>(nextNode, factory.createReplicationChildContext(nextValue, nextNode, question));
		}
		/*
		 * if no more rep values exists and this is NOT the first run then we created the context for the left side
		 * already in the last step and is located above the current context
		 */
		else if (!itQuantifiers.hasNext() && !firstRun)
		{
			nextNode = factory.createLastReplication();
			// the outer is the pre-calculated context from the previous run
			Context leftChildContext = question.outer;
			// we take the outer.outer because we want the parent context of this one to be the one given to the
			// replication node
			Context rightChildContext = factory.createReplicationChildContext(nextValue, nextNode, question.outer.outer);
			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
			return new Pair<INode, Context>(nextNode, factory.createOperatorContext(nextNode, ql, question.outer.outer));
		}

		NameValuePairList afterNextValue = itQuantifiers.next();
		// itQuantifiers.remove();

		// If no values are left then we have exactly 2 values and thus we must create the last replication
		if (!itQuantifiers.hasNext() && firstRun)
		{
			nextNode = factory.createLastReplication();

			Context leftChildContext = factory.createReplicationChildContext(nextValue, nextNode, question);
			Context rightChildContext = factory.createReplicationChildContext(afterNextValue, nextNode, question);
			itQuantifiers.remove();
			// the replication context, if it exist is lowest. But if this is the first run
			// then the replication context does not exist

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));

			return new Pair<INode, Context>(nextNode, factory.createOperatorContext(nextNode, ql, question));
		}
		// If we have more than two replication values then we make an interleaving between the
		// first value and the rest of the replicated values
		else
		{
			nextNode = factory.createNextReplication();
			Context leftChildContext;
			Context rightChildContext;

			// the replication context must always be the lowest
			if (firstRun)
			{
				// if this is the first run then me must create the right child context and
				// attach it to the replication context
				leftChildContext = factory.createReplicationChildContext(nextValue, nextNode, question);
				rightChildContext = factory.createReplicationChildContext(afterNextValue, nextNode, question);
				itQuantifiers.remove();
			} else
			{
				// if this is not the first run the the replication context already exist
				// so we can pull out the parent and attach the right child context to this and
				// then attach the replication
				leftChildContext = question.outer;
				rightChildContext = factory.createReplicationChildContext(nextValue, nextNode, question.outer.outer);
			}

			rightChildContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "replication contexts", rightChildContext);
			rightChildContext.putNew(new NameValuePair(replicationContextValueName, ql));

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
			return new Pair<INode, Context>(nextNode, factory.createOperatorContext(nextNode, ql, question));
		}
	}

	// FIXME this check is not sufficient
	private class UnboundedChecker extends DepthFirstAnalysisCMLAdaptor
	{
		private boolean isUnbounded = false;

		public boolean isUnbounded()
		{
			return isUnbounded;
		}

		@Override
		public void defaultInSNumericBasicType(SNumericBasicType node)
				throws AnalysisException
		{
			isUnbounded = true;
		}
	}

	protected CmlSetQuantifier createQuantifierList(
			List<PSingleDeclaration> replicationDeclaration, Context question)
			throws AnalysisException
	{
		NameValuePairList replicationDecls = new NameValuePairList();
		List<ILexNameToken> quantifierNames = new LinkedList<ILexNameToken>();
		SetValue quantifierValues;

		// Convert all the single decls into a NameValuePairList
		for (PSingleDeclaration singleDecl : replicationDeclaration)
		{
			for (NameValuePair nvp : singleDecl.apply(this.cmlDefEvaluator, question))
			{
				// We do not allow unbounded replication
				// FIXME this check is not sufficient, this needs to be more general
				if (nvp.value instanceof LatticeTopValue)
				{
					UnboundedChecker uc = new UnboundedChecker();
					((LatticeTopValue) nvp.value).getType().apply(uc);
					if (uc.isUnbounded())
					{
						throw new CmlInterpreterException(singleDecl, InterpretationErrorMessages.UNBOUNDED_REPLICATION.customizeMessage());
					}
				}

				replicationDecls.add(nvp);
			}
		}

		if (replicationDecls.size() == 1)
		{
			NameValuePair nvp = replicationDecls.get(0);
			quantifierNames.add(nvp.name);
			quantifierValues = new SetValue();
			for (Value val : getIterator(nvp.value, question))
			{
				quantifierValues.values.add(new TupleValue(new ValueList(val)));
			}

		} else
		{
			// If more than one decl then we need to calculate the cross product of them
			// First we append all the sets into a list
			List<SetValue> sets = new LinkedList<SetValue>();
			for (NameValuePair nvp : replicationDecls)
			{
				List<Value> values = new LinkedList<Value>();
				for (Value val : getIterator(nvp.value, question))
				{
					values.add(val);
				}
				quantifierNames.add(nvp.name);
				sets.add(new SetValue(nvp.value.setValue(question)));
			}
			quantifierValues = SetMath.getCrossProduct(sets);
		}

		return new CmlSetQuantifier(quantifierNames, quantifierValues);
	}

	private Iterable<Value> getIterator(Value val, Context question)
			throws ValueException
	{
		if (val instanceof SetValue)
		{
			return val.setValue(question);
		} else
		{
			return val.seqValue(question);
		}
	}

	/*
	 * Non public replication helper methods -- End
	 */

}
