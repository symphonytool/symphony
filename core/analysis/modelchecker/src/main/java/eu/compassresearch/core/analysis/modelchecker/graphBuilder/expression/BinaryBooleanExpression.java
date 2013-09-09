package eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;

public abstract class BinaryBooleanExpression implements BooleanExpression{

	public Type getFirstType() {
		return firstType;
	}

	public void setFirstType(Type firstType) {
		this.firstType = firstType;
	}

	public Type getSecondType() {
		return secondType;
	}

	public void setSecondType(Type secondType) {
		this.secondType = secondType;
	}

	protected Type firstType;
	protected Type secondType;
	
	public BinaryBooleanExpression(Type firstType, Type secondType) {
		super();
		this.firstType = firstType;
		this.secondType = secondType;
	}


	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof BinaryBooleanExpression){
			BinaryBooleanExpression other = (BinaryBooleanExpression) obj;
			result = this.getFirstType().equals(other.getFirstType()) && this.getSecondType().equals(other.getSecondType());
		}
		return result;
	}
	

}