package eu.compassresearch.core.interpreter.testing.classes.external;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.Value;


public class Test
{
	public static Value sub(Value a, Value b) throws ValueException
	{
		return new IntegerValue(a.intValue(null) - b.intValue(null));
	}
}
