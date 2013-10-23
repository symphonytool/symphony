package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPAction;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public abstract class MCGuardDef implements MCNode{

	protected Binding max;
	protected int counterId;
	protected MCPCMLExp condition;
	protected MCPAction parentAction;
	
	public MCGuardDef(int counterId, MCPCMLExp condition,
			MCPAction parentAction) {
		super();
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.counterId = counterId;
		this.condition = condition;
		this.parentAction = parentAction;
	}
	
	public Binding getMax() {
		return max;
	}

	public void setMax(Binding max) {
		this.max = max;
	}

	public int getCounterId() {
		return counterId;
	}

	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}

	public MCPCMLExp getCondition() {
		return condition;
	}

	public void setCondition(MCPCMLExp condition) {
		this.condition = condition;
	}

	public MCPAction getParentAction() {
		return parentAction;
	}

	public void setParentAction(MCPAction parentAction) {
		this.parentAction = parentAction;
	}
	
	
}
