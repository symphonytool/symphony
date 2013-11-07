package eu.compassresearch.core.analysis.modelchecker.ast.auxiliary;

import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.ast.actions.MCPParametrisation;
import eu.compassresearch.core.analysis.modelchecker.ast.definitions.MCATypeDefinition;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAIntNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANamedInvariantType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCANatNumericBasicType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCAProductType;
import eu.compassresearch.core.analysis.modelchecker.ast.types.MCPCMLType;
import eu.compassresearch.core.analysis.modelchecker.visitors.NewCMLModelcheckerContext;

public class TypeManipulator {
	
	private static TypeManipulator instance;
	
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
		}
		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCANamedInvariantType type){
		ExpressionEvaluator evaluator = ExpressionEvaluator.getInstance();
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();
		NewCMLModelcheckerContext context = NewCMLModelcheckerContext.getInstance();
		
		MCATypeDefinition typeDef = context.getTypeDefinition(type.getName());
		if(typeDef != null){
			LinkedList<String> valueSet = evaluator.getValueSet(typeDef.getInvExpression());
			for (String string : valueSet) {
				result.add(new SingleTypeValue(string));
			}
		}
		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCANatNumericBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		result.add(new SingleTypeValue("0"));

		return result;
	}
	
	public LinkedList<TypeValue> getValues(MCAIntNumericBasicType type){
		LinkedList<TypeValue> result = new LinkedList<TypeValue>();

		result.add(new SingleTypeValue("0"));

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
				//int k = 0;
				
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

}
