package eu.compassresearch.core.typechecker;

import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.TypeCheckerAssistantFactory;

import eu.compassresearch.core.typechecker.assistant.CmlFunctionChecker;
import eu.compassresearch.core.typechecker.assistant.CmlImplicitDefinitionFinder;

public class CmlTypeCheckerAssistantFactory extends TypeCheckerAssistantFactory
{
	static
	{
		// FIXME: remove this when conversion to factory obtained assistants are completed.
		// init(new AstAssistantFactory());
		init(new CmlTypeCheckerAssistantFactory());
	}

	@Override
	public IQuestion<Environment> getImplicitDefinitionFinder()
	{
		return new CmlImplicitDefinitionFinder(this);
	}

	// @Override
	// public IAnswer<PType> getDefinitionTypeFinder()
	// {
	// return new CmlDefinitionTypeFinder(this);
	// }

	@Override
	public AnswerAdaptor<Boolean> getFunctionChecker()
	{
		return new CmlFunctionChecker(this);
	}
}
