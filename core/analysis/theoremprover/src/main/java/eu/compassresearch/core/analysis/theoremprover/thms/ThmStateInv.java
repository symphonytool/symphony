package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmStateUtil;

public class ThmStateInv extends ThmDecl {

	//Private components of state
	private String name;
    private String expr;
    
	public ThmStateInv(String name, String expr) {
		this.name = name;
		this.expr = expr;
	}

	/********
	 * Return a string of the form: "dw \<equiv> MkVarD ''dw'' DwarfType"
	 ********/
	@Override
	public String toString() {
		return (ThmStateUtil.isaState + " \"" + name + " = " + "|" + expr + "|\"\n");
	}

	public String getName() {
		return name;
	}
	
	
}
