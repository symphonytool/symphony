package eu.compassresearch.core.typechecker;

import java.util.List;

import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.analysis.intf.IQuestion;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.typechecker.Environment;
import org.overture.typechecker.assistant.TypeCheckerAssistantFactory;
import org.overture.typechecker.assistant.definition.PDefinitionListAssistantTC;
import org.overture.typechecker.assistant.definition.SClassDefinitionAssistantTC;

import eu.compassresearch.core.typechecker.assistant.CmlDefinitionCollector;
import eu.compassresearch.core.typechecker.assistant.CmlFunctionChecker;
import eu.compassresearch.core.typechecker.assistant.CmlImplicitDefinitionFinder;
import eu.compassresearch.core.typechecker.assistant.CmlKindFinder;
import eu.compassresearch.core.typechecker.assistant.CmlPDefinitionListAssistant;
import eu.compassresearch.core.typechecker.assistant.CmlSClassDefinitionAssistant;
import eu.compassresearch.core.typechecker.assistant.CmlVariableNameCollector;

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

	@Override
	public PDefinitionListAssistantTC createPDefinitionListAssistant()
	{
		return new CmlPDefinitionListAssistant(this);
	}

	@Override
	public IAnswer<String> getKindFinder()
	{
		return new CmlKindFinder(this);
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

	@Override
	public IAnswer<LexNameList> getVariableNameCollector()
	{
		return new CmlVariableNameCollector(this);
	}
}
