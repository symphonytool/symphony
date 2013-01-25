package eu.compassresearch.core.analysis.pog.visitors;


import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.pog.obligation.POContextStack;
import org.overture.pog.obligation.ProofObligationList;

import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AAlphabetisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AChannelRenamingProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AExternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismReplicatedProcess;
import eu.compassresearch.ast.process.AHidingProcess;
import eu.compassresearch.ast.process.AInstantiationProcess;
import eu.compassresearch.ast.process.AInterleavingProcess;
import eu.compassresearch.ast.process.AInterleavingReplicatedProcess;
import eu.compassresearch.ast.process.AInternalChoiceProcess;
import eu.compassresearch.ast.process.AInternalChoiceReplicatedProcess;
import eu.compassresearch.ast.process.AInterruptProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASequentialCompositionReplicatedProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.analysis.pog.obligations.CMLNonZeroTimeObligation;
import eu.compassresearch.core.analysis.pog.obligations.CMLProofObligationList;

@SuppressWarnings("serial")
public class POGProcessVisitor extends QuestionAnswerCMLAdaptor<POContextStack, ProofObligationList>
{
    private ProofObligationGenerator parentPOG;
    
    public POGProcessVisitor(ProofObligationGenerator parent)
    {
        this.parentPOG = parent;
    }
    
    @Override
    public ProofObligationList defaultPProcess(PProcess node,
	    POContextStack question) throws AnalysisException {
    	System.out.println("PProcess: " + node.toString());
		return new ProofObligationList();
    }
    
    @Override
    public CMLProofObligationList caseAActionProcess(AActionProcess node,POContextStack question) throws AnalysisException{
    	System.out.println("A AActionProcess: " + node.toString());
    	CMLProofObligationList pol = new CMLProofObligationList();
    	
    	//Print the separate parts to screen
    	System.out.println("A StateProcess: " + node.toString());
    	System.out.println("A StateProcess defintion paragraphs: " + node.getDefinitionParagraphs());
    	System.out.println("A StateProcess action: " + node.getAction());
    	

    	LinkedList<PDefinition> pdef = node.getDefinitionParagraphs();
    	for (PDefinition def : pdef) {
    		System.out.println(def.toString());
    		
    		pol.addAll(def.apply(parentPOG, question));
    	}
    	
    	// RWL Line below does not apply in the updated Ast.
    	// System.out.println(node.getProcessDefinition());
    	System.out.println(pdef);
    	System.out.println(node.getAction());
		return pol;
    }
    
    
	@Override
	public ProofObligationList caseAInternalChoiceProcess(AInternalChoiceProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AInternalChoiceProcess: " + node.toString());

		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		
		pol.addAll(left.apply(parentPOG, question));
		//TO DO: Consider internal choice POs
		pol.addAll(right.apply(parentPOG, question));
				
		return pol;
	}


	@Override
	public ProofObligationList caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AUntimedTimeoutProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		
		pol.addAll(left.apply(parentPOG, question));
		//TO DO: Consider untimed timeout POs
		pol.addAll(right.apply(parentPOG, question));
				
		return pol;
	}


	@Override
	public ProofObligationList caseATimeoutProcess(ATimeoutProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A ATimeoutProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PExp timedExp = node.getTimeoutExpression();
		PProcess right = node.getRight();
		PProcess left = node.getLeft();


		pol.addAll(left.apply(parentPOG, question));
		//dispatch time expr to POG checker
		pol.addAll(timedExp.apply(parentPOG,question));
		//check for Non-Zero time obligation and dispatch exp for POG checking
		pol.add(new CMLNonZeroTimeObligation(timedExp, question));
		//TO DO: Consider other timeout POs
		pol.addAll(right.apply(parentPOG, question));

		return pol;
	}


	@Override
	public ProofObligationList caseASynchronousParallelismReplicatedProcess(
			ASynchronousParallelismReplicatedProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A ASynchronousParallelismReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		
		//TO DO: Ensure this it the correct way to handle synch parallel POs
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;
	}


	@Override
	public ProofObligationList caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, POContextStack question)
			throws AnalysisException {

    	System.out.println("A ASequentialCompositionReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		
		//TO DO: Ensure this it the correct way to handle synch seq comp repl POs
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;
	}


	@Override
	public ProofObligationList caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, POContextStack question)
			throws AnalysisException {

    	System.out.println("A AInternalChoiceReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		
		//TO DO: Ensure this it the correct way to handle synch int choice repl POs
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;
	}


	@Override
	public ProofObligationList caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AGeneralisedParallelismReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

		PVarsetExpression csExp = node.getChansetExpression();
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		//TO DO: Ensure this it the correct way to handle gen par repl POs
		pol.addAll(csExp.apply(parentPOG,question));
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;		
	}


	@Override
	public ProofObligationList caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, POContextStack question)
			throws AnalysisException {

    	System.out.println("A AExternalChoiceReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		//TO DO: Ensure this it the correct way to ext choice repl POs
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;		
	}



	@Override
	public ProofObligationList caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AAlphabetisedParallelismReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

		PVarsetExpression csExp = node.getChansetExpression();
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();
		
		//TO DO: Ensure this it the correct way to handle alpha par repl POs
		pol.addAll(csExp.apply(parentPOG,question));
		for(PSingleDeclaration decl : repdecl)
		{
			pol.addAll(decl.apply(parentPOG,question));
		}
		
		pol.addAll(proc.apply(parentPOG,question));
							
		return pol;		
	}





	@Override
	public ProofObligationList caseAInterruptProcess(AInterruptProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AInterruptProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		
		pol.addAll(left.apply(parentPOG, question));
		//TO DO: Consider interrupt  POs
		pol.addAll(right.apply(parentPOG, question));
				
		return pol;
	}





	@Override
	public ProofObligationList caseAInterleavingProcess(AInterleavingProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AInterleavingProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		
		pol.addAll(left.apply(parentPOG, question));
		//TO DO: Consider interleaving choice POs
		pol.addAll(right.apply(parentPOG, question));
				
		return pol;
	}





	@Override
	public ProofObligationList caseAInstantiationProcess(AInstantiationProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AInstantiationProcess: " + node.toString());
		//TO DO: Consider instantiation POs
		CMLProofObligationList pol = new CMLProofObligationList();
				
		return pol;
	}





	@Override
	public ProofObligationList caseAHidingProcess(AHidingProcess node, POContextStack question)
			throws AnalysisException {

    	System.out.println("A AHidingProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
	
		PProcess left = node.getLeft();
		PVarsetExpression csExp = node.getChansetExpression();


		pol.addAll(left.apply(parentPOG, question));
		//dispatch chanset expr to POG checker
		pol.addAll(csExp.apply(parentPOG,question));
		//TO DO: Consider timeout POs

		return pol;
	}





	@Override
	public ProofObligationList caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, POContextStack question)
					throws AnalysisException {

    	System.out.println("A AGeneralisedParallelismProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
	
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		PVarsetExpression csExp = node.getChansetExpression();

		pol.addAll(left.apply(parentPOG, question));
		//dispatch chanset expr to POG checker
		pol.addAll(csExp.apply(parentPOG,question));
		//TO DO: Consider gen par POs
		pol.addAll(right.apply(parentPOG, question));

		return pol;
	}





	@Override
	public ProofObligationList caseAExternalChoiceProcess(AExternalChoiceProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AExternalChoiceProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		pol.addAll(left.apply(parentPOG, question));
		//TO DO: Consider ext choice POs
		pol.addAll(right.apply(parentPOG, question));

		return pol;
	}





	@Override
	public ProofObligationList caseAChannelRenamingProcess(AChannelRenamingProcess node,
			POContextStack question) throws AnalysisException {

    	System.out.println("A AChannelRenamingProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess process = node.getProcess();
		SRenameChannelExp renameExp = node.getRenameExpression();

		pol.addAll(process.apply(parentPOG,question));
		pol.addAll(renameExp.apply(parentPOG,question));
		//TO DO: Consider chan rename POs
		
		return pol;
	}



	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, POContextStack question)
					throws AnalysisException {

    	System.out.println("A AAlphabetisedParallelismProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		pol.addAll(left.apply(parentPOG, question));

		PProcess right = node.getRight();
		pol.addAll(right.apply(parentPOG, question));

		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		pol.addAll(leftChanSet.apply(parentPOG, question));
		
		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		pol.addAll(rightChanSet.apply(parentPOG, question));

		//TO DO: Consider alpha parallel POs
						
		return pol;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ProofObligationList caseAStartDeadlineProcess(AStartDeadlineProcess node,
			POContextStack question)
					throws AnalysisException {

    	System.out.println("A AStartDeadlineProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PExp timeExp = node.getExpression();

		pol.addAll(left.apply(parentPOG, question));
		pol.addAll(timeExp.apply(parentPOG, question));

		//TO DO: Consider start deadline  POs
		
		return pol;
	}



	@Override
	public ProofObligationList caseAEndDeadlineProcess(AEndDeadlineProcess node,
			POContextStack question) throws AnalysisException {
		//TODO RWL Make this complete
    	System.out.println("A AEndDeadlineProcess: " + node.toString());

		CMLProofObligationList pol = new CMLProofObligationList();
				
		return pol;
	}

	@Override
	public ProofObligationList caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, POContextStack question)
					throws AnalysisException {

    	System.out.println("A AInterleavingReplicatedProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
				
		LinkedList<PSingleDeclaration> declarations = node
				.getReplicationDeclaration();
		PProcess replicatedProcess = node.getReplicatedProcess();

		for (PSingleDeclaration singleDecl : declarations) {
			pol.addAll(singleDecl.apply(parentPOG, question));
		}

		pol.addAll(replicatedProcess.apply(parentPOG,question));

		// TODO: consider interleaving replicated POs	
		return pol;
	}


	@Override
	public ProofObligationList caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node,
			POContextStack question)
					throws AnalysisException {
    	System.out.println("A ASynchronousParallelismProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

		pol.addAll(node.getLeft().apply(parentPOG, question));
		pol.addAll(node.getRight().apply(parentPOG, question));

		// TODO: consider synchronous POs				
		return pol;
	}

	@Override
	public ProofObligationList caseASequentialCompositionProcess(
			ASequentialCompositionProcess node,
			POContextStack question)
					throws AnalysisException {

    	System.out.println("A ASequentialCompositionProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();

		pol.addAll(node.getLeft().apply(parentPOG, question));
		pol.addAll(node.getRight().apply(parentPOG, question));

		// TODO: consider Sequential POs

				
		return pol;
	}

	@Override
	public ProofObligationList caseAReferenceProcess(AReferenceProcess node,
			POContextStack question)
					throws AnalysisException {
		

    	System.out.println("A AReferenceProcess: " + node.toString());
		// TODO: consider Reference POs
		
		CMLProofObligationList pol = new CMLProofObligationList();
				
		return pol;
	}



	

    

	@Override
	public ProofObligationList caseATimedInterruptProcess(ATimedInterruptProcess node,
			POContextStack question) throws AnalysisException
	{
    	System.out.println("A ATimedInterruptProcess: " + node.toString());
		CMLProofObligationList pol = new CMLProofObligationList();
		
		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		PExp timeExp = node.getTimeExpression();

		//Send left-hand side
		pol.addAll(left.apply(parentPOG, question));
		//check for Non-Zero time obligation and dispatch exp for POG checking
		pol.add(new CMLNonZeroTimeObligation(timeExp, question));
		pol.addAll(timeExp.apply(parentPOG, question));
		//Send right-hand side
		pol.addAll(right.apply(parentPOG, question));

		return pol;
	}

}
