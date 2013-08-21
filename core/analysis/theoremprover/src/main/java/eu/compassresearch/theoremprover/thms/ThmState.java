package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmStateUtil;

public class ThmState extends ThmDecl{
	
	public String name;
    public String type;
    
	public ThmState(String name, String type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
	//	definition "dw \<equiv> MkVarD ''dw'' DwarfType"
		return (ThmStateUtil.isaState + " \"" + name + ThmStateUtil.stateEquiv + 
				ThmStateUtil.mkvar +" \'\'" + name + "\'\' " + ThmStateUtil.typeDelim + type + ThmStateUtil.typeDelim +"\"\n" + tacHook(name));
	}

}
