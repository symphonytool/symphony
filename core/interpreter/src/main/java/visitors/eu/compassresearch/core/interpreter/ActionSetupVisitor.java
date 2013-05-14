package eu.compassresearch.core.interpreter;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.cml.core.CmlBehaviour;

class ActionSetupVisitor extends AbstractSetupVisitor {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner,visitorAccess);
	}
	
	private void caseASequentialComposition(INode node, INode leftNode, Context question) throws AnalysisException
	{
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(leftNode, question,new LexNameToken("",owner.name().getSimpleName() + ";",owner.name().getLocation()), owner));
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
	
	@Override
	public void caseAHidingAction(AHidingAction node, Context question)
			throws AnalysisException {

		//We setup the child node for the hiding operator
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(node.getLeft(),question,owner));
	}
	
	@Override
	public void caseATimeoutAction(ATimeoutAction node, Context question)
			throws AnalysisException {

		//We setup the child nodes 
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(node.getLeft(),question,owner));
		//setRightChild(VanillaInterpreterFactory.newCmlBehaviour(node.getRight(),question,owner));
	}
	
}
