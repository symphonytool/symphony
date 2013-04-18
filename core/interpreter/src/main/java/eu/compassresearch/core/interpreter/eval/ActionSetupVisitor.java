package eu.compassresearch.core.interpreter.eval;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.runtime.Context;

import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.analysis.QuestionCMLAdaptor;
import eu.compassresearch.core.interpreter.VanillaInterpreterFactory;
import eu.compassresearch.core.interpreter.cml.CmlBehaviour;

public class ActionSetupVisitor extends AbstractSetupVisitor {

	
	public ActionSetupVisitor(CmlBehaviour owner, VisitorAccess visitorAccess)
	{
		super(owner,visitorAccess);
	}
	
	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException {
		
		INode leftNode = node.getLeft();
		setLeftChild(VanillaInterpreterFactory.newCmlBehaviour(leftNode, question,new LexNameToken("",owner.name().getSimpleName() + ";",node.getLocation()), owner));
		
	}
	
}
