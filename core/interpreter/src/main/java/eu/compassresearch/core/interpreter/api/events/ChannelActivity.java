package eu.compassresearch.core.interpreter.api.events;

/**
 * This represents the activites that a CmlChannel can participate in
 * 
 * @author akm
 */
public enum ChannelActivity
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
	SIGNAL,
	/**
	 * The channel has been selected by the supervisor environment
	 */
	SELECT
}
