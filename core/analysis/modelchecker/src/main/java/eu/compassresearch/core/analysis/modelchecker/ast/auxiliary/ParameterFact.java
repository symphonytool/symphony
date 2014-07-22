package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCAValParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCALocalDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABooleanBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCARealNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAUnionType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class ParameterFact {
	private String name;
	private MCPCMLExp value;
	private MCPCMLType type;
	
	
	public ParameterFact(String name, MCPCMLExp value, MCPCMLType type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}


	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		if(this.isInfiniteType()){
			result.append("  ParamW(\"");
			result.append(this.name);
			result.append("\",");
			result.append(value.toFormula(option));
			result.append(")");
		}else{
			LinkedList<TypeValue> typeValues = getTypeValues();
			for (TypeValue typeValue : typeValues) {
				result.append("  ParamW(\"");
				result.append(this.name);
				result.append("\",");
				result.append(typeValue.toFormula(option));
				result.append(")");
				result.append("\n");
			}
		}
		return result.toString();
	}

	
	public boolean isInfiniteType(){
		boolean result = false;
		//if(this.parametrisation instanceof MCAValParametrisation){
		//	MCALocalDefinition localDef = parametrisation.getDeclaration();
		//	String name = localDef.getName();
			MCPCMLType realType = type;
			
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
		//}
		
		return result;
	}
	
	private LinkedList<TypeValue> getTypeValues(){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		MCPCMLType realType = this.type; 
		
		//MCPCMLType type = type.getType();
		//if(this.type instanceof MCAChannelType){
		//	this.type = ((MCAChannelType) this.type).getType();
		//if(realType instanceof MCAChannelType){
		//	realType = ((MCAChannelType) realType).getType();
			TypeManipulator typeManipulator = TypeManipulator.getInstance();
			//we check if named types have been previously defined and get all possible values for them
			if(realType instanceof MCANamedInvariantType || realType instanceof MCAProductType || realType instanceof MCABooleanBasicType){ 
				result = typeManipulator.getValues(realType);
			} else{
				if(realType instanceof MCAUnionType){
					result = typeManipulator.getValues(realType);
				} else{
					result = typeManipulator.getValues(realType);
				}
			}  
		//}
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof ParameterFact){
			result = this.name.equals(((ParameterFact) obj).getName());
		}
		return result;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MCPCMLExp getValue() {
		return value;
	}


	public void setValue(MCPCMLExp value) {
		this.value = value;
	}


	public MCPCMLType getType() {
		return type;
	}


	public void setType(MCPCMLType type) {
		this.type = type;
	}
	
	
	
}
