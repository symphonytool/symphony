package eu.compassresearch.core.typechecker.assistant;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameList;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.AAltNonDeterministicStm;
import org.overture.ast.statements.ADoNonDeterministicStm;
import org.overture.ast.statements.AIfNonDeterministicStm;
import org.overture.ast.statements.ANewStm;
import org.overture.ast.statements.AUnresolvedStateDesignator;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.utilities.VariableNameCollector;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
import eu.compassresearch.ast.actions.AChaosAction;
import eu.compassresearch.ast.actions.ACommonInterleavingReplicatedAction;
import eu.compassresearch.ast.actions.ACommunicationAction;
import eu.compassresearch.ast.actions.ADeclarationInstantiatedAction;
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
import eu.compassresearch.ast.actions.ASynchronousParallelismParallelAction;
import eu.compassresearch.ast.actions.ASynchronousParallelismReplicatedAction;
import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.ATimeoutAction;
import eu.compassresearch.ast.actions.AUntimedTimeoutAction;
import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.ast.actions.AVresParametrisation;
import eu.compassresearch.ast.actions.AWaitAction;
import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.ast.analysis.intf.ICMLAnswer;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChannelsDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AChansetsDefinition;
import eu.compassresearch.ast.definitions.AFunctionsDefinition;
import eu.compassresearch.ast.definitions.AInitialDefinition;
import eu.compassresearch.ast.definitions.ALogicalAccess;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.ANamesetsDefinition;
import eu.compassresearch.ast.definitions.AOperationsDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypesDefinition;
import eu.compassresearch.ast.definitions.AValuesDefinition;
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
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.ATcpStreamSource;
import eu.compassresearch.ast.types.AActionParagraphType;
import eu.compassresearch.ast.types.AActionType;
import eu.compassresearch.ast.types.ABindType;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AChannelsParagraphType;
import eu.compassresearch.ast.types.AChansetParagraphType;
import eu.compassresearch.ast.types.AChansetType;
import eu.compassresearch.ast.types.AErrorType;
import eu.compassresearch.ast.types.AFunctionParagraphType;
import eu.compassresearch.ast.types.AInitialParagraphType;
import eu.compassresearch.ast.types.ANamesetType;
import eu.compassresearch.ast.types.ANamesetsType;
import eu.compassresearch.ast.types.AOperationParagraphType;
import eu.compassresearch.ast.types.AParagraphType;
import eu.compassresearch.ast.types.AProcessParagraphType;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.ast.types.ASourceType;
import eu.compassresearch.ast.types.AStateParagraphType;
import eu.compassresearch.ast.types.AStatementType;
import eu.compassresearch.ast.types.ATypeParagraphType;
import eu.compassresearch.ast.types.AValueParagraphType;
import eu.compassresearch.ast.types.AVarsetExpressionType;

public abstract class AbstractCmlVariableNameCollector extends
		VariableNameCollector implements ICMLAnswer<LexNameList>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractCmlVariableNameCollector(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public LexNameList caseFile(File node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseInputStream(InputStream node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAFileSource(AFileSource node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATcpStreamSource(ATcpStreamSource node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInputStreamSource(AInputStreamSource node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATypeSingleDeclaration(ATypeSingleDeclaration node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelsDefinition(AChannelsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChansetsDefinition(AChansetsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANamesetsDefinition(ANamesetsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionsDefinition(AActionsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAOperationsDefinition(AOperationsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseABracketedExp(ABracketedExp node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATupleSelectExp(ATupleSelectExp node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAUnresolvedPathExp(AUnresolvedPathExp node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANameChannelExp(ANameChannelExp node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAEnumVarsetExpression(AEnumVarsetExpression node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseACompVarsetExpression(ACompVarsetExpression node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAFatCompVarsetExpression(
			AFatCompVarsetExpression node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAUnionVOpVarsetExpression(
			AUnionVOpVarsetExpression node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterVOpVarsetExpression(
			AInterVOpVarsetExpression node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASubVOpVarsetExpression(ASubVOpVarsetExpression node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStatementType(AStatementType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAProcessType(AProcessType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAErrorType(AErrorType node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseABindType(ABindType node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAProcessParagraphType(AProcessParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChansetParagraphType(AChansetParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelsParagraphType(AChannelsParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionParagraphType(AActionParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAValueParagraphType(AValueParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAFunctionParagraphType(AFunctionParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATypeParagraphType(ATypeParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAOperationParagraphType(AOperationParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStateParagraphType(AStateParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASourceType(ASourceType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelType(AChannelType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChansetType(AChansetType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANamesetType(ANamesetType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANamesetsType(ANamesetsType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInitialParagraphType(AInitialParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionType(AActionType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAVarsetExpressionType(AVarsetExpressionType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAParagraphType(AParagraphType node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseALogicalAccess(ALogicalAccess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseARenamePair(ARenamePair node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionProcess(AActionProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASequentialCompositionProcess(
			ASequentialCompositionProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAExternalChoiceProcess(AExternalChoiceProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInternalChoiceProcess(AInternalChoiceProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterleavingProcess(AInterleavingProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterruptProcess(AInterruptProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATimedInterruptProcess(ATimedInterruptProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATimeoutProcess(ATimeoutProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAHidingProcess(AHidingProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASkipProcess(ASkipProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStartDeadlineProcess(AStartDeadlineProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAEndDeadlineProcess(AEndDeadlineProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInstantiationProcess(AInstantiationProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAReferenceProcess(AReferenceProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelRenamingProcess(AChannelRenamingProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASynchronousParallelismReplicatedProcess(
			ASynchronousParallelismReplicatedProcess node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASkipAction(ASkipAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStopAction(AStopAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChaosAction(AChaosAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseADivAction(ADivAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAWaitAction(AWaitAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseACommunicationAction(ACommunicationAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAGuardedAction(AGuardedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAExternalChoiceAction(AExternalChoiceAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInternalChoiceAction(AInternalChoiceAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterruptAction(AInterruptAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATimedInterruptAction(ATimedInterruptAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAUntimedTimeoutAction(AUntimedTimeoutAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseATimeoutAction(ATimeoutAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAHidingAction(AHidingAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStartDeadlineAction(AStartDeadlineAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAEndDeadlineAction(AEndDeadlineAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAChannelRenamingAction(AChannelRenamingAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAMuAction(AMuAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAParametrisedAction(AParametrisedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAStmAction(AStmAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAReferenceAction(AReferenceAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterleavingParallelAction(
			AInterleavingParallelAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseADeclarationInstantiatedAction(
			ADeclarationInstantiatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAReadCommunicationParameter(
			AReadCommunicationParameter node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseASignalCommunicationParameter(
			ASignalCommunicationParameter node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAValParametrisation(AValParametrisation node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAResParametrisation(AResParametrisation node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAVresParametrisation(AVresParametrisation node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAActionStm(AActionStm node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseANewStm(ANewStm node) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAIfNonDeterministicStm(AIfNonDeterministicStm node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseAAltNonDeterministicStm(AAltNonDeterministicStm node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LexNameList caseADoNonDeterministicStm(ADoNonDeterministicStm node)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
