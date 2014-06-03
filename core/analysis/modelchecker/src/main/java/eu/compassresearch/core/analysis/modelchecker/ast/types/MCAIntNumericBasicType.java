package eu.compassresearch.core.analysis.modelchecker.ast.types;

import org.overture.ast.types.AIntNumericBasicType;

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
		
		case MCNode.SEMANTIC_NAMED:
			result.append("Integer");
			break;
			
		default:
			result.append(this.getValue());
			break;
		}
		return result.toString();
	}

	@Override
	public MCPCMLType copy() {
		return new MCAIntNumericBasicType(new String(this.getValue()));
	}

	@Override
	public String getTypeAsName() {
		return MCTypeWrapper.getTypeWrapperString(this.getClass());
	}
}
