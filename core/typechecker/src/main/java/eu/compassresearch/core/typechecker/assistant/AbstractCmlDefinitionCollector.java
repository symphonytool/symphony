package eu.compassresearch.core.typechecker.assistant;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.AAltNonDeterministicStm;
import org.overture.ast.statements.ADoNonDeterministicStm;
import org.overture.ast.statements.AIfNonDeterministicStm;
import org.overture.ast.statements.ANewStm;
import org.overture.ast.statements.AUnresolvedObjectDesignator;
import org.overture.ast.statements.AUnresolvedStateDesignator;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.utilities.DefinitionCollector;

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
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AProcessType;

public class AbstractCmlDefinitionCollector extends DefinitionCollector
		implements ICMLAnswer<List<PDefinition>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractCmlDefinitionCollector(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public List<PDefinition> caseFile(File node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseInputStream(InputStream node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAFileSource(AFileSource node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATcpStreamSource(ATcpStreamSource node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInputStreamSource(AInputStreamSource node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATypeSingleDeclaration(
			ATypeSingleDeclaration node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChannelsDefinition(AChannelsDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChansetsDefinition(AChansetsDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseANamesetsDefinition(ANamesetsDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAActionsDefinition(AActionsDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATypesDefinition(ATypesDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAOperationsDefinition(
			AOperationsDefinition node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAValuesDefinition(AValuesDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAActionClassDefinition(
			AActionClassDefinition node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseABracketedExp(ABracketedExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATupleSelectExp(ATupleSelectExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUnresolvedPathExp(AUnresolvedPathExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseANameChannelExp(ANameChannelExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAEnumVarsetExpression(
			AEnumVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseACompVarsetExpression(
			ACompVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAFatCompVarsetExpression(
			AFatCompVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUnionVOpVarsetExpression(
			AUnionVOpVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterVOpVarsetExpression(
			AInterVOpVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASubVOpVarsetExpression(
			ASubVOpVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAProcessType(AProcessType node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseALogicalAccess(ALogicalAccess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseARenamePair(ARenamePair node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAActionProcess(AActionProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASequentialCompositionProcess(
			ASequentialCompositionProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAExternalChoiceProcess(
			AExternalChoiceProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInternalChoiceProcess(
			AInternalChoiceProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterleavingProcess(AInterleavingProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterruptProcess(AInterruptProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATimedInterruptProcess(
			ATimedInterruptProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUntimedTimeoutProcess(
			AUntimedTimeoutProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATimeoutProcess(ATimeoutProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAHidingProcess(AHidingProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASkipProcess(ASkipProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAStartDeadlineProcess(
			AStartDeadlineProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAEndDeadlineProcess(AEndDeadlineProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInstantiationProcess(
			AInstantiationProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAReferenceProcess(AReferenceProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChannelRenamingProcess(
			AChannelRenamingProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASynchronousParallelismReplicatedProcess(
			ASynchronousParallelismReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASkipAction(ASkipAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAStopAction(AStopAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChaosAction(AChaosAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseADivAction(ADivAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAWaitAction(AWaitAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseACommunicationAction(ACommunicationAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAGuardedAction(AGuardedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAExternalChoiceAction(
			AExternalChoiceAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInternalChoiceAction(
			AInternalChoiceAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterruptAction(AInterruptAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATimedInterruptAction(
			ATimedInterruptAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseATimeoutAction(ATimeoutAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAHidingAction(AHidingAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAStartDeadlineAction(AStartDeadlineAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAEndDeadlineAction(AEndDeadlineAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChannelRenamingAction(
			AChannelRenamingAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAMuAction(AMuAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAParametrisedAction(AParametrisedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAStmAction(AStmAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAReferenceAction(AReferenceAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterleavingParallelAction(
			AInterleavingParallelAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASynchronousParallelismParallelAction(
			ASynchronousParallelismParallelAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASynchronousParallelismReplicatedAction(
			ASynchronousParallelismReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAReadCommunicationParameter(
			AReadCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseASignalCommunicationParameter(
			ASignalCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAValParametrisation(AValParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAResParametrisation(AResParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAVresParametrisation(AVresParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAActionStm(AActionStm node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseANewStm(ANewStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAIfNonDeterministicStm(
			AIfNonDeterministicStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAAltNonDeterministicStm(
			AAltNonDeterministicStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseADoNonDeterministicStm(
			ADoNonDeterministicStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAUnresolvedObjectDesignator(
			AUnresolvedObjectDesignator node) throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseACallAction(ACallAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public List<PDefinition> caseAChannelType(AChannelType node)
			throws AnalysisException
	{
		return null;
	}

}
