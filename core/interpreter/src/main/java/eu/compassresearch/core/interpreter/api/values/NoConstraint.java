package eu.compassresearch.core.interpreter.api.values;

import org.overture.interpreter.values.Value;

public class NoConstraint implements ValueConstraint {

	@Override
	public boolean isValid(Value val) {
		return true;
	}

}
