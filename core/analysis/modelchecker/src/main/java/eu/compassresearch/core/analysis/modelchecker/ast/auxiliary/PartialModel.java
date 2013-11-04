package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

public class PartialModel {
	
	private Domain domain;
	
	
	public PartialModel(Domain domain) {
		super();
		this.domain = domain;
	}

	


	public Domain getDomain() {
		return domain;
	}




	public void setDomain(Domain domain) {
		this.domain = domain;
	}




	public String toFormula(String option){
		//TODO
		return null;
	}
}
