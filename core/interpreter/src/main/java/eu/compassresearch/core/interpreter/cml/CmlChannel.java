package eu.compassresearch.core.interpreter.cml;

import org.overture.ast.types.PType;

import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.events.EventSource;

public interface CmlChannel {
	
	public String getName();
	
	public PType getType();
	
	/**
	 * Signals that this channel is selected the channel listeners that thi
	 */
	public void select();
	
	/**
	 * Register or unregister for the onChannelSignal event
	 * @return The appropriate EventSource for event registration
	 */
	public EventSource<ChannelObserver> onSelect();
	
}
