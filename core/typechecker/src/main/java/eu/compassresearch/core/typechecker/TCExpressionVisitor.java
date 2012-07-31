package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.node.INode;

import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.expressions.AExists1Exp;
import eu.compassresearch.ast.expressions.PExp;
import eu.compassresearch.ast.patterns.PBind;
import eu.compassresearch.ast.types.PType;



public class TCExpressionVisitor extends QuestionAnswerAdaptor<TypeCheckInfo, PType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	final private QuestionAnswerAdaptor<TypeCheckInfo, PType> parent;

	public TCExpressionVisitor(CmlTypeChecker parentChecker)
	{
		parent = parentChecker;
	}

	@Override
	public PType defaultPExp(PExp node, TypeCheckInfo question)
			throws AnalysisException {
		org.overture.typechecker.TypeCheckInfo tci = new org.overture.typechecker.TypeCheckInfo();
		org.overture.typechecker.visitor.TypeCheckerExpVisitor ovtExpVisitor = new org.overture.typechecker.visitor.TypeCheckerExpVisitor(new org.overture.typechecker.visitor.TypeCheckVisitor() {

			@Override
			public org.overture.ast.types.PType defaultINode(INode node,
					org.overture.typechecker.TypeCheckInfo question) {
				throw new RuntimeException("Sorry mate no can do!");
			}
			
		});
		
		return null;
	}

	
	
	
}
