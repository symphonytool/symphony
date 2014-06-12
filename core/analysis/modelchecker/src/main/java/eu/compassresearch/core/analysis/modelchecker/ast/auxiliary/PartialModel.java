package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class PartialModel {
	
	private Domain domain;
	private String content;
	
	public PartialModel(Domain domain) {
		this.domain = domain;
		this.content = "";
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String toFormula(String option){
		StringBuilder result = new StringBuilder();

		//it generates the introduce facts if necessary
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		if(context.introduceFacts.size() > 0){
			result.append(context.introduceFacts.toString());
		}
		result.append("\n");
		result.append("partial model StartProcModel of ");
		result.append(this.domain.getName() + "{\n");

		result.append(this.content);
		
		result.append("\n}");
		
		return result.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		
		return this.toFormula(MCNode.DEFAULT);
	}
	
	
	
}
