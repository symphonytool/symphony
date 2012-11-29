package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import java.lang.reflect.Method;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.AInSetBinaryExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogExpVisitor;
import org.overture.pog.visitor.PogParamExpVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

// Still using some overture code. We will have to make changes on the overture side before we are able to use inheritance.
public class POGExpressionVisitor extends
	QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> rootVisitor;
    final private PogParamExpVisitor<POContextStack, ProofObligationList> overtureVisitor;

    public POGExpressionVisitor(
	    QuestionAnswerAdaptor<POContextStack, ProofObligationList> parentVisitor) {
	this.rootVisitor = parentVisitor;
	this.overtureVisitor = new PogParamExpVisitor<POContextStack, ProofObligationList>(this,this);
    }

    // publicPo

    @Override
    public ProofObligationList defaultINode(INode node, POContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(rootVisitor, question));
	return pol;
    }
//    
    

    @Override
    public ProofObligationList defaultPExp(PExp node, POContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }
    
    



    @Override
    public ProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    POContextStack question) throws AnalysisException {
	// TODO Auto-generated method stub
	return new CMLProofObligationList();
    }





    //    @Override
//    public ProofObligationList caseAInSetBinaryExp(AInSetBinaryExp node,
//	    POContextStack question) throws AnalysisException {
//	return handleBinaryExpression(node, question);
//    }
//    
//    private <T> ProofObligationList handleBinaryExpression(T node,
//	    POContextStack question) throws AnalysisException {
//
//	if (node == null)
//	    return new ProofObligationList();
//
//	PExp[] leftRight = getLeftRight(node);
//	PExp left = leftRight[LEFT];
//	PExp right = leftRight[RIGHT];
//
//	ProofObligationList obligations = new ProofObligationList();
//	obligations.addAll(left.apply(rootVisitor, question));
//	obligations.addAll(right.apply(rootVisitor, question));
//	return obligations;
//    }
//    
//    final static int LEFT = 0;
//    final static int RIGHT = 1;
//
//    private <T> PExp[] getLeftRight(T node) {
//	PExp[] res = new PExp[2];
//	try {
//	    Class<?> clz = node.getClass();
//	    Method getLeft = clz.getMethod("getLeft", new Class<?>[] {});
//	    Method getRight = clz.getMethod("getRight", new Class<?>[] {});
//	    res[LEFT] = (PExp) getLeft.invoke(node, new Object[0]);
//	    res[RIGHT] = (PExp) getRight.invoke(node, new Object[0]);
//
//	} catch (Exception k) {
//	    throw new RuntimeException(k);
//	}
//	return res;
//    }
//    
    private static final long serialVersionUID = -8208272656463333796L;

    //
    // private static final long serialVersionUID = 7899640121529246521L;
    // final private QuestionAnswerAdaptor<POContextStack, ProofObligationList>
    // rootVisitor;
    //
    // public PogParamExpVisitor(PogVisitor pogVisitor) {
    // this.rootVisitor = pogVisitor;
    //
    // }

}