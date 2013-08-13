package eu.compassresearch.core.analysis.modelchecker.formula.graph.param;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.type.Type;

public class DPar implements Param {

	private Type type1;
	private Type type2;

	public DPar(Type type1, Type type2) {
		this.type1 = type1;
		this.type2 = type2;
	}

}
