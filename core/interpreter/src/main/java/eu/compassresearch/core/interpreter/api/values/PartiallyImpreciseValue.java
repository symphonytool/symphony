//package eu.compassresearch.core.interpreter.api.values;
//
//import org.overture.interpreter.values.Value;
//
//public class PartiallyImpreciseValue extends Value implements LooseValue
//{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private Value complexValue;
//
//	public PartiallyImpreciseValue(Value complexValue)
//	{
//		this.complexValue = complexValue;
//	}
//
//	@Override
//	public boolean isResolvable(Value val)
//	{
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public String toString()
//	{
//		return complexValue.toString();
//	}
//
//	@Override
//	public boolean equals(Object other)
//	{
//		return this==other;
//	}
//
//	@Override
//	public int hashCode()
//	{
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public String kind()
//	{
//		return PartiallyImpreciseValue.class.getSimpleName();
//	}
//
//	@Override
//	public Object clone()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
