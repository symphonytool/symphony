package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;

public class ParameterDependency {
	
	private String name;
	private MCPParametrisation parametrisation;
	private String parentProcessDefinitionName;
	 
	
	public ParameterDependency(String name, MCPParametrisation parametrisation, String parentProcessDefinition) {
		this.name = name;
		this.parametrisation = parametrisation;
		this.parentProcessDefinitionName = parentProcessDefinition;
	}

	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		
			result.append("ParamW(\"");
			result.append(this.name);
			result.append("\",");
			result.append(parametrisation.getDeclaration().getName());
			result.append(")");
		
		return result.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof ParameterDependency){
			result = this.name.equals(((ParameterDependency) obj).getName())
					 && this.parentProcessDefinitionName.equals(((ParameterDependency) obj).getParentProcessDefinitionName());
		}
		return result;
	}
	public MCPParametrisation getParametrisation() {
		return parametrisation;
	}

	public void setParametrisation(MCPParametrisation parametrisation) {
		this.parametrisation = parametrisation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentProcessDefinitionName() {
		return parentProcessDefinitionName;
	}

	public void setParentProcessDefinitionName(String parentProcessDefinitionName) {
		this.parentProcessDefinitionName = parentProcessDefinitionName;
	}

	
	
}
