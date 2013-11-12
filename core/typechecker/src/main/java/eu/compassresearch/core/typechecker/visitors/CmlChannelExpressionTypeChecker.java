package eu.compassresearch.core.typechecker.visitors;

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
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;
import org.overture.typechecker.TypeCheckInfo;
import org.overture.typechecker.TypeComparator;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.expressions.AComprehensionRenameChannelExp;
import eu.compassresearch.ast.expressions.AEnumerationRenameChannelExp;
import eu.compassresearch.ast.expressions.ANameChannelExp;
import eu.compassresearch.ast.messages.InternalException;
import eu.compassresearch.ast.patterns.ARenamePair;
import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.assistant.TypeCheckerUtil;

public class CmlChannelExpressionTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

	@SuppressWarnings("deprecation")
	public CmlChannelExpressionTypeChecker(
			IQuestionAnswer<TypeCheckInfo, PType> root,
			ITypeIssueHandler issueHandler)
	{
		super(root);
		this.issueHandler = issueHandler;
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

		Iterator<PType> iterator = chanConcreteType.getParameters().iterator();
		PType singleChanConcType = null;
		for (PExp expression : node.getExpressions())
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
				return AstFactory.newAUnknownType(node.getLocation());
			}
		}
		chanConcreteType.getDefinitions().add(chanDef);
		node.setType(chanConcreteType);
		return node.getType();
	}

	@Override
	public PType caseAComprehensionRenameChannelExp(
			AComprehensionRenameChannelExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		throw new InternalException(0, "AComprehensionRenameChannelExp is not implemented");
	}

	@Override
	public PType caseAEnumerationRenameChannelExp(
			AEnumerationRenameChannelExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		List<PType> types = new Vector<PType>();
		LinkedList<ARenamePair> pairs = node.getRenamePairs();
		for (ARenamePair p : pairs)
		{
			ANameChannelExp from = p.getFrom();
			ANameChannelExp to = p.getTo();

			PDefinition fromChanDef = TypeCheckerUtil.findDefinition(from.getIdentifier(), question.env);
			if (fromChanDef == null)
			{
				issueHandler.addTypeError(from, TypeErrorMessages.UNDEFINED_SYMBOL, ""
						+ from);
				return null;
			}

			to.setType(fromChanDef.getType());
			types.add(fromChanDef.getType());
			// cmlEnv.addChannel(to.getIdentifier(), fromChanDef);
		}

		node.setType(TypeCheckerUtil.generateUnionType(node.getLocation(), types));
		return node.getType();

	}

	@Override
	public PType createNewReturnValue(INode node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PType createNewReturnValue(Object node, TypeCheckInfo question)
			throws AnalysisException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
