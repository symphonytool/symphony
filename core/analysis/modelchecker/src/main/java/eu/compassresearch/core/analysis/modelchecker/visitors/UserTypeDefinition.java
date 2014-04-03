package eu.compassresearch.core.analysis.modelchecker.visitors;

import org.overture.ast.definitions.ATypeDefinition;

public class UserTypeDefinition {
	protected ATypeDefinition typeDefinition;

	public UserTypeDefinition(ATypeDefinition typeDefinition) {
		super();
		this.typeDefinition = typeDefinition;
	}

	public ATypeDefinition getTypeDefinition() {
		return typeDefinition;
	}

	public void setTypeDefinition(ATypeDefinition typeDefinition) {
		this.typeDefinition = typeDefinition;
	}
	
	
}
