package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.lex.LexNameList;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AActionDefinition;
import eu.compassresearch.ast.definitions.AChannelDefinition;
import eu.compassresearch.ast.definitions.AChansetDefinition;
import eu.compassresearch.ast.definitions.AProcessDefinition;
import eu.compassresearch.ast.lex.LexNameToken;

public class CmlVariableNameCollector extends AbstractCmlVariableNameCollector
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		return new LexNameList(new LexNameToken("", node.getIdentifier()));
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

}
