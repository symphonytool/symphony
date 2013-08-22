package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmChanUtil;

public class ThmChanset extends ThmDecl {

	public String name;
	public String expr;
	

	public ThmChanset(String name, String expr)
	{
		this.name = name;
		this.expr = expr;
	}
	
	//definition "cs = {ch1↓, ch2↓}"
	public String toString(){
		return (ThmChanUtil.isaChanset + " \"" + name + " = " + expr + "\"\n" + tacHook(name));
	}
}
