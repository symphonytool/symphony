package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmAction extends ThmDecl {

	//private components of an Action
	private String name;
	private String action;
	
	public ThmAction(String name, String action) {
		this.name = name;
		this.action = action;
	}
	
	public String toString(){
		return (ThmProcessUtil.isaAct + " \"" + name + " = `"  + action + "`\"\n" + tacHook(name));
	}
}