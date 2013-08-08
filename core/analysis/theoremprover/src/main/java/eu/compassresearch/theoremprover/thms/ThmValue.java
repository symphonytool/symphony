package eu.compassresearch.theoremprover.thms;

public class ThmValue extends ThmDecl {

	public String name;
    public String body;
	
    
    
	public ThmValue(String name, String body) {
		super();
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
		return "abbreviation \"" + name + " == |" + body +"|\""; 
	}

	
	
}
