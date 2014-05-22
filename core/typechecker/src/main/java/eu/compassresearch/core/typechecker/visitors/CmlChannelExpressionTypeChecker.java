package eu.compassresearch.core.typechecker.visitors;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.intf.IQuestionAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.FlatCheckedEnvironment;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeCheckerErrors;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.expressions.ACompVarsetExpression;
import eu.compassresearch.ast.expressions.AComprehensionRenameChannelExp;
import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.patterns.ARenamePair;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;

public class CmlChannelExpressionTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	private final ITypeIssueHandler issueHandler;

	public CmlChannelExpressionTypeChecker(
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler)
	{
		super(root);
		this.issueHandler = issueHandler;
	}

	private void checkChannelExpArgs(ANameChannelExp node, String channelName,
			AChannelType chanConcreteType, List<PExp> args,
			boolean ignoreTooFewArgs, TypeCheckInfo question)
			throws AnalysisException
	{
		// expression size must match declared
		final int dclTypeSize = chanConcreteType.getParameters().size();
		if (args.size() > dclTypeSize)
		{
			issueHandler.addTypeError(node, TypeErrorMessages.COMMUNICATION_TOO_MANY_ARGUMENTS, channelName, ""
					+ args.size(), "" + dclTypeSize);
		} else if (!ignoreTooFewArgs
				&& args.size() < chanConcreteType.getParameters().size())
		{
			issueHandler.addTypeError(node, TypeErrorMessages.COMMUNICATION_TOO_FEW_ARGUMENTS, channelName, ""
					+ args.size(), "" + dclTypeSize);
		}

		Iterator<PType> iterator = chanConcreteType.getParameters().iterator();
		PType singleChanConcType = null;

		for (PExp expression : args)
		{
			if (iterator.hasNext())
			{
				singleChanConcType = iterator.next();
			} else
			{
				singleChanConcType = AstFactory.newAUnknownType(chanConcreteType.getLocation());
			}

			PType expressionType = expression.apply(THIS, question);

			if (!TypeComparator.compatible(singleChanConcType, expressionType))
			{
				issueHandler.addTypeError(expression, TypeErrorMessages.INCOMPATIBLE_TYPE, ""
						+ singleChanConcType, "" + expressionType);
			}
		}
	}

	@Override
	public PType caseANameChannelExp(ANameChannelExp node,
			TypeCheckInfo question) throws AnalysisException
	{

		ILexIdentifierToken channelId = node.getIdentifier();
		PDefinition chanDef = TypeCheckerUtil.findDefinition(channelId, question.env);
		if (!(chanDef instanceof AChannelDefinition))
		{
			issueHandler.addTypeError(node, TypeErrorMessages.EXPECTED_A_CHANNEL, channelId
					+ "");
			return AstFactory.newAUnknownType(node.getLocation());
		}

		AChannelType chanConcreteType = ((AChannelDefinition) chanDef).getType();

		boolean allArgsMustMatchTypes = true;

		if (node.getAncestor(ACompVarsetExpression.class) != null)
		{
			allArgsMustMatchTypes = false;
		}

		checkChannelExpArgs(node, chanDef.getName().getName(), chanConcreteType, node.getExpressions(), allArgsMustMatchTypes, question);

		chanConcreteType.getDefinitions().add(chanDef);
		node.setType(chanConcreteType);
		return node.getType();
	}

	@Override
	public PType caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		List<PType> types = new Vector<PType>();
		List<ARenamePair> pairs = Arrays.asList(new ARenamePair[] { node.getRenamePairs() });

		PDefinition def = AstFactory.newAMultiBindListDefinition(node.getLocation(), node.getBindings());
		def.apply(THIS, question);

		Environment local = new FlatCheckedEnvironment(question.assistantFactory, def, question.env, question.scope);
		TypeCheckInfo renameQuestion = new TypeCheckInfo(question.assistantFactory, local, question.scope);

		PExp predicate = node.getPredicate();

		if (predicate != null)
		{
			if (!question.assistantFactory.createPTypeAssistant().isType(predicate.apply(THIS, renameQuestion), ABooleanBasicType.class))
			{
				TypeCheckerErrors.report(3159, "Predicate is not boolean", predicate.getLocation(), predicate);
			}
		}

		local.unusedCheck();

		checkRenamePairs(renameQuestion, types, pairs);

		node.setType(TypeCheckerUtil.generateUnionType(question.assistantFactory, node.getLocation(), types));
		return node.getType();
	}

	@Override
	public PType caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		List<PType> types = new Vector<PType>();
		LinkedList<ARenamePair> pairs = node.getRenamePairs();
		checkRenamePairs(question, types, pairs);

		node.setType(TypeCheckerUtil.generateUnionType(question.assistantFactory, node.getLocation(), types));
		return node.getType();

	}

	protected void checkRenamePairs(TypeCheckInfo question, List<PType> types,
			List<ARenamePair> pairs) throws AnalysisException
	{
		for (ARenamePair p : pairs)
		{
			ANameChannelExp from = p.getFrom();
			ANameChannelExp to = p.getTo();
			List<PType> pairTypes = new Vector<PType>();

			for (ANameChannelExp chanExp : new ANameChannelExp[] { from, to })
			{
				PDefinition chanDef = TypeCheckerUtil.findDefinition(chanExp.getIdentifier(), question.env);
				if (chanDef == null)
				{
					issueHandler.addTypeError(from, TypeErrorMessages.UNDEFINED_SYMBOL, ""
							+ from);

					// throw new TypeCheckException(TypeErrorMessages.UNDEFINED_SYMBOL.customizeMessage("" + from),
					// from.getLocation(), from);
					continue;
				}

				final AChannelType channelType = (AChannelType) chanDef.getType();
				checkChannelExpArgs(chanExp, chanDef.getName().getName(), channelType, chanExp.getExpressions(), true, question);

				PType remainingType = null;

				final LinkedList<PType> channelParameterTypes = channelType.getParameters();
				if (channelParameterTypes.isEmpty())
				{
					remainingType = AstFactory.newAVoidType(channelType.getLocation());
				} else if (chanExp.getExpressions().isEmpty())
				{
					remainingType = createChannelType(channelParameterTypes, channelType.getLocation());
				} else if (channelParameterTypes.size() >= chanExp.getExpressions().size())
				{
					List<PType> rest = channelParameterTypes.subList(chanExp.getExpressions().size(), channelParameterTypes.size());

					remainingType = createChannelType(rest, channelType.getLocation());

				} else
				{
					remainingType = AstFactory.newAUnknownType(channelType.getLocation());
				}

				pairTypes.add(remainingType);
			}

			if (pairTypes.size() == 2)
			{
				final PType fromType = pairTypes.get(0);
				final PType toType = pairTypes.get(1);

				if (!TypeComparator.compatible(toType, fromType))
				{
					issueHandler.addTypeError(from, TypeErrorMessages.INCOMPATIBLE_TYPE, toType
							+ "", fromType + "");
				}
			}

			types.addAll(pairTypes);

		}
	}

	private PType createChannelType(List<PType> types, ILexLocation location)
	{
		PType remainingType = null;
		if (types.size() > 1)
		{
			remainingType = AstFactory.newAProductType(location, types);
		} else if (types.size() == 1)
		{
			remainingType = types.get(0);
		} else
		{
			remainingType = AstFactory.newAVoidType(location);
		}
		return remainingType;

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
