package eu.compassresearch.core.analysis.modelchecker.ast.types;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;

public class MCANatNumericBasicType extends MCPCMLNumericType {

	
	public MCANatNumericBasicType(String value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		switch (option) {
		case MCNode.DEFAULT:
			result.append("Natural");
			break;
		
		case MCNode.NAMED:
			result.append(this.getValue());
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
