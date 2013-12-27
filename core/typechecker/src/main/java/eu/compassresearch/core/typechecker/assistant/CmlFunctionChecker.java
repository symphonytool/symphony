package eu.compassresearch.core.typechecker.assistant;

import org.overture.ast.node.INode;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

public class CmlFunctionChecker extends AbstractCmlFunctionChecker
{

	public CmlFunctionChecker(ITypeCheckerAssistantFactory af)
	{
		super(af);
	}


	@Override
	public Boolean createNewReturnValue(INode node)
	{
		return false;
	}

}
