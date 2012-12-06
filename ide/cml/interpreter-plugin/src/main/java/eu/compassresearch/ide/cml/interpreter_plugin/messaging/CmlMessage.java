package eu.compassresearch.ide.cml.interpreter_plugin.messaging;

public abstract class CmlMessage {

	private String sessionId;
	
	public CmlMessage()
	{
		this.setSessionId("");
	}

	public String getSessionId() {
		return sessionId;
	}

	protected void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public abstract CmlMessageType getType();
	
	public abstract String getKey();
	
	
}
