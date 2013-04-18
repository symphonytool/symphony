//package eu.compassresearch.core.interpreter;
//
//import org.overture.ast.analysis.AnalysisException;
//import org.overture.interpreter.runtime.Context;
//
//import eu.compassresearch.ast.actions.AReferenceAction;
//import eu.compassresearch.ast.actions.ASequentialCompositionAction;
//import eu.compassresearch.ast.actions.PAction;
//import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
//import eu.compassresearch.core.interpreter.cml.ConcreteCmlBehaviour;
//
//public class ActionFactoryVisitor extends QuestionAnswerCMLAdaptor<Context,CmlBehaviour> {
//
//	private CmlBehaviour parent;
//	
//	public ActionFactoryVisitor(CmlBehaviour parent)
//	{
//		this.parent = parent;
//	}
//	
//	@Override
//	public CmlBehaviour defaultPAction(PAction node, Context question)
//			throws AnalysisException {
//
//		
//		
//		return super.defaultPAction(node, question);
//	}
//	
//	@Override
//	public CmlBehaviour caseASequentialCompositionAction(
//			ASequentialCompositionAction node, Context question)
//			throws AnalysisException {
//		
//		CmlBehaviour left = node.getLeft().apply(this,question);
//		CmlBehaviour right = VanillaInterpreterFactory.newCmlBehaviour(node.getRight(), question, parent);
//		
//		return new ConcreteCmlBehaviour(node, question, name, parent, left, right);
//	}
//	
//	@Override
//	public CmlBehaviour caseAReferenceAction(AReferenceAction node,
//			Context question) throws AnalysisException {
//		
//		
//		return super.caseAReferenceAction(node, question);
//	}
//	
//}
