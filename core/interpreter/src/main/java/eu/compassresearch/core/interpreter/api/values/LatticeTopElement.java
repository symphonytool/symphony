package eu.compassresearch.core.interpreter.api.values;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

public class LatticeTopElement extends Value implements LatticeElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438757665705569283L;
	private PType type;
	private static final String topString = "?"; 

	public LatticeTopElement(PType type)
	{
		this.type = type;
	}
	
	@Override
	public LatticeElement meet(LatticeElement other)
	{
		//this should always be the other element since
		return other;
	}

//	@Override
//	public Value meet(Value other)
//	{
//		//this should always be the other element since
//		return other;
//	}
	
	@Override
	public boolean isComparable(LatticeElement other)
	{
		//the top element is comparable with all the elements of the same type
		return this.type.equals(other.getType());
	}

//	@Override
//	public boolean isComparable(Value other)
//	{
//		return true;
//	}

	@Override
	public PType getType()
	{
		return type;
	}

	@Override
	public boolean isTopElement()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return topString;
	}

	@Override
	public boolean equals(Object other)
	{
		if(other instanceof LatticeElement)
			return ((LatticeTopElement) other).getType().equals(type) &&
					((LatticeTopElement) other).isTopElement();
					
		else
			return false;
	}

	@Override
	public int hashCode()
	{
		return topString.hashCode() + type.hashCode();
	}

	@Override
	public String kind()
	{
		return LatticeTopElement.class.getSimpleName();
	}

	@Override
	public Object clone()
	{
		return new LatticeTopElement(type);
	}

	@Override
	public Value getValue()
	{
		return null;
	}

	@Override
	public boolean isMostPrecise()
	{
		return false;
	}

}
