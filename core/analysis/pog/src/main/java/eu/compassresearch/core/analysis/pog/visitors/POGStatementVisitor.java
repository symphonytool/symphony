package eu.compassresearch.core.analysis.pog.visitors;



import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.pog.IPOContextStack;
import org.overture.pog.POContextStack;
import org.overture.pog.PogParamStmVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@SuppressWarnings("serial")
public class POGStatementVisitor extends 
QuestionAnswerCMLAdaptor<IPOContextStack, CMLProofObligationList> {
    
    
    private ProofObligationGenerator parentVisitor;
    final private PogParamStmVisitor<POContextStack, CMLProofObligationList> overtureVisitor;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parentVisitor = parent;
        this.overtureVisitor = new PogParamStmVisitor<POContextStack, CMLProofObligationList>(
		this, this);
    }
      
    // Call Overture for the other statements
    @Override
    public CMLProofObligationList defaultPStm(PStm node, IPOContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }


    // Call the main pog when it's not a statement
    @Override
    public CMLProofObligationList defaultINode(INode node, IPOContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(parentVisitor, question));
	return pol;
    }
   
    
      
}