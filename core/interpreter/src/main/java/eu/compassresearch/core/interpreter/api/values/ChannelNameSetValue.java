package eu.compassresearch.core.interpreter.api.values;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueSet;

public class ChannelNameSetValue extends SetValue implements Set<ChannelValue>
{

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;


	public ChannelNameSetValue()
	{
		super();
	}

	public ChannelNameSetValue(Set<ChannelValue> channelNames)
	{
		super(convertToValueSet(channelNames));
	}
	
	public ChannelNameSetValue(ValueSet values)
	{
		super(values);
	}

	private static ValueSet convertToValueSet(Set<ChannelValue> channelNames)
	{
		ValueSet set = new ValueSet();
		for (ChannelValue c : channelNames)
		{
			set.add(c);
		}
		return set;
	}

	public ChannelNameSetValue(ChannelValue channelName)
	{
		super();
		add(channelName);
	}

	@Override
	public String toString()
	{
		return values.toString();
	}

	@Override
	public String kind()
	{
		return "ChannelNameSetValue";
	}

	@Override
	public Object clone()
	{
		ChannelNameSetValue set = new ChannelNameSetValue();
		for (ChannelValue channelValue : this)
		{
			set.add((ChannelValue) channelValue.clone());
		}
		return set;
	}

	@Override
	public int size()
	{
		return values.size();
	}

	@Override
	public boolean isEmpty()
	{
		return values.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return values.contains(o);
	}

	@Override
	public Iterator<ChannelValue> iterator()
	{
		final Iterator<Value> itr = values.iterator();
		return new Iterator<ChannelValue>()
		{

			@Override
			public boolean hasNext()
			{
				return itr.hasNext();
			}

			@Override
			public ChannelValue next()
			{
				return (ChannelValue) itr.next();
			}

			@Override
			public void remove()
			{
				itr.remove();
			}
		};
	}

	@Override
	public Object[] toArray()
	{
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return values.toArray(a);
	}

	@Override
	public boolean add(ChannelValue e)
	{
		return values.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return values.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return values.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ChannelValue> c)
	{
		return values.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return values.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return values.removeAll(c);
	}

	@Override
	public void clear()
	{
		values.clear();
	}

}
