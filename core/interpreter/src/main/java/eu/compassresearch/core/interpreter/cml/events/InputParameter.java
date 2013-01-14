package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.UndefinedValue;
import org.overture.interpreter.values.Value;

public class InputParameter extends CommunicationParameter {

	public InputParameter() {
		super(new UndefinedValue());
	}

}
