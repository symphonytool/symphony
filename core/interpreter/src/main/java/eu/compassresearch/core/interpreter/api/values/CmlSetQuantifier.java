package eu.compassresearch.core.interpreter.api.values;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NameValuePair;
import org.overture.interpreter.values.NameValuePairList;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;

public class CmlSetQuantifier extends Value implements
		Iterable<NameValuePairList>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<ILexNameToken> quantifierNames;
	SetValue valueTuples;

	public CmlSetQuantifier(List<ILexNameToken> quantifierNames,
			SetValue valueTuples)
	{
		this.quantifierNames = quantifierNames;
		this.valueTuples = valueTuples;
	}

	@Override
	public String toString()
	{
		return this.quantifierNames.toString()+" = "+this.valueTuples;
	}

	@Override
	public boolean equals(Object other)
	{
		// FIXME include the values
		if (other instanceof CmlSetQuantifier)
		{
			return this.quantifierNames.equals(((CmlSetQuantifier) other).quantifierNames);
		} else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return this.quantifierNames.hashCode() + this.valueTuples.hashCode();
	}

	@Override
	public String kind()
	{
		return "CmlSetQuantifier";
	}

	@Override
	public Object clone()
	{
		return new CmlSetQuantifier(new LinkedList<ILexNameToken>(quantifierNames), valueTuples);
	}

	public int size()
	{
		return this.valueTuples.values.size();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.compassresearch.core.interpreter.api.values.CmlQuantifier#iterator()
	 */
	@Override
	public Iterator<NameValuePairList> iterator()
	{
		return new CmlQuantifierListIterator();
	}

	private class CmlQuantifierListIterator implements
			Iterator<NameValuePairList>
	{
		private int valueIndex = 0;

		@Override
		public boolean hasNext()
		{
			return valueTuples.values.size() > valueIndex;
		}

		@Override
		public NameValuePairList next()
		{
			NameValuePairList next = null;
			// get the next tuple
			try
			{
				ValueList tv = valueTuples.values.get(valueIndex++).tupleValue(null);
				next = new NameValuePairList();
				for (int tupleIndex = 0; tupleIndex < tv.size(); tupleIndex++)
				{
					next.add(new NameValuePair(quantifierNames.get(tupleIndex), tv.get(tupleIndex)));
				}

			} catch (ValueException e)
			{
				e.printStackTrace();
			}

			return next;
		}

		@Override
		public void remove()
		{
			valueTuples.values.remove(--valueIndex);
		}
	}

	public static NameValuePairList pop(Iterator<NameValuePairList> iterator)
	{
		// pop a value
		NameValuePairList value = iterator.next();
		iterator.remove();
		return value;
	}
}
