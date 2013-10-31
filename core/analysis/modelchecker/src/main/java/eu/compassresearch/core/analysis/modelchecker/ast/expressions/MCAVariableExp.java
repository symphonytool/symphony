package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import org.overture.ast.expressions.AVariableExp;

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
		switch (option) {
		case MCNode.DEFAULT:
			//TODO if there is a value defined with this same name, consider it
			result.append(this.getName());
			break;

		case MCNode.NAMED:
			result.append(this.getName());
			break;

		default:
			break;
		}
		
		
		return result.toString();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

}
