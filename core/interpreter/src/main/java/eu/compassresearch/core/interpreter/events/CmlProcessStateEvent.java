package eu.compassresearch.core.interpreter.events;

import eu.compassresearch.core.interpreter.cml.CmlProcess;
import eu.compassresearch.core.interpreter.cml.CmlProcessState;

public class CmlProcessStateEvent extends Event<CmlProcess> {

	private final CmlProcessState from;
	private final CmlProcessState to;
	
	public CmlProcessStateEvent(CmlProcess source,CmlProcessState from, CmlProcessState to)
	{
		super(source);
		this.from = from;
		this.to = to;
	}

	public CmlProcessState getTo() {
		return to;
	}

	public CmlProcessState getFrom() {
		return from;
	}
}
