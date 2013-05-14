package eu.compassresearch.core.interpreter.cml.transitions;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.AWriteCommunicationParameter;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class OutputParameter extends CommunicationParameter {

	private AWriteCommunicationParameter node;
	
	public OutputParameter(AWriteCommunicationParameter node, Value value)
	{
		super(value,node);
		this.node = node;
	}
	
	@Override
	public String toString() {
		return "." + getValue();
	}
}
