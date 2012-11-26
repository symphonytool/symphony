package eu.compassresearch.core.analysis.pog.visitors;

//POG-related imports
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOContextStack;
import eu.compassresearch.core.analysis.pog.obligations.CMLPOFunctionDefinitionContext;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@SuppressWarnings("serial")
public class POGDeclAndDefVisitor extends

QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList> {

    // Errors and other things are recorded on this guy
    private ProofObligationGenerator parentPOG;

    public POGDeclAndDefVisitor(ProofObligationGenerator parent) {
	this.parentPOG = parent;
    }

    @Override
    public CMLProofObligationList defaultPDeclaration(PDeclaration node,
	    POContextStack question) throws AnalysisException {
	System.out
		.println("Reached POGDeclAndDefVisitor - defaultPDeclaration");
	System.out.println(node.getClass());
	return new CMLProofObligationList();
    }

    @Override
    public CMLProofObligationList defaultPDefinition(PDefinition node,
	    POContextStack question) throws AnalysisException {
	System.out.println("Reached POGDeclAndDefVisitor - defaultPDefinition");
	System.out.println(node.getClass());
	return new CMLProofObligationList();
    }

    @Override
    public ProofObligationList caseAClassParagraphDefinition(
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
    
    
    @Override
    public ProofObligationList caseAProcessParagraphDefinition(
	    AProcessParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("- caseAProcessParagraphDefinition - ");

	AProcessDefinition pdef = node.getProcessDefinition();
	return pdef.apply(this, question);
    }

    @Override
    public ProofObligationList caseAProcessDefinition(
	    AProcessDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("- caseAProcessDefinition - ");

	System.out.println("----------***----------");
	System.out.println(node.toString());

	// NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
	// LinkedList<> lstate = node.getLocalState();
	// for ( s : lstate)
	// {
	// System.out.println("----------***----------");
	// CMLProofObligationList td = s.apply(this, question);
	// System.out.println("----------***----------");
	// }

	PProcess pdef = node.getProcess();
	System.out.println("----------***----------");
	ProofObligationList pol = pdef.apply(this, question);
	System.out.println(pdef.toString());
	System.out.println("----------***----------");

	// CMLProofObligationList td = d.apply(this, question);
	System.out.println("----------***----------");

	return pol;
    }

    /**
     * 
     * VDM ELEMENTS
     * 
     */
    @Override
    public CMLProofObligationList caseATypesParagraphDefinition(
	    ATypesParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("- caseATypesProcessDefinition -");
	CMLProofObligationList td = new CMLProofObligationList();
	for (PDefinition def : node.getTypes()) {
	    System.out.println("----------***----------");
	    td.addAll(def.apply(this, question));
	    System.out.println("----------***----------");
	}

	return td;
    }

    @Override
    public CMLProofObligationList caseATypeDefinition(ATypeDefinition node,
	    POContextStack question) throws AnalysisException {
	System.out.println("- caseATypeDefinition -");

	System.out.println(node.toString());
	CMLProofObligationList list = new CMLProofObligationList();

	return list;
    }

    @Override
    public CMLProofObligationList caseAChansetParagraphDefinition(
	    AChansetParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("- caseAChansetParagraphDefinition -");

	LinkedList<AChansetDefinition> subNodes = node.getChansets();
	CMLProofObligationList td = new CMLProofObligationList();
	for (AChansetDefinition d : subNodes) {
	    System.out.println("----------***----------");
	    td.addAll(d.apply(this, question));
	    System.out.println("----------***----------");
	}

	return td;
    }

    @Override
    public CMLProofObligationList caseAChansetDefinition(
	    AChansetDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("- caseAChansetDefinition -");

	System.out.println(node.toString());
	CMLProofObligationList list = new CMLProofObligationList();

	return list;
    }

    //
    // @Override
    // public ProofObligationList caseAProcessParagraphDefinition(
    // AProcessParagraphDefinition node, POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseAProcessParagraphDefinition - ");
    //
    // AProcessDefinition pdef = node.getProcessDefinition();
    // ProofObligationList pol = pdef.apply(this, question);
    //
    // return new ProofObligationList();
    // }
    //
    // @Override
    // public ProofObligationList caseAProcessDefinition(
    // AProcessDefinition node, POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseAProcessDefinition - ");
    //
    // System.out.println("----------***----------");
    // System.out.println(node.toString());
    //
    // //NEED TO WORK OUT CONTENTS OF PROCESS AND DISPATCH RELEVANT PARTS
    // //LinkedList<> lstate = node.getLocalState();
    // //for ( s : lstate)
    // //{
    // // System.out.println("----------***----------");
    // // ProofObligationList td = s.apply(this, question);
    // // System.out.println("----------***----------");
    // //}
    //
    // PProcess pdef = node.getProcess();
    // System.out.println("----------***----------");
    // ProofObligationList pol = pdef.apply(this, question);
    // System.out.println(pdef.toString());
    // System.out.println("----------***----------");
    //
    //
    //
    // //ProofObligationList td = d.apply(this, question);
    // System.out.println("----------***----------");
    //
    // return new ProofObligationList();
    // }
    // /**
    // *
    // * VDM ELEMENTS
    // *
    // */
    // @Override
    // public ProofObligationList
    // caseATypesParagraphDefinition(ATypesParagraphDefinition node,
    // POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseATypesProcessDefinition -");
    //
    // for (PDefinition def : node.getTypes())
    // {
    // System.out.println("----------***----------");
    // ProofObligationList td = def.apply(this, question);
    // System.out.println("----------***----------");
    // }
    //
    // return new ProofObligationList();
    // }
    //
    // @Override
    // public ProofObligationList caseATypeDefinition(ATypeDefinition node,
    // POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseATypeDefinition -");
    //
    // System.out.println(node.toString());
    // ProofObligationList list = new ProofObligationList();
    //
    // return list;
    // }
    //
    //
    //
    //
    //
    // @Override
    // public ProofObligationList caseAChansetParagraphDefinition(
    // AChansetParagraphDefinition node, POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseAChansetParagraphDefinition -");
    //
    // LinkedList<AChansetDefinition> subNodes = node.getChansets();
    // for (AChansetDefinition d : subNodes)
    // {
    // System.out.println("----------***----------");
    // ProofObligationList td = d.apply(this, question);
    // System.out.println("----------***----------");
    // }
    //
    // return new ProofObligationList();
    // }
    //
    // @Override
    // public ProofObligationList caseAChansetDefinition(
    // AChansetDefinition node, POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseAChansetDefinition -");
    //
    // System.out.println(node.toString());
    // ProofObligationList list = new ProofObligationList();
    //
    // return list;
    // }
    //
    //
    // @Override
    // public ProofObligationList caseAChannelParagraphDefinition(
    // AChannelParagraphDefinition node, POContextStack question)
    // throws AnalysisException
    // {
    // System.out.println("- caseAChannelParagraphDefinition -");
    //
    //
    // LinkedList<AChannelNameDeclaration> cns = node.getChannelNames();
    // for (AChannelNameDeclaration decl : cns)
    // {
    //
    // System.out.println("----------***----------");
    // System.out.println(decl.toString());
    // CMLProofObligationList typeBack = decl.apply(this, question);
    // System.out.println("----------***----------");
    // }
    //
    // return new CMLProofObligationList();
    // }

    @Override
    public CMLProofObligationList caseAValueParagraphDefinition(
	    AValueParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("------");
	System.out
		.println("Reached POGDeclAndDefVisitor - caseAValueParagraphDefinition");
	CMLProofObligationList defType = new CMLProofObligationList();
	LinkedList<PDefinition> list = node.getValueDefinitions();
	for (PDefinition def : list) {
	    System.out.println("In Value Paragraph Loop: " + def.toString());
	   defType.addAll(def.apply(parentPOG, question));
	}

	return defType;
    }

    @Override
    public CMLProofObligationList caseAValueDefinition(AValueDefinition node,
	    POContextStack question) throws AnalysisException {
	System.out.println("------");
	System.out
		.println("Reached POGDeclAndDefVisitor - caseAValueDefinition");

	return new CMLProofObligationList();
    }

    @Override
    public CMLProofObligationList caseAFunctionParagraphDefinition(
	    AFunctionParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("-caseAFunctionParagraphDefinition-");
	CMLProofObligationList obligations = new CMLProofObligationList();

	for (PDefinition def : node.getFunctionDefinitions()) {
	    obligations.addAll(def.apply(parentPOG, question));
	}

	return obligations;

    }

    @Override
    public CMLProofObligationList caseAExplicitFunctionDefinition(
	    AExplicitFunctionDefinition node, POContextStack question)
	    throws AnalysisException {

	CMLProofObligationList obligations = new CMLProofObligationList();

	System.out.println("----------***----------");
	System.out.println("caseAExplicitFunctionDefinition");
	System.out.println(node.toString());
	System.out.println("----------***----------");

	// do proof obligations for the pre-condition
	PExp precondition = node.getPrecondition();
	if (precondition != null) {
	    question.push(new CMLPOFunctionDefinitionContext(node, false));
	    obligations.addAll(precondition.apply(parentPOG, question));
	    question.pop();
	}

	// do proof obligations for the post-condition
	PExp postcondition = node.getPostcondition();
	if (postcondition != null) {
	    // question.push(new POFunctionDefinitionContext(node, false));
	    // obligations.add(new FuncPostConditionObligation(node, question));
	    // question.push(new POFunctionResultContext(node));
	    // obligations.addAll(postcondition.apply(parentPOG, question));
	    // question.pop();
	    // question.pop();
	}

	// do proof obligations for the function body
	question.push(new CMLPOFunctionDefinitionContext(node, true));
	PExp body = node.getBody();
	int sizeBefore = question.size();
	obligations.addAll(body.apply(parentPOG, question));
	assert sizeBefore <= question.size();

	// do proof obligation for the return type
	// if (node.getIsUndefined()
	// || !TypeComparator.isSubType(node.getActualResult(),
	// node.getExpectedResult()))
	// {
	//
	// obligations.add(new SubTypeObligation(node, node.getExpectedResult(),
	// node.getActualResult(), question));
	// }
	question.pop();

	return obligations;
    }

    @Override
    public CMLProofObligationList caseAImplicitFunctionDefinition(
	    AImplicitFunctionDefinition node, POContextStack question)
	    throws AnalysisException {
	CMLProofObligationList obligations = new CMLProofObligationList();

	// if implicit function has a precondition, dispatch for PO checking
	if (node.getPrecondition() != null) {
	    obligations.addAll(node.getPrecondition()
		    .apply(parentPOG, question));
	}

	// if has a postcondition, should generate SatisfiabilityObligation
	if (node.getPostcondition() != null) {
	    System.out.println("POSTCONDITION");
	    // obligations.add(new SatisfiabilityObligation(node, question));
	    //
	    // question.push(new POFunctionResultContext(node));
	    // obligations.addAll(node.getPostcondition().apply(parentPOG,
	    // question));
	    // question.pop();
	}
	return obligations;
    }

    @Override
    public CMLProofObligationList caseAOperationParagraphDefinition(
	    AOperationParagraphDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("------");
	System.out
		.println("Reached POGDeclAndDefVisitor - caseAFunctionParagraphDefinition");
	CMLProofObligationList pol = new CMLProofObligationList();
	for (PDefinition def : node.getOperations()) {
	    System.out
		    .println("In Operation Paragraph Loop: " + def.toString());
	   pol.addAll(def.apply(this, question));
	}

	return pol;
    }

    @Override
    public CMLProofObligationList caseAExplicitOperationDefinition(
	    AExplicitOperationDefinition node, POContextStack question)
	    throws AnalysisException {
	System.out.println("------");
	System.out
		.println("Reached POGDeclAndDefVisitor - caseAExplicitOperationDefinition");

	return new CMLProofObligationList();
    }

    @Override
    public CMLProofObligationList caseAActionParagraphDefinition(
	    AActionParagraphDefinition node, POContextStack question)
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

}