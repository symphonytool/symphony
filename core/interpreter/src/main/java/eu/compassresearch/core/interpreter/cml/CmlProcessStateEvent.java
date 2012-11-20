package eu.compassresearch.core.interpreter.cml;

public class CmlProcessStateEvent {

	private final CmlProcessState from;
	private final CmlProcessState to;
	private final CmlProcess source;
	
	public CmlProcessStateEvent(CmlProcess source,CmlProcessState from, CmlProcessState to)
	{
		this.source = source;
		this.from = from;
		this.to = to;
	}

	public CmlProcess getSource() {
		return source;
	}

	public CmlProcessState getTo() {
		return to;
	}

	public CmlProcessState getFrom() {
		return from;
	}
}
