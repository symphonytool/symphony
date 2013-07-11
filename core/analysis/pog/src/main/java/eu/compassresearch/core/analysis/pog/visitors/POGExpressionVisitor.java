package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.IPOContextStack;
import org.overture.pog.PogParamExpVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

public class POGExpressionVisitor extends
	QuestionAnswerCMLAdaptor<IPOContextStack, CMLProofObligationList> {

    private static final long serialVersionUID = -8208272656463333796L;
    final private QuestionAnswerAdaptor<IPOContextStack, CMLProofObligationList> rootVisitor;
    final private PogParamExpVisitor<IPOContextStack, CMLProofObligationList> overtureVisitor;

    public POGExpressionVisitor(
	    QuestionAnswerAdaptor<IPOContextStack, CMLProofObligationList> parentVisitor) {
	this.rootVisitor = parentVisitor;
	this.overtureVisitor = new PogParamExpVisitor<IPOContextStack, CMLProofObligationList>(
		this, this);
    }

    // handle CML expressions
// they are the PCMLExps or wheatever
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public CMLProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    IPOContextStack question) throws AnalysisException {
	return new CMLProofObligationList();
    }

    
    
    
    
    @Override
	public CMLProofObligationList caseABracketedExp(ABracketedExp node,
			IPOContextStack question) throws AnalysisException
	{
    	CMLProofObligationList pol = new CMLProofObligationList();
    	PExp exp = node.getExpression();
    	pol.addAll(exp.apply(this,question));
    	return pol;

	}

	//TODO handle PCML expressions
    @Override
	public CMLProofObligationList defaultPCMLExp(PCMLExp node,
			IPOContextStack question) throws AnalysisException {
		return new CMLProofObligationList();
	}

	// Call Overture for the other expressions
    @Override
    public CMLProofObligationList defaultPExp(PExp node, IPOContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }

    // Call the main pog when it's not an expression
    @Override
    public CMLProofObligationList defaultINode(INode node, IPOContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(rootVisitor, question));
	return pol;
    }

}