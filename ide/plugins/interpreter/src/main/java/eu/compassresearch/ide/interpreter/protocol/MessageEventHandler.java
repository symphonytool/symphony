package eu.compassresearch.ide.interpreter.protocol;

import eu.compassresearch.core.interpreter.debug.messaging.Message;

public interface MessageEventHandler<T extends Message>
{
	public boolean handleMessage(T message);
}