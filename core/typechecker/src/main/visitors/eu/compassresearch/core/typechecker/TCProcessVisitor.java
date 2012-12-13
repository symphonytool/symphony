package eu.compassresearch.core.typechecker;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.SSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.process.AAlphabetisedParallelismProcess;
import eu.compassresearch.ast.process.AEndDeadlineProcess;
import eu.compassresearch.ast.process.AInterleavingReplicatedProcess;
import eu.compassresearch.ast.process.AReferenceProcess;
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.typechecker.api.TypeComparator;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings("serial")
public class TCProcessVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	private VanillaCmlTypeChecker parentChecker;
	private TypeIssueHandler issueHandler;
	private TypeComparator typeComparator;

	@Override
	public PType defaultPAction(PAction node, TypeCheckInfo question)
			throws AnalysisException {
		return new AActionType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, TypeCheckInfo question)
			throws AnalysisException {

		PProcess left = node.getLeft();
		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + left));

		PProcess right = node.getRight();
		PType rightType = right.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
			return issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + right));

		PExp leftChanSet = node.getLeftChansetExpression();
		PType leftChanSetType = leftChanSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftChanSetType))
			return issueHandler.addTypeError(leftChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + leftChanSet));

		PExp rightChanSet = node.getRightChansetExpression();
		PType rightChanSetType = rightChanSet.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightChanSetType))
			return issueHandler.addTypeError(rightChanSet,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage("" + rightChanSet));

		return new AProcessType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAStartDeadlineProcess(AStartDeadlineProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PProcess left = node.getLeft();

		PExp timeExp = node.getExpression();

		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(left + ""));

		PType timeExpType = timeExp.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(timeExpType))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(timeExp + ""));

		if (!typeComparator.isSubType(timeExpType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(timeExp,
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
							.customizeMessage(node + "", timeExpType + ""));

		return new AProcessType(node.getLocation(), true);
	}

	
	
	@Override
	public PType caseAEndDeadlineProcess(AEndDeadlineProcess node,
			TypeCheckInfo question) throws AnalysisException {
		//TODO RWL Make this complete
		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException {

		LinkedList<SSingleDeclaration> declarations = node
				.getReplicationDeclaration();

		for (SSingleDeclaration singleDecl : declarations) {
			PType singleDeclType = singleDecl.apply(parentChecker, question);
			if (!TCDeclAndDefVisitor.successfulType(singleDeclType))
				return issueHandler.addTypeError(singleDecl,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(singleDecl + ""));
		}

		PProcess replicatedProcess = node.getReplicatedProcess();

		PType replicatedProcessType = replicatedProcess.apply(parentChecker,
				question);
		if (!TCDeclAndDefVisitor.successfulType(replicatedProcessType))
			return issueHandler.addTypeError(replicatedProcess,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(replicatedProcess + ""));

		return new AProcessType(node.getLocation(), true);
	}

	public TCProcessVisitor(VanillaCmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler, TypeComparator typeComparator) {
		this.parentChecker = parentChecker;
		this.issueHandler = issueHandler;
		this.typeComparator = typeComparator;
	}

	@Override
	public PType caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		node.getLeft().apply(this, question);
		node.getRight().apply(this, question);

		// TODO: missing marker on processes

		return new AProcessType();
	}

	@Override
	public PType caseASequentialCompositionProcess(
			ASequentialCompositionProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		node.getLeft().apply(this, question);
		node.getRight().apply(this, question);

		// TODO: missing marker on processes

		return new AProcessType();
	}

	@Override
	public PType caseAReferenceProcess(AReferenceProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		eu.compassresearch.core.typechecker.CmlTypeCheckInfo newQ = (eu.compassresearch.core.typechecker.CmlTypeCheckInfo) question;
		PDefinition processDef = newQ.lookupVariable(node.getProcessName());

		if (processDef == null) {
			return issueHandler.addTypeError(
					node,
					TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node
							.getProcessName() + ""));
		}

		if (!(processDef instanceof AProcessParagraphDefinition))
			return issueHandler.addTypeError(processDef,
					TypeErrorMessages.EXPECTED_PROCESS_DEFINITION
							.customizeMessage(node.getProcessName() + ""));
		node.setProcessDefinition(((AProcessParagraphDefinition) processDef)
				.getProcessDefinition());

		return new AProcessType();
	}

	
	
	@Override
	public PType caseAStateProcess(AStateProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		// Set the process def for this node
		node.setProcessDefinition(node.getAncestor(AProcessDefinition.class));

		// Type check all the paragraph definitions
		for (PDefinition def : node.getDefinitionParagraphs()) {
			PType type = def.apply(this.parentChecker, question);

			if (!TCDeclAndDefVisitor.successfulType(type))
				return issueHandler.addTypeError(def,
						TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
								.customizeMessage(def.getName() + ""));
		}
		
		question.contextSet(eu.compassresearch.core.typechecker.CmlTypeCheckInfo.class, (eu.compassresearch.core.typechecker.CmlTypeCheckInfo)question);
		PType actionType = node.getAction().apply(this.parentChecker, question);
		question.contextRem(eu.compassresearch.core.typechecker.CmlTypeCheckInfo.class);
		if (!TCDeclAndDefVisitor.successfulType(actionType))
			return issueHandler.addTypeError(node.getAction(),
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getAction() + ""));
		return new AProcessType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException {
		PProcess left = node.getLeft();
		PType leftType = left.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(leftType))
			return issueHandler.addTypeError(left,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(left + ""));

		PProcess right = node.getRight();
		PType rightType = right.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(rightType))
			return issueHandler.addTypeError(right,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(right + ""));

		PType expType = node.getTimeExpression().apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(expType))
			return issueHandler.addTypeError(node.getTimeExpression(),
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getTimeExpression() + ""));
		if (!typeComparator.isSubType(expType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(node.getTimeExpression(),
					TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT
							.customizeMessage(node.getTimeExpression() + ""));

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseATimedInterruptAction(ATimedInterruptAction node,
			TypeCheckInfo question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseATimedInterruptAction(node, question);
	}

}
