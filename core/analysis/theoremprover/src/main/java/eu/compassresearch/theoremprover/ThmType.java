package eu.compassresearch.theoremprover;

import java.util.LinkedList;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.PType;

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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setInv(String inv) {
		this.invariant = " " + ThmUtil.isaInv + " " + invariant;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	public String getInv() {
		return invariant;
	}
	
	public String toString(){
		return (ThmUtil.isaType + " \"" + name + " = " + 
			    ThmUtil.typeDelim + type + invariant + ThmUtil.typeDelim + " \"");
	}
	
}
