package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmStateUtil;
import eu.compassresearch.theoremprover.utils.ThmValueUtil;

public class ThmState extends ThmDecl{
	
	public String name;
    public String type;
    
	public ThmState(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	public String getType() {
		return type;
	}


	@Override
	public String toString() {
	//	definition "dw \<equiv> MkVarD ''dw'' DwarfType"
		return (ThmStateUtil.isaState + " \"" + name + ThmStateUtil.stateEquiv + 
				ThmStateUtil.mkvar +" \'\'" + name + "\'\' " + type +"\"");
	}

	
	
}
