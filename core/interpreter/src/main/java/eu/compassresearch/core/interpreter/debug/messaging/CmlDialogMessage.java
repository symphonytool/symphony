package eu.compassresearch.core.interpreter.debug.messaging;

import java.util.UUID;

public abstract class CmlDialogMessage extends CmlMessage{

	private UUID requestId;
	private CmlRequest request;

	public CmlDialogMessage(CmlRequest request, Object value)
	{
		requestId = UUID.randomUUID();
		this.request = request;
		setContent(value);
	}
	
	public CmlDialogMessage(UUID requestId, CmlRequest request, Object value)
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
	public abstract CmlMessageType getType();
	
	@Override
	public String getKey()
	{
		return getRequest().toString();
	}
	
}
