package eu.compassresearch.core.interpreter.values;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CMLChannel;
import eu.compassresearch.core.interpreter.cml.CMLChannelSignal;
import eu.compassresearch.core.interpreter.cml.CMLCommunication;
import eu.compassresearch.core.interpreter.cml.CMLInputChannel;
import eu.compassresearch.core.interpreter.cml.CMLOutputChannel;

public class CMLChannelValue extends Value implements CMLChannel, CMLChannelSignal,CMLOutputChannel<Value> ,CMLInputChannel<Value> {

	private LexNameToken name;
	private PType channelType;
	
	public CMLChannelValue(PType channelType, LexNameToken name)
	{
		this.channelType = channelType;
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name.getName();
	}

	@Override
	public PType getType() {
		return this.channelType;
	}

	@Override
	public String toString() {
		return kind() + " " + getName() + " : " + getType();
	}

	@Override
	public boolean equals(Object other) {
		
		CMLChannelValue otherValue = null;
		
		if(!(other instanceof CMLChannelValue))
			return false;
		
		otherValue = (CMLChannelValue)other;
		
		return otherValue.getName().equals(getName()) &&
				otherValue.getType().equals(getType());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String kind() {
		return "Channel";
	}

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public Value read() {
		return null;
	}

	@Override
	public void write(Value value) {
		
	}

	@Override
	public void signal() {
		
	}

}
