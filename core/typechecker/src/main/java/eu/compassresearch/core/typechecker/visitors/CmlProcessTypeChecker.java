package eu.compassresearch.core.typechecker.visitors;

import static eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil.getVoidType;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
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
import eu.compassresearch.ast.process.ATimedInterruptProcess;
import eu.compassresearch.ast.process.ATimeoutProcess;
import eu.compassresearch.ast.process.AUntimedTimeoutProcess;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.PParametrisationAssistant;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;
import eu.compassresearch.core.typechecker.environment.PrivateActionClassEnvironment;

public class CmlProcessTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

	/**
	 * Type checker for var set expressions used for channel sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> channelSetChecker;

	public CmlProcessTypeChecker(IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler,
			QuestionAnswerAdaptor<TypeCheckInfo, PType> channelSetChecker)
	{
		super(root);
		this.issueHandler = issueHandler;
		this.channelSetChecker = channelSetChecker;
	}

	/**
	 * Find a channel, action or chanset in the environment
	 * 
	 * @param env
	 * @param identifier
	 * @return
	 */
	private static PDefinition findDefinition(ILexIdentifierToken identifier,
			Environment env)
	{
		Set<PDefinition> defs = env.findMatches(new eu.compassresearch.ast.lex.CmlLexNameToken("", identifier));

		if (defs.isEmpty())
		{
			return null;
		} else
		{
			return defs.iterator().next();
		}
	}

	/**
	 * The special process that actually is a definition of actions
	 */
	@Override
	public PType caseAActionProcess(AActionProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		Environment base = new PrivateActionClassEnvironment(question.assistantFactory, node.getActionDefinition(), question.env);
		Environment env = PParametrisationAssistant.updateEnvironment(base, node.getActionDefinition());

		// FIXME we properly need to assemble all action definitions in the process and add then to the env

		TypeCheckInfo q = new TypeCheckInfo(question.assistantFactory, env, NameScope.NAMESANDSTATE);

		node.getActionDefinition().apply(THIS, q);
		node.getAction().apply(THIS, q);

		return super.caseAActionProcess(node, question);
	}

	private PType typeCheck(ILexLocation location, TypeCheckInfo question,
			INode... iNodes) throws AnalysisException
	{
		List<PType> types = new Vector<PType>();
		for (INode iNode : iNodes)
		{
			types.add(iNode.apply(THIS, question));
		}

		return TypeCheckerUtil.generateUnionType(question.assistantFactory, location, types);
	}

	@Override
	public PType caseAInternalChoiceProcess(AInternalChoiceProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		typeCheck(node.getLocation(), question, node.getLeft(), node.getRight());

		return getVoidType(node);
	}

	@Override
	public PType caseAUntimedTimeoutProcess(AUntimedTimeoutProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		typeCheck(node.getLocation(), question, node.getLeft(), node.getRight());

		return getVoidType(node);
	}

	@Override
	public PType caseATimeoutProcess(ATimeoutProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		node.getTimeoutExpression().apply(THIS, question);

		typeCheck(node.getLocation(), question, node.getLeft(), node.getRight());

		return getVoidType(node);
	}

	@Override
	public PType caseASequentialCompositionReplicatedProcess(
			ASequentialCompositionReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess proc = node.getReplicatedProcess();

		TypeCheckInfo info = createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration());

		proc.apply(THIS, info);

		return getVoidType(node);
	}

	@Override
	public PType caseAInternalChoiceReplicatedProcess(
			AInternalChoiceReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{
		PProcess proc = node.getReplicatedProcess();
		TypeCheckInfo info = createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration());

		proc.apply(THIS, info);

		return getVoidType(node);
	}

	@Override
	public PType caseAGeneralisedParallelismReplicatedProcess(
			AGeneralisedParallelismReplicatedProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PVarsetExpression csExp = node.getChansetExpression();
		PProcess repProc = node.getReplicatedProcess();

		csExp.apply(channelSetChecker, question);

		TypeCheckInfo info = createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration());

		repProc.apply(THIS, info);

		return getVoidType(node);
	}

	@Override
	public PType caseAExternalChoiceReplicatedProcess(
			AExternalChoiceReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{
		PProcess repProc = node.getReplicatedProcess();

		repProc.apply(THIS, createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration()));

		return getVoidType(node);
	}

	/**
	 * Utility method to check replications and construct the new environment that containt them
	 * 
	 * @param question
	 * @param repDec
	 * @return
	 * @throws AnalysisException
	 */
	private TypeCheckInfo createAndCheckReplicationDeclarations(
			TypeCheckInfo question, List<PSingleDeclaration> repDec)
			throws AnalysisException
	{
		List<PDefinition> localDefinitions = new Vector<PDefinition>();

		for (PSingleDeclaration d : repDec)
		{
			PType dType = d.apply(THIS, question);
			for (PDefinition def : dType.getDefinitions())
			{
				localDefinitions.add(def);
			}
		}

		return question.newScope(localDefinitions);
	}

	@Override
	public PType caseAAlphabetisedParallelismReplicatedProcess(
			AAlphabetisedParallelismReplicatedProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		PVarsetExpression csExp = node.getChansetExpression();
		PProcess repProcess = node.getReplicatedProcess();

		TypeCheckInfo info = createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration());

		csExp.apply(channelSetChecker, info);

		repProcess.apply(THIS, info);

		return getVoidType(node);
	}

	@Override
	public PType caseAInterruptProcess(AInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAInterleavingProcess(AInterleavingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAInstantiationProcess(AInstantiationProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		LinkedList<PExp> args = node.getArgs();
		LinkedList<PParametrisation> decl = node.getParametrisations();
		PProcess proc = node.getProcess();

		for (PExp arg : args)
		{
			PType argType = arg.apply(THIS, question);
		}

		List<PDefinition> definitions = new LinkedList<PDefinition>();

		for (PParametrisation d : decl)
		{
			PType dType = d.apply(THIS, question);

			// only add if it could be resolved
			if (dType != null)
			{
				definitions.addAll(dType.getDefinitions());
			}
		}

		if (args.size() != definitions.size())
		{
			issueHandler.addTypeError(node, TypeErrorMessages.WRONG_NUMBER_OF_ARGUMENTS, ""
					+ definitions.size(), "" + args.size());
		} else
		{

			List<PDefinition> locals = new Vector<PDefinition>();

			for (int i = 0; i < args.size(); i++)
			{
				PExp ithExp = args.get(i);
				PDefinition ithDef = definitions.get(i);
				if (!TypeComparator.compatible(ithExp.getType(), ithDef.getType()))
				{
					issueHandler.addTypeError(node, TypeErrorMessages.INCOMPATIBLE_TYPE, ""
							+ ithDef.getType(), "" + ithExp.getType());
					break;
				}
				locals.add(ithDef);
			}

			PType procType = proc.apply(THIS, question.newScope(locals));
		}

		return getVoidType(node);
	}

	@Override
	public PType caseAHidingProcess(AHidingProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		PType leftType = left.apply(THIS, question);

		PVarsetExpression csexp = node.getChansetExpression();
		PType csexpType = csexp.apply(channelSetChecker, question);

		return getVoidType(node);
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

		PType csExpType = csExp.apply(channelSetChecker, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAExternalChoiceProcess(AExternalChoiceProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		PType leftType = left.apply(THIS, question);

		PType rightType = right.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAChannelRenamingProcess(AChannelRenamingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess process = node.getProcess();
		SRenameChannelExp renameExp = node.getRenameExpression();

		PType processType = process.apply(THIS, question);

		PType renameExpType = renameExp.apply(THIS, question);

		return getVoidType(node);
	}

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
		PType leftChanSetType = leftChanSet.apply(channelSetChecker, question);

		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		PType rightChanSetType = rightChanSet.apply(channelSetChecker, question);

		return getVoidType(node);
	}
	
	
	private PType caseDeadlineProcess(PProcess node, PProcess leftProcess,
			PExp expression,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = leftProcess;

		PExp timeExp = expression;

		PType leftType = left.apply(THIS, question);

		PType timeExpType = timeExp.apply(THIS, question);

		if (!TypeComparator.isSubType(timeExpType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(timeExp, TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, node
					+ "", timeExpType + "");
		}

		return getVoidType(node);
	} 

	@Override
	public PType caseAStartDeadlineProcess(AStartDeadlineProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		return caseDeadlineProcess(node, node.getLeft(), node.getExpression(), question);
	}

	@Override
	public PType caseAEndDeadlineProcess(AEndDeadlineProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		return caseDeadlineProcess(node, node.getLeft(), node.getExpression(), question);
	}

	@Override
	public PType caseAInterleavingReplicatedProcess(
			AInterleavingReplicatedProcess node, TypeCheckInfo question)
			throws AnalysisException
	{
		PProcess replicatedProcess = node.getReplicatedProcess();

		TypeCheckInfo info = createAndCheckReplicationDeclarations(question, node.getReplicationDeclaration());

		PType replicatedProcessType = replicatedProcess.apply(THIS, info);

		return replicatedProcessType;
	}

	@Override
	public PType caseASequentialCompositionProcess(
			ASequentialCompositionProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		node.getLeft().apply(THIS, question);
		node.getRight().apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAReferenceProcess(AReferenceProcess node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		LinkedList<PExp> args = node.getArgs();
		for (PExp arg : args)
		{
			PType type = arg.apply(this.THIS, question);
		}

		PDefinition processDef = findDefinition(node.getProcessName(), question.env);

		if (processDef == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL, node.getProcessName()
					+ "");
		} else
		{

			if (!(processDef instanceof AProcessDefinition))
			{
				issueHandler.addTypeError(processDef, TypeErrorMessages.EXPECTED_PROCESS_DEFINITION, node.getProcessName()
						+ "");
			} else
			{
				node.setProcessDefinition((AProcessDefinition) processDef);
			}
		}

		return getVoidType(node);
	}

	@Override
	public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		PProcess left = node.getLeft();
		PType leftType = left.apply(THIS, question);

		PProcess right = node.getRight();
		PType rightType = right.apply(THIS, question);

		PType expType = node.getTimeExpression().apply(THIS, question);
		if (!TypeComparator.isSubType(expType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(node.getTimeExpression(), TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, node.getTimeExpression()
					+ "");
		}

		return getVoidType(node);
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
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
	{
		return null;
	}
}
