package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;

public interface MCPParametrisation extends MCNode {
	public MCALocalDefinition getDeclaration();
}
