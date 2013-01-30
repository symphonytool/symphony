package eu.compassresearch.core.analysis.pog.visitors;

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
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.core.analysis.pog.obligations.CMLNonZeroTimeObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLWhileLoopObligation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AApplyExp;
import org.overture.ast.expressions.ATupleExp;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.SSeqType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;
import org.overture.typechecker.assistant.pattern.PPatternAssistantTC;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclarationInstantiatedAction;
import eu.compassresearch.ast.actions.ADeclareStatementAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AElseIfStatementAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AForIndexStatementAction;
import eu.compassresearch.ast.actions.AForSequenceStatementAction;
import eu.compassresearch.ast.actions.AForSetStatementAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.ANotYetSpecifiedStatementAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AMultipleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.AReturnStatementAction;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASingleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.ASpecificationStatementAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ASubclassResponsibilityAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PCommunicationParameter;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AExplicitCmlOperationDefinition;
import eu.compassresearch.ast.definitions.SCmlOperationDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChansetType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.AVarsetExpressionType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;

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
    	
    	PExp id = node.getStateDesignator();
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
	
	@Override
	public ProofObligationList caseATimedInterruptAction(ATimedInterruptAction node,
			POContextStack question) throws AnalysisException
	{
    	System.out.println("A ATimedInterruptAction: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PAction left = node.getLeft();
		PAction right = node.getRight();
		PExp timeExp = node.getTimeExpression();

		//Send left-hand side
		pol.addAll(left.apply(this, question));
		//check for Non-Zero time obligation and dispatch exp for POG checking
		pol.add(new CMLNonZeroTimeObligation(timeExp, question));
		pol.addAll(timeExp.apply(this, question));
		//Send right-hand side
		pol.addAll(right.apply(this, question));

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

    


    	@Override
    	public ProofObligationList caseAValParametrisation(AValParametrisation node,
    			POContextStack question) throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		List<PDefinition> defs = new LinkedList<PDefinition>();

    		ATypeSingleDeclaration decl = node.getDeclaration();
    		for(LexIdentifierToken id : decl.getIdentifiers())
    		{
    			
    		}

        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAResParametrisation(AResParametrisation node,
    			POContextStack question) throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		ATypeSingleDeclaration decl = node.getDeclaration();
    		List<PDefinition> defs = new LinkedList<PDefinition>();

    		for(LexIdentifierToken id : decl.getIdentifiers())
    		{
    		}
    		
        	return pol;
    	}


    	@Override
    	public ProofObligationList caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
    			POContextStack question) throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction left = node.getLeft();
    		PAction right = node.getRight();

    		pol.addAll(left.apply(parentPOG, question));
    		pol.addAll( right.apply(parentPOG,question));

    		//Any untimed POs?
    		
        	return pol;
    	}



    	@Override
    	public ProofObligationList caseATimeoutAction(ATimeoutAction node, POContextStack question)
    			throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction left = node.getLeft();
    		PAction right = node.getRight();
    		PExp timedExp = node.getTimeoutExpression();

    		pol.addAll(left.apply(parentPOG, question));
    		pol.addAll(timedExp.apply(parentPOG, question));
    		pol.addAll(right.apply(parentPOG,question));

    		//Any timeout POs?
    		
        	return pol;
    	}



    	@Override
    	public ProofObligationList caseAExternalClause(AExternalClause node,
    			POContextStack question) throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		LinkedList<LexNameToken> ids = node.getIdentifiers();
    		for(LexNameToken id : ids)
    		{
        		//Any ext clause POs?
    		}
        	return pol;    	
        }



    	@Override
    	public ProofObligationList caseASpecificationStatementAction(
    			ASpecificationStatementAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	//Any ASpecificationStatementAction POs?
        	return pol;    	
    	}



    	@Override
    	public ProofObligationList caseAInternalChoiceReplicatedAction(
    			AInternalChoiceReplicatedAction node, POContextStack question)
    					throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		PAction repAction = node.getReplicatedAction();
    		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();
    		for(PSingleDeclaration d : repDecl)
    		{
    		}
        	//Any AInternalChoiceReplicatedAction POs?
    		return pol;

    	}



    	@Override
    	public ProofObligationList caseAGeneralisedParallelismReplicatedAction(
    			AGeneralisedParallelismReplicatedAction node, POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();

    		PVarsetExpression csexp = node.getChansetExpression();

    		PVarsetExpression sexp = node.getNamesetExpression();

    		PAction repAction = node.getReplicatedAction();

    		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();

    		return pol;
    	}



    	@Override
    	public ProofObligationList caseAExternalChoiceReplicatedAction(
    			AExternalChoiceReplicatedAction node, POContextStack question)
    					throws AnalysisException {

        	CMLProofObligationList pol = new CMLProofObligationList();

    		PAction action = node.getReplicatedAction();
    		LinkedList<PSingleDeclaration> decl = node.getReplicationDeclaration();
    		for(PSingleDeclaration d : decl)
    		{
    		}

    		pol.addAll(action.apply(parentPOG,question));

    		return pol;
    	}



    	@Override
    	public ProofObligationList caseANonDeterministicIfStatementAction(
    			ANonDeterministicIfStatementAction node, POContextStack question)
    					throws AnalysisException {

        	CMLProofObligationList pol = new CMLProofObligationList();
    		
    		LinkedList<ANonDeterministicAltStatementAction> alternatives = node.getAlternatives();
    		for(ANonDeterministicAltStatementAction alt : alternatives)
    		{
    			pol.addAll(alt.apply(parentPOG,question));
    		}

    		return pol;
    	}



    	@Override
    	public ProofObligationList caseANewStatementAction(ANewStatementAction node,
    			POContextStack question) throws AnalysisException {

    	   	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAMultipleGeneralAssignmentStatementAction(
    			AMultipleGeneralAssignmentStatementAction node,
    			POContextStack question) throws AnalysisException {

    		// extract sub-stuff
    		LinkedList<ASingleGeneralAssignmentStatementAction> assigns = node
    				.getAssignments();

    	   	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseALetStatementAction(ALetStatementAction node,
    			POContextStack question) throws AnalysisException {
    		// Extract sub-stuff
    		PAction action = node.getAction();
    		LinkedList<PDefinition> localDefs = node.getLocalDefinitions();

    	   	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAInterruptAction(AInterruptAction node,
    			POContextStack question) throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction left = node.getLeft();
    		PAction right = node.getRight();

    		pol.addAll(left.apply(parentPOG, question));
    		pol.addAll( right.apply(parentPOG,question));

    		//Any AInterruptAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAInterleavingParallelAction(
    			AInterleavingParallelAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction leftAction = node.getLeftAction();
    		pol.addAll(leftAction.apply(parentPOG, question));

    		PAction rightAction = node.getRightAction();
    		pol.addAll(rightAction.apply(parentPOG, question));

    		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
    		pol.addAll(leftNamesetExp.apply(parentPOG, question));

    		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();
    		pol.addAll(rightnamesetExp.apply(parentPOG, question));

    		//TODO: Consider AInterleavingParallelAction POs
    						
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseADeclarationInstantiatedAction(
    			ADeclarationInstantiatedAction node, POContextStack question)
    					throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		// Extract sub-stuff
    		PAction action = node.getAction();
    		LinkedList<ATypeSingleDeclaration> declarations = node.getDeclaration();

        	pol.addAll(action.apply(parentPOG, question));

    		for (ATypeSingleDeclaration declr : declarations) {
    			//TODO: Any ADeclarationInstantiatedAction POs?
    		}

    		// All done!
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAGeneralisedParallelismParallelAction(
    			AGeneralisedParallelismParallelAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction leftAction = node.getLeftAction();
    		pol.addAll(leftAction.apply(parentPOG, question));

    		PAction rightAction = node.getRightAction();
    		pol.addAll(rightAction.apply(parentPOG, question));

    		PVarsetExpression leftNamesetExp = node.getLeftNamesetExpression();
    		pol.addAll(leftNamesetExp.apply(parentPOG, question));

    		PVarsetExpression rightnamesetExp = node.getRightNamesetExpression();
    		pol.addAll(rightnamesetExp.apply(parentPOG, question));

    		//TODO: Consider AGeneralisedParallelismParallelAction POs
    						
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseAForSetStatementAction(AForSetStatementAction node,
    			POContextStack question) throws AnalysisException {
    		// TODO RWL Working on it !

    		// extract sub-trees
    		PAction action = node.getAction();
    		PPattern pattern = node.getPattern();
    		PExp set = node.getSet();
    		
        	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;    		
    	}



    	@Override
    	public ProofObligationList caseAForSequenceStatementAction(
    			AForSequenceStatementAction node, POContextStack question)
    					throws AnalysisException {

    		PAction action = node.getAction();
    		PExp exp = node.getExp();
    		ADefPatternBind pattern = node.getPatternBind();
    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAForIndexStatementAction(AForIndexStatementAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAChannelRenamingAction(AChannelRenamingAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAWaitAction(AWaitAction node,
    			POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();

    		PExp timedExp = node.getExpression();

    		pol.addAll(timedExp.apply(parentPOG, question));

    		//Any AWaitAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseACaseAlternativeAction(ACaseAlternativeAction node,
    			POContextStack question)
    					throws AnalysisException {

    		LinkedList<PDefinition> defs = node.getDefs();
    		LinkedList<PPattern> ptrn = node.getPattern();
    		PAction res = node.getResult();

    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any ACaseAlternativeAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseACasesStatementAction(ACasesStatementAction node,
    			POContextStack question)
    					throws AnalysisException {

    		LinkedList<ACaseAlternativeAction> cases = node.getCases();
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any ACasesStatementAction POs?
    		
        	return pol;
    	}


    	@Override
    	public ProofObligationList caseAMuAction(AMuAction node, POContextStack question)
    					throws AnalysisException {

    		// extract elements from the node
    		LinkedList<LexIdentifierToken> ids = node.getIdentifiers();
    		LinkedList<PAction> acts = node.getActions();


    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any AMuAction POs?
    		
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAChaosAction(AChaosAction node, POContextStack question)
    					throws AnalysisException {
    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any AChaosAction POs?
    		
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseASequentialCompositionReplicatedAction(
    			ASequentialCompositionReplicatedAction node, POContextStack question)
    					throws AnalysisException {

    		PAction replicatedAction = node.getReplicatedAction();
    		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any ASequentialCompositionReplicatedAction POs?
    		
        	return pol;
    	}


    	@Override
    	public ProofObligationList caseAAssignmentCallStatementAction(
    			AAssignmentCallStatementAction node,POContextStack question)
    					throws AnalysisException {

    		PExp designator = node.getDesignator();
    		ACallStatementAction call = node.getCall();

    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any AAssignmentCallStatementAction POs?
    		
        	return pol;
    	}

    	

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAAlphabetisedParallelismParallelAction(
    			AAlphabetisedParallelismParallelAction node, POContextStack question)
    					throws AnalysisException {
    		CMLProofObligationList pol = new CMLProofObligationList();

    		PAction leftAction = node.getLeftAction();
    		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
    		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

    		PAction rightAction = node.getRightAction();
    		PVarsetExpression rightChanSet = node.getRightChansetExpression();
    		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();


    		pol.addAll(leftAction.apply(parentPOG, question));
    		pol.addAll(leftChanSet.apply(parentPOG,question));
    		pol.addAll(leftNameSet.apply(parentPOG, question));
    		pol.addAll(rightAction.apply(parentPOG,question));
    		pol.addAll(rightChanSet.apply(parentPOG, question));
    		pol.addAll(rightNameSet.apply(parentPOG,question));

    		//Any AAlphabetisedParallelismParallelAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAReturnStatementAction(AReturnStatementAction node,
    			POContextStack question)
    					throws AnalysisException {
    		AExplicitCmlOperationDefinition operation = node
    				.getAncestor(AExplicitCmlOperationDefinition.class);
    		

    		PExp exp = node.getExp();
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		//Any AAssignmentCallStatementAction POs?
    		
        	return pol;
    	}




    	@Override
    	public ProofObligationList caseAGuardedAction(AGuardedAction node, POContextStack question)
    			throws AnalysisException {

    		PExp exp = node.getExpression();
    		PAction action = node.getAction();

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		pol.addAll(exp.apply(parentPOG, question));
    		pol.addAll(action.apply(parentPOG,question));

    		//Any AGuardedAction POs?
    		
        	return pol;

    	}



    	@Override
    	public ProofObligationList caseADivAction(ADivAction node, POContextStack question)
    			throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseASubclassResponsibilityAction(
    			ASubclassResponsibilityAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseACommonInterleavingReplicatedAction(
    			ACommonInterleavingReplicatedAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseAInterleavingReplicatedAction(
    			AInterleavingReplicatedAction node, POContextStack question)
    					throws AnalysisException {
    		
    		PVarsetExpression namesetExp = node.getNamesetExpression();
    		PAction repAction = node.getReplicatedAction();
    		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();


    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseASynchronousParallelismReplicatedAction(
    			ASynchronousParallelismReplicatedAction node, POContextStack question)
    					throws AnalysisException {
    		
    		PVarsetExpression namesetExp = node.getNamesetExpression();
    		PAction repAction = node.getReplicatedAction();
    		LinkedList<PSingleDeclaration> decls = node.getReplicationDeclaration();

    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseANotYetSpecifiedStatementAction(
    			ANotYetSpecifiedStatementAction node, POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		return pol;
    	}

   	

    	@Override
    	public ProofObligationList caseAInternalChoiceAction(AInternalChoiceAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction left = node.getLeft();
    		PAction right = node.getRight();

    		pol.addAll(left.apply(parentPOG, question));
    		pol.addAll( right.apply(parentPOG,question));

    		//Any AInternalChoiceAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAReferenceAction(AReferenceAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseACommunicationAction(ACommunicationAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	
    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseASkipAction(ASkipAction node,
    			POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
       	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAExternalChoiceAction(AExternalChoiceAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction left = node.getLeft();
    		PAction right = node.getRight();

    		pol.addAll(left.apply(parentPOG, question));
    		pol.addAll( right.apply(parentPOG,question));

    		//Any AInternalChoiceAction POs?
    		
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAHidingAction(AHidingAction node,
    			POContextStack question) throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		PAction action = node.getLeft();
    		PVarsetExpression chanSet = node.getChansetExpression();

    		

    		pol.addAll(action.apply(parentPOG, question));
    		pol.addAll(chanSet.apply(parentPOG,question));

    		//Any AHidingAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAVresParametrisation(AVresParametrisation node,
    			POContextStack question) throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();

    		ATypeSingleDeclaration decl = node.getDeclaration();
    		pol.addAll(decl.apply(parentPOG,question));

    		//Any AVresParametrisation POs?
    		
    		return pol;
    	}

    	@Override
    	public ProofObligationList caseAParametrisedInstantiatedAction(
    			AParametrisedInstantiatedAction node, POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();

    		AParametrisedAction action = node.getAction();
    		LinkedList<PExp> args = node.getArgs();


    		LinkedList<PParametrisation> parameterNames = node.getAction().getParametrisations();
    		
    		pol.addAll(action.apply(parentPOG, question));

    		//Any AParametrisedInstantiatedAction POs?
    		
        	return pol;
    	}




    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAStartDeadlineAction(AStartDeadlineAction node,
    			POContextStack question)
    					throws AnalysisException {
    		
    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction event = node.getLeft();
    		PExp timeExp = node.getExpression();

    		pol.addAll(event.apply(parentPOG, question));
    		pol.addAll(timeExp.apply(parentPOG,question));

    		//Any AStartDeadlineAction POs?
    		
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseAEndDeadlineAction(AEndDeadlineAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
    		
    		PAction event = node.getLeft();
    		PExp timeExp = node.getExpression();

    		pol.addAll(event.apply(parentPOG, question));
    		pol.addAll(timeExp.apply(parentPOG,question));

    		//Any AEndDeadlineAction POs?
    		
        	return pol;
    	}

    	@Override
    	public ProofObligationList caseAStopAction(AStopAction node,
    			POContextStack question)
    					throws AnalysisException {

        	CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
        }

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseACallStatementAction(ACallStatementAction node,
    			POContextStack question)
    					throws AnalysisException {

    		CMLProofObligationList pol = new CMLProofObligationList();
        	return pol;
    	}

    	@SuppressWarnings("deprecation")
    	@Override
    	public ProofObligationList caseASynchronousParallelismParallelAction(
    			ASynchronousParallelismParallelAction node,	POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();

    		PAction leftAction = node.getLeftAction();
    		PVarsetExpression leftNameSet = node.getLeftNamesetExpression();

    		PAction rightAction = node.getRightAction();
    		PVarsetExpression rightNameSet = node.getLeftNamesetExpression();


    		pol.addAll(leftAction.apply(parentPOG, question));
    		pol.addAll(leftNameSet.apply(parentPOG, question));
    		pol.addAll(rightAction.apply(parentPOG,question));
    		pol.addAll(rightNameSet.apply(parentPOG,question));
    		
    		//Any ASynchronousParallelismParallelAction POs?
    		
        	return pol;
    	}






    	@Override
    	public ProofObligationList caseANonDeterministicDoStatementAction(
    			ANonDeterministicDoStatementAction node, POContextStack question)
    					throws AnalysisException {
        	CMLProofObligationList pol = new CMLProofObligationList();

    		LinkedList<ANonDeterministicAltStatementAction> alternatives = node.getAlternatives();
    		for(ANonDeterministicAltStatementAction act : alternatives){
    			
    		}

    		//Any ANonDeterministicDoStatementAction POs?
    		
        	return pol;
    	}






    	@Override
    	public ProofObligationList caseANonDeterministicAltStatementAction(
    			ANonDeterministicAltStatementAction node, POContextStack question)
    					throws AnalysisException {

        	CMLProofObligationList pol = new CMLProofObligationList();

    		PExp guard = node.getGuard();
    		PAction action = node.getAction();

    		pol.addAll(guard.apply(parentPOG,question));
    		pol.addAll(action.apply(parentPOG,question));

    		//Any ANonDeterministicAltStatementAction POs?
    		return pol;
    	}






    	@Override
    	public ProofObligationList caseAParametrisedAction(AParametrisedAction node,
    			POContextStack question) throws AnalysisException {

        	CMLProofObligationList pol = new CMLProofObligationList();

    		PAction action = node.getAction();
    		LinkedList<PParametrisation> params = node.getParametrisations();

    		pol.addAll(action.apply(parentPOG,question));
    		
    		return pol;
    	}




    }

