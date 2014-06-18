package eu.compassresearch.core.interpreter.runtime;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.DelayedUpdatableWrapper;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

public class DelayedWriteContext extends Context
{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 2677833973970244511L;


	private Set<DelayedUpdatableWrapper> obtainedValues = new HashSet<DelayedUpdatableWrapper>();

	public DelayedWriteContext(IInterpreterAssistantFactory af,
			ILexLocation location, String title, Context outer)
	{
		super(af, location, title, outer);
	}

	// @Override
	// public Value put(ILexNameToken key, Value value)
	// {
	// // TODO Auto-generated method stub
	// return super.put(key, value);
	// }
	
	public Value wrap(Value val)
	{
		if (val instanceof UpdatableValue)
		{
			// this is state
			DelayedUpdatableWrapper wrappedVal = new DelayedUpdatableWrapper((UpdatableValue)val);
			obtainedValues.add(wrappedVal);
			return wrappedVal;
		}

		return val;
	}

	@Override
	public Value lookup(ILexNameToken name)
	{
		return wrap( super.lookup(name));
	}


	
	@Override
	public Value get(Object name)
	{
		return wrap(super.get(name));
	}

	@Override
	public Value check(ILexNameToken name)
	{
	return wrap( super.check(name));
	}
	
	@Override
	public Context getVisibleVariables()
	{
		// TODO Auto-generated method stub
		return super.getVisibleVariables();
	}
	
	@Override
	public ObjectValue getSelf()
	{
		// TODO Auto-generated method stub
		return super.getSelf();
	}
	
	@Override
	public Collection<Value> values()
	{
		// TODO Auto-generated method stub
		return super.values();
	}
	@Override
	public String toString()
	{
	return "Delayed ctxt: " +super.toString() + " delayed states: "+obtainedValues;
	}
}
