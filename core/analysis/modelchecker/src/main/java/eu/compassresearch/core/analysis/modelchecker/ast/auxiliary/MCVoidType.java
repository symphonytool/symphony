package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

public class MCVoidType extends MCType {

	public MCVoidType() {
		super("void");
	}

	@Override
	public String toFormula(String option) {
		return this.getName();
	}

	
}
