package eu.compassresearch.core.analysis.modelchecker.ast.actions;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class MCAIfStatementAction implements MCPAction {

	private MCPCMLExp ifExp;
	private MCPAction thenStm;
	//private NodeList<AElseIfStatementAction> _elseIf = new NodeList<AElseIfStatementAction>(this);
	private MCPAction elseStm;
	
	
	public MCAIfStatementAction(MCPCMLExp ifExp, MCPAction thenStm,
			MCPAction elseStm) {
		
		this.ifExp = ifExp;
		this.thenStm = thenStm;
		this.elseStm = elseStm;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCPCMLExp getIfExp() {
		return ifExp;
	}


	public void setIfExp(MCPCMLExp ifExp) {
		this.ifExp = ifExp;
	}


	public MCPAction getThenStm() {
		return thenStm;
	}


	public void setThenStm(MCPAction thenStm) {
		this.thenStm = thenStm;
	}


	public MCPAction getElseStm() {
		return elseStm;
	}


	public void setElseStm(MCPAction elseStm) {
		this.elseStm = elseStm;
	}

	
}
