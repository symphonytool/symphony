package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;

public class RetainChannelNamesAndTime extends RetainChannelNames
{

	public RetainChannelNamesAndTime(ChannelNameSetValue channelNameSetValue)
	{
		super(channelNameSetValue);
	}
	
	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		return super.isAccepted(transition) || (transition instanceof TimedTransition);
	}

}
