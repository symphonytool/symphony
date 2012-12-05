package eu.compassresearch.ide.cml.interpreter_plugin;

import java.util.UUID;

public class CmlResponseMessage extends CmlDialogMessage {

	protected boolean isInterrupted = false;
	
	public CmlResponseMessage()
	{
		super(null,null,null);
		isInterrupted = true;
	}
	
	public CmlResponseMessage(UUID requestId, CmlRequest request, Object value)
	{
		super(requestId,request,value);
		isInterrupted = false;
	}
	
	@Override
	public CmlMessageType getType() {
		return CmlMessageType.RESPONSE;
	}
	
	public boolean isRequestInterrupted()
	{
		return isInterrupted;
	}

}
