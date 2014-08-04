package eu.compassresearch.core.interpreter.api.values;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.api.InterpreterRuntimeException;

public class ChannelValue extends Value
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2916244711082591555L;

	private final CmlChannel channel;
	private final List<Value> values;
	private final List<ValueConstraint> constraints;

	public ChannelValue(CmlChannel channel, List<Value> values,
			List<ValueConstraint> constraints)
	{
		this.channel = channel;
		this.values = values;
		this.constraints = constraints;
	}

	public ChannelValue(CmlChannel channel, List<Value> values)
	{
		this(channel, values, new LinkedList<ValueConstraint>());
		for (int i = 0; i < values.size(); i++)
		{
			this.constraints.add(new NoConstraint());
		}
	}

	public ChannelValue(CmlChannel channel)
	{
		this(channel, new LinkedList<Value>(), new LinkedList<ValueConstraint>());
	}

	/*
	 * Copy constructor
	 */
	protected ChannelValue(ChannelValue other)
	{
		this.channel = other.channel;
		this.values = new LinkedList<Value>(other.values);
		this.constraints = new LinkedList<ValueConstraint>(other.constraints);
	}

	@Override
	public String toString()
	{

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(this.channel.getName());
		for (Value v : values)
		{
			strBuilder.append("." + v);
		}

		return strBuilder.toString();
	}

	@Override
	public boolean equals(Object other)
	{

		ChannelValue otherChannelName = null;

		if (!(other instanceof ChannelValue))
		{
			return false;
		}

		otherChannelName = (ChannelValue) other;

		return otherChannelName.getChannel().equals(this.getChannel())
				&& otherChannelName.getValues().equals(this.getValues());
	}

	@Override
	public int hashCode()
	{
		return channel.hashCode() + values.hashCode();
	}

	@Override
	public String kind()
	{
		return "ChannelName";
	}

	@Override
	public Object clone()
	{
		return new ChannelValue(channel, new LinkedList<Value>(values));
	}

	/**
	 * This represents the channel id e.g a.x.y.z would refer to the channel a
	 * 
	 * @return
	 */
	public CmlChannel getChannel()
	{
		return channel;
	}

	/**
	 * This represents the values after the channel id e.g a.x.y.z would have 3 values x, y and z
	 * 
	 * @return
	 */
	public List<Value> getValues()
	{
		return values;
	}

	public boolean hasValues()
	{
		return this.values.size() > 0;
	}

	public boolean isPrecise()
	{
		return AbstractValueInterpreter.isValueMostPrecise(this);
	}

	public ChannelValue meet(ChannelValue other)
	{
		List<Value> meetValues = new LinkedList<Value>();
		List<ValueConstraint> meetConstraints = new LinkedList<ValueConstraint>();
		// find the meet value and make a copy
		for (int i = 0; i < values.size(); i++)
		{
			meetValues.add(AbstractValueInterpreter.meet(this.values.get(i), other.values.get(i)));
		}

		// Combine all the constraint as a MultiConstraint
		for (int i = 0; i < constraints.size(); i++)
		{
			meetConstraints.add(MultiConstraint.combine(this.constraints.get(i), other.constraints.get(i)));
		}

		return new ChannelValue(this.channel, meetValues, meetConstraints);
	}

	public ChannelValue rename(CmlChannel channel)
	{
		return new ChannelValue(channel, values, constraints);
	}

	public boolean isComparable(ChannelValue channelNameValue)
	{
		return this.getChannel().equals(channelNameValue.channel)
				&& isValuesComparable(channelNameValue);
	}

	private boolean isValuesComparable(ChannelValue channelNameValue)
	{
		for (Iterator<Value> thisIt = values.listIterator(), otherIt = channelNameValue.values.listIterator(); thisIt.hasNext()
				&& otherIt.hasNext();)
		{
			Value thisValue = thisIt.next();
			Value otherValue = otherIt.next();

			// if both values are not loose and they differ then we cannot compare them
			if (AbstractValueInterpreter.isValueMostPrecise(thisValue)
					&& AbstractValueInterpreter.isValueMostPrecise(otherValue)
					&& !thisValue.equals(otherValue))
			{
				return false;
			}

		}

		return true;
	}

	/**
	 * Checks if <b>other</b> is equal or more precise than this. e.g. if <b>this</b> is c.? and other is c.1 then true else false. <br/>
	 * <b>Note the twitched args compared to the method name.</b>
	 * 
	 * @param other
	 *            the transition to check if is isEquallyOrMorePrecise to this
	 * @return
	 */
	public boolean isEquallyOrMorePrecise(ChannelValue other)
	{
		boolean res = true;
		for (int i = 0; i < values.size(); i++)
		{
			Value thisValue = values.get(i);
			Value otherValue = other.values.get(i);
			res &= AbstractValueInterpreter.isEquallyOrMorePrecise(otherValue, thisValue);
		}

		return res;
	}

	public boolean isConstraintValid() throws AnalysisException
	{
		for (int i = 0; i < values.size(); i++)
		{
			if (!constraints.get(i).isValid(values.get(i)))
			{
				return false;
			}
		}

		return true;
	}

	public void updateValues(List<Value> values)
	{
		for (int i = 0; i < values.size(); i++)
		{
			if (this.values.size() < i)
			{
				updateValue(i, values.get(i));
			} else
			{
				this.values.add(values.get(i));
			}
		}
	}

	public void updateValue(int index, Value value)
	{
		if (value == null)
		{
			throw new InterpreterRuntimeException("Null value parsed to channel name value update index: "
					+ index);
		}
		values.set(index, value);
	}

	/**
	 * return the constraint associated with the value
	 * 
	 * @return
	 */
	public List<ValueConstraint> getConstraints()
	{
		return this.constraints;
	}
}
