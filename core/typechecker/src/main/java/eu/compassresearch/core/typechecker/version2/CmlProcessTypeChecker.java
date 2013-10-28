package eu.compassresearch.core.typechecker.version2;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

//import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.lex.LexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.actions.ATimedInterruptAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.PSingleDeclaration;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SRenameChannelExp;
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
import eu.compassresearch.ast.process.AStartDeadlineProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismProcess;
import eu.compassresearch.ast.process.ASynchronousParallelismReplicatedProcess;
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.types.AProcessType;
import eu.compassresearch.core.typechecker.CmlTypeCheckInfo;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeWarningMessages;
import eu.compassresearch.core.typechecker.util.CmlTCUtil;

public class CmlProcessTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionAnswerAdaptor<TypeCheckInfo, PType> tc;
	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

	@SuppressWarnings("deprecation")
	public CmlProcessTypeChecker(
			QuestionAnswerAdaptor<TypeCheckInfo, PType> tc2,
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler)
	{
		super(root);
		this.tc = tc2;
		this.issueHandler = issueHandler;
	}

	@Override
	public PType caseAInternalChoiceProcess(AInternalChoiceProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseATimeoutProcess(ATimeoutProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PExp timedExp = node.getTimeoutExpression();
		PProcess right = node.getRight();
		PProcess left = node.getLeft();

		PType timedExpType = timedExp.apply(THIS, question);

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseASynchronousParallelismReplicatedProcess(
			ASynchronousParallelismReplicatedProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		CmlTypeCheckInfo repProcEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : repdecl)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repProcEnv.addVariable(def.getName(), def);
		}

		PType procType = proc.apply(THIS, repProcEnv);

		return new AProcessType();
	}

	@Override
	public PType caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node));

		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();

		CmlTypeCheckInfo repProcEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : repdecl)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repProcEnv.addVariable(def.getName(), def);
		}

		PType procType = proc.apply(THIS, repProcEnv);

		return new AProcessType();
	}

	@Override
	public PType caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node));

		PProcess proc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repdecl = node.getReplicationDeclaration();

		CmlTypeCheckInfo repProcEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : repdecl)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repProcEnv.addVariable(def.getName(), def);
		}

		PType procType = proc.apply(THIS, repProcEnv);

		return new AProcessType();
	}

	@Override
	public PType caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node));
		PVarsetExpression csExp = node.getChansetExpression();
		PProcess repProc = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();

		PType csExpType = csExp.apply(THIS, question);

		CmlTypeCheckInfo repProcEnv = cmlEnv.newScope();
		for (PSingleDeclaration decl : repDecl)
		{
			PType declType = decl.apply(THIS, question);

			for (PDefinition def : declType.getDefinitions())
				repProcEnv.addVariable(def.getName(), def);
		}

		PType repProcType = repProc.apply(THIS, repProcEnv);

		return new AProcessType();
	}

	@Override
	public PType caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		LinkedList<PSingleDeclaration> repDecl = node.getReplicationDeclaration();
		PProcess repProc = node.getReplicatedProcess();

		for (PSingleDeclaration decl : repDecl)
		{
			PType declType = decl.apply(THIS, question);

		}

		PType repProcType = repProc.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node));

		PVarsetExpression csExp = node.getChansetExpression();
		PProcess repProcess = node.getReplicatedProcess();
		LinkedList<PSingleDeclaration> repDec = node.getReplicationDeclaration();

		CmlTypeCheckInfo local = cmlEnv.newScope();

		for (PSingleDeclaration d : repDec)
		{
			PType dType = d.apply(THIS, question);
			for (PDefinition def : dType.getDefinitions())
			{
				local.addVariable(def.getName(), def);
			}
		}

		PType csExpType = csExp.apply(THIS, local);

		// TODO: Maybe the declarations above needs to go into the environment ?
		issueHandler.addTypeWarning(repProcess, TypeWarningMessages.INCOMPLETE_TYPE_CHECKING.customizeMessage(""
				+ repProcess));
		PType repProcessType = repProcess.apply(THIS, local);

		return new AProcessType();
	}

	@Override
	public PType caseAInterruptProcess(AInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseAInterleavingProcess(AInterleavingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseAInstantiationProcess(AInstantiationProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		LinkedList<PExp> args = node.getArgs();
		LinkedList<PParametrisation> decl = node.getParametrisations();
		PProcess proc = node.getProcess();

		CmlTypeCheckInfo cmlEnv = null;//FIXME TCActionVisitor.getTypeCheckInfo(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(""
					+ node));

		CmlTypeCheckInfo procEnv = cmlEnv.newScope();

		for (PExp arg : args)
		{
			PType argType = arg.apply(THIS, question);
		}

		List<PDefinition> definitions = new LinkedList<PDefinition>();
		List<LexIdentifierToken> ids = new LinkedList<LexIdentifierToken>();
		for (PParametrisation d : decl)
		{
			PType dType = d.apply(THIS, question);

			definitions.addAll(dType.getDefinitions());
		}

		if (args.size() != definitions.size())
		{
			return issueHandler.addTypeError(node, TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS.customizeMessage(""
					+ definitions.size(), "" + args.size()));
		}

		for (int i = 0; i < args.size(); i++)
		{
			PExp ithExp = args.get(i);
			PDefinition ithDef = definitions.get(i);
			if (!TypeComparator.compatible(ithExp.getType(), ithDef.getType()))
			{
				return issueHandler.addTypeError(node, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""
						+ ithDef.getType(), "" + ithExp.getType()));
			}
			procEnv.addVariable(ithDef.getName(), ithDef);
		}

		PType procType = proc.apply(THIS, procEnv);

		return new AProcessType();
	}

	@Override
	public PType caseAHidingProcess(AHidingProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		PType leftType = left.apply(THIS, question);

		PVarsetExpression csexp = node.getChansetExpression();
		PType csexpType = csexp.apply(THIS, question);

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAGeneralisedParallelismProcess(
			AGeneralisedParallelismProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();
		PVarsetExpression csExp = node.getChansetExpression();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		PType csExpType = csExp.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseAExternalChoiceProcess(AExternalChoiceProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return new AProcessType();
	}

	@Override
	public PType caseAChannelRenamingProcess(AChannelRenamingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess process = node.getProcess();
		SRenameChannelExp renameExp = node.getRenameExpression();

		PType processType = process.apply(THIS, question);

		PType renameExpType = renameExp.apply(THIS, question);

		return new AProcessType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		PType leftType = left.apply(THIS, question);

		PProcess right = node.getRight();
		PType rightType = right.apply(THIS, question);

		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		PType leftChanSetType = leftChanSet.apply(THIS, question);

		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PType rightChanSetType = rightChanSet.apply(THIS, question);

		return new AProcessType(node.getLocation(), true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseAStartDeadlineProcess(AStartDeadlineProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();

		PExp timeExp = node.getExpression();

		PType leftType = left.apply(THIS, question);

		PType timeExpType = timeExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timeExpType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(node
					+ "", timeExpType + ""));

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAEndDeadlineProcess(AEndDeadlineProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		// TODO RWL Make this complete
		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);

		LinkedList<PSingleDeclaration> declarations = node.getReplicationDeclaration();

		CmlTypeCheckInfo processScope = cmlEnv.newScope();

		for (PSingleDeclaration singleDecl : declarations)
		{
			PType singleDeclType = singleDecl.apply(THIS, question);

			for (PDefinition def : singleDeclType.getDefinitions())
				processScope.addVariable(def.getName(), def);

		}

		PProcess replicatedProcess = node.getReplicatedProcess();

		PType replicatedProcessType = replicatedProcess.apply(THIS, processScope);

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseASynchronousParallelismProcess(
			ASynchronousParallelismProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		node.getLeft().apply(this, question);
		node.getRight().apply(this, question);

		// TODO: missing marker on processes

		return new AProcessType();
	}

	@Override
	public PType caseASequentialCompositionProcess(
			ASequentialCompositionProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		node.getLeft().apply(this, question);
		node.getRight().apply(this, question);

		// TODO: missing marker on processes

		return new AProcessType();
	}

	@Override
	public PType caseAReferenceProcess(AReferenceProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		eu.compassresearch.core.typechecker.CmlTypeCheckInfo newQ = (eu.compassresearch.core.typechecker.CmlTypeCheckInfo) question;

		LinkedList<PExp> args = node.getArgs();
		for (PExp arg : args)
		{
			PType type = arg.apply(this.THIS, question);
		}

		PDefinition processDef = newQ.lookup(node.getProcessName(), PDefinition.class);

		if (processDef == null)
		{
			return issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage(node.getProcessName()
					+ ""));
		}

		if (!(processDef instanceof AProcessDefinition))
			return issueHandler.addTypeError(processDef, TypeErrorMessages.EXPECTED_PROCESS_DEFINITION.customizeMessage(node.getProcessName()
					+ ""));
		node.setProcessDefinition((AProcessDefinition) processDef);

		return new AProcessType();
	}

	@Override
	public PType caseAActionProcess(AActionProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		if (cmlEnv == null)
			return issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT.customizeMessage(node
					+ ""));

		CmlTypeCheckInfo actionScope = cmlEnv.newScope();

		// resolve functions/operation names prior to TC
		// for (PDefinition def : node.getDefinitionParagraphs())
		// {
		// if (def.getName() != null)
		// {
		// if (def instanceof AFunctionsDefinition)
		// {
		// AFunctionsDefinition funcDef = (AFunctionsDefinition) def;
		// for (PDefinition d : funcDef.getFunctionDefinitions())
		// {
		// actionScope.addVariable(d.getName(), d);
		// }
		// } else if (def instanceof AOperationsDefinition)
		// {
		// AOperationsDefinition opDef = (AOperationsDefinition) def;
		// for (PDefinition d : opDef.getOperations())
		// {
		// actionScope.addVariable(d.getName(), d);
		// }
		// } else
		// {
		// actionScope.addVariable(def.getName(), def);
		// }
		// }
		// }

		// Type check all the paragraph definitions
		List<PDefinition> fixedDefinitions = new LinkedList<PDefinition>();
		// for (PDefinition def : node.getDefinitionParagraphs())
		// {
		// CmlTypeCheckInfo scope = actionScope;
		//
		// if (def.apply(question.assistantFactory.getFunctionChecker()))
		// {
		// scope = cmlEnv;
		// }
		// PType type = def.apply(this.THIS, scope);
		// if (!successfulType(type))
		// return issueHandler.addTypeError(def,
		// TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(def.getName()
		// + ""));
		// fixedDefinitions.addAll(TCDeclAndDefVisitor.handleDefinitionsForOverture(def));
		// // for (PDefinition d : type.getDefinitions()) {
		// // actionScope.addVariable(d.getName(), d);
		// // }
		// }
		// node.getDefinitionParagraphs().clear();
		// node.getDefinitionParagraphs().addAll(fixedDefinitions);

		question.contextSet(eu.compassresearch.core.typechecker.CmlTypeCheckInfo.class, (eu.compassresearch.core.typechecker.CmlTypeCheckInfo) question);
		PType actionType = node.getAction().apply(this.THIS, actionScope);
		question.contextRem(eu.compassresearch.core.typechecker.CmlTypeCheckInfo.class);

		return new AProcessType();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		PProcess left = node.getLeft();
		PType leftType = left.apply(THIS, question);

		PProcess right = node.getRight();
		PType rightType = right.apply(THIS, question);

		PType expType = node.getTimeExpression().apply(THIS, question);
		if (!TypeComparator.isSubType(expType, new ANatNumericBasicType()))
			return issueHandler.addTypeError(node.getTimeExpression(), TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT.customizeMessage(node.getTimeExpression()
					+ ""));

		return new AProcessType(node.getLocation(), true);
	}

	@Override
	public PType caseATimedInterruptAction(ATimedInterruptAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		// TODO Auto-generated method stub
		return super.caseATimedInterruptAction(node, question);
	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
