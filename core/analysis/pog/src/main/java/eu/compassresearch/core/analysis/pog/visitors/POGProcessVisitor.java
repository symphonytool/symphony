package eu.compassresearch.core.analysis.pog.visitors;


import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

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
    
    @Override
    public CMLProofObligationList caseAStateProcess(AStateProcess node,POContextStack question) throws AnalysisException{
    	System.out.println("A StateProcess: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();
    	
    	LinkedList<SParagraphDefinition> pdef = node.getDefinitionParagraphs();
    	for (SParagraphDefinition def : pdef) {
    		System.out.println(def.toString());
    		
    		pol.addAll(def.apply(parentPOG, question));
    	}
    	
    	
    	System.out.println(node.getProcessDefinition());
    	System.out.println(pdef);
    	System.out.println(node.getAction());
		return pol;
    }

}
