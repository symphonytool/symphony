package eu.compassresearch.core.analysis.pog.visitors;

import org.overture.ast.analysis.AnalysisException;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;

@SuppressWarnings("serial")
public class POGProcessVisitor extends
		QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList>
{
    private ProofObligationGenerator parentPOG;
    
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
	@Override
    public ProofObligationList defaultPProcess(PProcess node, POContextStack question)
         throws AnalysisException
    {
		System.out.println("Reached POGProcessVisitor - defaultPProcess ");
		return new ProofObligationList();
    }    
    
}
