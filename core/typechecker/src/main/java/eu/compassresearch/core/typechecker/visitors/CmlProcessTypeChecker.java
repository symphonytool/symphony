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
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ANatNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeComparator;

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
import eu.compassresearch.core.typechecker.assistant.AReferenceAssistant;
import eu.compassresearch.core.typechecker.assistant.PParametrisationAssistant;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;
import eu.compassresearch.core.typechecker.environment.PrivateActionClassEnvironment;

public class CmlProcessTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	private final ITypeIssueHandler issueHandler;

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

		left.apply(THIS, question);

		right.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAInterleavingProcess(AInterleavingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		left.apply(THIS, question);

		right.apply(THIS, question);

		return getVoidType(node);
	}

	/**
	 * This case checks the process instantiation<br>
	 * {@code (val x:int @ begin @ Skip end startsby x)(23)}<br>
	 * or {@code (val x : int @ A(x))(23)} with {@code process A = val x : int @ Skip} it is assumed that this actually
	 * means {@code A(23)}
	 */
	@Override
	public PType caseAInstantiationProcess(AInstantiationProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		List<PType> atypes = question.assistantFactory.createACallObjectStatementAssistant().getArgTypes(node.getArgs(), THIS, question);

		List<PDefinition> definitions = new LinkedList<PDefinition>();

		List<PType> paramTypes = new Vector<PType>();
		for (PParametrisation localDef : node.getParametrisations())
		{
			PType t = localDef.getDeclaration().getType();
			question.assistantFactory.createPTypeAssistant().typeResolve(t, null, THIS, question);
			paramTypes.add(t);
			// only add if it could be resolved
			if (t != null)
			{
				definitions.add(localDef.getDeclaration());
			}
		}

		AReferenceAssistant.checkArgTypes(node, AstFactory.newAVoidReturnType(node.getLocation()), paramTypes, atypes);
		node.getProcess().apply(THIS, question.newScope(definitions));
		return getVoidType(node);
	}

	@Override
	public PType caseAHidingProcess(AHidingProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		left.apply(THIS, question);

		PVarsetExpression csexp = node.getChansetExpression();
		csexp.apply(channelSetChecker, question);

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

		left.apply(THIS, question);

		right.apply(THIS, question);

		csExp.apply(channelSetChecker, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAExternalChoiceProcess(AExternalChoiceProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess left = node.getLeft();
		PProcess right = node.getRight();

		left.apply(THIS, question);

		right.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAChannelRenamingProcess(AChannelRenamingProcess node,
			TypeCheckInfo question) throws AnalysisException
	{

		PProcess process = node.getProcess();
		SRenameChannelExp renameExp = node.getRenameExpression();

		process.apply(THIS, question);

		renameExp.apply(THIS, question);

		return getVoidType(node);
	}

	@Override
	public PType caseAAlphabetisedParallelismProcess(
			AAlphabetisedParallelismProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = node.getLeft();
		left.apply(THIS, question);

		PProcess right = node.getRight();
		right.apply(THIS, question);

		PVarsetExpression leftChanSet = node.getLeftChansetExpression();
		leftChanSet.apply(channelSetChecker, question);

		PVarsetExpression rightChanSet = node.getRightChansetExpression();
		rightChanSet.apply(channelSetChecker, question);

		return getVoidType(node);
	}

	private PType caseDeadlineProcess(PProcess node, PProcess leftProcess,
			PExp expression, org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PProcess left = leftProcess;

		PExp timeExp = expression;

		left.apply(THIS, question);

		PType timeExpType = timeExp.apply(THIS, question);

		if (!TypeComparator.compatible(new ANatNumericBasicType(), timeExpType))
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

		List<PType> atypes = question.assistantFactory.createACallObjectStatementAssistant().getArgTypes(node.getArgs(), THIS, question);

		PDefinition def = findDefinition(node.getProcessName(), question.env);

		if (def == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL, node.getProcessName()
					+ "");
		} else
		{

			if (def instanceof AProcessDefinition)
			{
				AProcessDefinition processDef = (AProcessDefinition) def;

				List<PType> paramTypes = new Vector<PType>();
				for (PParametrisation localDef : processDef.getLocalState())
				{
					PType t = localDef.getDeclaration().getType();
					question.assistantFactory.createPTypeAssistant().typeResolve(t, null, THIS, question);
					paramTypes.add(t);
				}

				AReferenceAssistant.checkArgTypes(node, AstFactory.newAVoidReturnType(node.getLocation()), paramTypes, atypes);
				node.setProcessDefinition(processDef);
			} else
			{
				issueHandler.addTypeError(def, TypeErrorMessages.EXPECTED_PROCESS_DEFINITION, node.getProcessName()
						+ "");
			}
		}

		return getVoidType(node);
	}

	@Override
	public PType caseATimedInterruptProcess(ATimedInterruptProcess node,
			TypeCheckInfo question) throws AnalysisException
	{
		PProcess left = node.getLeft();
		left.apply(THIS, question);

		PProcess right = node.getRight();
		right.apply(THIS, question);

		PType expType = node.getTimeExpression().apply(THIS, question);
		if (!TypeComparator.isSubType(expType, new ANatNumericBasicType(), question.assistantFactory))
		{
			issueHandler.addTypeError(node.getTimeExpression(), TypeErrorMessages.TIME_UNIT_EXPRESSION_MUST_BE_NAT, node.getTimeExpression()
					+ "");
		}

		return getVoidType(node);
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
