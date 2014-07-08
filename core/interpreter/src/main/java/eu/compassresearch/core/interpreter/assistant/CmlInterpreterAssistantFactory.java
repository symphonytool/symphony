package eu.compassresearch.core.interpreter.assistant;

import java.util.List;

import org.overture.ast.analysis.AnswerAdaptor;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.analysis.intf.IAnswer;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.lex.LexNameList;
import org.overture.interpreter.assistant.InterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.NameValuePairList;

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
	
	@Override
	public QuestionAnswerAdaptor<Context, NameValuePairList> getNamedValueLister()
	{
		return new CmlNamedValueLister(this);
	}
}
