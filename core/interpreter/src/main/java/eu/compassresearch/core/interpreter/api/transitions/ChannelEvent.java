package eu.compassresearch.core.interpreter.api.transitions;

import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

public interface ChannelEvent extends CmlTransition{

	/**
	 * The channel of this involved in this events
	 * @return
	 */
	public ChannelNameValue getChannelName();
	
//	public Value getValue();
//	
//	public void setValue(Value value);
	
	public List<ChannelEvent> expand();
	
	public boolean isPrecise();
	
	/**
	 * return the most precise of this and other
	 * @param other
	 * @return
	 */
	public ObservableEvent meet(ObservableEvent other); 
}
