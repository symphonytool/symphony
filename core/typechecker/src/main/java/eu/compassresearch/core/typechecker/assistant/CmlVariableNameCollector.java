package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameList;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelBlockDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetBlockDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AConfigDefinition;
import eu.compassresearch.ast.definitions.AFunctionBlockDefinition;
import eu.compassresearch.ast.definitions.ANamesetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.definitions.ATypeBlockDefinition;
import eu.compassresearch.ast.definitions.AValueBlockDefinition;
import eu.compassresearch.ast.lex.CmlLexNameToken;

public class CmlVariableNameCollector extends AbstractCmlVariableNameCollector
{


	public CmlVariableNameCollector(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	@Override
	public LexNameList caseAChannelDefinition(AChannelDefinition node)
			throws AnalysisException
	{
		return new LexNameList(node.getName());
	}

	@Override
	public LexNameList caseAChansetDefinition(AChansetDefinition node)
			throws AnalysisException
	{
		CmlLexNameToken name = new CmlLexNameToken("", node.getIdentifier());
		name.parent(node);
		return new LexNameList(name);
	}

	@Override
	public LexNameList caseAProcessDefinition(AProcessDefinition node)
			throws AnalysisException
	{
		return new LexNameList(node.getName());
	}

	@Override
	public LexNameList caseAActionDefinition(AActionDefinition node)
			throws AnalysisException
	{
		return new LexNameList(node.getName());
	}
	
	@Override
	public LexNameList caseANamesetDefinition(ANamesetDefinition node)
			throws AnalysisException
	{
		CmlLexNameToken name = new CmlLexNameToken("", node.getIdentifier());
		name.parent(node);
		return new LexNameList(name);
	}

	@Override
	public LexNameList caseAConfigDefinition(AConfigDefinition node)
			throws AnalysisException {
		return new LexNameList();
	}

	@Override
	public LexNameList caseATypeBlockDefinition(ATypeBlockDefinition node)
			throws AnalysisException {
		return new LexNameList();
	}

	@Override
	public LexNameList caseAChannelBlockDefinition(AChannelBlockDefinition node)
			throws AnalysisException {
		return new LexNameList();
	}

	@Override
	public LexNameList caseAChansetBlockDefinition(AChansetBlockDefinition node)
			throws AnalysisException {
		return new LexNameList();
	}

	@Override
	public LexNameList caseAFunctionBlockDefinition(
			AFunctionBlockDefinition node) throws AnalysisException {
		return new LexNameList();
	}

	@Override
	public LexNameList caseAValueBlockDefinition(AValueBlockDefinition node)
			throws AnalysisException {
		return new LexNameList();
	}

}
