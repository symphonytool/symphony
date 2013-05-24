package eu.compassresearch.core.interpreter.utility.messaging;

import com.google.gson.Gson;

public class MessageContainer {

	private MessageType type;
	private String version = "0.0.1";
	private String message;
	
	public MessageContainer()
	{
		this.setType(null);
		message = null;
	}
	
	public MessageContainer(Message message)
	{
		this.setType(type);
		this.setMessage(message);
	}
	
	public MessageType getType() {
		return type;
	}

	protected void setType(MessageType type) {
		this.type = type;
	}

//	public CmlMessage getMessage() {
//		return message;
//	}
	
	public <T> T getMessage(Class<T> classType)
	{
		Gson gson = new Gson();
		return gson.fromJson(message,classType);
	}

	protected void setMessage(Message message) {
		setType(message.getType());
		Gson gson = new Gson();
		this.message =  gson.toJson(message);
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
