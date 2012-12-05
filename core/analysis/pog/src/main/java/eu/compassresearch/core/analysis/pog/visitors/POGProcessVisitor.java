package eu.compassresearch.core.analysis.pog.visitors;


import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;

@SuppressWarnings("serial")
public class POGProcessVisitor extends QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList>
{
    private ProofObligationGenerator parentPOG;
    
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
 // This one will probably not be touched for some time.
    
}
