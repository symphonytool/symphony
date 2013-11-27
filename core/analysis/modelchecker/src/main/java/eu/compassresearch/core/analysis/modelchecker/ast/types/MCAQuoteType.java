package eu.compassresearch.core.analysis.modelchecker.ast.types;

import org.overture.ast.types.AQuoteType;

public class MCAQuoteType implements MCPCMLType {

	private String value;
	
	
	public MCAQuoteType(String value) {
		super();
		this.value = value;
	}

	@Override
	public String toFormula(String option) {
		return this.value;
	}

	@Override
	public MCPCMLType copy() {
		
		return new MCAQuoteType(new String(this.getValue()));
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
