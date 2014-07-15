package eu.compassresearch.core.interpreter.runtime;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.node.INode;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.DelayedUpdatableWrapper;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;
import org.overture.typechecker.util.LexNameTokenMap;

import eu.compassresearch.ast.actions.AExternalChoiceAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismParallelAction;
import eu.compassresearch.ast.actions.AGeneralisedParallelismReplicatedAction;
import eu.compassresearch.ast.actions.AInterleavingParallelAction;
import eu.compassresearch.ast.process.AExternalChoiceProcess;
import eu.compassresearch.ast.process.AGeneralisedParallelismProcess;

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

	/**
	 * A filter that accepts none filtered objects
	 * 
	 * @author kel
	 */
	public static interface INameFilter
	{
		/**
		 * Accepts none filtered objects
		 * 
		 * @param name
		 *            the object to by checked against the filter
		 * @return returns true if accepted or false if filtered
		 */
		public boolean accept(ILexNameToken name);
	}

	protected Map<ILexNameToken, DelayedUpdatableWrapper> obtainedValues = new LexNameTokenMap<DelayedUpdatableWrapper>();
	protected final INode owner;

	boolean disable = false;

	public DelayedWriteContext(INode owner, IInterpreterAssistantFactory af,
			ILexLocation location, String title, Context outer)
	{
		super(af, location, title, outer);
		this.owner = owner;
	}

	public DelayedWriteContext(INode owner, IInterpreterAssistantFactory af,
			ILexLocation location, String title, Context outer,
			Map<ILexNameToken, DelayedUpdatableWrapper> obtainedValues)
	{
		super(af, location, title, outer);
		this.obtainedValues.putAll(obtainedValues);
		this.owner = owner;
	}

	protected void disable()
	{
		this.disable = true;
	}

	public boolean isDisabled()
	{
		return this.disable;
	}

	public Value wrap(Value val, Object name)
	{
		if (disable)
		{
			return val;
		}

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
				)//&& !(val instanceof DelayedUpdatableWrapper))
		{
			// this is state
			DelayedUpdatableWrapper wrappedVal = new DelayedUpdatableWrapper((UpdatableValue) val);

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
		return "Delayed ctxt for '" + formatNode(this.owner) + "': "
				+ " \n\tdelayed states: " + obtainedValues + 
					 " \n\tdisabled: " + isDisabled()+ "\n"
				+ super.toString();
	}

	private static String formatNode(INode owner)
	{
		String name = owner.getClass().getSimpleName();
		
		if(name.equals("AExternalChoiceAction")||name.equals("AExternalChoiceProcess"))
		{
			return "[]";
		}else if(name.equals("AInterleavingParallelAction")||name.equals("AInterleavingParallelProcess"))
		{
			return "|||";
		}else if(name.equals("AGeneralisedParallelismParallelAction")||name.equals("AGeneralisedParallelismProcess"))
		{
			return "[||]";
		}else if(name.contains("Replicated"))
		{
			return "replicated something";
		}
		
		return (""+owner).replace('\n', ' ').replace('\t', ' ');
	}

	public void writeChanges() throws ValueException, AnalysisException
	{
		writeChanges(null);
	}

	public void writeChanges(INameFilter filter) throws ValueException,
			AnalysisException
	{
		// for (DelayedUpdatableWrapper val : obtainedValues.values())
		// {
		// val.set();
		// }

		Set<ILexNameToken> toBeRemoved = new HashSet<ILexNameToken>();
		for (Entry<ILexNameToken, Value> entry : super.entrySet())
		{
			if (entry.getValue() instanceof DelayedUpdatableWrapper)
			{
				DelayedUpdatableWrapper upVal = (DelayedUpdatableWrapper) entry.getValue();
				ILexNameToken key = entry.getKey();
				toBeRemoved.add(key);

				if (filter == null || filter.accept(key))
				{
					upVal.set();
				}
			}
		}

		for (ILexNameToken key : toBeRemoved)
		{
			remove(key);
		}
		disable();
	}

	public static void setOuter(Context source, Context newOuter)
	{
		for (Field f : source.getClass().getFields())
		{
			if (f.getName().equals("outer"))
			{
				f.setAccessible(true);
				try
				{
					f.set(source, new Context(newOuter.assistantFactory, newOuter.location, "", newOuter));
					return;
				} catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Context deepCopy()
	{
		Context below = null;

		if (outer != null)
		{
			below = outer.deepCopy();
		}

		Map<ILexNameToken, DelayedUpdatableWrapper> resultObtainedValues = new LexNameTokenMap<DelayedUpdatableWrapper>();
		for (Entry<ILexNameToken, DelayedUpdatableWrapper> entry : this.obtainedValues.entrySet())
		{
			resultObtainedValues.put(entry.getKey(), (DelayedUpdatableWrapper) entry.getValue().deepCopy());
		}

		Context result = new DelayedWriteContext(owner, assistantFactory, location, title, below, resultObtainedValues);

		result.threadState = threadState;

		for (ILexNameToken var : keySet())
		{
			Value v = get(var);
			result.put(var, v.deepCopy());
		}

		return result;
	}

	public boolean isOwnedBy(INode node)
	{
		return owner == node;
	}
}
