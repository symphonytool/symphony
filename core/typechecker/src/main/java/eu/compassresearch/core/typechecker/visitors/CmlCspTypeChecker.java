package eu.compassresearch.core.typechecker.visitors;

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
import org.overture.ast.node.INode;
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
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SChannelExp;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.ast.process.PProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.ast.statements.AActionStm;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.PParametrisationAssistant;
import eu.compassresearch.core.typechecker.visitors.CmlVarSetExpressionTypeChecker.VarSetCheckType;

public class CmlCspTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * Handler for error reporting
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
	 * Type checker for var set expressions used for channel sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> channelSetChecker;
	/**
	 * Type checker for var set expressions used for name sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> nameSetChecker;

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

		this.channelSetChecker = new CmlVarSetExpressionTypeChecker(this, issuehandler, VarSetCheckType.CHANNELSET);
		this.nameSetChecker = new CmlVarSetExpressionTypeChecker(this, issuehandler, VarSetCheckType.NAMESET);

		this.actionChecker = new CmlActionTypeChecker(this, issuehandler, channelSetChecker, nameSetChecker);
		this.processChecker = new CmlProcessTypeChecker(this, issuehandler, channelSetChecker);

		this.channelExpChecker = new CmlChannelExpressionTypeChecker(this, issuehandler);

	}

	public PType caseACallAction(
			eu.compassresearch.ast.actions.ACallAction node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.apply(actionChecker, question);
	};

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

		PType type = chansetExp.apply(channelSetChecker, question);

		node.setType(type);
		return node.getType();
	}

	@Override
	public PType caseANamesetDefinition(ANamesetDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		PType type = node.getNamesetExpression().apply(nameSetChecker, question);

		node.setType(type);
		return node.getType();
	}

	@Override
	public PType caseAProcessDefinition(AProcessDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		if (!node.getLocalState().isEmpty())
		{
			for (PParametrisation par : node.getLocalState())
			{
				try
				{
					question.assistantFactory.createPTypeAssistant().typeResolve(par.getDeclaration().getType(), null, vdmChecker, question);
				} catch (TypeCheckException te)
				{
					TypeChecker.report(3427, te.getMessage(), te.location);
				}
			}
		}

		Environment env = PParametrisationAssistant.updateEnvironment(question.env, node);
		node.getProcess().apply(THIS, new TypeCheckInfo(question.assistantFactory, env, NameScope.NAMESANDSTATE));

		return AstFactory.newAVoidType(node.getLocation());
	}

	@Override
	public PType defaultPAction(PAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(actionChecker, question.newScope(NameScope.NAMESANDSTATE));
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
		throw new InternalException(0, "Var set Expressions must explicitly state which visitor to use: channel-set or name-set");
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
		// Must be two pass.

		// 1) Resolve action names
		for (PDefinition def : node.getDefinitions())
		{
			if (def instanceof AActionDefinition
					&& def.getName().getTypeQualifier() != null)
			{
				System.out.println(def.getName());
				for (PType type : def.getName().getTypeQualifier())
				{
					System.out.println(type);
					try
					{
						question.assistantFactory.createPTypeAssistant().typeResolve(type, null, vdmChecker, question);
					} catch (TypeCheckException te)
					{
						TypeChecker.report(3427, te.getMessage(), te.location);
					}
				}
			}
		}

		// 2) TC
		for (PDefinition def : node.getDefinitions())
		{
			if (def instanceof AActionDefinition)
			{
				def.apply(THIS, question);
			} else if (def instanceof ANamesetDefinition)
			{
				def.apply(THIS, question);
			}
		}

		return AstFactory.newAClassType(node.getLocation(), node);
	}

	@Override
	public PType caseAActionDefinition(AActionDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		if (!node.getDeclarations().isEmpty())
		{
			for (PParametrisation par : node.getDeclarations())
			{
				try
				{
					question.assistantFactory.createPTypeAssistant().typeResolve(par.getDeclaration().getType(), null, vdmChecker, question);
				} catch (TypeCheckException te)
				{
					TypeChecker.report(3427, te.getMessage(), te.location);
				}
			}
		}

		Environment env = PParametrisationAssistant.updateEnvironment(question.env, node.getDeclarations());
		return node.getAction().apply(actionChecker, question.newInfo(env));

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

			CmlLexNameToken idName = new CmlLexNameToken("", node.getIdentifier());
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
			if (id instanceof CmlLexNameToken)
			{
				name = (CmlLexNameToken) id;
			} else
			{
				name = new CmlLexNameToken("", id.getName(), id.getLocation());
			}

			ASetType expressionSetType = (ASetType) expressionType;
			ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSetType.getSetof());
			defs.add(localDef);

		} else if (expressionType instanceof ASeq1SeqType)
		{
			ILexNameToken name = null;
			if (id instanceof CmlLexNameToken)
			{
				name = (CmlLexNameToken) id;
			} else
			{
				name = new CmlLexNameToken("", id.getName(), id.getLocation());
			}

			ASeq1SeqType expressionSeqType = (ASeq1SeqType) expressionType;
			ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSeqType.getSeqof());
			defs.add(localDef);
		} else
		{

			issueHandler.addTypeError(expression, TypeErrorMessages.INCOMPATIBLE_TYPE, ""
					+ AstFactory.newASetType(null, AstFactory.newAUndefinedType(null)), ""
					+ expressionType);
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
