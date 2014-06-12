package eu.compassresearch.core.analysis.modelchecker.ast.types;


public class MCVoidType implements MCPCMLType {

	public MCVoidType() {
		
	}


	@Override
	public String toFormula(String option) {
		return "void";
	}


	@Override
	public MCPCMLType copy() {
		return new MCVoidType();
	}


	@Override
	public String getTypeAsName() {
		return "void";
	}

	
}
