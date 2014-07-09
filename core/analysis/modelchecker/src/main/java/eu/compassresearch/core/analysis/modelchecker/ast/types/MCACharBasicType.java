package eu.compassresearch.core.analysis.modelchecker.ast.types;


public class MCACharBasicType implements MCPCMLType {

	public MCACharBasicType() {
	}
	
	@Override
	public String toFormula(String option) {
		//TODO Implement the method here
		return null;
	}

	@Override
	public MCPCMLType copy() {
		return new MCACharBasicType();
	}

	@Override
	public String getTypeAsName() {
		return null;
	}
}
