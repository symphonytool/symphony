package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.PType;
import org.overture.parser.messages.VDMError;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;
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
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;

class TCExpressionVisitor extends
		QuestionAnswerCMLAdaptor<org.overture.typechecker.TypeCheckInfo, PType> {

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

	private class CmlOvertureTypeExpressionVisitor extends
			TypeCheckerExpVisitor implements
			ICMLQuestionAnswer<org.overture.typechecker.TypeCheckInfo, PType> {

		public CmlOvertureTypeExpressionVisitor(
				QuestionAnswerAdaptor<TypeCheckInfo, PType> typeCheckVisitor) {
			super(typeCheckVisitor);
		}

		private PType escapeFromOvertureContext(INode node,
				org.overture.typechecker.TypeCheckInfo question) {
			try {
				return node.apply((VanillaCmlTypeChecker) parent, question);
			} catch (AnalysisException e) {
				e.printStackTrace();
			}
			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseFile(File node, TypeCheckInfo question)
				throws AnalysisException {
			return null;
		}

		@Override
		public PType caseInputStream(InputStream node, TypeCheckInfo question)
				throws AnalysisException {

			return null;
		}

		@Override
		public PType caseAFileSource(AFileSource node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATcpStreamSource(ATcpStreamSource node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInputStreamSource(AInputStreamSource node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelNameDeclaration(AChannelNameDeclaration node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExpressionSingleDeclaration(
				AExpressionSingleDeclaration node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAActionDefinition(AActionDefinition node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChansetDefinition(AChansetDefinition node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInvariantDefinition(AInvariantDefinition node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAProcessDefinition(AProcessDefinition node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAClassParagraphDefinition(
				AClassParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAProcessParagraphDefinition(
				AProcessParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelParagraphDefinition(
				AChannelParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChansetParagraphDefinition(
				AChansetParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAActionParagraphDefinition(
				AActionParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATypesParagraphDefinition(
				ATypesParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAOperationParagraphDefinition(
				AOperationParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAFunctionParagraphDefinition(
				AFunctionParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAValueParagraphDefinition(
				AValueParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInitialParagraphDefinition(
				AInitialParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStateParagraphDefinition(
				AStateParagraphDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExplicitOperationDefinition(
				AExplicitOperationDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAImplicitOperationDefinition(
				AImplicitOperationDefinition node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseABracketedExp(ABracketedExp node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANameExp(ANameExp node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATupleSelectExp(ATupleSelectExp node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANameChannelExp(ANameChannelExp node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAComprehensionRenameChannelExp(
				AComprehensionRenameChannelExp node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAEnumerationRenameChannelExp(
				AEnumerationRenameChannelExp node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAIdentifierChansetSetExp(
				AIdentifierChansetSetExp node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAEnumChansetSetExp(AEnumChansetSetExp node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACompChansetSetExp(ACompChansetSetExp node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStatementType(AStatementType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAProcessType(AProcessType node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAErrorType(AErrorType node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAProcessParagraphType(AProcessParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChansetParagraphType(AChansetParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelsParagraphType(AChannelsParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAActionParagraphType(AActionParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAValueParagraphType(AValueParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAFunctionParagraphType(AFunctionParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATypeParagraphType(ATypeParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAOperationParagraphType(AOperationParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStateParagraphType(AStateParagraphType node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASourceType(ASourceType node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelType(AChannelType node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseALogicalAccess(ALogicalAccess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAIdentifierTypePair(AIdentifierTypePair node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseARenamePair(ARenamePair node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStateProcess(AStateProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASequentialCompositionProcess(
				ASequentialCompositionProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExternalChoiceProcess(AExternalChoiceProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInternalChoiceProcess(AInternalChoiceProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAGeneralisedParallelismProcess(
				AGeneralisedParallelismProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAAlphabetisedParallelismProcess(
				AAlphabetisedParallelismProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASynchronousParallelismProcess(
				ASynchronousParallelismProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterleavingProcess(AInterleavingProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterruptProcess(AInterruptProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATimeoutProcess(ATimeoutProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAHidingProcess(AHidingProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStartDeadlineProcess(AStartDeadlineProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAEndDeadlineProcess(AEndDeadlineProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInstantiationProcess(AInstantiationProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelRenamingProcess(AChannelRenamingProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASequentialCompositionReplicatedProcess(
				ASequentialCompositionReplicatedProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExternalChoiceReplicatedProcess(
				AExternalChoiceReplicatedProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInternalChoiceReplicatedProcess(
				AInternalChoiceReplicatedProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAGeneralisedParallelismReplicatedProcess(
				AGeneralisedParallelismReplicatedProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAAlphabetisedParallelismReplicatedProcess(
				AAlphabetisedParallelismReplicatedProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASynchronousParallelismReplicatedProcess(
				ASynchronousParallelismReplicatedProcess node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterleavingReplicatedProcess(
				AInterleavingReplicatedProcess node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASkipAction(ASkipAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStopAction(AStopAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChaosAction(AChaosAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseADivAction(ADivAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAWaitAction(AWaitAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACommunicationAction(ACommunicationAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAGuardedAction(AGuardedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASequentialCompositionAction(
				ASequentialCompositionAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExternalChoiceAction(AExternalChoiceAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInternalChoiceAction(AInternalChoiceAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterruptAction(AInterruptAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATimedInterruptAction(ATimedInterruptAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAUntimedTimeoutAction(AUntimedTimeoutAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATimeoutAction(ATimeoutAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAHidingAction(AHidingAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAStartDeadlineAction(AStartDeadlineAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAEndDeadlineAction(AEndDeadlineAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAChannelRenamingAction(AChannelRenamingAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAMuAction(AMuAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAParametrisedAction(AParametrisedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACallAction(ACallAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASubclassResponsibilityAction(
				ASubclassResponsibilityAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANotYetSpecifiedAction(ANotYetSpecifiedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterleavingParallelAction(
				AInterleavingParallelAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAGeneralisedParallelismParallelAction(
				AGeneralisedParallelismParallelAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAAlphabetisedParallelismParallelAction(
				AAlphabetisedParallelismParallelAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASynchronousParallelismParallelAction(
				ASynchronousParallelismParallelAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASequentialCompositionReplicatedAction(
				ASequentialCompositionReplicatedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAExternalChoiceReplicatedAction(
				AExternalChoiceReplicatedAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInternalChoiceReplicatedAction(
				AInternalChoiceReplicatedAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAInterleavingReplicatedAction(
				AInterleavingReplicatedAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAGeneralisedParallelismReplicatedAction(
				AGeneralisedParallelismReplicatedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAAlphabetisedParallelismReplicatedAction(
				AAlphabetisedParallelismReplicatedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASynchronousParallelismReplicatedAction(
				ASynchronousParallelismReplicatedAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseADeclarationInstantiatedAction(
				ADeclarationInstantiatedAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAParametrisedInstantiatedAction(
				AParametrisedInstantiatedAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAReadCommunicationParameter(
				AReadCommunicationParameter node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAWriteCommunicationParameter(
				AWriteCommunicationParameter node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAReferenceCommunicationParameter(
				AReferenceCommunicationParameter node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAValParametrisation(AValParametrisation node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAResParametrisation(AResParametrisation node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAVresParametrisation(AVresParametrisation node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAIdentifierParameter(AIdentifierParameter node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseATupleParameter(ATupleParameter node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseARecordParameter(ARecordParameter node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseALetStatementAction(ALetStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseABlockStatementAction(ABlockStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANonDeterministicIfStatementAction(
				ANonDeterministicIfStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANonDeterministicAltStatementAction(
				ANonDeterministicAltStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAIfStatementAction(AIfStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAElseIfStatementAction(AElseIfStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACasesStatementAction(ACasesStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACallStatementAction(ACallStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASpecificationStatementAction(
				ASpecificationStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAAssignmentCallStatementAction(
				AAssignmentCallStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAReturnStatementAction(AReturnStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANewStatementAction(ANewStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseANonDeterministicDoStatementAction(
				ANonDeterministicDoStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAForSetStatementAction(AForSetStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAForIndexStatementAction(
				AForIndexStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAForSequenceStatementAction(
				AForSequenceStatementAction node, TypeCheckInfo question)
				throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAWhileStatementAction(AWhileStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseADeclareStatementAction(ADeclareStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseASingleGeneralAssignmentStatementAction(
				ASingleGeneralAssignmentStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseAMultipleGeneralAssignmentStatementAction(
				AMultipleGeneralAssignmentStatementAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

		@Override
		public PType caseACaseAlternativeAction(ACaseAlternativeAction node,
				TypeCheckInfo question) throws AnalysisException {

			return escapeFromOvertureContext(node, question);
		}

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
	@Override
	public PType defaultPExp(PExp node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException {
		org.overture.typechecker.TypeChecker.clearErrors();

		INode ovtNode = node;

		TypeCheckerExpVisitor overtureExpVisitor = new CmlOvertureTypeExpressionVisitor(
				((QuestionAnswerAdaptor<org.overture.typechecker.TypeCheckInfo, PType>) this));

		org.overture.typechecker.TypeCheckInfo quest = new org.overture.typechecker.TypeCheckInfo(
				question.env);

		quest.scope = NameScope.NAMES;

		try {
			ovtNode.apply(overtureExpVisitor, quest);
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

	public PType caseANameExp(ANameExp node, TypeCheckInfo question)
			throws AnalysisException {

		PDefinition type = question.env.findName(node.getName(),
				NameScope.GLOBAL);
		if (type == null) {
			node.setType(new AErrorType());
			issueHandler.addTypeError(node,
					TypeErrorMessages.NAMED_TYPE_UNDEFINED
							.customizeMessage(node.getName() + ""));
		} else
			node.setType(type.getType());
		return node.getType();
	}

}
