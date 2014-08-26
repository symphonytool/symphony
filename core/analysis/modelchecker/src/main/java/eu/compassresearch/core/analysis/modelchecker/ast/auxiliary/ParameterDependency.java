package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCARealNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

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
	
	public boolean containsVariable(String variableName){
		boolean result = false;
		
		result = variableName.equals(this.parametrisation.getDeclaration().getName());
		
		return result;
	}
	
	private boolean isInfiniteType(){
		boolean result = false;
		if(this.parametrisation instanceof MCAValParametrisation){
			MCALocalDefinition localDef = parametrisation.getDeclaration();
			String name = localDef.getName();
			MCPCMLType realType = localDef.getType();
			
			if(realType instanceof MCAChannelType){
				realType = ((MCAChannelType) realType).getType();
			} 
			if(realType instanceof MCANamedInvariantType){
				NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
				realType = context.getFinalType(((MCANamedInvariantType) realType).getName());
				//MCATypeDefinition typeDef = context.getTypeDefinition(((MCANamedInvariantType) realType).getName());
				//if(typeDef != null){
				//	realType = typeDef.getType();
				//} 
			}
			
			result = (realType instanceof MCAIntNumericBasicType || realType instanceof MCANatNumericBasicType || realType instanceof MCARealNumericBasicType);
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
