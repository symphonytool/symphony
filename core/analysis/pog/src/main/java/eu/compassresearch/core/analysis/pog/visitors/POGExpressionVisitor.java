package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogParamExpVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.expressions.PCMLExp;

public class POGExpressionVisitor extends
	QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

    private static final long serialVersionUID = -8208272656463333796L;
    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> rootVisitor;
    final private PogParamExpVisitor<POContextStack, ProofObligationList> overtureVisitor;

    public POGExpressionVisitor(
	    QuestionAnswerAdaptor<POContextStack, ProofObligationList> parentVisitor) {
	this.rootVisitor = parentVisitor;
	this.overtureVisitor = new PogParamExpVisitor<POContextStack, ProofObligationList>(
		this, this);
    }

    // handle CML expressions
// they are the PCMLExps or wheatever
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public ProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    POContextStack question) throws AnalysisException {
	return new ProofObligationList();
    }

    
    
    //TODO handle PCML expressions
    @Override
	public ProofObligationList defaultPCMLExp(PCMLExp node,
			POContextStack question) throws AnalysisException {
		return new ProofObligationList();
	}

	// Call Overture for the other expressions
    @Override
    public ProofObligationList defaultPExp(PExp node, POContextStack question)
	    throws AnalysisException {
	ProofObligationList pol = new ProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }

    // Call the main pog when it's not an expression
    @Override
    public ProofObligationList defaultINode(INode node, POContextStack question)
	    throws AnalysisException {
	ProofObligationList pol = new ProofObligationList();
	pol.addAll(node.apply(rootVisitor, question));
	return pol;
    }

}