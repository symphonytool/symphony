package eu.compassresearch.core.analysis.pog.visitors;


import org.overture.ast.analysis.AnalysisException;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;

@SuppressWarnings("serial")
public class POGProcessVisitor extends QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList>
{
    private ProofObligationGenerator parentPOG;
    
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
    @Override
    public ProofObligationList defaultPProcess(PProcess node,
	    POContextStack question) throws AnalysisException {
    	System.out.println("PProcess: " + node.toString());
		return new ProofObligationList();
    }

}
