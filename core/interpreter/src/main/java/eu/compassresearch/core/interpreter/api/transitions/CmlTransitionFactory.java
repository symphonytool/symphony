package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class CmlTransitionFactory {

	protected static SilentTransition instance = null;
	
			
	/*
	 * communication event factory methods 
	 */
	
	public static ObservableEvent newObservableChannelEvent(CmlBehaviour source, ChannelNameValue channelName)
	{
		return new ObservableChannelEvent(source, channelName);
	}
	
	public static SilentTransition newHiddenChannelEvent(CmlBehaviour source, ChannelEvent channelEvent)
	{
		return new HiddenEvent(source, channelEvent);
	}
}
