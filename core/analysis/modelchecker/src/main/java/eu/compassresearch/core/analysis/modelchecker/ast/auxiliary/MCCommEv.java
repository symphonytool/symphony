package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;

public class MCCommEv implements MCNode {

	private String name;
	private String expression;
	private MCType value;
	
	
	public MCCommEv(String name, String expression, MCType value) {
		super();
		this.name = name;
		this.expression = expression;
		this.value = value;
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


	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public MCType getValue() {
		return value;
	}


	public void setValue(MCType value) {
		this.value = value;
	}

	
}
