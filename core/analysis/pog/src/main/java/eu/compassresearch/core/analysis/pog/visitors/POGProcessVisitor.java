package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.pog.obligation.POContextStack;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@SuppressWarnings("serial")
public class POGProcessVisitor extends QuestionAnswerCMLAdaptor<POContextStack, CMLProofObligationList>
{
    private ProofObligationGenerator parentPOG;
    
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
 // This one will probably not be touched for some time.
    
}
