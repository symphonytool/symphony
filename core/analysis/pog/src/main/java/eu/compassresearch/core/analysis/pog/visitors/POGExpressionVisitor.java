package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.visitors.PogParamExpVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;

public class POGExpressionVisitor extends
	QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList> {

    final private QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> rootVisitor;
    final private PogParamExpVisitor<IPOContextStack, CmlProofObligationList> overtureVisitor;

    
    public POGExpressionVisitor(
	    QuestionAnswerAdaptor<IPOContextStack, CmlProofObligationList> parentVisitor) {
	this.rootVisitor = parentVisitor;
	this.overtureVisitor = new PogParamExpVisitor<IPOContextStack, CmlProofObligationList>(
		this, this, new CmlPogAssistantFactory());
    }

    // handle CML expressions
// they are the PCMLExps or wheatever
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public CmlProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    IPOContextStack question) throws AnalysisException {
	return new CmlProofObligationList();
    }

    
    
    
    
    @Override
	public CmlProofObligationList caseABracketedExp(ABracketedExp node,
			IPOContextStack question) throws AnalysisException
	{
    	CmlProofObligationList pol = new CmlProofObligationList();
    	PExp exp = node.getExpression();
    	pol.addAll(exp.apply(this,question));
    	return pol;

	}

	//TODO handle PCML expressions
    @Override
	public CmlProofObligationList defaultPCMLExp(PCMLExp node,
			IPOContextStack question) throws AnalysisException {
		return new CmlProofObligationList();
	}

	// Call Overture for the other expressions
    @Override
    public CmlProofObligationList defaultPExp(PExp node, IPOContextStack question)
	    throws AnalysisException {
	CmlProofObligationList pol = new CmlProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }

    // Call the main pog when it's not an expression
    @Override
    public CmlProofObligationList defaultINode(INode node, IPOContextStack question)
	    throws AnalysisException {
	CmlProofObligationList pol = new CmlProofObligationList();
	pol.addAll(node.apply(rootVisitor, question));
	return pol;
    }

	@Override
	public CmlProofObligationList createNewReturnValue(INode node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmlProofObligationList createNewReturnValue(Object node,
			IPOContextStack question)
	{
		// TODO Auto-generated method stub
		return null;
	}

}