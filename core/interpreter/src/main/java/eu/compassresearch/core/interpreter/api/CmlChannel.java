package eu.compassresearch.core.interpreter.api;

import java.util.List;

import org.overture.ast.types.PType;

import eu.compassresearch.core.interpreter.api.events.ChannelObserver;
import eu.compassresearch.core.interpreter.api.events.EventSource;

public interface CmlChannel {
	
	public String getName();
	
	public PType getType();
	
	/**
	 * Returns the types of the possible values that can be sent on this channel
	 * @return
	 */
	public List<PType> getValueTypes();
	
	/**
	 * Tells whether this is a communication channel meaning that values can be sent 
	 * or a sync channel without any values
	 * @return true if the getValueType() method return non null else false
	 */
	public boolean isCommunicationChannel();
	
	/**
	 * Signals that this channel is selected by the environment to all the channel listeners
	 */
	public void select();
	
	/**
	 * Register or unregister for the onChannelSignal event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<ChannelObserver> onSelect();
	
}
