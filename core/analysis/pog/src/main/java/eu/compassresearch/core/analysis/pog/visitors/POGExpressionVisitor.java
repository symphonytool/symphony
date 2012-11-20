package eu.compassresearch.core.analysis.pog.visitors;
 
// Overture libraries 
import java.lang.reflect.Method;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.ADistIntersectUnaryExp;
import org.overture.ast.expressions.ADivNumericBinaryExp;
import org.overture.ast.expressions.AIntLiteralExp;
import org.overture.ast.expressions.ARealLiteralExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.expressions.SNumericBinaryExp;
import org.overture.pog.obligation.NonEmptySetObligation;
import org.overture.pog.obligation.NonZeroObligation;
import org.overture.pog.obligation.POContextStack;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;


public class POGExpressionVisitor extends
	QuestionAnswerCMLAdaptor<POContextStack, CMLProofObligationList>
  {
        
    /**
     * 
     */
    private static final long serialVersionUID = -8208272656463333796L;
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private QuestionAnswerAdaptor<POContextStack, CMLProofObligationList> parent;
    
    public POGExpressionVisitor(ProofObligationGenerator parentVisitor)
    {
        parent = parentVisitor;
    }

	@Override
	public CMLProofObligationList defaultPExp(PExp node, POContextStack question)
	{
		System.out.println("Reached POGExpressionVisitor - defaultPEx ");
		return new CMLProofObligationList();
	}

	@Override
	public CMLProofObligationList caseADistIntersectUnaryExp(
		ADistIntersectUnaryExp node, POContextStack question)
		throws AnalysisException {
		CMLProofObligationList obligations = node.getExp().apply(this, question);
		obligations.add(new NonEmptySetObligation(node.getExp(), question));
		return obligations;
	}
	
	@Override
	public CMLProofObligationList caseADivNumericBinaryExp(
			ADivNumericBinaryExp node, POContextStack question) throws AnalysisException
	{
		return handleDivideNumericBinaryExp(node, question);
	}
	

	private <T extends PExp> CMLProofObligationList handleDivideNumericBinaryExp(
			T node, POContextStack question) throws AnalysisException
	{
		CMLProofObligationList obligations = new CMLProofObligationList();
		PExp[] leftRight = getLeftRight(node);
		PExp rExp = leftRight[RIGHT];
			
		obligations.addAll(defaultSNumericBinaryExp((SNumericBinaryExp)node,question));
		
		if (!(rExp instanceof AIntLiteralExp)
				&& !(rExp instanceof ARealLiteralExp))
		{
			obligations.add(new NonZeroObligation(node.getLocation(), rExp, question));
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
	
}
