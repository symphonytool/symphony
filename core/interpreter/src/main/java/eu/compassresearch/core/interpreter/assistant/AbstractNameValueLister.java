package eu.compassresearch.core.interpreter.assistant;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.utilities.definition.NamedValueLister;
import org.overture.interpreter.values.NameValuePairList;

import eu.compassresearch.ast.actions.AAlphabetisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AAlphabetisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AChannelRenamingAction;
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
import eu.compassresearch.ast.analysis.intf.ICMLQuestionAnswer;
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

public class AbstractNameValueLister extends NamedValueLister implements
		ICMLQuestionAnswer<Context, NameValuePairList>
{

	public AbstractNameValueLister(IInterpreterAssistantFactory af)
	{
		super(af);
	}

	@Override
	public NameValuePairList caseFile(File node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseInputStream(InputStream node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAFileSource(AFileSource node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATcpStreamSource(ATcpStreamSource node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInputStreamSource(AInputStreamSource node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATypeSingleDeclaration(
			ATypeSingleDeclaration node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAActionDefinition(AActionDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAChansetDefinition(AChansetDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseANamesetDefinition(ANamesetDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAProcessDefinition(AProcessDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAChannelDefinition(AChannelDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInitialDefinition(AInitialDefinition node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAActionClassDefinition(
			AActionClassDefinition node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseABracketedExp(ABracketedExp node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATupleSelectExp(ATupleSelectExp node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUnresolvedPathExp(AUnresolvedPathExp node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseANameChannelExp(ANameChannelExp node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAEnumVarsetExpression(
			AEnumVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseACompVarsetExpression(
			ACompVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAFatEnumVarsetExpression(
			AFatEnumVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAFatCompVarsetExpression(
			AFatCompVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUnionVOpVarsetExpression(
			AUnionVOpVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterVOpVarsetExpression(
			AInterVOpVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASubVOpVarsetExpression(
			ASubVOpVarsetExpression node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAProcessType(AProcessType node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAChannelType(AChannelType node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseALogicalAccess(ALogicalAccess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseARenamePair(ARenamePair node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAActionProcess(AActionProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASequentialCompositionProcess(
			ASequentialCompositionProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAExternalChoiceProcess(
			AExternalChoiceProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInternalChoiceProcess(
			AInternalChoiceProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterleavingProcess(
			AInterleavingProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterruptProcess(AInterruptProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATimedInterruptProcess(
			ATimedInterruptProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUntimedTimeoutProcess(
			AUntimedTimeoutProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATimeoutProcess(ATimeoutProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAHidingProcess(AHidingProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASkipProcess(ASkipProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAStartDeadlineProcess(
			AStartDeadlineProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAEndDeadlineProcess(AEndDeadlineProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInstantiationProcess(
			AInstantiationProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAReferenceProcess(AReferenceProcess node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAChannelRenamingProcess(
			AChannelRenamingProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASkipAction(ASkipAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAStopAction(AStopAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseADivAction(ADivAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAWaitAction(AWaitAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseACommunicationAction(
			ACommunicationAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAGuardedAction(AGuardedAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASequentialCompositionAction(
			ASequentialCompositionAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAExternalChoiceAction(
			AExternalChoiceAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInternalChoiceAction(
			AInternalChoiceAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterruptAction(AInterruptAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATimedInterruptAction(
			ATimedInterruptAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUntimedTimeoutAction(
			AUntimedTimeoutAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseATimeoutAction(ATimeoutAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAHidingAction(AHidingAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAStartDeadlineAction(
			AStartDeadlineAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAEndDeadlineAction(AEndDeadlineAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAChannelRenamingAction(
			AChannelRenamingAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAMuAction(AMuAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAParametrisedAction(AParametrisedAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAStmAction(AStmAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAReferenceAction(AReferenceAction node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterleavingParallelAction(
			AInterleavingParallelAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAGeneralisedParallelismParallelAction(
			AGeneralisedParallelismParallelAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAAlphabetisedParallelismParallelAction(
			AAlphabetisedParallelismParallelAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASequentialCompositionReplicatedAction(
			ASequentialCompositionReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAExternalChoiceReplicatedAction(
			AExternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInternalChoiceReplicatedAction(
			AInternalChoiceReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAInterleavingReplicatedAction(
			AInterleavingReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAGeneralisedParallelismReplicatedAction(
			AGeneralisedParallelismReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAAlphabetisedParallelismReplicatedAction(
			AAlphabetisedParallelismReplicatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAParametrisedInstantiatedAction(
			AParametrisedInstantiatedAction node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAReadCommunicationParameter(
			AReadCommunicationParameter node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAWriteCommunicationParameter(
			AWriteCommunicationParameter node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseASignalCommunicationParameter(
			ASignalCommunicationParameter node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAValParametrisation(AValParametrisation node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAResParametrisation(AResParametrisation node,
			Context question) throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAVresParametrisation(
			AVresParametrisation node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUnresolvedStateDesignator(
			AUnresolvedStateDesignator node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAUnresolvedObjectDesignator(
			AUnresolvedObjectDesignator node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAActionStm(AActionStm node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseANewStm(ANewStm node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAIfNonDeterministicStm(
			AIfNonDeterministicStm node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseAAltNonDeterministicStm(
			AAltNonDeterministicStm node, Context question)
			throws AnalysisException
	{

		return null;
	}

	@Override
	public NameValuePairList caseADoNonDeterministicStm(
			ADoNonDeterministicStm node, Context question)
			throws AnalysisException
	{

		return null;
	}

}
