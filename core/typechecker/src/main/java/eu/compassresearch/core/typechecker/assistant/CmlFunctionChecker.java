package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.node.INode;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

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
	public Boolean createNewReturnValue(INode node)
	{
		return false;
	}

}
