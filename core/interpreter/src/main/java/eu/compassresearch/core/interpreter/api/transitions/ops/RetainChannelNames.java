package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class RetainChannelNames implements Filter
{
	private final ChannelNameSetValue channelNameSetValue;

	public RetainChannelNames(ChannelNameSetValue channelNameSetValue)
	{
		this.channelNameSetValue = channelNameSetValue;
	}

	public RetainChannelNames(ChannelValue channelNameValue)
	{
		this.channelNameSetValue = new ChannelNameSetValue(channelNameValue);

	}

	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		if (transition instanceof LabelledTransition)
		{
			LabelledTransition obsChannelEvent = (LabelledTransition) transition;

			for (ChannelValue channelNameValue : channelNameSetValue)
			{
				if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
						&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
				{
					return true;
				}
			}

			return false;
		} else
		{
			return false;
		}
	}

}
