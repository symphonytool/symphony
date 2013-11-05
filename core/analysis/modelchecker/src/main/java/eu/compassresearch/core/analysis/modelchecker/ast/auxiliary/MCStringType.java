package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;

public class MCStringType implements MCPCMLType {

	private String value;
	
	public MCStringType(String value) {
		this.value = value;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		switch (option) {
		case MCNode.DEFAULT:
			result.append("\"" + this.value + "\"");
			break;

		case MCNode.NAMED:
			result.append(this.value);
			break;

		default:
			break;
		}
		return result.toString();
	}


	@Override
	public MCPCMLType copy() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
