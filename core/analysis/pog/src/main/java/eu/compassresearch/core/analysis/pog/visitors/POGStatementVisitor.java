package eu.compassresearch.core.analysis.pog.visitors;



import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;


@SuppressWarnings("serial")
public class POGStatementVisitor extends QuestionAnswerCMLAdaptor<CMLPOContextStack, CMLProofObligationList>

{
    
    private ProofObligationGenerator parent;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parent = parent;
    }
      

      
}