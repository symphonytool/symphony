package eu.compassresearch.core.interpreter.api.transitions.ops;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
import eu.compassresearch.core.interpreter.api.transitions.LabelledTransition;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class RemoveChannelNames implements Filter
{

	private final SetValue channelNameSetValue;

	public RemoveChannelNames(SetValue channelNameSetValue)
	{
		this.channelNameSetValue = channelNameSetValue;
	}

	public RemoveChannelNames(ChannelValue channelNameValue)
	{
		this.channelNameSetValue = new SetValue();
		this.channelNameSetValue.values.add(channelNameValue);
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

			for (Value val : channelNameSetValue.values)
			{
				if (val instanceof ChannelValue)
				{
					ChannelValue channelNameValue = (ChannelValue) val;
					if (isTransitionCompatible(lt, channelNameValue))
					{
						retaintIt = false;
						break;
					}
				} else
				{
					throw new InterpreterRuntimeException("Only "
							+ ChannelValue.class.getSimpleName()
							+ " must be present in a channel value set. Actual: "
							+ val);
				}
			}

			return retaintIt;
		} else
		{
			return true;
		}
	}

}
