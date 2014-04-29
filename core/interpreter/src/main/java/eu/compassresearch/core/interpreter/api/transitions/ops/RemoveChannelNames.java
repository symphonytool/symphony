package eu.compassresearch.core.interpreter.api.transitions.ops;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelNameSetValue;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class RemoveChannelNames implements Filter
{

	private final ChannelNameSetValue channelNameSetValue;

	public RemoveChannelNames(ChannelNameSetValue channelNameSetValue)
	{
		this.channelNameSetValue = channelNameSetValue;
	}

	public RemoveChannelNames(ChannelValue channelNameValue)
	{
		this.channelNameSetValue = new ChannelNameSetValue(channelNameValue);
	}

	private boolean isTransitionCompatible(LabelledTransition transition,
			ChannelValue channelNameValue)
	{
		return transition.getChannelName().isComparable(channelNameValue)
				&& channelNameValue.isGTEQPrecise(transition.getChannelName());
	}

	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		if (transition instanceof LabelledTransition)
		{
			LabelledTransition lt = (LabelledTransition) transition;
			boolean retaintIt = true;

			for (ChannelValue channelNameValue : channelNameSetValue)
			{
				if (isTransitionCompatible(lt, channelNameValue))
				{
					retaintIt = false;
					break;
				}
			}

			return retaintIt;
		} else
		{
			return true;
		}
	}

}
