package eu.compassresearch.core.interpreter.api.transitions;

import java.util.List;

import eu.compassresearch.core.interpreter.api.values.ChannelValue;

public interface LabelledTransition extends ObservableTransition
{

	/**
	 * The channel of this involved in this events
	 * 
	 * @return
	 */
	public ChannelValue getChannelName();

	public List<LabelledTransition> expand();
	
	public LabelledTransition rename(ChannelValue value);

}
