package eu.compassresearch.core.interpreter.runtime;

import java.io.PrintWriter;

import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;

public class CmlStateContext extends CmlRootContext {

	/** The state context, if any. */
	public final CmlContext stateCtxt;

	/**
	 * Create a RootContext from the values passed.
	 *
	 * @param location The location of the context.
	 * @param title The name of the location.
	 * @param outer The context chain (not searched).
	 * @param sctxt Any state context.
	 */

	public CmlStateContext(LexLocation location, String title,
			CmlContext freeVariables, CmlContext outer, CmlContext sctxt)
	{
		super(location, title, freeVariables, outer);
		this.stateCtxt = sctxt;
	}

	public CmlStateContext(LexLocation location, String title,
			CmlContext outer, CmlContext sctxt)
	{
		this(location, title, null, outer, sctxt);
	}

	/**
	 * Create a RootContext with no outer context or state.
	 * @param location The location of the context.
	 * @param title The name of the location.
	 */

	public CmlStateContext(LexLocation location, String title)
	{
		super(location, title, null, null);
		this.stateCtxt = null;
	}

	/**
	 * Check for the name in the current context and state, and if
	 * not present search the global context. Note that the context
	 * chain is not followed.
	 *
	 * @see org.overturetool.vdmj.runtime.Context#check(org.overturetool.vdmj.lex.LexNameToken)
	 */

	@Override
	public Value check(LexNameToken name)
	{
		Value v = get(name);

		if (v != null)
		{
			return v;
		}

		if (freeVariables != null)
		{
			v = freeVariables.get(name);

			if (v != null)
			{
				return v;
			}
		}

		// A RootContext stops the name search from continuing down the
		// context chain. It first checks any state context, then goes
		// down to the global level.

		if (v == null)
		{
			if (stateCtxt != null)
			{
				v = stateCtxt.check(name);

				if (v != null)
				{
					return v;
				}
			}

			CmlContext g = getGlobal();

			if (g != this)
			{
				return g.check(name);
			}
		}

		return v;
	}

	@Override
	public String toString()
	{
		if (stateCtxt != null)
		{
			return super.toString() + "\tState visible\n";
		}
		else
		{
			return super.toString();
		}
	}

	@Override
	public void printStackTrace(PrintWriter out, boolean variables)
	{
		if (outer == null)		// Don't expand initial context
		{
			out.println("In root context of " + getVdmContext().title);
		}
		else
		{
			if (variables)
			{
    			out.print(this.format("\t", this));

    			if (stateCtxt != null)
    			{
    				out.println("\tState visible");
    			}
			}

			out.println("In root context of " + getVdmContext().title + " " + getVdmContext().location);
			outer.printStackTrace(out, false);
		}
	}
	
}
