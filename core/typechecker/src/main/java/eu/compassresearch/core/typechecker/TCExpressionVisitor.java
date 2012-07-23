package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.node.INode;
import org.overture.typecheck.visitors.TypeCheckVisitor;
import org.overture.typecheck.visitors.TypeCheckerExpVisitor;

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
		org.overture.typecheck.TypeCheckInfo tci = new org.overture.typecheck.TypeCheckInfo();
		TypeCheckerExpVisitor ovtExpVisitor = new TypeCheckerExpVisitor(new TypeCheckVisitor() {

			@Override
			public org.overture.ast.types.PType defaultINode(INode node,
					org.overture.typecheck.TypeCheckInfo question) {
				throw new RuntimeException("Sorry mate no can do!");
			}
			
		});
		
		return null;
	}

	
	
	
}
