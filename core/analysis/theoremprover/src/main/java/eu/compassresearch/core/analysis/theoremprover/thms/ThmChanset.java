package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmChanUtil;

public class ThmChanset extends ThmDecl {

	private String name;
	private String expr;
	

	public ThmChanset(String name, String expr)
	{
		this.name = name;
		this.expr = expr;
	}
	
	/***
	 * To string in the form: definition "cs = {ch1↓, ch2↓}"
	 */
	public String toString(){
		return (ThmChanUtil.isaChanset + " \"" + name + " = " + expr + "\"\n" + tacHook(name));
	}
}
