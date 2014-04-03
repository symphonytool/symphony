package eu.compassresearch.core.analysis.modelchecker.ast.types;


public abstract class MCPCMLNumericType implements MCPCMLType {
	
	private String value;
		
	public MCPCMLNumericType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



	
}
