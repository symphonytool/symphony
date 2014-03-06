package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class CmlTransitionFactory
{

	//protected static AbstractSilentTransition instance = null;

	/*
	 * communication event factory methods
	 */

	public static ObservableTransition newLabelledTransition(
			CmlBehaviour source, ChannelNameValue channelName)
	{
		return new ObservableLabelledTransition(source, channelName);
	}

	// public static SilentTransition newHiddenChannelEvent(CmlBehaviour source, LabelledTransition channelEvent)
	// {
	// return new HiddenEvent(source, channelEvent);
	// }
}
