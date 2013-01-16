package eu.compassresearch.core.analysis.pog.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.statements.PStateDesignator;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.SParagraphDefinition;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLWhileLoopObligation;

@SuppressWarnings("serial")
public class POGActionVisitor  extends 
QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

    private ProofObligationGenerator parentPOG;

    /**
     * Constructor - simply initialise parent POG
     * @param parent
     */
    public POGActionVisitor(ProofObligationGenerator parent)
	{
        this.parentPOG = parent;
    }
      

    /**
     * Block Statement. Currently, get the action and handle
     */
    @Override
    public CMLProofObligationList caseABlockStatementAction(ABlockStatementAction node,
    		POContextStack question) throws AnalysisException{
    	System.out.println("A ABlockStatementAction: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();
    	
    	pol.addAll(node.getAction().apply(parentPOG, question));
		return pol;
    }

    /**
     * Single assignment. Possibly naive - just handle the identifiers and expressions
     * May need more detail on identifiers?
     */
    @Override
    public CMLProofObligationList caseASingleGeneralAssignmentStatementAction(
    		ASingleGeneralAssignmentStatementAction node,POContextStack question) 
    		throws AnalysisException{
    	System.out.println("A ASingleGeneralAssignmentStatementAction: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();
    	
    	PStateDesignator id = node.getStateDesignator();
    	PExp expr = node.getExpression();
    			
    	pol.addAll(id.apply(parentPOG, question));	
    	pol.addAll(expr.apply(parentPOG, question));
		return pol;
    }
    
    /**
     * Composition action. Process left part, then right.
     */
    @Override
    public CMLProofObligationList caseASequentialCompositionAction(
    		ASequentialCompositionAction node,POContextStack question) 
    		throws AnalysisException{
    	System.out.println("A ASequentialCompositionAction: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();
    			
    	pol.addAll(node.getLeft().apply(parentPOG, question));	
    	pol.addAll(node.getRight().apply(parentPOG, question));
		return pol;
    }
    
    /**
     * If statement action. process expression, then 'then' part. Optionally
     * process 'else' and 'elseif'
     */
    @Override
	public CMLProofObligationList caseAIfStatementAction(
			AIfStatementAction node, POContextStack question)
    		throws AnalysisException{
    	System.out.println("A caseAIfStatementAction: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();

    	pol.addAll(node.getIfExp().apply(parentPOG, question));
		pol.addAll(node.getThenStm().apply(parentPOG, question));

		for (AElseIfStatementAction stmt : node.getElseIf())
		{
			pol.addAll(stmt.apply(this, question));
		}

		if (node.getElseStm() != null)
		{
			pol.addAll(node.getElseStm().apply(this, question));
		}

		return pol;
	}
    
	@Override
	public ProofObligationList caseAElseIfStatementAction(AElseIfStatementAction node,
			POContextStack question) throws AnalysisException
	{
    	System.out.println("A caseAElseIfStatementAction: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

    	pol.addAll(node.getElseIf().apply(parentPOG, question));
    	pol.addAll(node.getThenStm().apply(parentPOG, question));

		return pol;
	}
    
	@Override
	public ProofObligationList caseAWhileStatementAction(AWhileStatementAction node,
			POContextStack question) throws AnalysisException
	{
    	System.out.println("A caseAWhileStatementAction: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		pol.add(new CMLWhileLoopObligation(node, question));
		pol.addAll(node.getCondition().apply(parentPOG, question));
		pol.addAll(node.getAction().apply(this, question));

		return pol;
	}
	
    // Default action
    @Override
    public ProofObligationList defaultPAction(PAction node, POContextStack question)
	    throws AnalysisException 
	{
    	CMLProofObligationList pol = new CMLProofObligationList();

    	System.out.println("----------***----------");
		System.out.println("defaultPAction");
		System.out.println(node.toString());
		System.out.println("----------***----------");
		
		return pol;
    }
    
    // Call the main pog when it's not a statement
    @Override
    public ProofObligationList defaultINode(INode node, POContextStack question)
	    throws AnalysisException {
	CMLProofObligationList pol = new CMLProofObligationList();
	pol.addAll(node.apply(parentPOG, question));
	return pol;
    }
   
}
