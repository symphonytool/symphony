package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.util.Pair;

public class CmlEvaluationVisitor extends AbstractEvaluationVisitor {

	AbstractEvaluationVisitor actionEvalVisitor;
	AbstractEvaluationVisitor processEvalVisitor;
	
	public CmlEvaluationVisitor(AbstractEvaluationVisitor parentVisitor, CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(parentVisitor,owner,visitorAccess);
		actionEvalVisitor = new ActionEvaluationVisitor(this,owner,visitorAccess);
		processEvalVisitor = new ProcessEvaluationVisitor(this,owner,visitorAccess);
	}
			
	@Override
	public Pair<INode,Context> defaultPAction(PAction node, Context question)
			throws AnalysisException {
		
		return node.apply(actionEvalVisitor,question);
	}
	
	@Override
	public Pair<INode,Context> defaultPProcess(PProcess node, Context question)
			throws AnalysisException {
		
		return node.apply(processEvalVisitor,question);
	}
	
}
