package eu.compassresearch.core.interpreter.runtime;

import org.overture.ast.lex.LexLocation;

import eu.compassresearch.core.interpreter.values.CmlObjectValue;

public abstract class CmlRootContext extends CmlContext {
	
	protected final CmlContext freeVariables;

	public CmlRootContext(
			LexLocation location, String title, CmlContext freeVariables, CmlContext outer)
	{
		super(location, title, outer);
		this.freeVariables = freeVariables;
	}

	@Override
	public CmlContext getVisibleVariables()
	{
		CmlContext visible = new CmlContext(getVdmContext().location, getVdmContext().title, null);
		visible.getVdmContext().putAll(this.getVdmContext());

		if (freeVariables != null)
		{
			visible.getVdmContext().putAll(freeVariables.getVdmContext());
		}

		return visible;
	}

	@Override
	public CmlRootContext getRoot()
	{
		return this;
	}

	@Override
	public final int getDepth()
	{
		return outer == null ? 1 : outer.getDepth() + 1;
	}

	@Override
	public final CmlContext getFrame(int depth)
	{
		return depth == 0 ? this :
			outer == null ? this :
				outer.getFrame(depth - 1);
	}

	@Override
	public CmlObjectValue getSelf()
	{
		return null;	// Overridden in ObjectContext
	}
}
