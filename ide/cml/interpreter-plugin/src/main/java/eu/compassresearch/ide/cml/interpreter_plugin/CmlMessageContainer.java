package eu.compassresearch.ide.cml.interpreter_plugin;

import com.google.gson.Gson;

public class CmlMessageContainer {

	private CmlMessageType type;
	private String version = "0.0.1";
	private String message;
	private transient Gson gson;
	
	public CmlMessageContainer()
	{
		gson = new Gson();
		this.setType(null);
		message = null;
	}
	
	public CmlMessageContainer(CmlMessage message)
	{
		gson = new Gson();
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
		return gson.fromJson(message,classType);
	}

	protected void setMessage(CmlMessage message) {
		setType(message.getType());
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
