package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.Pair;

class CmlSetupVisitor extends AbstractSetupVisitor
{
	private AbstractSetupVisitor actionVisitor;
	private AbstractSetupVisitor processVisitor;

	public CmlSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner, visitorAccess);
		actionVisitor = new ActionSetupVisitor(owner, visitorAccess);
		processVisitor = new ProcessSetupVisitor(owner, visitorAccess);
	}
	
	@Override
	public Pair<INode, Context> defaultPAction(PAction node, Context question)
			throws AnalysisException
	{
		return node.apply(actionVisitor,question);
	}
	
	@Override
	public Pair<INode, Context> defaultPStm(PStm node, Context question)
			throws AnalysisException
	{
		return node.apply(actionVisitor,question);
	}
	
	@Override
	public Pair<INode, Context> defaultPProcess(PProcess node, Context question)
			throws AnalysisException
	{
		return node.apply(processVisitor,question);
	}

}
