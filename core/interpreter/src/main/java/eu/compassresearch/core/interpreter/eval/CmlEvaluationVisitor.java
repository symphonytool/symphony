package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.util.Pair;

public class CmlEvaluationVisitor extends AbstractEvaluationVisitor {

	AbstractEvaluationVisitor actionEvalVisitor = new ActionEvaluationVisitor(this);
	AbstractEvaluationVisitor processEvalVisitor = new ProcessEvaluationVisitor(this);
	
	public CmlEvaluationVisitor()
	{
		super(null);
	}
	
	@Override
	public void init(ControlAccess controlAccess) {
		super.init(controlAccess);
		actionEvalVisitor.init(controlAccess);
		processEvalVisitor.init(controlAccess);
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
