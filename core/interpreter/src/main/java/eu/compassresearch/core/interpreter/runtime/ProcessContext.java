/*******************************************************************************
 *
 *	Copyright (C) 2008 Fujitsu Services Ltd.
 *
 *	Author: Nick Battle
 *
 *	This file is part of VDMJ.
 *
 *	VDMJ is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	VDMJ is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package eu.compassresearch.core.interpreter.runtime;

import java.io.PrintWriter;

import org.overture.ast.lex.LexLocation;
import org.overture.ast.lex.LexNameToken;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.values.CmlObjectValue;
import eu.compassresearch.core.interpreter.values.ProcessObjectValue;


/**
 * A root context for process method invocations.
 */

@SuppressWarnings("serial")
public class ProcessContext extends CmlRootContext
{
	//public final ObjectValue self;
	public final ProcessObjectValue self;

	/**
	 * Create an ObjectContext from the values passed.
	 *
	 * @param location The location of the context.
	 * @param title The name of the location.
	 * @param outer The context chain (not searched).
	 * @param self The object context.
	 */

	public ProcessContext(
		LexLocation location, String title, CmlContext outer, ProcessObjectValue self)
	{
		super(location, title, null, outer);
		this.self = self;
	}

	@Override
	public CmlContext deepCopy()
	{
		CmlContext below = null;

		if (outer != null)
		{
			below = (CmlContext)outer.deepCopy();
		}

		//FIXME self should be deep copied
		CmlContext result = new ProcessContext(getLocation(), getTitle(), below, self);
			//new ProcessContext(location, title, freeVariables, below, self.deepCopy());

		for (LexNameToken var: keySet())
		{
			Value v = get(var);
			result.put(var, v.deepCopy());
		}

		return result;
	}

	/**
	 * Check for the name in the current context and self, and if
	 * not present search the global context. Note that the context
	 * chain is not followed.
	 *
	 * @see org.overturetool.vdmj.runtime.Context#check(org.overturetool.vdmj.lex.LexNameToken)
	 */

	@Override
	public Value check(LexNameToken name)
	{
		// A RootContext stops the name search from continuing down the
		// context chain. It first checks any local context, then it
		// checks the "self" context, then it goes down to the global level.

		Value v = get(name);		// Local variables

		if (v != null)
		{
			return v;
		}

		v = null;//self.get(name, name.explicit);

		if (v != null)
		{
			return v;
		}

		CmlContext g = getGlobal();

		if (g != this)
		{
			return g.check(name);
		}

		return v;
	}

	@Override
	public String toString()
	{
		return super.toString();	// Self there anyway ...+ self.toString();
	}

	@Override
	public void printStackTrace(PrintWriter out, boolean variables)
	{
		if (outer == null)		// Don't expand initial context
		{
			out.println("In process context of " + getTitle());
		}
		else
		{
			if (variables)
			{
    			out.print(this.format("\t", this));
			}

			out.println("In process context of " + getTitle() + " " + getLocation());
			outer.printStackTrace(out, false);
		}
	}
	
	public CmlObjectValue getSelf() {
		return self;
	}

	
}
