package eu.compassresearch.core.interpreter.api.transitions;

import org.overture.ast.patterns.PPattern;
import org.overture.interpreter.values.UndefinedValue;

import eu.compassresearch.ast.actions.AReadCommunicationParameter;
import eu.compassresearch.core.interpreter.api.values.AnyValue;

public class InputParameter extends CommunicationParameter {

	private AReadCommunicationParameter node;

	public InputParameter(AReadCommunicationParameter node) {
		//TODO The AnyValue should be set to the actual type
		super(new AnyValue(null),node);
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
