package eu.compassresearch.ide.collaboration.communication;

import java.io.InvalidObjectException;

import eu.compassresearch.ide.collaboration.communication.messages.BaseMessage;

public interface IMessageHandler
{
	void handleMessage(BaseMessage msg) throws InvalidObjectException;
	
	Boolean canHandleMessage(BaseMessage msg);
}