package eu.compassresearch.core.analysis.pog.visitors;



import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;


@SuppressWarnings("serial")
public class POGStatementVisitor extends QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList>

{
    
    private ProofObligationGenerator parent;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parent = parent;
    }
      

      
}