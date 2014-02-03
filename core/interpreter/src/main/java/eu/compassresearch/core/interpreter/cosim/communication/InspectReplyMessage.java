package eu.compassresearch.core.interpreter.cosim.communication;

import eu.compassresearch.core.interpreter.api.transitions.CmlTransitionSet;
import eu.compassresearch.core.interpreter.debug.messaging.JsonMessage;

/**
 * Message used by a client to reply an inspection with a {@link CmlTransitionSet}
 * 
 * @author kel
 */
public class InspectReplyMessage implements JsonMessage
{

	private String process;
	private CmlTransitionSet transitions;

	/**
	 * default for message parsing
	 */
	public InspectReplyMessage()
	{
	}

	public InspectReplyMessage(String process, CmlTransitionSet transitions)
	{
		this.process = process;
		this.transitions = transitions;
	}

	@Override
	public String toString()
	{
		return "Inspect reply: " + process + " transitions: " + transitions;
	}

	public String getProcess()
	{
		return this.process;
	}

	public CmlTransitionSet getTransitions()
	{
		return this.transitions;
	}

}
