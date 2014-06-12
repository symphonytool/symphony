package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class SingleTypeValue extends TypeValue {
	private String value;

	public SingleTypeValue(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append(this.value);
		
		return result.toString();
	}

	@Override
	public String toString() {
		return this.toFormula(MCNode.DEFAULT);
	}

	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTypeAsName() {
		// TODO Auto-generated method stub
		return this.toString();
	}
	
	
}
