package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class TypeValue implements Type{
	
	private String srt;

	public TypeValue(String srt) {
		this.srt = srt;
	}

	public String getSrt() {
		return srt;
	}

	public void setSrt(String srt) {
		this.srt = srt;
	}

	@Override
	public String toFormula() {
		return this.srt;
	}

	@Override
	public String toFormulaWithState() {
		return toFormula();
	}

	@Override
	public String toFormulaWithUnderscore() {
		return toFormula();
	}

	@Override
	public Type copy() {
		return new TypeValue(this.srt);
	}

	@Override
	public String toFormulaGeneric() {
		return toFormula();
	}

	@Override
	public String toString() {
		return toFormula();
	}

}
