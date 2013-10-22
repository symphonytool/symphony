package eu.compassresearch.core.analysis.modelchecker.ast.expressions;

import org.overture.ast.expressions.AIntLiteralExp;

public class MCAIntLiteralExp implements MCNumericExp {

	private String value;

	
	public MCAIntLiteralExp(String value) {
		super();
		this.value = value;
	}


	@Override
	public String toFormula(String option) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	
}
