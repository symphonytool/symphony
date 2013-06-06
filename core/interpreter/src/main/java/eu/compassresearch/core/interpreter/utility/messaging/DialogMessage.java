package eu.compassresearch.core.interpreter.utility.messaging;

import java.util.UUID;

public abstract class DialogMessage extends Message{

	private UUID requestId;
	private CmlRequest request;

	protected DialogMessage()
	{
	}
	
	public DialogMessage(CmlRequest request, Object value)
	{
		requestId = UUID.randomUUID();
		this.request = request;
		setContent(value);
	}
	
	public DialogMessage(UUID requestId, CmlRequest request, Object value)
	{
		this.requestId = requestId;
		this.request = request;
		setContent(value);
	}
	
	public UUID getRequestId() {
		return requestId;
	}

	public CmlRequest getRequest()
	{
		return request;
	}
		
	@Override
	public abstract MessageType getType();
	
	@Override
	public String getKey()
	{
		return getRequest().toString();
	}
	
}
