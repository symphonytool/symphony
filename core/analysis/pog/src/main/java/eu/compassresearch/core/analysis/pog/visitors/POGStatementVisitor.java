package eu.compassresearch.core.analysis.pog.visitors;



import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStm;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.pub.IPOContextStack;
import org.overture.pog.visitors.PogParamStmVisitor;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;

@SuppressWarnings("serial")
public class POGStatementVisitor extends 
QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList> {
    
    
    private ProofObligationGenerator parentVisitor;
    final private PogParamStmVisitor<POContextStack, CmlProofObligationList> overtureVisitor;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parentVisitor = parent;
        this.overtureVisitor = new PogParamStmVisitor<POContextStack, CmlProofObligationList>(
		this, this, new CmlPogAssistantFactory());
    }
      
    // Call Overture for the other statements
    @Override
    public CmlProofObligationList defaultPStm(PStm node, IPOContextStack question)
	    throws AnalysisException {
	CmlProofObligationList pol = new CmlProofObligationList();
	pol.addAll(node.apply(overtureVisitor, question));
	return pol;
    }


    // Call the main pog when it's not a statement
    @Override
    public CmlProofObligationList defaultINode(INode node, IPOContextStack question)
	    throws AnalysisException {
	CmlProofObligationList pol = new CmlProofObligationList();
	pol.addAll(node.apply(parentVisitor, question));
	return pol;
    }
   
    
      
}