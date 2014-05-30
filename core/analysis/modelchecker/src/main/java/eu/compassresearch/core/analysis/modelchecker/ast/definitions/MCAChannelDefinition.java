package eu.compassresearch.core.analysis.modelchecker.ast.definitions;

import java.util.Iterator;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ActionChannelDependency;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.ExpressionEvaluator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.IntroduceCommand;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeManipulator;
import eu.compassresearch.core.analysis.modelchecker.ast.auxiliary.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCPCMLExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABooleanBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAChannelType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCTypeWrapper;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCTypeWrapperBuilder;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCVoidType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class MCAChannelDefinition implements MCPCMLDefinition {

	private String name;
	private MCPCMLType type;
	//private LinkedList<MCChannel> channelDefs;

	public MCAChannelDefinition(String name, MCPCMLType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toFormula(String option) {
		StringBuilder result = new StringBuilder();
		
		if(this.isTyped()){
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
					
					NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
					int numberOfInstances = context.getNumberOfInstances();
					MCPCMLType realType = this.type; 
					
					if(realType instanceof MCAChannelType){
						realType = ((MCAChannelType) realType).getType();
					}
					IntroduceCommand introduce = new IntroduceCommand(realType); 
					context.addIntroduce(introduce);
										
					//lets try to get from the dependencies
					result.append("\n   //channels obtained from dependencies \n");
					LinkedList<ActionChannelDependency> dependencies = context.getActionChannelDependendiesByChannelName(this.name);
					if(dependencies.size() > 0){
						for (Iterator<ActionChannelDependency> iterator = dependencies.iterator(); iterator.hasNext();) {
							ActionChannelDependency actionChannelDependency = (ActionChannelDependency) iterator.next();
							String channelFact = actionChannelDependency.toFormula(option);
							if(result.indexOf(channelFact) == -1){
								result.append(channelFact);
								//to complete the Channel line if its type is infinite
								if(this.isTyped()){
									if(this.isInfiniteType()){
										result.append(" :- ");
										
										result.append(MCTypeWrapper.getTypeWrapperString(realType.getClass()));
										result.append("(");
										
										ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
										MCPCMLType paramTypes = evaluator.instantiateMCTypeFromCommParams(actionChannelDependency.getParameters());
										
										result.append(paramTypes.toFormula(option));
										result.append(").");
										//MCTypeWrapper typeWrapper =  MCTypeWrapperBuilder.buildTypeWrapper(realType);
										//if(typeWrapper != null){
											//result.append(typeWrapper.toFormula(option));
											//result.append(typeWrapper.toFormula("."));
										//}
									}
								}
								if(iterator.hasNext()){
									result.append("\n");
								}
							}
						}
					}else{
						result.append("  Channel(\"");
						result.append(this.name);
						result.append("\"");
						result.append(",");
						result.append("_");
						result.append(")");
					}
				} else{ 
					for (TypeValue typeValue : typeValues) {
						result.append("  Channel(\"");
						result.append(this.name);
						result.append("\"");
						result.append(",");
						result.append(typeValue.toFormula(option));
						result.append(").");
						result.append("\n");
					}
				}
				
				break;
				
			default:
				break;
			}
		}
		
		//the default option for generating channels is generic (with underscore)
		//channel facts generation makes sense only for typed channels
		
		return result.toString();
	}
	
	public boolean isInfiniteType(){
		boolean result = false;
		LinkedList<TypeValue> possibleValues = this.getTypeValues();
		result = this.isTyped() && (possibleValues.size() == 0);
		return result;
	}
	private LinkedList<TypeValue> getTypeValues(){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		MCPCMLType realType = this.type; 
		
		//MCPCMLType type = type.getType();
		//if(this.type instanceof MCAChannelType){
		//	this.type = ((MCAChannelType) this.type).getType();
		if(realType instanceof MCAChannelType){
			realType = ((MCAChannelType) realType).getType();
				
			//we check if named types have been previously defined and get all possible values for them
			if(realType instanceof MCANamedInvariantType || realType instanceof MCAProductType || realType instanceof MCABooleanBasicType){ 
				TypeManipulator typeManipulator = TypeManipulator.getInstance();
				result = typeManipulator.getValues(realType);
			} else{
				//MCAIntNumericBasicType, MCANatNumericBasicType are infinite, so we let formula to instantiate them
				if(realType instanceof MCANamedInvariantType){
					
				}
			}  
		}
		
		return result;
	}

	public boolean isTyped(){
		boolean result = false;
		//MCPCMLType realType = this.getSingleType().getType();
		//if(realType instanceof MCAChannelType){
		//	realType = ((MCAChannelType) realType).getType();
		//}
		if(type != null){
			if (type instanceof MCAChannelType){
				result = !(((MCAChannelType) type).getType() instanceof MCVoidType);
			} else{
				result = true;
			}
		}
				
		return result;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof MCAChannelDefinition){
			result = this.name.equals(((MCAChannelDefinition) obj).getName());
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MCPCMLType getType() {
		return type;
	}

	public void setType(MCPCMLType type) {
		this.type = type;
	}

	
}
