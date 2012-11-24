package eu.compassresearch.core.typechecker;

import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ANatOneNumericBasicType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings({ "deprecation", "serial" })
class TCTypeVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	private final CmlTypeChecker parentChecker;
	private final TypeIssueHandler issueHandler;

	@Override
	public PType caseATypesParagraphDefinition(ATypesParagraphDefinition node,
			TypeCheckInfo question) throws AnalysisException {

		LinkedList<ATypeDefinition> types = node.getTypes();
		for (ATypeDefinition td : types) {
			td.apply(parentChecker, question);
		}

		return new ATypeParagraphType();
	}

	public TCTypeVisitor(CmlTypeChecker parentTypeChecker,
			TypeIssueHandler issueHandler) {
		this.parentChecker = parentTypeChecker;
		this.issueHandler = issueHandler;
	}

	@Override
	public PType caseABooleanBasicType(ABooleanBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseACharBasicType(ACharBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseATokenBasicType(ATokenBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAIntNumericBasicType(AIntNumericBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseANatOneNumericBasicType(ANatOneNumericBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseANatNumericBasicType(ANatNumericBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseARationalNumericBasicType(ARationalNumericBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseARealNumericBasicType(ARealNumericBasicType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseANamedInvariantType(ANamedInvariantType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		eu.compassresearch.core.typechecker.TypeCheckInfo newQ = (eu.compassresearch.core.typechecker.TypeCheckInfo) question;
		PType lookedupType = newQ.lookupType(node.getName());
		if (lookedupType == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
							.customizeMessage(node.getName().name));
			lookedupType = new AErrorType(node.getLocation(), true);
		}

		return lookedupType;
	}

	@Override
	public PType caseASeqSeqType(ASeqSeqType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		PType innerType = node.getSeqof().apply(parentChecker, question);
		if (innerType == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getSeqof().toString()));
			return new AErrorType();
		}
		return node;
	}

	@Override
	public PType caseASetType(ASetType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PType setOfType = node.getSetof().apply(this, question);

		if (setOfType == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getSetof().toString()));
			return new AErrorType();
		}

		node.setSetof(setOfType);

		return node;
	}

	@Override
	public PType caseAMapMapType(AMapMapType node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {

		PType fromType = node.getFrom().apply(parentChecker, question);
		if (fromType == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getFrom().toString()));
			return new AErrorType();
		}

		PType toType = node.getTo().apply(parentChecker, question);
		if (toType == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.COULD_NOT_DETERMINE_TYPE
							.customizeMessage(node.getTo().toString()));
			return new AErrorType();
		}

		node.setFrom(fromType);

		node.setTo(toType);

		return node;
	}

}
