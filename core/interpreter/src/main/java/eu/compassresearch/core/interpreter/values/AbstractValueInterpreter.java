package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;


public class AbstractValueInterpreter {

	public static Value meet(Value val1, Value val2)
	{
		//if both are undefined we cannot come any close
		if(isAnyValue(val1) && isAnyValue(val2))
			return val1;
		else if(isAnyValue(val1))
			return val2;
		else if(isAnyValue(val2))
			return val1;
		else
			return null;
	}
	
	public static boolean isValuePrecise(Value value)
	{
		//TODO there should be a range/interval value
		boolean ret = true;
		if(value != null )
		{
			if(value instanceof TupleValue)
				for(Value innerValue : ((TupleValue) value).values)
					ret &= !isAnyValue(innerValue);
			else
				ret = !isAnyValue(value);
				
		}	
		
		return ret;
	}
	
	public static boolean isMorePrecise(Value val1, Value val2)
	{
		//if both are undefined we 
		if(isAnyValue(val1) && isAnyValue(val2))
			return false;
		else if(isAnyValue(val1))
			return false;
		else if(isAnyValue(val2))
			return true;
		else
			return false;
	}
	
	public static boolean isAnyValue(Value value)
	{
		return value instanceof AnyValue;
	}
}
