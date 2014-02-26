package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class RetainByChannelNameSetFilter implements Filter<CmlTransition>
{
	private final ChannelNameSetValue channelNameSetValue;
	
	public RetainByChannelNameSetFilter(ChannelNameSetValue channelNameSetValue)
	{
		this.channelNameSetValue = channelNameSetValue;
	}

	@Override
	public boolean apply(CmlTransition transition)
	{
		if(transition instanceof LabelledTransition)
		{
			LabelledTransition obsChannelEvent = (LabelledTransition) transition;

			for (ChannelNameValue channelNameValue : channelNameSetValue)
			{
				if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
						&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
					return true;
			}
			
			return false;
		}
		else
			return false;
	}

}
