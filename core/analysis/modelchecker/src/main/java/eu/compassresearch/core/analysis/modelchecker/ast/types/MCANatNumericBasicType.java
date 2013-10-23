package eu.compassresearch.core.analysis.modelchecker.ast.types;

public class MCANatNumericBasicType implements MCPCMLType {

	public MCANatNumericBasicType() {
	}

	@Override
	public String toFormula(String option) {
		
		return "Natural";
	}

}
