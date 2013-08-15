package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmTypeUtil;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}


	@Override
	public String toString() {
	//	return "abbreviation \"" + name + " == |" + body +"|\""; 
		return (ThmValueUtil.isaValue + " \"" + name + " = " + 
			    ThmValueUtil.valDelimLeft + value + " : " + type + ThmValueUtil.valDelimRight + "\"");
	}

	
	
}
