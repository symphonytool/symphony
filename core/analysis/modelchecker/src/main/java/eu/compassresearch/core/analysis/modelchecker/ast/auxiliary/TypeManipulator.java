package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;
import eu.compassresearch.core.analysis.modelchecker.ast.declarations.MCATypeSingleDeclaration;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAChansetDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCAValueDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.expressions.MCANameChannelExp;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABooleanBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCABracketType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAQuoteType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCARealNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCASetType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAUnionType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class TypeManipulator {
	
	private static TypeManipulator instance;
	private static int VARIABLE_NUMBER=1; 
	
	private TypeManipulator(){
		
	}

	public static TypeManipulator getInstance(){
		if(instance == null){
			instance = new TypeManipulator();
		}
		return instance;
	}
	
	//it gives the values defined by a type
	public LinkedList<TypeValue> getValues(MCPCMLType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		if(type instanceof MCANamedInvariantType){
			result = this.getValues((MCANamedInvariantType)type);
		} else if(type instanceof MCAProductType){
			result = this.getValues((MCAProductType)type);
		} else if(type instanceof MCANatNumericBasicType){
			result = this.getValues((MCANatNumericBasicType)type);
		} else if(type instanceof MCAIntNumericBasicType){
			result = this.getValues((MCAIntNumericBasicType)type);
		} else if(type instanceof MCARealNumericBasicType){
			result = this.getValues((MCARealNumericBasicType)type);
		} else if(type instanceof MCAUnionType){
			result = this.getValues((MCAUnionType)type);
		} else if(type instanceof MCATypeSingleDeclaration){
			result = this.getValues((MCATypeSingleDeclaration)type);
		} else if(type instanceof MCASetType){
			result = this.getValues((MCASetType)type);
		} else if(type instanceof MCABooleanBasicType){
			result = this.getValues((MCABooleanBasicType)type);
		}else if(type instanceof MCABracketType){
			result = this.getValues((MCABracketType)type);
		}else if(type instanceof MCAQuoteType){
			result = this.getValues((MCAQuoteType)type);
		}
		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCANamedInvariantType type){

		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		LinkedList<String> valueSet = new LinkedList<String>();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		//if it is a value definition, its values must be taken from the value definition
		MCAValueDefinition valueDef = context.getValueDefinition(type.getName());
		
		if(valueDef != null){
			valueSet = evaluator.getValueSet(valueDef.getExpression());
			for (String string : valueSet) {
				result.add(new SingleTypeValue(string));
			}
		}else{
			MCATypeDefinition typeDef = context.getTypeDefinition(type.getName());
			if(typeDef != null){
				 
				if(typeDef.getInvExpression() != null){
					
					valueSet = evaluator.getValueSet(typeDef.getInvExpression());
				}else{
					LinkedList<TypeValue> typeValues = getValues(typeDef.getType());
					for (TypeValue typeValue : typeValues) {
						valueSet.add(typeValue.toFormula(MCNode.DEFAULT));
					}
				}
				for (String string : valueSet) {
					result.add(new SingleTypeValue(string));
				}
			} else{//the type can be defined in a chanset definition
				MCAChansetDefinition chansetDef = context.getChansetDefinition(type.getName());
				if(chansetDef != null){
					LinkedList<MCANameChannelExp> chansetValues = chansetDef.getChansetExpression().getChannelNames();
					for (MCANameChannelExp mcaNameChannelExp : chansetValues) {
						result.add(new SingleTypeValue(mcaNameChannelExp.getIdentifier()));
					}
				} else{ //name invariant has the same type as the original type
					typeDef = context.getTypeDefinition(type.getOriginalTypeName());
					if(typeDef != null){
						if(typeDef.hasValues()){
							result = getValues(new MCANamedInvariantType(type.getOriginalTypeName(), type.getOriginalTypeName()));
						}else{
							MCPCMLType originalType = context.getFinalType(type.getOriginalTypeName());
							//originalType = getTypeForStringNameType(type.getOriginalTypeName());
							originalType = getTypeForStringNameType(originalType.toFormula(MCNode.DEFAULT));
							//if(getTypeForStringNameType(type.getOriginalTypeName()) != null){
								result = getValues(originalType);
							//}
						}
					} else {
						MCPCMLType originalType = getTypeForStringNameType(type.getOriginalTypeName());
						//if(getTypeForStringNameType(type.getOriginalTypeName()) != null){
						result = getValues(originalType);
						//}
						if (result.size() == 0){
							TypeValue typeValue = new SingleTypeValue(type.getName());
							result.add(typeValue);
						}
					}
				}
				
			}
		}
		return result;
	}
	
	private MCPCMLType getTypeForStringNameType(String nameType){
		MCPCMLType result = null;
		if(nameType.equals("nat")){
			result = new MCANatNumericBasicType("0");
		} else if (nameType.equals("int")){
			result = new MCAIntNumericBasicType("0");
		}
		return result;
	} 
	
	public LinkedList<TypeValue> getValues(MCANatNumericBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		//lets put as many variable values in the result as are the number of instances
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (int i = 0; i < context.getNumberOfInstances(); i++) {
			//we need to use a new variable generator
			result.add(new SingleTypeValue("x"+i));
			//result.add(new SingleTypeValue("x"+VARIABLE_NUMBER++));
		}
		
		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCAIntNumericBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		//result.add(new SingleTypeValue("0"));
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (int i = 0; i < context.getNumberOfInstances(); i++) {
			result.add(new SingleTypeValue("x"+i));
		}

		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCARealNumericBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		//result.add(new SingleTypeValue("0"));
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		for (int i = 0; i < context.getNumberOfInstances(); i++) {
			result.add(new SingleTypeValue("x"+i));
		}

		return result;
	}

	public LinkedList<TypeValue> getValues(MCAUnionType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		for (MCPCMLType currType : type.getTypes()) {
			if(currType instanceof MCAQuoteType){
				result.add(new SingleTypeValue(((MCAQuoteType) currType).getValue()));
			} else if (currType instanceof MCANamedInvariantType){
				LinkedList<TypeValue> values = this.getValues(currType);
				result.addAll(values);
			}
		}
		

		return result;
	}
	public LinkedList<TypeValue> getValues(MCABracketType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		result = getValues(type.getType());
		/*
		for (MCPCMLType currType : type.getTypes()) {
			if(currType instanceof MCAQuoteType){
				result.add(new SingleTypeValue(((MCAQuoteType) currType).getValue()));
			} else if (currType instanceof MCANamedInvariantType){
				LinkedList<TypeValue> values = this.getValues(currType);
				result.addAll(values);
			}
		}
		*/

		return result;
	}

	public LinkedList<TypeValue> getValues(MCAQuoteType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		TypeValue typeValue = new SingleTypeValue(type.getValue());
		result.add(typeValue);
		
		return result;
	}
	public LinkedList<TypeValue> getValues(MCABooleanBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		result.add(new SingleTypeValue("true"));
		result.add(new SingleTypeValue("false"));
		return result;
	}
	public LinkedList<TypeValue> getValues(MCAProductType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		//if(type.getTypes().size() > 0){
			LinkedList<MCPCMLType> typesCopy = new LinkedList<MCPCMLType>();
			typesCopy.addAll(type.getTypes());
			
			LinkedList<TypeValue> firstValues = this.getValues(typesCopy.getFirst());
			typesCopy.removeFirst();
			
			if(typesCopy.size() > 0){
				MCAProductType remainingTypes = new MCAProductType(typesCopy);
				LinkedList<TypeValue> remainingValues = this.getValues(remainingTypes);
				
				
				for (TypeValue fstTypeValue : firstValues) {
					for (TypeValue sndTypeValue : remainingValues) {
						ProductTypeValue prodValue = new ProductTypeValue(fstTypeValue, sndTypeValue);
						result.add(prodValue);
					}
				}
			}else{
				result.addAll(firstValues);
			}
		//}
		
		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCATypeSingleDeclaration type){
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		MCATypeDefinition typeDef = context.getTypeDefinition(type.getIdentifier());
		if(typeDef != null){
			LinkedList<String> valueSet = new LinkedList<String>(); 
			if(typeDef.getInvExpression() != null){
				valueSet = evaluator.getValueSet(typeDef.getInvExpression());
			}else{
				LinkedList<TypeValue> typeValues = getValues(typeDef.getType());
				for (TypeValue typeValue : typeValues) {
					valueSet.add(typeValue.toFormula(MCNode.DEFAULT));
				}
			}
			for (String string : valueSet) {
				result.add(new SingleTypeValue(string));
			}
		} else{//the type can be defined in a chanset definition
			MCAChansetDefinition chansetDef = context.getChansetDefinition(type.getIdentifier());
			if(chansetDef != null){
				LinkedList<MCANameChannelExp> chansetValues = chansetDef.getChansetExpression().getChannelNames();
				for (MCANameChannelExp mcaNameChannelExp : chansetValues) {
					result.add(new SingleTypeValue(mcaNameChannelExp.getIdentifier()));
				}
			}
			
		}
		return result;
	}

	public LinkedList<TypeValue> getValues(MCASetType type){
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		MCPCMLType internalType = type.getSetOf();
		if(internalType instanceof MCANamedInvariantType){
			MCATypeDefinition typeDef = context.getTypeDefinition(((MCANamedInvariantType) internalType).getName());
			if(typeDef != null){
				LinkedList<String> valueSet = new LinkedList<String>(); 
				if(typeDef.getInvExpression() != null){
					valueSet = evaluator.getValueSet(typeDef.getInvExpression());
				}else{
					LinkedList<TypeValue> typeValues = getValues(typeDef.getType());
					for (TypeValue typeValue : typeValues) {
						valueSet.add(typeValue.toFormula(MCNode.DEFAULT));
					}
				}
				for (String string : valueSet) {
					result.add(new SingleTypeValue(string));
				}
			} else{//the type can be defined in a chanset definition
				MCAChansetDefinition chansetDef = context.getChansetDefinition(((MCANamedInvariantType) internalType).getName());
				if(chansetDef != null){
					LinkedList<MCANameChannelExp> chansetValues = chansetDef.getChansetExpression().getChannelNames();
					for (MCANameChannelExp mcaNameChannelExp : chansetValues) {
						result.add(new SingleTypeValue(mcaNameChannelExp.getIdentifier()));
					}
				}
				
			}
		}else{
		  //nothing to do for the moment	
		}
		
		return result;
	}
}
