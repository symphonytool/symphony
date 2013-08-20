package eu.compassresearch.theoremprover;

public class ThmTypeAbbrev {//extends ThmType {

	String body;
	String name;

	public ThmTypeAbbrev(String name, String body) {
	//	super();
		this.name = name;
		this.body = body;
	}

	@Override
	public String toString() {
		return "abbreviation \"" + name + " == \\<parallel>" + body + "\\<parallel>\"";
	}
	
	
	
}
