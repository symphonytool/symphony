package eu.compassresearch.core.interpreter.api.transitions.ops;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class RetainChannelNames implements Filter
{
	private final SetValue channelNameSetValue;

	public RetainChannelNames(SetValue channelNameSetValue)
	{
		this.channelNameSetValue = channelNameSetValue;
	}

	public RetainChannelNames(ChannelValue channelNameValue)
	{
		this.channelNameSetValue = new SetValue();
		this.channelNameSetValue.values.add(channelNameValue);

	}

	@Override
	public boolean isAccepted(CmlTransition transition)
	{
		if (transition instanceof LabelledTransition)
		{
			LabelledTransition obsChannelEvent = (LabelledTransition) transition;

			for (Value val : channelNameSetValue.values)
			{
				if (val instanceof ChannelValue)
				{
					ChannelValue channelNameValue = (ChannelValue) val;
					if (obsChannelEvent.getChannelName().isComparable(channelNameValue)
							&& channelNameValue.isGTEQPrecise(obsChannelEvent.getChannelName()))
					{
						return true;
					}
				} else
				{
					throw new InterpreterRuntimeException("Only "
							+ ChannelValue.class.getSimpleName()
							+ " must be present in a channel value set. Actual: "
							+ val);
				}
			}

			return false;
		} else
		{
			return false;
		}
	}

}
