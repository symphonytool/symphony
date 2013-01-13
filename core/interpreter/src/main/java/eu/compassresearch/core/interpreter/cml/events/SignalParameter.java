package eu.compassresearch.core.interpreter.cml.events;

import org.overture.interpreter.values.Value;

import eu.compassresearch.ast.actions.ASignalCommunicationParameter;
import eu.compassresearch.core.interpreter.events.ChannelObserver;
import eu.compassresearch.core.interpreter.values.CMLChannelValue;

public class SignalParameter extends CommunicationParameter {

	private ASignalCommunicationParameter node;
	
	public SignalParameter(ASignalCommunicationParameter node, Value value)
	{
		super(value);
	}
	
	@Override
	public String toString() {
		return "." + getValue();
	}

	@Override
	public void handleChannelEventRegistration(CMLChannelValue channel,
			ChannelObserver observer) {
		channel.onChannelSignal().registerObserver(observer);
		
	}

	@Override
	public void handleChannelEventUnregistration(CMLChannelValue channel,
			ChannelObserver observer) {
		channel.onChannelSignal().registerObserver(observer);
		
	}
	
//	private static Value extractValue(ASignalCommunicationParameter node)
//	{
//		ASignalCommunicationParameter signal = (ASignalCommunicationParameter)p;
//		//TODO: this will change in the next AST version
//		AExpressionPattern patternExp  = (AExpressionPattern)signal.getPattern(); 
//		return patternExp.getExp().apply(cmlEvaluator,question);
//	}
	
}
