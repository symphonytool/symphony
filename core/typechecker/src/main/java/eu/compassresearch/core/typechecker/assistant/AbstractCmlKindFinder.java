package eu.compassresearch.core.typechecker.assistant;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;
import org.overture.typechecker.utilities.KindFinder;

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
import eu.compassresearch.ast.analysis.intf.ICMLAnswer;
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

public class AbstractCmlKindFinder extends KindFinder implements
		ICMLAnswer<String>
{


	public AbstractCmlKindFinder(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public String caseFile(File node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseInputStream(InputStream node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAFileSource(AFileSource node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATcpStreamSource(ATcpStreamSource node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInputStreamSource(AInputStreamSource node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATypeSingleDeclaration(ATypeSingleDeclaration node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInitialDefinition(AInitialDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAActionClassDefinition(AActionClassDefinition node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseABracketedExp(ABracketedExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATupleSelectExp(ATupleSelectExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUnresolvedPathExp(AUnresolvedPathExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseANameChannelExp(ANameChannelExp node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAEnumVarsetExpression(AEnumVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseACompVarsetExpression(ACompVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAFatCompVarsetExpression(AFatCompVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASubVOpVarsetExpression(ASubVOpVarsetExpression node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAProcessType(AProcessType node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseALogicalAccess(ALogicalAccess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseARenamePair(ARenamePair node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAActionProcess(AActionProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASequentialCompositionProcess(
			ASequentialCompositionProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAExternalChoiceProcess(AExternalChoiceProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInternalChoiceProcess(AInternalChoiceProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterleavingProcess(AInterleavingProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterruptProcess(AInterruptProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATimedInterruptProcess(ATimedInterruptProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATimeoutProcess(ATimeoutProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAHidingProcess(AHidingProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASkipProcess(ASkipProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAStartDeadlineProcess(AStartDeadlineProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAEndDeadlineProcess(AEndDeadlineProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInstantiationProcess(AInstantiationProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAReferenceProcess(AReferenceProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChannelRenamingProcess(AChannelRenamingProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASkipAction(ASkipAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAStopAction(AStopAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChaosAction(AChaosAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseADivAction(ADivAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAWaitAction(AWaitAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseACommunicationAction(ACommunicationAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAGuardedAction(AGuardedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASequentialCompositionAction(
			ASequentialCompositionAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAExternalChoiceAction(AExternalChoiceAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInternalChoiceAction(AInternalChoiceAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterruptAction(AInterruptAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATimedInterruptAction(ATimedInterruptAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUntimedTimeoutAction(AUntimedTimeoutAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseATimeoutAction(ATimeoutAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAHidingAction(AHidingAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAStartDeadlineAction(AStartDeadlineAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAEndDeadlineAction(AEndDeadlineAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChannelRenamingAction(AChannelRenamingAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAMuAction(AMuAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAParametrisedAction(AParametrisedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAStmAction(AStmAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAReferenceAction(AReferenceAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterleavingParallelAction(
			AInterleavingParallelAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseACommonInterleavingReplicatedAction(
			ACommonInterleavingReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAReadCommunicationParameter(
			AReadCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseASignalCommunicationParameter(
			ASignalCommunicationParameter node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAValParametrisation(AValParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAResParametrisation(AResParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAVresParametrisation(AVresParametrisation node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUnresolvedStateDesignator(AUnresolvedStateDesignator node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAUnresolvedObjectDesignator(
			AUnresolvedObjectDesignator node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAActionStm(AActionStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseANewStm(ANewStm node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAIfNonDeterministicStm(AIfNonDeterministicStm node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAAltNonDeterministicStm(AAltNonDeterministicStm node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseADoNonDeterministicStm(ADoNonDeterministicStm node)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseACallAction(ACallAction node) throws AnalysisException
	{

		return null;
	}

	@Override
	public String caseAChannelType(AChannelType node) throws AnalysisException
	{
		return null;
	}

}
