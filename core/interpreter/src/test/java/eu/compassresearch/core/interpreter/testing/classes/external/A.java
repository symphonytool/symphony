package eu.compassresearch.core.interpreter.testing.classes.external;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;

public class A
{
	public static Value getHello()
	{
		System.out.println("Yep I got in here");
		return new IntegerValue(2);
	}

	public static Value add(Value a, Value b) throws ValueException
	{
		return new IntegerValue(a.intValue(null) + b.intValue(null));
	}
}
