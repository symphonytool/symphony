package eu.compassresearch.core.analysis.theoremprover.thms;

public class ThmExpr implements ThmArtifact{

	private String body;
	
	public ThmExpr(String body){
		this.body = body;
	}
	
	/**
	 * To string simply returns the body of the expression
	 */
	public String toString(){
		return body;
	}
}
