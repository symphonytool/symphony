package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;

public class MCAValParametrisation implements MCPParametrisation {

	private MCALocalDefinition declaration;
	
	
	public MCAValParametrisation(MCALocalDefinition declaration) {
		this.declaration = declaration;
	}


	@Override
	public String toFormula(String option) {
		return this.declaration.toFormula(option);
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAValParametrisation){
			result = this.declaration.equals(((MCAValParametrisation) obj).getDeclaration());
		}
		return result;
	}


	public MCALocalDefinition getDeclaration() {
		return declaration;
	}


	public void setDeclaration(MCALocalDefinition declaration) {
		this.declaration = declaration;
	}

	
	
}
