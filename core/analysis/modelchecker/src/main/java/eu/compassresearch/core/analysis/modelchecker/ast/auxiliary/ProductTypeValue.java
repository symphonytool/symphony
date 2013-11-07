package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;


public class ProductTypeValue extends TypeValue {
	private TypeValue firstValue;
	private TypeValue secondValue;
	
	public ProductTypeValue(TypeValue firstValue, TypeValue secondValue) {
		super();
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public TypeValue getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(TypeValue firstValue) {
		this.firstValue = firstValue;
	}

	public TypeValue getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(TypeValue secondValue) {
		this.secondValue = secondValue;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();

		result.append("ProdType(");
		result.append(this.firstValue.toFormula(option));
		result.append(",");
		result.append(this.secondValue.toFormula(option));
		result.append(")");
		
		return result.toString();
	}

	
}
