package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class ActionSetupVisitor extends AbstractSetupVisitor {

	
	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner,visitorAccess);
	}
	
	private void caseASequentialComposition(INode node, INode leftNode, Context question)
	{
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(leftNode, question,new LexNameToken("",owner.name().getSimpleName() + ";",owner.name().location), owner));
	}
	
	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {
		
		caseASequentialComposition(node, node.getLeft(), question);
	}
	
	@Override
	public void caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException {
		
		caseASequentialComposition(node, node.getLeft(), question);
	}
	
}
