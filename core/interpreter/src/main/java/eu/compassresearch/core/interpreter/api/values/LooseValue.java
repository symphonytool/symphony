package eu.compassresearch.core.interpreter.api.values;

import org.overture.interpreter.values.Value;

public interface LooseValue
{
	boolean isResolvable(Value val);
}
