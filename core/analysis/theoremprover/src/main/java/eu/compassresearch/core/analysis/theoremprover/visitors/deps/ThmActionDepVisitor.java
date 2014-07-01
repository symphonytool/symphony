package eu.compassresearch.core.analysis.theoremprover.visitors.deps;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.theoremprover.thms.NodeNameList;

public class ThmActionDepVisitor  extends
QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList>{
	
	final private QuestionAnswerCMLAdaptor<NodeNameList, NodeNameList> thmDepVisitor;
	
	public ThmActionDepVisitor(ThmDepVisitor thmDepVisitor) {
		this.thmDepVisitor = thmDepVisitor;
	}
	
	public NodeNameList caseASkipAction(ASkipAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseAStopAction(AStopAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseADivAction(ADivAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
	
		return nodeDeps;
	}
	
	public NodeNameList caseACommunicationAction(ACommunicationAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
						
		for( PCommunicationParameter p: a.getCommunicationParameters())
		{
			if(p instanceof AReadCommunicationParameter)
			{
				AReadCommunicationParameter cp = (AReadCommunicationParameter) p;
				PPattern patt = cp.getPattern();
				if(patt instanceof AIdentifierPattern)
				{
					AIdentifierPattern ip = (AIdentifierPattern) patt;
					for (PDefinition def : ip.getDefinitions())
					{
						nodeDeps.addAll(def.getType().apply(thmDepVisitor, bvars));
					}
					bvars.add(ip.getName());
				}
			}
			else if (p instanceof AWriteCommunicationParameter)
			{
				AWriteCommunicationParameter cp = (AWriteCommunicationParameter) p;
				nodeDeps.addAll(cp.getExpression().apply(thmDepVisitor, bvars));
				
			}else
			{
				nodeDeps.addAll(p.getExpression().apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAGuardedAction(AGuardedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseASequentialCompositionAction(ASequentialCompositionAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAExternalChoiceAction(AExternalChoiceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAInternalChoiceAction(AInternalChoiceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAHidingAction(AHidingAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getChansetExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAMuAction(AMuAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
	//	for (ILexIdentifierToken id : a.getIdentifiers()) 
	//	{
	//		
	//		nodeDeps.add(new LexNameToken("", id.getName().toString(), a.getLocation()));
	//	}
		
	//	for (PAction pa : a.getActions())
	//	{
	//		nodeDeps.addAll(ThmProcessUtil.getIsabelleActionDeps(pa, bvars));
	//	}

		return nodeDeps;
	}
	
	public NodeNameList caseAReferenceAction(AReferenceAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		LinkedList<PExp> args = a.getArgs();
		if (args.size() != 0)
		{
			for (PExp e : a.getArgs()) 
			{
				nodeDeps.addAll(e.apply(thmDepVisitor, bvars));
			}
		}
		nodeDeps.add(a.getName());

		return nodeDeps;
	}

	
	public NodeNameList caseAInterleavingParallelAction(AInterleavingParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}
	
	public NodeNameList caseAGeneralisedParallelismParallelAction(AGeneralisedParallelismParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeftAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRightAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getChansetExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}

	public NodeNameList caseAStmAction(AStmAction a, NodeNameList bvars) throws AnalysisException{ 
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getStatement().apply(thmDepVisitor, bvars));

		return nodeDeps;	
	}

	public NodeNameList caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		return nodeDeps;
	}
	
	public NodeNameList caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getNamesetExpression().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	public NodeNameList caseAChannelRenamingAction(AChannelRenamingAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}

	public NodeNameList caseAEndDeadlineAction(AEndDeadlineAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
	
	public NodeNameList caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getNamesetExpression().apply(thmDepVisitor, bvars));
		
		// FIXME: Add support declarations with the action
		
		return nodeDeps;
	}

	public NodeNameList caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getNamesetExpression().apply(thmDepVisitor, bvars));
		
		// FIXME: Add support declarations with the action		
				
		return nodeDeps;
	}
	
	public NodeNameList caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		
		// FIXME: Add support declarations with the action		
				
		return nodeDeps;
	}

	public NodeNameList caseAInterruptAction(AInterruptAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}
	
	public NodeNameList caseAParametrisedAction(AParametrisedAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}

	public NodeNameList caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getReplicatedAction().apply(thmDepVisitor, bvars));
		
		// FIXME: Add support declarations with the action		
		
		return nodeDeps;
	}

	public NodeNameList caseAStartDeadlineAction(AStartDeadlineAction node, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		return nodeDeps;
	}
	
	public NodeNameList caseATimedInterruptAction(ATimedInterruptAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getTimeExpression().apply(thmDepVisitor, bvars));

		return nodeDeps;
	}

	public NodeNameList caseATimeoutAction(ATimeoutAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();

		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getTimeoutExpression().apply(thmDepVisitor, bvars));
				
		return nodeDeps;
	}
	
	public NodeNameList caseAUntimedTimeoutAction(AUntimedTimeoutAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getLeft().apply(thmDepVisitor, bvars));
		nodeDeps.addAll(a.getRight().apply(thmDepVisitor, bvars));
				
		return nodeDeps;
	}

	public NodeNameList caseAWaitAction(AWaitAction a, NodeNameList bvars)
			throws AnalysisException {
		NodeNameList nodeDeps = new NodeNameList();
		
		nodeDeps.addAll(a.getExpression().apply(thmDepVisitor, bvars));
		
		return nodeDeps;
	}

	
	
	@Override
	public NodeNameList createNewReturnValue(INode arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeNameList createNewReturnValue(Object arg0, NodeNameList arg1)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return null;
	}
}
