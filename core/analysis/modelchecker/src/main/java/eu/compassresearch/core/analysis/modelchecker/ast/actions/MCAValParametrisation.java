package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.ast.actions.AValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;

public class MCAValParametrisation implements MCPParametrisation {

	private MCALocalDefinition declaration;
	
	
	public MCAValParametrisation(MCALocalDefinition declaration) {
		this.declaration = declaration;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		//question.getScriptContent().append("Int(");
		//question.getScriptContent().append(node.getDeclaration().getIdentifiers().getFirst().toString());
		//question.getScriptContent().append(")");
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
