package eu.compassresearch.core.interpreter.api;

public enum TransitionEvent
{
	SKIP,
	WAIT_EVENT,
	WAIT_CHILD,
	CHILD_EVENT,
	CHANNEL_EVENT,
	STOP,
	DIV,
}
