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
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;
import org.overture.pog.visitor.PogParamDefinitionVisitor;

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
import eu.compassresearch.ast.definitions.AInvariantDefinition;
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
import eu.compassresearch.core.analysis.pog.obligations.CMLParameterPatternObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CMLSatisfiabilityObligation;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends
	QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {


    final private QuestionAnswerAdaptor<POContextStack, ProofObligationList> parentPOG;
    final private PogParamDefinitionVisitor<POContextStack, ProofObligationList> overtureVisitor;
    
    /**
 	  * Constructor to initialise visitors
      */
    public POGDeclAndDefVisitor(QuestionAnswerAdaptor<POContextStack, 
    		ProofObligationList> parent) {
    	this.parentPOG = parent;
    	this.overtureVisitor = new PogParamDefinitionVisitor<POContextStack, ProofObligationList>
    		(this, this);
    }
    
    
    /**
	  * CML channel paragraph - split into channel
     */
    @Override
    public CMLProofObligationList caseAChannelParagraphDefinition(
    	AChannelParagraphDefinition node, POContextStack question)
    	throws AnalysisException{
    	
    	CMLProofObligationList pol = new CMLProofObligationList();
    
    	for (AChannelNameDefinition c : node.getChannelNameDeclarations()){
    		pol.addAll(c.apply(this, question));
    	}
    
    	return pol;
     }
     
      
    /**
	  * CML channel definition
	  * CURRENTLY JUST PRINT TO SCREEN
     */    
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
	  * CML chanset paragraph - split into chansets
     */
    @Override
    public ProofObligationList caseAChansetParagraphDefinition(
	    AChansetParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	
		CMLProofObligationList pol = new CMLProofObligationList();
		
		for (AChansetDefinition d : node.getChansets()) {
		    pol.addAll(d.apply(this, question));
		}
	
		return pol;
    }
    
    /**
	  * CML chanset definition
	  * CURRENTLY JUST PRINT TO SCREEN
     */
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
	  * CML class paragraph - split into definitions
	  * CURRENTLY JUST PRINT TO SCREEN
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
	  * CML process paragraph - handle process
	  * KEEP SEPERATE TO caseAProcessDefinition INCASE NEED TO DO ANYHTING ELSE LATER
     */
    @Override
    public ProofObligationList caseAProcessParagraphDefinition(
	    AProcessParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	
		AProcessDefinition pdef = node.getProcessDefinition();
		return pdef.apply(parentPOG, question);
    }
    
    /**
	  * CML process definition - handle process
	  * CURRENTLY PRINT TO SCREEN and handle process defn
     */
    @Override
    public ProofObligationList caseAProcessDefinition(
	    AProcessDefinition node, POContextStack question)
	    throws AnalysisException {
    	
    	System.out.println("----------***----------");
		System.out.println("AProcessDefinition");
		
		CMLProofObligationList pol = new CMLProofObligationList();
		
		LinkedList<PDeclaration> lstate = node.getLocalState();
		PProcess pdef = node.getProcess();
		
		System.out.println("State :" + lstate.toString() + ", process :" + pdef.toString()); 
		System.out.println("----------***----------");
	
		// Dispatch local state?
		// for (PDeclaration s : node.getLocalState())
		// {
		// 		pol.addAll(s.apply(parentPOG, question));
		// }

		pol.addAll(pdef.apply(parentPOG, question));
		return pol;
    }
    

    /**
	  * CML state paragraph - split into state defs
	  * CURRENTLY JUST PRINT TO SCREEN
     */
    @Override
    public ProofObligationList caseAStateParagraphDefinition(
    		AStateParagraphDefinition node, POContextStack question)
    	    throws AnalysisException {
    	System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAStateParagraphDefinition");
		
		System.out.println("State: " + node.toString() + ", Type: " + node.getType());
	
		CMLProofObligationList pol = new CMLProofObligationList();

		for (PDefinition def : node.getStateDefs()) {
		    System.out
			    .println("In State Paragraph Loop");
		    System.out.println("Def: " + def.toString() + ", Type: " + def.getType());
		    pol.addAll(def.apply(parentPOG, question));
		}
	
		return pol;
    }
    
    /**
	  * CML Action paragraph - split into actions
	  * CURRENTLY JUST PRINT TO SCREEN
     */
    @Override
    public ProofObligationList caseAActionParagraphDefinition(
	    AActionParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
		System.out.println("------");
		System.out
			.println("Reached POGDeclAndDefVisitor - caseAActionParagraphDefinition");
		
    	CMLProofObligationList pol = new CMLProofObligationList();
	
		for (AActionDefinition action : node.getActions()) {
		    System.out.println("Action: " + action.toString() + ", Type: " + action.getType());
		}

		return pol;
    }

   
    /**
	  * Type paragraph - split into types
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
	  * Invariant definition
	  * CURRENTLY PRINT TO SCREEN
	  */
   @Override
   public ProofObligationList caseAInvariantDefinition(
		   AInvariantDefinition node, POContextStack question)
	    throws AnalysisException {
   	
   	CMLProofObligationList pol = new CMLProofObligationList();
   	
	System.out.println("----------***----------");
	System.out.println("AInvariantDefinition");
	System.out.println(node.toString());
	System.out.println(node.getPattern());
	System.out.println(node.getExpression());
	System.out.println("----------***----------");
	
   	return pol;
   }
    
    /**
	  * Value paragraph - split into values
	  */
	 @Override
	 public ProofObligationList caseAValueParagraphDefinition(
		    AValueParagraphDefinition node, POContextStack question)
		    throws AnalysisException {
	 	
			CMLProofObligationList pol = new CMLProofObligationList();
			
			for (PDefinition def : node.getValueDefinitions()) {
			   pol.addAll(def.apply(parentPOG, question));
			}
		
			return pol;
	 }
	 
	 /**
	  * Function paragraph - split into functions
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
	  * Operation paragraph - split into operations
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
	 
	/**
	 * Implicit operations - CML does not reuse Overture operations
	 */
	@Override
	public ProofObligationList caseAImplicitOperationDefinition(
			 AImplicitOperationDefinition node, POContextStack question) 
			throws AnalysisException{

		System.out.println("----------***----------");
		System.out.println("AImplicitOperationDefinition");
		System.out.println(node.toString());
		System.out.println("----------***----------");
		
		CMLProofObligationList pol = new CMLProofObligationList();

		//Taken from Overture - Needed?
		LexNameList pids = new LexNameList();

		for (APatternListTypePair tp : node.getParameterPatterns())
			for (PPattern p : tp.getPatterns())
				for (PDefinition def : p.getDefinitions())
					pids.add(def.getName());

		//Taken from Overture - Needed?
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

		//	COMMENTED AS CONTEXT GENERATES VARIOUS NULL OBJECTS, DUE TO NEW AST...
		//	AS SUCH SAT OBLIG DOESN'T DO MUCH
		//	question.push(new CMLPOOperationDefinitionContext(node, false, node.getStateDefinition()));
			pol.add(new CMLSatisfiabilityObligation(node, node.getStateDefinition(), question));
		//	question.pop();			
		}
		 
		return pol;
	}
	 
	/**
	 * Explicit operations - CML does not reuse Overture operations
	 */	 
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

		// if operation has a precondition, dispatch for PO checking
		if (node.getPrecondition() != null){
			pol.addAll(node.getPrecondition().apply(parentPOG, question));
		}
		// if  operation has a precondition, dispatch for PO checking
		// and generate OperationPostConditionObligation
		if (node.getPostcondition() != null){
			pol.addAll(node.getPostcondition().apply(parentPOG, question));
			pol.add(new CMLOperationPostConditionObligation(node, question));
		}
		
		// dispatch operation body for PO checking
		pol.addAll(node.getBody().apply(parentPOG, question));

//		/*
//		 * Taken from Overture POG - not currently working
//		 */
//		
//		if (node.getIsConstructor() && node.getClassDefinition() != null
//				&& node.getClassDefinition().getInvariant() != null){
//			pol.add(new CMLStateInvariantObligation(node, question));
//		}
//		
//		if (!node.getIsConstructor()
//				&& !TypeComparator.isSubType(node.getActualResult(), node.getType().getResult())){
//			pol.add(new SubTypeObligation(node, node.getActualResult(), question));
//		}
   	
		return pol;
	}
	
    // Typechecker will eventually solve resolve these. For now, we hack past it.
    @Override
    public ProofObligationList caseAUnresolvedPathExp(AUnresolvedPathExp node,
	    POContextStack question) throws AnalysisException {
	return new CMLProofObligationList();
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
    
 }