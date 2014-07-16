package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
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
import eu.compassresearch.core.interpreter.api.values.CmlSetQuantifier;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.api.values.RenamingValue;
import eu.compassresearch.core.interpreter.runtime.DelayedWriteContext;
import eu.compassresearch.core.interpreter.runtime.ReplicatedContext;
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

	protected SetValue eval(PVarsetExpression chansetExpression,
			Context question) throws AnalysisException
	{
		return (SetValue) chansetExpression.apply(cmlExpressionVisitor, question);
	}

	public Pair<INode, Context> caseAlphabetisedParallelism(INode node,
			PVarsetExpression leftChansetExpression,
			PVarsetExpression rightChansetExpression, Context question)
			throws AnalysisException
	{

		// evaluate the children in the their own context
		SetValue leftChanset = eval(leftChansetExpression, getChildContexts(question).first);
		SetValue rightChanset = generateChannelValues(node, rightChansetExpression, getChildContexts(question).second);

		Context chansetContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Alphabetised parallelism precalcualted channelsets", question);

		chansetContext.put(NamespaceUtility.getLeftPrecalculatedChannetSet(), leftChanset);
		chansetContext.put(NamespaceUtility.getRightPrecalculatedChannetSet(), rightChanset);

		return new Pair<INode, Context>(node, chansetContext);
	}

	/**
	 * Utility method to evaluate or generate channel set values. If called from a context where
	 * {@link NamespaceUtility#getReplicationNodeReminderName(INode)} is in context for the node parsed then the
	 * {@link CmlSetQuantifier} is used to evaluate the chanset expression for every value. If not then the chanset is
	 * evaluated in the context parsed.
	 * 
	 * @param node
	 *            the node where the qualifier reminder name is obtained by
	 * @param chansetExp
	 *            the chanset exp to evaluate
	 * @param ctxt
	 *            the context where the qualifier reminder name may exist in and the context where the chanset exp
	 *            should be evaluated
	 * @return a value holding all the values for the chanset exp
	 * @throws AnalysisException
	 */
	private SetValue generateChannelValues(INode node,
			PVarsetExpression chansetExp, Context ctxt)
			throws AnalysisException
	{
		final ILexNameToken name = NamespaceUtility.getReplicationNodeReminderName(node);
		CmlSetQuantifier ql = (CmlSetQuantifier) ctxt.check(name);

		SetValue rightChanset = null;

		if (ql == null)
		{
			rightChanset = eval(chansetExp, ctxt);
		} else
		{
			rightChanset = new SetValue();

			for (NameValuePairList nvpl : ql)
			{
				Context nextChildContext = new Context(ctxt.assistantFactory, ctxt.location, "local channel context", ctxt);
				nextChildContext.putList(nvpl);
				final SetValue eval = (SetValue) eval(chansetExp, nextChildContext);
				rightChanset.values.addAll(eval.values);
			}
		}

		return rightChanset;

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

	/*
	 * Non public replication helper methods -- Start
	 */

	/*
	 * Replication
	 */

	abstract static class AbstractReplicationFactory
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
		 * Creates the terminator for the given node. The default is Skip.
		 * 
		 * @return
		 */
		INode createTerminator()
		{
			return CmlAstFactory.newASkipAction(LocationExtractor.extractLocation(node));
		}

		/**
		 * Create a new replication child context. This is a context that delays all writes to state
		 * 
		 * @param npvl
		 * @param node
		 * @param outer
		 * @return
		 */
		Context createReplicationDelayedChildContext(NameValuePairList npvl,
				INode node, Context outer)
		{
			Context childContext = createDelayedContext(outer, node);

			for (NameValuePair nvp : npvl)
			{
				// turn the values into constants, important for the delayed context removal later
				NameValuePair pair = new NameValuePair(nvp.name, nvp.value.getConstant());
				childContext.putNew(pair);
			}

			return childContext;
		}

		/**
		 * Create a new replication child context for a particular node. This is a context that delays all writes to
		 * state. Note that the creation will be skipped if the location of the node matches the location set in the
		 * outer context, if so the outer context is returned.
		 * 
		 * @param outer
		 * @param currentNode
		 * @return
		 */
		public static Context createDelayedContext(Context outer,
				INode currentNode)
		{
			final ILexLocation location = LocationExtractor.extractLocation(currentNode);

			Context oCtxt = outer;
			while(oCtxt.outer!=null)
			{
				if (oCtxt instanceof DelayedWriteContext && ((DelayedWriteContext)oCtxt).isOwnedBy(currentNode))
				{
					return outer;// already done of this node
				}	
				oCtxt = oCtxt.outer;
			}
			
			Context childContext = new DelayedWriteContext(currentNode,outer.assistantFactory, location, "delayed write context for "
					+ outer.title, outer);
			return childContext;
		}

		/**
		 * Create a new replication child context.
		 * 
		 * @param npvl
		 * @param node
		 * @param outer
		 * @return
		 */
		Context createReplicationChildContext(NameValuePairList npvl,
				INode node, Context outer)
		{
			Context childContext = new Context(outer.assistantFactory, LocationExtractor.extractLocation(node), "local context for "
					+ outer.title, outer);

			for (NameValuePair nvp : npvl)
			{
				// turn the values into constants, important for the delayed context removal later
				NameValuePair pair = new NameValuePair(nvp.name, nvp.value.getConstant());
				childContext.putNew(pair);
			}

			return childContext;
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
		ILexNameToken replicationContextValueName = NamespaceUtility.getReplicationNodeName(node);
		CmlSetQuantifier ql = (CmlSetQuantifier) question.check(replicationContextValueName);

		if (ql == null)
		{
			// evaluate decelerations
			ql = createQuantifierList(decls, question);
		} else
		{
			question = question.outer;
		}

		Iterator<NameValuePairList> itQuantifiers = ql.iterator();

		if (/* empty */!itQuantifiers.hasNext())
		{
			/*
			 * No decelerations exists, thus just terminate it
			 */
			return new Pair<INode, Context>(factory.createTerminator(), question);
		} else
		{
			final NameValuePairList nextChildValue = CmlSetQuantifier.pop(itQuantifiers);

			if (!itQuantifiers.hasNext())
			{
				/*
				 * Optimization applies here so avoid subtree with terminator on the right. This allows |~| to use this
				 * method as well
				 */
				final INode nextNode = factory.getReplicatedNode();
				/*
				 * do not create delayed context here. There is not way to determine when to write the values and there
				 * is only ever one of these
				 */
				final Context nextContext = factory.createReplicationChildContext(nextChildValue, nextNode, question);

				storeReplificationQualifierRemainder(nextNode, ql, nextContext);

				return new Pair<INode, Context>(nextNode, nextContext);
			} else
			{
				/* recursive case */
				final INode nextNode = factory.createNextReplication();

				final Context replicatedContext = new ReplicatedContext(question.assistantFactory, question.location, "Replicated declaration ctxt", question);
				replicatedContext.put(replicationContextValueName, ql);
				storeReplificationQualifierRemainder(nextNode, ql, replicatedContext);

				Context leftChildContext = factory.createReplicationDelayedChildContext(nextChildValue, nextNode, question);
				storeReplificationQualifierRemainder(nextNode, ql, leftChildContext);

				Context rightChildContext = replicatedContext;

				setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));

				return new Pair<INode, Context>(nextNode, replicatedContext);
			}
		}

	}

	private static void storeReplificationQualifierRemainder(INode node,
			CmlSetQuantifier ql, Context ctxt)
	{
		// store the reminder of replication qualifier for channel set expression evaluations of e.g.
		// AAlphabetisedParallelismReplicatedProcess
		ctxt.put(NamespaceUtility.getReplicationNodeReminderName(node), ql);
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
			for (NameValuePair nvp : singleDecl.apply(question.assistantFactory.getNamedValueLister(), question))
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
		if (val.deref() instanceof SetValue)
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
