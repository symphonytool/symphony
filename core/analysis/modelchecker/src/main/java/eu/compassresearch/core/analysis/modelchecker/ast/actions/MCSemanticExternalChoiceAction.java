package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.Binding;

public class MCSemanticExternalChoiceAction extends MCAExternalChoiceAction {

	private Binding leftBinding;
	private Binding rightBinding;
	
	
	public MCSemanticExternalChoiceAction(MCPAction left, MCPAction right, Binding leftBind, Binding rightBind) {
		super(left, right);
		this.leftBinding = leftBind;
		this.rightBinding = rightBind;
	}


	public Binding getLeftBinding() {
		return leftBinding;
	}


	public void setLeftBinding(Binding leftBinding) {
		this.leftBinding = leftBinding;
	}


	public Binding getRightBinding() {
		return rightBinding;
	}


	public void setRightBinding(Binding rightBinding) {
		this.rightBinding = rightBinding;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append("extraChoice(");
		result.append(this.getLeftBinding().toFormula(MCNode.NAMED));
		result.append(",");
		result.append(this.getLeft().toFormula(DEFAULT));
		result.append(",");
		result.append(this.getRightBinding().toFormula(MCNode.NAMED));
		result.append(",");
		result.append(this.getRight().toFormula(DEFAULT));
		
		result.append(")");

		
		return result.toString();
	}

	
	
}
