package eu.compassresearch.core.analysis.modelchecker.ast.types;

import java.util.HashMap;

import eu.compassresearch.core.analysis.modelchecker.ast.MCNode;

public abstract class MCTypeWrapper {
	protected static final String INTEGER_TYPE_WRAPPER_STR = "IntegerW";
	protected static final String NATURAL_TYPE_WRAPPER_STR = "NaturalW";
	protected static final String REAL_TYPE_WRAPPER_STR = "RealW";
	protected static final String BOOLEAN_TYPE_WRAPPER_STR = "BooleanW";
	protected static final String STRING_TYPE_WRAPPER_STR = "StringW";
	
	protected MCPCMLType internalType;
	
	protected static HashMap<Class, String> typeWrapperMap;
	
	static{
		typeWrapperMap = new HashMap<Class, String>();
		typeWrapperMap.put(MCAIntNumericBasicType.class, INTEGER_TYPE_WRAPPER_STR);
		typeWrapperMap.put(MCANatNumericBasicType.class, NATURAL_TYPE_WRAPPER_STR);
		typeWrapperMap.put(MCABooleanBasicType.class, BOOLEAN_TYPE_WRAPPER_STR);
		//OTHER TYPES ARE NOT IMPLEMENTED YET
	}
	
	public static String getTypeWrapperString(Class theClass){
		return typeWrapperMap.get(theClass);
	}
	
	public MCTypeWrapper(MCPCMLType internalType) {
		this.internalType = internalType;
	}
	
	public String toFormula(String option){
		StringBuilder result = new StringBuilder();
		String typeWrapper = typeWrapperMap.get(internalType.getClass());
		
		result.append(typeWrapper);
		switch (option) {
		case MCNode.GENERIC:
			result.append("(_)");
			break;
		default:
			result.append("(");
			result.append(internalType.toFormula(option));
			result.append("(");
			break;
		}
		return result.toString();
	}
	
	public abstract MCTypeWrapper copy();
	
	public static String getWrapperForType(String typeName){
		StringBuilder result = new StringBuilder(); 
		
		result.append(typeName + "W");
		
		return result.toString();
	} 
	
	public static String getNameForWapper(String wrapperName){
		StringBuilder result = new StringBuilder(); 
		int lastIndex = wrapperName.lastIndexOf('W');
		
		result.append(wrapperName.substring(0, lastIndex));
		
		return result.toString();
	}
	public MCPCMLType getInternalType() {
		return internalType;
	}
	public void setInternalType(MCPCMLType internalType) {
		this.internalType = internalType;
	}
	
	
}
