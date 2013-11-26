package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

public class EmptySet implements Set {

	@Override
	public String toFormula(String option) {
		return "emptySet";
	}

}
