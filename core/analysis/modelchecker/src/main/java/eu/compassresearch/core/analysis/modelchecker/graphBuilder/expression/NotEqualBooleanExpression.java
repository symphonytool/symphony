package eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class NotEqualBooleanExpression extends BinaryBooleanExpression {

	
	
	public NotEqualBooleanExpression(Type firstType,Type secondType) {
		super(firstType,secondType);		
	}
	
	@Override
	public String toString() {
		return firstType.toString() + "!=" + secondType.toString();
	}
}
