package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.NameValuePair;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
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
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviorFactory;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;

@SuppressWarnings("deprecation")
class ActionSetupVisitor extends CommonSetupVisitor
{

	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess,
			CmlBehaviorFactory cmlBehaviorFactory)
	{
		super(owner, visitorAccess, cmlBehaviorFactory);
	}

	@Override
	public Pair<INode, Context> caseAActionStm(AActionStm node, Context question)
			throws AnalysisException
	{
		return node.getAction().apply(this, question);
	}

	@Override
	public Pair<INode, Context> caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, Context question)
			throws AnalysisException
	{
		return caseAlphabetisedParallelism(node, node.getLeftChansetExpression(), node.getRightChansetExpression(), question);
	}

	@Override
	public Pair<INode, Context> caseAStmAction(AStmAction node, Context question)
			throws AnalysisException
	{
		return node.getStatement().apply(this, question);
	}

	/*
	 * Sequential Composition
	 */
	@Override
	public Pair<INode, Context> caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException
	{

		return caseASequentialComposition(node, node.getLeft(), question);
	}

	@Override
	public Pair<INode, Context> caseAHidingAction(AHidingAction node,
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

	/*
	 * Timeout
	 */
	@Override
	public Pair<INode, Context> caseATimeoutAction(ATimeoutAction node,
			Context question) throws AnalysisException
	{
		return caseATimeout(node, node.getLeft(), question);
	}

	/*
	 * Untimed timeout
	 */

	@Override
	public Pair<INode, Context> caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node, Context question)
			throws AnalysisException
	{

		return caseAUntimedTimeout(node, node.getLeft(), question);
	}

	/*
	 * Timed Interrupt
	 */

	@Override
	public Pair<INode, Context> caseATimedInterruptAction(
			ATimedInterruptAction node, Context question)
			throws AnalysisException
	{
		return caseATimedInterrupt(node, node.getLeft(), question);
	}

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

	@Override
	public Pair<INode, Context> caseAInterruptAction(AInterruptAction node,
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
