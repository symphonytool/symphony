package eu.compassresearch.core.analysis.modelchecker.ast.types;

import org.overture.ast.types.ABooleanBasicType;

public class MCABooleanBasicType implements MCPCMLType {

	private boolean value;
	
	
	public MCABooleanBasicType() {
		this.value = false;
	}
	
	public MCABooleanBasicType(boolean value) {
		this.value = value;
	}

	@Override
	public String toFormula(String option) {
		return String.valueOf(this.value);
	}

	@Override
	public MCPCMLType copy() {
		return new MCABooleanBasicType(value);
	}

	@Override
	public String getTypeAsName() {
		return MCTypeWrapper.getTypeWrapperString(this.getClass());
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	

	
	
}
