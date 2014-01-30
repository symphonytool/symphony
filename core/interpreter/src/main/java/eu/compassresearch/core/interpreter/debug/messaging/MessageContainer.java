package eu.compassresearch.core.interpreter.debug.messaging;

public class MessageContainer
{

	private String version = "0.0.1";
	private AbstractMessage message;

	protected MessageContainer()
	{
		message = null;
	}

	public MessageContainer(AbstractMessage message)
	{
		this.setMessage(message);
	}

	public MessageType getType()
	{

		return message.getType();
	}

	public JsonMessage getMessage()
	{
		return message;
	}

	protected void setMessage(AbstractMessage message)
	{

		this.message = message;
	}

	public String getVersion()
	{
		return version;
	}

	@Override
	public String toString()
	{
		String newLine = System.getProperty("line.separator");
		return "Type: " + getType() + newLine + "message: " + message;
	}
}