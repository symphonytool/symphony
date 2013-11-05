package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAChannelNameDefinition implements MCPCMLDefinition {

	private String name;
	private MCATypeSingleDeclaration typeSingle;
	//private LinkedList<MCChannel> channelDefs;

	public MCAChannelNameDefinition(String name, MCATypeSingleDeclaration typeSingle) {
		this.name = name;
		this.typeSingle = typeSingle;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		switch (option) {
		case MCNode.GENERIC:
			result.append("  Channel(\"");
			result.append(this.name);
			result.append("\"");
			result.append(",");
			result.append("_");
			result.append(")");
			break;

		case MCNode.NAMED:
			result.append("  Channel(\"");
			result.append(this.name);
			result.append("\"");
			result.append(",");
			result.append("PARAM_NAMES");
			result.append(")");
			break;
		
		case MCNode.DEFAULT:
			LinkedList<TypeValue> typeValues = getTypeValues();
			if(typeValues.size() == 0){ //it is (probably an infinite type and must be instantiated by formula)
				result.append("  Channel(\"");
				result.append(this.name);
				result.append("\"");
				result.append(",");
				result.append("_");
				result.append(")");
			} else{
				for (TypeValue typeValue : typeValues) {
					result.append("  Channel(\"");
					result.append(this.name);
					result.append("\"");
					result.append(",");
					result.append(typeValue.toFormula(option));
					result.append(")");
					result.append("\n");
				}
			}
			
			break;
			
		default:
			break;
		}
		
		//the default option for generating channels is generic (with underscore)
		//channel facts generation makes sense only for typed channels
		
		return result.toString();
	}
	
	private LinkedList<TypeValue> getTypeValues(){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		MCPCMLType type = typeSingle.getType();
		if(type instanceof MCAChannelType){
			type = ((MCAChannelType) type).getType();
			
			//MCAIntNumericBasicType, MCANatNumericBasicType are infinite, so we let formula to instantiate them
			
			//we check if named types have been previously defined and get all possible values for them
			if(type instanceof MCANamedInvariantType || type instanceof MCAProductType){ 
				TypeManipulator typeManipulator = TypeManipulator.getInstance();
				result = typeManipulator.getValues(type);
			}
		}
		
		return result;
	}

	public boolean isTyped(){
		boolean result = false;
		MCPCMLType realType = this.getSingleType().getType();
		if(realType instanceof MCAChannelType){
			realType = ((MCAChannelType) realType).getType();
		}
		result = realType != null;
				
		return result;
	}
	
	public MCATypeSingleDeclaration getSingleType() {
		return typeSingle;
	}

	public void setSingleType(MCATypeSingleDeclaration singleType) {
		this.typeSingle = singleType;
	}

	public MCATypeSingleDeclaration getTypeSingle() {
		return typeSingle;
	}

	public void setTypeSingle(MCATypeSingleDeclaration typeSingle) {
		this.typeSingle = typeSingle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
