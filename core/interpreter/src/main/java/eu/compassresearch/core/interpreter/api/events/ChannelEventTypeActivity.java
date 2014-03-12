package eu.compassresearch.core.interpreter.api.events;

/**
 * This represents the types of events that a CmlChannel can participate in
 * 
 * @author akm
 */
public enum ChannelEventTypeActivity
{
	/**
	 * An input communication is occurring.
	 */
	READ,
	/**
	 * An output communication is occurring.
	 */
	WRITE,
	/**
	 * A synchronization is occurring.
	 */
	SYNC,
}
