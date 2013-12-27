package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;

public class NamedSet implements Set {

	private String name;
	
	
	public NamedSet(String name) {
		this.name = name;
	}

	@Override
	public String toFormula(String option) {
		return this.name;
	}

	@Override
	public Set addElement(MCPCMLExp elem) {
		NonEmptySet result = new NonEmptySet(elem,this); 
		
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
