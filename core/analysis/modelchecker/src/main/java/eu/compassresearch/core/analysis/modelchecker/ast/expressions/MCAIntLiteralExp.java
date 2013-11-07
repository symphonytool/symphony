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
		return this.value;
	}

	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAIntLiteralExp){
			result = this.value.equals(((MCAIntLiteralExp) obj).getValue());
		}
		return result;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	
}
