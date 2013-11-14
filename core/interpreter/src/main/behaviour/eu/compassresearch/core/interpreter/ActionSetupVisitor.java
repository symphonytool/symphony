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
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

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
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
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
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.CmlQuantifierList;
import eu.compassresearch.core.interpreter.api.values.LatticeTopValue;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;
import eu.compassresearch.core.interpreter.utility.Pair;
import eu.compassresearch.core.interpreter.utility.SetMath;

class ActionSetupVisitor extends AbstractSetupVisitor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner, visitorAccess);
	}
	
	@Override
	public Pair<INode, Context> caseAActionStm(AActionStm node, Context question)
			throws AnalysisException
	{
		return node.getAction().apply(this,question);
	}
	
	@Override
	public Pair<INode, Context> caseAStmAction(AStmAction node, Context question)
			throws AnalysisException
	{
		return node.getStatement().apply(this,question);
	}

	/*
	 * Sequential Composition -- Start
	 */

	private Pair<INode, Context> caseASequentialComposition(INode node,
			INode leftNode, Context question) throws AnalysisException
	{
		// We set up the left child of the sequential process/action before entering. The right will not
		// be touched before the left has terminated
		setLeftChild(new ConcreteCmlBehaviour(leftNode, question, new LexNameToken("", owner.name().getSimpleName()
				+ ";", owner.name().getLocation()), owner));
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
		setLeftChild(new ConcreteCmlBehaviour(node.getLeft(), question, owner));
		return new Pair<INode, Context>(node, question);
	}

	@Override
	public Pair<INode, Context> caseAHidingProcess(AHidingProcess node,
			Context question) throws AnalysisException
	{
		// We setup the child node for the hiding operator
		setLeftChild(new ConcreteCmlBehaviour(node.getLeft(), question, owner));
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
		setLeftChild(new ConcreteCmlBehaviour(leftNode, question, owner));
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
		setLeftChild(new ConcreteCmlBehaviour(leftNode, question, owner));
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

	interface ReplicationFactory
	{
		INode createNextReplication();

		INode createLastReplication();
	}

	protected CmlQuantifierList createQuantifierList(
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
						throw new CmlInterpreterException(singleDecl, InterpretationErrorMessages.UNBOUNDED_REPLICATION.customizeMessage());
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
			for (Value val : nvp.value.setValue(question))
				quantifierValues.values.add(new TupleValue(new ValueList(val)));

		} else
		{
			// If more than one decl then we need to calculate the cross product of them
			// First we append all the sets into a list
			List<SetValue> sets = new LinkedList<SetValue>();
			for (NameValuePair nvp : replicationDecls)
			{
				List<Value> values = new LinkedList<Value>();
				for (Value val : nvp.value.setValue(question))
					values.add(val);
				quantifierNames.add(nvp.name);
				sets.add(new SetValue(nvp.value.setValue(question)));
			}
			quantifierValues = SetMath.getCrossProduct(sets);
		}

		return new CmlQuantifierList(quantifierNames, quantifierValues);
	}

	protected Context createReplicationChildContext(
			Iterator<NameValuePairList> itQuantifiers, INode node, Context outer)
	{
		Context childContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "", outer);
		childContext.putAllNew(itQuantifiers.next());
		itQuantifiers.remove();
		return childContext;
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

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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
	public Pair<INode, Context> caseASynchronousParallelismReplicatedAction(
			final ASynchronousParallelismReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
		{

			@Override
			public INode createNextReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new ASynchronousParallelismParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression(), node.getNamesetExpression(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				// TODO The i'th namesetexpression should be evaluated in the i'th context
				return new ASynchronousParallelismParallelAction(node.getLocation(), node.getReplicatedAction().clone(), node.getNamesetExpression(), node.getNamesetExpression(), node.getReplicatedAction().clone());
			}
		}, question);
	}

	@Override
	public Pair<INode, Context> caseAExternalChoiceReplicatedAction(
			final AExternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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
	}

	@Override
	public Pair<INode, Context> caseAInternalChoiceReplicatedAction(
			final AInternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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

	protected Pair<INode, Context> caseReplicated(INode node,
			List<PSingleDeclaration> decls, ReplicationFactory factory,
			Context question) throws AnalysisException
	{

		// The name of the value holding the state of the remaining values of the replication
		ILexNameToken replicationContextValueName = NamespaceUtility.getReplicationNodeName(node);
		CmlQuantifierList ql = (CmlQuantifierList) question.check(replicationContextValueName);

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
		// If we have two replication values then we need to have one interleaving action, since
		// each value represents one process replication

		if (ql.size() == 1)
		{
			nextNode = factory.createLastReplication();

			Context leftChildContext = question.outer;
			Context rightChildContext = createReplicationChildContext(itQuantifiers, node, question.outer.outer);
			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));

		} else if (firstRun && ql.size() == 2)
		{
			nextNode = factory.createLastReplication();

			Context leftChildContext = createReplicationChildContext(itQuantifiers, node, question);
			Context rightChildContext = createReplicationChildContext(itQuantifiers, node, question);
			// the replication context, if it exist is lowest. But if this is the first run
			// then the replication context does not exist

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
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
				leftChildContext = createReplicationChildContext(itQuantifiers, node, question);
				rightChildContext = createReplicationChildContext(itQuantifiers, node, question);
			} else
			{
				leftChildContext = question.outer;
				// if this is not the first run the the replication context already exist
				// so we can pull out the parent and attach the right child context to this and
				// then attach the replication
				rightChildContext = createReplicationChildContext(itQuantifiers, node, question.outer.outer);
			}

			rightChildContext = CmlContextFactory.newContext(LocationExtractor.extractLocation(node), "replication contexts", rightChildContext);
			rightChildContext.putNew(new NameValuePair(replicationContextValueName, ql));

			setChildContexts(new Pair<Context, Context>(leftChildContext, rightChildContext));
		}

		return new Pair<INode, Context>(nextNode, question);

	}

	@Override
	public Pair<INode, Context> caseAGeneralisedParallelismReplicatedProcess(
			final AGeneralisedParallelismReplicatedProcess node,
			Context question) throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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
	public Pair<INode, Context> caseASynchronousParallelismReplicatedProcess(
			final ASynchronousParallelismReplicatedProcess node,
			Context question) throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
		{

			@Override
			public INode createNextReplication()
			{
				return new ASynchronousParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.clone());
			}

			@Override
			public INode createLastReplication()
			{
				return new ASynchronousParallelismProcess(node.getLocation(), node.getReplicatedProcess().clone(), node.getReplicatedProcess().clone());
			}
		}, question);
	}

	@Override
	public Pair<INode, Context> caseAInterleavingReplicatedProcess(
			final AInterleavingReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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
		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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
		return caseReplicated(node, node.getReplicationDeclaration(), new ReplicationFactory()
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

	/*
	 * Non public replication helper methods -- Start
	 */
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

	/*
	 * Non public replication helper methods -- End
	 */

	protected Pair<INode, Context> caseAInterrupt(INode node, INode leftNode,
			INode rightNode, Context question) throws AnalysisException
	{
		// TODO create proper names!!
		setLeftChild(new ConcreteCmlBehaviour(leftNode, question, new LexNameToken("", "left /_\\", new LexLocation()), owner));
		setRightChild(new ConcreteCmlBehaviour(rightNode, question, new LexNameToken("", "/_\\ right", new LexLocation()), owner));

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
