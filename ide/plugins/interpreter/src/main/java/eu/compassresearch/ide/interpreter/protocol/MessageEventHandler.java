package eu.compassresearch.ide.interpreter.protocol;

import eu.compassresearch.core.interpreter.debug.messaging.AbstractMessage;

public interface MessageEventHandler<T extends AbstractMessage>
{
	public boolean handleMessage(T message);
}