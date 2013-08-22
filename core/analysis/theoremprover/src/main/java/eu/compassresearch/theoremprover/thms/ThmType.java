package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

public class ThmType extends ThmDecl {

	public String name;
	public String type;
	public String invariant = "";
	

	public ThmType(String name, String type)
	{
		this.name = name;
		this.type = type;
	}
	
	public ThmType(String name, String type, String inv)
	{
		this.name = name;
		this.type = type;
		this.invariant = inv;
	}
	
	public String toString(){
		return (ThmTypeUtil.isaType + " \"" + name + " = " + 
		    ThmTypeUtil.typeDelim + type + invariant + ThmTypeUtil.typeDelim + "\"\n" + tacHook(name));
		
	}
	
}
