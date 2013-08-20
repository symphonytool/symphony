package eu.compassresearch.theoremprover.thms;

public class ThmExpr implements ThmArtifact{

	private String body;
	
	public ThmExpr(String body){
		this.body = body;
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public String toString(){
		return body;
	}
}
