package eu.compassresearch.core.analysis.theoremprover.thms;

public class ThmTheorem {

	private String name;
	private String body;
	private String proof;
	
	public ThmTheorem (String n, String b, String p)
	{
		name = n;
		body = b;
		proof = p;
	}
	
	//lemma sdty: "|mk_DwarfType(&stop, &stop, {}, {}, &stop, &stop) hasType @DwarfSignal| = |true|"
	//	  oops
	public String toString()
	{
		return "theorem " + name + " : \"|" + body + "| = |true|\" \n " + proof;  
	}
}
