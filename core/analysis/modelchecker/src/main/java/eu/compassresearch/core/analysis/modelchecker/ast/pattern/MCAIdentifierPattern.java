package eu.compassresearch.core.analysis.modelchecker.ast.pattern;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAReadCommunicationParameter;

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

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAIdentifierPattern){
			result = this.name.equals(((MCAIdentifierPattern) obj).getName());
		}
		return result;
	}

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	

}
