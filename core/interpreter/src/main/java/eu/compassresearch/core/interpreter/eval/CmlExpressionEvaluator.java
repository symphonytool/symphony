package eu.compassresearch.core.interpreter.eval;

import java.io.File;
import java.io.InputStream;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.expressions.PExp;
import org.overture.config.Settings;
import org.overture.interpreter.eval.ExpressionEvaluator;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.scheduler.BasicSchedulableThread;
import org.overture.interpreter.scheduler.InitThread;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.Value;

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
import eu.compassresearch.ast.definitions.AExplicitOperationDefinition;
import eu.compassresearch.ast.definitions.AFunctionParagraphDefinition;
import eu.compassresearch.ast.definitions.AImplicitOperationDefinition;
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
import eu.compassresearch.ast.expressions.ATupleSelectExp;
import eu.compassresearch.ast.expressions.PCMLExp;
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
import eu.compassresearch.core.interpreter.api.CMLContext;

public class CmlExpressionEvaluator extends QuestionAnswerCMLAdaptor<CMLContext, Value>
{
	//private ExpressionEvaluator vdmExpressionEvaluator;
	private CML2VDMExpressionEvaluator vdmExpressionEvaluator = new CML2VDMExpressionEvaluator();
	
	class CML2VDMExpressionEvaluator extends ExpressionEvaluator 
		implements ICMLQuestionAnswer<Context, Value>
	{

		@Override
		public Value caseFile(File node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseInputStream(InputStream node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAFileSource(AFileSource node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATcpStreamSource(ATcpStreamSource node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInputStreamSource(AInputStreamSource node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelNameDeclaration(AChannelNameDeclaration node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATypeSingleDeclaration(ATypeSingleDeclaration node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExpressionSingleDeclaration(
				AExpressionSingleDeclaration node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAActionDefinition(AActionDefinition node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChansetDefinition(AChansetDefinition node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInvariantDefinition(AInvariantDefinition node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAProcessDefinition(AProcessDefinition node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAClassParagraphDefinition(
				AClassParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAProcessParagraphDefinition(
				AProcessParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelParagraphDefinition(
				AChannelParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChansetParagraphDefinition(
				AChansetParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAActionParagraphDefinition(
				AActionParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATypesParagraphDefinition(
				ATypesParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAOperationParagraphDefinition(
				AOperationParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAFunctionParagraphDefinition(
				AFunctionParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAValueParagraphDefinition(
				AValueParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInitialParagraphDefinition(
				AInitialParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStateParagraphDefinition(
				AStateParagraphDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExplicitOperationDefinition(
				AExplicitOperationDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAImplicitOperationDefinition(
				AImplicitOperationDefinition node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseABracketedExp(ABracketedExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Value caseATupleSelectExp(ATupleSelectExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANameChannelExp(ANameChannelExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAComprehensionRenameChannelExp(
				AComprehensionRenameChannelExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAEnumerationRenameChannelExp(
				AEnumerationRenameChannelExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAIdentifierChansetSetExp(
				AIdentifierChansetSetExp node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAEnumChansetSetExp(AEnumChansetSetExp node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACompChansetSetExp(ACompChansetSetExp node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStatementType(AStatementType node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAProcessType(AProcessType node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAErrorType(AErrorType node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAProcessParagraphType(AProcessParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChansetParagraphType(AChansetParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelsParagraphType(AChannelsParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAActionParagraphType(AActionParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAValueParagraphType(AValueParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAFunctionParagraphType(AFunctionParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATypeParagraphType(ATypeParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAOperationParagraphType(AOperationParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStateParagraphType(AStateParagraphType node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASourceType(ASourceType node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelType(AChannelType node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseALogicalAccess(ALogicalAccess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAIdentifierTypePair(AIdentifierTypePair node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseARenamePair(ARenamePair node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStateProcess(AStateProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASequentialCompositionProcess(
				ASequentialCompositionProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExternalChoiceProcess(AExternalChoiceProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInternalChoiceProcess(AInternalChoiceProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAGeneralisedParallelismProcess(
				AGeneralisedParallelismProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAAlphabetisedParallelismProcess(
				AAlphabetisedParallelismProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASynchronousParallelismProcess(
				ASynchronousParallelismProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterleavingProcess(AInterleavingProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterruptProcess(AInterruptProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATimedInterruptProcess(ATimedInterruptProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATimeoutProcess(ATimeoutProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAHidingProcess(AHidingProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStartDeadlineProcess(AStartDeadlineProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAEndDeadlineProcess(AEndDeadlineProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInstantiationProcess(AInstantiationProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelRenamingProcess(AChannelRenamingProcess node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASequentialCompositionReplicatedProcess(
				ASequentialCompositionReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExternalChoiceReplicatedProcess(
				AExternalChoiceReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInternalChoiceReplicatedProcess(
				AInternalChoiceReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAGeneralisedParallelismReplicatedProcess(
				AGeneralisedParallelismReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAAlphabetisedParallelismReplicatedProcess(
				AAlphabetisedParallelismReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASynchronousParallelismReplicatedProcess(
				ASynchronousParallelismReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterleavingReplicatedProcess(
				AInterleavingReplicatedProcess node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASkipAction(ASkipAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStopAction(AStopAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChaosAction(AChaosAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseADivAction(ADivAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAWaitAction(AWaitAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACommunicationAction(ACommunicationAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAGuardedAction(AGuardedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASequentialCompositionAction(
				ASequentialCompositionAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExternalChoiceAction(AExternalChoiceAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInternalChoiceAction(AInternalChoiceAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterruptAction(AInterruptAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATimedInterruptAction(ATimedInterruptAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATimeoutAction(ATimeoutAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAHidingAction(AHidingAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAStartDeadlineAction(AStartDeadlineAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAEndDeadlineAction(AEndDeadlineAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAChannelRenamingAction(AChannelRenamingAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAMuAction(AMuAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAParametrisedAction(AParametrisedAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACallAction(ACallAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASubclassResponsibilityAction(
				ASubclassResponsibilityAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANotYetSpecifiedAction(ANotYetSpecifiedAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterleavingParallelAction(
				AInterleavingParallelAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAGeneralisedParallelismParallelAction(
				AGeneralisedParallelismParallelAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAAlphabetisedParallelismParallelAction(
				AAlphabetisedParallelismParallelAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASynchronousParallelismParallelAction(
				ASynchronousParallelismParallelAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASequentialCompositionReplicatedAction(
				ASequentialCompositionReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAExternalChoiceReplicatedAction(
				AExternalChoiceReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInternalChoiceReplicatedAction(
				AInternalChoiceReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAInterleavingReplicatedAction(
				AInterleavingReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAGeneralisedParallelismReplicatedAction(
				AGeneralisedParallelismReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAAlphabetisedParallelismReplicatedAction(
				AAlphabetisedParallelismReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASynchronousParallelismReplicatedAction(
				ASynchronousParallelismReplicatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseADeclarationInstantiatedAction(
				ADeclarationInstantiatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAParametrisedInstantiatedAction(
				AParametrisedInstantiatedAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAReadCommunicationParameter(
				AReadCommunicationParameter node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAWriteCommunicationParameter(
				AWriteCommunicationParameter node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAReferenceCommunicationParameter(
				AReferenceCommunicationParameter node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAValParametrisation(AValParametrisation node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAResParametrisation(AResParametrisation node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAVresParametrisation(AVresParametrisation node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAIdentifierParameter(AIdentifierParameter node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseATupleParameter(ATupleParameter node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseARecordParameter(ARecordParameter node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseALetStatementAction(ALetStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseABlockStatementAction(ABlockStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANonDeterministicIfStatementAction(
				ANonDeterministicIfStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANonDeterministicAltStatementAction(
				ANonDeterministicAltStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAIfStatementAction(AIfStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAElseIfStatementAction(AElseIfStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACasesStatementAction(ACasesStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACallStatementAction(ACallStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASpecificationStatementAction(
				ASpecificationStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAAssignmentCallStatementAction(
				AAssignmentCallStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAReturnStatementAction(AReturnStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANewStatementAction(ANewStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseANonDeterministicDoStatementAction(
				ANonDeterministicDoStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAForSetStatementAction(AForSetStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAForIndexStatementAction(
				AForIndexStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAForSequenceStatementAction(
				AForSequenceStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAWhileStatementAction(AWhileStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseADeclareStatementAction(ADeclareStatementAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseASingleGeneralAssignmentStatementAction(
				ASingleGeneralAssignmentStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseAMultipleGeneralAssignmentStatementAction(
				AMultipleGeneralAssignmentStatementAction node, Context question)
				throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value caseACaseAlternativeAction(ACaseAlternativeAction node,
				Context question) throws AnalysisException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public Value defaultPExp(PExp node, CMLContext question)
			throws AnalysisException {
		// TODO Auto-generated method stub
				
		InitThread initThread = new InitThread(Thread.currentThread());
        BasicSchedulableThread.setInitialThread(initThread);
        question.setThreadState(null, CPUValue.vCPU);
        Settings.dynamictypechecks = false;
        
		return node.apply(vdmExpressionEvaluator, question);
	}
	
	@Override
	public Value defaultPCMLExp(PCMLExp node, CMLContext question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.defaultPCMLExp(node, question);
	}
	
	@Override
	public Value caseANameChannelExp(ANameChannelExp node, CMLContext question)
			throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseANameChannelExp(node, question);
	}
	
	@Override
	public Value caseAEnumChansetSetExp(AEnumChansetSetExp node,
			CMLContext question) throws AnalysisException {
		// TODO Auto-generated method stub
		return super.caseAEnumChansetSetExp(node, question);
	}
	
    
//    CmlAstToOvertureAst                               transform  = new CmlAstToOvertureAst(
//                                                                     null, null);
//    org.overture.interpreter.eval.ExpressionEvaluator ovtExpEval = new org.overture.interpreter.eval.ExpressionEvaluator();
//    
//    @Override
//    public Value defaultPExp(PExp node, CMLContext question)
//        throws AnalysisException
//      {
//        
//        INode ovtNode = transform.defaultINode(node);
//        
//        /** The initial execution context. */
//        // RootContext initialContext = new StateContext(
//        // new LexLocation(), "global environment");
//        // initialContext.setThreadState(null, CPUValue.vCPU);
//        
//        // TODO For now we diable VDM typechecking, since something is not
//        // working
//        Settings.dynamictypechecks = false;
//        
//        Value value = null;
//        
//        try
//          {
//            InitThread initThread = new InitThread(Thread.currentThread());
//            BasicSchedulableThread.setInitialThread(initThread);
//            
//            org.overture.interpreter.runtime.Context evalContext = question
//                .getOvertureContext();
//            // new org.overture.interpreter.runtime.Context(location,
//            // "process expression", initialContext);
//            evalContext.setThreadState(null, CPUValue.vCPU);
//            // LexNameToken dName = new LexNameToken("Default", "d", location);
//            // evalContext.putNew(new NameValuePair(dName, new
//            // NaturalValue(10)));
//            
//            value = ovtNode.apply(ovtExpEval, evalContext);
//          } catch (org.overture.ast.analysis.AnalysisException e)
//          {
//            throw new AnalysisException(e.getMessage());
//          }
//        
//        return value;
//      }

}