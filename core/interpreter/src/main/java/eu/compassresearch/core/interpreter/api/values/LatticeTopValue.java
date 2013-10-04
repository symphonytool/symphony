package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

/**
 * Represents any value of a specific type. This corresponds to the top element of the lattice where the possible values
 * of the type are in the middle and AnyValue are the top and Undefined are the bottom. E.g for the nat type:
 * LatticeTopValue / | \ 1,2,....,infinite \ | / Undefined
 * 
 * @author akm
 */
public class LatticeTopValue extends Value implements ImpreciseValue
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2580417766834032898L;
	// FIXME unicode seems to be causing problems on Windows and Mac
	// private static final String topString = "\u22A4";
	private static final String topString = "?";
	private PType type;

	public LatticeTopValue(PType type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return topString;
	}

	@Override
	public boolean isResolvable(Value val)
	{
		return true;
	}

	@Override
	public boolean equals(Object other)
	{
		return other instanceof LatticeTopValue;
	}

	@Override
	public int hashCode()
	{
		return topString.hashCode();
	}

	@Override
	public String kind()
	{
		return toString();
	}

	public PType getType()
	{
		return type;
	}

	@Override
	public Object clone()
	{
		return new LatticeTopValue(type);
	}

}
