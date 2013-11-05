package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;

public class MCAValParametrisation implements MCPParametrisation {

	private MCATypeSingleDeclaration declaration;
	
	
	public MCAValParametrisation(MCATypeSingleDeclaration declaration) {
		super();
		this.declaration = declaration;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCATypeSingleDeclaration getDeclaration() {
		return declaration;
	}


	public void setDeclaration(MCATypeSingleDeclaration declaration) {
		this.declaration = declaration;
	}

	
}
