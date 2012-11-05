package eu.compassresearch.core.interpreter.values;

import org.overture.ast.lex.LexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.core.interpreter.cml.CMLChannel;
import eu.compassresearch.core.interpreter.cml.CMLChannelSignal;
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
		return "Channel";
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(Value value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signal() {
		// TODO Auto-generated method stub
		
	}

}
