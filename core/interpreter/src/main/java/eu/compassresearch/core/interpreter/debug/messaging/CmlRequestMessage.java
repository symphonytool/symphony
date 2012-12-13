package eu.compassresearch.core.interpreter.debug.messaging;

public class CmlRequestMessage extends CmlDialogMessage {

	public CmlRequestMessage(CmlRequest request, Object value)
	{
		super(request,value);
	}
	
	@Override
	public CmlMessageType getType() {
		return CmlMessageType.REQUEST;
	}

}
