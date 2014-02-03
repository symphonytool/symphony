package eu.compassresearch.core.interpreter.api.transitions;

import java.util.List;

import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public interface LabelledTransition extends CmlTransition
{

	/**
	 * The channel of this involved in this events
	 * 
	 * @return
	 */
	public ChannelNameValue getChannelName();

	public List<LabelledTransition> expand();
	
	public LabelledTransition rename(ChannelNameValue value);

}
