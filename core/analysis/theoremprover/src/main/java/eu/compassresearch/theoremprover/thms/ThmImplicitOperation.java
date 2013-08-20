package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmImplicitOperation extends ThmDecl{
	
	private String name;
	private String pre;
	private String post;

	public ThmImplicitOperation(String name, String pre, String post) {
		this.name = name;
		
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
	
	@Override
	public String toString() {
		return (ThmProcessUtil.isaOp + " \"" + name + " = `" + ThmProcessUtil.opExpLeft + pre + ThmProcessUtil.opExpRight + " " +  
				ThmProcessUtil.opTurn + " " + ThmProcessUtil.opExpLeft + post + ThmProcessUtil.opExpRight + "`");
	}
}
