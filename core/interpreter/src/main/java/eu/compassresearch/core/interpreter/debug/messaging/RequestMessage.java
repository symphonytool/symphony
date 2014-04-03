package eu.compassresearch.core.interpreter.debug.messaging;

public class RequestMessage extends DialogMessage
{

	public RequestMessage()
	{
		super();
	}

	public RequestMessage(CmlRequest request, Object value)
	{
		super(request, value);
	}

	public RequestMessage(CmlRequest request)
	{
		super(request, "");
	}

	@Override
	public MessageType getType()
	{
		return MessageType.REQUEST;
	}

	@Override
	public String toString()
	{
		return this.content.toString();
	}
}
