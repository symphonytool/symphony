package eu.compassresearch.core.analysis.pog.visitors;



import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogParamStmVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;

@SuppressWarnings("serial")
public class POGStatementVisitor extends 
QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {
    
    
    private ProofObligationGenerator parentVisitor;
    final private PogParamStmVisitor<POContextStack, ProofObligationList> overtureVisitor;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parentVisitor = parent;
        this.overtureVisitor = new PogParamStmVisitor<POContextStack, ProofObligationList>(
		this, this);
    }
      
    // Call Overture for the other statements
    @Override
    public ProofObligationList defaultPStm(PStm node, POContextStack question)
	    throws AnalysisException {
	ProofObligationList pol = new ProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }


    // Call the main pog when it's not a statement
    @Override
    public ProofObligationList defaultINode(INode node, POContextStack question)
	    throws AnalysisException {
	ProofObligationList pol = new ProofObligationList();
	pol.addAll(node.apply(parentVisitor, question));
	return pol;
    }
   
    
      
}