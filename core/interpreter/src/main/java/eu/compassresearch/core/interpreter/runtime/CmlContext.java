package eu.compassresearch.core.interpreter.runtime;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ContextException;
import org.overture.interpreter.runtime.VdmRuntimeError;
import org.overture.interpreter.values.CPUValue;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.OperationValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.values.CmlObjectValue;

public class CmlContext {

	private Context vdmContext;
	
	/** A link to a lower level context, if present. */
	public final CmlContext outer;

	/** Non-zero if this is a pre or postcondition call. */
	public int prepost = 0;
	/** Set to the error message if prepost is set. */
	public String prepostMsg = null;
	/** Set to the operation being guarded, if any. */
	public OperationValue guardOp = null;

	/**
	 * Create a context at the given location.
	 *
	 * @param location
	 * @param title
	 * @param outer
	 */

	public CmlContext(LexLocation location, String title, CmlContext outer)
	{
		this.outer = outer; 
		vdmContext = new Context(location, title, outer != null ? outer.getVdmContext() : null);
		vdmContext.setThreadState(null, CPUValue.vCPU);
	}

	public Context getVdmContext()
	{
		return vdmContext;
	}
	
	/**
	 * Find the outermost context from this one.
	 *
	 * @return The outermost context.
	 */

	public CmlContext getGlobal()
	{
		CmlContext op = this;

		while (op.outer != null)
		{
			op = op.outer;
		}

		return op;
	}

	/**
	 * Find the nearest RootContext in the context chain.
	 */

	public CmlRootContext getRoot()
	{
		assert outer != null : "Root context is wrong type";
		return outer.getRoot();		// RootContext overrides this!
	}

	/**
	 * Make a deep copy of the context, using Value.deepCopy.
	 */

	public CmlContext deepCopy()
	{
		CmlContext below = null;

		if (outer != null)
		{
			below = outer.deepCopy();
		}

		CmlContext result = new CmlContext(this.vdmContext.location, this.vdmContext.title, below);

		for (LexNameToken var: this.keySet())
		{
			Value v = get(var);
			result.put(var, v.deepCopy());
		}

		return result;
	}

	/**
	 * Add a list of name/value pairs to this context.
	 *
	 * @param nvl A list of name/value pairs.
	 */

	public void putList(NameValuePairList nvl)
	{
		vdmContext.putList(nvl);
	}

	public void putNew(NameValuePair nvp)
	{
		vdmContext.putNew(nvp);
	}

	public void putAllNew(NameValuePairList list)
	{
		vdmContext.putAllNew(list);
	}
	
	public void putAll(Map<? extends LexNameToken, ? extends Value> m)
	{
		vdmContext.putAll(m);
	}
	
	public void put(LexNameToken key, Value value)
	{
		vdmContext.put(key, value);
	}
	
	public Set<LexNameToken> keySet()
	{
		return vdmContext.keySet();
		
	}

	/**
	 * Get a name, taking type overloading into account. If we use the
	 * superclass method, different names are considered different,
	 * because the map is driven by the names' hashCodes. The equals
	 * method of LexNameToken makes a TypeComparator check, which is
	 * what we need. But we try a simple super.get() first.
	 *
	 * TODO Slow though.
	 */

	public Value get(Object name)
	{
		return vdmContext.get(name);
	}

	/**
	 * Get all visible names from this Context, with more visible
	 * values overriding those below.
	 *
	 * @return	A new Context with all visible names.
	 */

	public CmlContext getVisibleVariables()
	{
		CmlContext visible = new CmlContext(this.vdmContext.location, this.vdmContext.title, null);

		if (outer != null)
		{
			visible.getVdmContext().putAll(outer.getVisibleVariables().getVdmContext());
		}

		visible.getVdmContext().putAll(this.getVdmContext());	// Overriding anything below here
		return visible;
	}

	/**
	 * Get the value for a given name. This searches outer contexts, if
	 * any are present.
	 *
	 * @param name The name to look for.
	 * @return The value of the name, or null.
	 */

	public Value check(LexNameToken name)
	{
		Value v = get(name);

		if (v == null)
		{
			if (outer != null)
			{
				return outer.check(name);
			}
		}

		return v;
	}

	/**
	 * Locate the Context in a chain that contains a name, if any.
	 */

	public CmlContext locate(LexNameToken name)
	{
		Value v = get(name);

		if (v == null)
		{
			if (outer != null)
			{
				return outer.locate(name);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return this;
		}
	}

	/**
	 * Return the value of a name, else fail. If the name is not present, a
	 * {@link ContextException} is thrown.
	 *
	 * @param name The name to look for.
	 * @return The value of the name.
	 */

	public <T extends Value> T lookup(LexNameToken name)
	{
		T v = (T)check(name);

		if (v == null)
		{
			VdmRuntimeError.abort(name.getLocation(),4034, "Name '" + name + "' not in scope", this.getVdmContext());
		}

		return v;
	}

	@Override
	public String toString()
	{
		return format("", this);
	}

	protected String format(String indent, CmlContext what)
	{
		StringBuilder sb = new StringBuilder();

		for (LexNameToken name: what.keySet())
		{
			sb.append(indent + name + " = " +
				what.get(name).toShortString(100) + "\n");
		}

		return sb.toString();
	}

	public void printStackTrace(PrintWriter out, boolean variables)
	{
		if (outer == null)		// Don't expand initial context
		{
			out.println("In context of " + getTitle());
		}
		else
		{
			if (variables)
			{
				out.print(this.format("\t", this));
			}

			out.println("In context of " + getTitle() + " " + getLocation());
			outer.printStackTrace(out, variables);
		}
	}

	public int getDepth()
	{
		return outer == null ? 0 : outer.getDepth();	// NB only roots count
	}

	public CmlContext getFrame(int depth)
	{
		return outer == null ? this : outer.getFrame(depth);
	}

	public CmlObjectValue getSelf()
	{
		return outer == null ? null : outer.getSelf();
		
	}
	
	public String getTitle()
	{
		return vdmContext.title;
	}
	
	public LexLocation getLocation()
	{
		return vdmContext.location;
	}

//	public void setPrepost(int prepost, String prepostMsg)
//	{
//		this.prepost = prepost;
//		this.prepostMsg = prepostMsg;
//	}
	
}
