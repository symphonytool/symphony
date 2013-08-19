package eu.compassresearch.core.analysis.modelchecker.formula.graph.expression;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.type.Type;

public class BinaryBooleanExpression implements BooleanExpression{

	protected Type firstType;
	protected Type secondType;
	
	public BinaryBooleanExpression(Type firstType, Type secondType) {
		super();
		this.firstType = firstType;
		this.secondType = secondType;
	}

	

}