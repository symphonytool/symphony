package eu.compassresearch.core.interpreter.api.values;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.values.Value;

public class NamesetValue extends Value implements Set<ILexNameToken>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3293768665736520072L;
	Set<ILexNameToken> names;

	public NamesetValue(Set<ILexNameToken> names)
	{
		this.names = names;
	}

	@Override
	public String toString()
	{
		return names.toString();
	}

	@Override
	public boolean equals(Object other)
	{
		return names.equals(other);
	}

	@Override
	public int hashCode()
	{
		return names.hashCode();
	}

	@Override
	public String kind()
	{
		return "NamesetValue";
	}

	@Override
	public Object clone()
	{
		return new NamesetValue(names);
	}

	@Override
	public int size()
	{
		return names.size();
	}

	@Override
	public boolean isEmpty()
	{
		return names.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return names.contains(o);
	}

	@Override
	public Iterator<ILexNameToken> iterator()
	{
		return names.iterator();
	}

	@Override
	public Object[] toArray()
	{
		return names.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return names.toArray(a);
	}

	@Override
	public boolean add(ILexNameToken e)
	{
		return names.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return names.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return names.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ILexNameToken> c)
	{
		return names.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return names.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return names.removeAll(c);
	}

	@Override
	public void clear()
	{
		names.clear();
	}

}
