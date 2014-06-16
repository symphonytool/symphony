package eu.compassresearch.core.analysis.theoremprover.thms;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmProcStand extends ThmDecl {

	//private components of standard (non-action) process
	private String name;
    private String body;
    
	public ThmProcStand(String name, String body) {
		this.name = name;
		this.body = body;
	}

	/***
	 * Returns a string of the form:  
	 * "definition \"" + name + " == |" + body +"|\""; 
	 */
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(ThmProcessUtil.isaActProc + " " + name);
				
		sb.append("\n");
		
		sb.append(ThmProcessUtil.isaProcBegin + "\n");

	    sb.append(ThmProcessUtil.isaProc + " \"" + ThmProcessUtil.isaMainAction + " = " 
	              + ThmProcessUtil.procDelimLeft + body + ThmProcessUtil.procDelimRight + "\"\n" + tacHook(name));
				  
		sb.append("\n" + ThmProcessUtil.isaProcEnd);

		return sb.toString();
	}
}
