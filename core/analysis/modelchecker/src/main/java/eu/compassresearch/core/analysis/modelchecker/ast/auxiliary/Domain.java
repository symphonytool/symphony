package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;


public class Domain {
	private String name;
	private Domain parentDomain;
	private String content;
	
	
	public Domain(String name, Domain parentDomain, String content) {
		this.name = name;
		this.parentDomain = parentDomain;
		this.content = content;
	}

	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		
		result.append("domain");
		result.append(" ");
		result.append(this.name);
		result.append(" ");
		if(parentDomain != null){
			result.append("extends");
			result.append(" ");
			result.append(parentDomain.getName());
		}
		result.append(" {\n");
		
		result.append(this.content);
		
		result.append("}\n");
		
		return result.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Domain getParentDomain() {
		return parentDomain;
	}

	public void setParentDomain(Domain parentDomain) {
		this.parentDomain = parentDomain;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	
}
