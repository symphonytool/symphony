package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmProcAction extends ThmDecl{
	
	private String name;
	private String body;
	private String mainAction;
    
	public ThmProcAction(String name, String body) {
		this.name = name;
		this.body = body;
	}

	/**
	 * MEthod to retun string in the form:  "abbreviation \"" + name + " == |" + body +"|\""; 
	 */
	@Override
	public String toString() {
		return (ThmProcessUtil.isaActProc + " " + name + "\n" + ThmProcessUtil.isaProcBegin + "\n" + 
				body +  "\n" + ThmProcessUtil.isaProcEnd);
	}
}
