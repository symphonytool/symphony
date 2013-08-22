package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmAction extends ThmDecl {


	public String name;
	public String action;
	
	public ThmAction(String name, String action) {
		this.name = name;
		this.action = action;
		
	}
	
	public String toString(){
		return (ThmProcessUtil.isaAct + " \"" + name + " = " + action + "\"\n" + tacHook(name));
	}


}
