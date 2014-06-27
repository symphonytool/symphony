package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.values.Value;

public class NameValue extends Value
{
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the value
	 */
	public final ILexNameToken name;

	protected NameValue()
	{
		this.name = null;
	}

	public NameValue(ILexNameToken name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return this.name.toString();
	}

	@Override
	public boolean equals(Object other)
	{
		return this.name.equals(other);
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	@Override
	public String kind()
	{
		return "name";
	}

	@Override
	public Object clone()
	{
		return new NameValue(name.clone());
	}
}
