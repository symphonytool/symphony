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

    /**
     * Constructor - simply initialise parent POG
     * @param parent
     */
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
    /**
	  * Default case - just print to screen
	  */
    @Override
    public ProofObligationList defaultPProcess(PProcess node,
	    POContextStack question) throws AnalysisException {
    	System.out.println("PProcess: " + node.toString());
		return new ProofObligationList();
    }
    
    /**
	  * State process - split process into definition paragraphs and process 
	  */
    @Override
    public CMLProofObligationList caseAStateProcess(AStateProcess node,POContextStack question) throws AnalysisException{

    	CMLProofObligationList pol = new CMLProofObligationList();

    	//Print the separate parts to screen
    	System.out.println("A StateProcess: " + node.toString());
    	System.out.println("A StateProcess process defintions: " + node.getProcessDefinition());
    	System.out.println("A StateProcess defintion paragraphs: " + node.getDefinitionParagraphs());
    	System.out.println("A StateProcess action: " + node.getAction());
    	
    	for (SParagraphDefinition def : node.getDefinitionParagraphs()) {
    		System.out.println(def.toString());
    		
    		pol.addAll(def.apply(parentPOG, question));
    	}
    	
		return pol;
    }
}
