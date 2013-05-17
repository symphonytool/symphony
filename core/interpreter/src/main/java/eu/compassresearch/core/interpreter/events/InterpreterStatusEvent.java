package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;

public class InterpreterStatusEvent extends Event<CmlInterpreter> {

	private final CmlInterpreterState status;

	public InterpreterStatusEvent(CmlInterpreter interpreter,CmlInterpreterState status)
	{
		super(interpreter);
		this.status = status;
	}
	
	public CmlInterpreterState getStatus() {
		return status;
	}
}
