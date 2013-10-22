package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCABracketedExp implements MCPCMLExp {

	private MCPCMLExp exp;
	
	
	public MCABracketedExp(MCPCMLExp exp) {
		super();
		this.exp = exp;
	}


	@Override
	public String toFormula(String option) {
		return getExp().toFormula(option);
	}


	public MCPCMLExp getExp() {
		return exp;
	}


	public void setExp(MCPCMLExp exp) {
		this.exp = exp;
	}
	
	

}
