package eu.compassresearch.core.typechecker.version2;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.definitions.SFunctionDefinition;
import org.overture.ast.definitions.SOperationDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.statements.AActionStm;
import org.overture.ast.statements.PStm;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckException;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeChecker;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.actions.PParametrisation;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SChannelExp;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.CmlSClassDefinitionAssistant;

public class CmlCspTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Handlder for error reporting
	 */
	private final ITypeIssueHandler issueHandler;

	/**
	 * Type checker for the PAction type tree
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> actionChecker;

	/**
	 * The checker for processes
	 */
	private final CmlProcessTypeChecker processChecker;

	/**
	 * The checker for vdm
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> vdmChecker;

	/**
	 * Type checker for var set expressions
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> varSetExpChecker;

	/**
	 * Type checker for channel expressions
	 */
	private final CmlChannelExpressionTypeChecker channelExpChecker;

	public CmlCspTypeChecker(ITypeIssueHandler issuehandler)
	{
		this.vdmChecker = new CmlVdmTypeCheckVisitor()
		{

			@Override
			public PType caseAActionStm(AActionStm node, TypeCheckInfo question)
					throws AnalysisException
			{
				PType type = node.getAction().apply(CmlCspTypeChecker.this, question);
				node.setType(type);
				return node.getType();
			}
		};

		this.issueHandler = issuehandler;
		this.actionChecker = new CmlActionTypeChecker(vdmChecker, this, issuehandler);
		this.processChecker = new CmlProcessTypeChecker(vdmChecker, this, issuehandler);
		this.varSetExpChecker = new CmlVarSetExpressionTypeChecker(vdmChecker, this, issuehandler);
		this.channelExpChecker = new CmlChannelExpressionTypeChecker(this, issuehandler);

	}

	@Override
	public PType defaultPSource(PSource node, TypeCheckInfo question)
			throws AnalysisException
	{
		for (PDefinition d : node.getParagraphs())
		{
			try
			{
				d.apply(this, question);
			} catch (TypeCheckException te)
			{
				TypeChecker.report(3428, te.getMessage(), te.location);
			}
		}
		return AstFactory.newAVoidType(null);
	}

	@Override
	public PType defaultSClassDefinition(SClassDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		// all ready done with this
		return node.getType();
	}

	@Override
	public PType defaultSFunctionDefinition(SFunctionDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.getType();
	}

	@Override
	public PType defaultSOperationDefinition(SOperationDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.getType();
	}

	@Override
	public PType caseAChannelDefinition(AChannelDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		try
		{
			return question.assistantFactory.createPTypeAssistant().typeResolve(node.getType(), null, vdmChecker, question);
		} catch (TypeCheckException te)
		{
			TypeChecker.report(3427, te.getMessage(), te.location);
		}

		return node.getType();

	}

	@Override
	public PType caseAChansetDefinition(AChansetDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		PVarsetExpression chansetExp = node.getChansetExpression();

		PType type = chansetExp.apply(THIS, question);
		// CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		// cmlEnv.addChannel(node.getIdentifier(), node);

		node.setType(type);
		return node.getType();
	}

	@Override
	public PType caseAProcessDefinition(AProcessDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		Environment env = CmlSClassDefinitionAssistant.updateProcessEnvironment(node, question.env);
		node.getProcess().apply(THIS, new TypeCheckInfo(question.assistantFactory, env, question.scope));

		return AstFactory.newAVoidType(node.getLocation());
	}

	@Override
	public PType defaultPAction(PAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(actionChecker, question);
	}

	@Override
	public PType defaultPParametrisation(PParametrisation node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.apply(actionChecker, question);
	}

	@Override
	public PType defaultPProcess(PProcess node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(processChecker, question);
	}

	@Override
	public PType defaultPExp(PExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmChecker, question);
	}

	@Override
	public PType defaultPVarsetExpression(PVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.apply(varSetExpChecker, question);
	}

	@Override
	public PType defaultPStm(PStm node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmChecker, question);
	}

	@Override
	public PType defaultSChannelExp(SChannelExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(channelExpChecker, question);
	}

	/**
	 * If nothing is specified then assume that it is a VDM construct
	 */
	@Override
	public PType defaultINode(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmChecker, question);
	}

	@Override
	public PType caseAActionClassDefinition(AActionClassDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		for (PDefinition def : node.getDefinitions())
		{
			if (def instanceof AActionDefinition)
			{
				def.apply(this, question);
			}

			// TODO namesets?
		}

		return super.caseAActionClassDefinition(node, question);
	}

	@Override
	public PType caseAActionDefinition(AActionDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		node.getAction().apply(actionChecker, question);

		return super.caseAActionDefinition(node, question);
	}

	// the strange single type
	@Override
	public PType caseATypeSingleDeclaration(ATypeSingleDeclaration node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{
		PType type = node.getType();

		if (type != null)
		{
			PType typetype = question.assistantFactory.createPTypeAssistant().typeResolve(type, null, vdmChecker, question);// type.apply(THIS,
																															// question);

			List<PDefinition> defs = new LinkedList<PDefinition>();

			LexNameToken idName = new LexNameToken("", node.getIdentifier());
			ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), idName, NameScope.LOCAL, typetype);
			defs.add(localDef);
			type.setDefinitions(defs);
		}
		return node.getType();
	}

	@Override
	public PType caseAExpressionSingleDeclaration(
			AExpressionSingleDeclaration node,
			org.overture.typechecker.TypeCheckInfo question)
			throws AnalysisException
	{

		PExp expression = node.getExpression();

		PType expressionType = expression.apply(THIS, question);

		List<PDefinition> defs = new LinkedList<PDefinition>();
		ILexIdentifierToken id = node.getIdentifier();
		if (expressionType instanceof ASetType)
		{

			ILexNameToken name = null;
			if (id instanceof LexNameToken)
				name = (LexNameToken) id;
			else
				name = new LexNameToken("", id.getName(), id.getLocation());

			ASetType expressionSetType = (ASetType) expressionType;
			ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSetType.getSetof());
			defs.add(localDef);

		} else if (expressionType instanceof ASeq1SeqType)
		{
			ILexNameToken name = null;
			if (id instanceof LexNameToken)
				name = (LexNameToken) id;
			else
				name = new LexNameToken("", id.getName(), id.getLocation());

			ASeq1SeqType expressionSeqType = (ASeq1SeqType) expressionType;
			ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSeqType.getSeqof());
			defs.add(localDef);
		} else
		{

			return issueHandler.addTypeError(expression, TypeErrorMessages.INCOMPATIBLE_TYPE.customizeMessage(""
					+ new ASetType(), "" + expressionType));
		}

		expressionType.setDefinitions(defs);

		return expressionType;
	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		return null;
	}

}
