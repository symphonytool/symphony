package eu.compassresearch.core.analysis.pog.visitors;
 
// Overture libraries 
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp; 

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
//import eu.compassresearch.ast.types.PType;
import eu.compassresearch.core.analysis.pog.obligations.MapApplyObligation;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.POContext;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligation;
//import org.overturetool.vdmj.lex.LexIdentifierToken;
//import org.overturetool.vdmj.lex.LexNameToken;



import org.overture.ast.node.INode;


public class POGExpressionVisitor extends
	QuestionAnswerAdaptor<POContextStack, ProofObligationList>
  {
        
    // A parent checker may actually not be necessary on this
    @SuppressWarnings("unused")
    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> parent;
    
    public POGExpressionVisitor(ProofObligationGenerator parentVisitor)
    {
        parent = parentVisitor;
    }

	@Override
	public ProofObligationList defaultPExp(PExp node, POContextStack question)
	{
		System.out.println("Reached POGExpressionVisitor - defaultPEx ");
		return new ProofObligationList();
	}
	
}
