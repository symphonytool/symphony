package eu.compassresearch.core.interpreter.values;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.analysis.AnswerCMLAdaptor;
import eu.compassresearch.core.interpreter.CmlRuntime;
import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;


public class AbstractValueInterpreter {

	public static Value meet(Value val1, Value val2)
	{
//		if(value instanceof TupleValue)
//		{
//			for(Value innerValue : ((TupleValue) value).values);
//			
//		}
//		else
//		{
			//The meet operator is relexive so if both are AnyValue we can return either of them
			if(isAnyValue(val1) && isAnyValue(val2))
				return val1;
			else if(!isAnyValue(val1) && !isAnyValue(val2))
			{
				//If both are a value and they differ the meet would be the set of of them, for now we
				//just return the AnyValue
				//TODO we need to be able to represent a AnyValue with a rstriction, maybe an 
				//additional constraint expression would do?
				if(!val1.equals(val2))
				{
					CmlRuntime.logger().warning("A Value just descreased in precision");
					return new AnyValue();
				}
				//any value would do here since they are identical
				else
					return val1;
			}
			else if(isAnyValue(val1))
				return val2;
			else //if(isAnyValue(val2))
				return val1;
//		}
	}
	
	/**
	 * Determines whether the value is as precise as it can be, 
	 * meaning that it only contains exact values.
	 * @param value
	 * @return
	 */
	public static boolean isValueMostPrecise(Value value)
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
	
	/**
	 * Determines whether val1 is more precise than val2. A value is imprecise if it can be
	 * AnyValue.
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static boolean isMorePrecise(Value val1, Value val2)
	{
		//if both are undefined we 
		if(!isValueMostPrecise(val1) && !isValueMostPrecise(val2))
			return false;
		else if(!isValueMostPrecise(val1))
			return false;
		else if(!isValueMostPrecise(val2))
			return true;
		else
			return false;
	}
	
	public static boolean isAnyValue(Value value)
	{
		return value instanceof AnyValue;
	}
}
