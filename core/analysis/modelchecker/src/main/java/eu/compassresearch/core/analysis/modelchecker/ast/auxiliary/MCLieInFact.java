package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPVarsetExpression;

public class MCLieInFact implements MCNode {
	
	private MCCommEv commEvent;
	private MCPVarsetExpression setExp;
	
	
	public MCLieInFact(MCCommEv commEvent, MCPVarsetExpression setExp) {
		this.commEvent = commEvent;
		this.setExp = setExp;
	}


	@Override
	public String toFormula(String option) {
		StringBuilder lieIn = new StringBuilder();
		lieIn.append("  lieIn(");
		lieIn.append(commEvent.toFormula(option));
		lieIn.append(",");
		lieIn.append(setExp.toFormula(option));
		lieIn.append(")");
		
		return lieIn.toString();
	}


	public MCCommEv getCommEvent() {
		return commEvent;
	}


	public void setCommEvent(MCCommEv commEvent) {
		this.commEvent = commEvent;
	}


	public MCPVarsetExpression getSetExp() {
		return setExp;
	}


	public void setSetExp(MCPVarsetExpression setExp) {
		this.setExp = setExp;
	}


	

	
}
