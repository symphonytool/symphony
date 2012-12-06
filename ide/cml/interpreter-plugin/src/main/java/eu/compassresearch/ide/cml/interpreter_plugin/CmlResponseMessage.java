package eu.compassresearch.ide.cml.interpreter_plugin;

import java.util.UUID;

public class CmlResponseMessage extends CmlDialogMessage {

	public CmlResponseMessage(UUID requestId, CmlRequest request, Object value)
	{
		super(requestId,request,value);
	}
	
	@Override
	public CmlMessageType getType() {
		return CmlMessageType.RESPONSE;
	}

}
