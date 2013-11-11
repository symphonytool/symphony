package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

public class MCABooleanConstExp implements MCBooleanExp {

	private boolean value;
	
	public MCABooleanConstExp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public MCPCMLExp copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
