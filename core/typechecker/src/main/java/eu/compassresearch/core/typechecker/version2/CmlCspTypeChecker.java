package eu.compassresearch.core.typechecker.version2;

import static eu.compassresearch.core.typechecker.util.CmlTCUtil.successfulType;

import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.expressions.PExp;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.intf.lex.ILexIdentifierToken;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.lex.LexNameToken;
import org.overture.ast.node.INode;
import org.overture.ast.typechecker.NameScope;
import org.overture.ast.types.ASeq1SeqType;
import org.overture.ast.types.ASetType;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.actions.PAction;
import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.declarations.AExpressionSingleDeclaration;
import eu.compassresearch.ast.declarations.ATypeSingleDeclaration;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
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
	 * Type checker for channels and channel sets
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> channelChecker;

	/**
	 * The checker for processes
	 */
	private final CmlProcessTypeChecker processChecker;

	/**
	 * The checker for vdm
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> vdmChecker;

	public CmlCspTypeChecker(QuestionAnswerAdaptor<TypeCheckInfo, PType> tc2,
			ITypeIssueHandler issuehandler)
	{
		this.issueHandler = issuehandler;
		this.actionChecker = new CmlActionTypeChecker(tc2, this, issuehandler);
		this.channelChecker = new CmlChannelTypeChecker(tc2, this, issuehandler);
		this.processChecker = new CmlProcessTypeChecker(tc2, this, issuehandler);
		this.vdmChecker = tc2;
	}

	@Override
	public PType defaultPSource(PSource node, TypeCheckInfo question)
			throws AnalysisException
	{
		for (PDefinition d : node.getParagraphs())
		{
			d.apply(this, question);
		}
		return AstFactory.newAVoidType(null);
	}

	@Override
	public PType caseAChannelDefinition(AChannelDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.apply(channelChecker, question);
	}

	@Override
	public PType caseAChansetDefinition(AChansetDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{
		return node.apply(channelChecker, question);
	}

	@Override
	public PType caseAProcessDefinition(AProcessDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		node.getProcess().apply(this, question);

		return AstFactory.newAVoidType(node.getLocation());
	}

	@Override
	public PType defaultPAction(PAction node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(actionChecker, question);
	}
	
	@Override
	public PType defaultPProcess(PProcess node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(processChecker,question);
	}
	
	@Override
	public PType defaultPExp(PExp node, TypeCheckInfo question)
			throws AnalysisException
	{
		return node.apply(vdmChecker,question);
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
			PType typetype = type.apply(THIS, question);
			if (!successfulType(typetype))
			{
				node.setType(issueHandler.addTypeError(type, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(""
						+ type)));
				return node.getType();
			}

			LinkedList<ILexIdentifierToken> ids = node.getIdentifiers();
			List<PDefinition> defs = new LinkedList<PDefinition>();
			for (ILexIdentifierToken id : ids)
			{

				LexNameToken idName = new LexNameToken("", id);
				ALocalDefinition localDef = AstFactory.newALocalDefinition(node.getLocation(), idName, NameScope.LOCAL, node.getType());
				defs.add(localDef);
			}
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
		if (!successfulType(expressionType))
			return issueHandler.addTypeError(expressionType, TypeErrorMessages.COULD_NOT_DETERMINE_TYPE.customizeMessage(expression
					+ ""));

		List<PDefinition> defs = new LinkedList<PDefinition>();
		LinkedList<ILexIdentifierToken> identifiers = node.getIdentifiers();

		if (expressionType instanceof ASetType)
		{
			for (ILexIdentifierToken id : identifiers)
			{
				ILexNameToken name = null;
				if (id instanceof LexNameToken)
					name = (LexNameToken) id;
				else
					name = new LexNameToken("", id.getName(), id.getLocation());

				ASetType expressionSetType = (ASetType) expressionType;
				ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSetType.getSetof());
				defs.add(localDef);
			}

		} else if (expressionType instanceof ASeq1SeqType)
		{
			for (ILexIdentifierToken id : identifiers)
			{
				ILexNameToken name = null;
				if (id instanceof LexNameToken)
					name = (LexNameToken) id;
				else
					name = new LexNameToken("", id.getName(), id.getLocation());

				ASeq1SeqType expressionSeqType = (ASeq1SeqType) expressionType;
				ALocalDefinition localDef = AstFactory.newALocalDefinition(id.getLocation(), name, node.getNameScope(), expressionSeqType.getSeqof());
				defs.add(localDef);
			}
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
