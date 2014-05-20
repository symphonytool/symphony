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

	private final ITypeIssueHandler issueHandler;// = VanillaFactory.newCollectingIssueHandle();

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
		
		if(node.getAncestor(ACompVarsetExpression.class)!=null)
		{
			//expression size must match declared
			final int argSize = node.getExpressions().size();
			final int dclTypeSize = chanConcreteType.getParameters().size();
			if(argSize > dclTypeSize)
			{
				issueHandler.addTypeError(node, TypeErrorMessages.COMMUNICATION_TOO_MANY_ARGUMENTS, chanDef.getName().getName()
						,""+argSize,""+dclTypeSize);
			}else if(node.getExpressions().size() < chanConcreteType.getParameters().size())
			{
				issueHandler.addTypeError(node, TypeErrorMessages.COMMUNICATION_TOO_FEW_ARGUMENTS, chanDef.getName().getName()
						,""+argSize,""+dclTypeSize);
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
		issueHandler.addTypeError(node, TypeErrorMessages.UNSUPPORTED_CONSTRUCT, ""
				+ node);
		return AstFactory.newAUnknownType(node.getLocation());
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
			List<PType> pairTypes = new Vector<PType>();

			for (ANameChannelExp chanExp : new ANameChannelExp[] { from, to })
			{
				PDefinition chanDef = TypeCheckerUtil.findDefinition(chanExp.getIdentifier(), question.env);
				if (chanDef == null)
				{
					issueHandler.addTypeError(from, TypeErrorMessages.UNDEFINED_SYMBOL, ""
							+ from);
					return null;
				}

				pairTypes.add(chanDef.getType());
			}

			if (pairTypes.size() == 2)
			{
				final AChannelType toType = (AChannelType) pairTypes.get(1);
				final AChannelType fromType = (AChannelType) pairTypes.get(0);
				if (!TypeComparator.compatible(toType.getParameters(), fromType.getParameters(), question.assistantFactory))
				{
					issueHandler.addTypeError(from, TypeErrorMessages.INCOMPATIBLE_TYPE, formatChannelType(toType), formatChannelType(fromType));
					return AstFactory.newAUnknownType(node.getLocation());
				}
			}

			types.addAll(pairTypes);

		}

		node.setType(TypeCheckerUtil.generateUnionType(question.assistantFactory, node.getLocation(), types));
		return node.getType();

	}

	private String formatChannelType(AChannelType t)
	{
		String typeMsg = "" + t;
		if (typeMsg.trim().isEmpty())
		{
			typeMsg = "()";
		}

		return typeMsg;
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
