package eu.compassresearch.core.interpreter.debug.messaging;

public abstract class AbstractMessage implements JsonMessage
{

	/**
	 * sessions id current debugging session
	 */
	// TODO: this is not currently used
	protected String sessionId;
	/**
	 * message content
	 */
	protected Object content;

	public AbstractMessage()
	{
		this.setSessionId("");
	}

	public String getSessionId()
	{
		return sessionId;
	}

	protected void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	protected void setContent(Object value)
	{
		this.content = value;
	}

	@SuppressWarnings("unchecked")
	public <T> T getContent()
	{
		// Gson gson = new Gson();
		// return gson.fromJson(content,classType);
		return (T) this.content;
	}

	// public <T> T getContent(Type classType)
	// {
	// // Gson gson = new Gson();
	// // return gson.fromJson(content,classType);
	// return classType.cast(this.content);
	// }

	public abstract MessageType getType();

	public abstract String getKey();
	
	@Override
	public String toString()
	{
		return getType()+" "+ getContent();
	}

}
