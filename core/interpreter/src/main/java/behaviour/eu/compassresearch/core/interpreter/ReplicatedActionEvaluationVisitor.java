package eu.compassresearch.core.interpreter;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.ValueSet;

import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;

/**
 * Visitor that handles all the replicated action logic.
 * @author akm
 *
 */
public class ReplicatedActionEvaluationVisitor extends AbstractEvaluationVisitor {

	public ReplicatedActionEvaluationVisitor(
			AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner,
			VisitorAccess visitorAccess) {
		super(parentVisitor, owner, visitorAccess);
	}
	/**
	 * Replicated interleaving
	 * Syntax 	: '|||' , replication declarations , @ , action
	 * 
	 * Example 	: |||i:e @ A(i)
	 * 
	 * Execute all the actions A(i) in parallel without sync
	 * 
	 */
	@Override
	public Pair<INode, Context> caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, Context question)
			throws AnalysisException {
		
		List<ValueSet> replicationDecls = new LinkedList<ValueSet>(); 
		
		for(PSingleDeclaration singleDecl :  node.getReplicationDeclaration())
			replicationDecls.add(singleDecl.apply(cmlExpressionVisitor,question).setValue(question));
		
		//node.getReplicatedAction()
		
		throw new RuntimeException("test");
	}

}
