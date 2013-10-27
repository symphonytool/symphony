package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;

public class MCOperationDef implements MCNode {

	protected Binding max;
	protected String name;
	protected MCPCMLExp preCondition;
	protected MCPAction parentAction;
	
	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

}
