package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;


public class ThmProcStand extends ThmDecl {

	public String name;
    public String body;
    
	public ThmProcStand(String name, String body) {
		this.name = name;
		this.body = body;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getBody() {
		return body;
	}


	@Override
	public String toString() {
	//	return "abbreviation \"" + name + " == |" + body +"|\""; 
		return (ThmProcessUtil.isaProc + " \"" + name + " = " + 
				ThmProcessUtil.procDelimLeft + body + ThmProcessUtil.procDelimRight + "\"");
	}
}
