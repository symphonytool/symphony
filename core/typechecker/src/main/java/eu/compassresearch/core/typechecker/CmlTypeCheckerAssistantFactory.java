package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.definitions.PDefinition;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.TypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

import eu.compassresearch.core.typechecker.assistant.CmlDefinitionCollector;
import eu.compassresearch.core.typechecker.assistant.CmlFunctionChecker;
import eu.compassresearch.core.typechecker.assistant.CmlImplicitDefinitionFinder;
import eu.compassresearch.core.typechecker.assistant.CmlSClassDefinitionAssistant;

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
	
	
	@Override
	public SClassDefinitionAssistantTC createSClassDefinitionAssistant()
	{
		return new CmlSClassDefinitionAssistant(this);
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
	
	
	@Override
	public IAnswer<List<PDefinition>> getDefinitionCollector()
	{
		return new CmlDefinitionCollector(this);
	}
}
