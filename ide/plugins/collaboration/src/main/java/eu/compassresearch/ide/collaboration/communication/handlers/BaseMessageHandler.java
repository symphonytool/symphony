package eu.compassresearch.ide.collaboration.communication.handlers;

import java.io.InvalidObjectException;

import eu.compassresearch.ide.collaboration.communication.IMessageHandler;
import eu.compassresearch.ide.collaboration.communication.messages.BaseMessage;
import eu.compassresearch.ide.collaboration.notifications.Notification;

public abstract class BaseMessageHandler<T extends BaseMessage> implements IMessageHandler
{
	final Class<T> msgType;
	
	public BaseMessageHandler(Class<T> messageType)
	{
		msgType = messageType;
	}

	public Boolean canHandleMessage(BaseMessage msg)
	{
		return msg.getClass() == msgType;
	}

	public void handleMessage(BaseMessage msg) throws InvalidObjectException
	{
		if(!canHandleMessage(msg)) {
			throw new InvalidObjectException(Notification.Collaboration_ERROR_UNKNOWN_MESSAGE_TYPE
				+ msg.getClass().getName());
		}
		
		process(msgType.cast(msg));
	}
	
	public abstract void process(T msg);
}