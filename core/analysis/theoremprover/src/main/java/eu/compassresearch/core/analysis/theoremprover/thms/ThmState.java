package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmStateUtil;

public class ThmState extends ThmDecl{
	
	//Private components of state
	private String name;
    private String type;
    
	public ThmState(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/********
	 * Return a string of the form: "dw \<equiv> MkVarD ''dw'' DwarfType"
	 ********/
	@Override
	public String toString() {
		return (ThmStateUtil.isaState + " \"" + name + ThmStateUtil.stateEquiv + 
				ThmStateUtil.mkvar +" \'\'" + name + "\'\' " + ThmStateUtil.typeDelim + type + ThmStateUtil.typeDelim +"\"\n" + tactic(name, state));
	}

}
