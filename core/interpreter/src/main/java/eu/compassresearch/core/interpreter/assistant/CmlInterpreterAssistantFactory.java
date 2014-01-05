package eu.compassresearch.core.interpreter.assistant;

import java.util.List;

import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.interpreter.assistant.InterpreterAssistantFactory;

import eu.compassresearch.core.typechecker.assistant.CmlDefinitionCollector;
import eu.compassresearch.core.typechecker.assistant.CmlFunctionChecker;
import eu.compassresearch.core.typechecker.assistant.CmlKindFinder;
import eu.compassresearch.core.typechecker.assistant.CmlVariableNameCollector;

public class CmlInterpreterAssistantFactory extends InterpreterAssistantFactory
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static
	{
		// FIXME: remove this when conversion to factory obtained assistants are completed.
		// init(new AstAssistantFactory());
		init(new CmlInterpreterAssistantFactory());
	}

	// @Override
	// public SClassDefinitionAssistantTC createSClassDefinitionAssistant()
	// {
	// return new CmlSClassDefinitionAssistant(this);
	// }
	//
	// @Override
	// public PDefinitionListAssistantTC createPDefinitionListAssistant()
	// {
	// return new CmlPDefinitionListAssistant(this);
	// }

	@Override
	public IAnswer<String> getKindFinder()
	{
		return new CmlKindFinder(this);
	}

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
