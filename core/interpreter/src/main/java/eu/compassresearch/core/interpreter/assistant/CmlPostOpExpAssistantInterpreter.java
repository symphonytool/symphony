package eu.compassresearch.core.interpreter.assistant;

import org.overture.ast.expressions.APostOpExp;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.assistant.expression.APostOpExpAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.FunctionValue;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueMap;

import eu.compassresearch.ast.lex.CmlLexNameToken;

public class CmlPostOpExpAssistantInterpreter extends
		APostOpExpAssistantInterpreter
{

	public CmlPostOpExpAssistantInterpreter(IInterpreterAssistantFactory af)
	{
		super(af);
	}

	@Override
	public void populate(APostOpExp node, Context ctxt, String classname,
			ValueMap oldvalues) throws ValueException
	{
		for (Value var : oldvalues.keySet())
		{
			String name = var.stringValue(ctxt);
			Value val = oldvalues.get(var);

			if (!(val instanceof FunctionValue)
					&& !(val instanceof OperationValue))
			{
				/*
				 * Since modules are not being handled in the same way as overture, and therefore we have to add the
				 * name twice. one with module and one without. This is not optimal but it has to be like this to work.
				 * See issue 291 for an example that require this.
				 */
				ILexNameToken oldname = new CmlLexNameToken("", name, node.getLocation(), true, false);
				ctxt.put(oldname, val);
				oldname = new CmlLexNameToken(classname, name, node.getLocation(), true, false);
				ctxt.put(oldname, val);
			}
		}
	}
}
