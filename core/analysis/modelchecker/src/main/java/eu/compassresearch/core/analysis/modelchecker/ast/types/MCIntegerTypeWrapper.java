package eu.compassresearch.core.analysis.modelchecker.ast.types;


public class MCIntegerTypeWrapper extends MCTypeWrapper {

	public MCIntegerTypeWrapper(MCPCMLType internalType) {
		super(internalType);
	}

	@Override
	public MCTypeWrapper copy() {
		return new MCIntegerTypeWrapper(internalType.copy());
	}

}
