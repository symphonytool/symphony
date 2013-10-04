package eu.compassresearch.core.analysis.modelchecker.graphBuilder.type;

public class Str implements Type {
	
	private String value;
	
	
	public Str(String value) {
		this.value = value;
	}


	@Override
	public Str copy(){
		Str result = new Str(this.value);
		return result;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String toFormula() {
		return "Str(\""+ value + "\")";
	}

	@Override
	public String toFormulaWithState() {
		return "Str(\""+ value + "\")";
	}

	public String toFormulaWithUnderscore(){
		return "Str(_)";
	}
}
