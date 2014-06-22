package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.List;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmProcessUtil;

public class ThmProcAction extends ThmDecl{
	
	private String name;
	private String body;
	private List<String> param;
	private String mainAction;
    
	public ThmProcAction(String name, List<String> param, String body) {
		this.name = name;
		this.param = param;
		this.body = body;
	}

	/**
	 * MEthod to retun string in the form:  "abbreviation \"" + name + " == |" + body +"|\""; 
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ThmProcessUtil.isaActProc + " " + name);
		
		if (!param.isEmpty()) {
			sb.append (" = \n");
			for (String s : param) {
				sb.append(s + "\n");
			}
		}
		
		sb.append("\n");
		
		sb.append(ThmProcessUtil.isaProcBegin + "\n" + 
				  body +  "\n" + ThmProcessUtil.isaProcEnd);

		return sb.toString();
	}
}
