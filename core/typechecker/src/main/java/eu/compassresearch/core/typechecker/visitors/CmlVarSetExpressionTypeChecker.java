package eu.compassresearch.core.typechecker.visitors;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.AAssignmentDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.AStateDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.AVariableExp;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;
import org.overture.typechecker.assistant.type.PTypeAssistantTC;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.expressions.ACompVarsetExpression;
import eu.compassresearch.ast.expressions.AEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AFatCompVarsetExpression;
import eu.compassresearch.ast.expressions.AFatEnumVarsetExpression;
import eu.compassresearch.ast.expressions.AIdentifierVarsetExpression;
import eu.compassresearch.ast.expressions.AInterVOpVarsetExpression;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.expressions.ASubVOpVarsetExpression;
import eu.compassresearch.ast.expressions.AUnionVOpVarsetExpression;
import eu.compassresearch.ast.expressions.PVarsetExpression;
import eu.compassresearch.ast.expressions.SVOpVarsetExpression;
import eu.compassresearch.ast.lex.CmlLexNameToken;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;

public class CmlVarSetExpressionTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	public enum VarSetCheckType
	{
		CHANNELSET, NAMESET
	};

	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();
	private final VarSetCheckType type;

	@SuppressWarnings("deprecation")
	public CmlVarSetExpressionTypeChecker(
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler, VarSetCheckType type)
	{
		super(root);
		this.issueHandler = issueHandler;
		this.type = type;
	}

	private PDefinition findDefinition(Environment env,
			ILexIdentifierToken identifier)
	{
		PDefinition def = null;
		switch (type)
		{
			case CHANNELSET:
				def = TypeCheckerUtil.findDefinition(identifier, env);
				break;
			case NAMESET:
				def = env.findName(new CmlLexNameToken("", identifier), NameScope.NAMESANDANYSTATE);
				break;
			default:
				break;

		}
		return def;

	}

	@Override
	public PType caseAIdentifierVarsetExpression(
			AIdentifierVarsetExpression node, TypeCheckInfo question)
			throws AnalysisException
	{

		ILexIdentifierToken id = node.getIdentifier();
		PDefinition idDef = findDefinition(question.env, id);

		if (idDef == null)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL, node
					+ "");
			return null;
		}

		if (idDef.getType() == null)
		{
			idDef.apply(THIS, question);
		}

		switch (type)
		{
			case CHANNELSET:
			{
				if (!(idDef instanceof AChansetDefinition || idDef instanceof AChannelDefinition))
				{
					issueHandler.addTypeError(node, TypeErrorMessages.EXPECTING_A_CHANNEL_OR_CHANSET_DEFINITION, idDef.apply(question.assistantFactory.getKindFinder()));
					return null;
				}
				break;
			}
			case NAMESET:
			{
				if (!(idDef instanceof ANamesetDefinition
						|| idDef instanceof AInstanceVariableDefinition || idDef instanceof AStateDefinition))
				{
					issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_STATE_OR_NAMESET_DEFINITION, idDef.apply(question.assistantFactory.getKindFinder()));
					return null;
				}
				break;
			}
			default:
				break;

		}

		node.setType(idDef.getType());
		node.getType().getDefinitions().add(idDef);
		return node.getType();
	}

	@Override
	public PType caseAEnumVarsetExpression(AEnumVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{

		List<PType> types = new Vector<PType>();

		// PType result = AstFactory.newAUnknownType(node.getLocation());

		LinkedList<ANameChannelExp> chanNames = node.getChannelNames();
		LinkedList<PDefinition> defs = new LinkedList<PDefinition>();
		for (ANameChannelExp chanName : chanNames)
		{
			ILexIdentifierToken id = chanName.getIdentifier();
			PDefinition def = findDefinition(question.env, id);

			if (def == null)
			{
				issueHandler.addTypeError(id, TypeErrorMessages.UNDEFINED_SYMBOL, id
						+ "");
				continue;
			}

			validateChannelNameDefinition(question, chanName, def);

			if (def instanceof AChannelDefinition)
			{
				// FIXME this looks wrong
				PType chanValueType = def.getType();
				types.add(chanValueType);

				if (chanValueType instanceof AProductType)
				{
					AProductType prodType = (AProductType) chanValueType;
					LinkedList<PType> prodTypes = prodType.getTypes();
					LinkedList<PExp> chanExpressions = chanName.getExpressions();

					// product and channel call same size?
					if (chanExpressions.size() != prodType.getTypes().size())
					{
						issueHandler.addTypeError(id, TypeErrorMessages.INCOMPATIBLE_TYPE, chanValueType
								+ "", chanName + "");
						return null;
					}

					// product and channel expressions same type?
					int i = 0;
					for (PExp pExp : chanExpressions)
					{
						PType expType = pExp.apply(THIS, question);
						PType pt = prodTypes.get(i++);

						if (!TypeComparator.isSubType(expType, pt))
						{
							issueHandler.addTypeError(id, TypeErrorMessages.INCOMPATIBLE_TYPE, pt
									+ "", expType + "");
						}
					}
				}
			}

			defs.add(def);
		}

		PType result = TypeCheckerUtil.setType(node, types);
		result.setDefinitions(new LinkedList<PDefinition>(defs));
		return result;
	}

	@Override
	public PType caseACompVarsetExpression(ACompVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		// bnd { a.x.y.z | x : Type, z : Type : z : Type } ]

		// ANameChannelExp chanNameExp = node.getChannelNameExp();
		// PExp predicate = node.getPredicate();
		// LinkedList<PMultipleBind> bindings = node.getBindings();
		//
		// CmlTypeCheckInfo compScope = cmlEnv.newScope();
		//
		// for (PMultipleBind mbnd : bindings)
		// {
		// PType mbndType = mbnd.apply(THIS, compScope);
		// }
		//
		// if (predicate != null)
		// {
		// PType predicateType = predicate.apply(THIS, compScope);
		// }
		//
		// PType chanType = chanNameExp.apply(THIS, compScope);
		//
		// node.setType(new AVarsetExpressionType(node.getLocation(), true));
		// return node.getType();

		// FIXME copied from TypeCheckExpVisitor.caseASetCompSetExp this node is properly standard VDM

		PDefinition def = AstFactory.newAMultiBindListDefinition(node.getLocation(), node.getBindings());
		def.apply(THIS, question);

		Environment local = new FlatCheckedEnvironment(question.assistantFactory, def, question.env, question.scope);
		question = new TypeCheckInfo(question.assistantFactory, local, question.scope);

		PType etype = node.getChannelNameExp().apply(THIS, question);
		PExp predicate = node.getPredicate();

		if (predicate != null)
		{
			if (!PTypeAssistantTC.isType(predicate.apply(THIS, question), ABooleanBasicType.class))
			{
				TypeCheckerErrors.report(3159, "Predicate is not boolean", predicate.getLocation(), predicate);
			}
		}

		local.unusedCheck();
		ASetType setType = AstFactory.newASetType(node.getLocation(), etype);
		node.setType(setType);
		// node.setSetType(setType);
		return setType;
	}

	@Override
	public PType caseAFatEnumVarsetExpression(AFatEnumVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		List<PType> types = new Vector<PType>();

		LinkedList<ANameChannelExp> chanNames = node.getChannelNames();
		LinkedList<PDefinition> defs = new LinkedList<PDefinition>();
		for (ANameChannelExp chanName : chanNames)
		{
			PDefinition idDef = findDefinition(question.env, chanName.getIdentifier());
			if (idDef == null)
			{
				issueHandler.addTypeError(node, TypeErrorMessages.UNDEFINED_SYMBOL, ""
						+ chanName.getIdentifier());
				continue;
			}

			validateChannelNameDefinition(question, chanName, idDef);

			defs.add(idDef);
			types.add(idDef.getType());
		}

		PType result = TypeCheckerUtil.setType(node, types);
		result.getDefinitions().addAll(defs);
		return result;

	}

	@SuppressWarnings("static-access")
	public boolean validateChannelNameDefinition(TypeCheckInfo question,
			ANameChannelExp chanName, PDefinition idDef)
	{
		String defKind = question.assistantFactory.createPDefinitionAssistant().kind(idDef);

		switch (type)
		{
			case CHANNELSET:
			{
				if (!(idDef instanceof AChannelDefinition))
				{
					// error not a channel
					issueHandler.addTypeError(chanName, TypeErrorMessages.DEFINITION_X_BUT_FOUND_Y, "channel", defKind, idDef.getName().getName());
					return false;
				}

				AChannelDefinition def = (AChannelDefinition) idDef;

				if (def.getType().getParameters().size() < chanName.getExpressions().size())
				{
					issueHandler.addTypeError(chanName, TypeErrorMessages.TOO_MANY_CHANNEL_PARAMETERS, idDef.getName().getName());
					return false;
				}

				for (PExp exp : chanName.getExpressions())
				{
					if (exp instanceof AVariableExp)
					{
						AVariableExp var = (AVariableExp) exp;
						PDefinition varDef = question.env.findName(var.getName(), question.scope);
						if (varDef == null)
						{
							issueHandler.addTypeError(var, TypeErrorMessages.IDENTIFIER_X_NOT_IN_SCOPE, var.getName().getName());
						}
					} else
					{
						// parse error
					}
				}

				break;
			}
			case NAMESET:
			{
				if (!(idDef instanceof AInstanceVariableDefinition || idDef instanceof AAssignmentDefinition))
				{
					// error not a state
					issueHandler.addTypeError(chanName, TypeErrorMessages.DEFINITION_X_BUT_FOUND_Y, "state", defKind, idDef.getName().getName());
					return false;
				}
				break;

			}

		}

		return true;
	}

	@Override
	public PType caseAFatCompVarsetExpression(AFatCompVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		// bnd predicate
		// { a.x | x : int }

		// CmlTypeCheckInfo cmlEnv = CmlTCUtil.getCmlEnv(question);
		// if (cmlEnv == null)
		// {
		// node.setType(issueHandler.addTypeError(node, TypeErrorMessages.ILLEGAL_CONTEXT,""
		// + node)));
		// return node.getType();
		// }

		// PExp predicate = node.getPredicate();
		// LinkedList<PMultipleBind> bindings = node.getBindings();
		//
		// for (PMultipleBind mbnd : bindings)
		// {
		// PType mbndType = mbnd.apply(THIS, question);
		// }

		// if (predicate != null)
		// {
		// CmlTypeCheckInfo compScope = cmlEnv.newScope();
		// PType predicateType = predicate.apply(THIS, compScope);
		// }
		// node.setType(new AVarsetExpressionType(node.getLocation(), true));
		// return node.getType();

		PDefinition def = AstFactory.newAMultiBindListDefinition(node.getLocation(), node.getBindings());
		def.apply(THIS, question);

		Environment local = new FlatCheckedEnvironment(question.assistantFactory, def, question.env, question.scope);
		question = new TypeCheckInfo(question.assistantFactory, local, question.scope);

		PType etype = node.getChannelNameExp().apply(THIS, question);
		PExp predicate = node.getPredicate();

		if (predicate != null)
		{
			if (!PTypeAssistantTC.isType(predicate.apply(THIS, question), ABooleanBasicType.class))
			{
				TypeCheckerErrors.report(3159, "Predicate is not boolean", predicate.getLocation(), predicate);
			}
		}

		local.unusedCheck();
		ASetType setType = AstFactory.newASetType(node.getLocation(), etype);
		setType.getDefinitions().addAll(etype.getDefinitions());
		node.setType(setType);
		return node.getType();
	}

	// #OP

	@Override
	public PType caseAUnionVOpVarsetExpression(AUnionVOpVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{

		return typeCheckOpVarset(node, question);
	}

	@Override
	public PType caseAInterVOpVarsetExpression(AInterVOpVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		return typeCheckOpVarset(node, question);
	}

	@Override
	public PType caseASubVOpVarsetExpression(ASubVOpVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		return typeCheckOpVarset(node, question);
	}

	public PType typeCheckOpVarset(SVOpVarsetExpression node,
			TypeCheckInfo question) throws AnalysisException
	{
		PVarsetExpression left = node.getLeft();
		PVarsetExpression right = node.getRight();

		PType leftType = left.apply(this, question);

		PType rightType = right.apply(this, question);

		node.setType(TypeCheckerUtil.generateUnionType(node.getLocation(), leftType, rightType));

		node.getType().getDefinitions().addAll(leftType.getDefinitions());
		node.getType().getDefinitions().addAll(rightType.getDefinitions());

		return node.getType();
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
