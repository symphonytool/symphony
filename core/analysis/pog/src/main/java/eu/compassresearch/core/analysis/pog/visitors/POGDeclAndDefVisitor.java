package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.PPattern;
import org.overture.ast.types.PType;
import org.overture.pog.obligation.OperationPostConditionObligation;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.POOperationDefinitionContext;
import org.overture.pog.obligation.ParameterPatternObligation;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.obligation.SatisfiabilityObligation;
import org.overture.pog.obligation.StateInvariantObligation;
import org.overture.pog.visitor.PogParamDefinitionVisitor;


import eu.compassresearch.ast.actions.SStatementAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AImplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.definitions.SOperationDefinition;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLOperationPostConditionObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOOperationDefinitionContext;
import eu.compassresearch.core.analysis.pog.obligations.CMLParameterPatternObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLSatisfiabilityObligation;

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
    public CMLProofObligationList caseAChannelParagraphDefinition(
    	AChannelParagraphDefinition node, POContextStack question)
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
		LinkedList ids = node.getSingleType().getIdentifiers();
		PType type = node.getSingleType().getType();    	
    
    	return pol;
    }
    
    /**
     * 
     * CML ELEMENT - Chansets
     * 
     */
    @Override
    public ProofObligationList caseAChansetParagraphDefinition(
	    AChansetParagraphDefinition node, POContextStack question)
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
		LexIdentifierToken id = node.getIdentifier();
		PExp expr = node.getChansetExpressions();
		
		return pol;
    }
    
    
    /**
     * 
     * CML ELEMENT - Classes
     * 
     */
    @Override
    public CMLProofObligationList caseAClassParagraphDefinition(
	    AClassParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
		System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAClassParagraphDefinition");
	
		CMLProofObligationList pol = new CMLProofObligationList();
	
		for (PDefinition def : node.getDefinitions()) {
		    System.out.println("In defn Paragraph Loop: " + def.toString());
		    pol.addAll(def.apply(parentPOG, question));
		}
	
		return pol;
    }


    /**
     * 
     * CML ELEMENT - Processes
     * 
     */
    @Override
    public ProofObligationList caseAProcessParagraphDefinition(
	    AProcessParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	
		AProcessDefinition pdef = node.getProcessDefinition();
		return pdef.apply(parentPOG, question);
    }

    @Override
    public ProofObligationList caseAProcessDefinition(
	    AProcessDefinition node, POContextStack question)
	    throws AnalysisException {
    	
    	System.out.println("----------***----------");
		System.out.println("AProcessDefinition");
		System.out.println(node.toString());
		
		CMLProofObligationList pol = new CMLProofObligationList();
		
		LinkedList<PDeclaration> lstate = node.getLocalState();
		
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

		pol.addAll(pdef.apply(parentPOG, question));
		return pol;
    }
    
    
    @Override
    public ProofObligationList caseAStateParagraphDefinition(
    		AStateParagraphDefinition node, POContextStack question)
    	    throws AnalysisException {
    	System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAStateParagraphDefinition");
		
		System.out.println("State: " + node.toString() + ", Type: " + node.getType());
	
		CMLProofObligationList pol = new CMLProofObligationList();

		LinkedList<PDefinition> defs = node.getStateDefs();
		for (PDefinition def : defs) {
		    System.out
			    .println("In State Paragraph Loop");
		    System.out.println("Def: " + def.toString() + ", Type: " + def.getType());
		    pol.addAll(def.apply(parentPOG, question));
		}
	
		return pol;
    }
    
    /**
     * 
     * CML ELEMENT - Actions
     * 
     */
    @Override
    public ProofObligationList caseAActionParagraphDefinition(
	    AActionParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
		System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");
	
		LinkedList<AActionDefinition> actions = node.getActions();
		for (AActionDefinition action : actions) {
		    System.out
			    .println("In Action Paragraph Loop: " + action.toString());
		    System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
		}

	return new CMLProofObligationList();
    }
    
    
    
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public ProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    POContextStack question) throws AnalysisException {
	return new CMLProofObligationList();
    }





   
    /**
     * 
     * VDM ELEMENT - Types
     * 
     */
    @Override
    public ProofObligationList caseATypesParagraphDefinition(
	    ATypesParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
    	CMLProofObligationList pol = new CMLProofObligationList();
	
    	for (PDefinition def : node.getTypes()) {
    		pol.addAll(def.apply(parentPOG, question));
    	}

    	return pol;
    }
    
	  /**
	  * 
	  * VDM ELEMENT - Values
	  * 
	  */
	 @Override
	 public ProofObligationList caseAValueParagraphDefinition(
		    AValueParagraphDefinition node, POContextStack question)
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
	 public ProofObligationList caseAFunctionParagraphDefinition(
		    AFunctionParagraphDefinition node, POContextStack question)
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
	 public ProofObligationList caseAOperationParagraphDefinition(
		    AOperationParagraphDefinition node, POContextStack question)
		    throws AnalysisException {
	 	
		 CMLProofObligationList pol = new CMLProofObligationList();
		 for (SOperationDefinition def : node.getOperations()) {
			 pol.addAll(def.apply(parentPOG, question));
		 }
		 return pol;
	 }

	 @Override
	 public ProofObligationList caseAImplicitOperationDefinition(
			 AImplicitOperationDefinition node, POContextStack question) 
			throws AnalysisException{

		 System.out.println("----------***----------");
		 System.out.println("AImplicitOperationDefinition");
		 System.out.println(node.toString());
		 System.out.println("----------***----------");
		 
		 CMLProofObligationList pol = new CMLProofObligationList();
		 LexNameList pids = new LexNameList();

		 for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

			if (pids.hasDuplicates()){
				pol.add(new CMLParameterPatternObligation(node, question));
			}

			// if implicit operation has a precondition, dispatch for PO checking
			if (node.getPrecondition() != null){
				pol.addAll(node.getPrecondition().apply(parentPOG, question));
			}
			
			// if implicit operation has a precondition, dispatch for PO checking
			// and generate OperationPostConditionObligation
			if (node.getPostcondition() != null){
				pol.addAll(node.getPostcondition().apply(parentPOG, question));
				pol.add(new CMLOperationPostConditionObligation(node, question));

		//		COMMENTED AS CONTEXT GENERATED VARIOUS NULL OBJECTS, DUE TO NEW AST...
		//		AS SUCH SAT OBLIG DOESN'T DO MUCH
		//		question.push(new CMLPOOperationDefinitionContext(node, false, node.getStateDefinition()));
				pol.add(new CMLSatisfiabilityObligation(node, node.getStateDefinition(), question));
		//		question.pop();			
			}
		 
			return pol;
		}
	 
	 
	 @Override
   public ProofObligationList caseAExplicitOperationDefinition(
	    AExplicitOperationDefinition node, POContextStack question)
	    throws AnalysisException {
		
		 CMLProofObligationList pol = new CMLProofObligationList();

		LexNameList pids = new LexNameList();

		// add all defined names from the function parameter list
		for (PPattern p : node.getParameterPatterns())
			for (PDefinition def : p.getDefinitions())
				pids.add(def.getName());

		if (pids.hasDuplicates()){
			pol.add(new CMLParameterPatternObligation(node, question));
		}

		if (node.getPrecondition() != null){
			pol.addAll(node.getPrecondition().apply(parentPOG, question));
		}

		if (node.getPostcondition() != null){
			pol.addAll(node.getPostcondition().apply(parentPOG, question));
			pol.add(new CMLOperationPostConditionObligation(node, question));
		}
		
		pol.addAll(node.getBody().apply(parentPOG, question));

//		if (node.getIsConstructor() && node.getClassDefinition() != null
//				&& node.getClassDefinition().getInvariant() != null){
//			pol.add(new CMLStateInvariantObligation(node, question));
	//	}
		
		/*
		 * Subtype Obligation not working well - mainly the if statement, I believe.
		 */
//		if (!node.getIsConstructor()
//				&& !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
//			pol.add(new SubTypeObligation(node, node.getActualResult(), question));
//		}
   	
		return pol;
   }
 
	    // Call Overture for the other declarations    
	    @Override
	    public CMLProofObligationList defaultPDeclaration(PDeclaration node,
		    POContextStack question) throws AnalysisException {
	    	CMLProofObligationList pol = new CMLProofObligationList();
	    	pol.addAll(node.apply(overtureVisitor, question));
	    	return pol;
	    }

	    // Call Overture for the other definitions - essentially the VDM definitions    
	    @Override
	    public CMLProofObligationList defaultPDefinition(PDefinition node,
		    POContextStack question) throws AnalysisException {
	    	CMLProofObligationList pol = new CMLProofObligationList();
	    	pol.addAll(node.apply(overtureVisitor, question));
	    	return pol;
	    }
	    

	    // Call the main pog when it's not a definition/declaration
	    @Override
	    public ProofObligationList defaultINode(INode node, POContextStack question)
		    throws AnalysisException {
	    	CMLProofObligationList pol = new CMLProofObligationList();
	    	pol.addAll(node.apply(parentPOG, question));
	    	return pol;
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
//    
//    
    
 }