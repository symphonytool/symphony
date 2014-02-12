package eu.compassresearch.core.interpreter.api.values;

import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractValueInterpreter
{
	final static Logger logger = LoggerFactory.getLogger("cml-interpreter");

	public static Value meet(Value val1, Value val2)
	{
		// The meet operator is reflexive so if both are AnyValue we can return either of them
		// and since they only are comparable if
		if (isValueMostPrecise(val1) && isValueMostPrecise(val2))
		{
			return val1;
		} else if (!isValueMostPrecise(val1) && !isValueMostPrecise(val2))
		{
			// If both are a nonprecise value and they differ the meet would be the set of of them, for now we
			// just return the AnyValue
			// TODO we need to be able to represent a AnyValue with a restriction, maybe an
			// additional constraint expression would do?
			if (!val1.equals(val2))
			{
				logger.warn("A Value just descreased in precision");
				// return new AnyValue();
				return new UndefinedValue();
			}
			// any value would do here since they are identical
			else
			{
				return val1;
			}
		} else if (isValueMostPrecise(val1))
		{
			return val1;
		} else
		{
			// if(isAnyValue(val2))
			return val2;
		}
	}

	/**
	 * Determines whether the value is as precise as it can be, meaning that it only contains exact values.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isValueMostPrecise(Value value)
	{
		// TODO there should be a range/interval value
		boolean ret = true;
		if (value != null)
		{
			if (value instanceof ChannelNameValue)
			{
				for (Value innerValue : ((ChannelNameValue) value).getValues())
				{
					ret &= isValueMostPrecise(innerValue);
				}
			} else if (value instanceof TupleValue)
			{
				for (Value innerValue : ((TupleValue) value).values)
				{
					ret &= isValueMostPrecise(innerValue);
				}
			} else
			{
				ret = !isAnyValue(value);
			}

		}

		return ret;
	}

	/**
	 * Determines whether val1 is equally or more precise than val2. A value is imprecise if it is AnyValue.
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static boolean isEquallyOrMorePrecise(Value val1, Value val2)
	{
		// if both are undefined we
		if (isValueMostPrecise(val1) && isValueMostPrecise(val2)
				|| !isValueMostPrecise(val1) && !isValueMostPrecise(val2))
		{
			return true;
		} else if (isValueMostPrecise(val1))
		{
			return true;
		} else
		{
			// if(!isValueMostPrecise(val2))
			return false;
		}
	}

	public static boolean isAnyValue(Value value)
	{
		return value instanceof LooseValue;
	}
}
