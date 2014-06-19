package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmChanUtil;

public class ThmChannel extends ThmDecl {

	private String name;
	private String type;
	
	public ThmChannel(String name, String type)
	{
		this.name = name;
		//Can't have empty type, so use () if non-typed channel
		if (type.equals(""))
		{
			this.type = "()";
		}
		else
		{
		this.type = type;
		}
	}
	
	/***
	 * To string in form of: definition "init = MkChanD ''init'' \<parallel>()\<parallel>"
	 */
	public String toString(){
		return (ThmChanUtil.isaChan + " \"" + name + " = " + ThmChanUtil.isaMkChan + " " + ThmChanUtil.isaChanQuote +
				name + ThmChanUtil.isaChanQuote + " " + ThmChanUtil.chanTpDelim + type + ThmChanUtil.chanTpDelim + 
				"\"\n" + tacHook(name));
	}
}
