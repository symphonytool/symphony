package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmChanUtil;

public class ThmChannel extends ThmDecl {

	public String name;
	public String type;
	

	public ThmChannel(String name, String type)
	{
		this.name = name;
		if (type.equals("")){
			this.type = "()";
		}
		else{
		this.type = type;
		}
	}
	
	//definition "init = MkChanD ''init'' \<parallel>()\<parallel>"
	public String toString(){
		return (ThmChanUtil.isaChan + " \"" + name + " = " + ThmChanUtil.isaMkChan + ThmChanUtil.isaChanQuote +
				name + ThmChanUtil.isaChanQuote + ThmChanUtil.chanTpDelim + type + ThmChanUtil.chanTpDelim + 
				"\"\n" + tacHook(name));
	}

}
