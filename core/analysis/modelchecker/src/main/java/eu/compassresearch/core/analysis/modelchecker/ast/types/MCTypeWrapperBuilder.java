package eu.compassresearch.core.analysis.modelchecker.ast.types;

public class MCTypeWrapperBuilder {

	public static MCTypeWrapper buildTypeWrapper(MCPCMLType type){
		MCTypeWrapper result = null;
		
		if(type instanceof MCAIntNumericBasicType){
			result = new MCIntegerTypeWrapper(type);
		}else if(type instanceof MCANatNumericBasicType){
			result = new MCNaturalTypeWrapper(type);
		}
		return result;
	}

}
