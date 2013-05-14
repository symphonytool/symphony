package eu.compassresearch.core.interpreter.cml.transitions;

import org.overture.ast.types.PType;
import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.PCommunicationParameter;

public abstract class CommunicationParameter {

	protected Value value;
	protected PCommunicationParameter param;
	
	protected CommunicationParameter(Value value,PCommunicationParameter param)
	{
		this.value = value;
		this.param = param;
	}
	
	public Value getValue()
	{
		return value;
	}
	
	public void setValue(Value value)
	{
		this.value = value;
	}
	
	public PType getType()
	{
		return param.getExpression().getType();
	}
	
}
