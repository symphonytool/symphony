package eu.compassresearch.core.analysis.modelchecker.ast.types;

public class MCAIntNumericBasicType implements MCPCMLType {

	public MCAIntNumericBasicType() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toFormula(String option) {
		return "Natural";
	}

}
