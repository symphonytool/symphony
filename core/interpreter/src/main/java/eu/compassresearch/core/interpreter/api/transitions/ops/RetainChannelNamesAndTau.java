package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.TauTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;

public class RetainChannelNamesAndTau extends RetainChannelNames
{

	public RetainChannelNamesAndTau(ChannelNameSetValue channelNameSetValue)
	{
		super(channelNameSetValue);
	}
	
	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		return super.isAccepted(transition) || (transition instanceof TauTransition);
	}

}
