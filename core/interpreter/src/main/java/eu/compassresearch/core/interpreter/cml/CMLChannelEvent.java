package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.lex.LexNameToken;

public class CMLChannelEvent {
		
	final private LexNameToken channelName;
	final private CMLCommunicationType eventType;
	
	public CMLChannelEvent(LexNameToken channelName,CMLCommunicationType eventType)
	{
		this.channelName = channelName;
		this.eventType = eventType;
	}

	public LexNameToken getChannelName() {
		return channelName;
	}
	
	public CMLCommunicationType getEventType()
	{
		return eventType;
	}
}
