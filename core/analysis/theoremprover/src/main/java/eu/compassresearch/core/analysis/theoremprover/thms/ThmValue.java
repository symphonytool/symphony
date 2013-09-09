package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmValueUtil;

public class ThmValue extends ThmDecl {

	//Private components of values
	private String name;
	private String value;
	private String type;
    
	public ThmValue(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns string of the form: "definition \"" + name + " == |" + body +"|\""; 
	 */
	@Override
	public String toString() {
		return (ThmValueUtil.isaValue + " \"" + name + " = " + 
			    ThmValueUtil.valDelimLeft + value + " : " + type + ThmValueUtil.valDelimRight + "\"" + "\n" +
			    tacHook(name));
	}
}
