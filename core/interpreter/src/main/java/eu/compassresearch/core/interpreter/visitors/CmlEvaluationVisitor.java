package eu.compassresearch.core.interpreter.visitors;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.util.Pair;

/**
 * This class has the responsibility of performing transitions of a CmlBehaviour. It is structured as a tree of visitors each taken a specific part of the CML AST and this is the 
 * root of this tree.
 * @author akm
 *
 */
public class CmlEvaluationVisitor extends AbstractEvaluationVisitor {

	QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> actionEvalVisitor;
	QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> processEvalVisitor;
	
	public CmlEvaluationVisitor(QuestionAnswerCMLAdaptor<Context, Pair<INode, Context>> parentVisitor, CmlBehaviour owner, VisitorAccess visitorAccess)
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
