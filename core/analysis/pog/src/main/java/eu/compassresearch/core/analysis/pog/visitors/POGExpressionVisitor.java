package eu.compassresearch.core.analysis.pog.visitors;

// Overture libraries 
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogParamExpVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

// Still using some overture code. We will have to make changes on the overture side before we are able to use inheritance.
public class POGExpressionVisitor
	extends
	PogParamExpVisitor<CMLPOContextStack, CMLProofObligationList> {



    public POGExpressionVisitor(
	    QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> parentVisitor) {
	super (parentVisitor);
    }

    private static final long serialVersionUID = -8208272656463333796L;




}