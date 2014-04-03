package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public class CmlTransitionFactory
{

	//protected static AbstractSilentTransition instance = null;

	/*
	 * communication event factory methods
	 */

	public static ObservableTransition newLabelledTransition(
			CmlBehaviour source, ChannelValue channelName)
	{
		return new ObservableLabelledTransition(source, channelName);
	}

	// public static SilentTransition newHiddenChannelEvent(CmlBehaviour source, LabelledTransition channelEvent)
	// {
	// return new HiddenEvent(source, channelEvent);
	// }
}
