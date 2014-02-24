package eu.compassresearch.ide.modelchecker;

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
import org.overture.ast.definitions.traces.AApplyExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.ABracketedExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.AConcurrentExpressionTraceCoreDefinition;
import org.overture.ast.definitions.traces.AInstanceTraceDefinition;
import org.overture.ast.definitions.traces.ALetBeStBindingTraceDefinition;
import org.overture.ast.definitions.traces.ALetDefBindingTraceDefinition;
import org.overture.ast.definitions.traces.ARepeatTraceDefinition;
import org.overture.ast.definitions.traces.ATraceDefinitionTerm;
import org.overture.ast.expressions.*;
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
import org.overture.ast.statements.ALetStm;
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

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ACallAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADivAction;
import eu.compassresearch.ast.actions.AEndDeadlineAction;
import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AExternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AGuardedAction;
import eu.compassresearch.ast.actions.AHidingAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.actions.AInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.AInternalChoiceAction;
import eu.compassresearch.ast.actions.AInternalChoiceReplicatedAction;
import eu.compassresearch.ast.actions.AInterruptAction;
import eu.compassresearch.ast.actions.AMuAction;
import eu.compassresearch.ast.actions.AParametrisedAction;
import eu.compassresearch.ast.actions.AParametrisedInstantiatedAction;
import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.ast.actions.AReferenceAction;
import eu.compassresearch.ast.actions.AResParametrisation;
import eu.compassresearch.ast.actions.ASequentialCompositionAction;
import eu.compassresearch.ast.actions.ASequentialCompositionReplicatedAction;
import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.ast.actions.ASkipAction;
import eu.compassresearch.ast.actions.AStartDeadlineAction;
import eu.compassresearch.ast.actions.AStmAction;
import eu.compassresearch.ast.actions.AStopAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.ast.definitions.ALogicalAccess;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.ABracketedExp;
import eu.compassresearch.ast.expressions.ACompVarsetExpression;
import eu.compassresearch.ast.expressions.AComprehensionRenameChannelExp;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ATupleSelectExp;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnresolvedPathExp;
import eu.compassresearch.ast.patterns.ARenamePair;
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
import eu.compassresearch.ast.process.ASkipProcess;
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.ATcpStreamSource;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.ast.statements.AAltNonDeterministicStm;
import eu.compassresearch.ast.statements.ADoNonDeterministicStm;
import eu.compassresearch.ast.statements.AIfNonDeterministicStm;
import eu.compassresearch.ast.statements.ANewStm;
import eu.compassresearch.ast.statements.AUnresolvedStateDesignator;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ide.core.unsupported.UnsupportedCollector;
import eu.compassresearch.ide.core.unsupported.UnsupportedElementInfo;
import eu.compassresearch.ide.core.unsupported.UnsupportingFeatures;

/**
 * Unsupported element collector for the Model checker feature of CML. This class traverses an AST and returns a list of
 * {@link UnsupportedElementInfo} for nodes which are not supported. The superclass, {@link UnsupportedCollector},
 * considers everything unsupported by default so support must be overriden for all cases.
 * 
 * @author ldc
 */
public class MCUnsupportedCollector extends UnsupportedCollector
{

	/**
	 * Default constructor. Instantiates the collector and identifies it as a
	 * Model Checker unsupport collector.
	 */
	public MCUnsupportedCollector() {
		super(UnsupportingFeatures.MC);

	}

	/*
	 * Add support for elements here. Simply override any cases and set
	 * unsupported to false. LEAVE THE SUPER CALL IN as that is responsible for
	 * traversing the tree
	 */

	@Override
	public void caseAAbsoluteUnaryExp(AAbsoluteUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAbsoluteUnaryExp(node);
	}

	@Override
	public void caseAAccessSpecifierAccessSpecifier(
			AAccessSpecifierAccessSpecifier node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAccessSpecifierAccessSpecifier(node);
	}

	@Override
	public void caseAActionDefinition(AActionDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAActionDefinition(node);
	}

	@Override
	public void caseAActionProcess(AActionProcess node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAActionProcess(node);
	}

	@Override
	public void caseAAllExport(AAllExport node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAllExport(node);
	}

	@Override
	public void caseAAllImport(AAllImport node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAllImport(node);
	}

	@Override
	public void caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAlphabetisedParallelismParallelAction(node);
	}

	@Override
	public void caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAlphabetisedParallelismProcess(node);
	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAlphabetisedParallelismReplicatedAction(node);
	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAlphabetisedParallelismReplicatedProcess(node);
	}

	@Override
	public void caseAAlwaysStm(AAlwaysStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAlwaysStm(node);
	}

	@Override
	public void caseAAndBooleanBinaryExp(AAndBooleanBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAAndBooleanBinaryExp(node);
	}

	@Override
	public void caseAApplyExp(AApplyExp node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAApplyExp(node);
	}

	@Override
	public void caseAApplyExpressionTraceCoreDefinition(
			AApplyExpressionTraceCoreDefinition node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAApplyExpressionTraceCoreDefinition(node);
	}

	@Override
	public void caseAApplyObjectDesignator(AApplyObjectDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAApplyObjectDesignator(node);
	}

	@Override
	public void caseAAssignmentDefinition(AAssignmentDefinition node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAssignmentDefinition(node);
	}

	@Override
	public void caseAAssignmentStm(AAssignmentStm node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAssignmentStm(node);
	}

	@Override
	public void caseAAtomicStm(AAtomicStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAtomicStm(node);
	}

	@Override
	public void caseABlockSimpleBlockStm(ABlockSimpleBlockStm node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseABlockSimpleBlockStm(node);
	}

	@Override
	public void caseABooleanBasicType(ABooleanBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseABooleanBasicType(node);
	}

	@Override
	public void caseABooleanConstExp(ABooleanConstExp node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseABooleanConstExp(node);
	}

	@Override
	public void caseABooleanPattern(ABooleanPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseABooleanPattern(node);
	}

	@Override
	public void caseABracketedExp(ABracketedExp node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseABracketedExp(node);
	}

	@Override
	public void caseABracketedExpressionTraceCoreDefinition(
			ABracketedExpressionTraceCoreDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseABracketedExpressionTraceCoreDefinition(node);
	}

	@Override
	public void caseABracketType(ABracketType node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseABracketType(node);
	}

	@Override
	public void caseABusClassDefinition(ABusClassDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseABusClassDefinition(node);
	}

	@Override
	public void caseACallObjectStm(ACallObjectStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACallObjectStm(node);
	}

	@Override
	public void caseACallStm(ACallStm node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACallStm(node);
	}

	@Override
	public void caseACardinalityUnaryExp(ACardinalityUnaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseACardinalityUnaryExp(node);
	}

	@Override
	public void caseACaseAlternative(ACaseAlternative node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACaseAlternative(node);
	}

	@Override
	public void caseACaseAlternativeStm(ACaseAlternativeStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACaseAlternativeStm(node);
	}

	@Override
	public void caseACasesExp(ACasesExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACasesExp(node);
	}

	@Override
	public void caseACasesStm(ACasesStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACasesStm(node);
	}

	@Override
	public void caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAChannelDefinition(node);
	}

	@Override
	public void caseAChannelRenamingAction(AChannelRenamingAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAChannelRenamingAction(node);
	}

	@Override
	public void caseAChannelRenamingProcess(AChannelRenamingProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAChannelRenamingProcess(node);
	}

	@Override
	public void caseAChannelType(AChannelType arg0) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAChannelType(arg0);
	}
	
	@Override
	public void caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAChansetDefinition(node);
	}

	@Override
	public void caseAChaosAction(AChaosAction node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAChaosAction(node);
	}

	@Override
	public void caseACharacterPattern(ACharacterPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACharacterPattern(node);
	}

	@Override
	public void caseACharBasicType(ACharBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseACharBasicType(node);
	}

	@Override
	public void caseACharLiteralExp(ACharLiteralExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseACharLiteralExp(node);
	}

	@Override
	public void caseAClassInvariantDefinition(AClassInvariantDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAClassInvariantDefinition(node);
	}

	@Override
	public void caseAActionClassDefinition(AActionClassDefinition arg0)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAActionClassDefinition(arg0);
	}

	@Override
	public void caseAActionStm(AActionStm node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAActionStm(node);
	}

	@Override
	public void caseAAltNonDeterministicStm(AAltNonDeterministicStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAAltNonDeterministicStm(node);
	}

	@Override
	public void caseACallAction(ACallAction arg0) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACallAction(arg0);
	}

	@Override
	public void caseADoNonDeterministicStm(ADoNonDeterministicStm arg0)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADoNonDeterministicStm(arg0);
	}

	@Override
	public void caseAIfNonDeterministicStm(AIfNonDeterministicStm arg0)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIfNonDeterministicStm(arg0);
	}

	@Override
	public void caseALetStm(ALetStm arg0) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetStm(arg0);
	}

	@Override
	public void caseANewStm(ANewStm arg0) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANewStm(arg0);
	}

	@Override
	public void caseAStmAction(AStmAction node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStmAction(node);
	}

	@Override
	public void caseAClassInvariantStm(AClassInvariantStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAClassInvariantStm(node);
	}

	@Override
	public void caseAClassType(AClassType node) throws AnalysisException {
	 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAClassType(node);
	}

	@Override
	public void caseAClassClassDefinition(AClassClassDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAClassClassDefinition(node);
	}

	@Override
	public void caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACommonInterleavingReplicatedAction(node);
	}

	@Override
	public void caseACommunicationAction(ACommunicationAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseACommunicationAction(node);
	}

	@Override
	public void caseACompBinaryExp(ACompBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACompBinaryExp(node);
	}

	@Override
	public void caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAComprehensionRenameChannelExp(node);
	}

	@Override
	public void caseACompVarsetExpression(ACompVarsetExpression node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACompVarsetExpression(node);
	}

	@Override
	public void caseAConcatenationPattern(AConcatenationPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAConcatenationPattern(node);
	}

	@Override
	public void caseAConcurrentExpressionTraceCoreDefinition(
			AConcurrentExpressionTraceCoreDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAConcurrentExpressionTraceCoreDefinition(node);
	}

	@Override
	public void caseACpuClassDefinition(ACpuClassDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACpuClassDefinition(node);
	}

	@Override
	public void caseACyclesStm(ACyclesStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseACyclesStm(node);
	}

	@Override
	public void caseADefExp(ADefExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADefExp(node);
	}

	// @Override
	// public void caseADefLetDefStm(ADefLetDefStm node) throws
	// AnalysisException {
	// // unsupported=false;
	// // TODO Uncomment the above line to signal support for this node
	// // Do not remove the super call below.
	// super.caseADefLetDefStm(node);
	// }

	@Override
	public void caseADefPatternBind(ADefPatternBind node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADefPatternBind(node);
	}

	@Override
	public void caseADistConcatUnaryExp(ADistConcatUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADistConcatUnaryExp(node);
	}

	@Override
	public void caseADistIntersectUnaryExp(ADistIntersectUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADistIntersectUnaryExp(node);
	}

	@Override
	public void caseADistMergeUnaryExp(ADistMergeUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADistMergeUnaryExp(node);
	}

	@Override
	public void caseADistUnionUnaryExp(ADistUnionUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADistUnionUnaryExp(node);
	}

	@Override
	public void caseADivAction(ADivAction node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADivAction(node);
	}

	@Override
	public void caseADivideNumericBinaryExp(ADivideNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseADivideNumericBinaryExp(node);
	}

	@Override
	public void caseADivNumericBinaryExp(ADivNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseADivNumericBinaryExp(node);
	}

	@Override
	public void caseADomainResByBinaryExp(ADomainResByBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADomainResByBinaryExp(node);
	}

	@Override
	public void caseADomainResToBinaryExp(ADomainResToBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADomainResToBinaryExp(node);
	}

	@Override
	public void caseADurationStm(ADurationStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseADurationStm(node);
	}

	@Override
	public void caseAElementsUnaryExp(AElementsUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAElementsUnaryExp(node);
	}

	@Override
	public void caseAElseIfExp(AElseIfExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAElseIfExp(node);
	}

	@Override
	public void caseAElseIfStm(AElseIfStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAElseIfStm(node);
	}

	@Override
	public void caseAEndDeadlineAction(AEndDeadlineAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEndDeadlineAction(node);
	}

	@Override
	public void caseAEndDeadlineProcess(AEndDeadlineProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEndDeadlineProcess(node);
	}

	@Override
	public void caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEnumerationRenameChannelExp(node);
	}

	@Override
	public void caseAEnumVarsetExpression(AEnumVarsetExpression node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEnumVarsetExpression(node);
	}

	@Override
	public void caseAEqualsBinaryExp(AEqualsBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAEqualsBinaryExp(node);
	}

	@Override
	public void caseAEqualsDefinition(AEqualsDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEqualsDefinition(node);
	}

	@Override
	public void caseAEquivalentBooleanBinaryExp(AEquivalentBooleanBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAEquivalentBooleanBinaryExp(node);
	}

	@Override
	public void caseAErrorCase(AErrorCase node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAErrorCase(node);
	}

	@Override
	public void caseAErrorStm(AErrorStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAErrorStm(node);
	}

	@Override
	public void caseAExists1Exp(AExists1Exp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExists1Exp(node);
	}

	@Override
	public void caseAExistsExp(AExistsExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExistsExp(node);
	}

	@Override
	public void caseAExitStm(AExitStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExitStm(node);
	}

	@Override
	public void caseAExplicitFunctionDefinition(AExplicitFunctionDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAExplicitFunctionDefinition(node);
	}

	@Override
	public void caseAExplicitOperationDefinition(
			AExplicitOperationDefinition node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExplicitOperationDefinition(node);
	}

	@Override
	public void caseAExpressionPattern(AExpressionPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExpressionPattern(node);
	}

	@Override
	public void caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExpressionSingleDeclaration(node);
	}

	@Override
	public void caseAExternalChoiceAction(AExternalChoiceAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAExternalChoiceAction(node);
	}

	@Override
	public void caseAExternalChoiceProcess(AExternalChoiceProcess node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAExternalChoiceProcess(node);
	}

	@Override
	public void caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExternalChoiceReplicatedAction(node);
	}

	@Override
	public void caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExternalChoiceReplicatedProcess(node);
	}

	@Override
	public void caseAExternalClause(AExternalClause node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExternalClause(node);
	}

	@Override
	public void caseAExternalDefinition(AExternalDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAExternalDefinition(node);
	}

	@Override
	public void caseAFatCompVarsetExpression(AFatCompVarsetExpression node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFatCompVarsetExpression(node);
	}

	@Override
	public void caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFatEnumVarsetExpression(node);
	}

	@Override
	public void caseAFieldExp(AFieldExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFieldExp(node);
	}

	@Override
	public void caseAFieldField(AFieldField node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFieldField(node);
	}

	@Override
	public void caseAFieldNumberExp(AFieldNumberExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFieldNumberExp(node);
	}

	@Override
	public void caseAFieldObjectDesignator(AFieldObjectDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFieldObjectDesignator(node);
	}

	@Override
	public void caseAFieldStateDesignator(AFieldStateDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFieldStateDesignator(node);
	}

	@Override
	public void caseAFileSource(AFileSource node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFileSource(node);
	}

	@Override
	public void caseAFloorUnaryExp(AFloorUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFloorUnaryExp(node);
	}

	@Override
	public void caseAForAllExp(AForAllExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAForAllExp(node);
	}

	@Override
	public void caseAForAllStm(AForAllStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAForAllStm(node);
	}

	@Override
	public void caseAForIndexStm(AForIndexStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAForIndexStm(node);
	}

	@Override
	public void caseAForPatternBindStm(AForPatternBindStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAForPatternBindStm(node);
	}

	@Override
	public void caseAFromModuleImports(AFromModuleImports node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFromModuleImports(node);
	}

	@Override
	public void caseAFuncInstatiationExp(AFuncInstatiationExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFuncInstatiationExp(node);
	}

	@Override
	public void caseAFunctionExport(AFunctionExport node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFunctionExport(node);
	}

	@Override
	public void caseAFunctionType(AFunctionType node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFunctionType(node);
	}

	@Override
	public void caseAFunctionValueImport(AFunctionValueImport node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAFunctionValueImport(node);
	}

	@Override
	public void caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAGeneralisedParallelismParallelAction(node);
	}

	@Override
	public void caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAGeneralisedParallelismProcess(node);
	}

	@Override
	public void caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAGeneralisedParallelismReplicatedAction(node);
	}

	@Override
	public void caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAGeneralisedParallelismReplicatedProcess(node);
	}

	@Override
	public void caseAGreaterEqualNumericBinaryExp(
			AGreaterEqualNumericBinaryExp node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAGreaterEqualNumericBinaryExp(node);
	}

	@Override
	public void caseAGreaterNumericBinaryExp(AGreaterNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAGreaterNumericBinaryExp(node);
	}

	@Override
	public void caseAGuardedAction(AGuardedAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAGuardedAction(node);
	}

	@Override
	public void caseAHeadUnaryExp(AHeadUnaryExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAHeadUnaryExp(node);
	}

	@Override
	public void caseAHidingAction(AHidingAction node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAHidingAction(node);
	}

	@Override
	public void caseAHidingProcess(AHidingProcess node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAHidingProcess(node);
	}

	@Override
	public void caseAHistoryExp(AHistoryExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAHistoryExp(node);
	}

	@Override
	public void caseAIdentifierObjectDesignator(AIdentifierObjectDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIdentifierObjectDesignator(node);
	}

	@Override
	public void caseAIdentifierPattern(AIdentifierPattern node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIdentifierPattern(node);
	}

	@Override
	public void caseAIdentifierStateDesignator(AIdentifierStateDesignator node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIdentifierStateDesignator(node);
	}

	@Override
	public void caseAIdentifierVarsetExpression(AIdentifierVarsetExpression node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIdentifierVarsetExpression(node);
	}

	@Override
	public void caseAIfExp(AIfExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIfExp(node);
	}

	@Override
	public void caseAIfStm(AIfStm node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIfStm(node);
	}

	@Override
	public void caseAIgnorePattern(AIgnorePattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIgnorePattern(node);
	}

	@Override
	public void caseAImplicitFunctionDefinition(AImplicitFunctionDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAImplicitFunctionDefinition(node);
	}

	@Override
	public void caseAImplicitOperationDefinition(
			AImplicitOperationDefinition node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAImplicitOperationDefinition(node);
	}

	@Override
	public void caseAImpliesBooleanBinaryExp(AImpliesBooleanBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAImpliesBooleanBinaryExp(node);
	}

	@Override
	public void caseAImportedDefinition(AImportedDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAImportedDefinition(node);
	}

	@Override
	public void caseAIndicesUnaryExp(AIndicesUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIndicesUnaryExp(node);
	}

	@Override
	public void caseAInheritedDefinition(AInheritedDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInheritedDefinition(node);
	}

	@Override
	public void caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInitialDefinition(node);
	}

	@Override
	public void caseAInMapMapType(AInMapMapType node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInMapMapType(node);
	}

	@Override
	public void caseAInputStreamSource(AInputStreamSource node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInputStreamSource(node);
	}

	@Override
	public void caseAInSetBinaryExp(AInSetBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInSetBinaryExp(node);
	}

	@Override
	public void caseAInstanceTraceDefinition(AInstanceTraceDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInstanceTraceDefinition(node);
	}

	@Override
	public void caseAInstanceVariableDefinition(AInstanceVariableDefinition node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInstanceVariableDefinition(node);
	}

	@Override
	public void caseAInstantiationProcess(AInstantiationProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInstantiationProcess(node);
	}

	@Override
	public void caseAIntegerPattern(AIntegerPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIntegerPattern(node);
	}

	@Override
	public void caseAInterleavingParallelAction(AInterleavingParallelAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInterleavingParallelAction(node);
	}

	@Override
	public void caseAInterleavingProcess(AInterleavingProcess node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInterleavingProcess(node);
	}

	@Override
	public void caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInterleavingReplicatedAction(node);
	}

	@Override
	public void caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInterleavingReplicatedProcess(node);
	}

	@Override
	public void caseAInternalChoiceAction(AInternalChoiceAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInternalChoiceAction(node);
	}

	@Override
	public void caseAInternalChoiceProcess(AInternalChoiceProcess node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAInternalChoiceProcess(node);
	}

	@Override
	public void caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInternalChoiceReplicatedAction(node);
	}

	@Override
	public void caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInternalChoiceReplicatedProcess(node);
	}

	@Override
	public void caseAInterruptAction(AInterruptAction node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInterruptAction(node);
	}

	@Override
	public void caseAInterruptProcess(AInterruptProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInterruptProcess(node);
	}

	@Override
	public void caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAInterVOpVarsetExpression(node);
	}

	@Override
	public void caseAIntLiteralExp(AIntLiteralExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAIntLiteralExp(node);
	}

	@Override
	public void caseAIntNumericBasicType(AIntNumericBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAIntNumericBasicType(node);
	}

	@Override
	public void caseAIotaExp(AIotaExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIotaExp(node);
	}

	@Override
	public void caseAIsExp(AIsExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIsExp(node);
	}

	@Override
	public void caseAIsOfBaseClassExp(AIsOfBaseClassExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIsOfBaseClassExp(node);
	}

	@Override
	public void caseAIsOfClassExp(AIsOfClassExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAIsOfClassExp(node);
	}

	@Override
	public void caseALambdaExp(ALambdaExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALambdaExp(node);
	}

	@Override
	public void caseALenUnaryExp(ALenUnaryExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALenUnaryExp(node);
	}

	@Override
	public void caseALessEqualNumericBinaryExp(ALessEqualNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseALessEqualNumericBinaryExp(node);
	}

	@Override
	public void caseALessNumericBinaryExp(ALessNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseALessNumericBinaryExp(node);
	}

	@Override
	public void caseALetBeStBindingTraceDefinition(
			ALetBeStBindingTraceDefinition node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetBeStBindingTraceDefinition(node);
	}

	@Override
	public void caseALetBeStExp(ALetBeStExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetBeStExp(node);
	}

	@Override
	public void caseALetBeStStm(ALetBeStStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetBeStStm(node);
	}

	@Override
	public void caseALetDefBindingTraceDefinition(
			ALetDefBindingTraceDefinition node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetDefBindingTraceDefinition(node);
	}

	@Override
	public void caseALetDefExp(ALetDefExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALetDefExp(node);
	}

	@Override
	public void caseALocalDefinition(ALocalDefinition node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALocalDefinition(node);
	}

	@Override
	public void caseALogicalAccess(ALogicalAccess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseALogicalAccess(node);
	}

	@Override
	public void caseAMapCompMapExp(AMapCompMapExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapCompMapExp(node);
	}

	@Override
	public void caseAMapDomainUnaryExp(AMapDomainUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapDomainUnaryExp(node);
	}

	@Override
	public void caseAMapEnumMapExp(AMapEnumMapExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapEnumMapExp(node);
	}

	@Override
	public void caseAMapInverseUnaryExp(AMapInverseUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapInverseUnaryExp(node);
	}

	@Override
	public void caseAMapletExp(AMapletExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapletExp(node);
	}

	@Override
	public void caseAMapletPatternMaplet(AMapletPatternMaplet node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapletPatternMaplet(node);
	}

	@Override
	public void caseAMapMapType(AMapMapType node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAMapMapType(node);
	}

	@Override
	public void caseAMapPattern(AMapPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapPattern(node);
	}

	@Override
	public void caseAMapRangeUnaryExp(AMapRangeUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapRangeUnaryExp(node);
	}

	@Override
	public void caseAMapSeqStateDesignator(AMapSeqStateDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapSeqStateDesignator(node);
	}

	@Override
	public void caseAMapUnionBinaryExp(AMapUnionBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapUnionBinaryExp(node);
	}

	@Override
	public void caseAMapUnionPattern(AMapUnionPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMapUnionPattern(node);
	}

	@Override
	public void caseAMkBasicExp(AMkBasicExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMkBasicExp(node);
	}

	@Override
	public void caseAMkTypeExp(AMkTypeExp node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAMkTypeExp(node);
	}

	@Override
	public void caseAModNumericBinaryExp(AModNumericBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAModNumericBinaryExp(node);
	}

	@Override
	public void caseAModuleExports(AModuleExports node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAModuleExports(node);
	}

	@Override
	public void caseAModuleImports(AModuleImports node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAModuleImports(node);
	}

	@Override
	public void caseAModuleModules(AModuleModules node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAModuleModules(node);
	}

	@Override
	public void caseAMuAction(AMuAction node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAMuAction(node);
	}

	@Override
	public void caseAMuExp(AMuExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMuExp(node);
	}

	@Override
	public void caseAMultiBindListDefinition(AMultiBindListDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMultiBindListDefinition(node);
	}

	@Override
	public void caseAMutexSyncDefinition(AMutexSyncDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAMutexSyncDefinition(node);
	}

	@Override
	public void caseANameChannelExp(ANameChannelExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANameChannelExp(node);
	}

	@Override
	public void caseANamedInvariantType(ANamedInvariantType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANamedInvariantType(node);
	}

	@Override
	public void caseANamedTraceDefinition(ANamedTraceDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANamedTraceDefinition(node);
	}

	@Override
	public void caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANamesetDefinition(node);
	}

	@Override
	public void caseANarrowExp(ANarrowExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANarrowExp(node);
	}

	@Override
	public void caseANatNumericBasicType(ANatNumericBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANatNumericBasicType(node);
	}

	@Override
	public void caseANatOneNumericBasicType(ANatOneNumericBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANatOneNumericBasicType(node);
	}

	@Override
	public void caseANewExp(ANewExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANewExp(node);
	}

	@Override
	public void caseANewObjectDesignator(ANewObjectDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANewObjectDesignator(node);
	}

	@Override
	public void caseANilExp(ANilExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANilExp(node);
	}

	@Override
	public void caseANilPattern(ANilPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANilPattern(node);
	}

	@Override
	public void caseANonDeterministicSimpleBlockStm(
			ANonDeterministicSimpleBlockStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANonDeterministicSimpleBlockStm(node);
	}

	@Override
	public void caseANotEqualBinaryExp(ANotEqualBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANotEqualBinaryExp(node);
	}

	@Override
	public void caseANotInSetBinaryExp(ANotInSetBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseANotInSetBinaryExp(node);
	}

	@Override
	public void caseANotUnaryExp(ANotUnaryExp node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANotUnaryExp(node);
	}

	@Override
	public void caseANotYetSpecifiedExp(ANotYetSpecifiedExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANotYetSpecifiedExp(node);
	}

	@Override
	public void caseANotYetSpecifiedStm(ANotYetSpecifiedStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseANotYetSpecifiedStm(node);
	}

	@Override
	public void caseAOperationExport(AOperationExport node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAOperationExport(node);
	}

	@Override
	public void caseAOperationType(AOperationType node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAOperationType(node);
	}

	@Override
	public void caseAOperationValueImport(AOperationValueImport node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAOperationValueImport(node);
	}

	@Override
	public void caseAOptionalType(AOptionalType node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAOptionalType(node);
	}

	@Override
	public void caseAOrBooleanBinaryExp(AOrBooleanBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAOrBooleanBinaryExp(node);
	}

	@Override
	public void caseAParameterType(AParameterType node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAParameterType(node);
	}

	@Override
	public void caseAParametrisedAction(AParametrisedAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAParametrisedAction(node);
	}

	@Override
	public void caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAParametrisedInstantiatedAction(node);
	}

	@Override
	public void caseAPatternListTypePair(APatternListTypePair node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPatternListTypePair(node);
	}

	@Override
	public void caseAPatternTypePair(APatternTypePair node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPatternTypePair(node);
	}

	@Override
	public void caseAPeriodicStm(APeriodicStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPeriodicStm(node);
	}

	@Override
	public void caseAPerSyncDefinition(APerSyncDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPerSyncDefinition(node);
	}

	@Override
	public void caseAPlusNumericBinaryExp(APlusNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAPlusNumericBinaryExp(node);
	}

	@Override
	public void caseAPlusPlusBinaryExp(APlusPlusBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPlusPlusBinaryExp(node);
	}

	@Override
	public void caseAPostOpExp(APostOpExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPostOpExp(node);
	}

	@Override
	public void caseAPowerSetUnaryExp(APowerSetUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPowerSetUnaryExp(node);
	}

	@Override
	public void caseAPreExp(APreExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPreExp(node);
	}

	@Override
	public void caseAPreOpExp(APreOpExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPreOpExp(node);
	}

	@Override
	public void caseAPrivateAccess(APrivateAccess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPrivateAccess(node);
	}

	@Override
	public void caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAProcessDefinition(node);
	}

	@Override
	public void caseAProcessType(AProcessType node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAProcessType(node);
	}

	@Override
	public void caseAProductType(AProductType node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAProductType(node);
	}
	
	@Override
	public void caseAUnresolvedStateDesignator(AUnresolvedStateDesignator node)
			throws AnalysisException {
		unsupported=false;
		// Do not remove the super call below.
		super.caseAUnresolvedStateDesignator(node);
	}

	@Override
	public void caseAProperSubsetBinaryExp(AProperSubsetBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAProperSubsetBinaryExp(node);
	}

	@Override
	public void caseAProtectedAccess(AProtectedAccess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAProtectedAccess(node);
	}

	@Override
	public void caseAPublicAccess(APublicAccess node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAPublicAccess(node);
	}

	@Override
	public void caseAQuoteLiteralExp(AQuoteLiteralExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAQuoteLiteralExp(node);
	}

	@Override
	public void caseAQuotePattern(AQuotePattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAQuotePattern(node);
	}

	@Override
	public void caseAQuoteType(AQuoteType node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAQuoteType(node);
	}

	@Override
	public void caseARangeResByBinaryExp(ARangeResByBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARangeResByBinaryExp(node);
	}

	@Override
	public void caseARangeResToBinaryExp(ARangeResToBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARangeResToBinaryExp(node);
	}

	@Override
	public void caseARationalNumericBasicType(ARationalNumericBasicType node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARationalNumericBasicType(node);
	}

	@Override
	public void caseAReadCommunicationParameter(AReadCommunicationParameter node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAReadCommunicationParameter(node);
	}

	@Override
	public void caseARealLiteralExp(ARealLiteralExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseARealLiteralExp(node);
	}

	@Override
	public void caseARealNumericBasicType(ARealNumericBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseARealNumericBasicType(node);
	}

	@Override
	public void caseARealPattern(ARealPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARealPattern(node);
	}

	@Override
	public void caseARecordInvariantType(ARecordInvariantType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseARecordInvariantType(node);
	}

	@Override
	public void caseARecordModifier(ARecordModifier node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARecordModifier(node);
	}

	@Override
	public void caseARecordPattern(ARecordPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARecordPattern(node);
	}

	@Override
	public void caseAReferenceAction(AReferenceAction node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAReferenceAction(node);
	}

	@Override
	public void caseAReferenceProcess(AReferenceProcess node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAReferenceProcess(node);
	}

	@Override
	public void caseARemNumericBinaryExp(ARemNumericBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARemNumericBinaryExp(node);
	}

	@Override
	public void caseARenamedDefinition(ARenamedDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARenamedDefinition(node);
	}

	@Override
	public void caseARenamePair(ARenamePair node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARenamePair(node);
	}

	@Override
	public void caseARepeatTraceDefinition(ARepeatTraceDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseARepeatTraceDefinition(node);
	}

	@Override
	public void caseAResParametrisation(AResParametrisation node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAResParametrisation(node);
	}

	@Override
	public void caseAReturnStm(AReturnStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAReturnStm(node);
	}

	@Override
	public void caseAReverseUnaryExp(AReverseUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAReverseUnaryExp(node);
	}

	@Override
	public void caseASameBaseClassExp(ASameBaseClassExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASameBaseClassExp(node);
	}

	@Override
	public void caseASameClassExp(ASameClassExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASameClassExp(node);
	}

	@Override
	public void caseASelfExp(ASelfExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASelfExp(node);
	}

	@Override
	public void caseASelfObjectDesignator(ASelfObjectDesignator node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASelfObjectDesignator(node);
	}

	@Override
	public void caseASeq1SeqType(ASeq1SeqType node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASeq1SeqType(node);
	}

	@Override
	public void caseASeqCompSeqExp(ASeqCompSeqExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASeqCompSeqExp(node);
	}

	@Override
	public void caseASeqConcatBinaryExp(ASeqConcatBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASeqConcatBinaryExp(node);
	}

	@Override
	public void caseASeqEnumSeqExp(ASeqEnumSeqExp node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASeqEnumSeqExp(node);
	}

	@Override
	public void caseASeqPattern(ASeqPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASeqPattern(node);
	}

	@Override
	public void caseASeqSeqType(ASeqSeqType node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASeqSeqType(node);
	}

	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASequentialCompositionAction(node);
	}

	@Override
	public void caseASequentialCompositionProcess(
			ASequentialCompositionProcess node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASequentialCompositionProcess(node);
	}

	@Override
	public void caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASequentialCompositionReplicatedAction(node);
	}

	@Override
	public void caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASequentialCompositionReplicatedProcess(node);
	}

	@Override
	public void caseASetBind(ASetBind node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASetBind(node);
	}

	@Override
	public void caseASetCompSetExp(ASetCompSetExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASetCompSetExp(node);
	}

	@Override
	public void caseASetDifferenceBinaryExp(ASetDifferenceBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASetDifferenceBinaryExp(node);
	}

	@Override
	public void caseASetEnumSetExp(ASetEnumSetExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASetEnumSetExp(node);
	}

	@Override
	public void caseASetIntersectBinaryExp(ASetIntersectBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASetIntersectBinaryExp(node);
	}

	@Override
	public void caseASetMultipleBind(ASetMultipleBind node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASetMultipleBind(node);
	}

	@Override
	public void caseASetPattern(ASetPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASetPattern(node);
	}

	@Override
	public void caseASetRangeSetExp(ASetRangeSetExp node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASetRangeSetExp(node);
	}

	@Override
	public void caseASetType(ASetType node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASetType(node);
	}

	@Override
	public void caseASetUnionBinaryExp(ASetUnionBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASetUnionBinaryExp(node);
	}

	@Override
	public void caseASignalCommunicationParameter(
			ASignalCommunicationParameter node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASignalCommunicationParameter(node);
	}

	@Override
	public void caseASkipAction(ASkipAction node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASkipAction(node);
	}

	@Override
	public void caseASkipProcess(ASkipProcess node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASkipProcess(node);
	}

	@Override
	public void caseASkipStm(ASkipStm node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASkipStm(node);
	}

	@Override
	public void caseASpecificationStm(ASpecificationStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASpecificationStm(node);
	}

	@Override
	public void caseAStarStarBinaryExp(AStarStarBinaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStarStarBinaryExp(node);
	}

	@Override
	public void caseAStartDeadlineAction(AStartDeadlineAction node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStartDeadlineAction(node);
	}

	@Override
	public void caseAStartDeadlineProcess(AStartDeadlineProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStartDeadlineProcess(node);
	}

	@Override
	public void caseAStartStm(AStartStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStartStm(node);
	}

	@Override
	public void caseAStateDefinition(AStateDefinition node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStateDefinition(node);
	}

	@Override
	public void caseAStateInitExp(AStateInitExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStateInitExp(node);
	}

	@Override
	public void caseAStopAction(AStopAction node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAStopAction(node);
	}

	@Override
	public void caseAStringLiteralExp(AStringLiteralExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAStringLiteralExp(node);
	}

	@Override
	public void caseAStringPattern(AStringPattern node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAStringPattern(node);
	}

	@Override
	public void caseASubclassResponsibilityExp(ASubclassResponsibilityExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASubclassResponsibilityExp(node);
	}

	@Override
	public void caseASubclassResponsibilityStm(ASubclassResponsibilityStm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASubclassResponsibilityStm(node);
	}

	@Override
	public void caseASubseqExp(ASubseqExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASubseqExp(node);
	}

	@Override
	public void caseASubsetBinaryExp(ASubsetBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASubsetBinaryExp(node);
	}

	@Override
	public void caseASubtractNumericBinaryExp(ASubtractNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseASubtractNumericBinaryExp(node);
	}

	@Override
	public void caseASubVOpVarsetExpression(ASubVOpVarsetExpression node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASubVOpVarsetExpression(node);
	}

	@Override
	public void caseASystemClassDefinition(ASystemClassDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseASystemClassDefinition(node);
	}

	@Override
	public void caseATailUnaryExp(ATailUnaryExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATailUnaryExp(node);
	}

	@Override
	public void caseATcpStreamSource(ATcpStreamSource node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATcpStreamSource(node);
	}

	@Override
	public void caseAThreadDefinition(AThreadDefinition node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAThreadDefinition(node);
	}

	@Override
	public void caseAThreadIdExp(AThreadIdExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAThreadIdExp(node);
	}

	@Override
	public void caseATimedInterruptAction(ATimedInterruptAction node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATimedInterruptAction(node);
	}

	@Override
	public void caseATimedInterruptProcess(ATimedInterruptProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATimedInterruptProcess(node);
	}

	@Override
	public void caseATimeExp(ATimeExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATimeExp(node);
	}

	@Override
	public void caseATimeoutAction(ATimeoutAction node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATimeoutAction(node);
	}

	@Override
	public void caseATimeoutProcess(ATimeoutProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATimeoutProcess(node);
	}

	@Override
	public void caseATimesNumericBinaryExp(ATimesNumericBinaryExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseATimesNumericBinaryExp(node);
	}

	@Override
	public void caseATixeStm(ATixeStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATixeStm(node);
	}

	@Override
	public void caseATixeStmtAlternative(ATixeStmtAlternative node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATixeStmtAlternative(node);
	}

	@Override
	public void caseATokenBasicType(ATokenBasicType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseATokenBasicType(node);
	}

	@Override
	public void caseATraceDefinitionTerm(ATraceDefinitionTerm node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATraceDefinitionTerm(node);
	}

	@Override
	public void caseATrapStm(ATrapStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATrapStm(node);
	}

	@Override
	public void caseATupleExp(ATupleExp node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATupleExp(node);
	}

	@Override
	public void caseATuplePattern(ATuplePattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATuplePattern(node);
	}

	@Override
	public void caseATupleSelectExp(ATupleSelectExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATupleSelectExp(node);
	}

	@Override
	public void caseATypeBind(ATypeBind node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATypeBind(node);
	}

	@Override
	public void caseATypeDefinition(ATypeDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseATypeDefinition(node);
	}

	@Override
	public void caseATypeExport(ATypeExport node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATypeExport(node);
	}

	@Override
	public void caseATypeImport(ATypeImport node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATypeImport(node);
	}

	@Override
	public void caseATypeMultipleBind(ATypeMultipleBind node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATypeMultipleBind(node);
	}

	@Override
	public void caseATypeSingleDeclaration(ATypeSingleDeclaration node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseATypeSingleDeclaration(node);
	}

	@Override
	public void caseAUnaryMinusUnaryExp(AUnaryMinusUnaryExp node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnaryMinusUnaryExp(node);
	}

	@Override
	public void caseAUnaryPlusUnaryExp(AUnaryPlusUnaryExp node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnaryPlusUnaryExp(node);
	}

	@Override
	public void caseAUndefinedExp(AUndefinedExp node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUndefinedExp(node);
	}

	@Override
	public void caseAUndefinedType(AUndefinedType node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAUndefinedType(node);
	}

	@Override
	public void caseAUnionPattern(AUnionPattern node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnionPattern(node);
	}

	@Override
	public void caseAUnionType(AUnionType node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnionType(node);
	}

	@Override
	public void caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnionVOpVarsetExpression(node);
	}

	@Override
	public void caseAUnknownType(AUnknownType node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnknownType(node);
	}

	@Override
	public void caseAUnresolvedPathExp(AUnresolvedPathExp node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAUnresolvedPathExp(node);
	}

	@Override
	public void caseAUnresolvedType(AUnresolvedType node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUnresolvedType(node);
	}

	@Override
	public void caseAUntimedTimeoutAction(AUntimedTimeoutAction node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUntimedTimeoutAction(node);
	}

	@Override
	public void caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUntimedTimeoutProcess(node);
	}

	@Override
	public void caseAUntypedDefinition(AUntypedDefinition node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAUntypedDefinition(node);
	}

	@Override
	public void caseAValParametrisation(AValParametrisation node)
			throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAValParametrisation(node);
	}

	@Override
	public void caseAValueDefinition(AValueDefinition node)
			throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAValueDefinition(node);
	}

	@Override
	public void caseAValueExport(AValueExport node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAValueExport(node);
	}

	@Override
	public void caseAValueValueImport(AValueValueImport node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAValueValueImport(node);
	}

	@Override
	public void caseAVariableExp(AVariableExp node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAVariableExp(node);
	}

	@Override
	public void caseAVoidReturnType(AVoidReturnType node)
			throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAVoidReturnType(node);
	}

	@Override
	public void caseAVoidType(AVoidType node) throws AnalysisException {
		 unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAVoidType(node);
	}

	@Override
	public void caseAVresParametrisation(AVresParametrisation node)
			throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAVresParametrisation(node);
	}

	@Override
	public void caseAWaitAction(AWaitAction node) throws AnalysisException {
		unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAWaitAction(node);
	}

	
	@Override
	public void caseAWhileStm(AWhileStm node) throws AnalysisException {
		// unsupported=false;
		// TODO Uncomment the above line to signal support for this node
		// Do not remove the super call below.
		super.caseAWhileStm(node);
	}

	@Override
	public void caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node) throws AnalysisException {
		unsupported = false;
		// Do not remove the super call below.
		super.caseAWriteCommunicationParameter(node);
	}

	
}
