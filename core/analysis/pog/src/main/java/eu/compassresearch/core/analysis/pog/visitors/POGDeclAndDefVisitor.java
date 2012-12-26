package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.node.INode;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogParamDefinitionVisitor;


import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AClassDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends
	QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

    // Errors and other things are recorded on this guy
    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> parentPOG;
    final private PogParamDefinitionVisitor<POContextStack, ProofObligationList> overtureVisitor;
    
    public POGDeclAndDefVisitor(QuestionAnswerAdaptor<POContextStack, 
    		ProofObligationList> parent) {
    	this.parentPOG = parent;
    	this.overtureVisitor = new PogParamDefinitionVisitor<POContextStack, ProofObligationList>
    		(this, this);
	
    }
    
    
    // handle CML Defns And Decls
    /**
     * 
     * CML ELEMENT - Channels
     * 
     */
    @Override
    public CMLProofObligationList caseAChannelsDefinition(
    	AChannelsDefinition node, POContextStack question)
    	throws AnalysisException{
    	
    	CMLProofObligationList pol = new CMLProofObligationList();
    
    	LinkedList<AChannelNameDefinition> cns = node.getChannelNameDeclarations();
    	for (AChannelNameDefinition c : cns){
    		pol.addAll(c.apply(this, question));
    	}
    
    	return pol;
     }
     
      
    
    @Override
    public CMLProofObligationList caseAChannelNameDefinition(
    		AChannelNameDefinition node, POContextStack question)
    		    	throws AnalysisException{

    	System.out.println("----------***----------");
		System.out.println("AChannelNameDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");
    	
    	CMLProofObligationList pol = new CMLProofObligationList();
    
    	/*
    	 * Not clear what POs these may generate? May be useful for generating CMLPOContext
    	 */
    	// Commented out by RWL, unused variables creates warnings
//		LinkedList ids = node.getSingleType().getIdentifiers();
//		PType type = node.getSingleType().getType();    	
    
    	return pol;
    }
    
    /**
     * 
     * CML ELEMENT - Chansets
     * 
     */
    @Override
    public ProofObligationList caseAChansetsDefinition(
	    AChansetsDefinition node, POContextStack question)
	    throws AnalysisException {
	
		LinkedList<AChansetDefinition> subNodes = node.getChansets();
		CMLProofObligationList pol = new CMLProofObligationList();
		
		for (AChansetDefinition d : subNodes) {
		    pol.addAll(d.apply(this, question));
		}
	
		return pol;
    }

    @Override
    public ProofObligationList caseAChansetDefinition(
	    AChansetDefinition node, POContextStack question)
	    throws AnalysisException {
    	
    	System.out.println("----------***----------");
		System.out.println("AChansetDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");
		
		CMLProofObligationList pol = new CMLProofObligationList();

    	/*
    	 * Not clear what POs these may generate? May be useful for generating CMLPOContext
    	 */
		// Commented out by RWL: Unused variables creates warnings.
		// LexIdentifierToken id = node.getIdentifier();
		// PVarsetExpression expr = node.getChansetExpression();
		
		return pol;
    }
    
    
    /**
     * 
     * CML ELEMENT - Classes
     * 
     */
    @Override
    public CMLProofObligationList caseAClassDefinition(
	    AClassDefinition node, POContextStack question)
	    throws AnalysisException {
		System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAClassParagraphDefinition");
	
		CMLProofObligationList pol = new CMLProofObligationList();
	
		for (PDefinition def : node.getBody()) {
		    System.out.println("In defn Paragraph Loop: " + def.toString());
		    pol.addAll(def.apply(parentPOG, question));
		}
	
		return pol;
    }


      @Override
    public ProofObligationList caseAProcessDefinition(
	    AProcessDefinition node, POContextStack question)
	    throws AnalysisException {
    	
    	System.out.println("----------***----------");
		System.out.println("AProcessDefinition");
		System.out.println(node.toString());
		
		// RWL: Commented out unused variables creates warnings.
		// CMLProofObligationList pol = new CMLProofObligationList();
		
		LinkedList<PSingleDeclaration> lstate = node.getLocalState();
		
		PProcess pdef = node.getProcess();
		System.out.println("State :" + lstate.toString() + ", process :" + pdef.toString()); 

		System.out.println("----------***----------");
	
		// NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
		// LinkedList<> lstate = node.getLocalState();
		// for ( s : lstate)
		// {
		// System.out.println("----------***----------");
		// CMLProofObligationList td = s.apply(this, question);
		// System.out.println("----------***----------");
		// }

		return pdef.apply(this, question);
//		return pol;
    }
    
    
    @Override
    public ProofObligationList caseAStateDefinition(
    		AStateDefinition node, POContextStack question)
    	    throws AnalysisException {
    	System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAStateParagraphDefinition");
		

		return new CMLProofObligationList();
    }
    
    /**
     * 
     * CML ELEMENT - Actions
     * 
     */
    @Override
    public ProofObligationList caseAActionsDefinition(
	    AActionsDefinition node, POContextStack question)
	    throws AnalysisException {
		System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");
	
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions) {
		    System.out
			    .println("In Action Paragraph Loop: " + action.toString());
	}

	return new CMLProofObligationList();
    }
    
    
    
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public ProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    POContextStack question) throws AnalysisException {
	return new CMLProofObligationList();
    }

   
    
    
    // Call Overture for the other expressions    
    @Override
    public CMLProofObligationList defaultPSingleDeclaration(PSingleDeclaration node,
	    POContextStack question) throws AnalysisException {
    	CMLProofObligationList pol = new CMLProofObligationList();
    	pol.addAll(node.apply(overtureVisitor, question));
    	return pol;
    }

    @Override
    public CMLProofObligationList defaultPDefinition(PDefinition node,
	    POContextStack question) throws AnalysisException {
    	CMLProofObligationList pol = new CMLProofObligationList();
    	pol.addAll(node.apply(overtureVisitor, question));
    	return pol;
    }
    

    // Call the main pog when it's not a defintion/declaration
    @Override
    public ProofObligationList defaultINode(INode node, POContextStack question)
	    throws AnalysisException {
    	CMLProofObligationList pol = new CMLProofObligationList();
    	pol.addAll(node.apply(parentPOG, question));
    	return pol;
    }



   
    /**
     * 
     * VDM ELEMENT - Types
     * 
     */
    @Override
    public ProofObligationList caseATypesDefinition(
	    ATypesDefinition node, POContextStack question)
	    throws AnalysisException {
    	CMLProofObligationList pol = new CMLProofObligationList();
	
    	for (PDefinition def : node.getTypes()) {
    		pol.addAll(def.apply(this, question));
    	}

    	return pol;
    }
    
	  /**
	  * 
	  * VDM ELEMENT - Values
	  * 
	  */
	 @Override
	 public ProofObligationList caseAValuesDefinition(
		    AValuesDefinition node, POContextStack question)
		    throws AnalysisException {
	 	
			CMLProofObligationList pol = new CMLProofObligationList();
			LinkedList<PDefinition> list = node.getValueDefinitions();
			
			for (PDefinition def : list) {
			   pol.addAll(def.apply(parentPOG, question));
			}
		
			return pol;
	 }
	 
	  /**
	  * 
	  * VDM ELEMENT - Functions
	  * 
	  */
	 @Override
	 public ProofObligationList caseAFunctionsDefinition(
		    AFunctionsDefinition node, POContextStack question)
		    throws AnalysisException {
			CMLProofObligationList obligations = new CMLProofObligationList();
		
			for (PDefinition def : node.getFunctionDefinitions()) {
				obligations.addAll(def.apply(parentPOG, question));
			}
		
			return obligations;
	 }
	 
	  /**
	  * 
	  * VDM ELEMENT - Operations
	  * 
	  */
	 @Override
	 public ProofObligationList caseAOperationsDefinition(
		    AOperationsDefinition node, POContextStack question)
		    throws AnalysisException {
	 	CMLProofObligationList pol = new CMLProofObligationList();
			for (PDefinition def : node.getOperations()) {
		    	pol.addAll(def.apply(this, question));
			}
			return pol;
	 }
}



//
//    @Override
//    public ProofObligationList caseATypeDefinition(ATypeDefinition node,
//	    POContextStack question) throws AnalysisException {
//
//    	System.out.println("----------***----------");
//		System.out.println("ATypeDefinition");
//		System.out.println(node.toString());
//	
//		CMLProofObligationList pol = new CMLProofObligationList();
//		AExplicitFunctionDefinition invDef = node.getInvdef();
//		
//		
//		PPattern pat = node.getInvPattern();
//		if (pat != null){
//			System.out.println("Pattern: " + pat.toString());
//		}
//		
//		PExp exp = node.getInvExpression();
//			
//		if (exp != null){
//			System.out.println("Expression: " + node.getInvExpression().toString());
//		}
//		System.out.println("----------***----------");
//		
//		/*
//		 * The AST currently will not produce an invDef value. Only
//		 * produces a pattern and expression. 
//		 */
//		if (invDef != null){
//			pol.addAll(invDef.apply(this, question));
//		}
//		return pol;
//    }
//
//

//
//    @Override
//    public ProofObligationList caseAValueDefinition(AValueDefinition node,
//	    POContextStack question) throws AnalysisException {
//
//    	System.out.println("----------***----------");
//		System.out.println("AValueDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//		
//		CMLProofObligationList pol = new CMLProofObligationList();
//
//		PExp exp = node.getExpression();
//		pol.addAll(exp.apply(parentPOG, question));
//		
//		/*
//		 *Some aspects of Overture POG are not working nicely.
//		 *Have commented out for moment - need to be readdressed, though
//	 	 */
//		
////		PPattern pattern = node.getPattern();
////		PType type = node.getType();
//		
////		if (!(pattern instanceof AIdentifierPattern)
////				&& !(pattern instanceof AIgnorePattern)
////				&& PTypeAssistant.isUnion(type))
////		{
////			PType patternType = PPatternAssistantTC.getPossibleType(pattern);
////			AUnionType ut = PTypeAssistant.getUnion(type);
////			PTypeSet set = new PTypeSet();
////
////			for (PType u : ut.getTypes())
////			{
////				if (TypeComparator.compatible(u, patternType))
////					set.add(u);
////			}
////
////			if (!set.isEmpty())
////			{
////				PType compatible = set.getType(node.getLocation());
////				if (!TypeComparator.isSubType(type, compatible))
////				{
////					obligations.add(new ValueBindingObligation(node, question));
////					obligations.add(new SubTypeObligation(exp, compatible, type, question));
////				}
////			}
////		}
////
////		if (!TypeComparator.isSubType(question.checkType(exp, node.getExpType()), type))
////		{
////			obligations.add(new SubTypeObligation(exp, type, node.getExpType(), question));
////		}
//		
//		return pol;
//    }
//
//

//
//    @Override
//    public ProofObligationList caseAExplicitFunctionDefinition(
//	    AExplicitFunctionDefinition node, POContextStack question)
//	    throws AnalysisException {
//
//		CMLProofObligationList obligations = new CMLProofObligationList();
//	
//		System.out.println("----------***----------");
//		System.out.println("caseAExplicitFunctionDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//	
//		LexNameList pids = new LexNameList();
//	
//		// add all defined names from the function parameter list
//		for (List<PPattern> patterns : node.getParamPatternList())
//			for (PPattern p : patterns)
//				for (PDefinition def : p.getDefinitions())
//					pids.add(def.getName());
//	
//		// check for duplicates
//		if (pids.hasDuplicates()){
//			obligations.add(new ParameterPatternObligation(node, question));
//		}
//	
//		// do proof obligations for the pre-condition
//		PExp precondition = node.getPrecondition();
//		if (precondition != null) {
//		    question.push(new POFunctionDefinitionContext(node, false));
//		    obligations.addAll(precondition.apply(parentPOG, question));
//		    question.pop();
//		}
//	
//	
//		// do proof obligations for the post-condition
//		PExp postcondition = node.getPostcondition();
//		if (postcondition != null){
//			question.push(new POFunctionDefinitionContext(node, false));
//			obligations.add(new FuncPostConditionObligation(node, question));
//			question.push(new POFunctionResultContext(node));
//			obligations.addAll(postcondition.apply(parentPOG, question));
//			question.pop();
//			question.pop();
//		}
//		
//		// do proof obligations for the function body
//		question.push(new POFunctionDefinitionContext(node, true));
//		PExp body = node.getBody();
//		int sizeBefore = question.size();
//		ProofObligationList pol = body.apply(parentPOG,question);
//		obligations.addAll(body.apply(parentPOG, question));
//		assert sizeBefore <= question.size();
//		
//		/*
//		 * Subtype Obligation not working well - mainly the if statement, I believe.
//		 */
////		// do proof obligation for the return type
////		if (node.getIsUndefined()
////				|| !TypeComparator.isSubType(node.getActualResult(), node.getExpectedResult()))
////		{
////			obligations.add(new SubTypeObligation(node, node.getExpectedResult(), node.getActualResult(), question));
////		}
//		question.pop();
//	
//		return obligations;
//	}
//
//    
//    @Override
//    public ProofObligationList caseAImplicitFunctionDefinition(
//	    AImplicitFunctionDefinition node, POContextStack question)
//	    throws AnalysisException {
//    	
//    	System.out.println("----------***----------");
//		System.out.println("AImplicitFunctionDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//    	
//		CMLProofObligationList obligations = new CMLProofObligationList();
//		
//		LexNameList pids = new LexNameList();
//	
//		//check for duplicates? 
//		for (APatternListTypePair pltp : node.getParamPatterns()){
//			for (PPattern p : pltp.getPatterns()){
//				for (PDefinition def : p.getDefinitions())
//					pids.add(def.getName());
//			}
//		}
//	
//		if (pids.hasDuplicates()){
//			obligations.add(new ParameterPatternObligation(node, question));
//		}
//	
//		// if implicit function has a precondition, dispatch for PO checking
//		if (node.getPrecondition() != null) {
//		    obligations.addAll(node.getPrecondition()
//			    .apply(parentPOG, question));
//		}
//		
//		// if has a postcondition should generate SatisfiabilityObligation
//		if (node.getPostcondition() != null) {
//	    	question.push(new POFunctionDefinitionContext(node, false));
//		    //if there is also a body, should generate a FuncPostConditionObligation
//		    if (node.getBody() != null){
//				obligations.add(new FuncPostConditionObligation(node, question));
//			}
//		    //if there is no body should generate SatisfiabilityObligation
//		    else{
//		    	/*
//		    	 * SatisfiabilityObligation is not working
//		    	 */
////		    	obligations.add(new SatisfiabilityObligation(node, question));
//		    }
//		    question.pop();
//		    //dispatch postconditon expression for PO checking
//		    question.push(new POFunctionResultContext(node));
//			obligations.addAll(node.getPostcondition().apply(parentPOG, question));
//			question.pop();
//		}
//		   
//		//if has a body, should check for POs in body
//		if (node.getBody() != null)	{
//			question.push(new POFunctionDefinitionContext(node, false));
//			obligations.addAll(node.getBody().apply(parentPOG, question));
//	
//			/*
//			 * Subtype Obligation not working well - mainly the if statement, I believe.
//			 */
////			if (node.getIsUndefined()
////					|| !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
////				obligations.add(new SubTypeObligation(node, node.getType().getResult(), node.getActualResult(), question));
////			}
//			question.pop();
//		}	
//		
//		return obligations;
//    }
//
//    
//

//
//    @Override
//    public ProofObligationList caseAExplicitOperationDefinition(
//	    AExplicitOperationDefinition node, POContextStack question)
//	    throws AnalysisException {
//    	System.out.println("----------***----------");
//		System.out.println("AExplicitOperationDefinition");
//		System.out.println(node.toString());
//		System.out.println("----------***----------");
//		
//    	CMLProofObligationList pol = new CMLProofObligationList();
//
//		LexNameList pids = new LexNameList();
//
//		// add all defined names from the function parameter list
//		for (PPattern p : node.getParameterPatterns())
//			for (PDefinition def : p.getDefinitions())
//				pids.add(def.getName());
//
//		if (pids.hasDuplicates()){
//			pol.add(new ParameterPatternObligation(node, question));
//		}
//
//		if (node.getPrecondition() != null){
//			pol.addAll(node.getPrecondition().apply(parentPOG, question));
//		}
//
//		if (node.getPostcondition() != null){
//			pol.addAll(node.getPostcondition().apply(parentPOG, question));
//			pol.add(new OperationPostConditionObligation(node, question));
//		}
//
//		pol.addAll(node.getBody().apply(parentPOG, question));
//
//		if (node.getIsConstructor() && node.getClassDefinition() != null
//				&& node.getClassDefinition().getInvariant() != null){
//			pol.add(new StateInvariantObligation(node, question));
//		}
//		
//		/*
//		 * Subtype Obligation not working well - mainly the if statement, I believe.
//		 */
////		if (!node.getIsConstructor()
////				&& !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
////			pol.add(new SubTypeObligation(node, node.getActualResult(), question));
////		}
//    	
//		return pol;
//    }
//    
//    @Override
//	public CMLProofObligationList caseAImplicitOperationDefinition(
//			AImplicitOperationDefinition node, POContextStack question) throws AnalysisException
//	{
//
//		CMLProofObligationList pol = new CMLProofObligationList();
//		LexNameList pids = new LexNameList();
//
//		for (APatternListTypePair tp : node.getParameterPatterns())
//			for (PPattern p : tp.getPatterns())
//				for (PDefinition def : p.getDefinitions())
//					pids.add(def.getName());
//
//		if (pids.hasDuplicates()){
//			pol.add(new ParameterPatternObligation(node, question));
//		}
//
//		// if implicit operation has a precondition, dispatch for PO checking
//		if (node.getPrecondition() != null){
//			pol.addAll(node.getPrecondition().apply(parentPOG, question));
//		}
//		
//		// if implicit operation has a precondition, dispatch for PO checking
//		// and generate OperationPostConditionObligation
//		if (node.getPostcondition() != null){
//			pol.addAll(node.getPostcondition().apply(parentPOG, question));
//			pol.add(new OperationPostConditionObligation(node, question));
//		}
//
//		
//		if (node.getBody() != null){
//			pol.addAll(node.getBody().apply(parentPOG, question));
//
//			if (node.getIsConstructor() && node.getClassDefinition() != null
//					&& node.getClassDefinition().getInvariant() != null){
//				pol.add(new StateInvariantObligation(node, question));
//			}
//
//			/*
//			 * Subtype Obligation not working well - mainly the if statement, I believe.
//			 */
////			if (!node.getIsConstructor()
////					&& !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
////				pol.add(new SubTypeObligation(node, node.getActualResult(), question));
////			}
//		}else if (node.getPostcondition() != null){
//				question.push(new POOperationDefinitionContext(node, false, node.getStateDefinition()));
//				pol.add(new SatisfiabilityObligation(node, node.getStateDefinition(), question));
//				question.pop();
//			
//		}
//
//		return pol;
//	}