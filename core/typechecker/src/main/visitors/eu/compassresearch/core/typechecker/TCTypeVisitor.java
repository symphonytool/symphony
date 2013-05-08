
package eu.compassresearch.core.typechecker;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.AInMapMapType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.AMapMapType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.ANatOneNumericBasicType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AOptionalType;
import org.overture.ast.types.AParameterType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.AQuoteType;
import org.overture.ast.types.ARationalNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.ATokenBasicType;
import org.overture.ast.types.AUndefinedType;
import org.overture.ast.types.AUnionType;
import org.overture.ast.types.AUnknownType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidReturnType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

@SuppressWarnings({ "deprecation", "serial" })
class TCTypeVisitor extends
QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

	private final VanillaCmlTypeChecker parentChecker;
	private final TypeIssueHandler issueHandler;
	private CmlAssistant assist = new CmlAssistant();

	
	
	
	@Override
	public PType caseAErrorType(AErrorType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseABracketType(ABracketType node, TypeCheckInfo question)
			throws AnalysisException {
		return node.getType();
	}

	@Override
	public PType caseAOperationType(AOperationType node, TypeCheckInfo question)
			throws AnalysisException {
		
		LinkedList<PType> paramTypes = node.getParameters();
		PType resultType = node.getResult();
		
		for(PType paramType : paramTypes)
		{
			PType paramTypeType = paramType.apply(parentChecker , question);
			if (!TCDeclAndDefVisitor.successfulType(paramTypeType))
				return issueHandler.addTypeError(paramType, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+paramType));
		}

		PType resultTypeType = resultType.apply(parentChecker, question);
		if (!TCDeclAndDefVisitor.successfulType(resultTypeType))
			return issueHandler.addTypeError(resultType, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node+""));
		
		return node;
	}

	@Override
	public PType caseAOptionalType(AOptionalType node, TypeCheckInfo question)
			throws AnalysisException {
		
		PType type = node.getType();
		PType typeType = type.apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(typeType))
			return issueHandler.addTypeError(type,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(type+""));
		
		return node;
	}

	@Override
	public PType caseAParameterType(AParameterType node, TypeCheckInfo question)
			throws AnalysisException {
		
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node+""));
		
		PType type = cmlEnv.lookupType(node.getName());
		if (type == null)
			issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node.getName()+""));
		return type;
	}

	@Override
	public PType caseAProductType(AProductType node, TypeCheckInfo question)
			throws AnalysisException {
		
		List<PType> types = node.getTypes();
		for(PType type : types)
		{
			PType typeType = type.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(typeType))
				return issueHandler.addTypeError(type,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(typeType+""));
		}
		return node;
	}

	@Override
	public PType caseAQuoteType(AQuoteType node, TypeCheckInfo question)
			throws AnalysisException {
		
		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node,TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node+""));
		
		return node;
	}

	@Override
	public PType caseAUndefinedType(AUndefinedType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAUnionType(AUnionType node, TypeCheckInfo question)
			throws AnalysisException {
		
		LinkedList<PType> types = node.getTypes();
		for(PType type : types)
		{
			PType typeType = type.apply(parentChecker,question);
			if (!TCDeclAndDefVisitor.successfulType(typeType))
				return issueHandler.addTypeError(node, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""+node));
		}
		
		return node;
	}

	@Override
	public PType caseAUnknownType(AUnknownType node, TypeCheckInfo question)
			throws AnalysisException {
		
		return node;
	}

	@Override
	public PType caseAVoidReturnType(AVoidReturnType node,
			TypeCheckInfo question) throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAVoidType(AVoidType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAInMapMapType(AInMapMapType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseARecordInvariantType(ARecordInvariantType node,
			TypeCheckInfo question) throws AnalysisException {
		return node;
	}

	@Override
	public PType defaultPType(PType node, TypeCheckInfo question)
			throws AnalysisException {
		OvertureRootCMLAdapter adapter = new OvertureRootCMLAdapter(parentChecker, issueHandler);
		return node.apply(adapter,question);
	}

	@Override
	public PType caseAChannelType(AChannelType node, TypeCheckInfo question)
			throws AnalysisException {
		
		if (node.getType() == null)
			return node;
		
		PType typeType = node.getType().apply(parentChecker,question);
		if (!TCDeclAndDefVisitor.successfulType(typeType))
			return issueHandler.addTypeError(node.getType(), TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(node+""));

		return node;
	}

	@Override
	public PType caseAFunctionType(AFunctionType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAClassType(AClassType node, TypeCheckInfo question)
			throws AnalysisException {
		return node;
	}

	@Override
	public PType caseAUnresolvedType(AUnresolvedType node,
			TypeCheckInfo question) throws AnalysisException {

		// is it a globally declared type
		List<SClassDefinition> classes = new LinkedList<SClassDefinition>();
		if (question.env.getEnclosingDefinition() == null)
		{
			eu.compassresearch.core.typechecker.CmlTypeCheckInfo q = (eu.compassresearch.core.typechecker.CmlTypeCheckInfo)question;
			classes.add(q.getGlobalClassDefinitions());

		}
		else
		{
			if (question.env.getEnclosingDefinition() instanceof SClassDefinition)
				classes.add((SClassDefinition) question.env.getEnclosingDefinition());
		}
		PDefinition tDef = SClassDefinitionAssistantTC.findType(classes,
				node.getName());

		if (tDef == null)
		{
			tDef = question.env.findName(node.getName(), NameScope.GLOBAL);
			if (tDef != null)
				return tDef.getType();
		}

		if (tDef == null)
			tDef = question.env.findType(node.getName(), "");

		if (tDef == null)
			tDef = CmlTCUtil.findDefByAllMeans(question, node.getName());

		if (tDef == null)
			tDef = CmlTCUtil.findNearestFunctionOrOperationInEnvironment(node.getName(), question.env);

		if (!(tDef instanceof ATypeDefinition)) {
			return issueHandler.addTypeError(node,
					TypeErrorMessages.EXPECTED_TYPE_DEFINITION
					.customizeMessage(node.getName() + ""));


		}
		return tDef.getType();
	}



	public TCTypeVisitor(VanillaCmlTypeChecker parentTypeChecker,
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

		PType type = node.getType().apply(parentChecker, question);

		if (!TCDeclAndDefVisitor.successfulType(type)) {
			return issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
					.customizeMessage(node.getName().getName()));
		}

		node.setType(type);
		return type;
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
		node.setSeqof(innerType);
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
