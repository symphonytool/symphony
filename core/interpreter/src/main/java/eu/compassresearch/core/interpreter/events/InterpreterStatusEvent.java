package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;

public class InterpreterStatusEvent extends Event<CmlInterpreter> {

	public InterpreterStatusEvent(CmlInterpreter interpreter)
	{
		super(interpreter);
	}
}
