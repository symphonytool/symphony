package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmChanUtil;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	//definition "init = MkChanD ''init'' \<parallel>()\<parallel>"
	public String toString(){
		return (ThmChanUtil.isaChan + " \"" + name + " = " + ThmChanUtil.isaMkChan + ThmChanUtil.isaChanQuote +
				name + ThmChanUtil.isaChanQuote + ThmChanUtil.chanTpDelim + type + ThmChanUtil.chanTpDelim + "\"");
	}

}
