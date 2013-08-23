package eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public class EqualBooleanExpression extends BinaryBooleanExpression  {
	
	public EqualBooleanExpression(Type firstType,Type secondType) {
		super(firstType,secondType);
		
	}

	@Override
	public String toString() {
		return firstType.toString() + "=" + secondType.toString();
	}
	

}
