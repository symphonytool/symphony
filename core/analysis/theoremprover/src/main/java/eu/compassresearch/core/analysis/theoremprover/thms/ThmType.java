package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;

public class ThmType extends ThmDecl {

	//Private components of types
	private String name;
	private String type;
	private String invariant = "";
	
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
	
	/***
	 * Return a String of the form: definition "name   \<equiv> \<parallel>type inv\<parallel>"
	 ***/
	public String toString(){
		return (ThmTypeUtil.isaType + " \"" + name + " = " + 
		    ThmTypeUtil.typeDelim + type + invariant + ThmTypeUtil.typeDelim + "\"\n" + tacHook(name));
		
	}
	
}
