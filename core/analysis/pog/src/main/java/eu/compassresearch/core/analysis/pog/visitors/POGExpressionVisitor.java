package eu.compassresearch.core.analysis.pog.visitors;
 
// Overture libraries 
import java.lang.reflect.Method;

import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.ADivNumericBinaryExp;
import org.overture.ast.expressions.ADivideNumericBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ARealLiteralExp;
import org.overture.ast.expressions.PExp; 
import org.overture.ast.expressions.SNumericBinaryExp;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.analysis.pog.obligations.CMLNonZeroObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
//import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
//import eu.compassresearch.core.analysis.pog.obligations.CMLPOContext;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligation;
//import org.overturetool.vdmj.lex.LexIdentifierToken;
//import org.overturetool.vdmj.lex.LexNameToken;



import org.overture.ast.node.INode;


public class POGExpressionVisitor extends
QuestionAnswerCMLAdaptor<CMLPOContextStack, CMLProofObligationList>
  {
        
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private QuestionAnswerAdaptor<CMLPOContextStack, CMLProofObligationList> parent;
    
    public POGExpressionVisitor(ProofObligationGenerator parentVisitor)
    {
        parent = parentVisitor;
    }

	@Override
	public CMLProofObligationList defaultPExp(PExp node, CMLPOContextStack question)
	{
		System.out.println("Reached POGExpressionVisitor - defaultPEx");
		System.out.println("Expression: " + node.toString());
		return new CMLProofObligationList();
	}
	

	private <T extends PExp> CMLProofObligationList handleDivideNumericBinaryExp(
			T node, CMLPOContextStack question)
	{
		CMLProofObligationList obligations = new CMLProofObligationList();
		PExp[] leftRight = getLeftRight(node);
		PExp rExp = leftRight[RIGHT];
			
		try {
			obligations.addAll(defaultSNumericBinaryExp((SNumericBinaryExp)node,question));
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!(rExp instanceof AIntLiteralExp)
				&& !(rExp instanceof ARealLiteralExp))
		{
			obligations.add(new CMLNonZeroObligation(node.getLocation(), rExp, question));
		}

		return obligations;
	}

	final static int LEFT = 0;
	final static int RIGHT = 1;

	private <T> PExp[] getLeftRight(T node)
	{
		PExp[] res = new PExp[2];
		try
		{
			Class<?> clz = node.getClass();
			Method getLeft = clz.getMethod("getLeft", new Class<?>[] {});
			Method getRight = clz.getMethod("getRight", new Class<?>[] {});
			res[LEFT] = (PExp) getLeft.invoke(node, new Object[0]);
			res[RIGHT] = (PExp) getRight.invoke(node, new Object[0]);

		} catch (Exception k)
		{
			throw new RuntimeException(k);
		}
		return res;
	}
	

	@Override
	// RWL see [1] pg.
	public CMLProofObligationList caseADivNumericBinaryExp(
			ADivNumericBinaryExp node, CMLPOContextStack question)
	{
		return handleDivideNumericBinaryExp(node, question);
	}

	@Override
	public CMLProofObligationList caseADivideNumericBinaryExp(
			ADivideNumericBinaryExp node, CMLPOContextStack question)
	{
		return handleDivideNumericBinaryExp(node, question);
	}
	
	
}
