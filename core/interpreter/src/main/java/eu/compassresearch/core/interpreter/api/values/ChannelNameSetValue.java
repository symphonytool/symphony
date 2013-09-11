package eu.compassresearch.core.interpreter.api.values;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.overture.interpreter.values.Value;

public class ChannelNameSetValue extends Value implements Set<ChannelNameValue>{
	
	Set<ChannelNameValue> channelNames;
	
	public ChannelNameSetValue(Set<ChannelNameValue> channelNames)
	{
		this.channelNames = channelNames;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String kind() {
		return "ChannelNameSetValue";
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return channelNames.size();
	}

	@Override
	public boolean isEmpty() {
		return channelNames.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return channelNames.contains(o);
	}

	@Override
	public Iterator<ChannelNameValue> iterator() {
		return channelNames.iterator();
	}

	@Override
	public Object[] toArray() {
		return channelNames.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return channelNames.toArray(a);
	}

	@Override
	public boolean add(ChannelNameValue e) {
		return channelNames.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return channelNames.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return channelNames.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ChannelNameValue> c) {
		return channelNames.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return channelNames.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return channelNames.removeAll(c);
	}

	@Override
	public void clear() {
		channelNames.clear();
	}

}
