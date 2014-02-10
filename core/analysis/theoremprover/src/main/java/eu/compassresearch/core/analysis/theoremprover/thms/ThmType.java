package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.patterns.AIdentifierPattern;
import org.overture.ast.patterns.PPattern;

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
	
	public ThmType(String name, String type, String inv, LinkedList<String> invparams)
	{
		this.name = name;
		this.type = type;
		this.invariant = createInvCall(inv, invparams);
	}
	
	/*****
	 * Method to create the invariant call for a datatype
	 * @param invname
	 * @param invparams
	 * @return
	 */
	private String createInvCall(String invname, LinkedList<String> invparams) {
		if (invname != "")
		{
			//generate the list of parameters
			String paramList = "";
			String funccalllist = "";
			for (Iterator<String> p = invparams.listIterator(); p.hasNext(); ) {
				String pname = p.next();
				paramList = paramList + pname + " ";
				funccalllist = funccalllist + "&"+ pname;
				if(p.hasNext())
				{
					funccalllist = funccalllist + ", ";
				}
			}
			//make the invariant call
			String invString  = " inv " + paramList + "== " + invname +"(" + funccalllist + ")";
			
			return invString;
		}
		else return "";
	}

	/***
	 * Return a String of the form: definition "name   \<equiv> \<parallel>type inv\<parallel>"
	 ***/
	public String toString(){
		return (ThmTypeUtil.isaType + " \"" + name + " = " + 
		    ThmTypeUtil.typeDelim + type + invariant + ThmTypeUtil.typeDelim + "\"\n" + tacHook(name));
		
	}
	
}
