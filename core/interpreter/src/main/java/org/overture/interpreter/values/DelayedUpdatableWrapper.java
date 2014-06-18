package org.overture.interpreter.values;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;

public class DelayedUpdatableWrapper extends UpdatableValue
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DelayedUpdatableWrapper(Value value,
			ValueListenerList listeners, PType type)
	{
		super(value, listeners, type);
	}

	public DelayedUpdatableWrapper(UpdatableValue val)
	{
		this(val, val.listeners, val.restrictedTo);
	}

	

@Override
public void set(ILexLocation location, Value newval, Context ctxt)
		throws AnalysisException
{
	// TODO Auto-generated method stub
	System.err.println("Setting value("+toShortString(10)+") to "+newval);
	super.set(location, newval, ctxt);
}
}
