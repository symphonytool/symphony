package eu.compassresearch.core.analysis.modelchecker.ast.types;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;

public class MCAIntNumericBasicType extends MCPCMLNumericType {

	
	
	public MCAIntNumericBasicType(String value) {
		super(value);
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		switch (option) {
		case MCNode.DEFAULT:
			result.append(this.getValue());
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
		return new MCAIntNumericBasicType(new String(this.getValue()));
	}

}
