package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

public class ReplicatedActionEvaluationVisitor extends CommonEvaluationVisitor {

	public ReplicatedActionEvaluationVisitor(
			AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner,
			VisitorAccess visitorAccess) {
		super(parentVisitor, owner, visitorAccess);
	}
	
	@Override
	public Pair<INode, Context> caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, Context question)
			throws AnalysisException {
		
		throw new RuntimeException("test");
	}

}
