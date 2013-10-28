package eu.compassresearch.core.typechecker.version2;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.factory.AstFactory;
import org.overture.ast.node.INode;
import org.overture.ast.types.PType;
import org.overture.typechecker.Environment;
import org.overture.typechecker.TypeCheckInfo;

import eu.compassresearch.ast.analysis.QuestionAnswerCMLAdaptor;
import eu.compassresearch.ast.definitions.AActionClassDefinition;
import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.process.AActionProcess;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.ITypeIssueHandler;
import eu.compassresearch.core.typechecker.assistant.CmlSClassDefinitionAssistant;

public class CmlCspTypeChecker extends
		QuestionAnswerCMLAdaptor<TypeCheckInfo, PType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Type checker for the PAction type tree
	 */
	private final QuestionAnswerAdaptor<TypeCheckInfo, PType> actionChecker;

	public CmlCspTypeChecker(QuestionAnswerAdaptor<TypeCheckInfo, PType> tc2,
			ITypeIssueHandler issuehandler)
	{
		this.actionChecker = new CmlActionTypeChecker(tc2, this, issuehandler);
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
	public PType caseAProcessDefinition(AProcessDefinition node,
			TypeCheckInfo question) throws AnalysisException
	{

		node.getProcess().apply(this, question);

		return AstFactory.newAVoidType(node.getLocation());
	}

	@Override
	public PType caseAActionProcess(AActionProcess node, TypeCheckInfo question)
			throws AnalysisException
	{

		Environment base = new PrivateActionClassEnvironment(question.assistantFactory, node.getActionDefinition(), question.env);
		Environment env = CmlSClassDefinitionAssistant.updateActionEnvironment(node.getActionDefinition(), base);

		// FIXME we properly need to assemble all action definitions in the process and add then to the env

		TypeCheckInfo q = new TypeCheckInfo(question.assistantFactory, env, question.scope);

		node.getActionDefinition().apply(this, q);
		node.getAction().apply(actionChecker, q);

		return super.caseAActionProcess(node, question);
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
