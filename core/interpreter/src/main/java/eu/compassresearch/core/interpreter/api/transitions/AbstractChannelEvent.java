package eu.compassresearch.core.interpreter.api.transitions;

import java.util.List;
import java.util.Set;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.ChannelNameValue;

/**
 * This represents an observable channel event from a set of CmlBehaviourThread objects
 * @author akm
 *
 */
abstract class AbstractChannelEvent extends AbstractCmlTransition implements LabelledTransition{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1807730095394673285L;
	final protected ChannelNameValue channelName;
	
	
	public AbstractChannelEvent(CmlBehaviour eventSource, ChannelNameValue channelName)
	{
		super(eventSource);
		this.channelName = channelName;
	}
	
	protected AbstractChannelEvent(Set<CmlBehaviour> sources, ChannelNameValue channelName)
	{
		super(sources);
		this.channelName = channelName;
	}
	
	/**
	 * The channel of this involved in this events
	 * @return
	 */
	@Override
	public ChannelNameValue getChannelName()
	{
		return channelName;
	}

	@Override
	public abstract List<LabelledTransition> expand();
	
//	/**
//	 * return the most precise of this and other
//	 * @param other
//	 * @return
//	 */
//	@Override
//	public abstract ObservableEvent meet(ObservableEvent other); 
	
	@Override
	public boolean equals(Object obj) {

		LabelledTransition other = null;
		
		if(!(obj instanceof LabelledTransition))
			return false;
		
		other = (LabelledTransition)obj;
		
		return other.getChannelName().equals(getChannelName()) && 
				super.equals(obj);
	}
	
}
