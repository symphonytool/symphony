package eu.compassresearch.core.analysis.modelchecker.ast.expressions;


public class MCANotUnaryExp implements MCBooleanExp {

	private MCBooleanExp exp;

	
	public MCANotUnaryExp(MCBooleanExp exp) {
		super();
		this.exp = exp;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public MCBooleanExp getExp() {
		return exp;
	}


	public void setExp(MCBooleanExp exp) {
		this.exp = exp;
	}

	
}
