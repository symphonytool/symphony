package eu.compassresearch.core.interpreter.cml.events;

import java.util.List;

import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CmlChannel;

public interface ChannelEvent extends CmlTransition{

	/**
	 * The channel of this involved in this events
	 * @return
	 */
	public CmlChannel getChannel();
	
	public Value getValue();
	
	public void setValue(Value value);
	
	public List<ChannelEvent> expand();
	
	public boolean isPrecise();
	
	/**
	 * return the most precise of this and other
	 * @param other
	 * @return
	 */
	public ObservableEvent meet(ObservableEvent other); 
}
