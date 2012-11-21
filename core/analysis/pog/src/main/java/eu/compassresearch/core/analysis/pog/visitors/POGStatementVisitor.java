package eu.compassresearch.core.analysis.pog.visitors;


 
// Overture libraries 

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

// import eu.compassresearch.ast.actions.ABlockStatementAction;
// import eu.compassresearch.ast.actions.AReturnStatementAction;
// import eu.compassresearch.ast.actions.PAction;
// import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
// import eu.compassresearch.ast.expressions.PExp;
// import eu.compassresearch.ast.types.AStatementType;
// import eu.compassresearch.ast.types.PType;

@SuppressWarnings("serial")
public class POGStatementVisitor extends QuestionAnswerCMLAdaptor<CMLPOContextStack, CMLProofObligationList>

{
    
    private ProofObligationGenerator parent;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parent = parent;
    }
      
//    @Override
//    public ProofObligationList defaultPAction(PAction node, POContextStack question)
//        throws AnalysisException
//     {
//		System.out.println("Reached POGStatementVisitor - defaultPAction");
//		return new ProofObligationList();
//      }
      
      
    // FROM OVERTURE POG
   // @Override
	//public ProofObligationList defaultPStm(PStm node, POContextStack question)
    //    throws AnalysisException
    //  {
	//	System.out.println("Reached POGStatementVisitor - defaultPStm");
	//	return new ProofObligationList();
     // }
      
}