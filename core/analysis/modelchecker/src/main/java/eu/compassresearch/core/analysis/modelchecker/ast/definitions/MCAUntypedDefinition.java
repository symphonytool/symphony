package eu.compassresearch.core.analysis.modelchecker.ast.definitions;


public class MCAUntypedDefinition implements MCPCMLDefinition {

	private String name;
	
	
	public MCAUntypedDefinition(String name) {
		super();
		this.name = name;
	}


	@Override
	public String toFormula(String option) {
		return this.name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
}
