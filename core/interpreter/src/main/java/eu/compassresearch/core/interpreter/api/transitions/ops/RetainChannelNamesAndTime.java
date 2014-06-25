package eu.compassresearch.core.interpreter.api.transitions.ops;

import org.overture.interpreter.values.SetValue;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.TimedTransition;

public class RetainChannelNamesAndTime extends RetainChannelNames
{

	public RetainChannelNamesAndTime(SetValue channelNameSetValue)
	{
		super(channelNameSetValue);
	}

	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		return super.isAccepted(transition)
				|| transition instanceof TimedTransition;
	}

}
