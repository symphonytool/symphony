package eu.compassresearch.core.interpreter.cml.transitions;

import org.overture.ast.patterns.PPattern;
import org.overture.interpreter.values.UndefinedValue;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.interpreter.values.AnyValue;

public class InputParameter extends CommunicationParameter {

	private AReadCommunicationParameter node;

	public InputParameter(AReadCommunicationParameter node) {
		super(new AnyValue(),node);
		this.node = node;
	}
	
	public PPattern getPattern() {
		return node.getPattern();
	}

	@Override
	public String toString() {
		return "." + getValue();
	}
}
