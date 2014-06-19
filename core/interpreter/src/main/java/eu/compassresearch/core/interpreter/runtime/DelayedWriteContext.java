package eu.compassresearch.core.interpreter.runtime;

import java.util.Map;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.DelayedUpdatableWrapper;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.util.LexNameTokenMap;

/**
 * Context that create transactions for all state changed and waits for them to be written by a call to
 * {@link DelayedWriteContext#writeChanges()}
 * 
 * @author kel
 */
public class DelayedWriteContext extends Context
{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 2677833973970244511L;

	private Map<ILexNameToken, DelayedUpdatableWrapper> obtainedValues = new LexNameTokenMap<DelayedUpdatableWrapper>();

	public DelayedWriteContext(IInterpreterAssistantFactory af,
			ILexLocation location, String title, Context outer)
	{
		super(af, location, title, outer);
	}

	public Value wrap(Value val, Object name)
	{
		if (name instanceof ILexNameToken)
		{
			Value v = obtainedValues.get((ILexNameToken) name);
			for (ILexNameToken var : obtainedValues.keySet())
			{
				// This is a relaxed check since we don't completely control the module. But any name that matched the
				// overridden state will be overridden.
				if (var.getName().equals(((ILexNameToken) name).getName()))
				{
					v = obtainedValues.get(var);
					break;
				}
			}
			if (v != null)
			{
				return v;
			}
		}

		if (val instanceof UpdatableValue
				&& !(val instanceof DelayedUpdatableWrapper))
		{
			// this is state
			DelayedUpdatableWrapper wrappedVal = new DelayedUpdatableWrapper(this, (UpdatableValue) val);

			if (name instanceof ILexNameToken)
			{
				obtainedValues.put((ILexNameToken) name, wrappedVal);
				put((ILexNameToken) name, wrappedVal);
			}
			return wrappedVal;
		}

		return val;
	}

	@Override
	public Value lookup(ILexNameToken name)
	{
		return wrap(super.lookup(name), name);
	}

	@Override
	public Value get(Object name)
	{
		return wrap(super.get(name), name);
	}

	@Override
	public Value check(ILexNameToken name)
	{
		return wrap(super.check(name), name);
	}

	@Override
	public String toString()
	{
		return "Delayed ctxt: " + super.toString() + " delayed states: "
				+ obtainedValues;
	}

	public void writeChanges() throws ValueException, AnalysisException
	{
		for (DelayedUpdatableWrapper val : obtainedValues.values())
		{
			val.set();
		}
	}
}
