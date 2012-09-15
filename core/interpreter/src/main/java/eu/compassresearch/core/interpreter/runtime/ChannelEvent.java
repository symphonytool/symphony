package eu.compassresearch.core.interpreter.runtime;

public class ChannelEvent {
	
	String channelName;
	
	public ChannelEvent(String channelName)
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
