package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterStatus;

public class InterpreterStatusEvent extends Event<CmlInterpreter> {

	private final CmlInterpreterStatus status;

	public InterpreterStatusEvent(CmlInterpreter interpreter,CmlInterpreterStatus status)
	{
		super(interpreter);
		this.status = status;
	}
	
	public CmlInterpreterStatus getStatus() {
		return status;
	}
}
