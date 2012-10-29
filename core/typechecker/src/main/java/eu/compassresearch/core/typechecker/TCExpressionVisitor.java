package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.ABusClassDefinition;
import org.overture.ast.definitions.AClassClassDefinition;
import org.overture.ast.definitions.AClassInvariantDefinition;
import org.overture.ast.definitions.ACpuClassDefinition;
import org.overture.ast.definitions.AEqualsDefinition;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AExternalDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AImportedDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.AMultiBindListDefinition;
import org.overture.ast.definitions.AMutexSyncDefinition;
import org.overture.ast.definitions.ANamedTraceDefinition;
import org.overture.ast.definitions.APerSyncDefinition;
import org.overture.ast.definitions.APrivateAccess;
import org.overture.ast.definitions.AProtectedAccess;
import org.overture.ast.definitions.APublicAccess;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.ASystemClassDefinition;
import org.overture.ast.definitions.AThreadDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.traces.AApplyExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.ABracketedExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.AConcurrentExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.AInstanceTraceDefinition;
import org.overture.ast.definitions.traces.ALetBeStBindingTraceDefinition;
import org.overture.ast.definitions.traces.ALetDefBindingTraceDefinition;
import org.overture.ast.definitions.traces.ARepeatTraceDefinition;
import org.overture.ast.definitions.traces.ATraceDefinitionTerm;
import org.overture.ast.expressions.*;
import org.overture.ast.lex.LexBooleanToken;
import org.overture.ast.lex.LexCharacterToken;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.lex.LexIntegerToken;
import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.lex.LexQuoteToken;
import org.overture.ast.lex.LexRealToken;
import org.overture.ast.lex.LexStringToken;
import org.overture.ast.lex.LexToken;
import org.overture.ast.modules.AAllExport;
import org.overture.ast.modules.AAllImport;
import org.overture.ast.modules.AFromModuleImports;
import org.overture.ast.modules.AFunctionExport;
import org.overture.ast.modules.AFunctionValueImport;
import org.overture.ast.modules.AModuleExports;
import org.overture.ast.modules.AModuleImports;
import org.overture.ast.modules.AModuleModules;
import org.overture.ast.modules.AOperationExport;
import org.overture.ast.modules.AOperationValueImport;
import org.overture.ast.modules.ATypeExport;
import org.overture.ast.modules.ATypeImport;
import org.overture.ast.modules.AValueExport;
import org.overture.ast.modules.AValueValueImport;
import org.overture.ast.node.INode;
import org.overture.ast.node.tokens.TAsync;
import org.overture.ast.node.tokens.TStatic;
import org.overture.ast.patterns.ABooleanPattern;
import org.overture.ast.patterns.ACharacterPattern;
import org.overture.ast.patterns.AConcatenationPattern;
import org.overture.ast.patterns.ADefPatternBind;
import org.overture.ast.patterns.AExpressionPattern;
import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.AIgnorePattern;
import org.overture.ast.patterns.AIntegerPattern;
import org.overture.ast.patterns.AMapPattern;
import org.overture.ast.patterns.AMapUnionPattern;
import org.overture.ast.patterns.AMapletPatternMaplet;
import org.overture.ast.patterns.ANilPattern;
import org.overture.ast.patterns.APatternListTypePair;
import org.overture.ast.patterns.APatternTypePair;
import org.overture.ast.patterns.AQuotePattern;
import org.overture.ast.patterns.ARealPattern;
import org.overture.ast.patterns.ARecordPattern;
import org.overture.ast.patterns.ASeqPattern;
import org.overture.ast.patterns.ASetBind;
import org.overture.ast.patterns.ASetMultipleBind;
import org.overture.ast.patterns.ASetPattern;
import org.overture.ast.patterns.AStringPattern;
import org.overture.ast.patterns.ATuplePattern;
import org.overture.ast.patterns.ATypeBind;
import org.overture.ast.patterns.ATypeMultipleBind;
import org.overture.ast.patterns.AUnionPattern;
import org.overture.ast.statements.AAlwaysStm;
import org.overture.ast.statements.AApplyObjectDesignator;
import org.overture.ast.statements.AAssignmentStm;
import org.overture.ast.statements.AAtomicStm;
import org.overture.ast.statements.ABlockSimpleBlockStm;
import org.overture.ast.statements.ACallObjectStm;
import org.overture.ast.statements.ACallStm;
import org.overture.ast.statements.ACaseAlternativeStm;
import org.overture.ast.statements.ACasesStm;
import org.overture.ast.statements.AClassInvariantStm;
import org.overture.ast.statements.ACyclesStm;
import org.overture.ast.statements.ADefLetDefStm;
import org.overture.ast.statements.ADurationStm;
import org.overture.ast.statements.AElseIfStm;
import org.overture.ast.statements.AErrorCase;
import org.overture.ast.statements.AErrorStm;
import org.overture.ast.statements.AExitStm;
import org.overture.ast.statements.AExternalClause;
import org.overture.ast.statements.AFieldObjectDesignator;
import org.overture.ast.statements.AFieldStateDesignator;
import org.overture.ast.statements.AForAllStm;
import org.overture.ast.statements.AForIndexStm;
import org.overture.ast.statements.AForPatternBindStm;
import org.overture.ast.statements.AIdentifierObjectDesignator;
import org.overture.ast.statements.AIdentifierStateDesignator;
import org.overture.ast.statements.AIfStm;
import org.overture.ast.statements.ALetBeStStm;
import org.overture.ast.statements.AMapSeqStateDesignator;
import org.overture.ast.statements.ANewObjectDesignator;
import org.overture.ast.statements.ANonDeterministicSimpleBlockStm;
import org.overture.ast.statements.ANotYetSpecifiedStm;
import org.overture.ast.statements.APeriodicStm;
import org.overture.ast.statements.AReturnStm;
import org.overture.ast.statements.ASelfObjectDesignator;
import org.overture.ast.statements.ASkipStm;
import org.overture.ast.statements.ASpecificationStm;
import org.overture.ast.statements.AStartStm;
import org.overture.ast.statements.ASubclassResponsibilityStm;
import org.overture.ast.statements.ATixeStm;
import org.overture.ast.statements.ATixeStmtAlternative;
import org.overture.ast.statements.ATrapStm;
import org.overture.ast.statements.AWhileStm;
import org.overture.ast.typechecker.ClassDefinitionSettings;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.typechecker.Pass;
import org.overture.ast.types.AAccessSpecifierAccessSpecifier;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ABracketType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AClassType;
import org.overture.ast.types.AFieldField;
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
import org.overture.ast.types.ASeq1SeqType;
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
import org.overture.ast.util.ClonableFile;
import org.overture.ast.util.ClonableString;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeChecker;
import org.overture.typechecker.visitor.TypeCheckVisitor;
import org.overture.typechecker.visitor.TypeCheckerExpVisitor;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AAssignmentCallStatementAction;
import eu.compassresearch.ast.actions.ABlockStatementAction;
import eu.compassresearch.ast.actions.ACallAction;
import eu.compassresearch.ast.actions.ACallStatementAction;
import eu.compassresearch.ast.actions.ACaseAlternativeAction;
import eu.compassresearch.ast.actions.ACasesStatementAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
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
import eu.compassresearch.ast.actions.AIdentifierParameter;
import eu.compassresearch.ast.actions.AIfStatementAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.ALetStatementAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AMultipleGeneralAssignmentStatementAction;
import eu.compassresearch.ast.actions.ANewStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicAltStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicDoStatementAction;
import eu.compassresearch.ast.actions.ANonDeterministicIfStatementAction;
import eu.compassresearch.ast.actions.ANotYetSpecifiedAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.ARecordParameter;
import eu.compassresearch.ast.actions.AReferenceCommunicationParameter;
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
import eu.compassresearch.ast.actions.ATupleParameter;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWhileStatementAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
import eu.compassresearch.ast.declarations.AChannelNameDeclaration;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionParagraphDefinition;
import eu.compassresearch.ast.definitions.AChannelParagraphDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetParagraphDefinition;
import eu.compassresearch.ast.definitions.AClassParagraphDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AInitialParagraphDefinition;
import eu.compassresearch.ast.definitions.AInvariantDefinition;
import eu.compassresearch.ast.definitions.ALogicalAccess;
import eu.compassresearch.ast.definitions.AOperationParagraphDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.AProcessParagraphDefinition;
import eu.compassresearch.ast.definitions.AStateParagraphDefinition;
import eu.compassresearch.ast.definitions.ATypesParagraphDefinition;
import eu.compassresearch.ast.definitions.AValueParagraphDefinition;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.ACompChansetSetExp;
import eu.compassresearch.ast.expressions.AComprehensionRenameChannelExp;
import eu.compassresearch.ast.expressions.AEnumChansetSetExp;
import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
import eu.compassresearch.ast.expressions.AIdentifierChansetSetExp;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.ANameExp;
import eu.compassresearch.ast.expressions.ATupleSelectExp;
import eu.compassresearch.ast.patterns.AIdentifierTypePair;
import eu.compassresearch.ast.patterns.ARenamePair;
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
import eu.compassresearch.ast.process.ASequentialCompositionProcess;
import eu.compassresearch.ast.process.ASequentialCompositionReplicatedProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.AStateProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.ATcpStreamSource;
import eu.compassresearch.ast.types.AActionParagraphType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChannelsParagraphType;
import eu.compassresearch.ast.types.AChansetParagraphType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.ASourceType;
import eu.compassresearch.ast.types.AStateParagraphType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.core.typechecker.api.CmlTypeChecker;
import eu.compassresearch.core.typechecker.api.TypeCheckQuestion;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class TCExpressionVisitor extends
		QuestionAnswerCMLAdaptor<TypeCheckQuestion, PType> {

	class FixMe extends TypeCheckerExpVisitor implements
			ICMLQuestionAnswer<TypeCheckQuestion, PType> {

		public FixMe(TypeCheckVisitor typeCheckVisitor) {
			super(typeCheckVisitor);
			// TODO Auto-generated constructor stub
		}

		@Override
		public PType caseFile(File node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseInputStream(InputStream node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAFileSource(AFileSource node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATcpStreamSource(ATcpStreamSource node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInputStreamSource(AInputStreamSource node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelNameDeclaration(AChannelNameDeclaration node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExpressionSingleDeclaration(
				AExpressionSingleDeclaration node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAActionDefinition(AActionDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChansetDefinition(AChansetDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInvariantDefinition(AInvariantDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAProcessDefinition(AProcessDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAClassParagraphDefinition(
				AClassParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAProcessParagraphDefinition(
				AProcessParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelParagraphDefinition(
				AChannelParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChansetParagraphDefinition(
				AChansetParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAActionParagraphDefinition(
				AActionParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATypesParagraphDefinition(
				ATypesParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAOperationParagraphDefinition(
				AOperationParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAFunctionParagraphDefinition(
				AFunctionParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAValueParagraphDefinition(
				AValueParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInitialParagraphDefinition(
				AInitialParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStateParagraphDefinition(
				AStateParagraphDefinition node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExplicitOperationDefinition(
				eu.compassresearch.ast.definitions.AExplicitOperationDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAImplicitOperationDefinition(
				eu.compassresearch.ast.definitions.AImplicitOperationDefinition node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseABracketedExp(ABracketedExp node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANameExp(ANameExp node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATupleSelectExp(ATupleSelectExp node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANameChannelExp(ANameChannelExp node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAComprehensionRenameChannelExp(
				AComprehensionRenameChannelExp node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAEnumerationRenameChannelExp(
				AEnumerationRenameChannelExp node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAIdentifierChansetSetExp(
				AIdentifierChansetSetExp node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAEnumChansetSetExp(AEnumChansetSetExp node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACompChansetSetExp(ACompChansetSetExp node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStatementType(AStatementType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAProcessType(AProcessType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAErrorType(AErrorType node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAProcessParagraphType(AProcessParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChansetParagraphType(AChansetParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelsParagraphType(AChannelsParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAActionParagraphType(AActionParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAValueParagraphType(AValueParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAFunctionParagraphType(AFunctionParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATypeParagraphType(ATypeParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAOperationParagraphType(AOperationParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStateParagraphType(AStateParagraphType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASourceType(ASourceType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelType(AChannelType node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseALogicalAccess(ALogicalAccess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAIdentifierTypePair(AIdentifierTypePair node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseARenamePair(ARenamePair node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStateProcess(AStateProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASequentialCompositionProcess(
				ASequentialCompositionProcess node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExternalChoiceProcess(AExternalChoiceProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInternalChoiceProcess(AInternalChoiceProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAGeneralisedParallelismProcess(
				AGeneralisedParallelismProcess node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAAlphabetisedParallelismProcess(
				AAlphabetisedParallelismProcess node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASynchronousParallelismProcess(
				ASynchronousParallelismProcess node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterleavingProcess(AInterleavingProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterruptProcess(AInterruptProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATimeoutProcess(ATimeoutProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAHidingProcess(AHidingProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStartDeadlineProcess(AStartDeadlineProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAEndDeadlineProcess(AEndDeadlineProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInstantiationProcess(AInstantiationProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelRenamingProcess(AChannelRenamingProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASequentialCompositionReplicatedProcess(
				ASequentialCompositionReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExternalChoiceReplicatedProcess(
				AExternalChoiceReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInternalChoiceReplicatedProcess(
				AInternalChoiceReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAGeneralisedParallelismReplicatedProcess(
				AGeneralisedParallelismReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAAlphabetisedParallelismReplicatedProcess(
				AAlphabetisedParallelismReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASynchronousParallelismReplicatedProcess(
				ASynchronousParallelismReplicatedProcess node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterleavingReplicatedProcess(
				AInterleavingReplicatedProcess node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASkipAction(ASkipAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStopAction(AStopAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChaosAction(AChaosAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseADivAction(ADivAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAWaitAction(AWaitAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACommunicationAction(ACommunicationAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAGuardedAction(AGuardedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASequentialCompositionAction(
				ASequentialCompositionAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExternalChoiceAction(AExternalChoiceAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInternalChoiceAction(AInternalChoiceAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterruptAction(AInterruptAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATimedInterruptAction(ATimedInterruptAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATimeoutAction(ATimeoutAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAHidingAction(AHidingAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAStartDeadlineAction(AStartDeadlineAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAEndDeadlineAction(AEndDeadlineAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAChannelRenamingAction(AChannelRenamingAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAMuAction(AMuAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAParametrisedAction(AParametrisedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACallAction(ACallAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASubclassResponsibilityAction(
				ASubclassResponsibilityAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANotYetSpecifiedAction(ANotYetSpecifiedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterleavingParallelAction(
				AInterleavingParallelAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAGeneralisedParallelismParallelAction(
				AGeneralisedParallelismParallelAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAAlphabetisedParallelismParallelAction(
				AAlphabetisedParallelismParallelAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASynchronousParallelismParallelAction(
				ASynchronousParallelismParallelAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASequentialCompositionReplicatedAction(
				ASequentialCompositionReplicatedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAExternalChoiceReplicatedAction(
				AExternalChoiceReplicatedAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInternalChoiceReplicatedAction(
				AInternalChoiceReplicatedAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAInterleavingReplicatedAction(
				AInterleavingReplicatedAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAGeneralisedParallelismReplicatedAction(
				AGeneralisedParallelismReplicatedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAAlphabetisedParallelismReplicatedAction(
				AAlphabetisedParallelismReplicatedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASynchronousParallelismReplicatedAction(
				ASynchronousParallelismReplicatedAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseADeclarationInstantiatedAction(
				ADeclarationInstantiatedAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAParametrisedInstantiatedAction(
				AParametrisedInstantiatedAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAReadCommunicationParameter(
				AReadCommunicationParameter node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAWriteCommunicationParameter(
				AWriteCommunicationParameter node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAReferenceCommunicationParameter(
				AReferenceCommunicationParameter node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAValParametrisation(AValParametrisation node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAResParametrisation(AResParametrisation node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAVresParametrisation(AVresParametrisation node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAIdentifierParameter(AIdentifierParameter node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseATupleParameter(ATupleParameter node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseARecordParameter(ARecordParameter node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseALetStatementAction(ALetStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseABlockStatementAction(ABlockStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANonDeterministicIfStatementAction(
				ANonDeterministicIfStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANonDeterministicAltStatementAction(
				ANonDeterministicAltStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAIfStatementAction(AIfStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAElseIfStatementAction(AElseIfStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACasesStatementAction(ACasesStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACallStatementAction(ACallStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASpecificationStatementAction(
				ASpecificationStatementAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAAssignmentCallStatementAction(
				AAssignmentCallStatementAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAReturnStatementAction(AReturnStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANewStatementAction(ANewStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseANonDeterministicDoStatementAction(
				ANonDeterministicDoStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAForSetStatementAction(AForSetStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAForIndexStatementAction(
				AForIndexStatementAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAForSequenceStatementAction(
				AForSequenceStatementAction node, TypeCheckQuestion question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAWhileStatementAction(AWhileStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseADeclareStatementAction(ADeclareStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseASingleGeneralAssignmentStatementAction(
				ASingleGeneralAssignmentStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseAMultipleGeneralAssignmentStatementAction(
				AMultipleGeneralAssignmentStatementAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PType caseACaseAlternativeAction(ACaseAlternativeAction node,
				TypeCheckQuestion question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509187123701383525L;

	// A parent checker may actually not be necessary on this
	@SuppressWarnings("unused")
	final private CmlTypeChecker parent;
	private final TypeIssueHandler issueHandler;

	public TCExpressionVisitor(CmlTypeChecker parentChecker,
			TypeIssueHandler issueHandler) {
		parent = parentChecker;
		this.issueHandler = issueHandler;
	}

	/**
	 * Translate a CML expression into an equivalent Overture VDM expression and
	 * type check that. Afterwards use the CopyTypesFromOvtToCmlAst to copy over
	 * the Overture VDM types.
	 * 
	 * 
	 * @param node
	 *            - the expression to type check
	 * @param question
	 *            - environmental stuff, green trees whatever
	 * @return A type checked cml expression
	 * @throws AnalysisException
	 *             - if anythings goes wrong that is not just a type error.
	 */

	public PType defaultPExp(PExp node, TypeCheckQuestion question)
			throws AnalysisException {
		org.overture.typechecker.TypeChecker.clearErrors();

		question.updateContextNameToCurrentScope(node);

		INode ovtNode = node;

		TypeCheckerExpVisitor ovtExpVist = new TypeCheckerExpVisitor(
				new TypeCheckVisitor());

		// TODO: Need to figure out how to translate the environment
		org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
				question.getOvertureEnvironment());

		quest.scope = NameScope.NAMES;

		try {
			ovtNode.apply(ovtExpVist, quest);
		} catch (org.overture.ast.analysis.AnalysisException e1) {
			e1.printStackTrace();
		}

		if (org.overture.typechecker.TypeChecker.getErrorCount() > 0) {
			List<VDMError> errorList = TypeChecker.getErrors();
			for (VDMError err : errorList) {
				issueHandler.addTypeError(node, err.toProblemString());
			}
			return new AErrorType(node.getLocation(), true);
		}

		return node.getType();
	}

	public PType caseANameExp(ANameExp node, TypeCheckQuestion question)
			throws AnalysisException {

		PDefinition type = question.lookupVariable(node.getName());
		if (type == null) {
			issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
							.customizeMessage(node.getName() + ""));
			return new AErrorType();
		}

		return type.getType();
	}

}
