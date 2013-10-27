package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;


public class MCAVariableExp implements MCNumericExp {

	private String name;
	
	

	public MCAVariableExp(String name) {
		super();
		this.name = name;
	}



	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		result.append(this.getName());
		
		return result.toString();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

}
