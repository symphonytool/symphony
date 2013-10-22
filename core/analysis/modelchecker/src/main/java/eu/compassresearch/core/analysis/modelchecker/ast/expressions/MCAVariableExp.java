package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import org.overture.ast.expressions.AVariableExp;

public class MCAVariableExp implements MCNumericExp {

	private String name;
	
	

	public MCAVariableExp(String name) {
		super();
		this.name = name;
	}



	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

}
