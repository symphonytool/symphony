package eu.compassresearch.core.interpreter.debug.messaging;

import com.google.gson.Gson;

public class CmlMessageContainer {

	private CmlMessageType type;
	private String version = "0.0.1";
	private String message;
	
	public CmlMessageContainer()
	{
		this.setType(null);
		message = null;
	}
	
	public CmlMessageContainer(CmlMessage message)
	{
		this.setType(type);
		this.setMessage(message);
	}
	
	public CmlMessageType getType() {
		return type;
	}

	protected void setType(CmlMessageType type) {
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

	protected void setMessage(CmlMessage message) {
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
