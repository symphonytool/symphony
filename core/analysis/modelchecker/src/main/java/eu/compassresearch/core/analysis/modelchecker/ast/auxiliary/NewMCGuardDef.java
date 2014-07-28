package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.statements.MCPCMLStm;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public abstract class NewMCGuardDef implements MCNode{

	protected Binding max;
	protected int counterId;
	protected MCPCMLExp condition;
	protected MCPCMLStm parentStm;
	
	public NewMCGuardDef(int counterId, MCPCMLExp condition,
			MCPCMLStm parentStm) {
		super();
		this.max = NewCMLModelcheckerContext.getInstance().maximalBinding;
		this.counterId = counterId;
		this.condition = condition;
		this.parentStm = parentStm;
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

	public MCPCMLStm getParentStm() {
		return parentStm;
	}

	public void setParentStm(MCPCMLStm parentStm) {
		this.parentStm = parentStm;
	}


}
