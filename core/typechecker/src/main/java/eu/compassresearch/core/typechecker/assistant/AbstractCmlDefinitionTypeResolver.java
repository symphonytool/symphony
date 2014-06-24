package eu.compassresearch.core.typechecker.assistant;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.utilities.DefinitionTypeResolver;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
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
import eu.compassresearch.ast.analysis.intf.ICMLQuestion;
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
import eu.compassresearch.ast.statements.AUnresolvedObjectDesignator;
import eu.compassresearch.ast.statements.AUnresolvedStateDesignator;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.ast.types.AProcessType;

public class AbstractCmlDefinitionTypeResolver extends DefinitionTypeResolver implements ICMLQuestion<DefinitionTypeResolver.NewQuestion>
{

	public AbstractCmlDefinitionTypeResolver(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public void caseFile(File node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseInputStream(InputStream node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAFileSource(AFileSource node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATcpStreamSource(ATcpStreamSource node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInputStreamSource(AInputStreamSource node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAActionDefinition(AActionDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChansetDefinition(AChansetDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseANamesetDefinition(ANamesetDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAProcessDefinition(AProcessDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChannelDefinition(AChannelDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInitialDefinition(AInitialDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAActionClassDefinition(AActionClassDefinition node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseABracketedExp(ABracketedExp node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATupleSelectExp(ATupleSelectExp node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUnresolvedPathExp(AUnresolvedPathExp node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseANameChannelExp(ANameChannelExp node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAEnumVarsetExpression(AEnumVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseACompVarsetExpression(ACompVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAFatCompVarsetExpression(AFatCompVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASubVOpVarsetExpression(ASubVOpVarsetExpression node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAProcessType(AProcessType node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChannelType(AChannelType node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseALogicalAccess(ALogicalAccess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseARenamePair(ARenamePair node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAActionProcess(AActionProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAExternalChoiceProcess(AExternalChoiceProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInternalChoiceProcess(AInternalChoiceProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterleavingProcess(AInterleavingProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterruptProcess(AInterruptProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATimedInterruptProcess(ATimedInterruptProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATimeoutProcess(ATimeoutProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAHidingProcess(AHidingProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASkipProcess(ASkipProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAStartDeadlineProcess(AStartDeadlineProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAEndDeadlineProcess(AEndDeadlineProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInstantiationProcess(AInstantiationProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAReferenceProcess(AReferenceProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChannelRenamingProcess(AChannelRenamingProcess node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASkipAction(ASkipAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAStopAction(AStopAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChaosAction(AChaosAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseADivAction(ADivAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAWaitAction(AWaitAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseACommunicationAction(ACommunicationAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAGuardedAction(AGuardedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASequentialCompositionAction(
			ASequentialCompositionAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAExternalChoiceAction(AExternalChoiceAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInternalChoiceAction(AInternalChoiceAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterruptAction(AInterruptAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATimedInterruptAction(ATimedInterruptAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseATimeoutAction(ATimeoutAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAHidingAction(AHidingAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAStartDeadlineAction(AStartDeadlineAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAEndDeadlineAction(AEndDeadlineAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAChannelRenamingAction(AChannelRenamingAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAMuAction(AMuAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAParametrisedAction(AParametrisedAction node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAStmAction(AStmAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAReferenceAction(AReferenceAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterleavingParallelAction(
			AInterleavingParallelAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAReadCommunicationParameter(
			AReadCommunicationParameter node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAValParametrisation(AValParametrisation node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAResParametrisation(AResParametrisation node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAVresParametrisation(AVresParametrisation node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUnresolvedStateDesignator(AUnresolvedStateDesignator node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAUnresolvedObjectDesignator(
			AUnresolvedObjectDesignator node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAActionStm(AActionStm node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseANewStm(ANewStm node, NewQuestion question)
			throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAIfNonDeterministicStm(AIfNonDeterministicStm node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseAAltNonDeterministicStm(AAltNonDeterministicStm node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

	@Override
	public void caseADoNonDeterministicStm(ADoNonDeterministicStm node,
			NewQuestion question) throws AnalysisException
	{
		
		
	}

}
