package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public class CmlTransitionFactory {

	protected static SilentTransition instance = null;
	
	/*
	 * prefix event factory methods 
	 */
	
	public static ObservableEvent newSynchronizationEvent(CmlBehaviour eventSource, ChannelNameValue channelName) {
		return new SynchronizationEvent(eventSource,channelName);
	}
		
	/*
	 * communication event factory methods 
	 */
	
	public static ObservableEvent newCmlCommunicationEvent(CmlBehaviour source, ChannelNameValue channelName)
	{
		return new CommunicationEvent(source, channelName);
	}
}
