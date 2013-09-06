package eu.compassresearch.core.interpreter.api.values;

import java.util.LinkedList;
import java.util.List;

import org.overture.interpreter.values.Value;

public class ChannelNameValue extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2916244711082591555L;
	
	private final CMLChannelValue channel;
	private final List<Value> values;
	private final List<ValueConstraint> constraints;
	
	public ChannelNameValue(CMLChannelValue channel, List<Value> values, List<ValueConstraint> constraints)
	{
		this.channel = channel;
		this.values = values;
		this.constraints = constraints;
	}
	
	public ChannelNameValue(CMLChannelValue channel, List<Value> values)
	{
		this(channel,values,new LinkedList<ValueConstraint>());
		for (int i = 0; i < values.size(); i++) {
			this.constraints.add(new NoConstraint());
		}
	}
	
	public ChannelNameValue(CMLChannelValue channel)
	{
		this(channel,new LinkedList<Value>(),new LinkedList<ValueConstraint>());
	}
	
	@Override
	public String toString() {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(this.channel.getName());
		for(Value v : values)
			strBuilder.append("." + v.toString());
		
		return strBuilder.toString();
	}

	@Override
	public boolean equals(Object other) {
		
		ChannelNameValue otherChannelName = null;
		
		if(!(other instanceof ChannelNameValue))
			return false;
		
		otherChannelName = (ChannelNameValue)other;
		
		return otherChannelName.getChannel().equals(this.getChannel()) &&
				otherChannelName.getValues().equals(this.getValues())	;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String kind() {
		return "ChannelName";
	}

	@Override
	public Object clone() {
		return new ChannelNameValue(channel, new LinkedList<Value>(values));
	}

	/**
	 * This represents the channel id e.g a.x.y.z would refer to the channel a
	 * @return
	 */
	public CMLChannelValue getChannel() {
		return channel;
	}

	/**
	 * This represents the values after the channel id e.g a.x.y.z would have 3 values x, y and z
	 * @return
	 */
	public List<Value> getValues() {
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

	public ChannelNameValue meet(ChannelNameValue other)
	{
		return (ChannelNameValue)AbstractValueInterpreter.meet(this, other);
	}
	
}
