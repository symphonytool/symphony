package eu.compassresearch.core.typechecker.version2;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.AFieldExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.AFieldStateDesignator;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.statements.AUnresolvedStateDesignator;
import org.overture.ast.statements.PStateDesignator;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.visitor.TypeCheckVisitor;

import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;

public class CmlVdmTypeCheckVisitor extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TypeCheckVisitor vdmVisitor = new TypeCheckVisitor()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PType defaultINode(INode node, TypeCheckInfo question)
				throws AnalysisException
		{
			return node.apply(CmlVdmTypeCheckVisitor.this, question);
		};
	};

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType defaultINode(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmVisitor, question);
	}

	@Override
	public PType caseAActionStm(AActionStm node, TypeCheckInfo question)
			throws AnalysisException
	{
		PType type = node.getAction().apply(this, question);
		node.setType(type);
		return type;
	}
	
	@Override
	public PType caseASkipAction(ASkipAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseASkipAction(node, question);
	}
	
	@Override
	public PType caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node, TypeCheckInfo question)
			throws AnalysisException
	{
		PExp exp = node.getPath();
		
		PStateDesignator designator = createStateDesignator(exp);
		
		node.parent().replaceChild(node, designator);
		return designator.apply(this,question);
		
		
	}
	
	private PStateDesignator createStateDesignator(PExp exp)
	{
		if(exp instanceof AVariableExp)
		{
			AVariableExp varExp = (AVariableExp) exp;
			AIdentifierStateDesignator designator = AstFactory.newAIdentifierStateDesignator(varExp.getName());
			return designator;
		}else if(exp instanceof AFieldExp)
		{
			AFieldExp field = (AFieldExp) exp;
			
			PStateDesignator object = createStateDesignator( field.getObject());
			AFieldStateDesignator designator = AstFactory.newAFieldStateDesignator(object , field.getField());
			return designator;
		}
		
		throw new RuntimeException("hit case for unresolved state designator: "+ exp);
	}

	@Override
	public PType caseAUnresolvedPathExp(AUnresolvedPathExp node,
			TypeCheckInfo question) throws AnalysisException
	{

		System.err.println("Unresolved path not resolved before TC");
		return null;
		// if(node.getIdentifiers().isEmpty())
		// {
		// assert false: "Oops missing identifiers";
		// return null;
		// }
		//
		// if(node.parent() instanceof PStm || node.parent() instanceof PAction)
		// {
		// //state designatores
		// return null;
		// }else
		// {
		// PExp exp =null;
		// Iterator<ILexIdentifierToken> itr = node.getIdentifiers().iterator();
		//
		// exp = AstFactory.newAVariableExp(itr.next().getClassName());
		//
		// while(itr.hasNext())
		// {
		// exp = AstFactory.newAFieldExp(exp, itr.next());
		// }
		// node.parent().replaceChild(node, exp);
		// return exp.apply(this, question);
		// }
		// PExp obj = AstFactory.newAVariableExp(node.getIdentifiers().get(0).getClassName());
		// PExp fieldExp = AstFactory.newAFieldExp(obj, node.getIdentifiers().get(1));
		//
		// node.parent().replaceChild(node, fieldExp);
		// return fieldExp.apply(this, question);
	}

	@Override
	public PType caseABracketedExp(ABracketedExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		PType type = node.getExpression().apply(this, question);
		node.setType(type);
		return type;
	}

}
