package eu.compassresearch.core.analysis.modelchecker.ast.pattern;

public class MCAIdentifierPattern implements MCPCMLPattern {

	private String name;
	
	
	public MCAIdentifierPattern(String name) {
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
