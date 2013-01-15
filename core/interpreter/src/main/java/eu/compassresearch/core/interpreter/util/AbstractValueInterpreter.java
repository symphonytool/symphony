package eu.compassresearch.core.interpreter.util;

import org.overture.interpreter.values.Value;

public class AbstractValueInterpreter {

	public static Value meet(Value val1, Value val2)
	{
		//if both are undefined we cannot come any close
		if(val1.isUndefined() && val2.isUndefined())
			return val1;
		else if(val1.isUndefined())
			return val2;
		else if(val2.isUndefined())
			return val1;
		else
			return null;
	}
	
	public static boolean isMorePrecise(Value val1, Value val2)
	{
		//if both are undefined we 
		if(val1.isUndefined() && val2.isUndefined())
			return false;
		else if(val1.isUndefined())
			return false;
		else if(val2.isUndefined())
			return true;
		else
			return false;
	}
}
