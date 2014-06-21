package eu.compassresearch.core.analysis.theoremprover.thms;

public class ThmTheorem {

	private String name;
	private String body;
	private String proof;
	private String process;
	
	public ThmTheorem (String n, String b, String p, String proc)
	{
		name = n.replaceAll("-", "_");
		body = b;
		proof = p;
		process = proc;
	}
	
	//lemma sdty: "|mk_DwarfType(&stop, &stop, {}, {}, &stop, &stop) hasType @DwarfSignal| = |true|"
	//	  oops
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("theorem ");
		if (process != null) {
			sb.append("(in " + process + ") ");
			sb.append(name + " [evalp]: \"|");
			sb.append("(@state_inv) => ");
			sb.append(body);
		} else {
			sb.append(name + " [evalp]: \"|");
			sb.append(body);
		}
		
		sb.append("| = |true|\" ");
		sb.append(proof);
		
		return sb.toString();  
	}
}
