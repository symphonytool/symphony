package eu.compassresearch.core.interpreter.debug.messaging;


public class MessageContainer {

	private String version = "0.0.1";
	private Message message;
	
	protected MessageContainer()
	{
		message = null;
	}
	
	public MessageContainer(Message message)
	{
		this.setMessage(message);
	}
	
	public MessageType getType() {
		
		return message.getType();
	}

	public Message getMessage()
	{
		return message;
	}

	protected void setMessage(Message message) {

		this.message = message;
	}
	
	public String getVersion() {
		return version;
	}
	
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		return "Type: " + getType() + newLine +
				"message: " + message;
	}
}