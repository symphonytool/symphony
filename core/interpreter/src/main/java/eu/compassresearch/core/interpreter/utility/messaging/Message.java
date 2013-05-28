package eu.compassresearch.core.interpreter.utility.messaging;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public abstract class Message {

	//sessions id current debugging session
	//TODO: this is not currently used
	protected String sessionId;
	//message content
	protected String content;
	
	
	public Message()
	{
		this.setSessionId("");
	}

	public String getSessionId() {
		return sessionId;
	}

	protected void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	protected void setContent(Object value)
	{
		Gson gson = new Gson();
		this.content = gson.toJson(value);
	}

	public <T> T getContent(Class<T> classType)
	{
		Gson gson = new Gson();
		return gson.fromJson(content,classType);
	}
	
	public <T> T getContent(Type classType)
	{
		Gson gson = new Gson();
		return gson.fromJson(content,classType);
	}
	
	public abstract MessageType getType();
	
	public abstract String getKey();
	
	
}
