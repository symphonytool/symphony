package eu.compassresearch.core.interpreter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.ast.types.SNumericBasicType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

import eu.compassresearch.ast.CmlAstFactory;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
import eu.compassresearch.ast.analysis.DepthFirstAnalysisCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInterleavingReplicatedProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASequentialCompositionReplicatedProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.CmlSetQuantifier;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.interpreter.utility.Pair;
import eu.compassresearch.core.interpreter.utility.SetMath;

class ActionSetupVisitor extends AbstractSetupVisitor
{

	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner, visitorAccess);
	}

	@Override
	public Pair<INode, Context> caseAActionStm(AActionStm node, Context question)
			throws AnalysisException
	{
		return node.getAction().apply(this, question);
	}

	@Override
	public Pair<INode, Context> caseAStmAction(AStmAction node, Context question)
			throws AnalysisException
	{
		return node.getStatement().apply(this, question);
	}

	/*
	 * Sequential Composition -- Start
	 */

	private Pair<INode, Context> caseASequentialComposition(INode node,
			INode leftNode, Context question) throws AnalysisException
	{
		// We set up the left child of the sequential process/action before entering. The right will not
		// be touched before the left has terminated
		setLeftChild(leftNode, new LexNameToken("", owner.name().getSimpleName()
				+ ";", owner.name().getLocation()), question);
		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException
	{

		return caseASequentialComposition(node, node.getLeft(), question);
	}

	@Override
	public Pair<INode, Context> caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException
	{

		return caseASequentialComposition(node, node.getLeft(), question);
	}

	/*
	 * Sequential Composition -- End
	 */

	@Override
	public Pair<INode, Context> caseAHidingAction(AHidingAction node,
			Context question) throws AnalysisException
	{
		// We setup the child node for the hiding operator
		setLeftChild(node.getLeft(), question);
		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseAHidingProcess(AHidingProcess node,
			Context question) throws AnalysisException
	{
		// We setup the child node for the hiding operator
		setLeftChild(node.getLeft(), question);
		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseAWaitAction(AWaitAction node,
			Context question) throws AnalysisException
	{

		Context context = CmlContextFactory.newContext(node.getLocation(), "Wait context", question);
		context.putNew(new NameValuePair(NamespaceUtility.getStartTimeName(), new IntegerValue(owner.getCurrentTime())));

		return new Pair<INode, Context>(node, context);
	}

	private Pair<INode, Context> caseATimeout(INode node, INode leftNode,
			Context question) throws AnalysisException
	{

		Context context = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "Timeout context", question);
		context.putNew(new NameValuePair(NamespaceUtility.getStartTimeName(), new IntegerValue(owner.getCurrentTime())));

		// We setup the child nodes
		setLeftChild(leftNode, question);
		return new Pair<INode, Context>(node, context);

	}

	@Override
	public Pair<INode, Context> caseATimeoutAction(ATimeoutAction node,
			Context question) throws AnalysisException
	{
		return caseATimeout(node, node.getLeft(), question);
	}

	@Override
	public Pair<INode, Context> caseATimeoutProcess(ATimeoutProcess node,
			Context question) throws AnalysisException
	{
		return caseATimeout(node, node.getLeft(), question);
	}

	public Pair<INode, Context> caseAUntimedTimeout(INode node, INode leftNode,
			Context question) throws AnalysisException
	{

		// We setup the child nodes
		setLeftChild(leftNode, question);
		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node, Context question)
			throws AnalysisException
	{

		return caseAUntimedTimeout(node, node.getLeft(), question);
	}

	@Override
	public Pair<INode, Context> caseAUntimedTimeoutProcess(
			AUntimedTimeoutProcess node, Context question)
			throws AnalysisException
	{
		return caseAUntimedTimeout(node, node.getLeft(), question);
	}

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

	/*
	 * Non public replication helper methods -- Start
	 */

	protected Pair<INode, Context> caseReplicated(INode node,
			List<PSingleDeclaration> decls, AbstractReplicationFactory factory,
			Context question) throws AnalysisException
	{

		// The name of the value holding the state of the remaining values of the replication
		ILexNameToken replicationContextValueName = NamespaceUtility.getReplicationNodeName(node);
		CmlSetQuantifier ql = (CmlSetQuantifier) question.check(replicationContextValueName);
		Context next = question;
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
			return new Pair<INode, Context>(factory.getReplicatedNode(), createReplicationChildContext(nextValue, node, question));
		}
		// and this is NOT the first run then we created the context for the left side already right above the
		// current context
		else if (!itQuantifiers.hasNext() && !firstRun)
		{
			nextNode = factory.createLastReplication();
			// the outer is the pre-calculated context from the previous run
			Context leftChildContext = question.outer;
			// we take the outer.outer because we want the parent context of this one to be the one given to the
			// replication node
			Context rightChildContext = createReplicationChildContext(nextValue, node, question.outer.outer);
			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
			return new Pair<INode, Context>(nextNode, next);
		}

		NameValuePairList afterNextValue = itQuantifiers.next();
		// itQuantifiers.remove();

		// If no values are left then we have exactly 2 values and thus we must create the last replication
		if (!itQuantifiers.hasNext() && firstRun)
		{
			nextNode = factory.createLastReplication();

			Context leftChildContext = createReplicationChildContext(nextValue, node, question);
			Context rightChildContext = createReplicationChildContext(afterNextValue, node, question);
			itQuantifiers.remove();
			// the replication context, if it exist is lowest. But if this is the first run
			// then the replication context does not exist

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));

			return new Pair<INode, Context>(nextNode, next);
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
				leftChildContext = createReplicationChildContext(nextValue, node, question);
				rightChildContext = createReplicationChildContext(afterNextValue, node, question);
				itQuantifiers.remove();
			} else
			{
				// if this is not the first run the the replication context already exist
				// so we can pull out the parent and attach the right child context to this and
				// then attach the replication
				leftChildContext = question.outer;
				rightChildContext = createReplicationChildContext(nextValue, node, question.outer.outer);
			}

			rightChildContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "replication contexts", rightChildContext);
			rightChildContext.putNew(new NameValuePair(replicationContextValueName, ql));

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
			return new Pair<INode, Context>(nextNode, next);
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

		// List<List<Value>> values = new LinkedList<List<Value>>();
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

	protected Context createReplicationChildContext(NameValuePairList npvl,
			INode node, Context outer)
	{
		Context childContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "", outer);
		childContext.putAllNew(npvl);
		return childContext;
	}

	/*
	 * Non public replication helper methods -- End
	 */

	/*
	 * Replicated actions
	 */

	@Override
	public Pair<INode, Context> caseASequentialCompositionReplicatedAction(
			final ASequentialCompositionReplicatedAction node, Context question)
			throws AnalysisException
	{
		Pair<INode, Context> res = caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{
			@Override
			public INode createNextReplication()
			{
				return new ASequentialCompositionAction(node.getLocation(), node.getReplicatedAction().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new ASequentialCompositionAction(node.getLocation(), node.getReplicatedAction().clone(), node.getReplicatedAction().clone());
			}
		}, question);

		return res.first.apply(ActionSetupVisitor.this, res.second);
	}

	/**
	 * Replicated interleaving Syntax : '|||' , replication declarations , @ , action Example : |||i:e @ A(i) Execute
	 * all the actions A(i) in parallel without sync
	 */
	@Override
	public Pair<INode, Context> caseAInterleavingReplicatedAction(
			final AInterleavingReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{
			@Override
			public INode createNextReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new AInterleavingParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression().clone(), node.getNamesetExpression().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new AInterleavingParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression().clone(), node.getNamesetExpression().clone(), node.getReplicatedAction().clone());
			}
		}, question);
	}

	@Override
	public Pair<INode, Context> caseAGeneralisedParallelismReplicatedAction(
			final AGeneralisedParallelismReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new AGeneralisedParallelismParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression(), node.getNamesetExpression(), node.clone(), node.getChansetExpression().clone());
			}

			@Override
			public INode createLastReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new AGeneralisedParallelismParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression(), node.getNamesetExpression(), node.getReplicatedAction().clone(), node.getChansetExpression().clone());
			}

		}, question);
	}

	@Override
	public Pair<INode, Context> caseAExternalChoiceReplicatedAction(
			final AExternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{
		Pair<INode, Context> ret = caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				return new AExternalChoiceAction(node.getLocation(), node.getReplicatedAction().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AExternalChoiceAction(node.getLocation(), node.getReplicatedAction().clone(), node.getReplicatedAction().clone());
			}

		}, question);

		if (ret.first instanceof ASkipAction)
		{
			return new Pair<INode, Context>(new AStopAction(node.getLocation()), question);
		} else
		{
			return ret;
		}
	}

	@Override
	public Pair<INode, Context> caseAInternalChoiceReplicatedAction(
			final AInternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				return new AInternalChoiceAction(node.getLocation(), node.getReplicatedAction().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AInternalChoiceAction(node.getLocation(), node.getReplicatedAction().clone(), node.getReplicatedAction().clone());
			}

		}, question);
	}

	/*
	 * Replicated processes
	 */

	@Override
	public Pair<INode, Context> caseASequentialCompositionReplicatedProcess(
			final ASequentialCompositionReplicatedProcess node, Context question)
			throws AnalysisException
	{
		Pair<INode, Context> res = caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{
			@Override
			public INode createNextReplication()
			{
				return new ASequentialCompositionProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new ASequentialCompositionProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getReplicatedProcess().clone());
			}
		}, question);

		return res.first.apply(ActionSetupVisitor.this, res.second);
	}

	@Override
	public Pair<INode, Context> caseAGeneralisedParallelismReplicatedProcess(
			final AGeneralisedParallelismReplicatedProcess node,
			Context question) throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				return new AGeneralisedParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getChansetExpression().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AGeneralisedParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getChansetExpression().clone(), node.getReplicatedProcess().clone());
			}

		}, question);
	}

	@Override
	public Pair<INode, Context> caseAInterleavingReplicatedProcess(
			final AInterleavingReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				return new AInterleavingProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AInterleavingProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getReplicatedProcess().clone());
			}

		}, question);
	}

	@Override
	public Pair<INode, Context> caseAAlphabetisedParallelismReplicatedProcess(
			final AAlphabetisedParallelismReplicatedProcess node,
			final Context question) throws AnalysisException
	{
		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{

				return new AAlphabetisedParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getChansetExpression().clone(), node.getChansetExpression().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AAlphabetisedParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getChansetExpression().clone(), node.getChansetExpression().clone(), node.getReplicatedProcess().clone());
			}

		}, question);
	}

	@Override
	public Pair<INode, Context> caseAInternalChoiceReplicatedProcess(
			final AInternalChoiceReplicatedProcess node, Context question)
			throws AnalysisException
	{
		return caseReplicated(node, node.getReplicationDeclaration(), new AbstractReplicationFactory(node)
		{

			@Override
			public INode createNextReplication()
			{
				return new AInternalChoiceProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new AInternalChoiceProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getReplicatedProcess().clone());
			}

		}, question);
	}

	protected Pair<INode, Context> caseAInterrupt(INode node, INode leftNode,
			INode rightNode, Context question) throws AnalysisException
	{
		// TODO create proper names!!
		setLeftChild(leftNode, new LexNameToken("", "left /_\\", new LexLocation()), question);
		setRightChild(rightNode, new LexNameToken("", "/_\\ right", new LexLocation()), question);

		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseAInterruptAction(AInterruptAction node,
			Context question) throws AnalysisException
	{
		return caseAInterrupt(node, node.getLeft(), node.getRight(), question);
	}

	@Override
	public Pair<INode, Context> caseAInterruptProcess(AInterruptProcess node,
			Context question) throws AnalysisException
	{
		return caseAInterrupt(node, node.getLeft(), node.getRight(), question);
	}

	@Override
	public Pair<INode, Context> caseAForPatternBindStm(AForPatternBindStm node,
			Context question) throws AnalysisException
	{

		Context context = CmlContextFactory.newContext(node.getLocation(), "Sequence for loop context", question);
		context.putNew(new NameValuePair(NamespaceUtility.getSeqForName(), node.getExp().apply(cmlExpressionVisitor, question)));

		return new Pair<INode, Context>(node, context);
	}
}
