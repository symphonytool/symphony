package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;

public class MCAChannelNameDefinition implements MCPCMLDefinition {

	private MCATypeSingleDeclaration singleType;

	public MCAChannelNameDefinition(MCATypeSingleDeclaration singleType) {
		super();
		this.singleType = singleType;
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public MCATypeSingleDeclaration getSingleType() {
		return singleType;
	}

	public void setSingleType(MCATypeSingleDeclaration singleType) {
		this.singleType = singleType;
	}

	
}
