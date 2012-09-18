package eu.compassresearch.core.analysis.pog.visitors;

import eu.compassresearch.ast.analysis.QuestionAnswerAdaptor;
import eu.compassresearch.ast.analysis.AnalysisException;
import eu.compassresearch.ast.actions.PAction;
//import eu.compassresearch.ast.expressions.PStm;
import eu.compassresearch.core.analysis.pog.obligations.MapApplyObligation;
import eu.compassresearch.core.analysis.pog.obligations.POContextStack;
import eu.compassresearch.core.analysis.pog.obligations.POContext;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.ProofObligation;

// import eu.compassresearch.ast.actions.ABlockStatementAction;
// import eu.compassresearch.ast.actions.AReturnStatementAction;
// import eu.compassresearch.ast.actions.PAction;
// import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
// import eu.compassresearch.ast.expressions.PExp;
// import eu.compassresearch.ast.types.AStatementType;
// import eu.compassresearch.ast.types.PType;

@SuppressWarnings("serial")
public class POGStatementVisitor extends
		QuestionAnswerAdaptor<POContextStack, ProofObligationList>
{
    
    private ProofObligationGenerator parent;
    
    public POGStatementVisitor(ProofObligationGenerator parent)
	{
        this.parent = parent;
    }
      
    @Override
    public ProofObligationList defaultPAction(PAction node, POContextStack question)
        throws AnalysisException
      {
		System.out.println("Reached POGStatementVisitor - defaultPAction");
		return new ProofObligationList();
      }
      
      
    // FROM OVERTURE POG
   // @Override
	//public ProofObligationList defaultPStm(PStm node, POContextStack question)
    //    throws AnalysisException
    //  {
	//	System.out.println("Reached POGStatementVisitor - defaultPStm");
	//	return new ProofObligationList();
     // }
      
}