package eu.compassresearch.core.interpreter.debug.messaging;

import java.util.UUID;

public class ResponseMessage extends DialogMessage {

	protected boolean isInterrupted = false;
	
	public ResponseMessage()
	{
		super(null,null,null);
		isInterrupted = true;
	}
	
	public ResponseMessage(UUID requestId, CmlRequest request, Object value)
	{
		super(requestId,request,value);
		isInterrupted = false;
	}
	
	@Override
	public MessageType getType() {
		return MessageType.RESPONSE;
	}
	
	public boolean isRequestInterrupted()
	{
		return isInterrupted;
	}

}
