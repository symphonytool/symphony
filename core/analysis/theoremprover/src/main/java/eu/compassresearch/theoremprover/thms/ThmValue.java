package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmValueUtil;

public class ThmValue extends ThmDecl {

	public String name;
    public String value;
    public String type;
    
	public ThmValue(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}


	@Override
	public String toString() {
	//	return "definition \"" + name + " == |" + body +"|\""; 
		return (ThmValueUtil.isaValue + " \"" + name + " = " + 
			    ThmValueUtil.valDelimLeft + value + " : " + type + ThmValueUtil.valDelimRight + "\"" + "\n" +
			    tacHook(name));
	}

	
	
}
