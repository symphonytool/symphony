package eu.compassresearch.core.interpreter.utility;

import org.overture.ast.expressions.PExp;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.VdmRuntime;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.AbstractCmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;

public class ValueParser
{
	/**
	 * Utility method to parse expressions into Values
	 * 
	 * @param expectedType
	 *            the expected type of the expression
	 * @param ctxt
	 *            the context that should be used to obtain the value
	 * @param expressionString
	 *            the syntax string representing the expression
	 * @return the new value
	 * @throws Exception
	 *             a InterpreterRuntimeException is shown if the type check fails, expect other Exceptions if the parser
	 *             fails
	 */
	public static Value parse(PType expectedType, Context ctxt,
			String expressionString) throws Exception
	{
		CmlInterpreter ip = AbstractCmlInterpreter.getInstance();
		PExp exp = ip.parseExpression(expressionString, "IO");

		PType expType = ip.typeCheck(exp);
		if (!ctxt.assistantFactory.getTypeComparator().compatible(expectedType, expType))
		{
			throw new InterpreterRuntimeException("Wrong expression type read from console. Expected: "
					+ expectedType + " Actual: " + expType);
		}

		Value val = exp.apply(VdmRuntime.getExpressionEvaluator(), ctxt);
		return val;
	}
}
