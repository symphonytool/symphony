package eu.compassresearch.core.interpreter.cml;

public class CMLChannelEvent {
	
	String channelName;
	
	public CMLChannelEvent(String channelName)
	{
		this.channelName = channelName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
