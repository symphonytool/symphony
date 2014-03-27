package eu.compassresearch.core.interpreter.api.values;

import java.io.Serializable;
import java.util.List;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.types.AChannelType;
import eu.compassresearch.core.interpreter.api.ICmlChannel;
import eu.compassresearch.core.interpreter.api.events.ChannelEventTypeActivity;
import eu.compassresearch.core.interpreter.api.events.ChannelEvent;
import eu.compassresearch.core.interpreter.api.events.ChannelObserver;
import eu.compassresearch.core.interpreter.api.events.EventFireMediator;
import eu.compassresearch.core.interpreter.api.events.EventSource;
import eu.compassresearch.core.interpreter.api.events.EventSourceHandler;

public class CmlChannel extends Value implements ICmlChannel // CmlIOChannel<Value>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6350630462785844551L;
	public final ILexNameToken name;
	private AChannelType channelType;
	private EventSourceHandler<ChannelObserver, ChannelEvent> selectObservers = new EventSourceHandler<ChannelObserver, ChannelEvent>(this, new ChannelEventMediator());
	
	
	private class ChannelEventMediator implements
			EventFireMediator<ChannelObserver, ChannelEvent>, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6748280966083470111L;

		@Override
		public void fireEvent(ChannelObserver observer, Object source,
				ChannelEvent event)
		{
			observer.onChannelEvent(CmlChannel.this, event);
		}
	}

	public CmlChannel(AChannelType channelType, ILexNameToken name)
	{
		this.channelType = channelType;
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name.getName();
	}

	@Override
	public List<PType> getValueTypes()
	{
		return this.channelType.getParameters();
	}

	@Override
	public String toString()
	{
		return kind() + " " + getName() + " : " + getValueTypes();
	}

	@Override
	public boolean equals(Object other)
	{

		CmlChannel otherValue = null;

		if (!(other instanceof CmlChannel))
		{
			return false;
		}

		otherValue = (CmlChannel) other;

		return otherValue.getName().equals(getName())
				&& channelType.equals(otherValue.channelType);
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}

	@Override
	public String kind()
	{
		return "Channel";
	}

	@Override
	public Object clone()
	{

		// return new CMLChannelValue(this);
		return this;
	}

	/**
	 * Helper method to fire away the channel events
	 * 
	 * @param source
	 * @param eventType
	 */
	private void notifyObservers(
			EventSourceHandler<ChannelObserver, ChannelEvent> source,
			ChannelEventTypeActivity eventType)
	{
		source.fireEvent(new ChannelEvent(this, eventType));
	}

	@Override
	public void signalChannelEvent(ChannelEventTypeActivity channelActivity)
	{
		notifyObservers(selectObservers, channelActivity);
	}

	@Override
	public EventSource<ChannelObserver> onChannelEvent()
	{
		return selectObservers;
	}
}
