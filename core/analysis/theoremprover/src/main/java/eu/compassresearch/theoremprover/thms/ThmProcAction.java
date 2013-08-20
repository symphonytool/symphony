package eu.compassresearch.theoremprover.thms;

import eu.compassresearch.theoremprover.utils.ThmProcessUtil;

public class ThmProcAction extends ThmDecl{
	public String name;
    public String body;
    public String mainAction;
    
	public ThmProcAction(String name, String body, String mainAction) {
		this.name = name;
		this.body = body;
		this.mainAction = mainAction;
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

	public String getMainAction() {
		return mainAction;
	}

	@Override
	public String toString() {
	//	return "abbreviation \"" + name + " == |" + body +"|\""; 
		return (ThmProcessUtil.isaActProc + " " + name + "\n" + ThmProcessUtil.isaProcBegin + "\n" + 
				body + "\n " + mainAction + "\n" + ThmProcessUtil.isaProcEnd);
	}
}
