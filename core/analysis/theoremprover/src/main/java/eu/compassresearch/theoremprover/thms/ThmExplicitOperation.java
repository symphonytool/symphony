package eu.compassresearch.theoremprover.thms;

import java.util.LinkedList;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;
import eu.compassresearch.theoremprover.utils.ThmStateUtil;

public class ThmExplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;
	private String body;

	public ThmExplicitOperation(String name, String pre, String post, String initExprs) {
		this.name = name;
		this.body = initExprs;
		
		if (pre == null)
		{
			this.pre = "true";
		}
		else
		{
			this.pre = pre;
		}
		
		if (post == null)
		{
			this.post = "true";
		}
		else
		{
			this.post = post;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	//definition "Init = `true ⊢ (dw := mk_DwarfType(&stop, {}, {}, &stop, &stop, &stop))`"
	@Override
	public String toString() {
		return (ThmProcessUtil.isaOp + " \"" + name + " = `" + ThmProcessUtil.opExpLeft + pre + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + post + ThmProcessUtil.opExpRight + " \\and (" +
				body + ")`");
	}

	
	
	
}
