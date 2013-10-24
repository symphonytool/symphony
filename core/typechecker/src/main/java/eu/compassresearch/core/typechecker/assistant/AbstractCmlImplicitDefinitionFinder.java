package eu.compassresearch.core.typechecker.assistant;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.AAltNonDeterministicStm;
import org.overture.ast.statements.ADoNonDeterministicStm;
import org.overture.ast.statements.AIfNonDeterministicStm;
import org.overture.ast.statements.ANewStm;
import org.overture.ast.statements.AUnresolvedStateDesignator;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.utilities.ImplicitDefinitionFinder;

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
import eu.compassresearch.ast.analysis.intf.ICMLQuestion;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AActionsDefinition;
import eu.compassresearch.ast.definitions.AChannelNameDefinition;
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

public abstract class AbstractCmlImplicitDefinitionFinder extends
		ImplicitDefinitionFinder implements IQuestion<Environment>,
		ICMLQuestion<Environment>
{

	public AbstractCmlImplicitDefinitionFinder(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4947466785754178741L;

	@Override
	public void caseFile(File node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseInputStream(InputStream node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAFileSource(AFileSource node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATcpStreamSource(ATcpStreamSource node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInputStreamSource(AInputStreamSource node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionDefinition(AActionDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChansetDefinition(AChansetDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANamesetDefinition(ANamesetDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAProcessDefinition(AProcessDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelNameDefinition(AChannelNameDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelsDefinition(AChannelsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChansetsDefinition(AChansetsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANamesetsDefinition(ANamesetsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionsDefinition(AActionsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATypesDefinition(ATypesDefinition node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAOperationsDefinition(AOperationsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAFunctionsDefinition(AFunctionsDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAValuesDefinition(AValuesDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInitialDefinition(AInitialDefinition node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseABracketedExp(ABracketedExp node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATupleSelectExp(ATupleSelectExp node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAUnresolvedPathExp(AUnresolvedPathExp node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANameChannelExp(ANameChannelExp node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAEnumVarsetExpression(AEnumVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseACompVarsetExpression(ACompVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAFatCompVarsetExpression(AFatCompVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASubVOpVarsetExpression(ASubVOpVarsetExpression node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStatementType(AStatementType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAProcessType(AProcessType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAErrorType(AErrorType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseABindType(ABindType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAProcessParagraphType(AProcessParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChansetParagraphType(AChansetParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelsParagraphType(AChannelsParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionParagraphType(AActionParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAValueParagraphType(AValueParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAFunctionParagraphType(AFunctionParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATypeParagraphType(ATypeParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAOperationParagraphType(AOperationParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStateParagraphType(AStateParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASourceType(ASourceType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelType(AChannelType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChansetType(AChansetType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANamesetType(ANamesetType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANamesetsType(ANamesetsType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInitialParagraphType(AInitialParagraphType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionType(AActionType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAVarsetExpressionType(AVarsetExpressionType node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAParagraphType(AParagraphType node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseALogicalAccess(ALogicalAccess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseARenamePair(ARenamePair node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionProcess(AActionProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAExternalChoiceProcess(AExternalChoiceProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInternalChoiceProcess(AInternalChoiceProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterleavingProcess(AInterleavingProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterruptProcess(AInterruptProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATimedInterruptProcess(ATimedInterruptProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATimeoutProcess(ATimeoutProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAHidingProcess(AHidingProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASkipProcess(ASkipProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStartDeadlineProcess(AStartDeadlineProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAEndDeadlineProcess(AEndDeadlineProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInstantiationProcess(AInstantiationProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAReferenceProcess(AReferenceProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelRenamingProcess(AChannelRenamingProcess node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASynchronousParallelismReplicatedProcess(
			ASynchronousParallelismReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASkipAction(ASkipAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStopAction(AStopAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChaosAction(AChaosAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseADivAction(ADivAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAWaitAction(AWaitAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseACommunicationAction(ACommunicationAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAGuardedAction(AGuardedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAExternalChoiceAction(AExternalChoiceAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInternalChoiceAction(AInternalChoiceAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterruptAction(AInterruptAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATimedInterruptAction(ATimedInterruptAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseATimeoutAction(ATimeoutAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAHidingAction(AHidingAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStartDeadlineAction(AStartDeadlineAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAEndDeadlineAction(AEndDeadlineAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAChannelRenamingAction(AChannelRenamingAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAMuAction(AMuAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAParametrisedAction(AParametrisedAction node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAReferenceAction(AReferenceAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseADeclarationInstantiatedAction(
			ADeclarationInstantiatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAReadCommunicationParameter(
			AReadCommunicationParameter node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAValParametrisation(AValParametrisation node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAResParametrisation(AResParametrisation node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAVresParametrisation(AVresParametrisation node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAActionStm(AActionStm node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAStmAction(AStmAction node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAUnresolvedStateDesignator(AUnresolvedStateDesignator node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseANewStm(ANewStm node, Environment question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAIfNonDeterministicStm(AIfNonDeterministicStm node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseAAltNonDeterministicStm(AAltNonDeterministicStm node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void caseADoNonDeterministicStm(ADoNonDeterministicStm node,
			Environment question) throws AnalysisException
	{
		// TODO Auto-generated method stub

	}
}
