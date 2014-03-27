package eu.compassresearch.core.interpreter.api.values;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.overture.interpreter.values.Value;

public class ChannelNameSetValue extends Value implements Set<ChannelValue>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Set<ChannelValue> channelNames;

	public ChannelNameSetValue()
	{
		this.channelNames = new HashSet<ChannelValue>();
	}
	
	public ChannelNameSetValue(Set<ChannelValue> channelNames)
	{
		this.channelNames = new HashSet<ChannelValue>(channelNames);
	}
	
	public ChannelNameSetValue(ChannelValue channelName)
	{
		this.channelNames = new HashSet<ChannelValue>();
		this.channelNames.add(channelName);
	}

	@Override
	public String toString()
	{
		return channelNames.toString();
	}

	@Override
	public boolean equals(Object other)
	{
		return channelNames.equals(other);
	}

	@Override
	public int hashCode()
	{
		return channelNames.hashCode();
	}

	@Override
	public String kind()
	{
		return "ChannelNameSetValue";
	}

	@Override
	public Object clone()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		return channelNames.size();
	}

	@Override
	public boolean isEmpty()
	{
		return channelNames.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return channelNames.contains(o);
	}

	@Override
	public Iterator<ChannelValue> iterator()
	{
		return channelNames.iterator();
	}

	@Override
	public Object[] toArray()
	{
		return channelNames.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return channelNames.toArray(a);
	}

	@Override
	public boolean add(ChannelValue e)
	{
		return channelNames.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return channelNames.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return channelNames.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ChannelValue> c)
	{
		return channelNames.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return channelNames.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return channelNames.removeAll(c);
	}

	@Override
	public void clear()
	{
		channelNames.clear();
	}

}
