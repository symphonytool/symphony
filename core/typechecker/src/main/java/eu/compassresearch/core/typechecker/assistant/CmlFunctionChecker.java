package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.node.INode;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

import eu.compassresearch.ast.definitions.AFunctionsDefinition;

public class CmlFunctionChecker extends AbstractCmlFunctionChecker
{

	public CmlFunctionChecker(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1470058386572243235L;

	@Override
	public Boolean caseAFunctionsDefinition(AFunctionsDefinition node)
			throws AnalysisException
	{
		return true;
	}

	@Override
	public Boolean createNewReturnValue(INode node)
	{
		return false;
	}

}
