package eu.compassresearch.core.analysis.pog.visitors;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.pog.obligation.PONameContext;
import org.overture.pog.pub.IPOContextStack;

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
import eu.compassresearch.core.analysis.pog.obligations.CmlProofObligationList;
import eu.compassresearch.core.analysis.pog.obligations.CmlNonZeroTimeObligation;

@SuppressWarnings("serial")
public class POGProcessVisitor extends
		QuestionAnswerCMLAdaptor<IPOContextStack, CmlProofObligationList> {
	private ProofObligationGenerator parentPOG;

	// FIXME figure out how to dispatch chan and varsets to the apropriate visitors

	public POGProcessVisitor(ProofObligationGenerator parent) {
		this.parentPOG = parent;
	}

	@Override
	public CmlProofObligationList defaultPProcess(PProcess node,
			IPOContextStack question) throws AnalysisException {

		return new CmlProofObligationList();
	}

	@Override
	public CmlProofObligationList caseAActionProcess(AActionProcess node,
			IPOContextStack question) throws AnalysisException {
		CmlProofObligationList pol = new CmlProofObligationList();

		// Get subparts
		LinkedList<PDefinition> pdef = node.getDefinitionParagraphs();
		PAction action = node.getAction();
		
		for (PDefinition def : pdef) {
			PONameContext name = def.apply(new PogNameContextVisitor());
			if (name != null) {
				question.push(def.apply(new PogNameContextVisitor()));
				pol.addAll(def.apply(parentPOG, question));
				question.pop();
			} else {
				pol.addAll(def.apply(parentPOG, question));
			}
		}

		// TODO: Consider any AActionProcess POs
		return pol;
	}

	//At the moment the pog will only visit action process
	// TODO re-enable pog process visits
	
//	@Override
//	public CmlProofObligationList caseAInternalChoiceProcess(
//			AInternalChoiceProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider any AInternalChoiceProcess POs
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAUntimedTimeoutProcess(
//			AUntimedTimeoutProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider any AUntimedTimeoutProcess POs
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseATimeoutProcess(ATimeoutProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PExp timedExp = node.getTimeoutExpression();
//		PProcess right = node.getRight();
//		PProcess left = node.getLeft();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(timedExp.apply(parentPOG, question));
//		// check for Non-Zero time obligation and dispatch exp for POG checking
//		pol.add(new CmlNonZeroTimeObligation(timedExp, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider any ATimeoutProcess POs
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseASynchronousParallelismReplicatedProcess(
//			ASynchronousParallelismReplicatedProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//
//		// TODO: Consider any ASynchronousParallelismReplicatedProcess POs
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseASequentialCompositionReplicatedProcess(
//			ASequentialCompositionReplicatedProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//
//		// TODO: Consider any ASequentialCompositionReplicatedProcess POs
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAInternalChoiceReplicatedProcess(
//			AInternalChoiceReplicatedProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//
//		// TODO: Consider any AInternalChoiceReplicatedProcess POs
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAGeneralisedParallelismReplicatedProcess(
//			AGeneralisedParallelismReplicatedProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PVarsetExpression csExp = node.getChansetExpression();
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		pol.addAll(csExp.apply(parentPOG, question));
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//
//		// TODO: Consider any AExternalChoiceReplicatedProcess POs
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAExternalChoiceReplicatedProcess(
//			AExternalChoiceReplicatedProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//
//		// TODO: Consider any AExternalChoiceReplicatedProcess POs
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAAlphabetisedParallelismReplicatedProcess(
//			AAlphabetisedParallelismReplicatedProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PVarsetExpression csExp = node.getChansetExpression();
//		PProcess proc = node.getReplicatedProcess();
//		LinkedList<PSingleDeclaration> repdecl = node
//				.getReplicationDeclaration();
//
//		pol.addAll(csExp.apply(parentPOG, question));
//		for (PSingleDeclaration decl : repdecl) {
//			// TODO: Ensure this it the correct way to handle declaration POs
//			pol.addAll(decl.apply(parentPOG, question));
//		}
//		// TODO: Consider any AAlphabetisedParallelismReplicatedProcess POs
//
//		pol.addAll(proc.apply(parentPOG, question));
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAInterruptProcess(AInterruptProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider interrupt POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAInterleavingProcess(
//			AInterleavingProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider AInterleavingProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAInstantiationProcess(
//			AInstantiationProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		LinkedList<PExp> args = node.getArgs();
//		PProcess proc = node.getProcess();
//		LinkedList<PParametrisation> paras = node.getParametrisations();
//
//		// TODO: Consider instantiation POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAHidingProcess(AHidingProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PVarsetExpression csExp = node.getChansetExpression();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(csExp.apply(parentPOG, question));
//
//		// TODO: Consider AHidingProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAGeneralisedParallelismProcess(
//			AGeneralisedParallelismProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//		PVarsetExpression csExp = node.getChansetExpression();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(csExp.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider AGeneralisedParallelismProcess POs
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAExternalChoiceProcess(
//			AExternalChoiceProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//
//		// TODO: Consider AExternalChoiceProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAChannelRenamingProcess(
//			AChannelRenamingProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess process = node.getProcess();
//		SRenameChannelExp renameExp = node.getRenameExpression();
//
//		pol.addAll(process.apply(parentPOG, question));
//		pol.addAll(renameExp.apply(parentPOG, question));
//		// TODO: Consider AChannelRenamingProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAAlphabetisedParallelismProcess(
//			AAlphabetisedParallelismProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
//		PVarsetExpression rightChanSet = node.getRightChansetExpression();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(right.apply(parentPOG, question));
//		pol.addAll(leftChanSet.apply(parentPOG, question));
//		pol.addAll(rightChanSet.apply(parentPOG, question));
//
//		// TODO: Consider AAlphabetisedParallelismProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAStartDeadlineProcess(
//			AStartDeadlineProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PExp timeExp = node.getExpression();
//
//		pol.addAll(left.apply(parentPOG, question));
//		pol.addAll(timeExp.apply(parentPOG, question));
//
//		// TODO: Consider AStartDeadlineProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAEndDeadlineProcess(
//			AEndDeadlineProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PExp exp = node.getExpression();
//		PProcess left = node.getLeft();
//
//		// TODO: consider AEndDeadlineProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAInterleavingReplicatedProcess(
//			AInterleavingReplicatedProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		LinkedList<PSingleDeclaration> declarations = node
//				.getReplicationDeclaration();
//		PProcess replicatedProcess = node.getReplicatedProcess();
//
//		for (PSingleDeclaration singleDecl : declarations) {
//			pol.addAll(singleDecl.apply(parentPOG, question));
//		}
//
//		pol.addAll(replicatedProcess.apply(parentPOG, question));
//
//		// TODO: consider AInterleavingReplicatedProcess POs
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseASynchronousParallelismProcess(
//			ASynchronousParallelismProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		pol.addAll(node.getLeft().apply(parentPOG, question));
//		pol.addAll(node.getRight().apply(parentPOG, question));
//
//		// TODO: consider ASynchronousParallelismProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseASequentialCompositionProcess(
//			ASequentialCompositionProcess node, IPOContextStack question)
//			throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		pol.addAll(node.getLeft().apply(parentPOG, question));
//		pol.addAll(node.getRight().apply(parentPOG, question));
//
//		// TODO: consider ASequentialCompositionProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseAReferenceProcess(AReferenceProcess node,
//			IPOContextStack question) throws AnalysisException {
//
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		LinkedList<PExp> args = node.getArgs();
//		AProcessDefinition defn = node.getProcessDefinition();
//		ILexNameToken name = node.getProcessName();
//
//		// TODO: consider AReferenceProcess POs
//
//		return pol;
//	}
//
//	@Override
//	public CmlProofObligationList caseATimedInterruptProcess(
//			ATimedInterruptProcess node, IPOContextStack question)
//			throws AnalysisException {
//		CmlProofObligationList pol = new CmlProofObligationList();
//
//		// Get subparts
//		PProcess left = node.getLeft();
//		PProcess right = node.getRight();
//		PExp timeExp = node.getTimeExpression();
//
//		// Send left-hand side
//		pol.addAll(left.apply(parentPOG, question));
//		// check for Non-Zero time obligation and dispatch exp for POG checking
//		pol.add(new CmlNonZeroTimeObligation(timeExp, question));
//		// TODO: any other ATimedInterruptProcess POs?
//		pol.addAll(timeExp.apply(parentPOG, question));
//		// Send right-hand side
//		pol.addAll(right.apply(parentPOG, question));
//
//		return pol;
//	}
}