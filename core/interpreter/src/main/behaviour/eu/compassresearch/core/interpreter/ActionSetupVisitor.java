package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
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

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.SReplicatedAction;
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
import eu.compassresearch.ast.process.SReplicatedProcess;
import eu.compassresearch.core.interpreter.api.CmlInterpreterException;
import eu.compassresearch.core.interpreter.api.InterpretationErrorMessages;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.LatticeTopElement;
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

	protected Pair<INode, Context> caseReplicatedAction(SReplicatedAction node,
			ReplicationFactory factory, Context question)
			throws AnalysisException
	{
		NameValuePairList replicationDecls = new NameValuePairList();
		Pair<SetValue, Context> pair = getCurrentReplicationValue(node.getLocation(), node.getReplicationDeclaration(), replicationDecls, question);

		SetValue setValue = pair.first;
		Context nextContext = pair.second;

		INode nextNode = null;

		if (setValue.values.size() == 1)
			throw new AnalysisException("A replicated action must have at least two enumeration values");
		// If we have two replication values then we need to have one interleaving action, since
		// each value represents one process replication
		else if (setValue.values.size() == 2)
		{
			nextNode = factory.createLastReplication();

			setChildContexts(new Pair<Context, Context>(convertReplicationToContext(setValue.values.get(0), replicationDecls, node.getLocation(), question), convertReplicationToContext(setValue.values.get(1), replicationDecls, node.getLocation(), question)));
		}
		// If we have more than two replication values then we make an interleaving between the
		// first value and the rest of the replicated values
		else
		{
			nextNode = factory.createNextReplication();

			setChildContexts(new Pair<Context, Context>(convertReplicationToContext(setValue.values.get(0), replicationDecls, node.getLocation(), question), nextContext));

			setValue.values.remove(0);
		}

		return new Pair<INode, Context>(nextNode, question);
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

		return caseReplicatedAction(node, new ReplicationFactory()
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

		return caseReplicatedAction(node, new ReplicationFactory()
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

		return caseReplicatedAction(node, new ReplicationFactory()
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

		return caseReplicatedAction(node, new ReplicationFactory()
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

		return caseReplicatedAction(node, new ReplicationFactory()
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

	protected Pair<INode, Context> caseReplicatedProcess(
			SReplicatedProcess node, ReplicationFactory factory,
			Context question) throws AnalysisException
	{
		NameValuePairList replicationDecls = new NameValuePairList();
		Pair<SetValue, Context> pair = getCurrentReplicationValue(node.getLocation(), node.getReplicationDeclaration(), replicationDecls, question);

		SetValue setValue = pair.first;
		Context nextContext = pair.second;

		INode nextNode = null;

		if (setValue.values.size() == 1)
			throw new AnalysisException("A replicated action must have at least two enumeration values");
		// If we have two replication values then we need to have one interleaving action, since
		// each value represents one process replication
		else if (setValue.values.size() == 2)
		{
			nextNode = factory.createLastReplication();

			setChildContexts(new Pair<Context, Context>(convertReplicationToContext(setValue.values.get(0), replicationDecls, node.getLocation(), question), convertReplicationToContext(setValue.values.get(1), replicationDecls, node.getLocation(), question)));
		}
		// If we have more than two replication values then we make an interleaving between the
		// first value and the rest of the replicated values
		else
		{
			nextNode = factory.createNextReplication();

			setChildContexts(new Pair<Context, Context>(convertReplicationToContext(setValue.values.get(0), replicationDecls, node.getLocation(), question), nextContext));

			setValue.values.remove(0);
		}

		return new Pair<INode, Context>(nextNode, question);
	}

	@Override
	public Pair<INode, Context> caseAGeneralisedParallelismReplicatedProcess(
			final AGeneralisedParallelismReplicatedProcess node,
			Context question) throws AnalysisException
	{

		return caseReplicatedProcess(node, new ReplicationFactory()
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

		return caseReplicatedProcess(node, new ReplicationFactory()
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

		return caseReplicatedProcess(node, new ReplicationFactory()
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
			final AAlphabetisedParallelismReplicatedProcess node, final Context question)
			throws AnalysisException
	{
		return caseReplicatedProcess(node, new ReplicationFactory()
		{

			@Override
			public INode createNextReplication()
			{
				
				return new AAlphabetisedParallelismProcess(
						node.getLocation(), 
						node.getReplicatedProcess().clone(),
						node.getChansetExpression().clone(), 
						node.getChansetExpression().clone(),
						node);
			}

			@Override
			public INode createLastReplication()
			{
				return new AAlphabetisedParallelismProcess(
						node.getLocation(), 
						node.getReplicatedProcess().clone(),
						node.getChansetExpression().clone(), 
						node.getChansetExpression().clone(),
						node);
			}

		}, question);
	}

	@Override
	public Pair<INode, Context> caseAInternalChoiceReplicatedProcess(
			final AInternalChoiceReplicatedProcess node, Context question)
			throws AnalysisException
	{
		return caseReplicatedProcess(node, new ReplicationFactory()
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

	private Context convertReplicationToContext(Value value,
			NameValuePairList replicationDecls, ILexLocation location,
			Context outer) throws ValueException
	{
		Context context = CmlContextFactory.newContext(location, "", outer);

		ValueList tuple = value.tupleValue(outer);
		for (int i = 0; i < tuple.size(); i++)
		{
			context.putNew(new NameValuePair(replicationDecls.get(i).name, tuple.get(i)));
		}

		return context;
	}

	private SetValue convertReplDeclToSetValue(
			NameValuePairList replicationDecls, Context question)
			throws ValueException
	{
		SetValue setValue = null;

		if (replicationDecls.size() == 1)
		{
			setValue = new SetValue();

			NameValuePair nvp = replicationDecls.get(0);
			for (Value val : nvp.value.setValue(question))
				setValue.values.add(new TupleValue(new ValueList(val)));

		} else
		{
			// If more than one decl then we need to calculate the cross product of them
			// First we append all the sets into a list
			List<SetValue> sets = new LinkedList<SetValue>();
			for (NameValuePair nvp : replicationDecls)
				sets.add(new SetValue(nvp.value.setValue(question)));

			setValue = SetMath.getCrossProduct(sets);
		}

		return setValue;
	}

	protected Pair<SetValue, Context> getCurrentReplicationValue(
			ILexLocation location,
			List<PSingleDeclaration> replicationDeclaration,
			NameValuePairList replicationNvpl, Context question)
			throws AnalysisException
	{
		// The name of the value holding the state of the remaining values of the replication
		LexNameToken replicationContextValueName = new LexNameToken("|REPLICATION|", location.toShortString(), location);

		Value value = question.check(replicationContextValueName);

		// Convert all the single decls into a NameValuePairList
		for (PSingleDeclaration singleDecl : replicationDeclaration)
		{
			for(NameValuePair nvp : singleDecl.apply(this.cmlDefEvaluator, question))
			{
				//We do not allow unbounded replication 
				//FIXME this check is not sufficient, this needs to be more general
				if(nvp.value instanceof LatticeTopValue && 
						((LatticeTopValue)nvp.value).getType() instanceof SNumericBasicType)
				{
					throw new CmlInterpreterException(singleDecl,InterpretationErrorMessages.UNBOUNDED_REPLICATION.customizeMessage());
				}
				
				
				replicationNvpl.add(nvp);
			}
		}

		SetValue setValue = null;
		Context nextContext = question;

		// if null then this is the first action of the replication
		// then we need to evaluate the
		if (value == null)
		{
			setValue = convertReplDeclToSetValue(replicationNvpl, question);
			// Make a set of tuples
			nextContext = CmlContextFactory.newContext(location, "replication contexts", question);
			nextContext.putNew(new NameValuePair(replicationContextValueName, setValue));
		} else
			setValue = new SetValue(value.setValue(question));

		return new Pair<SetValue, Context>(setValue, nextContext);
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
	public Pair<INode, Context> caseAForSequenceStatementAction(
			AForSequenceStatementAction node, Context question)
			throws AnalysisException
	{

		Context context = CmlContextFactory.newContext(node.getLocation(), "Sequence for loop context", question);
		context.putNew(new NameValuePair(NamespaceUtility.getSeqForName(), node.getExp().apply(cmlExpressionVisitor, question)));

		return new Pair<INode, Context>(node, context);
	}
}
