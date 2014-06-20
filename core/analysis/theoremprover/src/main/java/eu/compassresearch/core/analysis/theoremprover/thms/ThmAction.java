package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmAction extends ThmDecl {

	//private components of an Action
	private String name;
	private String act;
	
	public ThmAction(String name, String act) {
		this.name = name;
		this.act = act;
	}
	
	public String toString(){
		return ("  " + name + " = \""  + act + "\"");
	}
}