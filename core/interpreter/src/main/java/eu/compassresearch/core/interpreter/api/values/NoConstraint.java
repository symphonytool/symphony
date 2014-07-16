package eu.compassresearch.core.interpreter.api.values;

import org.overture.interpreter.values.Value;

public class NoConstraint implements ValueConstraint
{

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid(Value val)
	{
		return true;
	}

}
